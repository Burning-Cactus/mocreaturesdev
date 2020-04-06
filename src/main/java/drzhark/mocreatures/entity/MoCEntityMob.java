package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.ai.PathNavigateFlyerMoC;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageHealth;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.network.PacketDistributor;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

public abstract class MoCEntityMob extends CreatureEntity implements IMoCEntity//, IEntityAdditionalSpawnData
{

    protected boolean divePending;
    protected int maxHealth;
    protected float moveSpeed;
    protected String texture;
    protected PathNavigator navigatorWater;
    protected PathNavigator navigatorFlyer;
    protected EntityAIWanderMoC2 wander;

    protected static final DataParameter<Boolean> ADULT = EntityDataManager.<Boolean>createKey(CreatureEntity.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(CreatureEntity.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> AGE = EntityDataManager.<Integer>createKey(CreatureEntity.class, DataSerializers.VARINT);
    protected static final DataParameter<String> NAME_STR = EntityDataManager.<String>createKey(CreatureEntity.class, DataSerializers.STRING);
    
    public MoCEntityMob(World world) {
        super(new EntityType<? extends MoCEntityMob>, world);
        this.texture = "blank.jpg";
        this.moveController = new EntityAIMoverHelperMoC(this);
        this.navigatorWater = new SwimmerPathNavigator(this, world);
        this.navigatorFlyer = new PathNavigateFlyerMoC(this, world);
        this.goalSelector.addGoal(4, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(getMoveSpeed());
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(getAttackStrenght());
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData par1EntityLivingData, CompoundNBT dataTag) {
        selectType();
        return super.onInitialSpawn(worldIn, difficulty, reason, par1EntityLivingData, dataTag);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture(this.texture);
    }

    protected double getAttackStrenght() {
        return 2D;
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
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ADULT, Boolean.valueOf(false));
        this.dataManager.register(TYPE, Integer.valueOf(0));
        this.dataManager.register(AGE, Integer.valueOf(45));
        this.dataManager.register(NAME_STR, "");
    }

    @Override
    public void setType(int i) {
        this.dataManager.set(TYPE, Integer.valueOf(i));
    }

    @Override
    public EntityType getType() {
        //return ((Integer)this.dataManager.get(TYPE)).intValue();
        return super.getType();
    }

    @Override
    public boolean getIsAdult() {
        return ((Boolean)this.dataManager.get(ADULT)).booleanValue();
    }

    @Override
    public void setAdult(boolean flag) {
        this.dataManager.set(ADULT, Boolean.valueOf(flag));
    }
    
    @Override
    public boolean getIsTamed() {
        return false;
    }

    @Override
    public String getPetName() {
        return ((String)this.dataManager.get(NAME_STR)).toString();
    }

    @Override
    public int getEdad() {
        return ((Integer)this.dataManager.get(AGE)).intValue();
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
        this.dataManager.set(AGE, Integer.valueOf(i));
    }

    @Override
    public void setPetName(String name) {
        this.dataManager.set(NAME_STR, String.valueOf(name));
    }

    public boolean getCanSpawnHereLiving() {
        return this.world.checkNoEntityCollision(this.getBoundingBox())
                && this.world.getCollisionShapes(this, this.getBoundingBox()).count() == 0
                && !this.world.containsAnyLiquid(this.getBoundingBox());
    }

    public boolean getCanSpawnHereCreature() {
        int i = MathHelper.floor(this.getPosX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getPosZ());
        return getBlockPathWeight(new BlockPos(i, j, k)) >= 0.0F;
    }

    @Override
    public boolean getCanSpawnHere() {
        return (MoCreatures.entityMap.get(this.getClass()).getFrequency() > 0 && super.getCanSpawnHere());
    }

    public boolean getCanSpawnHereMob() {
        int i = MathHelper.floor(this.getPosX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getPosZ());
        BlockPos pos = new BlockPos(i, j, k);
        if (this.world.getLightFor(EnumSkyBlock.SKY, pos) > this.rand.nextInt(32)) {
            return false;
        }
        int l = this.world.getLightFromNeighbors(pos);
        if (this.world.isThundering()) {
            int i1 = this.world.getSkylightSubtracted();
            this.world.setSkylightSubtracted(10);
            l = this.world.getLightFromNeighbors(pos);
            this.world.setSkylightSubtracted(i1);
        }
        return l <= this.rand.nextInt(8);
    }

    // TODO move this to a class accessible by MocEntityMob and MoCentityAnimals
    protected LivingEntity getClosestEntityLiving(Entity entity, double d) {
        double d1 = -1D;
        LivingEntity entityliving = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);

            if (entitiesToIgnore(entity1)) {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((LivingEntity) entity1).canEntityBeSeen(entity)) {
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
                || ((entity instanceof WolfEntity) && !(MoCreatures.proxy.attackWolves)) || ((entity instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses)));
    }

    @Override
    public boolean checkSpawningBiome() {
        return true;
    }

    @Override
    public void onLivingUpdate() {
        if (!this.world.isRemote) {

            /*if (forceUpdates() && this.rand.nextInt(1000) == 0) {
                MoCTools.forceDataSync(this);
            }*/

            if (getIsTamed() && this.rand.nextInt(200) == 0) {
                MoCMessageHandler.CHANNEL.sendToAllAround(new MoCMessageHealth(this.getEntityId(), this.getHealth()), new PacketDistributor.TargetPoint(
                        this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
            }

            if (this.isHarmedByDaylight()) {
                if (this.world.isDaytime()) {
                    float var1 = this.getBrightness();
                    if (var1 > 0.5F
                            && this.world.canBlockSeeSky(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY()),
                                    MathHelper.floor(this.getPosZ()))) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F) {
                        this.setFire(8);
                    }
                }
            }
            if (getEdad() == 0) setEdad(getMaxEdad() - 10); //fixes tiny creatures spawned by error
            if (!getIsAdult() && (this.rand.nextInt(300) == 0)) {
                setEdad(getEdad() + 1);
                if (getEdad() >= getMaxEdad()) {
                    setAdult(true);
                }
            }

            if (getIsFlying() && this.getNavigator().noPath() && !isMovementCeased() && this.getAttackTarget() == null && this.rand.nextInt(20) == 0) {
                this.wander.makeUpdate();
            }
        }

        this.getNavigator().tick();
        super.tick();
    }

    protected int getMaxEdad() {
        return 100;
    }

    protected boolean isHarmedByDaylight() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (!this.world.isRemote && getIsTamed()) {
            MoCMessageHandler.CHANNEL.sendToAllAround(new MoCMessageHealth(this.getEntityId(), this.getHealth()), new TargetPoint(
                    this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }
        return super.attackEntityFrom(damagesource, i);
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
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        //nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.putBoolean("Adult", getIsAdult());
        nbttagcompound.putInt("Edad", getEdad());
        nbttagcompound.putString("Name", getPetName());
        nbttagcompound.putInt("TypeInt", getType());

    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        //nbttagcompound = MoCTools.getEntityData(this);
        setAdult(nbttagcompound.getBoolean("Adult"));
        setEdad(nbttagcompound.getInt("Edad"));
        setPetName(nbttagcompound.getString("Name"));
        setType(nbttagcompound.getInt("TypeInt"));

    }

    @Override
    public void fall(float f, float f1) {
        if (!isFlyer()) {
            super.fall(f, f1);
        }
    }

    @Override
    public boolean isOnLadder() {
        if (isFlyer()) {
            return false;
        } else {
            return super.isOnLadder();
        }
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (!isFlyer()) {
            super.travel(strafe, vertical, forward);
            return;
        }
        this.moveEntityWithHeadingFlyer(strafe, vertical, forward);
    }

    public void moveEntityWithHeadingFlyer(float strafe, float vertical, float forward) {
        if (this.isServerWorld()) {

            this.moveRelative( 0.1F, new Vec3d(strafe, vertical, forward));
            this.move(MoverType.SELF, this.getMotion());
            this.getMotion().scale(0.8999999761581421D);
        } else {
            super.travel(new Vec3d(strafe, vertical, forward));
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

    public float getMoveSpeed() {
        return 0.7F;
    }

    @Override
    public int nameYOffset() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public boolean renderName() {
        return MoCreatures.proxy.getDisplayPetName()
                && (getPetName() != null && !getPetName().equals("") && (!this.isBeingRidden()) && (this.getRidingEntity() == null));
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
    protected boolean canDespawn() {
        return !getIsTamed();
    }

    @Override
    public void setDead() {
        // Server check required to prevent tamed entities from being duplicated on client-side
        if (!this.world.isRemote && (getIsTamed()) && (getHealth() > 0)) {
            return;
        }
        super.setDead();
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
        return this.world.getDifficulty() != Difficulty.PEACEFUL;
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
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag =
                entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
                        .getValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
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
    public PathNavigator getNavigator() {
        if (this.isInWater() && this.isAmphibian()) {
            return this.navigatorWater;
        }
        if (this.isFlyer()) {
            return this.navigatorFlyer;
        }
        return this.navigator;
    }

    public boolean isAmphibian() {
        return false;
    }

    @Override
    public boolean getIsFlying() {
        return isFlyer();
    }

    /**
     * Returns true if the entity is of the @link{EnumCreatureType} provided
     *
     * @param type The EnumCreatureType type this entity is evaluating
     * @param forSpawnCount If this is being invoked to check spawn count caps.
     * @return If the creature is of the type provided
     */
    @Override
    public boolean isCreatureType(EntityClassification type, boolean forSpawnCount) {
        if (type == EntityClassification.MONSTER) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String getClazzString() {
        return EntityList.getEntityString(this);
    }

    @Override
    public boolean getIsGhost() {
        return false;
    }
}
