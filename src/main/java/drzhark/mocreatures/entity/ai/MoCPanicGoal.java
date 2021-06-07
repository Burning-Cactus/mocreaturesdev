package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.goal.PanicGoal;

public class MoCPanicGoal extends PanicGoal {

    private CreatureEntity entityCreature;

    public MoCPanicGoal(CreatureEntity creature, double speedIn) {
        super(creature, speedIn);
        this.entityCreature = creature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean canUse() {
        if (this.entityCreature instanceof IMoCEntity && ((IMoCEntity) this.entityCreature).isNotScared()) {
            return false;
        }
        return super.canUse();
    }

}
