package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class FlyEntity extends MoCEntityInsect {

    public FlyEntity(EntityType<? extends FlyEntity> type, World world) {
        super(type, world);
        this.texture = "fly.png";
    }

    private int soundCount;// = 50;

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level.isClientSide) {

            if (getIsFlying() && this.random.nextInt(200) == 0) {
                setIsFlying(false);
            }
            if (getIsFlying() && --this.soundCount == -1) {
                PlayerEntity ep = this.level.getNearestPlayer(this, 5D);
                if (ep != null) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_FLY_AMBIENT);
                    this.soundCount = 55;
                }
            }
        }
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.ROTTEN_FLESH;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getSpeed() {
        if (getIsFlying()) {
            return 0.2F;
        }
        return 0.12F;
    }
}
