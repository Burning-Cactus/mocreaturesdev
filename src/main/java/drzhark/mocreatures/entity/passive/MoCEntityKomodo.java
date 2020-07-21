package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
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
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class MoCEntityKomodo extends MoCEntityTameableAnimal {

    private int sitCounter;
    public int tailCounter;
    public int tongueCounter;
    public int mouthCounter;
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.<Boolean>createKey(MoCEntityKomodo.class, DataSerializers.BOOLEAN);
    
    public MoCEntityKomodo(EntityType<? extends MoCEntityKomodo> type, World world) {
        super(type, world);
        this.texture = "komododragon.png";
        setTamed(false);
        setAdult(false);
        this.stepHeight = 1.0F;

        if (this.rand.nextInt(6) == 0) {
            setEdad(30 + this.rand.nextInt(40));
        } else {
            setEdad(90 + this.rand.nextInt(20));
        }
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(2, new EntityAIPanicMoC(this, 1.1D));
        this.goalSelector.addGoal(3, new EntityAIFleeFromPlayer(this, 1.1D, 4D));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(7, new EntityAIWanderMoC2(this, 0.9D));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new EntityAIHunt(this, AnimalEntity.class, true));
        this.targetSelector.addGoal(2, new EntityAINearestAttackableTargetMoC(this, PlayerEntity.class, true));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        this.getAttributes().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.18D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(RIDEABLE, Boolean.valueOf(false));; // rideable: 0 nothing, 1 saddle
    }

    @Override
    public void setRideable(boolean flag) {
        this.dataManager.set(RIDEABLE, Boolean.valueOf(flag));
    }

    @Override
    public boolean getIsRideable() {
        return ((Boolean)this.dataManager.get(RIDEABLE)).booleanValue();
    }

    @Override
    public boolean canSpawn(IWorld worldIn, SpawnReason reason) {
        return getCanSpawnHereCreature() && getCanSpawnHereLiving();
    }

    @Override
    protected SoundEvent getDeathSound() {
        openmouth();
        return MoCSoundEvents.ENTITY_SNAKE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openmouth();
        return MoCSoundEvents.ENTITY_SNAKE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openmouth();
        return MoCSoundEvents.ENTITY_SNAKE_AMBIENT;
    }

    @Override
    public int getTalkInterval() {
        return 500;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.sitCounter > 0 && (this.isBeingRidden() || ++this.sitCounter > 150)) {
            this.sitCounter = 0;
        }
        if (!this.world.isRemote) {
            if (!this.isSwimming() && !this.isBeingRidden() && this.sitCounter == 0 && this.rand.nextInt(500) == 0) { //TODO
                sit();
            }

        } else //animation counters, not needed on server
        {
            if (this.tailCounter > 0 && ++this.tailCounter > 60) {
                this.tailCounter = 0;
            }

            if (this.rand.nextInt(100) == 0) {
                this.tailCounter = 1;
            }

            if (this.rand.nextInt(100) == 0) {
                this.tongueCounter = 1;
            }

            if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
                this.mouthCounter = 0;
            }

            if (this.tongueCounter > 0 && ++this.tongueCounter > 20) {
                this.tongueCounter = 0;
            }
        }
    }

    private void openmouth() {
        this.mouthCounter = 1;
    }

    private void sit() {
        this.sitCounter = 1;
        if (!this.world.isRemote) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }
        this.getNavigator().clearPath();
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //sitting animation
        {
            this.sitCounter = 1;
            this.getNavigator().clearPath();
        }
    }

//    @Override TODO
//    protected void dropFewItems(boolean flag, int x) {
//        boolean flag2 = (getEdad() > 90 && this.rand.nextInt(5) == 0);
//
//        if (flag2) {
//            int j = this.rand.nextInt(2) + 1;
//            for (int k = 0; k < j; k++) {
//                entityDropItem(new ItemStack(MoCItems.MOCEGG, 1, 33), 0.0F);
//            }
//        } else {
//
//            entityDropItem(new ItemStack(MoCItems.HIDECROC, 1, 0), 0.0F);
//        }
//    }

    @Override
    public float getSizeFactor() {
        if (!getIsAdult()) {
            return getEdad() * 0.01F;
        }
        return 1.2F;
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsTamed() && (getEdad() > 90 || getIsAdult()) && !getIsRideable()
                && (stack.getItem() == Items.SADDLE || stack.getItem() == MoCItems.HORSESADDLE)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            setRideable(true);
            return true;
        }

        if (getIsRideable() && getIsTamed() && getEdad() > 90 && (!this.isBeingRidden())) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
            }

            return true;
        }

        return super.processInteract(player, hand);
    }

    @Override
    public boolean isMovementCeased() {
        return this.getIsSitting() || (this.isBeingRidden());
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    public int nameYOffset() {
        if (getIsAdult()) {
            return (-50);
        }
        return (-50 + (getEdad() / 2));
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Saddle", getIsRideable());
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
    }

    @Override
    public double getMountedYOffset() {
        double yOff = 0.15F;
        boolean sit = (this.sitCounter != 0);
        if (sit) {
            //yOff = -0.5F;
        }
        if (getIsAdult()) {
            return yOff + (this.getHeight());
        }
        return this.getHeight() * (120 / getEdad());
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();

            if ((entity != null && getIsTamed() && entity instanceof PlayerEntity) || !(entity instanceof LivingEntity)) {
                return false;
            }

            if ((this.isBeingRidden()) && (entity == this.getRidingEntity())) {
                return false;
            }

            if ((entity != this) && (super.shouldAttackPlayers())) {
                setAttackTarget((LivingEntity) entity);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == MoCItems.RAT_RAW || stack.getItem() == MoCItems.TURKEY_RAW);
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public void dropMyStuff() {
        if (!this.world.isRemote) {
            dropArmor();
            MoCTools.dropSaddle(this, this.world);
        }
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 2;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return !(entity instanceof MoCEntityKomodo) && super.canAttackTarget(entity);
    }

    @Override
    public int getMaxEdad() {
        return 120;
    }

    @Override
    public boolean getIsSitting() {
        return this.sitCounter != 0;
    }

    @Override
    public boolean isNotScared() {
        return getEdad() > 70;
    }

    @Override
    protected void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        if (entityIn instanceof PlayerEntity) {
            MoCreatures.poisonPlayer((PlayerEntity) entityIn);
        }
        ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.POISON, 150, 0));
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean isReadyToHunt() {
        return this.isNotScared() && !this.isMovementCeased() && !this.isBeingRidden();
    }

    @Override
    public boolean isAmphibian() {
        return true;
    }

    @Override
    public boolean isSwimming() {
        return this.isInWater();
    }
}
