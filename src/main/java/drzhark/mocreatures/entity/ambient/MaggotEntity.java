package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.World;

public class MaggotEntity extends MoCEntityAmbient {

    public MaggotEntity(EntityType<? extends MaggotEntity> type, World world) {
        super(type, world);
        this.texture = "maggot.png";
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MoCAlternateWanderGoal(this, 0.8D));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityAmbient.registerAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D);
    }

//    @Override
//    public void fall(float f, float f1) {
//    }

    @Override
    public boolean onClimbable() {
        return this.horizontalCollision;
    }

    public boolean climbing() {
        return !this.onGround && onClimbable();
    }

    @Override
    public void jumpFromGround() {
    }
}
