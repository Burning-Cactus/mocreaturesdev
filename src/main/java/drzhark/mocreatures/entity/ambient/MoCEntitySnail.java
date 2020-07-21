package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class MoCEntitySnail extends MoCEntityAmbient {

    private static final DataParameter<Boolean> IS_HIDDING = EntityDataManager.<Boolean>createKey(MoCEntitySnail.class, DataSerializers.BOOLEAN);
    
    public MoCEntitySnail(EntityType<? extends MoCEntitySnail> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EntityAIWanderMoC2(this, 0.8D));
    }
    
    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(IS_HIDDING, Boolean.valueOf(false));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.10D);
    }

    @Override
    public boolean isMovementCeased() {
        return (getIsHiding());
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(this.rand.nextInt(6) + 1);
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
        return ((Boolean)this.dataManager.get(IS_HIDDING)).booleanValue();
    }

    public void setIsHiding(boolean flag) {
        this.dataManager.set(IS_HIDDING, Boolean.valueOf(flag));
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {
            LivingEntity entityliving = getBoogey(3D);
            if ((entityliving != null) && entityliving.getHeight() > 0.5F && entityliving.getWidth() > 0.5F && canEntityBeSeen(entityliving)) {
                if (!getIsHiding()) {
                    setIsHiding(true);
                }
                this.getNavigator().clearPath();
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
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
        }
    }

    @Override
    public boolean isOnLadder() {
        return this.collidedHorizontally;
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    public void jump() {
    }
}
