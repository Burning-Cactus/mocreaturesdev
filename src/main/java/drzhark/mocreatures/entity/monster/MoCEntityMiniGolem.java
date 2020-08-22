package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
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
    private static final DataParameter<Boolean> ANGRY = EntityDataManager.createKey(MoCEntityMiniGolem.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_ROCK = EntityDataManager.createKey(MoCEntityMiniGolem.class, DataSerializers.BOOLEAN);
    

    public MoCEntityMiniGolem(EntityType<? extends MoCEntityMiniGolem> type, World world) {
        super(type, world);
        this.texture = "minigolem.png";
    }


    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityMob.registerAttributes()
                .func_233815_a_(Attributes.MAX_HEALTH, 15.0D)
                .func_233815_a_(Attributes.ATTACK_DAMAGE, 2.0D)
                .func_233815_a_(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(ANGRY, Boolean.FALSE);
        this.dataManager.register(HAS_ROCK, Boolean.FALSE);
    }

    public boolean getIsAngry() {
        return this.dataManager.get(ANGRY);
    }

    public void setIsAngry(boolean flag) {
        this.dataManager.set(ANGRY, flag);
    }

    public boolean getHasRock() {
        return this.dataManager.get(HAS_ROCK);
    }

    public void setHasRock(boolean flag) {
        this.dataManager.set(HAS_ROCK, flag);
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {
            setIsAngry(this.getAttackTarget() != null);

            if (getIsAngry() && this.getAttackTarget() != null) {
                if (!getHasRock() && this.rand.nextInt(30) == 0) {
                    acquireTRock();
                }

                if (getHasRock()) {
                    this.getNavigator().clearPath();
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
        MoCEntityThrowableRock trock = new MoCEntityThrowableRock(this.world, this, this.getPosX(), this.getPosY() + 1.5D, this.getPosZ());
        this.world.addEntity(trock);
        trock.setState(tRockState);
        trock.setBehavior(1);
        this.tempRock = trock;
        setHasRock(true);
    }

    @Override
    public boolean isMovementCeased() {
        return getHasRock() && this.getAttackTarget() != null;
    }

    /**
     *
     */
    protected void attackWithTRock() {
        this.tcounter++;

        if (this.tcounter < 50) {
            //maintains position of Trock above head
            this.tempRock.setPosition(this.getPosX(), this.getPosY()+1.0D, this.getPosZ());
        }

        if (this.tcounter >= 50) {
            //throws a newly spawned Trock and destroys the held Trock
            if (this.getAttackTarget() != null && this.getDistance(this.getAttackTarget()) < 48F) {
                MoCTools.throwStone(this, this.getAttackTarget(), this.tempRock.getState(), 10D, 0.25D);
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
