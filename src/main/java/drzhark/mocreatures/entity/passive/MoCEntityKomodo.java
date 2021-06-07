package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.ai.EntityAIPanicMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.registry.MoCItems;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class MoCEntityKomodo extends MoCEntityTameableAnimal {

    private int sitCounter;
    public int tailCounter;
    public int tongueCounter;
    public int mouthCounter;
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.<Boolean>defineId(MoCEntityKomodo.class, DataSerializers.BOOLEAN);
    
    public MoCEntityKomodo(EntityType<? extends MoCEntityKomodo> type, World world) {
        super(type, world);
        this.texture = "komododragon.png";
        setTamed(false);
        setAdult(false);
        this.maxUpStep = 1.0F;

        if (this.random.nextInt(6) == 0) {
            setEdad(30 + this.random.nextInt(40));
        } else {
            setEdad(90 + this.random.nextInt(20));
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
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 1.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.18D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(RIDEABLE, Boolean.valueOf(false));; // rideable: 0 nothing, 1 saddle
    }

    @Override
    public void setRideable(boolean flag) {
        this.entityData.set(RIDEABLE, Boolean.valueOf(flag));
    }

    @Override
    public boolean getIsRideable() {
        return ((Boolean)this.entityData.get(RIDEABLE)).booleanValue();
    }

    @Override
    public boolean checkSpawnRules(IWorld worldIn, SpawnReason reason) {
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
    public int getAmbientSoundInterval() {
        return 500;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.sitCounter > 0 && (this.isVehicle() || ++this.sitCounter > 150)) {
            this.sitCounter = 0;
        }
        if (!this.level.isClientSide) {
            if (!this.isSwimming() && !this.isVehicle() && this.sitCounter == 0 && this.random.nextInt(500) == 0) { //TODO
                sit();
            }

        } else //animation counters, not needed on server
        {
            if (this.tailCounter > 0 && ++this.tailCounter > 60) {
                this.tailCounter = 0;
            }

            if (this.random.nextInt(100) == 0) {
                this.tailCounter = 1;
            }

            if (this.random.nextInt(100) == 0) {
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
        if (!this.level.isClientSide) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }
        this.getNavigation().stop();
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //sitting animation
        {
            this.sitCounter = 1;
            this.getNavigation().stop();
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

    /*@Override
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
    }*/

    @Override
    public boolean isMovementCeased() {
        return this.getIsSitting() || (this.isVehicle());
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
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putBoolean("Saddle", getIsRideable());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
    }

    @Override
    public double getPassengersRidingOffset() {
        double yOff = 0.15F;
        boolean sit = (this.sitCounter != 0);
        if (sit) {
            //yOff = -0.5F;
        }
        if (getIsAdult()) {
            return yOff + (this.getBbHeight());
        }
        return this.getBbHeight() * (120 / getEdad());
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (super.hurt(damagesource, i)) {
            Entity entity = damagesource.getEntity();

            if ((entity != null && getIsTamed() && entity instanceof PlayerEntity) || !(entity instanceof LivingEntity)) {
                return false;
            }

            if ((this.isVehicle()) && (entity == this.getVehicle())) {
                return false;
            }

            if ((entity != this) && (super.shouldAttackPlayers())) {
                setTarget((LivingEntity) entity);
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
    public boolean isPickable() {
        return !this.isVehicle();
    }

    @Override
    public void dropMyStuff() {
        if (!this.level.isClientSide) {
            dropArmor();
            MoCTools.dropSaddle(this, this.level);
        }
    }

    @Override
    public int getMaxSpawnClusterSize() {
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
    public void doEnchantDamageEffects(LivingEntity entityLivingBaseIn, Entity entityIn) {
        if (entityIn instanceof PlayerEntity) {
            MoCreatures.poisonPlayer((PlayerEntity) entityIn);
        }
        ((LivingEntity) entityIn).addEffect(new EffectInstance(Effects.POISON, 150, 0));
        super.doEnchantDamageEffects(entityLivingBaseIn, entityIn);
    }

    @Override
    public boolean isReadyToHunt() {
        return this.isNotScared() && !this.isMovementCeased() && !this.isVehicle();
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
