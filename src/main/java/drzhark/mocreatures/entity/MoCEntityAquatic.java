package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

public abstract class MoCEntityAquatic extends CreatureEntity implements IMoCEntity//, IEntityAdditionalSpawnData
{

    protected boolean divePending;
    protected boolean jumpPending;
    protected boolean isEntityJumping;
    protected int outOfWater;
    private boolean diving;
    private int divingCount;
    private int mountCount;
    public boolean fishHooked;
    protected boolean riderIsDisconnecting;
    protected float moveSpeed;
    protected String texture;
    protected PathNavigator navigatorWater;
    private boolean updateDivingDepth = false;
    private double divingDepth;
    protected int temper;

    protected static final DataParameter<Boolean> ADULT = EntityDataManager.defineId(MoCEntityAquatic.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> TYPE = EntityDataManager.defineId(MoCEntityAquatic.class, DataSerializers.INT);
    protected static final DataParameter<Integer> AGE = EntityDataManager.defineId(MoCEntityAquatic.class, DataSerializers.INT);
    protected static final DataParameter<String> NAME_STR = EntityDataManager.defineId(MoCEntityAquatic.class, DataSerializers.STRING);

    public MoCEntityAquatic(EntityType<? extends MoCEntityAquatic> type, World world) {
        super(type, world);
        this.outOfWater = 0;
        setTemper(50);
        this.setNewDivingDepth();
        this.riderIsDisconnecting = false;
        this.texture = "blank.jpg";
        this.navigatorWater = new SwimmerPathNavigator(this, world);
        this.moveControl = new EntityAIMoverHelperMoC(this);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return CreatureEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 6.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
//                .add(Attributes.MOVEMENT_SPEED, getMoveSpeed());
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture(this.texture);
    }

    @Override
    public ILivingEntityData finalizeSpawn(IServerWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData par1EntityLivingData, CompoundNBT dataTag) {
        selectType();
        return super.finalizeSpawn(worldIn, difficulty, reason, par1EntityLivingData, dataTag);
    }

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
    public int getOwnerPetId() {
        return -1;
    }

    @Override
    public void setOwnerPetId(int i) {
    }

    @Nullable
    public UUID getOwnerId() {
        return null;
    }

    @Override
    public boolean getIsTamed() {
        return false;
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
    public String getPetName() {
        return this.entityData.get(NAME_STR);
    }

    @Override
    public int getEdad() {
        return this.entityData.get(AGE);
    }

    @Override
    public void setEdad(int i) {
        this.entityData.set(AGE, i);
    }

    @Override
    public void setPetName(String name) {
        this.entityData.set(NAME_STR, name);
    }

    public int getTemper() {
        return this.temper;
    }

    public void setTemper(int i) {
        this.temper = i;
    }

    /**
     * How difficult is the creature to be tamed? the Higher the number, the
     * more difficult
     */
    public int getMaxTemper() {
        return 100;
    }

    public float b(float f, float f1, float f2) {
        float f3 = f1;
        for (f3 = f1 - f; f3 < -180F; f3 += 360F) {
        }
        for (; f3 >= 180F; f3 -= 360F) {
        }
        if (f3 > f2) {
            f3 = f2;
        }
        if (f3 < -f2) {
            f3 = -f2;
        }
        return f + f3;
    }

    public void faceItem(int i, int j, int k, float f) {
        double d = i - this.getX();
        double d1 = k - this.getZ();
        double d2 = j - this.getY();
        double d3 = MathHelper.sqrt((d * d) + (d1 * d1));
        float f1 = (float) ((Math.atan2(d1, d) * 180D) / 3.1415927410125728D) - 90F;
        float f2 = (float) ((Math.atan2(d2, d3) * 180D) / 3.1415927410125728D);
        this.xRot = -b(this.xRot, f2, f);
        this.yRot = b(this.yRot, f1, f);
    }

    @Override
    public boolean removeWhenFarAway(double d) {
        if (MoCConfig.COMMON_CONFIG.GLOBAL.forceDespawns.get()) {
            return !getIsTamed();
        } else {
            return false;
        }
    }

    @Override
    public boolean checkSpawningBiome() {
        return true;
    }

    @Override
    protected void playStepSound(BlockPos pos, BlockState state) {
    }

//    @Override
//    public void fall(float f, float f1) {
//    }

    public ItemEntity getClosestFish(Entity entity, double d) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            if ((entityitem1.getItem().getItem() != Items.COD) || entityitem1.getItem().getItem() != Items.SALMON || !entityitem1.isInWater()) {
                continue;
            }
            double d2 = entityitem1.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityitem = entityitem1;
            }
        }

        return entityitem;
    }

    @Override
    protected float getSoundVolume() {
        return 0.4F;
    }

    public boolean gettingOutOfWater() {
        int i = (int) this.getX();
        int j = (int) this.getY();
        int k = (int) this.getZ();
        return this.level.isEmptyBlock(new BlockPos(i, j + 1, k));
    }

    /**
     * mount jumping power
     */
    public double getCustomJump() {
        return 0.4D;
    }

    public void setIsJumping(boolean flag) {
        this.isEntityJumping = flag;
    }

    public boolean getIsJumping() {
        return this.isEntityJumping;
    }

    /**
     * Sets a flag that will make the Entity "jump" in the next onGround
     * moveEntity update
     */
    @Override
    public void makeEntityJump() {
        this.jumpPending = true;
    }

    /*@Override
    public boolean handleWaterMovement() {
        return this.world.handleMaterialAcceleration(this.getEntityBoundingBox(), Material.WATER, this);
    }*/

    protected boolean MoveToNextEntity(Entity entity) {
        if (entity != null) {
            int i = MathHelper.floor(entity.getX());
            int j = MathHelper.floor(entity.getY());
            int k = MathHelper.floor(entity.getZ());
            faceItem(i, j, k, 30F);
            if (this.getX() < i) {
                double d = entity.getX() - this.getX();
                if (d > 0.5D) {
                    this.getDeltaMovement().add(0.050000000000000003D, 0, 0);
                }
            } else {
                double d1 = this.getX() - entity.getX();
                if (d1 > 0.5D) {
                    this.getDeltaMovement().subtract(0.050000000000000003D, 0, 0);
                }
            }
            if (this.getZ() < k) {
                double d2 = entity.getZ() - this.getZ();
                if (d2 > 0.5D) {
                    this.getDeltaMovement().add(0, 0, 0.050000000000000003D);
                }
            } else {
                double d3 = this.getZ() - entity.getZ();
                if (d3 > 0.5D) {
                    this.getDeltaMovement().subtract(0, 0, 0.050000000000000003D);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Speed used to move the mob around
     *
     * @return
     */
    public double getCustomSpeed() {
        return 1.5D;
    }

//    @Override
//    public boolean isInWater() {
//        return this.world.handleMaterialAcceleration(this.getBoundingBox().expand(0.0D, -0.2D, 0.0D), Material.WATER, this);
//    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isDiving() {
        return this.diving;
    }

    @Override
    protected void jumpFromGround() {

    }

    // used to pick up objects while riding an entity
    public void Riding() {
        if ((this.isVehicle()) && (this.getVehicle() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getVehicle();
            List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(1.0D, 0.0D, 1.0D));
            for (Entity entity : list) {
                if (!entity.isAlive()) {
                    continue;
                }
                entity.playerTouch(entityplayer);
                if (!(entity instanceof MobEntity)) {
                    continue;
                }
                float f = distanceTo(entity);
                if (f < 2.0F && this.random.nextInt(10) == 0) {
                    hurt(DamageSource.mobAttack((LivingEntity) entity),
                            (float) ((MobEntity) entity).getAttribute(Attributes.ATTACK_DAMAGE).getValue());
                }
            }
            /*
             if (entityplayer.isSneaking()) {
                 this.makeEntityDive();
             }
             */
        }
    }

    @Override
    public boolean isMovementCeased() {
        return ((!isSwimming() && !this.isVehicle()) || this.isVehicle() || this.getIsSitting());
    }

    @Override
    public void aiStep() {
        if (!this.level.isClientSide) {
            if (this.isVehicle()) {
                Riding();
                this.mountCount = 1;
            }

            if (this.mountCount > 0) {
                if (++this.mountCount > 50) {
                    this.mountCount = 0;
                }
            }
            if (getEdad() == 0) setEdad(getMaxEdad() - 10); //fixes tiny creatures spawned by error
            if (!getIsAdult() && (this.random.nextInt(300) == 0)) {
                setEdad(getEdad() + 1);
                if (getEdad() >= getMaxEdad()) {
                    setAdult(true);
                }
            }

            this.getNavigation().tick();

            //updates diving depth after finishing movement
            if (!this.getNavigation().isDone())// && !updateDivingDepth)
            {
                if (!this.updateDivingDepth) {
                    float targetDepth =
                            (MoCTools.distanceToSurface(this.moveControl.getWantedX(), this.moveControl.getWantedY(), this.moveControl.getWantedZ(), this.level));
                    setNewDivingDepth(targetDepth);
                    this.updateDivingDepth = true;
                }
            } else {
                this.updateDivingDepth = false;
            }

            if (isMovementCeased() || random.nextInt(200) == 0) {
                this.getNavigation().stop();
            }

            /*if (this.isInWater() && this.onGroundHorizontally && rand.nextInt(10) == 0)
            {
                this.motionY += 0.05D;
            }*/

            /*
             if (getIsTamed() && rand.nextInt(100) == 0) {
                 MoCServerPacketHandler.sendHealth(this.getEntityId(),
                 this.world.provider.getDimensionType().getId(), this.getHealth());
             }
             */

            /*if (forceUpdates() && this.rand.nextInt(500) == 0) {
                MoCTools.forceDataSync(this);
            }*/

            if (isFisheable() && !this.fishHooked && this.random.nextInt(30) == 0) {
                getFished();
            }

            if (this.fishHooked && this.random.nextInt(200) == 0) {
                this.fishHooked = false;

                List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(2, 2, 2));
                for (int i = 0; i < list.size(); i++) {
                    Entity entity1 = list.get(i);

                    if (entity1 instanceof FishingBobberEntity) {
                        if (((FishingBobberEntity) entity1).getHookedIn() == this) {
//                            ((FishingBobberEntity) entity1).caughtEntity = null;
                        }
                    }
                }
            }
        }

        this.moveSpeed = getMoveSpeed();

        if (isSwimming()) {
            //floating();
            this.outOfWater = 0;
            this.setAirSupply(800);
        } else {
            this.outOfWater++;
            this.getDeltaMovement().subtract(0, 0.1D, 0);
            if (this.outOfWater > 20) {
                this.getNavigation().stop();
            }
            if (this.outOfWater > 300 && (this.outOfWater % 40) == 0) {
                this.setDeltaMovement((float) (Math.random() * 0.2D - 0.1D), getDeltaMovement().y + 0.3D, (float) (Math.random() * 0.2D - 0.1D));
                hurt(DamageSource.DROWN, 1);
            }
        }
        if (!this.diving) {
            if (!this.isVehicle() && getTarget() == null && !this.navigation.isDone() && this.random.nextInt(500) == 0) {
                this.diving = true;
            }
        } else {
            this.divingCount++;
            if (this.divingCount > 100 || this.isVehicle()) {
                this.diving = false;
                this.divingCount = 0;
            }
        }
        super.aiStep();
    }

    public boolean isSwimming() {
        return ((isInWater()));
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

    public void setTypeInt(int i) {
        setType(i);
        selectType();
    }

    /**
     * Used to synchronize the attack animation between server and client
     *
     * @param attackType
     */
    @Override
    public void performAnimation(int attackType) {
    }

    /**
     * Makes the entity despawn if requirements are reached changed to the
     * entities now last longer
     */
    @Override
    public void checkDespawn() {
        PlayerEntity var1 = this.level.getNearestPlayer(this, -1.0D);
        if (var1 != null) {
            double var2 = var1.getX() - this.getX();
            double var4 = var1.getY() - this.getY();
            double var6 = var1.getZ() - this.getZ();
            double var8 = var2 * var2 + var4 * var4 + var6 * var6;

            if (this.removeWhenFarAway(0) && var8 > 16384.0D) {
                this.remove();
            }
            //changed from 600
            if (this.noActionTime > 1800 && this.random.nextInt(800) == 0 && var8 > 1024.0D && this.removeWhenFarAway(0)) {
                this.remove();
            } else if (var8 < 1024.0D) {
                this.noActionTime = 0;
            }
        }
    }

    public float getMoveSpeed() {
        return 0.7F;
    }

    @Override
    public int nameYOffset() {
        return 0;
    }

//    @Override
//    public boolean renderName() {
//        return MoCreatures.proxy.getDisplayPetName()
//                && (getPetName() != null && !getPetName().equals("") && (!this.isBeingRidden()) && (this.getRidingEntity() == null));
//    }

    /*@Override
    public boolean updateMount() {
        return false;
    }*/

    /*@Override
    public boolean forceUpdates() {
        return false;
    }*/

    @Override
    public void makeEntityDive() {
        this.divePending = true;
    }

    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0F;
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this
     * entity.
     */
    /*@Override
    public boolean canSpawn(IWorld worldIn, SpawnReason reason) {
        return MoCreatures.entityMap.get(this.getType()).getFrequency() > 0 && this.world.checkNoEntityCollision(this);
    }*/

    

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (usesNewAI()) {
            return super.hurt(damagesource, i);
        }

        if (isNotScared()) {
            Entity tempEntity = this.getTarget();
            setTarget((LivingEntity) tempEntity);
            return super.hurt(damagesource, i);
        }

        return super.hurt(damagesource, i);
    }

    protected boolean canBeTrappedInNet() {
        return (this instanceof IMoCTameable) && getIsTamed();
    }

    protected void dropMyStuff() {
    }

    /**
     * Used to heal the animal
     *
     * @param itemstack
     * @return
     */
    protected boolean isMyHealFood(ItemStack itemstack) {
        return false;
    }

    @Override
    public void setArmorType(int i) {
    }

    @Override
    public float pitchRotationOffset() {
        return 0F;
    }

    @Override
    public float rollRotationOffset() {
        return 0F;
    }

    /**
     * The act of getting Hooked into a fish Hook.
     */
    private void getFished() {
        PlayerEntity entityplayer1 = this.level.getNearestPlayer(this, 18D);
        if (entityplayer1 != null) {
            FishingBobberEntity fishHook = entityplayer1.fishing;
            if (fishHook != null && fishHook.getHookedIn() == null) {
                float f = fishHook.distanceTo(this);
                if (f > 1) {
                    MoCTools.getPathToEntity(this, fishHook, f);
                } else {
//                    fishHook.caughtEntity = this; TODO
                    this.fishHooked = true;
                }
            }
        }
    }

    /**
     * Is this aquatic entity prone to be fished with a fish Hook?
     *
     * @return
     */
    protected boolean isFisheable() {
        return false;
    }

    @Override
    public float getAdjustedZOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedXOffset() {
        return 0F;
    }

    /**
     * Finds and entity described in entitiesToInclude at d distance
     *
     * @param d
     * @return
     */
    protected LivingEntity getBoogey(double d) {
        LivingEntity entityliving = null;
        List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(d, 4D, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (entitiesToInclude(entity)) {
                entityliving = (LivingEntity) entity;
            }
        }
        return entityliving;
    }

    /**
     * Used in getBoogey to specify what kind of entity to look for
     *
     * @param entity
     * @return
     */
    public boolean entitiesToInclude(Entity entity) {
        return ((entity.getClass() != this.getClass()) && (entity instanceof LivingEntity) && ((entity.getBbWidth() >= 0.5D) || (entity.getBbHeight() >= 0.5D)));
    }

    @Override
    public boolean isNotScared() {
        return false;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean canBeLeashed(PlayerEntity player) {
        if (!this.level.isClientSide && !MoCTools.isThisPlayerAnOP(player) && this.getIsTamed() && !player.getUUID().equals(this.getOwnerId())) {
            return false;
        }
        return super.canBeLeashed(player);
    }

    @Override
    public boolean getIsSitting() {
        return false;
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsTamed() && this.level.getDifficulty() != Difficulty.PEACEFUL;
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    @Override
    public void travel(Vector3d motion) {
            if (this.isInWater()) {
                if (this.isVehicle()) {
                    LivingEntity passenger = (LivingEntity)this.getControllingPassenger();
                    if (passenger != null)this.moveWithRider(motion, passenger); //riding movement
                        return;
                }
                this.moveRelative(0.1F, motion);
                this.move(MoverType.SELF, getDeltaMovement());
                this.getDeltaMovement().scale(0.8999999761581421D);

                if (this.getTarget() == null) {
                    this.getDeltaMovement().subtract(0, 0.005D, 0);
                }
                this.animationSpeedOld = this.animationSpeed;
                double d2 = this.getX() - this.xo;
                double d3 = this.getZ() - this.zo;
                float f7 = MathHelper.sqrt(d2 * d2 + d3 * d3) * 4.0F;

                if (f7 > 1.0F)
                {
                    f7 = 1.0F;
                }

                this.animationSpeed += (f7 - this.animationSpeed) * 0.4F;
                this.animationPosition += this.animationSpeed;
            } else {
                super.travel(motion);
            }
    }

    /**
     ** Riding Code
     * @param motion
     */
    public void moveWithRider(Vector3d motion, LivingEntity passenger) {
        if (passenger == null)
        {
            return;
        }
        //Buckles rider if out of water
        if (this.isVehicle() && !getIsTamed() && !isSwimming()) {
            this.ejectPassengers();
            return;
        }

        if (this.isVehicle() && !getIsTamed()) {
            this.moveWithRiderUntamed(motion, passenger);
            return;
        }

        if (this.isVehicle() && getIsTamed()) {
            this.yRotO = this.yRot = passenger.yRot;
            this.xRot = passenger.xRot * 0.5F;
            this.setRot(this.yRot, this.xRot);
            this.yHeadRot = this.yBodyRot = this.yRot;
            Vector3d motion1 = new Vector3d(passenger.xxa * 0.35F, motion.y, passenger.zza * (float)(this.getCustomSpeed() / 5D));
            if (this.jumpPending) {
                if (this.isSwimming()) {
                    this.getDeltaMovement().add(0, getCustomJump(), 0);
                }
                this.jumpPending = false;
            }
            //So it doesn't sink on its own
            if (this.getDeltaMovement().y < 0D && isSwimming()) {
                this.setDeltaMovement(getDeltaMovement().x, 0D, getDeltaMovement().z);
            }
            if (this.divePending) {
                this.divePending = false;
                this.getDeltaMovement().subtract(0, 0.3D, 0);
            }
                this.setSpeed((float) this.getAttribute(Attributes.MOVEMENT_SPEED).getValue());
                super.travel(motion1);
                this.moveRelative(0.1F, motion1);
            }
        }

    public void moveWithRiderUntamed(Vector3d motion, LivingEntity passenger) {
        //Riding behaviour if untamed
        if ((this.isVehicle()) && !getIsTamed()) {
            if ((this.random.nextInt(5) == 0) && !getIsJumping() && this.jumpPending) {
                this.getDeltaMovement().add(0, getCustomJump(), 0);
                setIsJumping(true);
                this.jumpPending = false;
            }
            if (this.random.nextInt(10) == 0) {
                this.getDeltaMovement().add(this.random.nextDouble() / 30D, 0, this.random.nextDouble() / 10D);
            }
            //if (!this.world.isRemote) {
                move(MoverType.SELF, this.getDeltaMovement());
            //}
            if (!this.level.isClientSide && this.random.nextInt(100) == 0) {
                passenger.getDeltaMovement().add(0D, 0.9D, -0.3D);
                passenger.stopRiding();
            }
            if (this.onGround) {
                setIsJumping(false);
            }
            if (!this.level.isClientSide && this instanceof IMoCTameable && passenger instanceof PlayerEntity) {
                int chance = (getMaxTemper() - getTemper());
                if (chance <= 0) {
                    chance = 1;
                }
                if (this.random.nextInt(chance * 8) == 0) {
                    MoCTools.tameWithName((PlayerEntity) passenger, (IMoCTameable) this);
                }

            }
        }
    }

    /**
     * Whether or not the current entity is in lava
     */
    @Override
    public boolean checkSpawnObstruction(IWorldReader worldIn) {
        return worldIn.isUnobstructed(this);
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    @Override
    public int getAmbientSoundInterval() {
        return 300;
    }

    /**
     * Get the experience points the entity currently has.
     */
    @Override
    protected int getExperienceReward(PlayerEntity player) {
        return 1 + this.level.random.nextInt(3);
    }

    /**
     * Gets called every tick from main Entity class
     */
    @Override
    public void tick() {
        int i = this.getAirSupply();
        super.tick();

        if (this.isAlive() && !this.isInWater()) {
            --i;
            this.setAirSupply(i);

            if (this.getAirSupply() == -30) {
                this.setAirSupply(0);
                this.hurt(DamageSource.DROWN, 1.0F);
                getDeltaMovement().add(this.random.nextDouble() / 10D, 0, this.random.nextDouble() / 10D);
            }
        } else {
            this.setAirSupply(300);
        }
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    protected boolean usesNewAI() {
        return false;
    }

    @Override
    public PathNavigator getNavigation() {
        if (this.isInWater()) {
            return this.navigatorWater;
        }
        return this.navigation;
    }

    /**
     * The distance the entity will float under the surface. 0F = surface 1.0F = 1 block under
     * @return
     */
    @Override
    public double getDivingDepth() {
        return (float) this.divingDepth;
    }

    /**
     * Sets diving depth. if setDepth given = 0.0D, will then choose a random value within proper range
     * @param setDepth
     */
    protected void setNewDivingDepth(double setDepth) {
        if (setDepth != 0.0D) {
            if (setDepth > maxDivingDepth()) {
                setDepth = maxDivingDepth();
            }
            if (setDepth < minDivingDepth()) {
                setDepth = minDivingDepth();
            }
            this.divingDepth = setDepth;
        } else {
            this.divingDepth = (float) (this.random.nextDouble() * (maxDivingDepth() - minDivingDepth()) + minDivingDepth());
        }

    }

    protected void setNewDivingDepth() {
        setNewDivingDepth(0.0D);
    }

    protected double minDivingDepth() {
        return 0.20D;
    }

    protected double maxDivingDepth() {
        return 3.0D;
    }

    @Override
    public void forceEntityJump() {
        this.jumpFromGround();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float yawRotationOffset() {
        double d4 = 0F;
        if ((this.getDeltaMovement().x != 0D) || (this.getDeltaMovement().z != 0D)) {
            d4 = Math.sin(this.tickCount * 0.5D) * 8D;
        }
        return (float) (d4);//latOffset;
    }

    public int getMaxEdad() {
        return 100;
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        if (!entityIn.isInWater()) {
            return false;
        }
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
        return 1;
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    /**
     * Boolean used for flying mounts
     */
    public boolean isFlyer() {
        return false;
    }

    @Override
    public boolean getIsFlying() {
        return false;
    }

    @Override
    public SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.GENERIC_HURT;
    }
    
    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @Nullable
    public Entity getControllingPassenger()
    {
        return this.getPassengers().isEmpty() ? null : this.getPassengers().get(0);
    }

    @Override
    public boolean getIsGhost() {
        return false;
    }

    @Override
    public void setLeashedTo(Entity entityIn, boolean sendAttachNotification) {
        if (this.getIsTamed() && entityIn instanceof PlayerEntity) {
            PlayerEntity entityplayer = (PlayerEntity) entityIn;
            if (MoCConfig.COMMON_CONFIG.OWNERSHIP.enableOwnership.get() && this.getOwnerId() != null
                    && !entityplayer.getUUID().equals(this.getOwnerId()) && !MoCTools.isThisPlayerAnOP((entityplayer))) {
                return;
            }
        }
        super.setLeashedTo(entityIn, sendAttachNotification);
    }
}
