package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import drzhark.mocreatures.entity.ai.PathNavigateFlyerMoC;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

public abstract class MoCEntityMob extends MonsterEntity implements IMoCEntity//, IEntityAdditionalSpawnData
{

    protected boolean divePending;
    protected int maxHealth;
    protected float moveSpeed;
    protected String texture;
//    protected PathNavigator navigatorWater;
    protected PathNavigator navigatorFlyer;
    protected MoCAlternateWanderGoal wander;

    protected static final DataParameter<Boolean> ADULT = EntityDataManager.defineId(CreatureEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> TYPE = EntityDataManager.defineId(CreatureEntity.class, DataSerializers.INT);
    protected static final DataParameter<Integer> AGE = EntityDataManager.defineId(CreatureEntity.class, DataSerializers.INT);
    protected static final DataParameter<String> NAME_STR = EntityDataManager.defineId(CreatureEntity.class, DataSerializers.STRING);
    
    public MoCEntityMob(EntityType<? extends MoCEntityMob> type, World world) {
        super(type, world);
        this.texture = "blank.jpg";
        this.moveControl = new EntityAIMoverHelperMoC(this);
//        this.navigatorWater = new SwimmerPathNavigator(this, world);
        this.navigatorFlyer = new PathNavigateFlyerMoC(this, world);
//        this.goalSelector.addGoal(4, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MonsterEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ATTACK_DAMAGE, 2D)
                .add(Attributes.MOVEMENT_SPEED, 0.7F)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData par1EntityLivingData, CompoundNBT dataTag) {
        selectType();
        return super.finalizeSpawn(worldIn, difficulty, reason, par1EntityLivingData, dataTag);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture(this.texture);
    }

    /**
     * Put your code to choose a texture / the mob type in here. Will be called
     * by default MocEntity constructors.
     */
    @Override
    public void selectType() {
        setType(1);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ADULT, false);
        this.entityData.define(TYPE, 0);
        this.entityData.define(AGE, 45);
        this.entityData.define(NAME_STR, "");
    }

    @Override
    public void setType(int i) {
        this.entityData.set(TYPE, i);
    }

    @Override
    public int getSubType() {
        return this.entityData.get(TYPE);
    }

    @Override
    public EntityType<?> getType() {
        //return ((Integer)this.dataManager.get(TYPE)).intValue();
        return super.getType();
    }

    @Override
    public boolean getIsAdult() {
        return this.entityData.get(ADULT);
    }

    @Override
    public void setAdult(boolean flag) {
        this.entityData.set(ADULT, flag);
    }
    
    @Override
    public boolean getIsTamed() {
        return false;
    }

    @Override
    public String getPetName() {
        return this.entityData.get(NAME_STR).toString();
    }

    @Override
    public int getEdad() {
        return this.entityData.get(AGE);
    }

    @Nullable
    public UUID getOwnerId()
    {
        return null;
    }

    public void setOwnerUniqueId(@Nullable UUID uniqueId) {
    }

    @Override
    public int getOwnerPetId() {
        return 0;
    }

    @Override
    public void setOwnerPetId(int petId) {
    }

    @Override
    public void setEdad(int i) {
        this.entityData.set(AGE, i);
    }

    @Override
    public void setPetName(String name) {
        this.entityData.set(NAME_STR, name);
    }

    public boolean getCanSpawnHereLiving() {
        return this.level.isUnobstructed(this)
                && this.level.getBlockCollisions(this, this.getBoundingBox()).count() == 0
                && !this.level.containsAnyLiquid(this.getBoundingBox());
    }

    public boolean getCanSpawnHereCreature() {
        int i = MathHelper.floor(this.getX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getZ());
        return getWalkTargetValue(new BlockPos(i, j, k)) >= 0.0F;
    }

//    @Override
//    public boolean getCanSpawnHere() {
//        return (MoCreatures.entityMap.get(this.getClass()).getFrequency() > 0 && super.getCanSpawnHere());
//    }

//    public boolean getCanSpawnHereMob() {
//        int i = MathHelper.floor(this.getPosX());
//        int j = MathHelper.floor(getBoundingBox().minY);
//        int k = MathHelper.floor(this.getPosZ());
//        BlockPos pos = new BlockPos(i, j, k);
//        if (this.world.getLightFor(LightType.SKY, pos) > this.rand.nextInt(32)) {
//            return false;
//        }
//        int l = this.world.getLightFromNeighbors(pos);
//        if (this.world.isThundering()) {
//            int i1 = this.world.getSkylightSubtracted();
//            this.world.setSkylightSubtracted(10);
//            l = this.world.getLightFromNeighbors(pos);
//            this.world.setSkylightSubtracted(i1);
//        }
//        return l <= this.rand.nextInt(8);
//    }

    // TODO move this to a class accessible by MocEntityMob and MoCentityAnimals
    protected LivingEntity getClosestEntityLiving(Entity entity, double d) {
        double d1 = -1D;
        LivingEntity entityliving = null;
        List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);

            if (entitiesToIgnore(entity1)) {
                continue;
            }
            double d2 = entity1.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((LivingEntity) entity1).canSee(entity)) {
                d1 = d2;
                entityliving = (LivingEntity) entity1;
            }
        }

        return entityliving;
    }

    //TODO REMOVE
    public boolean entitiesToIgnore(Entity entity) {
        return ((!(entity instanceof LivingEntity)) || (entity instanceof MobEntity) || (entity instanceof MoCEntityEgg)
                || (entity instanceof PlayerEntity && this.getIsTamed()) || (entity instanceof MoCEntityKittyBed)
                || (entity instanceof MoCEntityLitterBox)
                || (this.getIsTamed() && (entity instanceof MoCEntityAnimal && ((MoCEntityAnimal) entity).getIsTamed()))
                || ((entity instanceof WolfEntity) && !(MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.attackWolves.get())) || ((entity instanceof MoCEntityHorse) && !(MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.attackHorses.get())));
    }

    @Override
    public boolean checkSpawningBiome() {
        return true;
    }

    @Override
    public void aiStep() {
        if (!this.level.isClientSide) {

            /*if (forceUpdates() && this.rand.nextInt(1000) == 0) {
                MoCTools.forceDataSync(this);
            }*/

//            if (getIsTamed() && this.rand.nextInt(200) == 0) { TODO: Disabling the information packets for now, will fix them later.
//                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHealth(this.getEntityId(), this.getHealth()), new PacketDistributor.TargetPoint(
//                        this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
//            }

            if (this.isHarmedByDaylight()) {
                if (this.level.isDay()) {
                    float var1 = this.getBrightness();
                    if (var1 > 0.5F
                            && this.level.canSeeSkyFromBelowWater(new BlockPos(MathHelper.floor(this.getX()), MathHelper.floor(this.getY()),
                                    MathHelper.floor(this.getZ()))) && this.random.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F) {
                        this.setSecondsOnFire(8);
                    }
                }
            }
            if (getEdad() == 0) setEdad(getMaxEdad() - 10); //fixes tiny creatures spawned by error
            if (!getIsAdult() && (this.random.nextInt(300) == 0)) {
                setEdad(getEdad() + 1);
                if (getEdad() >= getMaxEdad()) {
                    setAdult(true);
                }
            }

            if (getIsFlying() && this.getNavigation().isDone() && !isMovementCeased() && this.getTarget() == null && this.random.nextInt(20) == 0) {
//                this.wander.makeUpdate(); TODO: Make wandering code work
            }
        }

        this.getNavigation().tick();
        super.aiStep();
    }

    protected int getMaxEdad() {
        return 100;
    }

    protected boolean isHarmedByDaylight() {
        return false;
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (!this.level.isClientSide && getIsTamed()) {
//            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHealth(this.getEntityId(), this.getHealth()), new TargetPoint(
//                    this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }
        return super.hurt(damagesource, i);
    }

    /**
     * Boolean used to select pathfinding behavior
     *
     * @return
     */
    public boolean isFlyer() {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        //nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.putBoolean("Adult", getIsAdult());
        nbttagcompound.putInt("Edad", getEdad());
        nbttagcompound.putString("Name", getPetName());
        nbttagcompound.putInt("TypeInt", getSubType());

    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        //nbttagcompound = MoCTools.getEntityData(this);
        setAdult(nbttagcompound.getBoolean("Adult"));
        setEdad(nbttagcompound.getInt("Edad"));
        setPetName(nbttagcompound.getString("Name"));
        setType(nbttagcompound.getInt("TypeInt"));

    }

//    @Override
//    public void fall(float f, float f1) {
//        if (!isFlyer()) {
//            super.fall(f, f1);
//        }
//    }

    @Override
    public boolean onClimbable() {
        if (isFlyer()) {
            return false;
        } else {
            return super.onClimbable();
        }
    }

    @Override
    public void travel(Vector3d motion) {
        if (!isFlyer()) {
            super.travel(motion);
            return;
        }
        this.moveEntityWithHeadingFlyer((float)motion.x, (float)motion.y, (float)motion.z);
    }

    public void moveEntityWithHeadingFlyer(float strafe, float vertical, float forward) {
        if (this.isEffectiveAi()) {

            this.moveRelative( 0.1F, new Vector3d(strafe, vertical, forward));
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.getDeltaMovement().scale(0.8999999761581421D);
        } else {
            super.travel(new Vector3d(strafe, vertical, forward));
        }
    }



    /**
     * Used to synchronize the attack animation between server and client
     *
     * @param attackType
     */
    @Override
    public void performAnimation(int attackType) {
    }

    @Override
    public int nameYOffset() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean renderName() {
        return MoCConfig.CLIENT_CONFIG.displayPetName.get() //TODO: Fix name rendering
                && (getPetName() != null && !getPetName().equals("") && (!this.isVehicle()) && (this.getVehicle() == null));
//        return false;
    }

    /*protected Vec3d findPossibleShelter() {
        Random var1 = this.getRNG();

        for (int var2 = 0; var2 < 10; ++var2) {
            int var3 = MathHelper.floor(this.posX + var1.nextInt(20) - 10.0D);
            int var4 = MathHelper.floor(this.getEntityBoundingBox().minY + var1.nextInt(6) - 3.0D);
            int var5 = MathHelper.floor(this.posZ + var1.nextInt(20) - 10.0D);
            BlockPos pos = new BlockPos(var3, var4, var5);

            if (!this.world.canBlockSeeSky(pos) && this.getBlockPathWeight(pos) < 0.0F) {
                return new Vec3d(var3, var4, var5);
            }
        }

        return null;
    }*/

    @Override
    public void makeEntityJump() {
        //TODO
    }

    @Override
    public void makeEntityDive() {
        this.divePending = true;
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        return !getIsTamed();
    }

    @Override
    public void remove() {
        // Server check required to prevent tamed entities from being duplicated on client-side
        if (!this.level.isClientSide && (getIsTamed()) && (getHealth() > 0)) {
            return;
        }
        super.remove();
    }

    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0F;
    }

    /*protected void getPathOrWalkableBlock(Entity entity, float f) {
        Path pathentity = this.navigator.getPathToPos(entity.getPosition());
        if ((pathentity == null) && (f > 12F)) {
            int i = MathHelper.floor(entity.posX) - 2;
            int j = MathHelper.floor(entity.posZ) - 2;
            int k = MathHelper.floor(entity.getEntityBoundingBox().minY);
            for (int l = 0; l <= 4; l++) {
                for (int i1 = 0; i1 <= 4; i1++) {
                    if (((l < 1) || (i1 < 1) || (l > 3) || (i1 > 3))
                            && this.world.getBlockState(new BlockPos(i + l, k - 1, j + i1)).isNormalCube()
                            && !this.world.getBlockState(new BlockPos(i + l, k, j + i1)).isNormalCube()
                            && !this.world.getBlockState(new BlockPos(i + l, k + 1, j + i1)).isNormalCube()) {
                        setLocationAndAngles((i + l) + 0.5F, k, (j + i1) + 0.5F, this.rotationYaw, this.rotationPitch);
                        return;
                    }
                }

            }
        } else {
            this.navigator.setPath(pathentity, 16F);
        }
    }*/

    @Override
    public void setArmorType(int i) {
    }

    public int getArmorType() {
        return 0;
    }

    @Override
    public float pitchRotationOffset() {
        return 0F;
    }

    @Override
    public float rollRotationOffset() {
        return 0F;
    }

    @Override
    public float yawRotationOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedZOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedXOffset() {
        return 0F;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean getIsSitting() {
        return false;
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Override
    public boolean isMovementCeased() {
        return false;
    }

    @Override
    public boolean shouldAttackPlayers() {
        return this.level.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    public double getDivingDepth() {
        return 0;
    }

    @Override
    public boolean isDiving() {
        return false;
    }

    @Override
    public void forceEntityJump() {
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        boolean flag =
                entityIn.hurt(DamageSource.mobAttack(this), ((int) this.getAttribute(Attributes.ATTACK_DAMAGE)
                        .getValue()));
        if (flag) {
            this.doEnchantDamageEffects(this, entityIn);
        }

        return flag;
    }

    @Override
    public int maxFlyingHeight() {
        return 5;
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    @Override
    public PathNavigator getNavigation() {
//        if (this.isInWater() && this.isAmphibian()) {
//            return this.navigatorWater;
//        }
        if (this.isFlyer()) {
            return this.navigatorFlyer;
        }
        return this.navigation;
    }

    @Override
    public boolean getIsFlying() {
        return isFlyer();
    }

//    @Override
//    public String getClazzString() {
//        return EntityList.getEntityString(this);
//    }

    @Override
    public boolean getIsGhost() {
        return false;
    }
}
