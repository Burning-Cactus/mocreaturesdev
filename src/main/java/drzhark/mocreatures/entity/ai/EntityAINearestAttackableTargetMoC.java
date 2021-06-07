package drzhark.mocreatures.entity.ai;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.EntityPredicates;

import java.util.Collections;
import java.util.Comparator;
import java.util.EnumSet;
import java.util.List;

import net.minecraft.entity.ai.goal.Goal.Flag;

public class EntityAINearestAttackableTargetMoC extends EntityAITargetMoC {

    protected final Class<? extends LivingEntity> targetClass;
    private final int targetChance;
    /** Instance of EntityAINearestAttackableTargetSorter. */
    protected final EntityAINearestAttackableTargetMoC.Sorter theNearestAttackableTargetSorter;
    /**
     * This filter is applied to the Entity search.  Only matching entities will be targetted.  (null -> no
     * restrictions)
     */
    protected Predicate<LivingEntity> targetEntitySelector;
    protected LivingEntity targetEntity;
    private IMoCEntity theAttacker;

    public EntityAINearestAttackableTargetMoC(CreatureEntity creature, Class<? extends LivingEntity> classTarget, boolean checkSight) {
        this(creature, classTarget, checkSight, false);
    }

    public EntityAINearestAttackableTargetMoC(CreatureEntity creature, Class<? extends LivingEntity> classTarget, boolean checkSight, boolean onlyNearby) {
        this(creature, classTarget, 10, checkSight, onlyNearby, null);
    }

    public EntityAINearestAttackableTargetMoC(CreatureEntity creature, Class<? extends LivingEntity> classTarget, int chance, boolean checkSight, boolean onlyNearby,
            final Predicate<LivingEntity> targetSelector) {
        super(creature, checkSight, onlyNearby);
        if (creature instanceof IMoCEntity) {
            this.theAttacker = (IMoCEntity) creature;
        }
        this.targetClass = classTarget;
        this.targetChance = chance;
        this.theNearestAttackableTargetSorter = new EntityAINearestAttackableTargetMoC.Sorter(creature);
        this.setFlags(EnumSet.of(Flag.TARGET));
        this.targetEntitySelector = new Predicate<LivingEntity>() {

            @Override
            public boolean apply(LivingEntity entitylivingbaseIn) {
                if (targetSelector != null && !targetSelector.apply(entitylivingbaseIn)) {
                    return false;
                } else {
                    if (entitylivingbaseIn instanceof PlayerEntity) {
                        double d0 = EntityAINearestAttackableTargetMoC.this.getTargetDistance();

                        if (entitylivingbaseIn.isCrouching()) {
                            d0 *= 0.800000011920929D;
                        }

                        if (entitylivingbaseIn.isInvisible()) {
                            float f = ((PlayerEntity) entitylivingbaseIn).getArmorCoverPercentage();

                            if (f < 0.1F) {
                                f = 0.1F;
                            }

                            d0 *= 0.7F * f;
                        }

                        if (entitylivingbaseIn.distanceTo(EntityAINearestAttackableTargetMoC.this.taskOwner) > d0) {
                            return false;
                        }
                    }

                    return EntityAINearestAttackableTargetMoC.this.isSuitableTarget(entitylivingbaseIn, false);
                }
            }
        };
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canUse() {
        if (this.theAttacker != null && (this.theAttacker.isMovementCeased() || !this.theAttacker.isNotScared())) {
            return false;
        }
        if (this.targetChance > 0 && this.taskOwner.getRandom().nextInt(this.targetChance) != 0) {
            return false;
        } else {
            double d0 = this.getTargetDistance();
            List list =
                    this.taskOwner.level.getEntitiesOfClass(this.targetClass, this.taskOwner.getBoundingBox().expandTowards(d0, 4.0D, d0),
                            Predicates.and(this.targetEntitySelector, (Predicate) EntityPredicates.NO_SPECTATORS));
            Collections.sort(list, this.theNearestAttackableTargetSorter);

            if (list.isEmpty()) {
                return false;
            } else {
                this.targetEntity = (LivingEntity) list.get(0);
                if (this.targetEntity instanceof PlayerEntity && !this.theAttacker.shouldAttackPlayers()) {
                    return false;
                }
                return true;
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    @Override
    public void start() {
        this.taskOwner.setTarget(this.targetEntity);
        super.start();
    }

    public static class Sorter implements Comparator<Entity> {

        private final Entity entity;

        public Sorter(Entity entityIn) {
            this.entity = entityIn;
        }

        public int compare(Entity p_compare_1_, Entity p_compare_2_) {
            double d0 = this.entity.distanceToSqr(p_compare_1_);
            double d1 = this.entity.distanceToSqr(p_compare_2_);
            return d0 < d1 ? -1 : (d0 > d1 ? 1 : 0);
        }
    }

}
