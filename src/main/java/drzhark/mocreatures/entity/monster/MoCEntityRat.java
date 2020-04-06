package drzhark.mocreatures.entity.monster;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.ai.EntityAINearestAttackableTargetMoC;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.Iterator;
import java.util.List;

public class MoCEntityRat extends MoCEntityMob {

    public MoCEntityRat(World world) {
        super(world);
        setSize(0.5F, 0.5F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MeleeAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.targetSelector.addGoal(1, new EntityAINearestAttackableTargetMoC(this, PlayerEntity.class, true));
    }

    @Override
    protected void registerAttributes() {
        super.registerAttributes();
        this.getAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.3D);
        this.getAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 65) {
                setType(1);
            } else if (i <= 98) {
                setType(2);
            } else {
                setType(3);
            }
        }
    }

    @Override
    protected double getAttackStrenght() {
        return 1D;
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getType()) {
            case 1:
                return MoCreatures.getTexture("ratb.png");
            case 2:
                return MoCreatures.getTexture("ratbl.png");
            case 3:
                return MoCreatures.getTexture("ratw.png");

            default:
                return MoCreatures.getTexture("ratb.png");
        }
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        Entity entity = damagesource.getTrueSource();

        if (entity != null && entity instanceof LivingEntity) {
            setAttackTarget((LivingEntity) entity);
            if (!this.world.isRemote) {
                List<MoCEntityRat> list =
                        this.world.getEntitiesWithinAABB(MoCEntityRat.class,
                                new AxisAlignedBB(this.getPosX(), this.getPosY(), this.getPosZ(), this.getPosX() + 1.0D, this.getPosY() + 1.0D, this.getPosZ() + 1.0D)
                                        .expand(16D, 4D, 16D));
                Iterator<MoCEntityRat> iterator = list.iterator();
                do {
                    if (!iterator.hasNext()) {
                        break;
                    }
                    MoCEntityRat entityrat = (MoCEntityRat) iterator.next();
                    if ((entityrat != null) && (entityrat.getAttackTarget() == null)) {
                        entityrat.setAttackTarget((LivingEntity) entity);
                    }
                } while (true);
            }
        }
        return super.attackEntityFrom(damagesource, i);
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if ((this.rand.nextInt(100) == 0) && (this.getBrightness() > 0.5F)) {
            setAttackTarget(null);
            return;
        }
    }

    @Override
    protected Item getDropItem() {
        return MoCItems.RATRAW;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_RAT_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_RAT_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_RAT_AMBIENT;
    }

    @Override
    public boolean isOnLadder() {
        return this.collidedHorizontally;
    }

    @Override
    public boolean shouldAttackPlayers() {
        return (this.getBrightness() < 0.5F) && super.shouldAttackPlayers();
    }
}
