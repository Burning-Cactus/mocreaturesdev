package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.FollowAdultGoal;
import drzhark.mocreatures.entity.ai.FollowOwnerGoal;
import drzhark.mocreatures.entity.ai.HuntGoal;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import drzhark.mocreatures.registry.MoCItems;
import drzhark.mocreatures.registry.MoCSoundEvents;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
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
import net.minecraft.util.*;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;

public class MoCEntityBigCat extends MoCEntityTameableAnimal {

    public int mouthCounter;
    public int tailCounter;
    public int wingFlapCounter;
    public MoCAnimalChest localchest;
    public ItemStack localstack;
    protected String chestName = "BigCatChest";
    private int tCounter;
    private float fTransparency;
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.defineId(MoCEntityBigCat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_AMULET = EntityDataManager.defineId(MoCEntityBigCat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SITTING = EntityDataManager.defineId(MoCEntityBigCat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GHOST = EntityDataManager.defineId(MoCEntityBigCat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.defineId(MoCEntityBigCat.class, DataSerializers.BOOLEAN);

    public MoCEntityBigCat(EntityType<? extends MoCEntityBigCat> type, World world) {
        super(type, world);
        setEdad(45);
        if (this.random.nextInt(4) == 0) {
            setAdult(false);
        } else {
            setAdult(true);
        }
        this.maxUpStep = 1.0F;
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(4, new FollowAdultGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new FollowOwnerGoal(this, 1D, 2F, 10F));
        this.goalSelector.addGoal(2, new MoCAlternateWanderGoal(this, 0.8D, 30));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(4, new HuntGoal(this, AnimalEntity.class, true));
    }

    /*@Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(8.0D);
    }*/

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .add(Attributes.MAX_HEALTH, 40D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 8.0D);
    }

    /**
     * Sets the type and texture of a BigCat if not set already.
     */
    @Override
    public void selectType() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(calculateAttackDmg());
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(getAttackRange());
        if (getIsAdult()) {
            setEdad(getMaxEdad());
        }
    }

    @Override
    public double getCustomSpeed() {
        return 2D;
    }

    public float getMoveSpeed() {
        return 1.6F;
    }

    public float calculateMaxHealth() {
        return 20F;
    }

    public double calculateAttackDmg() {
        return 5D;
    }

    public double getAttackRange() {
        return 6D;
    }

    /**
     * Initializes datawatchers for entity. Each datawatcher is used to sync
     * server data to client.
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RIDEABLE, Boolean.FALSE);
        this.entityData.define(SITTING, Boolean.FALSE);
        this.entityData.define(GHOST, Boolean.FALSE);
        this.entityData.define(HAS_AMULET, Boolean.FALSE);
        this.entityData.define(CHESTED, Boolean.FALSE);
    }

    public boolean getHasAmulet() {
        return this.entityData.get(HAS_AMULET);
    }

    @Override
    public boolean getIsSitting() {
        return this.entityData.get(SITTING);
    }

    @Override
    public boolean getIsRideable() {
        return this.entityData.get(RIDEABLE);
    }

    public boolean getIsChested() {
        return this.entityData.get(CHESTED);
    }

    @Override
    public boolean getIsGhost() {
        return this.entityData.get(GHOST);
    }

    public void setHasAmulet(boolean flag) {
        this.entityData.set(HAS_AMULET, flag);
    }

    public void setSitting(boolean flag) {
        this.entityData.set(SITTING, flag);
    }

    public void setIsChested(boolean flag) {
        this.entityData.set(CHESTED, flag);
    }

    public void setRideable(boolean flag) {
        this.entityData.set(RIDEABLE, flag);
    }

    public void setIsGhost(boolean flag) {
        this.entityData.set(GHOST, flag);
    }

    // Method used for receiving damage from another source
    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        Entity entity = damagesource.getEntity();
        if ((this.isVehicle()) && (entity == this.getVehicle())) {
            return false;
        }

        if (super.hurt(damagesource, i)) {
            if (entity != null && getIsTamed() && entity instanceof PlayerEntity) {
                return false;
            }
            if (entity != this && entity instanceof LivingEntity && (this.level.getDifficulty() != Difficulty.PEACEFUL)) {
                setTarget((LivingEntity) entity);
            }
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    protected SoundEvent getDeathSound() {
        openMouth();
        if (getIsAdult()) {
            return MoCSoundEvents.ENTITY_LION_DEATH;
        } else {
            return MoCSoundEvents.ENTITY_LION_DEATH_BABY;
        }
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        if (getIsAdult()) {
            return MoCSoundEvents.ENTITY_LION_HURT;
        } else {
            return MoCSoundEvents.ENTITY_LION_HURT_BABY;
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        if (getIsAdult()) {
            return MoCSoundEvents.ENTITY_LION_AMBIENT;
        } else {
            return MoCSoundEvents.ENTITY_LION_AMBIENT_BABY;
        }
    }

    @Override
    public void die(DamageSource damagesource) {
        if (!this.level.isClientSide) {
            if (getHasAmulet()) {
                    MoCTools.dropCustomItem(this, this.level, new ItemStack(MoCItems.MEDALLION, 1));
                    setHasAmulet(false);
            }

            if (getIsTamed() && !getIsGhost() && this.random.nextInt(4) == 0) {
                this.spawnGhost();
            }
        }
        super.die(damagesource);
    }

    public void spawnGhost() {
        /*try {
            LivingEntity templiving = (LivingEntity) EntityList.createEntityByIDFromName(new ResourceLocation(this.getClazzString().toLowerCase()), this.world);
            if (templiving != null && templiving instanceof MoCEntityBigCat) {
                MoCEntityBigCat ghost = (MoCEntityBigCat) templiving;
                ghost.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                this.world.addEntity(ghost);
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_MAGIC_APPEAR);
                ghost.setOwnerId(this.getOwnerId());
                ghost.setTamed(true);
                PlayerEntity entityplayer = this.world.getClosestPlayer(this, 24D);
                if (entityplayer != null) {
                    MoCTools.tameWithName(entityplayer, ghost);
                }

                ghost.setAdult(false);
                ghost.setEdad(1);
                ghost.setType(this.getSubType());
                ghost.selectType();
                ghost.setIsGhost(true);

            }
        } catch (Exception e) {
        }*/

    }

    @Override
    public void aiStep() {

        super.aiStep();

        if (!this.level.isClientSide) {
            if (this.getTarget() == null) {
                setSprinting(false);
            } else {
                setSprinting(true);
            }
        }
        
        if (this.level.isClientSide) //animation counters
        {
            if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
                this.mouthCounter = 0;
            }

            if (this.random.nextInt(250) == 0) {
                moveTail();
            }

            if (this.tailCounter > 0 && ++this.tailCounter > 10 && this.random.nextInt(15) == 0) {
                this.tailCounter = 0;
            }
        } else //server stuff
        {
            if (getIsGhost() && getEdad() > 0 && getEdad() < 10 && this.random.nextInt(5) == 0) {
                setEdad(getEdad() + 1);
                if (getEdad() == 9) {
                    setEdad(getMaxEdad());
                    setAdult(true);
                }
            }

            if (!getIsGhost() && getEdad() <10)
            {
                this.remove();
            }
            /*if (getHasEaten() && rand.nextInt(300) == 0)
            {
                setEaten(false);
            }*/
        }

        if (!this.level.isClientSide && isFlyer() && isOnAir()) {
            float myFlyingSpeed = MoCTools.getMyMovementSpeed(this);
            int wingFlapFreq = (int) (25 - (myFlyingSpeed * 10));
            if (!this.isVehicle() || wingFlapFreq < 5) {
                wingFlapFreq = 5;
            }
            if (this.random.nextInt(wingFlapFreq) == 0) {
                wingFlap();
            }
        }

        if (isFlyer()) {
            if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
                this.wingFlapCounter = 0;
            }
            if (!this.level.isClientSide && this.wingFlapCounter == 5) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_WINGFLAP);
            }
        }

        if ((this.random.nextInt(300) == 0) && (this.getHealth() <= getMaxHealth()) && (this.deathTime == 0) && !this.level.isClientSide) {
            this.setHealth(getHealth() + 1);
        }

        if ((this.deathTime == 0) && !isMovementCeased()) {
            ItemEntity entityitem = getClosestItem(this, 12D, Items.PORKCHOP, Items.COD);
            if (entityitem != null) {
                float f = entityitem.distanceTo(this);
                if (f > 2.0F) {
                    getMyOwnPath(entityitem, f);
                }
                if ((f < 2.0F) && (entityitem != null) && (this.deathTime == 0)) {
                    entityitem.remove();
                    this.setHealth(getMaxHealth());
                    setHasEaten(true);
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
                }
            }
        }
    }

    @Override
    public boolean readytoBreed() {
        return !this.getIsGhost() && super.readytoBreed();
    }

    public void wingFlap() {
        if (this.level.isClientSide) {
            return;
        }

        if (this.wingFlapCounter == 0) {
            this.wingFlapCounter = 1;
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult() || getEdad() > 80;
    }

    @Override
    public boolean isReadyToHunt() {
        return getIsAdult() && !this.isMovementCeased();
    }

    @Override
    public void positionRider(Entity passenger) {
        double dist = getSizeFactor() * (0.1D);
        double newPosX = this.getX() + (dist * Math.sin(this.yBodyRot / 57.29578F));
        double newPosZ = this.getZ() - (dist * Math.cos(this.yBodyRot / 57.29578F));
        passenger.setPos(newPosX, this.getY() + getPassengersRidingOffset() + passenger.getMyRidingOffset(), newPosZ);
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putBoolean("Saddle", getIsRideable());
        nbttagcompound.putBoolean("Sitting", getIsSitting());
        nbttagcompound.putBoolean("Chested", getIsChested());
        nbttagcompound.putBoolean("Ghost", getIsGhost());
        nbttagcompound.putBoolean("Amulet", getHasAmulet());
        /*if (getIsChested() && this.localchest != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localchest.getSizeInventory(); i++) {
                // grab the current item stack
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
        setSitting(nbttagcompound.getBoolean("Sitting"));
        setIsChested(nbttagcompound.getBoolean("Chested"));
        setIsGhost(nbttagcompound.getBoolean("Ghost"));
        setHasAmulet(nbttagcompound.getBoolean("Amulet"));
        /*if (getIsChested()) {
            ListNBT nbttaglist = nbttagcompound.getList("Items", 10);
            this.localchest = new MoCAnimalChest("BigCatChest", 18);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localchest.getSizeInventory()) {
                    this.localchest.setInventorySlotContents(j, new ItemStack(nbttagcompound1));
                }
            }
        }*/

    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && !getIsTamed() && getHasEaten() && !getIsAdult() && (stack.getItem() == MoCItems.MEDALLION)) {
            if (!this.world.isRemote) {
                setHasAmulet(true);
                MoCTools.tameWithName(player, this);
            }
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
                return true;
            }
            return true;
        }

        if (!stack.isEmpty() && getIsTamed() && !getHasAmulet() && (stack.getItem() == MoCItems.MEDALLION)) {
            if (!this.world.isRemote) {
                setHasAmulet(true);
            }
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
                return true;
        }
            return true;
        }
        
        if (!stack.isEmpty() && getIsTamed() && (stack.getItem() == MoCItems.WHIP)) {
            setSitting(!getIsSitting());
            return true;
        }
        if (!stack.isEmpty() && getIsTamed() && (MoCTools.isItemEdibleforCarnivores(stack.getItem()))) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            this.setHealth(getMaxHealth());
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            setIsHunting(false);
            setHasEaten(true);
            return true;
        }
        if (!stack.isEmpty() && getIsTamed() && !getIsRideable() && (getEdad() > 80)
                && (stack.getItem() == Items.SADDLE || stack.getItem() == MoCItems.HORSESADDLE)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            setRideable(true);
            return true;
        }

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

        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && !getIsChested() && (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST))) {
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
                this.localchest = new MoCAnimalChest(this.chestName, 18);
            }
            if (!this.world.isRemote) {
                player.displayGUIChest(this.localchest);
            }
            return true;
        }*//*

        return super.processInteract(player, hand);
    }*/

    @Override
    public float getSizeFactor() {
        return getEdad() * 0.01F;
    }

//    @Override
//    public void fall(float f, float f1) {
//        if (isFlyer()) {
//            return;
//        }
//        float i = (float) (Math.ceil(f - 3F) / 2F);
//        if (!this.world.isRemote && (i > 0)) {
//            i /= 2;
//            if (i > 1F) {
//                attackEntityFrom(DamageSource.FALL, i);
//            }
//            if ((this.isBeingRidden()) && (i > 1F)) {
//                for (Entity entity : this.getRecursivePassengers())
//                {
//                    entity.attackEntityFrom(DamageSource.FALL, (float)i);
//                }
//            }
//            BlockState blockstate = this.world.getBlockState(new BlockPos(this.getPosX(), this.getPosY() - 0.2D - (double)this.prevRotationYaw, this.getPosZ()));
//            Block block = blockstate.getBlock();
//
//            if (blockstate.getMaterial() != Material.AIR && !this.isSilent())
//            {
//                SoundType soundtype = block.getSoundType(blockstate, world, new BlockPos(this.getPosX(), this.getPosY() - 0.2D - (double)this.prevRotationYaw, this.getPosZ()), this);
//                this.world.playSound((PlayerEntity) null, this.getPosX(), this.getPosY(), this.getPosZ(), soundtype.getStepSound(), this.getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
//            }
//        }
//    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    public boolean hasMane() {
        return false;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    public boolean hasSaberTeeth() {
        return false;
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //tail animation
        {
            //setPoisoning(true);
        } else if (animationType == 3) //wing flap
        {
            this.wingFlapCounter = 1;
        }
    }

    @Override
    public void makeEntityJump() {
        if (this.isFlyer()) {
            wingFlap();
        }
        super.makeEntityJump();
    }

    @Override
    public void dropMyStuff() {
        if (!this.level.isClientSide) {
            dropArmor();
            MoCTools.dropSaddle(this, this.level);

            if (getIsChested()) {
//                MoCTools.dropInventory(this, this.localchest); TODO: Make chests work
                MoCTools.dropCustomItem(this, this.level, new ItemStack(Blocks.CHEST, 1));
                setIsChested(false);
            }
        }
    }

    public boolean getHasStinger() {
        return false;
    }

    @Override
    public double getPassengersRidingOffset() {
        double Yfactor = ((0.0833D * this.getEdad()) - 2.5D) / 10D;
        return this.getBbHeight() * Yfactor;
    }

    public float tFloat() {

        if (++this.tCounter > 30) {
            this.tCounter = 0;
            this.fTransparency = (this.random.nextFloat() * (0.4F - 0.2F) + 0.15F);
        }

        if (this.getEdad() < 10) {
            return 0F;
        }
        return this.fTransparency;
    }

    @Override
    public int nameYOffset() {
        return (int) (((0.445D * this.getEdad()) + 15D) * -1);
    }
    
    @Override
    public boolean rideableEntity() {
        return true;
    }
    
    @Override
    public float getSpeed() {
        if (isSprinting()) {
            return 0.37F;
        }
        return 0.18F;
    }
}

//would be nice
//lying down
//manticore sounds, drops
//cheetahs
//hand swing when attacking
//more hybrids
//jaguars
//lynx / bobcats