package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import drzhark.mocreatures.entity.ai.PathNavigateFlyerMoC;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.List;
import java.util.UUID;

import javax.annotation.Nullable;

public abstract class MoCEntityAnimal extends AnimalEntity implements IMoCEntity {

    protected boolean divePending;
    protected boolean jumpPending;
    protected int temper;
    protected boolean isEntityJumping;
    // used by MoCPlayerTracker to prevent dupes when a player disconnects on animal from server
    protected boolean riderIsDisconnecting;
    public float moveSpeed;
    protected String texture;
    private int huntingCounter;
    protected boolean isTameable;
    protected PathNavigator navigatorWater;
    protected PathNavigator navigatorFlyer;

    private double divingDepth;
    private boolean randomAttributesUpdated; //used to update divingDepth on world load 

    protected static final DataParameter<Boolean> ADULT = EntityDataManager.<Boolean>createKey(MoCEntityAnimal.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(MoCEntityAnimal.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> AGE = EntityDataManager.<Integer>createKey(MoCEntityAnimal.class, DataSerializers.VARINT);
    protected static final DataParameter<String> NAME_STR = EntityDataManager.<String>createKey(MoCEntityAnimal.class, DataSerializers.STRING);

    public MoCEntityAnimal(EntityType <? extends MoCEntityAnimal> type, World world) {
        super(type, world);
        this.riderIsDisconnecting = false;
        this.isTameable = false;
        this.texture = "blank.jpg";
        this.navigatorWater = new SwimmerPathNavigator(this, world);
        this.moveController = new EntityAIMoverHelperMoC(this);
        this.navigatorFlyer = new PathNavigateFlyerMoC(this, world);
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture(this.texture);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IWorld worldIn, DifficultyInstance difficulty, SpawnReason reason, ILivingEntityData par1EntityLivingData, CompoundNBT dataTag) {
        selectType();
        return super.onInitialSpawn(worldIn, difficulty, reason, par1EntityLivingData, dataTag);
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
    public AgeableEntity createChild(AgeableEntity var1) {
        return null;
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

    public void setDisplayName(boolean flag) {
    }

    @Override
    public boolean renderName() {
        return MoCreatures.proxy.getDisplayPetName()
                && (getPetName() != null && !getPetName().equals("") && (!this.isBeingRidden()) && (this.getRidingEntity() == null));
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
    public void setPetName(String name) {
        this.dataManager.set(NAME_STR, name);
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
    public boolean getIsTamed() {
        return false;
    }

    @Override
    public int getOwnerPetId() {
        return 0;
    }

    @Override
    public void setOwnerPetId(int petId) {
    }

    @Override
    public UUID getOwnerId() {
        return null;
    }

    public boolean getIsJumping() {
        return this.isEntityJumping;
    }

    public void setIsJumping(boolean flag) {
        this.isEntityJumping = flag;
    }

    @Override
    protected boolean canDespawn() {
        if (MoCreatures.proxy.forceDespawns) {
            return !getIsTamed();
        } else {
            return false;
        }
    }

    /**
     * called in getCanSpawnHere to make sure the right type of creature spawns
     * in the right biome i.e. snakes, rays, bears, BigCats and later wolves,
     * etc.
     */
    @Override
    public boolean checkSpawningBiome() {
        return true;
    }

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

    public LivingEntity getClosestTarget(Entity entity, double d) {
        double d1 = -1D;
        LivingEntity entityliving = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (!(entity1 instanceof LivingEntity) || (entity1 == entity) || (entity1 == entity.getRidingEntity())
                    || (entity1 == entity.getRidingEntity()) || (entity1 instanceof PlayerEntity) || (entity1 instanceof MobEntity)
                    || (this.getHeight() <= entity1.getHeight()) || (this.getWidth() <= entity1.getWidth())) {
                continue;
            }
            double d2 = entity1.getDistanceSq(entity.getPosY(), entity.getPosZ(), entity.getMotion().x);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((LivingEntity) entity1).canEntityBeSeen(entity)) {
                d1 = d2;
                entityliving = (LivingEntity) entity1;
            }
        }

        return entityliving;
    }

    protected LivingEntity getClosestSpecificEntity(Entity entity, Class<? extends LivingEntity> myClass, double d) {
        double d1 = -1D;
        LivingEntity entityliving = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(entity, entity.getBoundingBox().expand(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (!myClass.isAssignableFrom(entity1.getClass())) {
                continue;
            }

            double d2 = entity1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)))// && ((EntityLiving) entity1).canEntityBeSeen(entity))
            {
                d1 = d2;
                entityliving = (LivingEntity) entity1;
            }
        }

        return entityliving;
    }

    public boolean entitiesToIgnore(Entity entity) {
        return ((!(entity instanceof LivingEntity)) || (entity instanceof MobEntity) || (entity instanceof PlayerEntity)
                || (entity instanceof MoCEntityKittyBed) || (entity instanceof MoCEntityLitterBox)
                || (this.getIsTamed() && (entity instanceof IMoCEntity && ((IMoCEntity) entity).getIsTamed()))
                || ((entity instanceof WolfEntity) && !(MoCreatures.proxy.attackWolves))
                || ((entity instanceof MoCEntityHorse) && !(MoCreatures.proxy.attackHorses))
                || (entity.getWidth() >= this.getWidth() || entity.getHeight() >= this.getHeight()) || (entity instanceof MoCEntityEgg) || ((entity instanceof IMoCEntity) && !MoCreatures.proxy.enableHunters));
    }

    /**
     * Finds and entity described in entitiesToInclude at d distance
     *
     * @param d
     * @return
     */
    protected LivingEntity getBoogey(double d) {
        LivingEntity entityliving = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(d, 4D, d));
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
        return ((entity.getClass() != this.getClass()) && (entity instanceof LivingEntity) && ((entity.getWidth() >= 0.5D) || (entity.getHeight() >= 0.5D)));
    }

    @Override
    public void tick() {
        if (!this.world.isRemote) {
            if (rideableEntity() && this.isBeingRidden()) {
                Riding();
            }

            if (isMovementCeased()) {
                this.getNavigator().clearPath();
            }
            if (getEdad() == 0) setEdad(getMaxEdad() - 10); //fixes tiny creatures spawned by error
            if (!getIsAdult() && (this.rand.nextInt(300) == 0) && getEdad() <= getMaxEdad()) {
                setEdad(getEdad() + 1);
                if (getEdad() >= getMaxEdad()) {
                    setAdult(true);
                }
            }

            if (MoCreatures.proxy.enableHunters && this.isReadyToHunt() && !this.getIsHunting() && this.rand.nextInt(500) == 0) {
                setIsHunting(true);
            }

            if (getIsHunting() && ++this.huntingCounter > 50) {
                setIsHunting(false);
            }

            this.getNavigator().tick();
        }

        if (this.isInWater() && this.isAmphibian()) {
            if (this.rand.nextInt(500) == 0 || !this.randomAttributesUpdated) {
                this.setNewDivingDepth();
                this.randomAttributesUpdated = true;
            }

        }

        if (this.canRidePlayer() && this.isRiding()) MoCTools.dismountSneakingPlayer(this);
        this.resetInLove(); 
        super.tick();
    }

    public int getMaxEdad() {
        return 100;
    }

    @Override
    public boolean isNotScared() {
        return false;
    }

    public boolean swimmerEntity() {
        return false;
    }

    public boolean isSwimming() {
        return ((super.isSwimming()));
    }

    /**
     * Used to breed
     *
     * @param item1
     * @return
     */
    public boolean isMyAphrodisiac(Item item1) {
        return false;
    }

    //used to drop armor, inventory, saddles, etc.
    public void dropMyStuff() {
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
    public boolean isInWater() {
        if (isAmphibian()) {
            return this.world.handleMaterialAcceleration(this.getBoundingBox().expand(0.0D, -0.2D, 0.0D), Material.WATER, this);
        }
        return super.isInWater();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return isAmphibian();
    }

    public ItemEntity getClosestItem(Entity entity, double d, Item item, Item item1) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = list.get(k);
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            if ((entityitem1.getItem().getItem() != item) && (entityitem1.getItem().getItem() != item1)) {
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

    public ItemEntity getClosestEntityItem(Entity entity, double d) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = list.get(k);
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            double d2 = entityitem1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityitem = entityitem1;
            }
        }

        return entityitem;
    }

    public ItemEntity getClosestFood(Entity entity, double d) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = list.get(k);
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            if (!MoCTools.isItemEdible(entityitem1.getItem().getItem())) {
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

    public void faceLocation(int i, int j, int k, float f) {
        double var4 = i + 0.5D - this.getPosX();
        double var8 = k + 0.5D - this.getPosZ();
        double var6 = j + 0.5D - this.getPosY();
        double var14 = MathHelper.sqrt(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float) (-(Math.atan2(var6, var14) * 180.0D / Math.PI));
        this.rotationPitch = -this.updateRotation2(this.rotationPitch, var13, f);
        this.rotationYaw = this.updateRotation2(this.rotationYaw, var12, f);
    }

    /**
     * Arguments: current rotation, intended rotation, max increment.
     */
    private float updateRotation2(float par1, float par2, float par3) {
        float var4;

        for (var4 = par2 - par1; var4 < -180.0F; var4 += 360.0F) {
            ;
        }

        while (var4 >= 180.0F) {
            var4 -= 360.0F;
        }

        if (var4 > par3) {
            var4 = par3;
        }

        if (var4 < -par3) {
            var4 = -par3;
        }

        return par1 + var4;
    }

    public void getMyOwnPath(Entity entity, float f) {
        Path pathentity = this.getNavigator().getPathToEntity(entity, 0);
        if (pathentity != null) {
            this.getNavigator().setPath(pathentity, 1D);
        }
    }

    /**
     * Called to make ridden entities pass on collision to rider
     */
    public void Riding() {
        if ((this.isBeingRidden()) && (this.getRidingEntity() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getRidingEntity();
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(1.0D, 0.0D, 1.0D));
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Entity entity = list.get(i);
                    if (!entity.isAlive()) {
                        continue;
                    }
                    entity.onCollideWithPlayer(entityplayer);
                    if (!(entity instanceof MobEntity)) {
                        continue;
                    }
                    float f = getDistance(entity);
                    if ((f < 2.0F) && entity instanceof MobEntity && (this.rand.nextInt(10) == 0)) {
                        attackEntityFrom(DamageSource.causeMobDamage((LivingEntity) entity),
                                (float) ((MobEntity) entity).getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue());
                    }
                }
            }
            if (entityplayer.isCrouching()) {
                this.makeEntityDive();
            }
        }
    }

    protected void getPathOrWalkableBlock(Entity entity, float f) {
        Path pathentity = this.navigator.getPathToPos(entity.getPosition());
        if ((pathentity == null) && (f > 8F)) {
            int i = MathHelper.floor(entity.getPosX()) - 2;
            int j = MathHelper.floor(entity.getPosZ()) - 2;
            int k = MathHelper.floor(entity.getBoundingBox().minY);
            for (int l = 0; l <= 4; l++) {
                for (int i1 = 0; i1 <= 4; i1++) {
                    BlockPos pos = new BlockPos(i, k, j);
                    if (((l < 1) || (i1 < 1) || (l > 3) || (i1 > 3)) && this.world.getBlockState(pos.add(l, -1, i1)).isNormalCube()
                            && !this.world.getBlockState(pos.add(l, 0, i1)).isNormalCube()
                            && !this.world.getBlockState(pos.add(l, 1, i1)).isNormalCube()) {
                        setLocationAndAngles((i + l) + 0.5F, k, (j + i1) + 0.5F, this.rotationYaw, this.rotationPitch);
                        return;
                    }
                }
            }
        } else {
            this.navigator.setPath(pathentity, 16F);
        }
    }

    public MoCEntityAnimal spawnBabyAnimal(AgeableEntity par1EntityAgeable) {
        return null;
    }

    public boolean getCanSpawnHereCreature() {
        BlockPos pos =
                new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getBoundingBox().minY),
                        MathHelper.floor(this.getPosZ()));
        return this.getBlockPathWeight(pos) >= 0.0F;
    }

    public boolean getCanSpawnHereLiving() {
        return this.world.checkNoEntityCollision(this.getBoundingBox())
                && this.world.getCollisionBoxes(this, this.getBoundingBox()).isEmpty()
                && !this.world.containsAnyLiquid(this.getBoundingBox());
    }

    @Override
    public boolean getCanSpawnHere() {
        if (MoCreatures.entityMap.get(this.getClass()).getFrequency() <= 0) {
            return false;
        }
        if (this.world.dimension.getType().getId() != 0) {
            return getCanSpawnHereCreature() && getCanSpawnHereLiving();
        }
        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY), this.getPosZ());

        String s = MoCTools.biomeName(this.world, pos);

        if (s.toLowerCase().contains("jungle")) {
            return getCanSpawnHereJungle();
        }
        if (s.equals("WyvernBiome")) {
            return getCanSpawnHereMoCBiome();
        }
        return super.getCanSpawnHere();
    }

    private boolean getCanSpawnHereMoCBiome() {
        if (this.world.checkNoEntityCollision(this.getBoundingBox())
                && this.world.getCollisionBoxes(this, this.getBoundingBox()).isEmpty()
                && !this.world.containsAnyLiquid(this.getBoundingBox())) {
            BlockPos pos =
                    new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getBoundingBox().minY),
                            MathHelper.floor(this.getPosZ()));

            if (pos.getY() < 50) {
                return false;
            }

            BlockState blockstate = this.world.getBlockState(pos.down());
            final Block block = blockstate.getBlock();

            if (block == MoCBlocks.WYVERN_DIRT || block == MoCBlocks.OGRE_DIRT || block == MoCBlocks.WYVERN_GRASS || block == MoCBlocks.OGRE_GRASS
                    || (block != null && block.isLeaves(blockstate, this.world, pos.down()))) {
                return true;
            }
        }
        return false;
    }

    public boolean getCanSpawnHereJungle() {
        if (this.world.checkNoEntityCollision(this.getBoundingBox())
                && this.world.getCollisionShapes(this, this.getBoundingBox()).isEmpty()) {
            return true;
        }
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

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    @Override
    public void travel(Vec3d movement) {

            if (this.isBeingRidden()) {
                LivingEntity passenger = (LivingEntity)this.getControllingPassenger();
                if (passenger != null)this.moveWithRider((float)movement.x, (float)movement.y, (float)movement.z, passenger); //riding movement
                return;
            }
            if ((this.isAmphibian() && isInWater()) || (this.isFlyer() && getIsFlying())) { //amphibian in water movement
                this.moveRelative(0.1F, movement);
                this.move(MoverType.SELF, this.getMotion());
                this.getMotion().scale(0.8999999761581421D);
                if (this.getAttackTarget() == null) {
                    this.getMotion().subtract(0, 0.005D, 0);
                }
            } else // regular movement
            {
                super.travel(movement);
            }
        
    }

    /**
     ** Riding Code
     * @param strafe
     * @param forward
     * @param passenger 
     */
    public void moveWithRider(float strafe, float vertical, float forward, LivingEntity passenger) {
        if (passenger == null)
        {
            return;
        }
        if (this.isBeingRidden() && !getIsTamed()) {
            this.moveEntityWithRiderUntamed(strafe, forward, passenger);
            return;
        }
        boolean flySelfPropelled = selfPropelledFlyer() && isOnAir(); //like the black ostrich
        boolean flyingMount = isFlyer() && (this.isBeingRidden()) && getIsTamed() && !this.onGround && isOnAir();
        this.rotationYaw = passenger.rotationYaw;
        this.prevRotationYaw = this.rotationYaw;
        this.rotationPitch = passenger.rotationPitch * 0.5F;
        this.setRotation(this.rotationYaw, this.rotationPitch);
        this.renderYawOffset = this.rotationYaw;
        this.rotationYawHead = this.renderYawOffset;
        if (!selfPropelledFlyer() || (selfPropelledFlyer() && !isOnAir())) {
            strafe = (float) (passenger.moveStrafing * 0.5F * this.getCustomSpeed());
            forward = (float) (passenger.moveForward * this.getCustomSpeed());
        }

        if (this.jumpPending && (isFlyer())) {
            this.getMotion().add(0, flyerThrust(), 0);//0.3D;
            this.jumpPending = false;

            if (flySelfPropelled) {
                float velX = MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F);
                float velZ = MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F);

                this.getMotion().add(-0.5F * velX, 0, 0.5F * velZ);
            }
        } else if (this.jumpPending && !getIsJumping()) {
            this.setMotion(this.getMotion().x, getCustomJump()*2, this.getMotion().z);
            setIsJumping(true);
            this.jumpPending = false;
        }

        if (this.divePending) {
            this.divePending = false;
            this.getMotion().subtract(0, 0.3D, 0);
        }
            if (flyingMount) {
                this.move(MoverType.SELF, this.getMotion());
                this.moveRelative(this.flyerFriction() / 10F, new Vec3d(strafe, vertical, forward));
                this.getMotion().mul(this.flyerFriction(), this.myFallSpeed(), this.flyerFriction());
                this.getMotion().subtract(0, 0.055D, 0);
            } else {
                this.setAIMoveSpeed((float) this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue());
                super.travel(new Vec3d(strafe, vertical, forward));

        }
        if (this.onGround) {
            setIsJumping(false);
            this.divePending = false;
            this.jumpPending = false;
        }
    }

    public void moveEntityWithRiderUntamed(float strafe, float forward, LivingEntity passenger) {
        //Riding behaviour if untamed
        if ((this.isBeingRidden()) && !getIsTamed()) {
            if ((this.rand.nextInt(5) == 0) && !getIsJumping() && this.jumpPending) {
                this.getMotion().add(0, getCustomJump(), 0);
                setIsJumping(true);
                this.jumpPending = false;
            }
            if (this.rand.nextInt(10) == 0) {
                this.getMotion().add(this.rand.nextDouble() / 30D, 0, this.rand.nextDouble() / 10D);
            }
            if (!this.world.isRemote) {
                move(MoverType.SELF, getMotion());
            }
            if (!this.world.isRemote && this.rand.nextInt(50) == 0) {
                passenger.getMotion().add(0, 0.9D, -0.3D);
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
     * Maximum flyer height when moving autonomously
     *
     * @return
     */
    public int maxFlyingHeight() {
        return 5;
    }

    /**
     * Used for flyer mounts, to calculate fall speed
     *
     * @return
     */
    protected double myFallSpeed() {
        return 0.6D;
    }

    /**
     * flyer mounts Y thrust
     *
     * @return
     */
    protected double flyerThrust() {
        return 0.3D;
    }

    /**
     * flyer deceleration on Z and X axis
     *
     * @return
     */
    protected float flyerFriction() {
        return 0.91F;
    }

    /**
     * Alternative flyer mount movement, when true, the player only controls
     * frequency of wing flaps
     *
     * @return
     */
    protected boolean selfPropelledFlyer() {
        return false;
    }

    /**
     * Sets a flag that will make the Entity "jump" in the next onGround
     * moveEntity update
     */
    @Override
    public void makeEntityJump() {
        this.jumpPending = true;
    }

    /**
     * Boolean used for flying mounts
     */
    public boolean isFlyer() {
        return false;
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

    /**
     * mount speed
     */
    public double getCustomSpeed() {
        return 0.6D;
    }

    /**
     * mount jumping power
     */
    public double getCustomJump() {
        return 0.4D;
    }

    /**
     * sound played when an untamed mount buckles rider
     */
    protected SoundEvent getAngrySound() {
        return null;
    }

    /**
     * Is this a rideable entity?
     */
    public boolean rideableEntity() {
        return false;
    }

    @Override
    public int nameYOffset() {
        return -80;
    }


    /**
     * fixes bug with entities following a player carrying wheat
     */
    protected Entity findPlayerToAttack() {
        return null;
    }

    public void repelMobs(Entity entity1, Double dist, World world) {
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entity1, entity1.getBoundingBox().expand(dist, 4D, dist));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (!(entity instanceof MobEntity)) {
                continue;
            }
            MobEntity entitymob = (MobEntity) entity;
            entitymob.setAttackTarget(null);
            entitymob.getNavigator().clearPath();
        }
    }

    public void faceItem(int i, int j, int k, float f) {
        double d = i - this.getPosX();
        double d1 = k - this.getPosZ();
        double d2 = j - this.getPosY();
        double d3 = MathHelper.sqrt((d * d) + (d1 * d1));
        float f1 = (float) ((Math.atan2(d1, d) * 180D) / 3.1415927410125728D) - 90F;
        float f2 = (float) ((Math.atan2(d2, d3) * 180D) / 3.1415927410125728D);
        this.rotationPitch = -adjustRotation(this.rotationPitch, f2, f);
        this.rotationYaw = adjustRotation(this.rotationYaw, f1, f);
    }

    public float adjustRotation(float f, float f1, float f2) {
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

    public boolean isFlyingAlone() {
        return false;
    }

    /**
     * Used to synchronize animations between server and client
     *
     * @param attackType
     */
    @Override
    public void performAnimation(int attackType) {
    }

    /**
     * Used to follow the player carrying the item
     *
     * @param par1ItemStack
     * @return
     */
    public boolean isMyFavoriteFood(ItemStack par1ItemStack) {
        return false;
    }

    @SuppressWarnings("unused")
    private void followPlayer() {
        PlayerEntity entityplayer1 = this.world.getClosestPlayer(this, 24D);
        if (entityplayer1 == null) {
            return;
        }

        ItemStack itemstack1 = entityplayer1.inventory.getCurrentItem();
        if (itemstack1 != null && isMyFavoriteFood(itemstack1)) {
            this.getNavigator().tryMoveToEntityLiving(entityplayer1, 1D); 
        }
    }

    /**
     * This method must be overrided to work in conjunction with our
     * onLivingUpdate update packets. It is currently used to fix mount bug when
     * players reconnect.
     */
    /*@Override
    public void mountEntity(Entity par1Entity) {
        if (updateMount()) {
            if (par1Entity == null) {
                if (this.getRidingEntity() != null) {
                    this.setLocationAndAngles(this.getRidingEntity().posX, this.getRidingEntity().getEntityBoundingBox().minY + this.getRidingEntity().height,
                            this.getRidingEntity().posZ, this.rotationYaw, this.rotationPitch);
                    this.getRidingEntity().getRidingEntity() = null;
                }
                this.getRidingEntity() = null;
            } else {
                this.getRidingEntity() = par1Entity;
                par1Entity.getRidingEntity() = this;
            }
        } else {
            super.mountEntity(par1Entity);
        }
    }*/

    @Override
    public void makeEntityDive() {
        this.divePending = true;
    }

    public boolean isOnAir() {
        return (this.world.isAirBlock(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY() - 0.2D), MathHelper
                .floor(this.getPosZ()))) && this.world.isAirBlock(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper
                .floor(this.getPosY() - 1.2D), MathHelper.floor(this.getPosZ()))));
    }

    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0F;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        if (!this.world.isRemote) {
            dropMyStuff();
        }

        super.onDeath(damagesource);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (isNotScared()) {
            Entity tempEntity = this.getAttackTarget();
            boolean flag = super.attackEntityFrom(damagesource, i);
            setAttackTarget((LivingEntity) tempEntity);
            return flag;
        }

        return super.attackEntityFrom(damagesource, i);
    }

    public boolean getIsRideable() {
        return false;
    }

    public void setRideable(boolean b) {
    }

    @Override
    public void setArmorType(int i) {
    }

    public int getArmorType() {
        return 0;
    }

    /**
     * Drops armor if the animal has one
     */
    public void dropArmor() {
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

    protected boolean canBeTrappedInNet() {
        return (this instanceof IMoCTameable) && getIsTamed();
    }

    /**
     * used to select which entities run the new AI
     * @return
     */
    /*protected boolean usesNewAI() {
        return false;
    }*/

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return this.getHeight() >= entity.getHeight() && this.getWidth() >= entity.getWidth();
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

    public boolean isReadyToHunt() {
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
    public boolean isMovementCeased() {
        return getIsSitting() || this.isBeingRidden();
    }

    public boolean getIsHunting() {
        return this.huntingCounter != 0;
    }

    public void setIsHunting(boolean flag) {
        if (flag) {
            this.huntingCounter = this.rand.nextInt(30) + 1;
        } else {
            this.huntingCounter = 0;
        }
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsTamed() && this.world.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    public void onKillEntity(LivingEntity entityLivingIn) {
        if (!(entityLivingIn instanceof PlayerEntity)) {
            MoCTools.destroyDrops(this, 3D);
        }
    }

    @Override
    public PathNavigator getNavigator() {
        if (this.isInWater() && this.isAmphibian()) {
            return this.navigatorWater;
        }
        if (this.isFlyer() && getIsFlying()) {
            return this.navigatorFlyer;
        }
        return this.navigator;
    }

    public boolean isAmphibian() {
        return false;
    }

    @Override
    public boolean isDiving() {
        return false;
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

            this.divingDepth = (float) ((this.rand.nextDouble() * (maxDivingDepth() - minDivingDepth())) + minDivingDepth());
        }

    }

    protected void setNewDivingDepth() {
        setNewDivingDepth(0.0D);
    }

    protected double minDivingDepth() {
        return 0.2D;
    }

    protected double maxDivingDepth() {
        return 1.0D;
    }

    @Override
    public void forceEntityJump() {
        this.jump();
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    @Override
    public boolean getIsFlying() {
        return false;
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
        if (type == EntityClassification.CREATURE) {
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
    
    /***
     * Used to select Animals that can 'ride' the player. Like mice, snakes, turtles, birds
     * @return
     */
    public boolean canRidePlayer()
    {
        return false;
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
