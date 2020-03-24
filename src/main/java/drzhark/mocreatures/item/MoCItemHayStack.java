package drzhark.mocreatures.item;

import net.minecraft.item.Item;

public class MoCItemHayStack extends MoCItem {

    public MoCItemHayStack(Item.Properties builder) {
        super(builder.maxStackSize(16));
    }
}
