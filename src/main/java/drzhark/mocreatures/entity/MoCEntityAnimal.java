package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import drzhark.mocreatures.entity.ai.PathNavigateFlyerMoC;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.registry.MoCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;

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

    protected static final DataParameter<Boolean> ADULT = EntityDataManager.<Boolean>defineId(MoCEntityAnimal.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>defineId(MoCEntityAnimal.class, DataSerializers.INT);
    protected static final DataParameter<Integer> AGE = EntityDataManager.<Integer>defineId(MoCEntityAnimal.class, DataSerializers.INT);
    protected static final DataParameter<String> NAME_STR = EntityDataManager.<String>defineId(MoCEntityAnimal.class, DataSerializers.STRING);

    public MoCEntityAnimal(EntityType <? extends MoCEntityAnimal> type, World world) {
        super(type, world);
        this.riderIsDisconnecting = false;
        this.isTameable = false;
        this.texture = "blank.jpg";
        this.navigatorWater = new SwimmerPathNavigator(this, world);
        this.moveControl = new EntityAIMoverHelperMoC(this);
        this.navigatorFlyer = new PathNavigateFlyerMoC(this, world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return AnimalEntity.createLivingAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
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

    @Nullable
    @Override
    public AgeableEntity getBreedOffspring(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    /**
     * Put your code to choose a texture / the mob type in here. Will be called
     * by default MocEntity constructors.
     */
    @Override
    public void selectType() {
        setType(1);
    }

    /*@Override
    public AgeableEntity createChild(AgeableEntity var1) {
        return null;
    }*/

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

    public void setDisplayName(boolean flag) {
    }

    @Override
    public boolean renderName() {
//        return MoCreatures.proxy.getDisplayPetName()
//                && (getPetName() != null && !getPetName().equals("") && (!this.isBeingRidden()) && (this.getRidingEntity() == null));
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
    public void setPetName(String name) {
        this.entityData.set(NAME_STR, name);
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
    public boolean removeWhenFarAway(double d) {
        if (MoCConfig.COMMON_CONFIG.GLOBAL.forceDespawns.get()) {
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

    public LivingEntity getClosestTarget(Entity entity, double d) {
        double d1 = -1D;
        LivingEntity entityliving = null;
        List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (!(entity1 instanceof LivingEntity) || (entity1 == entity) || (entity1 == entity.getVehicle())
                    || (entity1 == entity.getVehicle()) || (entity1 instanceof PlayerEntity) || (entity1 instanceof MobEntity)
                    || (this.getBbHeight() <= entity1.getBbHeight()) || (this.getBbWidth() <= entity1.getBbWidth())) {
                continue;
            }
            double d2 = entity1.distanceToSqr(entity.getY(), entity.getZ(), entity.getDeltaMovement().x);
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1)) && ((LivingEntity) entity1).canSee(entity)) {
                d1 = d2;
                entityliving = (LivingEntity) entity1;
            }
        }

        return entityliving;
    }

    protected LivingEntity getClosestSpecificEntity(Entity entity, Class<? extends LivingEntity> myClass, double d) {
        double d1 = -1D;
        LivingEntity entityliving = null;
        List<Entity> list = this.level.getEntities(entity, entity.getBoundingBox().expandTowards(d, d, d));
        for (int i = 0; i < list.size(); i++) {
            Entity entity1 = list.get(i);
            if (!myClass.isAssignableFrom(entity1.getClass())) {
                continue;
            }

            double d2 = entity1.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());
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
                || ((entity instanceof WolfEntity) && !(MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.attackWolves.get()))
                || ((entity instanceof MoCEntityHorse) && !(MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.attackHorses.get()))
                || (entity.getBbWidth() >= this.getBbWidth() || entity.getBbHeight() >= this.getBbHeight()) || (entity instanceof MoCEntityEgg) || ((entity instanceof IMoCEntity) && !MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.enableHunters.get()));
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
    public void tick() {
        if (!this.level.isClientSide) {
            if (rideableEntity() && this.isVehicle()) {
                Riding();
            }

            if (isMovementCeased()) {
                this.getNavigation().stop();
            }
            if (getEdad() == 0) setEdad(getMaxEdad() - 10); //fixes tiny creatures spawned by error
            if (!getIsAdult() && (this.random.nextInt(300) == 0) && getEdad() <= getMaxEdad()) {
                setEdad(getEdad() + 1);
                if (getEdad() >= getMaxEdad()) {
                    setAdult(true);
                }
            }

            if (MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.enableHunters.get() && this.isReadyToHunt() && !this.getIsHunting() && this.random.nextInt(500) == 0) {
                setIsHunting(true);
            }

            if (getIsHunting() && ++this.huntingCounter > 50) {
                setIsHunting(false);
            }

            this.getNavigation().tick();
        }

        if (this.isInWater() && this.isAmphibian()) {
            if (this.random.nextInt(500) == 0 || !this.randomAttributesUpdated) {
                this.setNewDivingDepth();
                this.randomAttributesUpdated = true;
            }

        }

        if (this.canRidePlayer() && this.isPassenger()) MoCTools.dismountSneakingPlayer(this);
        this.resetLove(); 
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
//        if (isAmphibian()) {
//            return this.world.handleMaterialAcceleration(this.getBoundingBox().expand(0.0D, -0.2D, 0.0D), Material.WATER, this);
//        }
        return super.isInWater();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return isAmphibian();
    }

    public ItemEntity getClosestItem(Entity entity, double d, Item item, Item item1) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = list.get(k);
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            if ((entityitem1.getItem().getItem() != item) && (entityitem1.getItem().getItem() != item1)) {
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

    public ItemEntity getClosestEntityItem(Entity entity, double d) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = list.get(k);
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            double d2 = entityitem1.distanceToSqr(entity.getX(), entity.getY(), entity.getZ());
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
        List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = list.get(k);
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            if (!MoCTools.isItemEdible(entityitem1.getItem().getItem())) {
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

    public void faceLocation(int i, int j, int k, float f) {
        double var4 = i + 0.5D - this.getX();
        double var8 = k + 0.5D - this.getZ();
        double var6 = j + 0.5D - this.getY();
        double var14 = MathHelper.sqrt(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float) (-(Math.atan2(var6, var14) * 180.0D / Math.PI));
        this.xRot = -this.updateRotation2(this.xRot, var13, f);
        this.yRot = this.updateRotation2(this.yRot, var12, f);
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
        Path pathentity = this.getNavigation().createPath(entity, 0);
        if (pathentity != null) {
            this.getNavigation().moveTo(pathentity, 1D);
        }
    }

    /**
     * Called to make ridden entities pass on collision to rider
     */
    public void Riding() {
        if ((this.isVehicle()) && (this.getVehicle() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getVehicle();
            List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(1.0D, 0.0D, 1.0D));
            if (list != null) {
                for (int i = 0; i < list.size(); i++) {
                    Entity entity = list.get(i);
                    if (!entity.isAlive()) {
                        continue;
                    }
                    entity.playerTouch(entityplayer);
                    if (!(entity instanceof MobEntity)) {
                        continue;
                    }
                    float f = distanceTo(entity);
                    if ((f < 2.0F) && entity instanceof MobEntity && (this.random.nextInt(10) == 0)) {
                        hurt(DamageSource.mobAttack((LivingEntity) entity),
                                (float) ((MobEntity) entity).getAttribute(Attributes.ATTACK_DAMAGE).getBaseValue());
                    }
                }
            }
            if (entityplayer.isCrouching()) {
                this.makeEntityDive();
            }
        }
    }

    protected void getPathOrWalkableBlock(Entity entity, float f) {
        Path pathentity = this.navigation.createPath(new BlockPos(entity.position()), 0); //TODO: I'm not sure what the int parameter here is for.
        if ((pathentity == null) && (f > 8F)) {
            int i = MathHelper.floor(entity.getX()) - 2;
            int j = MathHelper.floor(entity.getZ()) - 2;
            int k = MathHelper.floor(entity.getBoundingBox().minY);
            for (int l = 0; l <= 4; l++) {
                for (int i1 = 0; i1 <= 4; i1++) {
                    BlockPos pos = new BlockPos(i, k, j);
                    if (((l < 1) || (i1 < 1) || (l > 3) || (i1 > 3)) && this.level.getBlockState(pos.offset(l, -1, i1)).isRedstoneConductor(level, pos)
                            && !this.level.getBlockState(pos.offset(l, 0, i1)).isRedstoneConductor(level, pos)
                            && !this.level.getBlockState(pos.offset(l, 1, i1)).isRedstoneConductor(level, pos)) {
                        moveTo((i + l) + 0.5F, k, (j + i1) + 0.5F, this.yRot, this.xRot);
                        return;
                    }
                }
            }
        } else {
            this.navigation.moveTo(pathentity, 16F);
        }
    }

    public MoCEntityAnimal spawnBabyAnimal(AgeableEntity par1EntityAgeable) {
        return null;
    }

    public boolean getCanSpawnHereCreature() {
        BlockPos pos =
                new BlockPos(MathHelper.floor(this.getX()), MathHelper.floor(this.getBoundingBox().minY),
                        MathHelper.floor(this.getZ()));
        return this.getWalkTargetValue(pos) >= 0.0F;
    }

    public boolean getCanSpawnHereLiving() {
        return this.level.isUnobstructed(this)
                && this.level.getBlockCollisions(this, this.getBoundingBox()).count() == 0
                && !this.level.containsAnyLiquid(this.getBoundingBox());
    }

    @Override
    public boolean checkSpawnRules(IWorld worldIn, SpawnReason reason) {
        /*if (MoCreatures.entityMap.get(this.getType()).getFrequency() <= 0) {
            return false;
        }*/
        /*if (this.world.dimension.getType().getId() != 0) {
            return getCanSpawnHereCreature() && getCanSpawnHereLiving();
        }*/
        BlockPos pos = new BlockPos(MathHelper.floor(this.getX()), MathHelper.floor(getBoundingBox().minY), this.getZ());

        /*String s = MoCTools.biomeName(this.world, pos); TODO: Biomes have changed, rewrite this.

        if (s.toLowerCase().contains("jungle")) {
            return getCanSpawnHereJungle();
        }
        if (s.equals("WyvernBiome")) {
            return getCanSpawnHereMoCBiome();
        }*/
        return super.checkSpawnRules(worldIn, reason);
    }

    private boolean getCanSpawnHereMoCBiome() {
        if (this.level.isUnobstructed(this)
                && this.level.getBlockCollisions(this, this.getBoundingBox()).count() == 0
                && !this.level.containsAnyLiquid(this.getBoundingBox())) {
            BlockPos pos =
                    new BlockPos(MathHelper.floor(this.getX()), MathHelper.floor(this.getBoundingBox().minY),
                            MathHelper.floor(this.getZ()));

            if (pos.getY() < 50) {
                return false;
            }

            BlockState blockstate = this.level.getBlockState(pos.below());
            final Block block = blockstate.getBlock();

            if (block == MoCBlocks.WYVERN_DIRT || block == MoCBlocks.OGRE_DIRT || block == MoCBlocks.WYVERN_GRASS || block == MoCBlocks.OGRE_GRASS
                    || (block.getTags().contains(BlockTags.LEAVES))) {
                return true;
            }
        }
        return false;
    }

    public boolean getCanSpawnHereJungle() {
        if (this.level.isUnobstructed(this) //Original: this.getBoundingBox() instead of this
                && this.level.getBlockCollisions(this, this.getBoundingBox()).count() == 0) {
            return true;
        }
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

    /**
     * Moves the entity based on the specified heading.  Args: strafe, forward
     */
    @Override
    public void travel(Vector3d movement) {

            if (this.isVehicle()) {
                LivingEntity passenger = (LivingEntity)this.getControllingPassenger();
                if (passenger != null)this.moveWithRider((float)movement.x, (float)movement.y, (float)movement.z, passenger); //riding movement
                return;
            }
            if ((this.isAmphibian() && isInWater()) || (this.isFlyer() && getIsFlying())) { //amphibian in water movement
                this.moveRelative(0.1F, movement);
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.getDeltaMovement().scale(0.8999999761581421D);
                if (this.getTarget() == null) {
                    this.getDeltaMovement().subtract(0, 0.005D, 0);
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
        if (this.isVehicle() && !getIsTamed()) {
            this.moveEntityWithRiderUntamed(strafe, forward, passenger);
            return;
        }
        boolean flySelfPropelled = selfPropelledFlyer() && isOnAir(); //like the black ostrich
        boolean flyingMount = isFlyer() && (this.isVehicle()) && getIsTamed() && !this.onGround && isOnAir();
        this.yRot = passenger.yRot;
        this.yRotO = this.yRot;
        this.xRot = passenger.xRot * 0.5F;
        this.setRot(this.yRot, this.xRot);
        this.yBodyRot = this.yRot;
        this.yHeadRot = this.yBodyRot;
        if (!selfPropelledFlyer() || (selfPropelledFlyer() && !isOnAir())) {
            strafe = (float) (passenger.xxa * 0.5F * this.getCustomSpeed());
            forward = (float) (passenger.zza * this.getCustomSpeed());
        }

        if (this.jumpPending && (isFlyer())) {
            this.getDeltaMovement().add(0, flyerThrust(), 0);//0.3D;
            this.jumpPending = false;

            if (flySelfPropelled) {
                float velX = MathHelper.sin(this.yRot * (float) Math.PI / 180.0F);
                float velZ = MathHelper.cos(this.yRot * (float) Math.PI / 180.0F);

                this.getDeltaMovement().add(-0.5F * velX, 0, 0.5F * velZ);
            }
        } else if (this.jumpPending && !getIsJumping()) {
            this.setDeltaMovement(this.getDeltaMovement().x, getCustomJump()*2, this.getDeltaMovement().z);
            setIsJumping(true);
            this.jumpPending = false;
        }

        if (this.divePending) {
            this.divePending = false;
            this.getDeltaMovement().subtract(0, 0.3D, 0);
        }
            if (flyingMount) {
                this.move(MoverType.SELF, this.getDeltaMovement());
                this.moveRelative(this.flyerFriction() / 10F, new Vector3d(strafe, vertical, forward));
                this.getDeltaMovement().multiply(this.flyerFriction(), this.myFallSpeed(), this.flyerFriction());
                this.getDeltaMovement().subtract(0, 0.055D, 0);
            } else {
                this.setSpeed((float) this.getAttribute(Attributes.MOVEMENT_SPEED).getBaseValue());
                super.travel(new Vector3d(strafe, vertical, forward));

        }
        if (this.onGround) {
            setIsJumping(false);
            this.divePending = false;
            this.jumpPending = false;
        }
    }

    public void moveEntityWithRiderUntamed(float strafe, float forward, LivingEntity passenger) {
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
            if (!this.level.isClientSide) {
                move(MoverType.SELF, getDeltaMovement());
            }
            if (!this.level.isClientSide && this.random.nextInt(50) == 0) {
                passenger.getDeltaMovement().add(0, 0.9D, -0.3D);
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
        List<Entity> list = world.getEntities(entity1, entity1.getBoundingBox().expandTowards(dist, 4D, dist));
        for (int i = 0; i < list.size(); i++) {
            Entity entity = list.get(i);
            if (!(entity instanceof MobEntity)) {
                continue;
            }
            MobEntity entitymob = (MobEntity) entity;
            entitymob.setTarget(null);
            entitymob.getNavigation().stop();
        }
    }

    public void faceItem(int i, int j, int k, float f) {
        double d = i - this.getX();
        double d1 = k - this.getZ();
        double d2 = j - this.getY();
        double d3 = MathHelper.sqrt((d * d) + (d1 * d1));
        float f1 = (float) ((Math.atan2(d1, d) * 180D) / 3.1415927410125728D) - 90F;
        float f2 = (float) ((Math.atan2(d2, d3) * 180D) / 3.1415927410125728D);
        this.xRot = -adjustRotation(this.xRot, f2, f);
        this.yRot = adjustRotation(this.yRot, f1, f);
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
        PlayerEntity entityplayer1 = this.level.getNearestPlayer(this, 24D);
        if (entityplayer1 == null) {
            return;
        }

        ItemStack itemstack1 = entityplayer1.inventory.getSelected();
        if (itemstack1 != null && isMyFavoriteFood(itemstack1)) {
            this.getNavigation().moveTo(entityplayer1, 1D); 
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
        return (this.level.isEmptyBlock(new BlockPos(MathHelper.floor(this.getX()), MathHelper.floor(this.getY() - 0.2D), MathHelper
                .floor(this.getZ()))) && this.level.isEmptyBlock(new BlockPos(MathHelper.floor(this.getX()), MathHelper
                .floor(this.getY() - 1.2D), MathHelper.floor(this.getZ()))));
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
    public void die(DamageSource damagesource) {
        if (!this.level.isClientSide) {
            dropMyStuff();
        }

        super.die(damagesource);
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (isNotScared()) {
            Entity tempEntity = this.getTarget();
            boolean flag = super.hurt(damagesource, i);
            setTarget((LivingEntity) tempEntity);
            return flag;
        }

        return super.hurt(damagesource, i);
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
        return this.getBbHeight() >= entity.getBbHeight() && this.getBbWidth() >= entity.getBbWidth();
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

    public boolean isReadyToHunt() {
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
    public boolean isMovementCeased() {
        return getIsSitting() || this.isVehicle();
    }

    public boolean getIsHunting() {
        return this.huntingCounter != 0;
    }

    public void setIsHunting(boolean flag) {
        if (flag) {
            this.huntingCounter = this.random.nextInt(30) + 1;
        } else {
            this.huntingCounter = 0;
        }
    }

    @Override
    public boolean shouldAttackPlayers() {
        return !getIsTamed() && this.level.getDifficulty() != Difficulty.PEACEFUL;
    }

    /*@Override
    public void onKillEntity(LivingEntity entityLivingIn) {
        if (!(entityLivingIn instanceof PlayerEntity)) {
            MoCTools.destroyDrops(this, 3D);
        }
    }*/

    @Override
    public PathNavigator getNavigation() {
        if (this.isInWater() && this.isAmphibian()) {
            return this.navigatorWater;
        }
        if (this.isFlyer() && getIsFlying()) {
            return this.navigatorFlyer;
        }
        return this.navigation;
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

            this.divingDepth = (float) ((this.random.nextDouble() * (maxDivingDepth() - minDivingDepth())) + minDivingDepth());
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
        this.jumpFromGround();
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

//    @Override
//    public String getClazzString() {
//        return EntityList.getEntityString(this);
//    }

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
