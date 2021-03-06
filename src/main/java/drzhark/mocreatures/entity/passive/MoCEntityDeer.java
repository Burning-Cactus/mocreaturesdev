package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.FleeFromEntityGoal;
import drzhark.mocreatures.entity.ai.FollowAdultGoal;
import drzhark.mocreatures.entity.ai.MoCAlternateWanderGoal;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class MoCEntityDeer extends MoCEntityTameableAnimal {

    private int readyToJumpTimer;

    public MoCEntityDeer(EntityType<? extends MoCEntityDeer> type, World world) {
        super(type, world);
        setEdad(75);
        setAdult(true);
        setTamed(false);
    }
    
    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new FleeFromEntityGoal(this, entity ->
                !(entity instanceof MoCEntityDeer) && (entity.getBbHeight() > 0.8F || entity.getBbWidth() > 0.8F),
                6.0F, this.getMyAISpeed(), this.getMyAISpeed() * 1.2D));
        this.goalSelector.addGoal(2, new PanicGoal(this, this.getMyAISpeed() * 1.2D));
        this.goalSelector.addGoal(4, new FollowAdultGoal(this, getMyAISpeed()));
        this.goalSelector.addGoal(5, new MoCAlternateWanderGoal(this, getMyAISpeed()));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.35D);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            int i = this.random.nextInt(100);
            if (i <= 20) {
                setType(1);
            } else if (i <= 70) {
                setType(2);
            } else {
                setAdult(false);
                setType(3);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {

        switch (getSubType()) {
            case 1:
                return MoCreatures.getTexture("deer.png");
            case 2:
                return MoCreatures.getTexture("deerf.png");
            case 3:
                setAdult(false);
                return MoCreatures.getTexture("deerb.png");

            default:
                return MoCreatures.getTexture("deer.png");
        }
    }

    @Override
    public boolean entitiesToInclude(Entity entity) {
        return !(entity instanceof MoCEntityDeer) && super.entitiesToInclude(entity);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_DEER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_DEER_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!getIsAdult()) {
            return MoCSoundEvents.ENTITY_DEER_AMBIENT_BABY;
        } else {
            return MoCSoundEvents.ENTITY_DEER_AMBIENT;
        }
    }

    public double getMyAISpeed() {
        /*if (getType() == 1) {
            return 1.1D;
        } else if (getType() == 2) {
            return 1.3D;
        }*/
        return 1.1D;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 400;
    }

    @Override
    public int getMaxEdad() {
        return 130;
    }

    @Override
    public void setAdult(boolean flag) {
        if (!this.level.isClientSide) {
            setType(this.random.nextInt(1));
        }
        super.setAdult(flag);
    }

    @Override
    public boolean getIsAdult() {
        return this.getSubType() != 3 && super.getIsAdult();
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.level.isClientSide) {

            if (this.onGround && --this.readyToJumpTimer <= 0) {
                if (MoCTools.getMyMovementSpeed(this) > 0.17F) {
                    float velX = (float) (0.5F * Math.cos((MoCTools.realAngle(this.yRot - 90F)) / 57.29578F));
                    float velZ = (float) (0.5F * Math.sin((MoCTools.realAngle(this.yRot - 90F)) / 57.29578F));
                    this.setDeltaMovement(this.getDeltaMovement().x - velX, 0.5D, this.getDeltaMovement().z - velZ);
                    this.readyToJumpTimer = this.random.nextInt(10) + 20;
                }
            }
        }
    }

    @Override
    public float pitchRotationOffset() {
        if (!this.onGround && MoCTools.getMyMovementSpeed(this) > 0.08F) {
            if (this.getDeltaMovement().y > 0.5D) {
                return 25F;
            }
            if (this.getDeltaMovement().y < -0.5D) {
                return -25F;
            }
            return (float) (this.getDeltaMovement().y * 70D);
        }
        return 0F;
    }

    @Override
    public float getSizeFactor() {
        if (getSubType() == 1) {
            return 1.6F;
        }
        if (getSubType() == 2) {
            return 1.3F;
        }
        return getEdad() * 0.01F;
    }
}
