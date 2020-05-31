package drzhark.mocreatures.entity.passive;

import com.google.common.base.Predicate;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIFollowOwnerPlayer;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Hand;
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
    private static final DataParameter<Boolean> PRE_TAMED = EntityDataManager.<Boolean>createKey(MoCEntityBird.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_FLYING = EntityDataManager.<Boolean>createKey(MoCEntityBird.class, DataSerializers.BOOLEAN);
    
    public MoCEntityBird(EntityType<? extends MoCEntityBird> type, World world) {
        super(type, world);
        this.collidedVertically = true;
        this.wingb = 0.0F;
        this.wingc = 0.0F;
        this.wingh = 1.0F;
        this.fleeing = false;
        this.textureSet = false;
        setTamed(false);
        this.stepHeight = 1.0F;
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SwimGoal(this));
        this.goalSelector.addGoal(2, new EntityAIFleeFromEntityMoC(this, new Predicate<Entity>() {

            public boolean apply(Entity entity) {
                return !(entity instanceof MoCEntityBird) && (entity.getHeight() > 0.4F || entity.getWidth() > 0.4F);
            }
        }, 6.0F, 1.D, 1.3D));
        this.goalSelector.addGoal(3, new EntityAIFollowOwnerPlayer(this, 0.8D, 2F, 10F));
        this.goalSelector.addGoal(4, this.wander = new EntityAIWanderMoC2(this, 1.0D, 80));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 8.0F));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(this.rand.nextInt(6) + 1);
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
    protected void registerData() {
        super.registerData();
        this.dataManager.register(PRE_TAMED, Boolean.valueOf(false));
        this.dataManager.register(IS_FLYING, Boolean.valueOf(false));
    }

    public boolean getPreTamed() {
        return ((Boolean)this.dataManager.get(PRE_TAMED)).booleanValue();
    }

    public void setPreTamed(boolean flag) {
        this.dataManager.set(PRE_TAMED, Boolean.valueOf(flag));
    }

    public boolean getIsFlying() {
        return ((Boolean)this.dataManager.get(IS_FLYING)).booleanValue();
    }

    public void setIsFlying(boolean flag) {
        this.dataManager.set(IS_FLYING, Boolean.valueOf(flag));
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
                BlockState blockstate = this.world.getBlockState(pos);
                if (blockstate.getBlock().isAir(blockstate, this.world, pos) || (blockstate.getMaterial() != Material.WOOD)) {
                    continue;
                }
                int l2 = j;
                do {
                    if (l2 >= k1) {
                        continue label0;
                    }
                    BlockPos pos1 = new BlockPos(i2, l2, j2);
                    BlockState blockstate1 = this.world.getBlockState(pos1);
                    if (blockstate1.getBlock().isAir(blockstate1, this.world, pos1)) {
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
            int i = MathHelper.floor(entity.getPosX());
            int j = MathHelper.floor(entity.getPosY());
            int k = MathHelper.floor(entity.getPosZ());
            faceLocation(i, j, k, 30F);
            if (MathHelper.floor(this.getPosY()) < j) {
                this.getMotion().add(0, 0.14999999999999999D, 0);
            }
            if (this.getPosX() < entity.getPosX()) {
                double d = entity.getPosX() - this.getPosX();
                if (d > 0.5D) {
                    this.getMotion().add(0.050000000000000003D, 0, 0);
                }
            } else {
                double d1 = this.getPosX() - entity.getPosX();
                if (d1 > 0.5D) {
                    this.getMotion().subtract(0.050000000000000003D, 0, 0);
                }
            }
            if (this.getPosZ() < entity.getPosZ()) {
                double d2 = entity.getPosZ() - this.getPosZ();
                if (d2 > 0.5D) {
                    this.getMotion().add(0, 0, 0.050000000000000003D);
                }
            } else {
                double d3 = this.getPosZ() - entity.getPosZ();
                if (d3 > 0.5D) {
                    this.getMotion().add(0, 0, 0.050000000000000003D);
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
            if ((j - MathHelper.floor(this.getPosY())) > 2) {
                this.getMotion().add(0, 0.14999999999999999D, 0);
            }
            int l = 0;
            int i1 = 0;
            if (this.getPosX() < i) {
                l = i - MathHelper.floor(this.getPosX());
                this.getMotion().add(0.050000000000000003D, 0, 0);
            } else {
                l = MathHelper.floor(this.getPosX()) - i;
                this.getMotion().subtract(0.050000000000000003D, 0, 0);
            }
            if (this.getPosZ() < k) {
                i1 = k - MathHelper.floor(this.getPosZ());
                this.getMotion().add(0, 0, 0.050000000000000003D);
            } else {
                i1 = MathHelper.floor(this.getPosX()) - k;
                this.getMotion().subtract(0, 0, 0.050000000000000003D);
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
    public double getYOffset() {
        if (this.getRidingEntity() instanceof PlayerEntity) {
            return ((PlayerEntity) this.getRidingEntity()).isCrouching() ? 0.2 : 0.45F;
        }

        return super.getYOffset();
    }

    @Override
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
    }

    @Override
    public void livingTick() {
        super.livingTick();

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
        if (!this.onGround && (this.getMotion().y < 0.0D)) {
            this.getMotion().mul(0, 0.8D, 0);
        }
        this.wingb += this.wingh * 2.0F;

        //check added to avoid duplicating behavior on client / server
        if (!this.world.isRemote) {

            if (isMovementCeased() && getIsFlying()) {
                setIsFlying(false);
            }

            if (getIsFlying() && this.getNavigator().noPath() && !isMovementCeased() && this.getAttackTarget() == null && rand.nextInt(30) == 0) {
                this.wander.makeUpdate();
            }

            if (!getIsFlying() && !getIsTamed() && this.rand.nextInt(10) == 0) {
                List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().expand(4D, 4D, 4D));
                for (int i = 0; i < list.size(); i++) {
                    Entity entity1 = list.get(i);
                    if (!(entity1 instanceof LivingEntity) || entity1 instanceof MoCEntityBird) {
                        continue;
                    }
                    if (((LivingEntity) entity1).getWidth() >= 0.4F && ((LivingEntity) entity1).getHeight() >= 0.4F && canEntityBeSeen(entity1)) {
                        setIsFlying(true);
                        this.fleeing = true;
                        this.wander.makeUpdate();
                    }
                }
            }

            if (!isMovementCeased() && !getIsFlying() && this.rand.nextInt(getIsTamed() ? 1000 : 400) == 0) {
                setIsFlying(true);
                this.wander.makeUpdate();
            }

            if (getIsFlying() && rand.nextInt(200) == 0) {
                setIsFlying(false);
            }

            if (this.fleeing && rand.nextInt(50) == 0) {
                this.fleeing = false;
            }

            //TODO move to new AI
            if (!this.fleeing) {
                ItemEntity entityitem = getClosestItem(this, 12D, Items.WHEAT_SEEDS, Items.MELON_SEEDS);
                if (entityitem != null) {
                    FlyToNextEntity(entityitem);
                    ItemEntity entityitem1 = getClosestItem(this, 1.0D, Items.WHEAT_SEEDS, Items.MELON_SEEDS);
                    if ((this.rand.nextInt(50) == 0) && (entityitem1 != null)) {
                        entityitem1.remove();
                        setPreTamed(true);
                    }
                }
            }
            if (this.rand.nextInt(10) == 0 && isInWater()) {
                WingFlap();
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.getRidingEntity() != null) {
            this.rotationYaw = this.getRidingEntity().rotationYaw;
        }

        if ((this.getRidingEntity() != null) && (this.getRidingEntity() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getRidingEntity();
            this.rotationYaw = entityplayer.rotationYaw;
            entityplayer.fallDistance = 0.0F;
            if (entityplayer.getMotion().y < -0.1D)
                entityplayer.getMotion().mul(0, 0.60D, 0);
        }

        if (--this.jumpTimer <= 0 && this.onGround
                && ((this.getMotion().x > 0.05D) || (this.getMotion().z > 0.05D) || (this.getMotion().x < -0.05D) || (this.getMotion().z < -0.05D))) {
            this.setMotion(this.getMotion().x, 0.25D, this.getMotion().z);
            float velX = MathHelper.sin(this.rotationYaw * (float) Math.PI / 180.0F);
            float velZ = MathHelper.cos(this.rotationYaw * (float) Math.PI / 180.0F);

            this.getMotion().add(-0.2F * velX, 0, 0.2F * velZ);
            this.jumpTimer = 15;
        }
    }

    public int[] ReturnNearestMaterialCoord(Entity entity, Material material, Double double1) {
        AxisAlignedBB axisalignedbb = entity.getBoundingBox().expand(double1.doubleValue(), double1.doubleValue(), double1.doubleValue());
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
                    BlockState blockstate = this.world.getBlockState(pos);
                    if (blockstate.getBlock() != null && !blockstate.getBlock().isAir(blockstate, this.world, pos)
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
        if (!this.world.isRemote && getIsTamed() && (this.getHealth() > 0)) {
            return;
        } else {
            super.remove();
            return;
        }
    }

    private void WingFlap() {
        this.getMotion().add(0, 0.05D, 0);
        if (this.rand.nextInt(30) == 0) {
            this.getMotion().add(0.2D, 0, 0);
        }
        if (this.rand.nextInt(30) == 0) {
            this.getMotion().subtract(0.2D, 0, 0);
        }
        if (this.rand.nextInt(30) == 0) {
            this.getMotion().add(0, 0, 0.2D);
        }
        if (this.rand.nextInt(30) == 0) {
            this.getMotion().subtract(0, 0, 0.2D);
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
