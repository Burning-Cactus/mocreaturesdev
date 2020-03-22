package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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

    protected static final DataParameter<Boolean> ADULT = EntityDataManager.<Boolean>createKey(MoCEntityAquatic.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(MoCEntityAquatic.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> AGE = EntityDataManager.<Integer>createKey(MoCEntityAquatic.class, DataSerializers.VARINT);
    protected static final DataParameter<String> NAME_STR = EntityDataManager.<String>createKey(MoCEntityAquatic.class, DataSerializers.STRING);

    public MoCEntityAquatic(World world) {
        super(world);
        this.outOfWater = 0;
        setTemper(50);
        this.setNewDivingDepth();
        this.riderIsDisconnecting = false;
        this.texture = "blank.jpg";
        this.navigatorWater = new SwimmerPathNavigator(this, world);
        this.moveHelper = new EntityAIMoverHelperMoC(this);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(getMoveSpeed());
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getTexture(this.texture);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData par1EntityLivingData, CompoundNBT dataTag) {
        selectType();
        return super.onInitialSpawn(worldIn, difficulty, reason, par1EntityLivingData, dataTag);
    }

    @Override
    public void selectType() {
        setType(1);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ADULT, false);
        this.dataManager.register(TYPE, 0);
        this.dataManager.register(AGE, 45);
        this.dataManager.register(NAME_STR, "");
    }

    @Override
    public void setType(int i) {
        this.dataManager.set(TYPE, i);
    }

    @Override
    public int getType() {
        return this.dataManager.get(TYPE);
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
        return this.dataManager.get(ADULT);
    }

    @Override
    public void setAdult(boolean flag) {
        this.dataManager.set(ADULT, flag);
    }

    @Override
    public String getPetName() {
        return this.dataManager.get(NAME_STR);
    }

    @Override
    public int getEdad() {
        return this.dataManager.get(AGE);
    }

    @Override
    public void setEdad(int i) {
        this.dataManager.set(AGE, i);
    }

    @Override
    public void setPetName(String name) {
        this.dataManager.set(NAME_STR, name);
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
        double d = i - this.getPosX();
        double d1 = k - this.getPosZ();
        double d2 = j - this.getPosY();
        double d3 = MathHelper.sqrt((d * d) + (d1 * d1));
        float f1 = (float) ((Math.atan2(d1, d) * 180D) / 3.1415927410125728D) - 90F;
        float f2 = (float) ((Math.atan2(d2, d3) * 180D) / 3.1415927410125728D);
        this.rotationPitch = -b(this.rotationPitch, f2, f);
        this.rotationYaw = b(this.rotationYaw, f1, f);
    }

    @Override
    protected boolean canDespawn() {
        if (MoCreatures.proxy.forceDespawns) {
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
    protected void playStepSound(BlockPos pos, Block par4) {
    }

    @Override
    public void fall(float f, float f1) {
    }

    public ItemEntity getClosestFish(Entity entity, double d) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            if ((entityitem1.getItem().getItem() != Items.COD) || entityitem1.getItem().getItem() != Items.SALMON || !entityitem1.isInWater()) {
                continue;
            }
            double d2 = entityitem1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
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
        int i = (int) this.getPosX();
        int j = (int) this.getPosY();
        int k = (int) this.getPosZ();
        return this.world.isAirBlock(new BlockPos(i, j + 1, k));
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
            int i = MathHelper.floor(entity.getPosX());
            int j = MathHelper.floor(entity.getPosY());
            int k = MathHelper.floor(entity.getPosZ());
            faceItem(i, j, k, 30F);
            if (this.getPosX() < i) {
                double d = entity.getPosX() - this.getPosX();
                if (d > 0.5D) {
                    this.motionX += 0.050000000000000003D;
                }
            } else {
                double d1 = this.getPosX() - entity.getPosX();
                if (d1 > 0.5D) {
                    this.motionX -= 0.050000000000000003D;
                }
            }
            if (this.getPosZ() < k) {
                double d2 = entity.getPosZ() - this.getPosZ();
                if (d2 > 0.5D) {
                    this.motionZ += 0.050000000000000003D;
                }
            } else {
                double d3 = this.getPosZ() - entity.getPosZ();
                if (d3 > 0.5D) {
                    this.motionZ -= 0.050000000000000003D;
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

    @Override
    public boolean isInWater() {
        return this.world.handleMaterialAcceleration(this.getBoundingBox().expand(0.0D, -0.2D, 0.0D), Material.WATER, this);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isDiving() {
        return this.diving;
    }

    @Override
    protected void jump() {

    }

    // used to pick up objects while riding an entity
    public void Riding() {
        if ((this.isBeingRidden()) && (this.getRidingEntity() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getRidingEntity();
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(1.0D, 0.0D, 1.0D));
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Entity entity = list.get(i);
                    if (entity.isDead) {
                        continue;
                    }
                    entity.onCollideWithPlayer(entityplayer);
                    if (!(entity instanceof MobEntity)) {
                        continue;
                    }
                    float f = getDistance(entity);
                    if ((f < 2.0F) && entity instanceof MobEntity && (this.rand.nextInt(10) == 0)) {
                        attackEntityFrom(DamageSource.causeMobDamage((LivingEntity) entity),
                                (float) ((MobEntity) entity).getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getValue());
                    }
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
        return ((!isSwimming() && !this.isBeingRidden()) || this.isBeingRidden() || this.getIsSitting());
    }

    @Override
    public void onLivingUpdate() {
        if (!this.world.isRemote) {
            if (this.isBeingRidden()) {
                Riding();
                this.mountCount = 1;
            }

            if (this.mountCount > 0) {
                if (++this.mountCount > 50) {
                    this.mountCount = 0;
                }
            }
            if (getEdad() == 0) setEdad(getMaxEdad() - 10); //fixes tiny creatures spawned by error
            if (!getIsAdult() && (this.rand.nextInt(300) == 0)) {
                setEdad(getEdad() + 1);
                if (getEdad() >= getMaxEdad()) {
                    setAdult(true);
                }
            }

            this.getNavigator().onUpdateNavigation();

            //updates diving depth after finishing movement
            if (!this.getNavigator().noPath())// && !updateDivingDepth)
            {
                if (!this.updateDivingDepth) {
                    float targetDepth =
                            (MoCTools.distanceToSurface(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ(), this.world));
                    setNewDivingDepth(targetDepth);
                    this.updateDivingDepth = true;
                }
            } else {
                this.updateDivingDepth = false;
            }

            if (isMovementCeased() || rand.nextInt(200) == 0) {
                this.getNavigator().clearPath();
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

            if (isFisheable() && !this.fishHooked && this.rand.nextInt(30) == 0) {
                getFished();
            }

            if (this.fishHooked && this.rand.nextInt(200) == 0) {
                this.fishHooked = false;

                List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(2, 2, 2));
                for (int i = 0; i < list.size(); i++) {
                    Entity entity1 = list.get(i);

                    if (entity1 instanceof FishingBobberEntity) {
                        if (((FishingBobberEntity) entity1).caughtEntity == this) {
                            ((FishingBobberEntity) entity1).caughtEntity = null;
                        }
                    }
                }
            }
        }

        this.moveSpeed = getMoveSpeed();

        if (isSwimming()) {
            //floating();
            this.outOfWater = 0;
            this.setAir(800);
        } else {
            this.outOfWater++;
            this.motionY -= 0.1D;
            if (this.outOfWater > 20) {
                this.getNavigator().clearPath();
            }
            if (this.outOfWater > 300 && (this.outOfWater % 40) == 0) {
                this.motionY += 0.3D;
                this.motionX = (float) (Math.random() * 0.2D - 0.1D);
                this.motionZ = (float) (Math.random() * 0.2D - 0.1D);
                attackEntityFrom(DamageSource.DROWN, 1);
            }
        }
        if (!this.diving) {
            if (!this.isBeingRidden() && getAttackTarget() == null && !this.navigator.noPath() && this.rand.nextInt(500) == 0) {
                this.diving = true;
            }
        } else {
            this.divingCount++;
            if (this.divingCount > 100 || this.isBeingRidden()) {
                this.diving = false;
                this.divingCount = 0;
            }
        }
        super.onLivingUpdate();
    }

    public boolean isSwimming() {
        return ((isInsideOfMaterial(Material.WATER)));
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
        super.writeEntityToNBT(nbttagcompound);
        //nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.setBoolean("Adult", getIsAdult());
        nbttagcompound.setInteger("Edad", getEdad());
        nbttagcompound.setString("Name", getPetName());
        nbttagcompound.setInteger("TypeInt", getType());
    }

    @Override
    public void readEntityFromNBT(CompoundNBT nbttagcompound) {
        super.readEntityFromNBT(nbttagcompound);
        //nbttagcompound = MoCTools.getEntityData(this);
        setAdult(nbttagcompound.getBoolean("Adult"));
        setEdad(nbttagcompound.getInteger("Edad"));
        setPetName(nbttagcompound.getString("Name"));
        setType(nbttagcompound.getInteger("TypeInt"));
    }

    public void setDisplayName(boolean flag) {
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
    protected void despawnEntity() {
        PlayerEntity var1 = this.world.getClosestPlayer(this, -1.0D);
        if (var1 != null) {
            double var2 = var1.getPosX() - this.getPosX();
            double var4 = var1.getPosY() - this.getPosY();
            double var6 = var1.getPosZ() - this.getPosZ();
            double var8 = var2 * var2 + var4 * var4 + var6 * var6;

            if (this.canDespawn() && var8 > 16384.0D) {
                this.setDead();
            }
            //changed from 600
            if (this.idleTime > 1800 && this.rand.nextInt(800) == 0 && var8 > 1024.0D && this.canDespawn()) {
                this.setDead();
            } else if (var8 < 1024.0D) {
                this.idleTime = 0;
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

    @Override
    public boolean renderName() {
        return MoCreatures.proxy.getDisplayPetName()
                && (getPetName() != null && !getPetName().equals("") && (!this.isBeingRidden()) && (this.getRidingEntity() == null));
    }

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
    @Override
    public boolean getCanSpawnHere() {
        return MoCreatures.entityMap.get(this.getClass()).getFrequency() > 0 && this.world.checkNoEntityCollision(this.getEntityBoundingBox());
    }

    

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (usesNewAI()) {
            return super.attackEntityFrom(damagesource, i);
        }

        if (isNotScared()) {
            Entity tempEntity = this.getAttackTarget();
            setAttackTarget((LivingEntity) tempEntity);
            return super.attackEntityFrom(damagesource, i);
        }

        return super.attackEntityFrom(damagesource, i);
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
        PlayerEntity entityplayer1 = this.world.getClosestPlayer(this, 18D);
        if (entityplayer1 != null) {
            FishingBobberEntity fishHook = entityplayer1.fishEntity;
            if (fishHook != null && fishHook.caughtEntity == null) {
                float f = fishHook.getDistance(this);
                if (f > 1) {
                    MoCTools.getPathToEntity(this, fishHook, f);
                } else {
                    fishHook.caughtEntity = this;
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
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(d, 4D, d));
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
        return ((entity.getClass() != this.getClass()) && (entity instanceof LivingEntity) && ((entity.width >= 0.5D) || (entity.height >= 0.5D)));
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
    public boolean canBeLeashedTo(PlayerEntity player) {
        if (!this.world.isRemote && !MoCTools.isThisPlayerAnOP(player) && this.getIsTamed() && !player.getUniqueID().equals(this.getOwnerId())) {
            return false;
        }
        return super.canBeLeashedTo(player);
    }

    @Override
    public boolean getIsSitting() {
        return false;
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsTamed() && this.world.getDifficulty() != Difficulty.PEACEFUL;
    }

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    @Override
    public void travel(float strafe, float vertical, float forward) {
            if (this.isInWater()) {
                if (this.isBeingRidden()) {
                    LivingEntity passenger = (LivingEntity)this.getControllingPassenger();
                    if (passenger != null)this.moveWithRider(strafe, vertical, forward, passenger); //riding movement
                        return;
                }
                this.moveRelative(strafe, vertical, forward, 0.1F);
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.8999999761581421D;
                this.motionY *= 0.8999999761581421D;
                this.motionZ *= 0.8999999761581421D;

                if (this.getAttackTarget() == null) {
                    this.motionY -= 0.005D;
                }
                this.prevLimbSwingAmount = this.limbSwingAmount;
                double d2 = this.getPosX() - this.prevPosX;
                double d3 = this.getPosZ() - this.prevPosZ;
                float f7 = MathHelper.sqrt(d2 * d2 + d3 * d3) * 4.0F;

                if (f7 > 1.0F)
                {
                    f7 = 1.0F;
                }

                this.limbSwingAmount += (f7 - this.limbSwingAmount) * 0.4F;
                this.limbSwing += this.limbSwingAmount;
            } else {
                super.travel(strafe, vertical, forward);
            }
    }

    /**
     ** Riding Code
     * @param strafe
     * @param forward
     */
    public void moveWithRider(float strafe, float vertical, float forward, LivingEntity passenger) {
        if (passenger == null)
        {
            return;
        }
        //Buckles rider if out of water
        if (this.isBeingRidden() && !getIsTamed() && !isSwimming()) {
            this.removePassengers();
            return;
        }

        if (this.isBeingRidden() && !getIsTamed() && passenger instanceof LivingEntity) {
            this.moveWithRiderUntamed(strafe, vertical, forward, passenger);
            return;
        }

        if ((this.isBeingRidden()) && getIsTamed() && passenger instanceof LivingEntity) {
            this.prevRotationYaw = this.rotationYaw = passenger.rotationYaw;
            this.rotationPitch = passenger.rotationPitch * 0.5F;
            this.setRotation(this.rotationYaw, this.rotationPitch);
            this.rotationYawHead = this.renderYawOffset = this.rotationYaw;
            strafe = passenger.moveStrafing * 0.35F;
            forward = passenger.moveForward * (float) (this.getCustomSpeed() / 5D);
            if (this.jumpPending) {
                if (this.isSwimming()) {
                    this.motionY += getCustomJump();
                }
                this.jumpPending = false;
            }
            //So it doesn't sink on its own
            if (this.motionY < 0D && isSwimming()) {
                this.motionY = 0D;
            }
            if (this.divePending) {
                this.divePending = false;
                this.motionY -= 0.3D;
            }
                this.setAIMoveSpeed((float) this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getValue());
                super.travel(strafe, vertical, forward);
                this.moveRelative(strafe, vertical, forward, 0.1F);
            }
        }

    public void moveWithRiderUntamed(float strafe, float vertical, float forward, LivingEntity passenger) {
        //Riding behaviour if untamed
        if ((this.isBeingRidden()) && !getIsTamed()) {
            if ((this.rand.nextInt(5) == 0) && !getIsJumping() && this.jumpPending) {
                this.motionY += getCustomJump();
                setIsJumping(true);
                this.jumpPending = false;
            }
            if (this.rand.nextInt(10) == 0) {
                this.motionX += this.rand.nextDouble() / 30D;
                this.motionZ += this.rand.nextDouble() / 10D;
            }
            //if (!this.world.isRemote) {
                move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
            //}
            if (!this.world.isRemote && this.rand.nextInt(100) == 0) {
                passenger.motionY += 0.9D;
                passenger.motionZ -= 0.3D;
                passenger.dismountRidingEntity();
            }
            if (this.onGround) {
                setIsJumping(false);
            }
            if (!this.world.isRemote && this instanceof IMoCTameable && passenger instanceof PlayerEntity) {
                int chance = (getMaxTemper() - getTemper());
                if (chance <= 0) {
                    chance = 1;
                }
                if (this.rand.nextInt(chance * 8) == 0) {
                    MoCTools.tameWithName((PlayerEntity) passenger, (IMoCTameable) this);
                }

            }
        }
    }

    /**
     * Whether or not the current entity is in lava
     */
    @Override
    public boolean isNotColliding() {
        return this.world.checkNoEntityCollision(this.getBoundingBox(), this);
    }

    /**
     * Get number of ticks, at least during which the living entity will be silent.
     */
    @Override
    public int getTalkInterval() {
        return 300;
    }

    /**
     * Get the experience points the entity currently has.
     */
    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return 1 + this.world.rand.nextInt(3);
    }

    /**
     * Gets called every tick from main Entity class
     */
    @Override
    public void onEntityUpdate() {
        int i = this.getAir();
        super.onEntityUpdate();

        if (this.isEntityAlive() && !this.isInWater()) {
            --i;
            this.setAir(i);

            if (this.getAir() == -30) {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.DROWN, 1.0F);
                this.motionX += this.rand.nextDouble() / 10D;
                this.motionZ += this.rand.nextDouble() / 10D;
            }
        } else {
            this.setAir(300);
        }
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    protected boolean usesNewAI() {
        return false;
    }

    @Override
    public PathNavigator getNavigator() {
        if (this.isInWater()) {
            return this.navigatorWater;
        }
        return this.navigator;
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
            this.divingDepth = (float) (this.rand.nextDouble() * (maxDivingDepth() - minDivingDepth()) + minDivingDepth());
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
        this.jump();
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float yawRotationOffset() {
        double d4 = 0F;
        if ((this.motionX != 0D) || (this.motionZ != 0D)) {
            d4 = Math.sin(this.ticksExisted * 0.5D) * 8D;
        }
        return (float) (d4);//latOffset;
    }

    public int getMaxEdad() {
        return 100;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (!entityIn.isInWater()) {
            return false;
        }
        boolean flag =
                entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE)
                        .getAttributeValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
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

    protected SoundEvent getUpsetSound() {
        return SoundEvents.ENTITY_GENERIC_HURT;
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
        if (type == EntityClassification.WATER_CREATURE) {
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * For vehicles, the first passenger is generally considered the controller and "drives" the vehicle. For example,
     * Pigs, Horses, and Boats are generally "steered" by the controlling passenger.
     */
    @Nullable
    public Entity getControllingPassenger()
    {
        return this.getPassengers().isEmpty() ? null : (Entity)this.getPassengers().get(0);
    }

    @Override
    public String getClazzString() {
        return EntityList.getEntityString(this);
    }

    @Override
    public boolean getIsGhost() {
        return false;
    }

    @Override
    public void setLeashHolder(Entity entityIn, boolean sendAttachNotification) {
        if (this.getIsTamed() && entityIn instanceof PlayerEntity) {
            PlayerEntity entityplayer = (PlayerEntity) entityIn;
            if (MoCreatures.proxy.enableOwnership && this.getOwnerId() != null
                    && !entityplayer.getUniqueID().equals(this.getOwnerId()) && !MoCTools.isThisPlayerAnOP((entityplayer))) {
                return;
            }
        }
        super.setLeashHolder(entityIn, sendAttachNotification);
    }
}
