package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.MathHelper;

public class EntityAIMoverHelperMoC extends MovementController {

    CreatureEntity theCreature;
    protected MovementController.Action action = MovementController.Action.WAIT;

    public boolean isUpdating()
    {
        return this.action == MovementController.Action.MOVE_TO;
    }

    public double getSpeed()
    {
        return this.speed;
    }

    /**
     * Sets the speed and location to move to
     */
    public void setMoveTo(double x, double y, double z, double speedIn)
    {
        this.posX = x;
        this.posY = y;
        this.posZ = z;
        this.speed = speedIn;
        this.action = MovementController.Action.MOVE_TO;
    }

    public void strafe(float forward, float strafe)
    {
        this.action = MovementController.Action.STRAFE;
        this.moveForward = forward;
        this.moveStrafe = strafe;
        this.speed = 0.25D;
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
            float f = (float)this.mob.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue();
            float f1 = (float)this.speed * f;
            float f2 = this.moveForward;
            float f3 = this.moveStrafe;
            float f4 = MathHelper.sqrt(f2 * f2 + f3 * f3);

            if (f4 < 1.0F)
            {
                f4 = 1.0F;
            }

            f4 = f1 / f4;
            f2 = f2 * f4;
            f3 = f3 * f4;
            float f5 = MathHelper.sin(this.mob.rotationYaw * 0.017453292F);
            float f6 = MathHelper.cos(this.mob.rotationYaw * 0.017453292F);
            float f7 = f2 * f6 - f3 * f5;
            float f8 = f3 * f6 + f2 * f5;
            PathNavigator pathnavigate = this.mob.getNavigator();

            if (pathnavigate != null)
            {
                NodeProcessor nodeprocessor = pathnavigate.getNodeProcessor();

                if (nodeprocessor != null && nodeprocessor.getPathNodeType(this.mob.world, MathHelper.floor(this.mob.getPosX() + (double)f7), MathHelper.floor(this.mob.getPosY()), MathHelper.floor(this.mob.getPosZ() + (double)f8)) != PathNodeType.WALKABLE)
                {
                    this.moveForward = 1.0F;
                    this.moveStrafe = 0.0F;
                    f1 = f;
                }
            }

            this.mob.setAIMoveSpeed(f1);
            this.mob.setMoveForward(this.moveForward);
            this.mob.setMoveStrafing(this.moveStrafe);
            this.action = MovementController.Action.WAIT;
        }
        else if (this.action == MovementController.Action.MOVE_TO)
        {
            this.action = MovementController.Action.WAIT;
            double d0 = this.posX - this.mob.getPosX();
            double d1 = this.posZ - this.mob.getPosZ();
            double d2 = this.posY - this.mob.getPosY();
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;

            if (d3 < 2.500000277905201E-7D)
            {
                this.mob.setMoveForward(0.0F);
                return;
            }

            float f9 = (float)(MathHelper.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
            this.mob.rotationYaw = this.limitAngle(this.mob.rotationYaw, f9, 20.0F);
            this.mob.setAIMoveSpeed((float)(this.speed * this.mob.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue()));

            if (d2 > (double)this.mob.stepHeight && d0 * d0 + d1 * d1 < (double)Math.max(1.0F, this.mob.getWidth()))
            {
                this.mob.getJumpController().setJumping();
            }
        }
        else
        {
            this.mob.setMoveForward(0.0F);
        }
    }

    /**
     * Limits the given angle to a upper and lower limit.
     */
    protected float limitAngle(float p_75639_1_, float p_75639_2_, float p_75639_3_)
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

    public double getX()
    {
        return this.posX;
    }

    public double getY()
    {
        return this.posY;
    }

    public double getZ()
    {
        return this.posZ;
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
        if (isFlyer && !theCreature.isBeingRidden()) {
            this.flyingMovementUpdate();
        }

        /*
         * Water movement code
         */
        if (isSwimmer) {
            this.swimmerMovementUpdate();
            fLimitAngle = 30F;
        }
        if (this.action == MovementController.Action.MOVE_TO && !this.theCreature.getNavigator().noPath()) {
            double d0 = this.posX - this.theCreature.getPosX();
            double d1 = this.posY - this.theCreature.getPosY();
            double d2 = this.posZ - this.theCreature.getPosZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            d3 = MathHelper.sqrt(d3);
            if (d3 < 0.5) {
                this.mob.setMoveForward(0.0F);
                this.theCreature.getNavigator().clearPath();
                return;
            }
            //System.out.println("distance to objective = " + d3 + "objective: X = " + this.posX + ", Y = " + this.posY + ", Z = " + this.posZ);
            d1 /= d3;
            float f = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            this.theCreature.rotationYaw = this.limitAngle(this.theCreature.rotationYaw, f, fLimitAngle);
            this.theCreature.renderYawOffset = this.theCreature.rotationYaw;
            float f1 = (float) (this.speed * this.theCreature.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
            this.theCreature.setAIMoveSpeed(this.theCreature.getAIMoveSpeed() + (f1 - this.theCreature.getAIMoveSpeed()) * 0.125F);
            double d4 = Math.sin((double) (this.theCreature.ticksExisted + this.theCreature.getEntityId()) * 0.75D) * 0.01D;
            double d5 = Math.cos((double) (this.theCreature.rotationYaw * (float) Math.PI / 180.0F));
            double d6 = Math.sin((double) (this.theCreature.rotationYaw * (float) Math.PI / 180.0F));
            this.theCreature.getMotion().add(d4 * d5,
                    (d4 * (d6 + d5) * 0.25D) + ((double)this.theCreature.getAIMoveSpeed() * d1 * 1.5D),
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
                    && (this.theCreature.collidedHorizontally || this.theCreature.world.rand.nextInt(100) == 0)) {
                this.theCreature.getMotion().add(0, 0.02D, 0);
            }
            if (distY > ((IMoCEntity) theCreature).maxFlyingHeight() || this.theCreature.world.rand.nextInt(150) == 0) {
                this.theCreature.getMotion().subtract(0, 0.02D, 0);
            }

        } else {
            if (this.theCreature.getMotion().y < 0) {
                this.theCreature.getMotion().mul(1, 0.6D, 1);
            }
        }

    }

    /**
     * Makes creatures in the water float to the right depth
     */
    private void swimmerMovementUpdate() {
        if (theCreature.isBeingRidden()) {
            return;
        }

        double distToSurface = (MoCTools.waterSurfaceAtGivenEntity(theCreature) - theCreature.getPosY());
        if (distToSurface > ((IMoCEntity) theCreature).getDivingDepth()) {
            if (theCreature.getMotion().getY() < 0) {
                theCreature.setMotion(theCreature.getMotion().x, 0, theCreature.getMotion().z);
            }
            theCreature.getMotion().add(0, 0.001D + distToSurface * 0.01, 0);
        }

        if (!theCreature.getNavigator().noPath() && theCreature.collidedHorizontally) {
            if (theCreature instanceof MoCEntityAquatic) {
                theCreature.getMotion().add(0, 0.05D, 0);
            } else {
                ((IMoCEntity) theCreature).forceEntityJump();
            }
        }

        if ((this.theCreature.getAttackTarget() != null && ((this.theCreature.getAttackTarget().getPosY() < (this.posY - 0.5D)) && this.theCreature
                .getDistance(this.theCreature.getAttackTarget()) < 10F))) {
            if (this.theCreature.getMotion().y < -0.1) {
                this.theCreature.getMotion().subtract(0, -0.1, 0);
            }
            return;
        }
    }
}
