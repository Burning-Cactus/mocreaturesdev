package drzhark.mocreatures.entity.ai;

import com.google.common.base.Predicate;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;

public class EntityAIHunt extends EntityAINearestAttackableTargetMoC {

    private CreatureEntity hunter;

    public EntityAIHunt(CreatureEntity entity, Class<? extends CreatureEntity> classTarget, int chance, boolean checkSight, boolean onlyNearby, Predicate<LivingEntity> predicate) {
        super(entity, classTarget, chance, checkSight, onlyNearby, predicate);
        this.hunter = entity;
    }

    public EntityAIHunt(CreatureEntity entityCreature, Class<? extends CreatureEntity> classTarget, boolean checkSight) {
        this(entityCreature, classTarget, checkSight, false);
    }

    public EntityAIHunt(CreatureEntity entity, Class<? extends CreatureEntity> classTarget, boolean checkSight, boolean onlyNearby) {
        this(entity, classTarget, 10, checkSight, onlyNearby, null);

    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        return ((MoCEntityAnimal) this.hunter).getIsHunting() && super.shouldExecute();
    }
}
