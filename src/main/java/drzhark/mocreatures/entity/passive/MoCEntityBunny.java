package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.*;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import drzhark.mocreatures.registry.MoCEntities;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.world.World;

import java.util.List;

public class MoCEntityBunny extends MoCEntityTameableAnimal {

    private int bunnyReproduceTickerA;
    private int bunnyReproduceTickerB;
    private int jumpTimer;
    private static final DataParameter<Boolean> HAS_EATEN = EntityDataManager.<Boolean>defineId(MoCEntityBunny.class, DataSerializers.BOOLEAN);
    
    public MoCEntityBunny(EntityType<? extends MoCEntityBunny> type, World world) {
        super(type, world);
        setAdult(true);
        setTamed(false);
        setEdad(50 + this.random.nextInt(15));
        if (this.random.nextInt(4) == 0) {
            setAdult(false);
        }
        this.bunnyReproduceTickerA = this.random.nextInt(64);
        this.bunnyReproduceTickerB = 0;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 0.8D, 6F, 5F));
        this.goalSelector.addGoal(2, new MoCPanicGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new FleeFromPlayerGoal(this, 1.0D, 4D));
        this.goalSelector.addGoal(4, new FollowAdultGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new MoCAlternateWanderGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .add(Attributes.MAX_HEALTH, 4.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(HAS_EATEN, Boolean.valueOf(false));
    }

    public boolean getHasEaten() {
        return ((Boolean)this.entityData.get(HAS_EATEN)).booleanValue();
    }

    public void setHasEaten(boolean flag) {
        this.entityData.set(HAS_EATEN, Boolean.valueOf(flag));
    }

    @Override
    public void selectType() {
        checkSpawningBiome();

        if (getSubType() == 0) {
            setType(this.random.nextInt(5) + 1);
        }

    }

    /*@Override
    public boolean checkSpawningBiome() {
        int i = MathHelper.floor(this.getPosX());
        int j = MathHelper.floor(getBoundingBox().minY);
        int k = MathHelper.floor(this.getPosZ());
        BlockPos pos = new BlockPos(i, j, k);

        Biome currentbiome = MoCTools.Biomekind(this.world, pos);
        try {
            if (BiomeDictionary.hasType(currentbiome, Type.SNOWY)) {
                setType(3); //snow white bunnies!
                return true;
            }
        } catch (Exception e) {
        }
        return true;
    }*/

    @Override
    public ResourceLocation getTexture() {
        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("bunny.png");
            case 2:
                return MoCreatures.getTexture("bunnyb.png");
            case 3:
                return MoCreatures.getTexture("bunnyc.png");
            case 4:
                return MoCreatures.getTexture("bunnyd.png");
            case 5:
                return MoCreatures.getTexture("bunnye.png");

            default:
                return MoCreatures.getTexture("bunny.png");
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_RABBIT_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_RABBIT_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.RABBIT_AMBIENT;
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (!stack.isEmpty() && (stack.getItem() == Items.GOLDEN_CARROT) && !getHasEaten()) {
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
            setHasEaten(true);
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            return true;
        }
        if (this.getRidingEntity() == null) {
            if (this.startRiding(player)) {
                this.rotationYaw = player.rotationYaw;
                if (!getIsTamed() && !this.world.isRemote) {
                    MoCTools.tameWithName(player, this);
                }
            }

            return true;
        }

        return super.processInteract(player, hand);
    }*/

    @Override
    public void tick() {
        super.tick();

        if (this.getVehicle() != null) {
            this.yRot = this.getVehicle().yRot;
        }
        if (!this.level.isClientSide) {

            if (--this.jumpTimer <= 0 && this.onGround
                    && ((this.getDeltaMovement().x > 0.05D) || (this.getDeltaMovement().z > 0.05D) || (this.getDeltaMovement().x < -0.05D) || (this.getDeltaMovement().z < -0.05D))) {
                this.setDeltaMovement(this.getDeltaMovement().x, 0.3D, this.getDeltaMovement().z);
                this.jumpTimer = 15;
            }

            if (!getIsTamed() || !getIsAdult() || !getHasEaten() || (this.getVehicle() != null)) {
                return;
            }
            if (this.bunnyReproduceTickerA < 1023) {
                this.bunnyReproduceTickerA++;
            } else if (this.bunnyReproduceTickerB < 127) {
                this.bunnyReproduceTickerB++;
            } else {
                List<Entity> list1 = this.level.getEntities(this, getBoundingBox().expandTowards(4.0D, 4.0D, 4.0D));
                for (int i1 = 0; i1 < list1.size(); i1++) {
                    Entity entity1 = (Entity) list1.get(i1);
                    if (!(entity1 instanceof MoCEntityBunny) || (entity1 == this)) {
                        continue;
                    }
                    MoCEntityBunny entitybunny = (MoCEntityBunny) entity1;
                    if ((entitybunny.getVehicle() != null) || (entitybunny.bunnyReproduceTickerA < 1023) || !entitybunny.getIsAdult()
                            || !entitybunny.getHasEaten()) {
                        continue;
                    }
                    MoCEntityBunny entitybunny1 = new MoCEntityBunny(MoCEntities.BUNNY, this.level);
                    entitybunny1.setPos(this.getX(), this.getY(), this.getZ());
                    entitybunny1.setAdult(false);
                    int babytype = this.getSubType();
                    if (this.random.nextInt(2) == 0) {
                        babytype = entitybunny.getSubType();
                    }
                    entitybunny1.setType(babytype);
                    this.level.addFreshEntity(entitybunny1);
                    MoCTools.playCustomSound(this, SoundEvents.CHICKEN_EGG);
                    proceed();
                    entitybunny.proceed();
                    break;
                }
            }
        }
    }

    public void proceed() {
        setHasEaten(false);
        this.bunnyReproduceTickerB = 0;
        this.bunnyReproduceTickerA = this.random.nextInt(64);
    }

    @Override
    public int nameYOffset() {
        return -40;
    }

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.CARROT;
    }

    /**
     * So bunny-hats don't suffer damage
     */
    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (this.getVehicle() != null) {
            return false;
        }
        return super.hurt(damagesource, i);
    }

    @Override
    public boolean isNotScared() {
        return getIsTamed();
    }

    @Override
    public double getMyRidingOffset() {
        if (this.getVehicle() instanceof PlayerEntity) {
            return ((PlayerEntity) this.getVehicle()).isShiftKeyDown() ? 0.25 : 0.5F;
        }

        return super.getMyRidingOffset();
    }

    @Override
    public float getAdjustedYOffset() {
        return 0.2F;
    }
    
    @Override
    public boolean canRidePlayer()
    {
        return true;
    }
}
