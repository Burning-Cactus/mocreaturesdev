package drzhark.mocreatures.item;

import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.registry.MoCEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MoCItemKittyBed extends MoCItem {

    private int sheetType;

    public MoCItemKittyBed(Item.Properties builder) {
        super(builder);
    }

    public MoCItemKittyBed(Item.Properties builder, int type) {
        this(builder);
        this.sheetType = type;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            MoCEntityKittyBed entitykittybed = new MoCEntityKittyBed(MoCEntities.KITTY_BED, world/*, this.sheetType*/);
            entitykittybed.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
            world.addEntity(entitykittybed);
            entitykittybed.getMotion().add((world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F, world.rand.nextFloat() * 0.05F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F);
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, ItemStack.EMPTY);
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }
}
