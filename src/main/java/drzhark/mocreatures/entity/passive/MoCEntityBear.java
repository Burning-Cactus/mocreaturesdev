package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.registry.MoCSoundEvents;
import drzhark.mocreatures.inventory.MoCAnimalChest;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
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
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.network.NetworkHooks;

public class MoCEntityBear extends MoCEntityTameableAnimal {

    public int mouthCounter;
    private int attackCounter;
    private int standingCounter;
    private static final DataParameter<Integer> BEAR_STATE = EntityDataManager.defineId(MoCEntityBear.class, DataSerializers.INT);
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.defineId(MoCEntityBear.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.defineId(MoCEntityBear.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> GHOST = EntityDataManager.defineId(MoCEntityBear.class, DataSerializers.BOOLEAN);
    public MoCAnimalChest localchest;
    public ItemStack localstack;
    
    public MoCEntityBear(EntityType<? extends MoCEntityBear> type, World world) {
        super(type, world);
        setEdad(55);
        if (this.random.nextInt(4) == 0) {
            setAdult(false);
        } else {
            setAdult(true);
        }
        this.maxUpStep = 1.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new EntityAIPanicMoC(this, 1.0D));
        this.goalSelector.addGoal(3, new EntityAIFollowOwnerPlayer(this, 1D, 2F, 10F));
        this.goalSelector.addGoal(4, new EntityAIFollowAdult(this, 1.0D));
        this.goalSelector.addGoal(5, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(6, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new EntityAIHunt(this, AnimalEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
//                .add(Attributes.MAX_HEALTH, calculateMaxHealth())
//        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
//                .add(Attributes.ATTACK_DAMAGE, getAttackStrength())
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    /**
     * Initializes datawatchers for entity. Each datawatcher is used to sync
     * server data to client.
     */
    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BEAR_STATE, 0);
        this.entityData.define(RIDEABLE, Boolean.FALSE);
        this.entityData.define(CHESTED, Boolean.FALSE);
        this.entityData.define(GHOST, Boolean.FALSE);
    }

    /**
     * 0 - bear is on fours 1 - standing 2 - sitting
     *
     * @return
     */
    public int getBearState() {
        return this.entityData.get(BEAR_STATE);
    }

    public void setBearState(int i) {
        this.entityData.set(BEAR_STATE, i);
    }

    @Override
    public boolean getIsRideable() {
        return this.entityData.get(RIDEABLE);
    }

    public boolean getIsChested() {
        return this.entityData.get(CHESTED);
    }

    public boolean getIsGhost() {
        return this.entityData.get(GHOST);
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
    
    @Override
    public void selectType() {
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(getAttackStrength());
        this.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(getAttackRange());
        if (getIsAdult()) {
            setEdad(getMaxEdad());
        }
    }

    /**
     * Returns the factor size for the bear, polars are bigger and pandas
     * smaller
     *
     * @return
     */
    public float getBearSize() {
                return 1.0F;
    }

    public float calculateMaxHealth() {
                  return 30;
    }

    /**
     * Returns the distance at which the bear attacks prey
     *
     * @return
     */
    public double getAttackRange() {
              return 8D;
    }

    /**
     * The damage the bear does
     *
     * @return
     */
    public int getAttackStrength() {
                   return 2;
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        startAttack();
        return super.doHurtTarget(entityIn);
    }

    /**
     * Checks if entity is sitting.
     */
    @Override
    public boolean isMovementCeased() {
        return getBearState() == 2;
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (super.hurt(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if (this.hasIndirectPassenger(entity)) {
                return true;
            }
            if ((entity != this && entity instanceof LivingEntity) && super.shouldAttackPlayers()) {
                setTarget((LivingEntity) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult();
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.mouthCounter > 0 && ++this.mouthCounter > 20) {
            this.mouthCounter = 0;
        }
        if (this.attackCounter > 0 && ++this.attackCounter > 9) {
            this.attackCounter = 0;
        }
        if (!this.level.isClientSide && !getIsAdult() && getEdad() < 80 && (this.random.nextInt(300) == 0)) {
            setBearState(2);
        }
        /**
         * Sitting non tamed bears will resume on fours stance every now and then
         */
        if (!this.level.isClientSide && getBearState() == 2 && !getIsTamed() && this.random.nextInt(800) == 0) {
            setBearState(0);
        }
        if (!this.level.isClientSide && getBearState() == 2 && !getIsTamed() && !this.getNavigation().isDone()) {
            setBearState(0);
        }
        if (!this.level.isClientSide && this.standingCounter > 0 && ++this.standingCounter > 100) {
            this.standingCounter = 0;
            setBearState(0);
        }
        /**\
         * Standing if close to a vulnerable player
         */
        if (!this.level.isClientSide && !getIsTamed() && getIsStanding() 
                && getBearState() != 2 && getIsAdult() && (this.random.nextInt(200) == 0) && shouldAttackPlayers()) {
            PlayerEntity entityplayer1 = this.level.getNearestPlayer(this, 4D);
            if ((entityplayer1 != null && this.canSee(entityplayer1) && !entityplayer1.abilities.invulnerable)) {
                this.setStand();
                setBearState(1);
            }
        }
        //TODO move to AI
        if (!this.level.isClientSide && getSubType() == 3 && (this.deathTime == 0) && getBearState() != 2) {
            ItemEntity entityitem = getClosestItem(this, 12D, Items.SUGAR_CANE, Items.SUGAR);
            if (entityitem != null) {

                float f = entityitem.distanceTo(this);
                if (f > 2.0F) {
                    getMyOwnPath(entityitem, f);
                }
                if ((f < 2.0F) && (this.deathTime == 0)) {
                    entityitem.remove();
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
                    this.setHealth(getMaxHealth());
                }

            }
        }
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return !(entity instanceof MoCEntityBear) && entity.getBbHeight() <= 1D && entity.getBbWidth() <= 1D;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_BEAR_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        return MoCSoundEvents.ENTITY_BEAR_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_BEAR_AMBIENT;
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    public float getAttackSwing() {
        if (attackCounter == 0)
            return 0;
        return 1.5F + ((float) (attackCounter / 10F) - 10F) * 5F;
    }

    private void startAttack() {
       if (!this.level.isClientSide && this.attackCounter == 0 && getBearState() == 1) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
            this.attackCounter = 1;
        }
    }

    @Override
    public void performAnimation(int i) {
        this.attackCounter = 1;
    }

    protected void eatingAnimal() {
        openMouth();
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
    }

    @Override
    public double getCustomSpeed() {
        if (getBearState() == 2) {
            return 0D;
        }
        return super.getCustomSpeed();
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }
    
    public boolean getIsStanding(){
        return this.standingCounter != 0;
    }
    
    public void setStand(){
        this.standingCounter = 1;
    }
    
//    @Override
//    public boolean processInteract(PlayerEntity player, Hand hand) {
//        final Boolean tameResult = this.processTameInteract(player, hand);
//        if (tameResult != null) {
//            return tameResult;
//        }
//
//        final ItemStack stack = player.getHeldItem(hand);
//        if (!stack.isEmpty() && getIsTamed() && !getIsRideable() && (getEdad() > 80)
//                && (stack.getItem() == Items.SADDLE || stack.getItem() == MoCItems.HORSESADDLE)) {
//            stack.shrink(1);
//            if (stack.isEmpty()) {
//                player.setHeldItem(hand, ItemStack.EMPTY);
//            }
//            setRideable(true);
//            return true;
//        }
//        if (!stack.isEmpty() && getIsTamed() && (MoCTools.isItemEdibleforCarnivores(stack.getItem()))) {
//            stack.shrink(1);
//            if (stack.isEmpty()) {
//                player.setHeldItem(hand, ItemStack.EMPTY);
//            }
//            this.setHealth(getMaxHealth());
//            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
//            setIsHunting(false);
//            setHasEaten(true);
//            return true;
//        }
//        if (!stack.isEmpty() && getIsTamed() && getIsAdult() && !getIsChested() && (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST))) {
//            stack.shrink(1);
//            if (stack.isEmpty()) {
//                player.setHeldItem(hand, ItemStack.EMPTY);
//            }
//            setIsChested(true);
//            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
//            return true;
//        }
//        /*if (getIsChested() && player.isCrouching()) {
//            if (this.localchest == null) {
//                this.localchest = new MoCAnimalChest("BigBearChest", 18);
//            }
//            if (!this.world.isRemote) {
//               player.displayGUIChest(this.localchest);
//            }
//            return true;
//        }*/
//
//        return super.processInteract(player, hand);
//    }

    @Override
    public double getPassengersRidingOffset() {
        double Yfactor = ((0.086D * this.getEdad()) - 2.5D) / 10D;
        return this.getBbHeight() * Yfactor;
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
    public float getSizeFactor() {
        return getEdad() * 0.01F;
    }
    
    @Override
    public void positionRider(Entity passenger) {
        double dist = getSizeFactor() * (0.1D);
        double newPosX = this.getX() + (dist * Math.sin(this.yBodyRot / 57.29578F));
        double newPosZ = this.getZ() - (dist * Math.cos(this.yBodyRot / 57.29578F));
        passenger.setPos(newPosX, this.getY() + getPassengersRidingOffset() + passenger.getMyRidingOffset(), newPosZ);
    }
    
    /*@Override
    public int nameYOffset() {
        if (getIsAdult()) {
            return (-55);
        }
        return (100 / getEdad()) * (-40);
    }*/

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
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putBoolean("Saddle", getIsRideable());
        nbttagcompound.putBoolean("Chested", getIsChested());
        nbttagcompound.putBoolean("Ghost", getIsGhost());
        nbttagcompound.putInt("BearState", getBearState());
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
    
    /*@Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setIsChested(nbttagcompound.getBoolean("Chested"));
        setIsGhost(nbttagcompound.getBoolean("Ghost"));
        setBearState(nbttagcompound.getInt("BearState"));
        if (getIsChested()) {
            ListNBT nbttaglist = nbttagcompound.getList("Items", 10);
            this.localchest = new MoCAnimalChest("BigBearChest", 18);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if ((j >= 0) && j < this.localchest.getSizeInventory()) {
                    this.localchest.setInventorySlotContents(j, new ItemStack(nbttagcompound1));
                }
            }
        }
    }*/
}
