package drzhark.mocreatures;

import drzhark.mocreatures.registry.MoCItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class MoCItemGroup extends ItemGroup {
    public static final ItemGroup TABMOC = new MoCItemGroup("MoCreatures");

    public MoCItemGroup(String label) {
        super(label);
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public ItemStack createIcon() {
        return new ItemStack(MoCItems.HORSESADDLE);
    }
}
