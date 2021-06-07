package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;

public class MoCWanderGoal extends RandomWalkingGoal { //EntityAIWander

    private CreatureEntity entityCreature;

    public MoCWanderGoal(CreatureEntity creature, double speedIn) {
        super(creature, speedIn);
        this.entityCreature = creature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canUse() {
        if (this.entityCreature instanceof IMoCEntity && ((IMoCEntity) this.entityCreature).isMovementCeased()) {
            return false;
        }
        return super.canUse();
    }

}
