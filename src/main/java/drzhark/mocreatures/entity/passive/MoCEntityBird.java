package drzhark.mocreatures.entity.passive;

import com.google.common.base.Predicate;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityBird extends MoCEntityTameableAnimal {

    private boolean fleeing;
    public float wingb;
    public float wingc;
    public float wingd;
    public float winge;
    public float wingh;
    public boolean textureSet;
    private int jumpTimer;
    protected EntityAIWanderMoC2 wander;
    public static final String birdNames[] = {"Dove", "Crow", "Parrot", "Blue", "Canary", "Red"};
    private static final DataParameter<Boolean> PRE_TAMED = EntityDataManager.defineId(MoCEntityBird.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_FLYING = EntityDataManager.defineId(MoCEntityBird.class, DataSerializers.BOOLEAN);
    
    public MoCEntityBird(EntityType<? extends MoCEntityBird> type, World world) {
        super(type, world);
        this.verticalCollision = true;
        this.wingb = 0.0F;
        this.wingc = 0.0F;
        this.wingh = 1.0F;
        this.fleeing = false;
        this.textureSet = false;
        setTamed(false);
        this.maxUpStep = 1.0F;
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new EntityAIFleeFromEntityMoC(this, new Predicate<Entity>() {

            public boolean apply(Entity entity) {
                return !(entity instanceof MoCEntityBird) && (entity.getBbHeight() > 0.4F || entity.getBbWidth() > 0.4F);
            }
        }, 6.0F, 1.D, 1.3D));
        this.goalSelector.addGoal(3, new EntityAIFollowOwnerPlayer(this, 0.8D, 2F, 10F));
        this.goalSelector.addGoal(4, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .add(Attributes.MAX_HEALTH, 8.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(this.random.nextInt(6) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {

        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("birdwhite.png");
            case 2:
                return MoCreatures.getTexture("birdblack.png");
            case 3:
                return MoCreatures.getTexture("birdgreen.png");
            case 4:
                return MoCreatures.getTexture("birdblue.png");
            case 5:
                return MoCreatures.getTexture("birdyellow.png");
            case 6:
                return MoCreatures.getTexture("birdred.png");

            default:
                return MoCreatures.getTexture("birdblue.png");
        }
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(PRE_TAMED, Boolean.valueOf(false));
        this.entityData.define(IS_FLYING, Boolean.valueOf(false));
    }

    public boolean getPreTamed() {
        return ((Boolean)this.entityData.get(PRE_TAMED)).booleanValue();
    }

    public void setPreTamed(boolean flag) {
        this.entityData.set(PRE_TAMED, Boolean.valueOf(flag));
    }

    public boolean getIsFlying() {
        return ((Boolean)this.entityData.get(IS_FLYING)).booleanValue();
    }

    public void setIsFlying(boolean flag) {
        this.entityData.set(IS_FLYING, Boolean.valueOf(flag));
    }

    private int[] FindTreeTop(int i, int j, int k) {
        int l = i - 5;
        int i1 = k - 5;
        int j1 = i + 5;
        int k1 = j + 7;
        int l1 = k + 5;
        for (int i2 = l; i2 < j1; i2++) {
            label0: for (int j2 = i1; j2 < l1; j2++) {
                BlockPos pos = new BlockPos(i2, j, j2);
                BlockState blockstate = this.level.getBlockState(pos);
                if (blockstate.getBlock().isAir(blockstate, this.level, pos) || (blockstate.getMaterial() != Material.WOOD)) {
                    continue;
                }
                int l2 = j;
                do {
                    if (l2 >= k1) {
                        continue label0;
                    }
                    BlockPos pos1 = new BlockPos(i2, l2, j2);
                    BlockState blockstate1 = this.level.getBlockState(pos1);
                    if (blockstate1.getBlock().isAir(blockstate1, this.level, pos1)) {
                        return (new int[] {i2, l2 + 2, j2});
                    }
                    l2++;
                } while (true);
            }

        }

        return (new int[] {0, 0, 0});
    }

    private boolean FlyToNextEntity(Entity entity) {
        if (entity != null) {
            int i = MathHelper.floor(entity.getX());
            int j = MathHelper.floor(entity.getY());
            int k = MathHelper.floor(entity.getZ());
            faceLocation(i, j, k, 30F);
            if (MathHelper.floor(this.getY()) < j) {
                this.getDeltaMovement().add(0, 0.14999999999999999D, 0);
            }
            if (this.getX() < entity.getX()) {
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
            if (this.getZ() < entity.getZ()) {
                double d2 = entity.getZ() - this.getZ();
                if (d2 > 0.5D) {
                    this.getDeltaMovement().add(0, 0, 0.050000000000000003D);
                }
            } else {
                double d3 = this.getZ() - entity.getZ();
                if (d3 > 0.5D) {
                    this.getDeltaMovement().add(0, 0, 0.050000000000000003D);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @SuppressWarnings("unused")
    private boolean FlyToNextTree() {
        int ai[] = ReturnNearestMaterialCoord(this, Material.LEAVES, Double.valueOf(20D));
        int ai1[] = FindTreeTop(ai[0], ai[1], ai[2]);
        if (ai1[1] != 0) {
            int i = ai1[0];
            int j = ai1[1];
            int k = ai1[2];
            faceLocation(i, j, k, 30F);
            if ((j - MathHelper.floor(this.getY())) > 2) {
                this.getDeltaMovement().add(0, 0.14999999999999999D, 0);
            }
            int l = 0;
            int i1 = 0;
            if (this.getX() < i) {
                l = i - MathHelper.floor(this.getX());
                this.getDeltaMovement().add(0.050000000000000003D, 0, 0);
            } else {
                l = MathHelper.floor(this.getX()) - i;
                this.getDeltaMovement().subtract(0.050000000000000003D, 0, 0);
            }
            if (this.getZ() < k) {
                i1 = k - MathHelper.floor(this.getZ());
                this.getDeltaMovement().add(0, 0, 0.050000000000000003D);
            } else {
                i1 = MathHelper.floor(this.getX()) - k;
                this.getDeltaMovement().subtract(0, 0, 0.050000000000000003D);
            }
            double d = l + i1;
            if (d < 3D) {
                return true;
            }
        }
        return false;
    }
//
//    @Override
//    protected Item getDropItem() {
//        return Items.FEATHER;
//    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_BIRD_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_BIRD_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (getSubType() == 1) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_WHITE;
        }
        if (getSubType() == 2) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_BLACK;
        }
        if (getSubType() == 3) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_GREEN;
        }
        if (getSubType() == 4) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_BLUE;
        }
        if (getSubType() == 5) {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_YELLOW;
        } else {
            return MoCSoundEvents.ENTITY_BIRD_AMBIENT_RED;
        }
    }

    @Override
    public double getMyRidingOffset() {
        if (this.getVehicle() instanceof PlayerEntity) {
            return ((PlayerEntity) this.getVehicle()).isCrouching() ? 0.2 : 0.45F;
        }

        return super.getMyRidingOffset();
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && getPreTamed() && !getIsTamed() && stack.getItem() == Items.WHEAT_SEEDS) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            if (!this.world.isRemote) {
                MoCTools.tameWithName(player, this);
            }
            return true;
        }

        if (!getIsTamed()) {
            return false;
        }
        if (this.getRidingEntity() == null) {
            if (this.startRiding(player)) {
                this.rotationYaw = player.rotationYaw;
            }

            return true;
        }

        return super.processInteract(player, hand);
    }*/

    @Override
    public void aiStep() {
        super.aiStep();

        this.winge = this.wingb;
        this.wingd = this.wingc;
        this.wingc = (float) (this.wingc + ((this.onGround ? -1 : 4) * 0.3D));
        if (this.wingc < 0.0F) {
            this.wingc = 0.0F;
        }
        if (this.wingc > 1.0F) {
            this.wingc = 1.0F;
        }
        if (!this.onGround && (this.wingh < 1.0F)) {
            this.wingh = 1.0F;
        }
        this.wingh = (float) (this.wingh * 0.9D);
        if (!this.onGround && (this.getDeltaMovement().y < 0.0D)) {
            this.getDeltaMovement().multiply(0, 0.8D, 0);
        }
        this.wingb += this.wingh * 2.0F;

        //check added to avoid duplicating behavior on client / server
        if (!this.level.isClientSide) {

            if (isMovementCeased() && getIsFlying()) {
                setIsFlying(false);
            }

            if (getIsFlying() && this.getNavigation().isDone() && !isMovementCeased() && this.getTarget() == null && random.nextInt(30) == 0) {
                this.wander.makeUpdate();
            }

            if (!getIsFlying() && !getIsTamed() && this.random.nextInt(10) == 0) {
                List<Entity> list = this.level.getEntities(this, getBoundingBox().expandTowards(4D, 4D, 4D));
                for (int i = 0; i < list.size(); i++) {
                    Entity entity1 = list.get(i);
                    if (!(entity1 instanceof LivingEntity) || entity1 instanceof MoCEntityBird) {
                        continue;
                    }
                    if (((LivingEntity) entity1).getBbWidth() >= 0.4F && ((LivingEntity) entity1).getBbHeight() >= 0.4F && canSee(entity1)) {
                        setIsFlying(true);
                        this.fleeing = true;
                        this.wander.makeUpdate();
                    }
                }
            }

            if (!isMovementCeased() && !getIsFlying() && this.random.nextInt(getIsTamed() ? 1000 : 400) == 0) {
                setIsFlying(true);
                this.wander.makeUpdate();
            }

            if (getIsFlying() && random.nextInt(200) == 0) {
                setIsFlying(false);
            }

            if (this.fleeing && random.nextInt(50) == 0) {
                this.fleeing = false;
            }

            //TODO move to new AI
            if (!this.fleeing) {
                ItemEntity entityitem = getClosestItem(this, 12D, Items.WHEAT_SEEDS, Items.MELON_SEEDS);
                if (entityitem != null) {
                    FlyToNextEntity(entityitem);
                    ItemEntity entityitem1 = getClosestItem(this, 1.0D, Items.WHEAT_SEEDS, Items.MELON_SEEDS);
                    if ((this.random.nextInt(50) == 0) && (entityitem1 != null)) {
                        entityitem1.remove();
                        setPreTamed(true);
                    }
                }
            }
            if (this.random.nextInt(10) == 0 && isInWater()) {
                WingFlap();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getVehicle() != null) {
            this.yRot = this.getVehicle().yRot;
        }

        if ((this.getVehicle() != null) && (this.getVehicle() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getVehicle();
            this.yRot = entityplayer.yRot;
            entityplayer.fallDistance = 0.0F;
            if (entityplayer.getDeltaMovement().y < -0.1D)
                entityplayer.getDeltaMovement().multiply(0, 0.60D, 0);
        }

        if (--this.jumpTimer <= 0 && this.onGround
                && ((this.getDeltaMovement().x > 0.05D) || (this.getDeltaMovement().z > 0.05D) || (this.getDeltaMovement().x < -0.05D) || (this.getDeltaMovement().z < -0.05D))) {
            this.setDeltaMovement(this.getDeltaMovement().x, 0.25D, this.getDeltaMovement().z);
            float velX = MathHelper.sin(this.yRot * (float) Math.PI / 180.0F);
            float velZ = MathHelper.cos(this.yRot * (float) Math.PI / 180.0F);

            this.getDeltaMovement().add(-0.2F * velX, 0, 0.2F * velZ);
            this.jumpTimer = 15;
        }
    }

    public int[] ReturnNearestMaterialCoord(Entity entity, Material material, Double double1) {
        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expandTowards(double1.doubleValue(), double1.doubleValue(), double1.doubleValue());
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.floor(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.floor(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.floor(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockPos pos = new BlockPos(k1, l1, i2);
                    BlockState blockstate = this.level.getBlockState(pos);
                    if (blockstate.getBlock() != null && !blockstate.getBlock().isAir(blockstate, this.level, pos)
                            && blockstate.getMaterial() == material) {
                        return (new int[] {k1, l1, i2});
                    }
                }

            }

        }

        return (new int[] {-1, 0, 0});
    }

    @Override
    public void remove() {
        if (!this.level.isClientSide && getIsTamed() && (this.getHealth() > 0)) {
            return;
        } else {
            super.remove();
            return;
        }
    }

    private void WingFlap() {
        this.getDeltaMovement().add(0, 0.05D, 0);
        if (this.random.nextInt(30) == 0) {
            this.getDeltaMovement().add(0.2D, 0, 0);
        }
        if (this.random.nextInt(30) == 0) {
            this.getDeltaMovement().subtract(0.2D, 0, 0);
        }
        if (this.random.nextInt(30) == 0) {
            this.getDeltaMovement().add(0, 0, 0.2D);
        }
        if (this.random.nextInt(30) == 0) {
            this.getDeltaMovement().subtract(0, 0, 0.2D);
        }
    }

    @Override
    public int nameYOffset() {
        return -40;
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == Items.WHEAT_SEEDS || stack.getItem() == Items.MELON_SEEDS);
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public int maxFlyingHeight() {
        if (getIsTamed())
            return 4;
        return 6;
    }

    @Override
    public int minFlyingHeight() {
        return 2;
    }
    
    @Override
    public boolean canRidePlayer()
    {
        return true;
    }
}
