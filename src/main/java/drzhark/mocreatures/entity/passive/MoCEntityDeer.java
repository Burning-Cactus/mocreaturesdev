package drzhark.mocreatures.entity.passive;

import com.google.common.base.Predicate;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityTameableAnimal;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
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
        this.goalSelector.addGoal(1, new EntityAIFleeFromEntityMoC(this, entity ->
                !(entity instanceof MoCEntityDeer) && (entity.getHeight() > 0.8F || entity.getWidth() > 0.8F),
                6.0F, this.getMyAISpeed(), this.getMyAISpeed() * 1.2D));
        this.goalSelector.addGoal(2, new PanicGoal(this, this.getMyAISpeed() * 1.2D));
        this.goalSelector.addGoal(4, new EntityAIFollowAdult(this, getMyAISpeed()));
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, getMyAISpeed()));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityTameableAnimal.registerAttributes()
                .func_233815_a_(Attributes.MAX_HEALTH, 10.0D)
                .func_233815_a_(Attributes.MOVEMENT_SPEED, 0.35D);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            int i = this.rand.nextInt(100);
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
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public int getMaxEdad() {
        return 130;
    }

    @Override
    public void setAdult(boolean flag) {
        if (!this.world.isRemote) {
            setType(this.rand.nextInt(1));
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

        if (!this.world.isRemote) {

            if (this.onGround && --this.readyToJumpTimer <= 0) {
                if (MoCTools.getMyMovementSpeed(this) > 0.17F) {
                    float velX = (float) (0.5F * Math.cos((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F));
                    float velZ = (float) (0.5F * Math.sin((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F));
                    this.setMotion(this.getMotion().x - velX, 0.5D, this.getMotion().z - velZ);
                    this.readyToJumpTimer = this.rand.nextInt(10) + 20;
                }
            }
        }
    }

    @Override
    public float pitchRotationOffset() {
        if (!this.onGround && MoCTools.getMyMovementSpeed(this) > 0.08F) {
            if (this.getMotion().y > 0.5D) {
                return 25F;
            }
            if (this.getMotion().y < -0.5D) {
                return -25F;
            }
            return (float) (this.getMotion().y * 70D);
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
