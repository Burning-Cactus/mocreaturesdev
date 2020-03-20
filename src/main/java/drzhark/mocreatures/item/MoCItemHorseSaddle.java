package drzhark.mocreatures.item;

import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;

public class MoCItemHorseSaddle extends MoCItem {

    public MoCItemHorseSaddle() {
        super(new Item.Properties().maxStackSize(32));
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, PlayerEntity playerIn, LivingEntity target, Hand hand) {
        if (target instanceof MoCEntityHorse) {
            MoCEntityHorse entityhorse = (MoCEntityHorse) target;
            if (!entityhorse.getIsRideable() && entityhorse.getIsAdult()) {
                entityhorse.setRideable(true);
                stack.shrink(1);
                return true;
            }
        }
        return false;
    }
}
