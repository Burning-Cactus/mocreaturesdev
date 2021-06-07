package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.registry.MoCSoundEvents;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemStaffTeleport extends MoCItem {

    public ItemStaffTeleport(Item.Properties builder) {
        super(builder);
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
//    @Override
//    public boolean isFull3D() {
//        return true;
//    }

    /**
     * returns the action that specifies what animation to play when the items
     * is being used
     */
//    @Override
//    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
//        return EnumAction.BLOCK;
//    }

    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ActionResult<ItemStack> use(World worldIn, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        if (player.getVehicle() != null || player.isVehicle()) {
            return ActionResult.pass(stack);
        }

        double coordY = player.getY() + player.getEyeHeight();
        double coordZ = player.getZ();
        double coordX = player.getX();
        for (int x = 4; x < 128; x++) {
            double newPosY = coordY - Math.cos((player.xRot - 90F) / 57.29578F) * x;
            double newPosX =
                    coordX + Math.cos((MoCTools.realAngle(player.yRot - 90F) / 57.29578F))
                            * (Math.sin((player.xRot - 90F) / 57.29578F) * x);
            double newPosZ =
                    coordZ + Math.sin((MoCTools.realAngle(player.yRot - 90F) / 57.29578F))
                            * (Math.sin((player.xRot - 90F) / 57.29578F) * x);
            BlockPos pos = new BlockPos(MathHelper.floor(newPosX), MathHelper.floor(newPosY), MathHelper.floor(newPosZ));
            BlockState blockstate = player.level.getBlockState(pos);
            if (blockstate.getBlock() != Blocks.AIR) {
                newPosY = coordY - Math.cos((player.xRot - 90F) / 57.29578F) * (x - 1);
                newPosX =
                        coordX + Math.cos((MoCTools.realAngle(player.yRot - 90F) / 57.29578F))
                                * (Math.sin((player.xRot - 90F) / 57.29578F) * (x - 1));
                newPosZ =
                        coordZ + Math.sin((MoCTools.realAngle(player.yRot - 90F) / 57.29578F))
                                * (Math.sin((player.xRot - 90F) / 57.29578F) * (x - 1));

                if (!worldIn.isClientSide) {
                    ServerPlayerEntity playerMP = (ServerPlayerEntity) player;
                    playerMP.connection.teleport(newPosX, newPosY, newPosZ, player.yRot,
                            player.xRot);
                    MoCTools.playCustomSound(player, MoCSoundEvents.ENTITY_GENERIC_MAGIC_APPEAR);
                }
//                MoCreatures.proxy.teleportFX(player);
               // player.setItemInUse(stack, 200);
                stack.hurtAndBreak(1, player, d -> d.broadcastBreakEvent(hand));

                return ActionResult.success(stack);
            }
        }

        //player.setItemInUse(stack, this.getMaxItemUseDuration(stack));
        return ActionResult.success(stack);
    }

    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 200;
    }
}
