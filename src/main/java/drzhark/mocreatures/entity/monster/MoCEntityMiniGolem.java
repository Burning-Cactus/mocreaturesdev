package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.MoCNearestAttackableTargetGoal;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MoCEntityMiniGolem extends MoCEntityMob {

    public int tcounter;
    public MoCEntityThrowableRock tempRock;
    private static final DataParameter<Boolean> ANGRY = EntityDataManager.defineId(MoCEntityMiniGolem.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ROCK = EntityDataManager.defineId(MoCEntityMiniGolem.class, DataSerializers.BOOLEAN);
    

    public MoCEntityMiniGolem(EntityType<? extends MoCEntityMiniGolem> type, World world) {
        super(type, world);
        this.texture = "minigolem.png";
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new MoCNearestAttackableTargetGoal(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityMob.registerAttributes()
                .add(Attributes.MAX_HEALTH, 15.0D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANGRY, Boolean.FALSE);
        this.entityData.define(HAS_ROCK, Boolean.FALSE);
    }

    public boolean getIsAngry() {
        return this.entityData.get(ANGRY);
    }

    public void setIsAngry(boolean flag) {
        this.entityData.set(ANGRY, flag);
    }

    public boolean getHasRock() {
        return this.entityData.get(HAS_ROCK);
    }

    public void setHasRock(boolean flag) {
        this.entityData.set(HAS_ROCK, flag);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
            setIsAngry(this.getTarget() != null);

            if (getIsAngry() && this.getTarget() != null) {
                if (!getHasRock() && this.random.nextInt(30) == 0) {
                    acquireTRock();
                }

                if (getHasRock()) {
                    this.getNavigation().stop();
                    attackWithTRock();
                }
            }
        }
    }

    protected void acquireTRock() {
        BlockState tRockState = MoCTools.destroyRandomBlockWithIBlockState(this, 3D);
        if (tRockState == null) {
            this.tcounter = 1;
            setHasRock(false);
            return;
        }

        //creates a dummy Trock on top of it
        MoCEntityThrowableRock trock = new MoCEntityThrowableRock(this.level, this, this.getX(), this.getY() + 1.5D, this.getZ());
        this.level.addFreshEntity(trock);
        trock.setState(tRockState);
        trock.setBehavior(1);
        this.tempRock = trock;
        setHasRock(true);
    }

    @Override
    public boolean isMovementCeased() {
        return getHasRock() && this.getTarget() != null;
    }

    /**
     *
     */
    protected void attackWithTRock() {
        this.tcounter++;

        if (this.tcounter < 50) {
            //maintains position of Trock above head
            this.tempRock.setPos(this.getX(), this.getY()+1.0D, this.getZ());
        }

        if (this.tcounter >= 50) {
            //throws a newly spawned Trock and destroys the held Trock
            if (this.getTarget() != null && this.distanceTo(this.getTarget()) < 48F) {
                MoCTools.throwStone(this, this.getTarget(), this.tempRock.getState(), 10D, 0.25D);
            }

            this.tempRock.remove();
            setHasRock(false);
            this.tcounter = 0;
        }
    }

    /**
     * Stretches the model to that size
     */
    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */
    @Override
    protected void playStepSound(BlockPos pos, BlockState block) {
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GOLEM_WALK);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_GOLEM_DYING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_GOLEM_AMBIENT;
    }

    @Override
    protected boolean isHarmedByDaylight() {
        return true;
    }
}
