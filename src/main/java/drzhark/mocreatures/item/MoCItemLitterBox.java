package drzhark.mocreatures.item;

import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MoCItemLitterBox extends MoCItem {

    public MoCItemLitterBox() {
        super(new Item.Properties().maxStackSize(16));
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        if (!world.isRemote) {
            stack.shrink(1);
            MoCEntityLitterBox entitylitterbox = new MoCEntityLitterBox(MoCEntities.LITTERBOX, world);
            entitylitterbox.setPosition(player.getPosX(), player.getPosY(), player.getPosZ());
            player.world.addEntity(entitylitterbox);
            entitylitterbox.getMotion().add((world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F, world.rand.nextFloat() * 0.05F, (world.rand.nextFloat() - world.rand.nextFloat()) * 0.3F);
        }
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }
}
