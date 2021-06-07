package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.FollowOwnerGoal;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class MoCEntityTurtle extends MoCEntityTameableAnimal {

    private boolean isSwinging;
    private boolean twistright;
    private int flopcounter;
    private static final DataParameter<Boolean> IS_UPSIDE_DOWN = EntityDataManager.defineId(MoCEntityTurtle.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_HIDING = EntityDataManager.defineId(MoCEntityTurtle.class, DataSerializers.BOOLEAN);

    public MoCEntityTurtle(EntityType<? extends MoCEntityTurtle> type, World world) {
        super(type, world);
        setAdult(false);
        setEdad(60 + this.random.nextInt(50));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new FollowOwnerGoal(this, 0.8D, 2F, 10F));
        this.goalSelector.addGoal(5, new MoCAlternateWanderGoal(this, 0.8D, 50));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.15D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_UPSIDE_DOWN, Boolean.FALSE); // rideable: 0 nothing, 1 saddle
        this.entityData.define(IS_HIDING, Boolean.FALSE); // rideable: 0 nothing, 1 saddle
    }

    @Override
    public ResourceLocation getTexture() {
        String tempText = "turtle.png";

        if (getPetName().equals("Donatello") || getPetName().equals("donatello")) {
            tempText = "turtled.png";
        }

        if (getPetName().equals("Leonardo") || getPetName().equals("leonardo")) {
            tempText = "turtlel.png";
        }

        if (getPetName().equals("Rafael") || getPetName().equals("rafael") || getPetName().equals("raphael") || getPetName().equals("Raphael")) {
            tempText = "turtler.png";
        }

        if (getPetName().equals("Michelangelo") || getPetName().equals("michelangelo") || getPetName().equals("Michaelangelo")
                || getPetName().equals("michaelangelo")) {
            tempText = "turtlem.png";
        }

        return MoCreatures.getTexture(tempText);
    }

    public boolean getIsHiding() {
        return this.entityData.get(IS_HIDING);
    }

    public boolean getIsUpsideDown() {
        return this.entityData.get(IS_UPSIDE_DOWN);
    }
    
    public void setIsHiding(boolean flag) {
        this.entityData.set(IS_HIDING, flag);
    }

    public void setIsUpsideDown(boolean flag) {
        this.flopcounter = 0;
        this.attackAnim = 0.0F;
        this.entityData.set(IS_UPSIDE_DOWN, flag);
    }

    @Override
    public double getMyRidingOffset() {
        if (this.getVehicle() instanceof PlayerEntity) {
            if (this.getVehicle().isCrouching()) {
                return -0.25D + ((300D - this.getEdad()) / 500D);
            }
            return (300D - this.getEdad()) / 500D;
        }

        return super.getMyRidingOffset();
    }

    /*@Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        if (getIsTamed()) {
            if (getIsUpsideDown()) {
                flipflop(false);
                return true;
            }
            if (this.getRidingEntity() == null) {
                if (this.startRiding(player)) {
                    this.rotationYaw = player.rotationYaw;
                }
            }
            return true;
        }

        flipflop(!getIsUpsideDown());

        return super.processInteract(player, hand);
    }*/

    @Override
    protected void jumpFromGround() {
        if (isInWater()) {
            this.setDeltaMovement(this.getDeltaMovement().x, 0.3D, this.getDeltaMovement().z);
            if (isSprinting()) {
                float f = this.yRot * 0.01745329F;
                this.getDeltaMovement().add(MathHelper.sin(f) * -0.2F, 0, MathHelper.cos(f) * 0.2F);
            }
            this.hasImpulse = true;
        }
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (!this.level.isClientSide) {
            if (!getIsUpsideDown() && !getIsTamed()) {
                LivingEntity entityliving = getBoogey(4D);
                if ((entityliving != null) && canSee(entityliving)) {
                    if (!getIsHiding() && !isInWater()) {
                        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_TURTLE_ANGRY);
                        setIsHiding(true);
                    }

                    this.getNavigation().stop();
                } else {

                    setIsHiding(false);
                    if (!isPathFinding() && this.random.nextInt(50) == 0) {
                        ItemEntity entityitem = getClosestItem(this, 10D, Items.MELON, Items.SUGAR_CANE);
                        if (entityitem != null) {
                            float f = entityitem.distanceTo(this);
                            if (f > 2.0F) {
                                getMyOwnPath(entityitem, f);
                            }
                            if (f < 2.0F && this.deathTime == 0) {
                                entityitem.remove();
                                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_TURTLE_EATING);
                                PlayerEntity entityplayer = this.level.getNearestPlayer(this, 24D);
                                if (entityplayer != null) {
                                    MoCTools.tameWithName(entityplayer, this);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean swimmerEntity() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        Entity entity = damagesource.getEntity();
        if (this.getVehicle() != null) {
            return false;
        }
        if (entity == null) {
            return super.hurt(damagesource, i);
        }
        if (getIsHiding()) {
            if (this.random.nextInt(10) == 0) {
                flipflop(true);
            }
            return false;
        } else {
            boolean flag = super.hurt(damagesource, i);
            if (this.random.nextInt(3) == 0) {
                flipflop(true);
            }
            return flag;
        }
    }

    public void flipflop(boolean flip) {
        setIsUpsideDown(flip);
        setIsHiding(false);
        this.getNavigation().stop();
    }

    @Override
    public boolean entitiesToIgnore(Entity entity) {
        return (entity instanceof MoCEntityTurtle) || ((entity.getBbHeight() <= this.getBbHeight()) && (entity.getBbWidth() <= this.getBbWidth()))
                || super.entitiesToIgnore(entity);
    }

    @Override
    public void tick() {
        super.tick();

        if ((this.getVehicle() != null) && (this.getVehicle() instanceof PlayerEntity)) {
            PlayerEntity entityplayer = (PlayerEntity) this.getVehicle();
            if (entityplayer != null) {
                this.yRot = entityplayer.yRot;
            }
        }
        //to make mega turtles if tamed
        if (getIsTamed() && getEdad() < 300 && this.random.nextInt(900) == 0) {
            setEdad(getEdad() + 1);
        }
        if (getIsUpsideDown() && isInWater()) {
            setIsUpsideDown(false);
        }
        if (getIsUpsideDown() && (this.getVehicle() == null) && this.random.nextInt(20) == 0) {
            setSwinging(true);
            this.flopcounter++;
        }

        if (getIsSwinging()) {
            this.attackAnim += 0.2F;

            boolean flag = (this.flopcounter > (this.random.nextInt(3) + 8));

            if (this.attackAnim > 2.0F && (!flag || this.random.nextInt(20) == 0)) {
                setSwinging(false);
                this.attackAnim = 0.0F;
                if (this.random.nextInt(2) == 0) {
                    this.twistright = !this.twistright;
                }

            } else if (this.attackAnim > 9.0F && flag) {
                setSwinging(false);
                MoCTools.playCustomSound(this, SoundEvents.CHICKEN_EGG);
                setIsUpsideDown(false);
            }
        }
    }

    public boolean getIsSwinging() {
        return this.isSwinging;
    }

    public void setSwinging(boolean flag) {
        this.isSwinging = flag;
    }

    @Override
    public boolean isMovementCeased() {
        return (getIsUpsideDown() || getIsHiding());
    }

    public int getFlipDirection() {
        if (this.twistright) {
            return 1;
        }
        return -1;
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.readAdditionalSaveData(nbttagcompound);
        setIsUpsideDown(nbttagcompound.getBoolean("UpsideDown"));
    }

    @Override
    public void addAdditionalSaveData(CompoundNBT nbttagcompound) {
        super.addAdditionalSaveData(nbttagcompound);
        nbttagcompound.putBoolean("UpsideDown", getIsUpsideDown());
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_TURTLE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_TURTLE_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_TURTLE_DEATH;
    }

//    @Override
//    protected Item getDropItem() {
//        if (getPetName().equals("Donatello") || getPetName().equals("donatello")) {
//            return MoCItems.BO;
//        }
//
//        if (getPetName().equals("Leonardo") || getPetName().equals("leonardo")) {
//            return MoCItems.KATANA;
//        }
//
//        if (getPetName().equals("Rafael") || getPetName().equals("rafael") || getPetName().equals("raphael") || getPetName().equals("Raphael")) {
//            return MoCItems.SAI;
//        }
//
//        if (getPetName().equals("Michelangelo") || getPetName().equals("michelangelo") || getPetName().equals("Michaelangelo")
//                || getPetName().equals("michaelangelo")) {
//            return MoCItems.NUNCHAKU;
//        }
//        return MoCItems.TURTLERAW;
//    }

    /**
     * Used to avoid rendering the top shell cube
     *
     * @return
     */
    public boolean isTMNT() {
        if (getPetName().equals("Donatello") || getPetName().equals("donatello") || getPetName().equals("Leonardo") || getPetName().equals("leonardo")
                || getPetName().equals("Rafael") || getPetName().equals("rafael") || getPetName().equals("raphael") || getPetName().equals("Raphael")
                || getPetName().equals("Michelangelo") || getPetName().equals("michelangelo") || getPetName().equals("Michaelangelo")
                || getPetName().equals("michaelangelo")) {
            return true;
        }
        return false;
    }

    /*@Override
    public boolean updateMount() {
        return getIsTamed();
    }*/

    /*@Override
    public boolean forceUpdates() {
        return getIsTamed();
    }*/

    @Override
    public boolean isMyHealFood(ItemStack stack) {
        return !stack.isEmpty() && (stack.getItem() == Items.SUGAR_CANE || stack.getItem() == Items.MELON);
    }

    @Override
    public int getMaxSpawnClusterSize() {
        return 2;
    }

    @Override
    public int nameYOffset() {
        return -10 - (getEdad() / 5);
    }

    @Override
    public boolean isPushedByFluid() {
        return true;
    }

    @Override
    public boolean isAmphibian() {
        return true;
    }

    @Override
    public float getSpeed() {
        if (isInWater()) {
            return 0.08F;
        }
        return 0.12F;
    }

    @Override
    protected double minDivingDepth() {
        return (getEdad() + 8D) / 340D;
    }

    @Override
    protected double maxDivingDepth() {
        return 1D * (this.getEdad() / 100D);
    }

    @Override
    public int getMaxEdad() {
        return 120;
    }
    
    @Override
    public boolean canRidePlayer()
    {
        return true;
    }
}
