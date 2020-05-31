package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class MoCEntityDuck extends MoCEntityAnimal//EntityChicken
{

    public boolean field_70885_d = false;
    public float field_70886_e = 0.0F;
    public float destPos = 0.0F;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;

    public MoCEntityDuck(EntityType<? extends MoCEntityDuck> type, World world) {
        super(type, world);
        this.texture = "duck.png";
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_DUCK_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_DUCK_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_DUCK_AMBIENT;
    }

    @Override
    public boolean canDespawn(double d) {
        if (MoCConfig.COMMON_CONFIG.GLOBAL.forceDespawns.get()) {
            return !getIsTamed();
        } else {
            return false;
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos = (float) (this.destPos + (this.onGround ? -1 : 4) * 0.3D);

        if (this.destPos < 0.0F) {
            this.destPos = 0.0F;
        }

        if (this.destPos > 1.0F) {
            this.destPos = 1.0F;
        }

        if (!this.onGround && this.field_70889_i < 1.0F) {
            this.field_70889_i = 1.0F;
        }

        this.field_70889_i = (float) (this.field_70889_i * 0.9D);

        if (!this.onGround && this.getMotion().y < 0.0D) {
            this.getMotion().mul(1, 0.6D, 1);
        }

        this.field_70886_e += this.field_70889_i * 2.0F;
    }

    @Override
    public boolean renderName() {
        return false;
    }


//    @Override
//    protected Item getDropItem() {
//        return Items.FEATHER;
//    }
}
