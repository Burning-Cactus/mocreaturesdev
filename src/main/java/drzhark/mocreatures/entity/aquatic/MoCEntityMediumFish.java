package drzhark.mocreatures.entity.aquatic;

import com.google.common.base.Predicate;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.ai.FleeFromEntityGoal;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import drzhark.mocreatures.registry.MoCEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCEntityMediumFish extends MoCEntityTameableAquatic {

    public static final String fishNames[] = {"Salmon", "Cod", "Bass"};

    public MoCEntityMediumFish(EntityType<? extends MoCEntityMediumFish> type, World world) {
        super(type, world);
        setEdad(30 + this.random.nextInt(70));
    }

    public static MoCEntityMediumFish createEntity(World world, int type) {
        if (type == 1) {
            return new MoCEntitySalmon(MoCEntities.SALMON, world);
        }
        if (type == 2) {
            return new MoCEntityCod(MoCEntities.COD, world);
        }
        if (type == 3) {
            return new MoCEntityBass(MoCEntities.BASS, world);
        }

        return new MoCEntitySalmon(MoCEntities.SALMON, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new FleeFromEntityGoal(this, new Predicate<Entity>() {

            public boolean apply(Entity entity) {
                return (entity.getBbHeight() > 0.6F && entity.getBbWidth() > 0.3F);
            }
        }, 2.0F, 0.6D, 1.5D));
        this.goalSelector.addGoal(5, new MoCAlternateWanderGoal(this, 1.0D, 50));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAquatic.registerAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(this.random.nextInt(fishNames.length) + 1);
        }
    }

//    @Override //TODO: Mediumfish loot table
//    protected void dropFewItems(boolean flag, int x) {
//        int i = this.rand.nextInt(100);
//        if (i < 70) {
//            entityDropItem(new ItemStack(Items.COD, 1, 0), 0.0F);
//        } else {
//            int j = this.rand.nextInt(2);
//            for (int k = 0; k < j; k++) {
//                entityDropItem(new ItemStack(MoCItems.MOCEGG, 1, getEggNumber()), 0.0F);
//            }
//        }
//    }

    protected int getEggNumber() {
        return 70;
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
            if (getIsTamed() && this.random.nextInt(100) == 0 && getHealth() < getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
        }
        if (!this.isInWater()) {
            this.yBodyRotO = this.yBodyRot = this.yRot = this.yRotO;
            this.xRot = this.xRotO;
        }
    }

    @Override
    public float getSizeFactor() {
        return getEdad() * 0.01F;
    }

    @Override
    public float getAdjustedYOffset() {
        if (!this.isInWater()) {
            return 1F;
        }
        return 0.5F;
    }

    @Override
    protected boolean isFisheable() {
        return !getIsTamed();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float yawRotationOffset() {
        if (!this.isInWater()) {
            return 90F;
        }
        return 90F + super.yawRotationOffset();
    }

    @Override
    public float rollRotationOffset() {
        if (!isInWater() && this.onGround) {
            return -90F;
        }
        return 0F;
    }

    @Override
    public int nameYOffset() {
        return -30;
    }

    @Override
    public float getAdjustedZOffset() {
        if (!isInWater()) {
            return 0.2F;
        }
        return 0F;
    }

    @Override
    public float getAdjustedXOffset() {
        return 0F;
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getSpeed() {
        return 0.15F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double minDivingDepth() {
        return 0.5D;
    }

    @Override
    protected double maxDivingDepth() {
        return 4.0D;
    }

    @Override
    public int getMaxEdad() {
        return 120;
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }
}
