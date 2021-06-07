package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromPlayer;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.passive.MoCEntityPetScorpion;
import drzhark.mocreatures.registry.MoCEntities;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

public class MoCEntityScorpion extends MoCEntityMob {

    private boolean isPoisoning;
    private int poisontimer;
    public int mouthCounter;
    public int armCounter;

    private static final DataParameter<Boolean> IS_PICKED = EntityDataManager.defineId(MoCEntityScorpion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_BABIES = EntityDataManager.defineId(MoCEntityScorpion.class, DataSerializers.BOOLEAN);
    
    public MoCEntityScorpion(EntityType<? extends MoCEntityScorpion> type, World world) {
        super(type, world);
        this.poisontimer = 0;
        setAdult(true);
        setEdad(20);

        if (!this.level.isClientSide) {
            if (this.random.nextInt(4) == 0) {
                setHasBabies(true);
            } else {
                setHasBabies(false);
            }
        }
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(2, new RestrictSunGoal(this));
        this.goalSelector.addGoal(7, new FleeSunGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new EntityAIFleeFromPlayer(this, 1.2D, 4D));
        this.goalSelector.addGoal(6, new LeapAtTargetGoal(this, 0.4F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityMob.registerAttributes()
                .add(Attributes.MAX_HEALTH, 18.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getSubType() == 0) {
            setType(1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("scorpiondirt.png");
            case 2:
                return MoCreatures.getTexture("scorpioncave.png");
            case 3:
                return MoCreatures.getTexture("scorpionnether.png");
            case 4:
                return MoCreatures.getTexture("scorpionfrost.png");
            default:
                return MoCreatures.getTexture("scorpiondirt.png");
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_PICKED, Boolean.FALSE);
        this.entityData.define(HAS_BABIES, Boolean.FALSE);
    }

    public boolean getHasBabies() {
        return this.entityData.get(HAS_BABIES);
    }

    public boolean getIsPicked() {
        return this.entityData.get(IS_PICKED);
    }

    public boolean getIsPoisoning() {
        return this.isPoisoning;
    }

    public void setHasBabies(boolean flag) {
        this.entityData.set(HAS_BABIES, flag);
    }

    public void setPicked(boolean flag) {
        this.entityData.set(IS_PICKED, flag);
    }

    public void setPoisoning(boolean flag) {
        if (flag && !this.level.isClientSide) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
//                    new TargetPoint(this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }
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
        }
    }

    @Override
    public boolean onClimbable() {
        return this.horizontalCollision;
    }

    public boolean climbing() {
        return !this.onGround && onClimbable();
    }

    @Override
    public void aiStep() {

        if (!this.onGround && (this.getVehicle() != null)) {
            this.yRot = this.getVehicle().yRot;
        }

        if (this.mouthCounter != 0 && this.mouthCounter++ > 50) {
            this.mouthCounter = 0;
        }

        if (!this.level.isClientSide && (this.armCounter == 10 || this.armCounter == 40)) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SCORPION_CLAW);
        }

        if (this.armCounter != 0 && this.armCounter++ > 24) {
            this.armCounter = 0;
        }

        if (!this.level.isClientSide && !this.isVehicle() && this.getIsAdult() && !this.getHasBabies() && this.random.nextInt(100) == 0) {
            MoCTools.findMobRider(this);
            /*List list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(4D, 2D, 4D));
            for (int i = 0; i < list.size(); i++) {
                Entity entity = (Entity) list.get(i);
                if (!(entity instanceof EntityMob)) {
                    continue;
                }
                EntityMob entitymob = (EntityMob) entity;
                if (entitymob.getRidingEntity() == null
                        && (entitymob instanceof EntitySkeleton || entitymob instanceof EntityZombie || entitymob instanceof MoCEntitySilverSkeleton)) {
                    entitymob.mountEntity(this);
                    break;
                }
            }*/
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
        super.aiStep();
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (super.hurt(damagesource, i)) {
            Entity entity = damagesource.getEntity();

            if ((entity != this && entity instanceof LivingEntity) && this.shouldAttackPlayers() && getIsAdult()) {
                setTarget((LivingEntity) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean entitiesToIgnore(Entity entity) {
        return ((super.entitiesToIgnore(entity)) || (this.getIsTamed() && entity instanceof MoCEntityScorpion && ((MoCEntityScorpion) entity)
                .getIsTamed()));
    }

    @Override
    public void doEnchantDamageEffects(LivingEntity entityLivingBaseIn, Entity entityIn) {
        boolean flag = (entityIn instanceof PlayerEntity);
        if (!getIsPoisoning() && this.random.nextInt(5) == 0 && entityIn instanceof LivingEntity) {
            setPoisoning(true);
            if (getSubType() <= 2)// regular scorpions
            {
                if (flag) {
                    MoCreatures.poisonPlayer((PlayerEntity) entityIn);
                }
                ((LivingEntity) entityIn).addEffect(new EffectInstance(Effects.POISON, 70, 0));
            } else if (getSubType() == 4)// blue scorpions
            {
                if (flag) {
                    MoCreatures.freezePlayer((PlayerEntity) entityIn);
                }
                ((LivingEntity) entityIn).addEffect(new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 70, 0));

            } else if (getSubType() == 3)// red scorpions
            {
                if (!this.level.isClientSide && flag /*&& !this.world.dimension.doesWaterVaporize()*/) {
                    MoCreatures.burnPlayer((PlayerEntity) entityIn);
                    ((LivingEntity) entityIn).setSecondsOnFire(15);
                }
            }
        } else {
            swingArm();
        }
        super.doEnchantDamageEffects(entityLivingBaseIn, entityIn);
    }

    public void swingArm() {
//        if (!this.world.isRemote) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
//                    new TargetPoint(this.world.dimension.getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//        }
    }

    @Override
    public void tick() {
        super.tick();
    }

    public boolean swingingTail() {
        return getIsPoisoning() && this.poisontimer < 15;
    }

    @Override
    public void die(DamageSource damagesource) {
        super.die(damagesource);
        if (!this.level.isClientSide && getIsAdult() && getHasBabies()) {
            int k = this.random.nextInt(5);
            for (int i = 0; i < k; i++) {
                MoCEntityPetScorpion entityscorpy = new MoCEntityPetScorpion(MoCEntities.PET_SCORPION, this.level);
                entityscorpy.setPos(this.getX(), this.getY(), this.getZ());
                entityscorpy.setAdult(false);
                entityscorpy.setEdad(20);
                entityscorpy.setType(getSubType());
                this.level.addFreshEntity(entityscorpy);
                MoCTools.playCustomSound(entityscorpy, SoundEvents.CHICKEN_EGG);
            }
        }
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
//            default:
//                return Items.STRING;
//        }
//    }
//      TODO: Loot tables are done in json now.
//    @Override
//    protected void dropFewItems(boolean flag, int x) {
//        if (!flag) {
//            return;
//        }
//        Item item = this.getDropItem();
//
//        if (item != null) {
//            if (this.rand.nextInt(3) == 0) {
//                this.dropItem(item, 1);
//            }
//        }
//
//    }

//    @Override
//    public boolean canSpawn(IWorld worldIn, SpawnReason reason) {
//        return (/*isValidLightLevel() &&*/ MoCreatures.entityMap.get(this.getType()).getFrequency() > 0) && getCanSpawnHereLiving()
//                && getCanSpawnHereCreature();
//    }

    @Override
    public boolean checkSpawningBiome() {
        if (/*this.world.dimension.doesWaterVaporize()*/false) {
            setType(3);
//            this.isImmuneToFire = true;
            return true;
        }

        int i = MathHelper.floor(this.getX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getZ());
        BlockPos pos = new BlockPos(i, j, k);

        Biome currentbiome = MoCTools.Biomekind(this.level, pos);

        /*if (BiomeDictionary.hasType(currentbiome, Type.SNOWY)) {
            setType(4);
        } else if (!this.world.canBlockSeeSky(pos) && (this.getPosY() < 50D)) {
            setType(2);
            return true;
        }*/

        return true;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        setHasBabies(nbttagcompound.getBoolean("Babies"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putBoolean("Babies", getHasBabies());
    }

    @Override
    public int getAmbientSoundInterval() {
        return 300;
    }

    /**
     * Get this Entity's EnumCreatureAttribute
     */
    @Override
    public CreatureAttribute getMobType() {
        return CreatureAttribute.ARTHROPOD;
    }

    @Override
    public float getAdjustedYOffset() {
        return 30F;
    }

    @Override
    protected int getMaxEdad() {
        return 120;
    }

    @Override
    public boolean isNotScared() {
        return getIsAdult() || this.getEdad() > 70;
    }

    @Override
    public double getPassengersRidingOffset() {
        return (this.getBbHeight() * 0.75D) - 0.15D;
    }

    @Override
    public void positionRider(Entity passenger) {
        double dist = (0.2D);
        double newPosX = this.getX() + (dist * Math.sin(this.yBodyRot / 57.29578F));
        double newPosZ = this.getZ() - (dist * Math.cos(this.yBodyRot / 57.29578F));
        passenger.setPos(newPosX, this.getY() + getPassengersRidingOffset() + passenger.getMyRidingOffset(), newPosZ);
        passenger.yRot = this.yRot;
    }
}
