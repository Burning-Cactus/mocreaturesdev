package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.MoCNearestAttackableTargetGoal;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class MoCEntityOgre extends MoCEntityMob {

    public int attackCounterLeft;
    public int attackCounterRight;
    private int movingHead;
    public int smashCounter;
    public int armToAnimate; // 1 = left, 2 = right, 3 = both
    public int attackCounter;

    public MoCEntityOgre(EntityType<? extends MoCEntityOgre> type, World world) {
        super(type, world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new MoCNearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityMob.registerAttributes()
//                .add(Attributes.MAX_HEALTH, calculateMaxHealth())
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public void selectType() {
        if (getSubType() == 0) {
            setType(this.random.nextInt(2) + 1);
        }
    }

    public float calculateMaxHealth() {
        return 35F;
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (super.hurt(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if (this.hasIndirectPassenger(entity)) {
                return true;
            }
            if ((entity != this) && (this.level.getDifficulty().getId() > 0) && entity instanceof LivingEntity) {
                setTarget((LivingEntity) entity);
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean shouldAttackPlayers() {
        return (this.getBrightness() < 0.5F) && super.shouldAttackPlayers();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_OGRE_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_OGRE_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_OGRE_AMBIENT;
    }

    public boolean isFireStarter() {
        return false;
    }

    /**
     * Returns the strength of the blasting power
     * @return
     */
    public float getDestroyForce() {
        return MoCConfig.COMMON_CONFIG.GENERAL.monsterSettings.ogreStrength.get().floatValue();
    }

    public int getAttackRange() {
        return MoCConfig.COMMON_CONFIG.GENERAL.monsterSettings.ogreAttackRange.get();
    }

    @Override
    public void aiStep() {
        if (!this.level.isClientSide) {
            if (this.smashCounter > 0 && ++this.smashCounter > 10) {
                this.smashCounter = 0;
                performDestroyBlastAttack();
//                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageExplode(this.getEntityId()),
//                        new TargetPoint(this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
            }

            if ((this.getTarget() != null) && (this.random.nextInt(40) == 0) && this.smashCounter == 0 && this.attackCounter == 0) {
                startDestroyBlast();
            }
        }

        if (this.attackCounter > 0) {
            if (armToAnimate == 3) {
                this.attackCounter++;
            } else {
                this.attackCounter += 2;
            }

            if (this.attackCounter > 10) {
                this.attackCounter = 0;
                this.armToAnimate = 0;
            }
        }
        super.aiStep();
    }

    /**
     * Starts counter to perform the DestroyBlast and synchronizes animations with clients
     */
    protected void startDestroyBlast() {
        this.smashCounter = 1;
//        MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
//                new TargetPoint(this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
    }

    /**
     * Performs the destroy Blast Attack
     */
    public void performDestroyBlastAttack() {
        if (this.deathTime > 0) {
            return;
        }
        MoCTools.DestroyBlast(this, this.getX(), this.getY() + 1.0D, this.getZ(), getDestroyForce(), isFireStarter());
    }

    @Override
    protected boolean isHarmedByDaylight() {
        return false;
    }

    /**
     * Starts attack counters and synchronizes animations with clients
     */
    protected void startArmSwingAttack() {
        if (!this.level.isClientSide) {
            if (this.smashCounter != 0)
                return;

            boolean leftArmW = (getSubType() == 2 || getSubType() == 4 || getSubType() == 6) && this.random.nextInt(2) == 0;

            this.attackCounter = 1;
            if (leftArmW) {
                this.armToAnimate = 1;
//                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1),
//                        new TargetPoint(this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
            } else {
                this.armToAnimate = 2;
//                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 2),
//                        new TargetPoint(this.world.getDimension().getType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType != 0) {
            this.attackCounter = 1;
            this.armToAnimate = animationType;
        }

    }

    public int getMovingHead() {
        if (getSubType() == 1) //single headed ogre
        {
            return 1;
        }

        if (this.random.nextInt(60) == 0) {
            this.movingHead = this.random.nextInt(2) + 2; //randomly changes the focus head, returns 2 or 3
        }
        return this.movingHead;
    }

    @Override
    public boolean doHurtTarget(Entity entityIn) {
        startArmSwingAttack();
        return super.doHurtTarget(entityIn);
    }
}
