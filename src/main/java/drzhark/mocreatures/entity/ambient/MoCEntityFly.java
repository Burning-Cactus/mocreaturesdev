package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

public class MoCEntityFly extends MoCEntityInsect {

    public MoCEntityFly(EntityType<? extends MoCEntityFly> type, World world) {
        super(type, world);
        this.texture = "fly.png";
    }

    private int soundCount;// = 50;

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {

            if (getIsFlying() && this.rand.nextInt(200) == 0) {
                setIsFlying(false);
            }
            if (getIsFlying() && --this.soundCount == -1) {
                PlayerEntity ep = this.world.getClosestPlayer(this, 5D);
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
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.2F;
        }
        return 0.12F;
    }
}
