package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAquatic;
import drzhark.mocreatures.entity.ai.MoCPanicGoal;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import drzhark.mocreatures.registry.MoCEntities;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityDolphin extends MoCEntityTameableAquatic {

    public int gestationtime;
    private static final DataParameter<Boolean> IS_HUNGRY = EntityDataManager.<Boolean>defineId(MoCEntityDolphin.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_EATEN = EntityDataManager.<Boolean>defineId(MoCEntityDolphin.class, DataSerializers.BOOLEAN);
    
    public MoCEntityDolphin(EntityType<? extends MoCEntityDolphin> type, World world) {
        super(type, world);
        setEdad(60 + this.random.nextInt(100));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new MoCPanicGoal(this, 1.3D));
        this.goalSelector.addGoal(5, new MoCAlternateWanderGoal(this, 1.0D, 30));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAquatic.registerAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            int i = this.random.nextInt(100);
            if (i <= 35) {
                setType(1);
            } else if (i <= 60) {
                setType(2);
            } else if (i <= 85) {
                setType(3);
            } else if (i <= 96) {
                setType(4);
            } else if (i <= 98) {
                setType(5);
            } else {
                setType(6);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {

        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("dolphin.png");
            case 2:
                return MoCreatures.getTexture("dolphin2.png");
            case 3:
                return MoCreatures.getTexture("dolphin3.png");
            case 4:
                return MoCreatures.getTexture("dolphin4.png");
            case 5:
                return MoCreatures.getTexture("dolphin5.png");
            case 6:
                return MoCreatures.getTexture("dolphin6.png");
            default:
                return MoCreatures.getTexture("dolphin.png");
        }
    }

    @Override
    public int getMaxTemper() {

        switch (getSubType()) {
            case 1:
                return 50;
            case 2:
                return 100;
            case 3:
                return 150;
            case 4:
                return 200;
            case 5:
                return 250;
            case 6:
                return 300;
            default:
                return 100;
        }
    }

    public int getInitialTemper() {
        switch (getSubType()) {
            case 1:
                return 50;
            case 2:
                return 100;
            case 3:
                return 150;
            case 4:
                return 200;
            case 5:
                return 250;
            case 6:
                return 300;
            default:
                return 50;
        }
    }

    @Override
    public double getCustomSpeed() {
        switch (getSubType()) {
            case 1:
                return 1.5D;
            case 2:
                return 2.0D;
            case 3:
                return 2.5D;
            case 4:
                return 3.D;
            case 5:
                return 3.5D;
            case 6:
                return 4.D;
            default:
                return 1.5D;
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_HUNGRY, Boolean.FALSE);
        this.entityData.define(HAS_EATEN, Boolean.FALSE);
    }

    public boolean getIsHungry() {
        return this.entityData.get(IS_HUNGRY);
    }

    public boolean getHasEaten() {
        return this.entityData.get(HAS_EATEN);
    }

    public void setIsHungry(boolean flag) {
        this.entityData.set(IS_HUNGRY, flag);
    }

    public void setHasEaten(boolean flag) {
        this.entityData.set(HAS_EATEN, flag);
    }

    //TODO
    /*@Override
    protected void attackEntity(Entity entity, float f) {
        if (attackTime <= 0 && (f < 3.5D) && (entity.getEntityBoundingBox().maxY > getEntityBoundingBox().minY)
                && (entity.getEntityBoundingBox().minY < getEntityBoundingBox().maxY) && (getEdad() >= 100)) {
            attackTime = 20;
            entity.attackEntityFrom(DamageSource.causeMobDamage(this), 5);
        }
    }*/

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (super.hurt(damagesource, i) && (this.level.getDifficulty().getId() > 0)) {
            Entity entity = damagesource.getEntity();
            if (entity instanceof LivingEntity) {
                LivingEntity entityliving = (LivingEntity) entity;
                if (this.hasIndirectPassenger(entity)) {
                    return true;
                }
                if (entity != this && this.getEdad() >= 100) {
                    setTarget(entityliving);
                }
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean isPickable() {
        return !this.isVehicle();
    }

    private int Genetics(MoCEntityDolphin entitydolphin, MoCEntityDolphin entitydolphin1) {
        if (entitydolphin.getSubType() == entitydolphin1.getSubType()) {
            return entitydolphin.getSubType();
        }
        int i = entitydolphin.getSubType() + entitydolphin1.getSubType();
        boolean flag = this.random.nextInt(3) == 0;
        boolean flag1 = this.random.nextInt(10) == 0;
        if ((i < 5) && flag) {
            return i;
        }
        if (((i == 5) || (i == 6)) && flag1) {
            return i;
        } else {
            return 0;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_DOLPHIN_DEATH;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_DOLPHIN_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_DOLPHIN_AMBIENT;
    }

//    @Override
//    protected SoundEvent getUpsetSound() {
//        return MoCSoundEvents.ENTITY_DOLPHIN_UPSET;
//    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && (stack.getItem() == Items.COD)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            if (!this.world.isRemote) {
                setTemper(getTemper() + 25);
                if (getTemper() > getMaxTemper()) {
                    setTemper(getMaxTemper() - 1);
                }

                if ((getHealth() + 15) > getMaxHealth()) {
                    this.setHealth(getMaxHealth());
                }

                if (!getIsAdult()) {
                    setEdad(getEdad() + 1);
                }
            }

            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);

            return true;
        }
        if (!stack.isEmpty() && (stack.getItem() == Items.COOKED_COD) && getIsTamed() && getIsAdult()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            if ((getHealth() + 25) > getMaxHealth()) {
                this.setHealth(getMaxHealth());
            }
            setHasEaten(true);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            return true;
        }
        if (!this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                player.setPosition(player.getPosX(), this.getPosY(), player.getPosZ());
            }

            return true;
        }

        return super.processInteract(player, hand);
    }*/

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {

            //TODO
            /*if (!getIsHungry() && (this.rand.nextInt(100) == 0)) {
                setIsHungry(true);
            }*/
            // fixes growth
            if (!getIsAdult() && (random.nextInt(50) == 0))
            {
                setEdad(getEdad() + 1);
                if (getEdad() >= 150)
                {
                    setAdult(true);
                }
            }
            //TODO
            if ((!this.isVehicle()) && (this.deathTime == 0) && (!getIsTamed() || getIsHungry())) {
                ItemEntity entityitem = getClosestFish(this, 12D);
                if (entityitem != null) {
                    MoveToNextEntity(entityitem);
                    ItemEntity entityitem1 = getClosestFish(this, 2D);
                    if ((this.random.nextInt(20) == 0) && (entityitem1 != null) && (this.deathTime == 0)) {

                        entityitem1.remove();
                        setTemper(getTemper() + 25);
                        if (getTemper() > getMaxTemper()) {
                            setTemper(getMaxTemper() - 1);
                        }
                        this.setHealth(getMaxHealth());
                    }
                }
            }
            if (!ReadyforParenting(this)) {
                return;
            }
            int i = 0;
            List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(8D, 2D, 8D));
            for (int j = 0; j < list.size(); j++) {
                Entity entity = list.get(j);
                if (entity instanceof MoCEntityDolphin) {
                    i++;
                }
            }

            if (i > 1) {
                return;
            }
            List<Entity> list1 = this.level.getEntities(this, getBoundingBox().expandTowards(4D, 2D, 4D));
            for (int k = 0; k < list1.size(); k++) {
                Entity entity1 = list1.get(k);
                if (!(entity1 instanceof MoCEntityDolphin) || (entity1 == this)) {
                    continue;
                }
                MoCEntityDolphin entitydolphin = (MoCEntityDolphin) entity1;
                if (!ReadyforParenting(this) || !ReadyforParenting(entitydolphin)) {
                    continue;
                }
                if (this.random.nextInt(100) == 0) {
                    this.gestationtime++;
                }
                if (this.gestationtime % 3 == 0) {
//                    MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHeart(this.getEntityId()),
//                            new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
                }
                if (this.gestationtime <= 50) {
                    continue;
                }
                MoCEntityDolphin babydolphin = new MoCEntityDolphin(MoCEntities.DOLPHIN, this.level);
                babydolphin.setPos(this.getX(), this.getY(), this.getZ());
                if (this.level.addFreshEntity(babydolphin)) {
                    MoCTools.playCustomSound(this, SoundEvents.CHICKEN_EGG);
                    setHasEaten(false);
                    entitydolphin.setHasEaten(false);
                    this.gestationtime = 0;
                    entitydolphin.gestationtime = 0;
                    int l = Genetics(this, entitydolphin);
                    babydolphin.setEdad(35);
                    babydolphin.setAdult(false);
                    babydolphin.setOwnerId(this.getOwnerId());
                    babydolphin.setTamed(true);
                    PlayerEntity entityplayer = this.level.getPlayerByUUID(this.getOwnerId());
                    if (entityplayer != null) {
                        MoCTools.tameWithName(entityplayer, babydolphin);
                    }
                    babydolphin.setTypeInt(l);
                    break;
                }
            }
        }
    }

    public boolean ReadyforParenting(MoCEntityDolphin entitydolphin) {
        LivingEntity passenger = (LivingEntity)this.getControllingPassenger();
        return (entitydolphin.getVehicle() == null) && (passenger == null) && entitydolphin.getIsTamed()
                && entitydolphin.getHasEaten() && entitydolphin.getIsAdult();
    }

    @Override
    public void remove() {
        if (!(!this.level.isClientSide && getIsTamed() && (getHealth() > 0)))
            super.remove();
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 1;
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
        return 0.4D;
    }

    @Override
    protected double maxDivingDepth() {
        return 4.0D;
    }

    @Override
    public int getMaxEdad() {
        return 160;
    }

    @Override
    public void positionRider(Entity passenger) {
        double dist = (0.8D);
        double newPosX = this.getX() + (dist * Math.sin(this.yBodyRot / 57.29578F));
        double newPosZ = this.getZ() - (dist * Math.cos(this.yBodyRot / 57.29578F));
        passenger.setPos(newPosX, this.getY() + getPassengersRidingOffset() + passenger.getMyRidingOffset(), newPosZ);
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getEdad() * 0.01F * (this.getBbHeight() * 0.3D);
    }
}
