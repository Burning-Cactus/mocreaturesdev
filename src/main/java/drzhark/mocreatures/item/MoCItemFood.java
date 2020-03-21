package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCItemGroup;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.item.Item;

public class MoCItemFood extends MoCItem { //Formerly extended ItemFood

    public MoCItemFood(int j, Item.Properties builder) {
        super(builder);
//        super(j, 0.6F, false);
//        this.setCreativeTab(MoCreatures.tabMoC);
//        this.setRegistryName(MoCConstants.MOD_ID, name);
//        this.setUnlocalizedName(name);
//        this.maxStackSize = 32;
    }

    public MoCItemFood(int j, float f, boolean flag, Item.Properties builder) {
        super(builder);
//        super(j, f, flag);
//        this.setCreativeTab(MoCreatures.tabMoC);
//        this.setRegistryName(MoCConstants.MOD_ID, name);
//        this.setUnlocalizedName(name);
    }
}
