package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.FleeFromPlayerGoal;
import drzhark.mocreatures.entity.ai.HuntGoal;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class MoCEntityCrocodile extends MoCEntityTameableAnimal {

    public float biteProgress;
    public float spin;
    public int spinInt;
    private boolean waterbound;
    private static final DataParameter<Boolean> IS_RESTING = EntityDataManager.defineId(MoCEntityCrocodile.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> EATING_PREY = EntityDataManager.defineId(MoCEntityCrocodile.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_BITING = EntityDataManager.defineId(MoCEntityCrocodile.class, DataSerializers.BOOLEAN);
    
    public MoCEntityCrocodile(EntityType<? extends MoCEntityCrocodile> type, World world) {
        super(type, world);
        this.texture = "crocodile.png";
        setEdad(50 + this.random.nextInt(50));
        setTamed(false);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(3, new FleeFromPlayerGoal(this, 0.8D, 4D));
        this.goalSelector.addGoal(4, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(7, new MoCAlternateWanderGoal(this, 0.9D));
        this.goalSelector.addGoal(9, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new HuntGoal(this, AnimalEntity.class, true));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));

    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .add(Attributes.MAX_HEALTH, 25.0D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(IS_RESTING, Boolean.FALSE);
        this.entityData.define(EATING_PREY, Boolean.FALSE);
        this.entityData.define(IS_BITING, Boolean.FALSE);
    }

    public boolean getIsBiting() {
        return this.entityData.get(IS_BITING);
    }

    public boolean getIsSitting() {
        return this.entityData.get(IS_RESTING);
    }

    public boolean getHasCaughtPrey() {
        return this.entityData.get(EATING_PREY);
    }

    public void setBiting(boolean flag) {
        this.entityData.set(IS_BITING, flag);
    }

    public void setIsSitting(boolean flag) {
        this.entityData.set(IS_RESTING, flag);
    }

    public void setHasCaughtPrey(boolean flag) {
        this.entityData.set(EATING_PREY, flag);
    }

    @Override
    protected void jumpFromGround() {

        if (isSwimming()) {
            if (getHasCaughtPrey()) {
                return;
            }
            this.setDeltaMovement(this.getDeltaMovement().x, 0.3D, this.getDeltaMovement().z);
            this.hasImpulse = true;

        } else if (this.getTarget() != null || getHasCaughtPrey()) {
            super.jumpFromGround();
        }
    }

    @Override
    public boolean isMovementCeased() {
        return getIsSitting();
    }

    @Override
    public void aiStep() {
        if (getIsSitting()) {
            this.xRot = -5F;
            if (!isSwimming() && this.biteProgress < 0.3F && this.random.nextInt(5) == 0) {
                this.biteProgress += 0.005F;
            }
            if (this.getTarget() != null) {
                setIsSitting(false);
            }
            if (!this.level.isClientSide && this.getTarget() != null || isSwimming() || getHasCaughtPrey() || this.random.nextInt(500) == 0)// isInsideOfMaterial(Material.WATER)
            {
                setIsSitting(false);
                this.biteProgress = 0;
            }

        } else {
            if (!this.level.isClientSide && (this.random.nextInt(500) == 0) && this.getTarget() == null && !getHasCaughtPrey() && !isSwimming()) {
                setIsSitting(true);
                this.getNavigation().stop();
            }

        }

        if (this.random.nextInt(500) == 0 && !getHasCaughtPrey() && !getIsSitting()) {
            crocBite();
        }

        //TODO replace with move to water AI
        if (!this.level.isClientSide && this.random.nextInt(500) == 0 && !this.waterbound && !getIsSitting() && !isSwimming()) {
            MoCTools.MoveToWater(this);
        }

        if (this.waterbound) {
            if (!isInWater()) {
                MoCTools.MoveToWater(this);
            } else {
                this.waterbound = false;
            }
        }

        if (getHasCaughtPrey()) {
            if (this.isVehicle()) {
                setTarget(null);

                this.biteProgress = 0.4F;
                setIsSitting(false);

                if (!isInWater()) {
                    this.waterbound = true;
                    if (this.getVehicle() instanceof LivingEntity && ((LivingEntity) this.getVehicle()).getHealth() > 0) {
                        ((LivingEntity) this.getVehicle()).deathTime = 0;
                    }

                    if (!this.level.isClientSide && this.random.nextInt(50) == 0) {
                        this.getVehicle().hurt(DamageSource.mobAttack(this), 2);
                    }
                }
            } else {
                setHasCaughtPrey(false);
                this.biteProgress = 0F;
                this.waterbound = false;
            }

            if (isSpinning()) {
                this.spinInt += 3;
                if ((this.spinInt % 20) == 0) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_CROCODILE_ROLL);
                }
                if (this.spinInt > 80) {
                    this.spinInt = 0;
                    this.getVehicle().hurt(DamageSource.mobAttack(this), 4); //TODO ADJUST

                }

                //the following if to be removed from SMP

                if (!this.level.isClientSide && this.isVehicle() && this.getVehicle() instanceof PlayerEntity) {
                    //TODO 4FIX
                    //MoCreatures.mc.gameSettings.thirdPersonView = 1;
                }
            }
        }

        super.aiStep();
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    public void crocBite() {
        if (!getIsBiting()) {
            setBiting(true);
            this.biteProgress = 0.0F;
        }
    }

    @Override
    public void tick() {
        if (getIsBiting() && !getHasCaughtPrey())// && biteProgress <0.3)
        {
            this.biteProgress += 0.1F;
            if (this.biteProgress == 0.4F) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_CROCODILE_JAWSNAP);
            }
            if (this.biteProgress > 0.6F) {

                setBiting(false);
                this.biteProgress = 0.0F;
            }
        }

        super.tick();
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        if (getHasCaughtPrey()) {
            return false;
        }

        //TODO FIX!!!!
        /*if (!this.world.isRemote && entityIn.getRidingEntity() == null && this.rand.nextInt(3) == 0) {
            entityIn.mountEntity(this);
            this.setHasCaughtPrey(true);
            return false;
        } */

        crocBite();
        setHasCaughtPrey(false);
        return super.doHurtTarget(entityIn);
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (this.isVehicle()) {

            Entity entity = damagesource.getEntity();
            if (entity != null && this.getVehicle() == entity) {
                if (this.random.nextInt(2) != 0) {
                    return false;
                } else {
                    unMount();
                }
            }

        }
        if (super.hurt(damagesource, i)) {
            Entity entity = damagesource.getEntity();

            if (this.isVehicle() && this.getVehicle() == entity) {
                if ((entity != this) && entity instanceof LivingEntity && super.shouldAttackPlayers()) {
                    setTarget((LivingEntity) entity);
                }
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return !(entity instanceof MoCEntityCrocodile);
    }

    @Override
    public void positionRider(Entity passenger) {
        if (!this.isVehicle()) {
            return;
        }
        int direction = 1;

        double dist = getEdad() * 0.01F + passenger.getBbWidth() - 0.4D;
        double newPosX = this.getX() - (dist * Math.cos((MoCTools.realAngle(this.yRot - 90F)) / 57.29578F));
        double newPosZ = this.getZ() - (dist * Math.sin((MoCTools.realAngle(this.yRot - 90F)) / 57.29578F));
        passenger.setPos(newPosX, this.getY() + getPassengersRidingOffset() + passenger.getMyRidingOffset(), newPosZ);

        if (this.spinInt > 40) {
            direction = -1;
        } else {
            direction = 1;
        }

        ((LivingEntity) passenger).yBodyRot = this.yRot * direction;
        ((LivingEntity) passenger).yBodyRotO = this.yRot * direction;
    }

    @Override
    public double getPassengersRidingOffset() {
        return this.getBbHeight() * 0.35D;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_CROCODILE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_CROCODILE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (getIsSitting()) {
            return MoCSoundEvents.ENTITY_CROCODILE_RESTING;
        }
        return MoCSoundEvents.ENTITY_CROCODILE_AMBIENT;
    }

    public boolean isSpinning() {
        return getHasCaughtPrey() && (this.isVehicle()) && (this.isSwimming());
    }

    @Override
    public void die(DamageSource damagesource) {
        unMount();
//        MoCTools.checkForTwistedEntities(this.world);
        super.die(damagesource);
    }

    public void unMount() {

        if (this.isVehicle()) {
            if (this.getVehicle() instanceof LivingEntity && ((LivingEntity) this.getVehicle()).getHealth() > 0) {
                ((LivingEntity) this.getVehicle()).deathTime = 0;
            }

            this.getVehicle().stopRiding();
            setHasCaughtPrey(false);
        }
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    @Override
    public boolean isAmphibian() {
        return true;
    }

    @Override
    public boolean isSwimming() {
        return this.isInWater();
    }

    @Override
    public boolean isReadyToHunt() {
        return this.isNotScared() && !this.isMovementCeased() && !this.isVehicle() && !this.getHasCaughtPrey();
    }
}
