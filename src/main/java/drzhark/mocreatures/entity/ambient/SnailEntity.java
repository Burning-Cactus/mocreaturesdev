package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class SnailEntity extends MoCEntityAmbient {

    private static final DataParameter<Boolean> IS_HIDDING = EntityDataManager.<Boolean>defineId(SnailEntity.class, DataSerializers.BOOLEAN);
    
    public SnailEntity(EntityType<? extends SnailEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MoCAlternateWanderGoal(this, 0.8D));
    }
    
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_HIDDING, Boolean.valueOf(false));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityAmbient.registerAttributes()
                .add(Attributes.MAX_HEALTH, 5.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.1D);
    }

    @Override
    public boolean isMovementCeased() {
        return (getIsHiding());
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(this.random.nextInt(6) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("snaila.png");
            case 2:
                return MoCreatures.getTexture("snailb.png");
            case 3:
                return MoCreatures.getTexture("snailc.png");
            case 4:
                return MoCreatures.getTexture("snaild.png");
            case 5:
                return MoCreatures.getTexture("snaile.png");
            case 6:
                return MoCreatures.getTexture("snailf.png");
            default:
                return MoCreatures.getTexture("snaila.png");
        }
    }

    public boolean getIsHiding() {
        return ((Boolean)this.entityData.get(IS_HIDDING)).booleanValue();
    }

    public void setIsHiding(boolean flag) {
        this.entityData.set(IS_HIDDING, Boolean.valueOf(flag));
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
            LivingEntity entityliving = getBoogey(3D);
            if ((entityliving != null) && entityliving.getBbHeight() > 0.5F && entityliving.getBbWidth() > 0.5F && canSee(entityliving)) {
                if (!getIsHiding()) {
                    setIsHiding(true);
                }
                this.getNavigation().stop();
            } else {
                setIsHiding(false);
            }
            /**
             * snails without a shell won't hide
             */
            if (getIsHiding() && this.getSubType() > 4) {
                setIsHiding(false);
            }
        }
    }

//    @Override
//    public void fall(float f, float f1) {
//    }

    @Override
    public void tick() {
        super.tick();

        if (getIsHiding()) {
            this.yBodyRotO = this.yBodyRot = this.yRot = this.yRotO;
        }
    }

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
