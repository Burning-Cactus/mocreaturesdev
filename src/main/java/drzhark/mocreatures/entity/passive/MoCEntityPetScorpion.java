package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIHunt;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.registry.MoCItems;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.world.World;

public class MoCEntityPetScorpion extends MoCEntityTameableAnimal {

    public static final String[] scorpionNames = {"Dirt", "Cave", "Nether", "Frost", "Undead"};
    private boolean isPoisoning;
    private int poisontimer;
    public int mouthCounter;
    public int armCounter;
    private int transformCounter;
    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.createKey(MoCEntityPetScorpion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_BABIES = EntityDataManager.createKey(MoCEntityPetScorpion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_SITTING = EntityDataManager.createKey(MoCEntityPetScorpion.class, DataSerializers.BOOLEAN);

    public MoCEntityPetScorpion(EntityType<? extends MoCEntityPetScorpion> type, World world) {
        super(type, world);
        this.poisontimer = 0;
        setAdult(false);
        setEdad(20);
        setHasBabies(false);
        this.stepHeight = 20.0F;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(4, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(5, new EntityAIFleeFromPlayer(this, 1.2D, 4D));
        this.goalSelector.addGoal(6, new EntityAIFollowOwnerPlayer(this, 1.0D, 2F, 10F));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new EntityAIHunt(this, AnimalEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .func_233815_a_(Attributes.MAX_HEALTH, 40.0D)
                .func_233815_a_(Attributes.MOVEMENT_SPEED, 0.3D)
                .func_233815_a_(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        boolean saddle = getIsRideable();

        if (this.transformCounter != 0) {
            String newText = saddle ? "scorpionundeadsaddle.png" : "scorpionundead.png";
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
                if (!saddle) {
                    return MoCreatures.getTexture("scorpiondirt.png");
                }
                return MoCreatures.getTexture("scorpiondirtsaddle.png");
            case 2:
                if (!saddle) {
                    return MoCreatures.getTexture("scorpioncave.png");
                }
                return MoCreatures.getTexture("scorpioncavesaddle.png");
            case 3:
                if (!saddle) {
                    return MoCreatures.getTexture("scorpionnether.png");
                }
                return MoCreatures.getTexture("scorpionnethersaddle.png");
            case 4:
                if (!saddle) {
                    return MoCreatures.getTexture("scorpionfrost.png");
                }
                return MoCreatures.getTexture("scorpionfrostsaddle.png");
            case 5:
                if (!saddle) {
                    return MoCreatures.getTexture("scorpionundead.png");
                }
                return MoCreatures.getTexture("scorpionundeadsaddle.png");
            default:
                return MoCreatures.getTexture("scorpiondirt.png");
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(HAS_BABIES, Boolean.FALSE);
        this.dataManager.register(IS_SITTING, Boolean.FALSE);
        this.dataManager.register(RIDEABLE, Boolean.FALSE);
    }
    
    @Override
    public void setRideable(boolean flag) {
        this.dataManager.set(RIDEABLE, flag);
    }

    @Override
    public boolean getIsRideable() {
        return this.dataManager.get(RIDEABLE);
    }
    
    public boolean getHasBabies() {
        return getIsAdult() && this.dataManager.get(HAS_BABIES);
    }

    public boolean getIsPoisoning() {
        return this.isPoisoning;
    }

    @Override
    public boolean getIsSitting() {
        return this.dataManager.get(IS_SITTING);
    }

    public void setSitting(boolean flag) {
        this.dataManager.set(IS_SITTING, flag);
    }

    public void setHasBabies(boolean flag) {
        this.dataManager.set(HAS_BABIES, flag);
    }

    public void setPoisoning(boolean flag) {
//        if (flag && !this.world.isRemote) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//        }
        this.isPoisoning = flag;
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) //tail animation
        {
            setPoisoning(true);
        } else if (animationType == 1) //arm swinging
        {
            this.armCounter = 1;
            //swingArm();
        } else if (animationType == 3) //movement of mouth
        {
            this.mouthCounter = 1;
        } else if (animationType == 5) //transforming into undead scorpion
        {
            this.transformCounter = 1;
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
    public void livingTick() {
        if (!this.onGround && (this.getRidingEntity() != null)) {
            this.rotationYaw = this.getRidingEntity().rotationYaw;
        }

        if (this.mouthCounter != 0 && this.mouthCounter++ > 50) {
            this.mouthCounter = 0;
        }

        if (!this.world.isRemote && (this.armCounter == 10 || this.armCounter == 40)) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SCORPION_CLAW);
        }

        if (this.armCounter != 0 && this.armCounter++ > 24) {
            this.armCounter = 0;
        }

        if (getIsPoisoning()) {
            this.poisontimer++;
            if (this.poisontimer == 1) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SCORPION_STING);
            }
            if (this.poisontimer > 50) {
                this.poisontimer = 0;
                setPoisoning(false);
            }
        }

        if (this.transformCounter > 0) {
            if (this.transformCounter == 40) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_TRANSFORM);
            }

            if (++this.transformCounter > 100) {
                this.transformCounter = 0;
                setType(5);
                selectType();
            }
        }
        super.livingTick();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();
            if (!(entity instanceof LivingEntity) || entity instanceof PlayerEntity && getIsTamed()) {
                return false;
            }

            if ((entity != null) && (entity != this) && (super.shouldAttackPlayers()) && getIsAdult()) {
                setAttackTarget((LivingEntity) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void applyEnchantments(LivingEntity entityLivingBaseIn, Entity entityIn) {
        boolean flag = (entityIn instanceof PlayerEntity);
        if (!getIsPoisoning() && this.rand.nextInt(5) == 0 && entityIn instanceof LivingEntity) {
            setPoisoning(true);
            if (getSubType() <= 2)// regular scorpions
            {
                if (flag) {
                    MoCreatures.poisonPlayer((PlayerEntity) entityIn);
                }
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.POISON, 70, 0));
            } else if (getSubType() == 4)// blue scorpions
            {
                if (flag) {
                    MoCreatures.freezePlayer((PlayerEntity) entityIn);
                }
                ((LivingEntity) entityIn).addPotionEffect(new EffectInstance(Effects.SLOWNESS, 70, 0));

            } else if (getSubType() == 3)// red scorpions
            {
                /*if (flag && !this.world.isRemote && !this.world.dimension.doesWaterVaporize()) {
                    MoCreatures.burnPlayer((PlayerEntity) entityIn);
                    ((LivingEntity) entityIn).setFire(15);
                }*/
            }
        } else {
            swingArm();
        }
        super.applyEnchantments(entityLivingBaseIn, entityIn);
    }

    public void swingArm() {
//        if (!this.world.isRemote) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//        }
    }

    public boolean swingingTail() {
        return getIsPoisoning() && this.poisontimer < 15;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_SCORPION_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_SCORPION_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
//        if (!this.world.isRemote) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//        }

        return MoCSoundEvents.ENTITY_SCORPION_AMBIENT;
    }

//    @Override
//    protected Item getDropItem() {
//        if (!getIsAdult()) {
//            return Items.STRING;
//        }
//
//        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);
//
//        switch (getSubType()) {
//            case 1:
//                if (flag) {
//                    return MoCItems.SCORPSTINGDIRT;
//                }
//                return MoCItems.CHITIN;
//            case 2:
//                if (flag) {
//                    return MoCItems.SCORPSTINGCAVE;
//                }
//                return MoCItems.CHITINCAVE;
//            case 3:
//                if (flag) {
//                    return MoCItems.SCORPSTINGNETHER;
//                }
//                return MoCItems.CHITINNETHER;
//            case 4:
//                if (flag) {
//                    return MoCItems.SCORPSTINGFROST;
//                }
//                return MoCItems.CHITINFROST;
//            case 5:
//                return Items.ROTTEN_FLESH;
//
//            default:
//                return Items.STRING;
//        }
//    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getIsAdult() && !getIsRideable()
                && (stack.getItem() == Items.SADDLE || stack.getItem() == MoCItems.HORSESADDLE)) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            setRideable(true);
            return true;
        }

        if (!stack.isEmpty() && (stack.getItem() == MoCItems.WHIP) && getIsTamed() && (!this.isBeingRidden())) {
            setSitting(!getIsSitting());
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && stack.getItem() == MoCItems.ESSENCEUNDEAD) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            transform(5);
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && stack.getItem() == MoCItems.ESSENCEDARKNESS) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            this.setHealth(getMaxHealth());
            if (!this.world.isRemote) {
                int i = getSubType() + 40;
                MoCEntityEgg entityegg = new MoCEntityEgg(MoCEntities.EGG, this.world, i);
                entityegg.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
                player.world.addEntity(entityegg);
                entityegg.getMotion().add((this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.3F, this.world.rand.nextFloat() * 0.05F, (this.world.rand.nextFloat() - this.world.rand.nextFloat()) * 0.3F);
            }
            return true;
        }

        if (this.getRidingEntity() == null && this.getEdad() < 60 && !getIsAdult()) {
            if (this.startRiding(player)) {
                this.rotationYaw = player.rotationYaw;
                if (!this.world.isRemote && !getIsTamed()) {
                    MoCTools.tameWithName(player, this);
                }
            }

            return true;
        } else if (this.getRidingEntity() != null) {
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            this.stopRiding();
            this.setMotion(player.getMotion().x * 5D, (player.getMotion().y / 2D) + 0.5D, player.getMotion().z * 5D);
            return true;
        }

        if (getIsRideable() && getIsTamed() && getIsAdult() && (!this.isBeingRidden())) {
            player.rotationYaw = this.rotationYaw;
            player.rotationPitch = this.rotationPitch;
            if (!this.world.isRemote) {
                player.startRiding(this);
            }

            return true;
        }

        return super.processInteract(player, hand);
    }*/

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setHasBabies(nbttagcompound.getBoolean("Babies"));
        setRideable(nbttagcompound.getBoolean("Saddled"));
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Babies", getHasBabies());
        nbttagcompound.putBoolean("Saddled", getIsRideable());
    }

    @Override
    public int nameYOffset() {
        int n = (int) (1 - (getEdad() * 0.8));
        if (n < -60) {
            n = -60;
        }
        if (getIsAdult()) {
            n = -60;
        }
        if (getIsSitting()) {
            n = (int) (n * 0.8);
        }
        return n;
    }

    @Override
    protected boolean isMyHealFood(ItemStack itemstack) {
        return (itemstack.getItem() == MoCItems.RAT_RAW || itemstack.getItem() == MoCItems.RAT_COOKED);
    }

    @Override
    public int getTalkInterval() {
        return 300;
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    public boolean isMovementCeased() {
        return (this.isBeingRidden()) || getIsSitting();
    }

    @Override
    public void dropMyStuff() {
        MoCTools.dropSaddle(this, this.world);
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0.2F;
    }

    @Override
    public int getMaxEdad() {
        return 120;
    }

    @Override
    public double getMountedYOffset() {
        return (this.getHeight() * 0.75D) - 0.15D;
    }

    @Override
    public double getYOffset() {
        if (this.getRidingEntity() instanceof PlayerEntity /*&& this.getRidingEntity() == MoCreatures.proxy.getPlayer()*/ && this.world.isRemote) {
            return 0.1F;
        }

        if ((this.getRidingEntity() instanceof PlayerEntity) && this.world.isRemote) {
            return (super.getYOffset() + 0.1F);
        } else {
            return super.getYOffset();
        }
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double dist = (0.2D);
        double newPosX = this.getPosX() + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.getPosZ() - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        passenger.setPosition(newPosX, this.getPosY() + getMountedYOffset() + passenger.getYOffset(), newPosZ);
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    public void transform(int tType) {
//        if (!this.world.isRemote) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), tType),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//        }
        this.transformCounter = 1;
    }

    @Override
    public boolean isReadyToHunt() {
        return this.getIsAdult() && !this.isMovementCeased();
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return !(entity instanceof MoCEntityFox) && entity.getHeight() <= 1D && entity.getWidth() <= 1D;
    }

}
