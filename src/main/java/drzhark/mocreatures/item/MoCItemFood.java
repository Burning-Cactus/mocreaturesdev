package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCItemGroup;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.item.Item;

public class MoCItemFood extends MoCItem { //Formerly extended ItemFood TODO: Make food work

    public MoCItemFood(int j, Item.Properties builder) {
        super(builder);
//        super(j, 0.6F, false);
//        this.maxStackSize = 32;
    }

    public MoCItemFood(int j, float f, boolean flag, Item.Properties builder) {
        super(builder);
//        super(j, f, flag);
    }
}
