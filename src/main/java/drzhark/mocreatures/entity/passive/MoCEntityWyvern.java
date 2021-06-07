package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.registry.MoCEntities;
import drzhark.mocreatures.registry.MoCItems;
import drzhark.mocreatures.registry.MoCSoundEvents;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class MoCEntityWyvern extends MoCEntityTameableAnimal {

    public MoCAnimalChest localchest;
    public ItemStack localstack;
    public int mouthCounter;
    public int wingFlapCounter;
    public int diveCounter;
    public static final String[] wyvernNames = {"Jungle", "Swamp", "Savanna", "Sand", "Mother", "Undead", "Light", "Dark", "Arctic", "Cave",
            "Mountain", "Sea"};

    protected EntityAIWanderMoC2 wander;
    private int transformType;
    private int transformCounter;
    private int tCounter;
    private float fTransparency;
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.defineId(MoCEntityWyvern.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.defineId(MoCEntityWyvern.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SITTING = EntityDataManager.defineId(MoCEntityWyvern.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GHOST = EntityDataManager.defineId(MoCEntityWyvern.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FLYING = EntityDataManager.defineId(MoCEntityWyvern.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> ARMOR_TYPE = EntityDataManager.defineId(MoCEntityWyvern.class, DataSerializers.INT);


    public MoCEntityWyvern(EntityType<? extends MoCEntityWyvern> type, World world) {
        super(type, world);
        setAdult(false);
        setTamed(false);
        this.maxUpStep = 1.0F;

        if (this.random.nextInt(6) == 0) {
            setEdad(50 + this.random.nextInt(50));
        } else {
            setEdad(80 + this.random.nextInt(20));
        }
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(4, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(2, new EntityAIHunt(this, AnimalEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .add(Attributes.MAX_HEALTH, 40D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RIDEABLE, Boolean.FALSE); // rideable: 0 nothing, 1 saddle
        this.entityData.define(SITTING, Boolean.FALSE); // rideable: 0 nothing, 1 saddle
        this.entityData.define(CHESTED, Boolean.FALSE);
        this.entityData.define(FLYING, Boolean.FALSE);
        this.entityData.define(GHOST, Boolean.FALSE);
        this.entityData.define(ARMOR_TYPE, 0);// armor 0 by default, 1 metal, 2 gold, 3 diamond, 4 crystaline
    }

    public boolean getIsFlying() {
        return this.entityData.get(FLYING);
    }

    public void setIsFlying(boolean flag) {
        this.entityData.set(FLYING, flag);
    }

    @Override
    public int getArmorType() {
        return this.entityData.get(ARMOR_TYPE);
    }

    @Override
    public void setArmorType(int i) {
        this.entityData.set(ARMOR_TYPE, i);
    }

    @Override
    public boolean getIsRideable() {
        return this.entityData.get(RIDEABLE);
    }

    @Override
    public void setRideable(boolean flag) {
        this.entityData.set(RIDEABLE, flag);
    }

    public boolean getIsChested() {
        return this.entityData.get(CHESTED);
    }

    public void setIsChested(boolean flag) {
        this.entityData.set(CHESTED, flag);
    }

    @Override
    public boolean getIsSitting() {
        return this.entityData.get(SITTING);
    }

    public void setSitting(boolean flag) {
        this.entityData.set(SITTING, flag);
    }

    public boolean getIsGhost() {
        return this.entityData.get(GHOST);
    }

    public void setIsGhost(boolean flag) {
        this.entityData.set(GHOST, flag);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            if (random.nextInt(5) == 0) {
                setType(5);
            } else {
                int i = this.random.nextInt(100);
                if (i <= 12) {
                    setType(1);
                } else if (i <= 24) {
                    setType(2);
                } else if (i <= 36) {
                    setType(3);
                } else if (i <= 48) {
                    setType(4);
                } else if (i <= 60) {
                    setType(9);
                } else if (i <= 72) {
                    setType(10);
                } else if (i <= 84) {
                    setType(11);
                } else if (i <= 95) {
                    setType(12);
                } else {
                    setType(5);
                }
            }
        }
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(calculateAttackDmg());
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    public double calculateMaxHealth() {
        if (this.getSubType() == 6 || this.getSubType() == 7 || this.getSubType() == 8) {
            return 60D;
        }
        if (this.getSubType() == 5) {
            return 80D;
        }
        if (this.getSubType() == 13) {
            return 100D;
        }
        return 40D;
    }

    public double calculateAttackDmg() {
        if (this.getSubType() == 6 || this.getSubType() == 7 || this.getSubType() == 8 || this.getSubType() == 13) {
            return 8D;
        }
        if (this.getSubType() == 5) {
            return 12D;
        }
        return 5D;
    }

    /**
    * 1-4 regular wyverns
    * 5 mother wyvern
    * 6 undead
    * 7 light
    * 8 darkness
    * 9-12 extra wyverns
    */
    @Override
    public ResourceLocation getTexture() {
        if (this.transformCounter != 0 && this.transformType > 5) {
            String newText = "wyverndark.png";
            if (this.transformType == 6) {
                newText = "wyvernundead.png";
            }
            if (this.transformType == 7) {
                newText = "wyvernlight.png";
            }
            if (this.transformType == 8) {
                newText = "wyverndark.png";
            }

            if ((this.transformCounter % 5) == 0) {
                return MoCreatures.getTexture(newText);
            }
            if (this.transformCounter > 50 && (this.transformCounter % 3) == 0) {
                return MoCreatures.getTexture(newText);
            }

            if (this.transformCounter > 75 && (this.transformCounter % 4) == 0) {
                return MoCreatures.getTexture(newText);
            }
        }

        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("wyvernjungle.png");
            case 2:
                return MoCreatures.getTexture("wyvernmix.png");
            case 3:
                return MoCreatures.getTexture("wyvernsand.png");
            case 4:
                return MoCreatures.getTexture("wyvernsun.png");
            case 5:
                return MoCreatures.getTexture("wyvernmother.png");
            case 6:
                return MoCreatures.getTexture("wyvernundead.png");
            case 7:
                return MoCreatures.getTexture("wyvernlight.png");
            case 8:
                return MoCreatures.getTexture("wyverndark.png");
            case 9:
                return MoCreatures.getTexture("wyvernarctic.png");
            case 10:
                return MoCreatures.getTexture("wyverncave.png");
            case 11:
                return MoCreatures.getTexture("wyvernmountain.png");
            case 12:
                return MoCreatures.getTexture("wyvernsea.png");
                //case 13:
                //    return MoCreatures.proxy.getTexture("wyvernghost.png");
            default:
                return MoCreatures.getTexture("wyvernsun.png");
        }
    }

    public void transform(int tType) {
//        if (!this.world.isRemote) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), tType),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//        }
        this.transformType = tType;
        this.transformCounter = 1;
    }

    @Override
    public void aiStep() {

        if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
            this.wingFlapCounter = 0;
        }
        if (this.wingFlapCounter == 5 && !this.level.isClientSide) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_WYVERN_WINGFLAP);
        }

        if (this.transformCounter > 0) {
            if (this.transformCounter == 40) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_TRANSFORM);
            }

            if (++this.transformCounter > 100) {
                this.transformCounter = 0;
                if (this.transformType != 0) {
                    setType(this.transformType);
                    selectType();
                }
            }
        }

        if (!this.level.isClientSide) {
            if (!isMovementCeased() && !this.getIsTamed() && this.random.nextInt(300) == 0) {
                setIsFlying(!getIsFlying());
            }
            if (isMovementCeased() && getIsFlying()) {
                setIsFlying(false);
            }

            if (getTarget() != null && (!this.getIsTamed() || this.getVehicle() != null) && !isMovementCeased() && this.random.nextInt(20) == 0) {
                setIsFlying(true);
            }
            /*if (!getIsTamed() && this.dimension.getId() == MoCreatures.WyvernLairDimensionID && (this.rand.nextInt(50) == 0) && this.getPosY() < 10D) {
                this.remove();
            }*/

            if (getIsFlying() && this.getNavigation().isDone() && !isMovementCeased() && this.getTarget() == null && random.nextInt(30) == 0) {
                this.wander.makeUpdate();
            }

            if (this.getDeltaMovement().y > 0.5) // prevent large boundingbox checks
            {
                this.setDeltaMovement(this.getDeltaMovement().x, 0.5, this.getDeltaMovement().z);
            }

            if (isOnAir()) {
                float myFlyingSpeed = MoCTools.getMyMovementSpeed(this);
                int wingFlapFreq = (int) (25 - (myFlyingSpeed * 10));
                if (!this.isVehicle() || wingFlapFreq < 5) {
                    wingFlapFreq = 5;
                }
                if (this.random.nextInt(wingFlapFreq) == 0) {
                    wingFlap();
                }
            }

            if (getIsGhost() && getEdad() > 0 && getEdad() < 10 && this.random.nextInt(5) == 0) {
                setEdad(getEdad() + 1);
                if (getEdad() == 9) {
                    setEdad(140);
                    setAdult(true);
                }
            }

        } else {

            if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
                this.mouthCounter = 0;
            }

            if (this.diveCounter > 0 && ++this.diveCounter > 5) {
                this.diveCounter = 0;
            }
        }
        super.aiStep();
    }

    public void wingFlap() {
        if (this.wingFlapCounter == 0) {
            this.wingFlapCounter = 1;
//            if (!this.world.isRemote) {
//                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
//                        new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//            }
        }
    }

    @Override
    public float getSizeFactor() {
        return getEdad() * 0.01F;
    }

    @Override
    public boolean isFlyingAlone() {
        return getIsFlying() && !this.isVehicle();
    }

    @Override
    public int maxFlyingHeight() {
        if (getIsTamed())
            return 5;
        return 18;
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && (stack.getItem() == MoCItems.WHIP) && getIsTamed() && (!this.isBeingRidden())) {
            setSitting(!getIsSitting());
            return true;
        }

        if (!stack.isEmpty() && !getIsRideable() && getEdad() > 90 && this.getIsTamed()
                && (stack.getItem() == Items.SADDLE || stack.getItem() == MoCItems.HORSESADDLE)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            setRideable(true);
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getEdad() > 90 && stack.getItem() == Items.IRON_HORSE_ARMOR) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON);
            }
            dropArmor();
            setArmorType((byte) 1);
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }

            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getEdad() > 90 && stack.getItem() == Items.GOLDEN_HORSE_ARMOR) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON);
            }
            dropArmor();
            setArmorType((byte) 2);
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getEdad() > 90 && stack.getItem() == Items.DIAMOND_HORSE_ARMOR) {
            if (getArmorType() == 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_ON);
            }
            dropArmor();
            setArmorType((byte) 3);
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            return true;
        }

        if (!stack.isEmpty() && getIsTamed() && getEdad() > 90 && !getIsChested() && (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST))) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            setIsChested(true);
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            return true;
        }

        *//*if (getIsChested() && player.isCrouching()) {
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("WyvernChest", 9);
            }
            if (!this.world.isRemote) {
                player.displayGUIChest(this.localchest);
            }
            return true;
        }*//*

        if (!stack.isEmpty() && this.getIsGhost() && this.getIsTamed() && stack.getItem() == MoCItems.AMULETGHOST) {

            player.setHeldItem(hand, ItemStack.EMPTY);
            if (!this.world.isRemote) {
                MoCPetData petData = MoCreatures.instance.mapData.getPetData(this.getOwnerId());
                if (petData != null) {
                    petData.setInAmulet(this.getOwnerPetId(), true);
                }
                this.dropMyStuff();
                MoCTools.dropAmulet(this, 3, player);
                this.dead = true;
            }

            return true;

        }

        if (!stack.isEmpty() && !this.getIsGhost() && (stack.getItem() == MoCItems.ESSENCELIGHT) && getIsTamed() && getEdad() > 90
                && getSubType() < 5) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (!this.world.isRemote) {
                int i = getSubType() + 49;
                MoCEntityEgg entityegg = new MoCEntityEgg(MoCEntities.EGG, this.world, i);
                entityegg.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                player.world.addEntity(entityegg);
                entityegg.getMotion().add((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.3F,
                        this.world.rand.nextFloat() * 0.05F,
                        (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.3F);
            }
            return true;
        }

        if (!stack.isEmpty() && this.transformCounter == 0 && !this.getIsGhost() && getSubType() == 5
                && (stack.getItem() == MoCItems.ESSENCEUNDEAD) && getIsTamed()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (!this.world.isRemote) {
                transform(6);
            }
            return true;
        }

        if (!stack.isEmpty() && this.transformCounter == 0 && !this.getIsGhost() && getSubType() == 5
                && (stack.getItem() == MoCItems.ESSENCELIGHT) && getIsTamed()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (!this.world.isRemote) {
                transform(7);
            }
            return true;
        }

        if (!stack.isEmpty() && this.transformCounter == 0 && !this.getIsGhost() && getSubType() == 5
                && (stack.getItem() == MoCItems.ESSENCEDARKNESS) && getIsTamed()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }

            if (!this.world.isRemote) {
                transform(8);
            }
            return true;
        }

        if (this.getIsRideable() && getEdad() > 90 && (!this.getIsChested() || !player.isCrouching()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setSitting(false);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }*/

    /**
     * Drops the current armor
     */
    @Override
    public void dropArmor() {
        if (!this.level.isClientSide) {
            int i = getArmorType();
            if (i != 0) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
            }

            if (i == 1) {
                ItemEntity entityitem = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.IRON_HORSE_ARMOR, 1));
                entityitem.setPickUpDelay(10);
                this.level.addFreshEntity(entityitem);
            }
            if (i == 2) {
                ItemEntity entityitem = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.GOLDEN_HORSE_ARMOR, 1));
                entityitem.setPickUpDelay(10);
                this.level.addFreshEntity(entityitem);
            }
            if (i == 3) {
                ItemEntity entityitem = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(Items.DIAMOND_HORSE_ARMOR, 1));
                entityitem.setPickUpDelay(10);
                this.level.addFreshEntity(entityitem);
            }
            setArmorType((byte) 0);
        }
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_WYVERN_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        return MoCSoundEvents.ENTITY_WYVERN_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_WYVERN_AMBIENT;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    @Override
    public boolean isMovementCeased() {
        return (this.isVehicle()) || getIsSitting();
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getBbHeight() * 0.85 * getSizeFactor();
    }

    @Override
    public void positionRider(Entity passenger) {
        double dist = getSizeFactor() * (0.3D);
        double newPosX = this.getX() - (dist * Math.cos((MoCTools.realAngle(this.yBodyRot - 90F)) / 57.29578F));
        double newPosZ = this.getZ() - (dist * Math.sin((MoCTools.realAngle(this.yBodyRot - 90F)) / 57.29578F));
        passenger.setPos(newPosX, this.getY() + getPassengersRidingOffset() + passenger.getMyRidingOffset(), newPosZ);
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        if (entityIn instanceof PlayerEntity && !shouldAttackPlayers()) {
            return false;
        }
        openMouth();
        return super.doHurtTarget(entityIn);
    }

    @Override
    public void doEnchantDamageEffects(LivingEntity entityLivingBaseIn, Entity entityIn) {
        if ((entityIn instanceof PlayerEntity) && this.random.nextInt(3) == 0) {
            MoCreatures.poisonPlayer((PlayerEntity) entityIn);
            ((LivingEntity) entityIn).addEffect(new EffectInstance(Effects.POISON, 200, 0));
        }

        super.doEnchantDamageEffects(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        Entity entity = damagesource.getEntity();
        if (entity != null && this.hasIndirectPassenger(entity)) {
            return false;
        }
        if (super.hurt(damagesource, i)) {
            if (entity instanceof PlayerEntity && getIsTamed()) {
                return false;
            }

            if ((entity != this) && (super.shouldAttackPlayers())) {
                setTarget((LivingEntity) entity);
            }
            return true;
        }
        return false;
    }

    /*@Override
    public boolean entitiesToIgnore(Entity entity) {
        return (super.entitiesToIgnore(entity) || (entity instanceof MoCEntityWyvern) || (entity instanceof EntityPlayer));
    }*/

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putBoolean("Saddle", getIsRideable());
        nbttagcompound.putBoolean("Chested", getIsChested());
        nbttagcompound.putInt("ArmorType", getArmorType());
        nbttagcompound.putBoolean("isSitting", getIsSitting());
        nbttagcompound.putBoolean("isGhost", getIsGhost());
        /*if (getIsChested() && this.localchest != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localchest.getSizeInventory(); i++) {
                this.localstack = this.localchest.getStackInSlot(i);
                if (this.localstack != null && !this.localstack.isEmpty()) {
                    CompoundNBT nbttagcompound1 = new CompoundNBT();
                    nbttagcompound1.putByte("Slot", (byte) i);
                    this.localstack.write(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }
            nbttagcompound.put("Items", nbttaglist);
        }*/
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setIsChested(nbttagcompound.getBoolean("Chested"));
        setArmorType(nbttagcompound.getInt("ArmorType"));
        setSitting(nbttagcompound.getBoolean("isSitting"));
        setIsGhost(nbttagcompound.getBoolean("isGhost"));
        /*if (getIsChested()) {
            ListNBT nbttaglist = nbttagcompound.getList("Items", 10);
            this.localchest = new MoCAnimalChest("WyvernChest", 14);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localchest.getSizeInventory()) {
                    this.localchest.setInventorySlotContents(j, new ItemStack(nbttagcompound1));
                }
            }
        }*/
    }

    @Override
    public int nameYOffset() {
        int yOff = getEdad() * -1;
        if (yOff < -120) {
            yOff = -120;
        }
        if (getIsSitting())
            yOff += 25;
        return yOff;
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == MoCItems.RAT_RAW || stack.getItem() == MoCItems.TURKEY_RAW);
    }

    private void openMouth() {
        if (!this.level.isClientSide) {
            this.mouthCounter = 1;
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }

    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 1) //opening mouth
        {
            this.mouthCounter = 1;
        }
        if (animationType == 2) //diving mount
        {
            this.diveCounter = 1;
        }
        if (animationType == 3) {
            this.wingFlapCounter = 1;
        }
        if (animationType > 5 && animationType < 9) //transform 6 - 8
        {
            this.transformType = animationType;
            this.transformCounter = 1;
        }
    }

    @Override
    public void makeEntityDive() {
//        if (!this.world.isRemote) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 2),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//        }
        super.makeEntityDive();
    }

//    @Override
//    protected void dropFewItems(boolean flag, int x) {
//        int chance = MoCreatures.proxy.wyvernEggDropChance;
//        if (getSubType() == 5) { //mother wyverns drop eggs more frequently
//            chance = MoCreatures.proxy.motherWyvernEggDropChance;
//        }
//        if (this.rand.nextInt(100) < chance) {
//            entityDropItem(new ItemStack(MoCItems.MOCEGG, 1, getSubType() + 49), 0.0F);
//        }
//    }

    @Override
    public boolean isPickable() {
        return !this.isVehicle();
    }

    @Override
    public void dropMyStuff() {
        if (!this.level.isClientSide) {
            dropArmor();
            MoCTools.dropSaddle(this, this.level);

            if (getIsChested()) {
//                MoCTools.dropInventory(this, this.localchest);
                MoCTools.dropCustomItem(this, this.level, new ItemStack(Blocks.CHEST, 1));
                setIsChested(false);
            }
        }
    }

    @Override
    public float getAdjustedYOffset() {
        if (getIsSitting()) {
            return 0.4F;
        }
        return 0F;
    }

    @Override
    public double getCustomSpeed() {
        if (this.isVehicle()) {
            return 1.0D;
        }
        return 0.8D;
    }

    @Override
    public int getMaxEdad() {
        if (this.getSubType() == 5) {
            return 180;
        }
        if (this.getSubType() == 6 || this.getSubType() == 7 || this.getSubType() == 8) {
            return 160;
        }
        return 120;
    }

    @Override
    public CreatureAttribute getMobType() {
        if (getSubType() == 6 || getIsGhost()) {
            return CreatureAttribute.UNDEAD;
        }
        return super.getMobType();
    }

    @Override
    public boolean isReadyToHunt() {
        return !this.isMovementCeased() && !this.isVehicle();
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return !(entity instanceof MoCEntityWyvern) && entity.getBbHeight() <= 1D && entity.getBbWidth() <= 1D;
    }

    @Override
    protected double flyerThrust() {
        return 0.6D;
    }

    @Override
    public float getSpeed() {
        if (getIsFlying()) {
            return 0.4F;
        }
        return super.getSpeed();
    }

    @Override
    protected float flyerFriction() {
        if (this.getSubType() == 5) {
            return 0.96F;
        }
        if (this.getSubType() == 6 || this.getSubType() == 7 || this.getSubType() == 8 || this.getIsGhost()) {
            return 0.96F;
        }
        return 0.94F;
    }

    @Override
    public void makeEntityJump() {
        wingFlap();
        super.makeEntityJump();
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsTamed() && super.shouldAttackPlayers();
    }

    @Override
    public void die(DamageSource damagesource) {
        if (!this.level.isClientSide) {
            if (this.getSubType() == 6) {
                MoCTools.spawnMaggots(this.level, this);
            }

            if (!getIsGhost() && getIsTamed() && this.random.nextInt(4) == 0) {
                MoCEntityWyvern entitywyvern = new MoCEntityWyvern(MoCEntities.WYVERN, this.level);
                entitywyvern.setPos(this.getX(), this.getY(), this.getZ());
                this.level.addFreshEntity(entitywyvern);
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_MAGIC_APPEAR);

                entitywyvern.setOwnerId(this.getOwnerId());
                entitywyvern.setTamed(true);
                PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                if (entityplayer != null) {
                    MoCTools.tameWithName(entityplayer, entitywyvern);
                }

                entitywyvern.setAdult(false);
                entitywyvern.setEdad(1);
                entitywyvern.setType(this.getSubType());
                entitywyvern.selectType();
                entitywyvern.setIsGhost(true);
            }

        }
        super.die(damagesource);

    }

    public float tFloat() {

        if (++this.tCounter > 30) {
            this.tCounter = 0;
            this.fTransparency = (this.random.nextFloat() * (0.4F - 0.2F) + 0.15F);
        }

        if (this.getEdad() < 10) {
            return 0F;
        }
        return fTransparency;
    }

    @Override
    protected boolean canBeTrappedInNet() {
        return this.getIsTamed() && !this.getIsGhost();
    }
}
