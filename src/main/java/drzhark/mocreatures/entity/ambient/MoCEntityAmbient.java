package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.PathNavigateFlyerMoC;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
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
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.Path;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public abstract class MoCEntityAmbient extends AnimalEntity implements IMoCEntity {

    protected static final DataParameter<Boolean> ADULT = EntityDataManager.defineId(MoCEntityAmbient.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> TYPE = EntityDataManager.defineId(MoCEntityAmbient.class, DataSerializers.INT);
    protected static final DataParameter<Integer> AGE = EntityDataManager.defineId(MoCEntityAmbient.class, DataSerializers.INT);
    protected static final DataParameter<String> NAME_STR = EntityDataManager.defineId(MoCEntityAmbient.class, DataSerializers.STRING);

    protected String texture;
    protected boolean riderIsDisconnecting;
    protected PathNavigator navigatorFlyer;

    public MoCEntityAmbient(EntityType<? extends MoCEntityAmbient> type, World world) {
        super(type, world);
        this.navigatorFlyer = new FlyingPathNavigator(this, world);
    }



    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.getTexture(this.texture);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return AnimalEntity.createLivingAttributes()
                .add(Attributes.FOLLOW_RANGE, 16.0D);
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

    @Override
    public void aiStep() {
        if (!this.level.isClientSide) {
            if (isMovementCeased()) {
                this.getNavigation().stop();
            }
            this.getNavigation().recomputePath();
        }
        super.aiStep();
    }

    public boolean swimmerEntity() {
        return false;
    }

    public boolean isSwimming() {
        return ((isInWater()));
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
        if (swimmerEntity()) {
            return false;
        }
        return super.isInWater();
    }

    @Override
    public boolean canBreatheUnderwater() {
        return swimmerEntity();
    }

    public ItemEntity getClosestItem(Entity entity, double d, ItemStack item, ItemStack item1) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(d, d, d));
        for (int k = 0; k < list.size(); k++) {
            Entity entity1 = list.get(k);
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            if ((entityitem1.getItem() != item) && (entityitem1.getItem() != item1)) {
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

    public void faceLocation(int i, int j, int k, float f) {
        double var4 = i + 0.5D - this.getX();
        double var8 = k + 0.5D - this.getZ();
        double var6 = j + 0.5D - this.getY();
        double var14 = MathHelper.sqrt(var4 * var4 + var8 * var8);
        float var12 = (float) (Math.atan2(var8, var4) * 180.0D / Math.PI) - 90.0F;
        float var13 = (float) (-(Math.atan2(var6, var14) * 180.0D / Math.PI));
        this.xRot = -this.updateRotation(this.xRot, var13, f);
        this.yRot = this.updateRotation(this.yRot, var12, f);
    }

    /**
     * Arguments: current rotation, intended rotation, max increment.
     */
    private float updateRotation(float par1, float par2, float par3) {
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
    public void riding() {
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
                    if ((f < 2.0F) && (this.random.nextInt(10) == 0)) {
                        
                    }
                }
            }
            if (entityplayer.isCrouching()) {
                if (!this.level.isClientSide) {
                    entityplayer.stopRiding();
                }
            }
        }
    }

    protected void getPathOrWalkableBlock(Entity entity, float f) {
        Path pathentity = this.navigation.createPath(new BlockPos(entity.position()), 0);
        if ((pathentity == null) && (f > 8F)) {
            int i = MathHelper.floor(entity.getX()) - 2;
            int j = MathHelper.floor(entity.getZ()) - 2;
            int k = MathHelper.floor(entity.getBoundingBox().minY);
            for (int l = 0; l <= 4; l++) {
                for (int i1 = 0; i1 <= 4; i1++) {
                    BlockPos pos = new BlockPos(i, j, k);
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

    public boolean getCanSpawnHereAnimal() {
        int i = MathHelper.floor(this.getX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getZ());
        BlockPos pos = new BlockPos(i, j, k);
        return this.level.getBlockState(pos.below()).getBlock() == Blocks.GRASS && this.level.getMaxLocalRawBrightness(pos) > 8;
    }

    public boolean getCanSpawnHereCreature() {
        int i = MathHelper.floor(this.getX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getZ());
        return getWalkTargetValue(new BlockPos(i, j, k)) >= 0.0F;
    }

    public boolean getCanSpawnHereLiving() {
        return this.level.isUnobstructed(this)
                && this.level.getBlockCollisions(this, this.getBoundingBox()).count() == 0
                && !this.level.containsAnyLiquid(this.getBoundingBox());
    }

    public boolean getCanSpawnHereAquatic() {
        return this.level.isUnobstructed(this);
    }

    @Override
    public boolean checkSpawnRules(IWorld worldIn, SpawnReason reason) {
        /*if (MoCreatures.entityMap.get(this.getType()).getFrequency() <= 0) {
            return false;
        }*/
        BlockPos pos = new BlockPos(MathHelper.floor(this.getX()), MathHelper.floor(getBoundingBox().minY), this.getZ());

        /*String s = MoCTools.biomeName(this.world, pos); TODO: Biomes have changed, rewrite this.

        if (s.equals("Jungle") || s.equals("JungleHills")) {
            return getCanSpawnHereJungle();
        }*/

        return super.checkSpawnRules(worldIn, reason);
    }

    public boolean getCanSpawnHereJungle() {
        if (this.level.isUnobstructed(this)
                && this.level.getBlockCollisions(this, this.getBoundingBox()).count() == 0
                && !this.level.containsAnyLiquid(this.getBoundingBox())) {
            int var1 = MathHelper.floor(this.getX());
            int var2 = MathHelper.floor(this.getBoundingBox().minY);
            int var3 = MathHelper.floor(this.getZ());

            if (var2 < 63) {
                return false;
            }

            BlockPos pos = new BlockPos(var1, var2, var3);
            BlockState blockstate = this.level.getBlockState(pos.below());
            final Block block = blockstate.getBlock();

            //TODO: Use tags instead
//            if (block == Blocks.GRASS || block == Blocks.ACACIA_LEAVES || block == Blocks.BIRCH_LEAVES || block == Blocks.DARK_OAK_LEAVES || block == Blocks.JUNGLE_LEAVES || block == Blocks.OAK_LEAVES || block == Blocks.SPRUCE_LEAVES || block.isLeaves(blockstate, this.world, pos.down())) {
                return true;
//            }
        }

        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound = MoCTools.getEntityData(this);
        nbttagcompound.putBoolean("Adult", getIsAdult());
        nbttagcompound.putInt("Edad", getEdad());
        nbttagcompound.putString("Name", getPetName());
        nbttagcompound.putInt("TypeInt", getSubType());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        nbttagcompound = MoCTools.getEntityData(this);
        setAdult(nbttagcompound.getBoolean("Adult"));
        setEdad(nbttagcompound.getInt("Edad"));
        setPetName(nbttagcompound.getString("Name"));
        setType(nbttagcompound.getInt("TypeInt"));
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
    }

    /**
     * Boolean used for flying mounts
     */
    public boolean isFlyer() {
        return false;
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
        return 0.8D;
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

    @Override
    public void makeEntityDive() {
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

    public float getMoveSpeed() {
        return 0.7F;
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
        if (isMyFavoriteFood(itemstack1)) {
            this.getNavigation().moveTo(entityplayer1, 1D); 
            }
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

    public boolean getIsRideable() {
        return false;
    }

    public void setRideable(boolean b) {
    }

    public boolean entitiesToIgnore(Entity entity) {
        return ((!(entity instanceof LivingEntity)) || (entity instanceof MobEntity) || (entity instanceof PlayerEntity && this.getIsTamed())
                || (entity instanceof MoCEntityKittyBed) || (entity instanceof MoCEntityLitterBox)
                || (this.getIsTamed() && (entity instanceof MoCEntityAnimal && ((MoCEntityAnimal) entity).getIsTamed()))
                || ((entity instanceof WolfEntity) && !(MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.attackWolves.get()))
                || ((entity instanceof MoCEntityHorse) && !(MoCConfig.COMMON_CONFIG.GENERAL.creatureSettings.attackHorses.get()))
                || (entity.getBbWidth() > this.getBbWidth() && entity.getBbHeight() > this.getBbHeight()) || (entity instanceof MoCEntityEgg));
    }

    @Override
    public void setArmorType(int i) {
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
        return ((entity instanceof LivingEntity) && ((entity.getBbWidth() >= 0.5D) || (entity.getBbHeight() >= 0.5D)));
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
    public boolean isNotScared() {
        return false;
    }

    @Override
    public boolean isMovementCeased() {
        return getIsSitting();
    }

    @Override
    public boolean shouldAttackPlayers() {
        return this.level.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    protected boolean isMovementNoisy() {
        return false;
    }

    /**
     * The distance the entity will float under the surface. 0F = surface 1.0F = 1 block under
     * @return
     */
    @Override
    public double getDivingDepth() {
        return 0.5D;
    }

    @Override
    public boolean isDiving() {
        return false;
    }

    @Override
    public void forceEntityJump() {
        this.jumpFromGround();
    }

    @Override
    public int minFlyingHeight() {
        return 2;
    }

    /**
     * Maximum flyer height when moving autonomously
     *
     * @return
     */
    public int maxFlyingHeight() {
        return 4;
    }

    @Override
    public void travel(Vector3d movement) {
        if (!getIsFlying()) {
            super.travel(movement);
            return;
        }
        this.moveEntityWithHeadingFlying((float)movement.x, (float)movement.y, (float)movement.z);
    }

    public void moveEntityWithHeadingFlying(float strafe, float vertical, float forward) {
        if (this.isEffectiveAi()) {

            this.moveRelative(0.1F, new Vector3d(strafe, vertical, forward));
            this.move(MoverType.SELF, this.getDeltaMovement());
            this.getDeltaMovement().scale(0.8999999761581421D);
        } else {
            super.travel(new Vector3d(strafe, vertical, forward));
        }
    }

    @Override
    public boolean getIsFlying() {
        return false;
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
