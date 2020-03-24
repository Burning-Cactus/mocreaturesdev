package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;

import java.util.Iterator;
import java.util.List;

public class EntityAIFollowAdult extends Goal {

    /** The child that is following its parent. */
    LivingEntity childAnimal;
    LivingEntity parentAnimal;
    double moveSpeed;
    private int delayCounter;

    public EntityAIFollowAdult(LivingEntity animal, double speed) {
        this.childAnimal = animal;
        this.moveSpeed = speed;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if ((!(this.childAnimal instanceof IMoCEntity)) || ((IMoCEntity) this.childAnimal).getIsAdult()) {
            return false;
        } else {
            List<LivingEntity> list =
                    this.childAnimal.world.getEntitiesWithinAABB(this.childAnimal.getClass(),
                            this.childAnimal.getBoundingBox().expand(8.0D, 4.0D, 8.0D));
            LivingEntity entityliving = null;
            double d0 = Double.MAX_VALUE;
            Iterator<LivingEntity> iterator = list.iterator();

            while (iterator.hasNext()) {
                LivingEntity entityliving1 = iterator.next();

                if (((IMoCEntity) entityliving1).getIsAdult()) {
                    double d1 = this.childAnimal.getDistanceSq(entityliving1);

                    if (d1 <= d0) {
                        d0 = d1;
                        entityliving = entityliving1;
                    }
                }
            }

            if (entityliving == null) {
                return false;
            } else if (d0 < 9.0D) {
                return false;
            } else {
                this.parentAnimal = entityliving;
                return true;
            }
        }
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean shouldContinueExecuting() {
        if (((IMoCEntity) this.childAnimal).getIsAdult()) {
            return false;
        } else if (!this.parentAnimal.isAlive()) {
            return false;
        } else {
            double d0 = this.childAnimal.getDistanceSq(this.parentAnimal);
            return d0 >= 9.0D && d0 <= 256.0D;
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void startExecuting() {
        this.delayCounter = 0;
    }

    /**
     * Resets the task
     */
    @Override
    public void resetTask() {
        this.parentAnimal = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        if (--this.delayCounter <= 0) {
            this.delayCounter = 10;
            ((MobEntity)this.childAnimal).getNavigator().tryMoveToEntityLiving(this.parentAnimal, this.moveSpeed);
        }
    }
}
