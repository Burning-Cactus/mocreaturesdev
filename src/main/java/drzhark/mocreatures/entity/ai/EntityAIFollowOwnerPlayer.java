package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.command.arguments.EntityAnchorArgument;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.EnumSet;
import java.util.UUID;

import net.minecraft.entity.ai.goal.Goal.Flag;

public class EntityAIFollowOwnerPlayer extends Goal {

    private LivingEntity thePet;
    private PlayerEntity theOwner;
    World world;
    private double speed;
    private PathNavigator petPathfinder;
    private int delayCounter;
    float maxDist;
    float minDist;

    public EntityAIFollowOwnerPlayer(LivingEntity thePetIn, double speedIn, float minDistIn, float maxDistIn) {
        this.thePet = thePetIn;
        this.world = thePetIn.level;
        this.speed = speedIn;
        this.petPathfinder = ((MobEntity)thePetIn).getNavigation();
        this.minDist = minDistIn;
        this.maxDist = maxDistIn;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));

        //if (!(thePetIn.getNavigator() instanceof PathNavigateGround)) {
        //System.out.println("exiting due to first illegal argument");
        //    throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        //}
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canUse() {
        if (((IMoCEntity) this.thePet).getIsSitting()) {
            return false;
        }

        UUID ownerUniqueId = ((IMoCTameable) this.thePet).getOwnerId();
        if (ownerUniqueId == null) {
            return false;
        }

        PlayerEntity entityplayer = EntityAITools.getIMoCTameableOwner((IMoCTameable) this.thePet);

        if (entityplayer == null) {
            return false;
        }

        else if (this.thePet.distanceToSqr(entityplayer) < this.minDist * this.minDist
                || this.thePet.distanceToSqr(entityplayer) > this.maxDist * this.maxDist) {
            return false;
        } else {
            this.theOwner = entityplayer;
            return true;
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return !this.petPathfinder.isDone() && this.thePet.distanceToSqr(this.theOwner) > this.maxDist * this.maxDist
                && !((IMoCEntity) this.thePet).getIsSitting();
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.delayCounter = 0;
        //this.flag = ((PathNavigateGround) this.thePet.getNavigator()).getAvoidsWater();
        //((PathNavigateGround) this.thePet.getNavigator()).setAvoidsWater(false);
    }

    /**
     * Resets the task
     */
    @Override
    public void stop() {
        this.theOwner = null;
        this.petPathfinder.stop();
        //((PathNavigateGround) this.thePet.getNavigator()).setAvoidsWater(true); //TODO
    }

    private boolean isEmptyBlock(BlockPos pos)
    {
        BlockState iblockstate = this.world.getBlockState(pos);
        return iblockstate.getMaterial() == Material.AIR || !iblockstate.isRedstoneConductor(world, pos);
    }

    @Override
    public void tick()
    {
//        if (!((IMoCEntity) this.thePet).getIsSitting()) {
//            if (--this.delayCounter <= 0)
//            {
//                this.delayCounter = 10;
//
//                if (!this.petPathfinder.tryMoveToEntityLiving(this.theOwner, this.speed))
//                {
//                    if (!this.thePet.isLeashed())
//                    {
//                        if (this.thePet.getDistanceSq(this.theOwner) >= 144.0D)
//                        {
//                            int i = MathHelper.floor(this.theOwner.getPosX()) - 2;
//                            int j = MathHelper.floor(this.theOwner.getPosZ()) - 2;
//                            int k = MathHelper.floor(this.theOwner.getBoundingBox().minY);
//
//                            for (int l = 0; l <= 4; ++l)
//                            {
//                                for (int i1 = 0; i1 <= 4; ++i1)
//                                {
//                                    final BlockPos pos = new BlockPos(i + l, k - 1, j + i1);
//                                    if ((l < 1 || i1 < 1 || l > 3 || i1 > 3) && this.world.getBlockState(pos).isSolidSide(world, pos, Direction.DOWN) && this.isEmptyBlock(new BlockPos(i + l, k, j + i1)) && this.isEmptyBlock(new BlockPos(i + l, k + 1, j + i1)))
//                                    {
//                                        this.thePet.setLocationAndAngles((double)((float)(i + l) + 0.5F), (double)k, (double)((float)(j + i1) + 0.5F), this.thePet.rotationYaw, this.thePet.rotationPitch);
//                                        this.petPathfinder.clearPath();
//                                        return;
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }
}
