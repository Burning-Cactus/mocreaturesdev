package drzhark.mocreatures.item;

import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.registry.MoCEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class MoCItemLitterBox extends MoCItem {

    public MoCItemLitterBox(Item.Properties builder) {
        super(builder);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        if (!world.isClientSide) {
            stack.shrink(1);
            MoCEntityLitterBox entitylitterbox = new MoCEntityLitterBox(MoCEntities.LITTERBOX, world);
            entitylitterbox.setPos(player.getX(), player.getY(), player.getZ());
            player.level.addFreshEntity(entitylitterbox);
            entitylitterbox.getDeltaMovement().add((world.random.nextFloat() - world.random.nextFloat()) * 0.3F, world.random.nextFloat() * 0.05F, (world.random.nextFloat() - world.random.nextFloat()) * 0.3F);
        }
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }
}
