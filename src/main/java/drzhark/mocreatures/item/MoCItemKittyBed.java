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
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            MoCEntityKittyBed entitykittybed = new MoCEntityKittyBed(MoCEntities.KITTY_BED, world/*, this.sheetType*/);
            entitykittybed.setPos(player.getX(), player.getY(), player.getZ());
            world.addFreshEntity(entitykittybed);
            entitykittybed.getDeltaMovement().add((world.random.nextFloat() - world.random.nextFloat()) * 0.3F, world.random.nextFloat() * 0.05F, (world.random.nextFloat() - world.random.nextFloat()) * 0.3F);
            stack.shrink(1);
            if (stack.isEmpty()) {
                player.setItemInHand(hand, ItemStack.EMPTY);
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }
}
