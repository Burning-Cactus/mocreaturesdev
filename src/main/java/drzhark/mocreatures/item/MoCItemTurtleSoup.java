package drzhark.mocreatures.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import javax.annotation.Nullable;

import net.minecraft.item.Item.Properties;

public class MoCItemTurtleSoup extends MoCItem {
    public MoCItemTurtleSoup(Properties builder) {
        super(builder);
    }

    @Override
    @Nullable
    public ItemStack finishUsingItem(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        super.finishUsingItem(stack, worldIn, entityLiving);
        return new ItemStack(Items.BOWL);
    }
}
