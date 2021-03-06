package drzhark.mocreatures.entity.aquatic;

import com.google.common.base.Predicate;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.ai.FleeFromEntityGoal;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import drzhark.mocreatures.entity.ai.MoCPanicGoal;
import drzhark.mocreatures.registry.MoCEntities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCEntitySmallFish extends MoCEntityTameableAquatic {

    public static final String fishNames[] = {"Anchovy", "Angelfish", "Angler", "Clownfish", "Goldfish", "Hippotang", "Manderin"};

    public MoCEntitySmallFish(EntityType<? extends MoCEntitySmallFish> type, World world) {
        super(type, world);
        setEdad(70 + this.random.nextInt(30));
    }

    public static MoCEntitySmallFish createEntity(World world, int type) {
        if (type == 1) {
            return new MoCEntityAnchovy(MoCEntities.ANCHOVY, world);
        }
        if (type == 2) {
            return new MoCEntityAngelFish(MoCEntities.ANGELFISH, world);
        }
        if (type == 3) {
            return new MoCEntityAngler(MoCEntities.ANGLER, world);
        }
        if (type == 4) {
            return new MoCEntityClownFish(MoCEntities.CLOWNFISH, world);
        }
        if (type == 5) {
            return new MoCEntityGoldFish(MoCEntities.GOLDFISH, world);
        }
        if (type == 6) {
            return new MoCEntityHippoTang(MoCEntities.HIPPOTANG, world);
        }
        if (type == 7) {
            return new MoCEntityManderin(MoCEntities.MANDERIN, world);
        }

        return new MoCEntityClownFish(MoCEntities.CLOWNFISH, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MoCPanicGoal(this, 1.3D));
        this.goalSelector.addGoal(2, new FleeFromEntityGoal(this, new Predicate<Entity>() {

            public boolean apply(Entity entity) {
                return (entity.getBbHeight() > 0.3F || entity.getBbWidth() > 0.3F);
            }
        }, 2.0F, 0.6D, 1.5D));
        this.goalSelector.addGoal(5, new MoCAlternateWanderGoal(this, 1.0D, 80));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAquatic.registerAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(this.random.nextInt(fishNames.length) + 1);
        }

    }

    @Override
    public ResourceLocation getTexture() {

        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("smallfish_anchovy.png");
            case 2:
                return MoCreatures.getTexture("smallfish_angelfish.png");
            case 3:
                return MoCreatures.getTexture("smallfish_angler.png");
            case 4:
                return MoCreatures.getTexture("smallfish_clownfish.png");
            case 5:
                return MoCreatures.getTexture("smallfish_goldfish.png");
            case 6:
                return MoCreatures.getTexture("smallfish_hippotang.png");
            case 7:
                return MoCreatures.getTexture("smallfish_manderin.png");
            default:
                return MoCreatures.getTexture("smallfish_clownfish.png");
        }
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return true;
    }

//    @Override //TODO: Small fish loot table
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
            return 0.5F;
        }
        return 0.3F;
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
        if (!this.isInWater()) {
            return -90F;
        }
        return 0F;
    }

    @Override
    public int nameYOffset() {
        return -25;
    }

    @Override
    public float getAdjustedXOffset() {
        return 0F;
    }

    @Override
    protected boolean usesNewAI() {
        return true;
    }

    @Override
    public float getSpeed() {
        return 0.10F;
    }

    @Override
    public boolean isMovementCeased() {
        return !isInWater();
    }

    @Override
    protected double minDivingDepth() {
        return 0.2D;
    }

    @Override
    protected double maxDivingDepth() {
        return 2.0D;
    }

    @Override
    public int getMaxEdad() {
        return 120;
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }
    
    @Override
    public float getAdjustedZOffset() {
        if (!isInWater()) {
            return 0.1F;
        }
        return 0F;
    }
    
    protected int getEggNumber() {
        return 80;
    }
}
