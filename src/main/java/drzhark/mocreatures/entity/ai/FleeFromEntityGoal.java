package drzhark.mocreatures.entity.ai;

import com.google.common.base.Predicate;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;
import java.util.List;

public class FleeFromEntityGoal extends Goal {

    public final Predicate<Entity> canBeSeenSelector = new Predicate<Entity>() {

        public boolean isApplicable(Entity entityIn) {
            return entityIn.isAlive() && FleeFromEntityGoal.this.entity.getSensing().canSee(entityIn);
        }

        @Override
        public boolean apply(Entity p_apply_1_) {
            return this.isApplicable(p_apply_1_);
        }
    };
    /** The entity we are attached to */
    protected CreatureEntity entity;
    private double farSpeed;
    private double nearSpeed;
    protected Entity closestLivingEntity;
    private float avoidDistance;
    private Predicate<Entity> avoidTargetSelector;

    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public FleeFromEntityGoal(CreatureEntity creature, Predicate<Entity> targetSelector, float searchDistance, double farSpeedIn, double nearSpeedIn) {
        this.entity = creature;
        this.avoidTargetSelector = targetSelector;
        this.avoidDistance = searchDistance;
        this.farSpeed = farSpeedIn;
        this.nearSpeed = nearSpeedIn;
        this.setFlags(EnumSet.of(Flag.MOVE));
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @SuppressWarnings("unchecked")
    @Override
    public boolean canUse() {
        if (this.entity instanceof IMoCEntity && ((IMoCEntity) this.entity).isNotScared()) {
            return false;
        }

        if (this.entity instanceof MoCEntityAquatic && !this.entity.isInWater()) {
            return false;
        }

        List<Entity> list =
                this.entity.level.getEntities(this.entity,
                        this.entity.getBoundingBox().expandTowards(this.avoidDistance, 3.0D, this.avoidDistance),
                        this.canBeSeenSelector);

        if (list.isEmpty()) {
            return false;
        } else {
            this.closestLivingEntity = list.get(0);
            Vector3d vec3 =
                    RandomPositionGenerator.getPosAvoid(this.entity, 16, 7, new Vector3d(this.closestLivingEntity.getX(),
                            this.closestLivingEntity.getY(), this.closestLivingEntity.getZ()));

            if (vec3 == null) {
                return false;
            } else if (this.closestLivingEntity.distanceToSqr(vec3.x, vec3.y, vec3.z) < this.closestLivingEntity
                    .distanceToSqr(this.entity)) {
                return false;
            } else {
                this.randPosX = vec3.x;
                this.randPosY = vec3.y;
                this.randPosZ = vec3.z;
                return true;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.entity.getNavigation().moveTo(this.randPosX, this.randPosY, this.randPosZ, this.nearSpeed);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    @Override
    public boolean canContinueToUse() {
        return !this.entity.getNavigation().isDone();
    }

    /**
     * Resets the task
     */
    @Override
    public void stop() {
        this.closestLivingEntity = null;
    }

    /**
     * Updates the task
     */
    @Override
    public void tick() {
        if (this.entity.distanceToSqr(this.closestLivingEntity) < 8.0D) {
            this.entity.getNavigation().setSpeedModifier(this.nearSpeed);
        } else {
            this.entity.getNavigation().setSpeedModifier(this.farSpeed);
        }
    }
}
