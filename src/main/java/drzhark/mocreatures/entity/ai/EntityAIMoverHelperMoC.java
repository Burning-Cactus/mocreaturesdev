package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.MathHelper;

public class EntityAIMoverHelperMoC extends MovementController {

    CreatureEntity theCreature;
    protected MovementController.Action action = MovementController.Action.WAIT;

    public boolean hasWanted()
    {
        return this.action == MovementController.Action.MOVE_TO;
    }

    public double getSpeedModifier()
    {
        return this.speedModifier;
    }

    /**
     * Sets the speed and location to move to
     */
    public void setWantedPosition(double x, double y, double z, double speedIn)
    {
        this.wantedX = x;
        this.wantedY = y;
        this.wantedZ = z;
        this.speedModifier = speedIn;
        this.action = MovementController.Action.MOVE_TO;
    }

    public void strafe(float forward, float strafe)
    {
        this.action = MovementController.Action.STRAFE;
        this.strafeForwards = forward;
        this.strafeRight = strafe;
        this.speedModifier = 0.25D;
    }

    /*public void read(EntityMoveHelper that)
    {
        this.action = that.action;
        this.posX = that.posX;
        this.posY = that.posY;
        this.posZ = that.posZ;
        this.speed = Math.max(that.speed, 1.0D);
        this.moveForward = that.moveForward;
        this.moveStrafe = that.moveStrafe;
    }*/

    public void onUpdateMoveOnGround()
    {
        if (this.action == MovementController.Action.STRAFE)
        {
            float f = (float)this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue();
            float f1 = (float)this.speedModifier * f;
            float f2 = this.strafeForwards;
            float f3 = this.strafeRight;
            float f4 = MathHelper.sqrt(f2 * f2 + f3 * f3);

            if (f4 < 1.0F)
            {
                f4 = 1.0F;
            }

            f4 = f1 / f4;
            f2 = f2 * f4;
            f3 = f3 * f4;
            float f5 = MathHelper.sin(this.mob.yRot * 0.017453292F);
            float f6 = MathHelper.cos(this.mob.yRot * 0.017453292F);
            float f7 = f2 * f6 - f3 * f5;
            float f8 = f3 * f6 + f2 * f5;
            PathNavigator pathnavigate = this.mob.getNavigation();

            if (pathnavigate != null)
            {
                NodeProcessor nodeprocessor = pathnavigate.getNodeEvaluator();

                if (nodeprocessor != null && nodeprocessor.getBlockPathType(this.mob.level, MathHelper.floor(this.mob.getX() + (double)f7), MathHelper.floor(this.mob.getY()), MathHelper.floor(this.mob.getZ() + (double)f8)) != PathNodeType.WALKABLE)
                {
                    this.strafeForwards = 1.0F;
                    this.strafeRight = 0.0F;
                    f1 = f;
                }
            }

            this.mob.setSpeed(f1);
            this.mob.setZza(this.strafeForwards);
            this.mob.setXxa(this.strafeRight);
            this.action = MovementController.Action.WAIT;
        }
        else if (this.action == MovementController.Action.MOVE_TO)
        {
            this.action = MovementController.Action.WAIT;
            double d0 = this.wantedX - this.mob.getX();
            double d1 = this.wantedZ - this.mob.getZ();
            double d2 = this.wantedY - this.mob.getY();
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;

            if (d3 < 2.500000277905201E-7D)
            {
                this.mob.setZza(0.0F);
                return;
            }

            float f9 = (float)(MathHelper.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
            this.mob.yRot = this.rotlerp(this.mob.yRot, f9, 20.0F);
            this.mob.setSpeed((float)(this.speedModifier * this.mob.getAttribute(Attributes.MOVEMENT_SPEED).getValue()));

            if (d2 > (double)this.mob.maxUpStep && d0 * d0 + d1 * d1 < (double)Math.max(1.0F, this.mob.getBbWidth()))
            {
                this.mob.getJumpControl().jump();
            }
        }
        else
        {
            this.mob.setZza(0.0F);
        }
    }

    /**
     * Limits the given angle to a upper and lower limit.
     */
    protected float rotlerp(float p_75639_1_, float p_75639_2_, float p_75639_3_)
    {
        float f = MathHelper.wrapDegrees(p_75639_2_ - p_75639_1_);

        if (f > p_75639_3_)
        {
            f = p_75639_3_;
        }

        if (f < -p_75639_3_)
        {
            f = -p_75639_3_;
        }

        float f1 = p_75639_1_ + f;

        if (f1 < 0.0F)
        {
            f1 += 360.0F;
        }
        else if (f1 > 360.0F)
        {
            f1 -= 360.0F;
        }

        return f1;
    }

    public double getWantedX()
    {
        return this.wantedX;
    }

    public double getWantedY()
    {
        return this.wantedY;
    }

    public double getWantedZ()
    {
        return this.wantedZ;
    }

    public static enum Action
    {
        WAIT,
        MOVE_TO,
        STRAFE;
    }

    public EntityAIMoverHelperMoC(LivingEntity entityliving) {
        super((MobEntity) entityliving);
        this.theCreature = (CreatureEntity) entityliving;
    }

    @Override
    public void tick() {
        boolean isFlyer = ((IMoCEntity) theCreature).isFlyer();
        boolean isSwimmer = this.theCreature.isInWater(); 
        float fLimitAngle = 90F;
        if (!isFlyer && !isSwimmer) {
            onUpdateMoveOnGround();
            return;
        }

        /*
         * Flying specific movement code
         */
        if (isFlyer && !theCreature.isVehicle()) {
            this.flyingMovementUpdate();
        }

        /*
         * Water movement code
         */
        if (isSwimmer) {
            this.swimmerMovementUpdate();
            fLimitAngle = 30F;
        }
        if (this.action == MovementController.Action.MOVE_TO && !this.theCreature.getNavigation().isDone()) {
            double d0 = this.wantedX - this.theCreature.getX();
            double d1 = this.wantedY - this.theCreature.getY();
            double d2 = this.wantedZ - this.theCreature.getZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            d3 = MathHelper.sqrt(d3);
            if (d3 < 0.5) {
                this.mob.setZza(0.0F);
                this.theCreature.getNavigation().stop();
                return;
            }
            //System.out.println("distance to objective = " + d3 + "objective: X = " + this.posX + ", Y = " + this.posY + ", Z = " + this.posZ);
            d1 /= d3;
            float f = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            this.theCreature.yRot = this.rotlerp(this.theCreature.yRot, f, fLimitAngle);
            this.theCreature.yBodyRot = this.theCreature.yRot;
            float f1 = (float) (this.speedModifier * this.theCreature.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
            this.theCreature.setSpeed(this.theCreature.getSpeed() + (f1 - this.theCreature.getSpeed()) * 0.125F);
            double d4 = Math.sin((double) (this.theCreature.tickCount + this.theCreature.getId()) * 0.75D) * 0.01D;
            double d5 = Math.cos((double) (this.theCreature.yRot * (float) Math.PI / 180.0F));
            double d6 = Math.sin((double) (this.theCreature.yRot * (float) Math.PI / 180.0F));
            this.theCreature.getDeltaMovement().add(d4 * d5,
                    (d4 * (d6 + d5) * 0.25D) + ((double)this.theCreature.getSpeed() * d1 * 1.5D),
                    d4 * d6);
            //d4 = Math.sin((double)(this.theCreature.ticksExisted + this.theCreature.getEntityId()) * 0.75D) * 0.05D;
        } //TODO: Fix the motion field calls in this class
    }
    
    /**
     * Makes flying creatures reach the proper flying height
     */
    private void flyingMovementUpdate() {

        //Flying alone
        if (((IMoCEntity) theCreature).getIsFlying()) {
            int distY = MoCTools.distanceToFloor(this.theCreature);
            if (distY <= ((IMoCEntity) theCreature).minFlyingHeight()
                    && (this.theCreature.horizontalCollision || this.theCreature.level.random.nextInt(100) == 0)) {
                this.theCreature.getDeltaMovement().add(0, 0.02D, 0);
            }
            if (distY > ((IMoCEntity) theCreature).maxFlyingHeight() || this.theCreature.level.random.nextInt(150) == 0) {
                this.theCreature.getDeltaMovement().subtract(0, 0.02D, 0);
            }

        } else {
            if (this.theCreature.getDeltaMovement().y < 0) {
                this.theCreature.getDeltaMovement().multiply(1, 0.6D, 1);
            }
        }

    }

    /**
     * Makes creatures in the water float to the right depth
     */
    private void swimmerMovementUpdate() {
        if (theCreature.isVehicle()) {
            return;
        }

        double distToSurface = (MoCTools.waterSurfaceAtGivenEntity(theCreature) - theCreature.getY());
        if (distToSurface > ((IMoCEntity) theCreature).getDivingDepth()) {
            if (theCreature.getDeltaMovement().y() < 0) {
                theCreature.setDeltaMovement(theCreature.getDeltaMovement().x, 0, theCreature.getDeltaMovement().z);
            }
            theCreature.getDeltaMovement().add(0, 0.001D + distToSurface * 0.01, 0);
        }

        if (!theCreature.getNavigation().isDone() && theCreature.horizontalCollision) {
            if (theCreature instanceof MoCEntityAquatic) {
                theCreature.getDeltaMovement().add(0, 0.05D, 0);
            } else {
                ((IMoCEntity) theCreature).forceEntityJump();
            }
        }

        if ((this.theCreature.getTarget() != null && ((this.theCreature.getTarget().getY() < (this.wantedY - 0.5D)) && this.theCreature
                .distanceTo(this.theCreature.getTarget()) < 10F))) {
            if (this.theCreature.getDeltaMovement().y < -0.1) {
                this.theCreature.getDeltaMovement().subtract(0, -0.1, 0);
            }
            return;
        }
    }
}
