package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityBigCat extends MoCEntityTameableAnimal {

    public int mouthCounter;
    public int tailCounter;
    public int wingFlapCounter;
    public MoCAnimalChest localchest;
    public ItemStack localstack;
    protected String chestName = "BigCatChest";
    private int tCounter;
    private float fTransparency;
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.<Boolean>createKey(MoCEntityBigCat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_AMULET = EntityDataManager.<Boolean>createKey(MoCEntityBigCat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SITTING = EntityDataManager.<Boolean>createKey(MoCEntityBigCat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GHOST = EntityDataManager.<Boolean>createKey(MoCEntityBigCat.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.<Boolean>createKey(MoCEntityBigCat.class, DataSerializers.BOOLEAN);

    public MoCEntityBigCat(EntityType<? extends MoCEntityBigCat> type, World world) {
        super(type, world);
        setEdad(45);
        if (this.rand.nextInt(4) == 0) {
            setAdult(false);
        } else {
            setAdult(true);
        }
        this.stepHeight = 1.0F;
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(4, new EntityAIFollowAdult(this, 1.0D));
        this.goalSelector.addGoal(5, new EntityAIFollowOwnerPlayer(this, 1D, 2F, 10F));
        this.goalSelector.addGoal(2, new EntityAIWanderMoC2(this, 0.8D, 30));
        this.targetSelector.addGoal(3, new EntityAINearestAttackableTargetMoC(this, PlayerEntity.class, true));
        this.targetSelector.addGoal(4, new EntityAIHunt(this, AnimalEntity.class, true));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(8.0D);
    }

    /**
     * Sets the type and texture of a BigCat if not set already.
     */
    @Override
    public void selectType() {
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(calculateAttackDmg());
        this.getAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(getAttackRange());
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
    protected void registerData() {
        super.registerData();
        this.dataManager.register(RIDEABLE, Boolean.valueOf(false)); 
        this.dataManager.register(SITTING, Boolean.valueOf(false));
        this.dataManager.register(GHOST, Boolean.valueOf(false)); 
        this.dataManager.register(HAS_AMULET, Boolean.valueOf(false)); 
        this.dataManager.register(CHESTED, Boolean.valueOf(false));
    }

    public boolean getHasAmulet() {
        return ((Boolean)this.dataManager.get(HAS_AMULET)).booleanValue();
    }

    @Override
    public boolean getIsSitting() {
        return ((Boolean)this.dataManager.get(SITTING)).booleanValue();
    }

    @Override
    public boolean getIsRideable() {
        return ((Boolean)this.dataManager.get(RIDEABLE)).booleanValue();
    }

    public boolean getIsChested() {
        return ((Boolean)this.dataManager.get(CHESTED)).booleanValue();
    }

    @Override
    public boolean getIsGhost() {
        return ((Boolean)this.dataManager.get(GHOST)).booleanValue();
    }

    public void setHasAmulet(boolean flag) {
        this.dataManager.set(HAS_AMULET, Boolean.valueOf(flag));
    }

    public void setSitting(boolean flag) {
        this.dataManager.set(SITTING, Boolean.valueOf(flag));
    }

    public void setIsChested(boolean flag) {
        this.dataManager.set(CHESTED, Boolean.valueOf(flag));
    }

    public void setRideable(boolean flag) {
        this.dataManager.set(RIDEABLE, Boolean.valueOf(flag));
    }

    public void setIsGhost(boolean flag) {
        this.dataManager.set(GHOST, Boolean.valueOf(flag));
    }

    // Method used for receiving damage from another source
    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getTrueSource();
        if ((this.isBeingRidden()) && (entity == this.getRidingEntity())) {
            return false;
        }

        if (super.attackEntityFrom(damagesource, i)) {
            if (entity != null && getIsTamed() && entity instanceof PlayerEntity) {
                return false;
            }
            if (entity != this && entity instanceof LivingEntity && (this.world.getDifficulty() != Difficulty.PEACEFUL)) {
                setAttackTarget((LivingEntity) entity);
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
//
//    @Override
//    protected Item getDropItem() {
//        return MoCItems.BIGCATCLAW;
//    }

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
    public void onDeath(DamageSource damagesource) {
        if (!this.world.isRemote) {
            if (getHasAmulet()) {
                    MoCTools.dropCustomItem(this, this.world, new ItemStack(MoCItems.MEDALLION, 1));
                    setHasAmulet(false);
            }

            if (getIsTamed() && !getIsGhost() && this.rand.nextInt(4) == 0) {
                this.spawnGhost();
            }
        }
        super.onDeath(damagesource);
    }

    public void spawnGhost() {
        try {
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
        }

    }

    @Override
    public void livingTick() {

        super.livingTick();

        if (!this.world.isRemote) {
            if (this.getAttackTarget() == null) {
                setSprinting(false);
            } else {
                setSprinting(true);
            }
        }
        
        if (this.world.isRemote) //animation counters
        {
            if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
                this.mouthCounter = 0;
            }

            if (this.rand.nextInt(250) == 0) {
                moveTail();
            }

            if (this.tailCounter > 0 && ++this.tailCounter > 10 && this.rand.nextInt(15) == 0) {
                this.tailCounter = 0;
            }
        } else //server stuff
        {
            if (getIsGhost() && getEdad() > 0 && getEdad() < 10 && this.rand.nextInt(5) == 0) {
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

        if (!this.world.isRemote && isFlyer() && isOnAir()) {
            float myFlyingSpeed = MoCTools.getMyMovementSpeed(this);
            int wingFlapFreq = (int) (25 - (myFlyingSpeed * 10));
            if (!this.isBeingRidden() || wingFlapFreq < 5) {
                wingFlapFreq = 5;
            }
            if (this.rand.nextInt(wingFlapFreq) == 0) {
                wingFlap();
            }
        }

        if (isFlyer()) {
            if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
                this.wingFlapCounter = 0;
            }
            if (!this.world.isRemote && this.wingFlapCounter == 5) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_WINGFLAP);
            }
        }

        if ((this.rand.nextInt(300) == 0) && (this.getHealth() <= getMaxHealth()) && (this.deathTime == 0) && !this.world.isRemote) {
            this.setHealth(getHealth() + 1);
        }

        if ((this.deathTime == 0) && !isMovementCeased()) {
            ItemEntity entityitem = getClosestItem(this, 12D, Items.PORKCHOP, Items.COD);
            if (entityitem != null) {
                float f = entityitem.getDistance(this);
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
        if (this.world.isRemote) {
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
    public void updatePassenger(Entity passenger) {
        double dist = getSizeFactor() * (0.1D);
        double newPosX = this.getPosX() + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.getPosZ() - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        passenger.setPosition(newPosX, this.getPosY() + getMountedYOffset() + passenger.getYOffset(), newPosZ);
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Saddle", getIsRideable());
        nbttagcompound.putBoolean("Sitting", getIsSitting());
        nbttagcompound.putBoolean("Chested", getIsChested());
        nbttagcompound.putBoolean("Ghost", getIsGhost());
        nbttagcompound.putBoolean("Amulet", getHasAmulet());
        if (getIsChested() && this.localchest != null) {
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
        }

    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setSitting(nbttagcompound.getBoolean("Sitting"));
        setIsChested(nbttagcompound.getBoolean("Chested"));
        setIsGhost(nbttagcompound.getBoolean("Ghost"));
        setHasAmulet(nbttagcompound.getBoolean("Amulet"));
        if (getIsChested()) {
            ListNBT nbttaglist = nbttagcompound.getList("Items", 10);
            this.localchest = new MoCAnimalChest("BigCatChest", 18);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localchest.getSizeInventory()) {
                    this.localchest.setInventorySlotContents(j, new ItemStack(nbttagcompound1));
                }
            }
        }

    }

    @Override
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

        if (getIsChested() && player.isCrouching()) {
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest(this.chestName, 18);
            }
            if (!this.world.isRemote) {
                player.displayGUIChest(this.localchest);
            }
            return true;
        }

        return super.processInteract(player, hand);
    }

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
    public int getTalkInterval() {
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
        if (!this.world.isRemote) {
            dropArmor();
            MoCTools.dropSaddle(this, this.world);

            if (getIsChested()) {
                MoCTools.dropInventory(this, this.localchest);
                MoCTools.dropCustomItem(this, this.world, new ItemStack(Blocks.CHEST, 1));
                setIsChested(false);
            }
        }
    }

    public boolean getHasStinger() {
        return false;
    }

    @Override
    public double getMountedYOffset() {
        double Yfactor = ((0.0833D * this.getEdad()) - 2.5D) / 10D;
        return this.getHeight() * Yfactor;
    }

    public float tFloat() {

        if (++this.tCounter > 30) {
            this.tCounter = 0;
            this.fTransparency = (this.rand.nextFloat() * (0.4F - 0.2F) + 0.15F);
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
    public float getAIMoveSpeed() {
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