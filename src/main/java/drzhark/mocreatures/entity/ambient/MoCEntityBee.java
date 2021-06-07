// todo freeze for some time if close to flower
// attack player if player attacks hive?
// hive block (honey, bee spawner)

package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class MoCEntityBee extends MoCEntityInsect
{

    private int soundCount;

    public MoCEntityBee(EntityType<? extends MoCEntityBee> type, World world) {
        super(type, world);
        this.texture = "bee.png";
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {
            if (getIsFlying() && --this.soundCount == -1) {
                PlayerEntity ep = this.level.getNearestPlayer(this, 5D);
                if (ep != null) {
                    MoCTools.playCustomSound(this, getMySound());
                    this.soundCount = 20;
                }
            }

            if (getIsFlying() && this.random.nextInt(500) == 0) {
                setIsFlying(false);
            }
        }
    }

    private SoundEvent getMySound() {
        if (getTarget() != null) {
            return MoCSoundEvents.ENTITY_BEE_UPSET;
        }
        return MoCSoundEvents.ENTITY_BEE_AMBIENT;
    }

    @Override
    public int getAmbientSoundInterval() {
        return 2000;
    }

    @Override
    protected float getSoundVolume() {
        return 0.1F;
    }

    @Override
    public boolean hurt(DamageSource damagesource, float i) {
        if (super.hurt(damagesource, i)) {
            Entity entity = damagesource.getEntity();
            if (entity instanceof LivingEntity) {
                LivingEntity entityliving = (LivingEntity) entity;
                if ((entity != this) && (this.level.getDifficulty().getId() > 0)) {
                    setTarget(entityliving);
                }
                return true;
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty()
                && (stack.getItem() == Item.byBlock(Blocks.POPPY) || stack.getItem() == Item
                        .byBlock(Blocks.DANDELION));
    }

    @Override
    public float getSpeed() {
        if (getIsFlying()) {
            return 0.15F;
        }
        return 0.12F;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }
}
