package drzhark.mocreatures.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCItemTurtleSoup extends MoCItemFood {

    public MoCItemTurtleSoup(int j, Item.Properties builder) {
        super(j, builder.maxStackSize(1));
    }

    public MoCItemTurtleSoup(int j, float f, boolean flag, Item.Properties builder) {
        super(j, f, flag, builder.maxStackSize(1));
    }

    @Override
    @Nullable
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, LivingEntity entityLiving) {
        super.onItemUseFinish(stack, worldIn, entityLiving);
        return new ItemStack(Items.BOWL);
    }
}
