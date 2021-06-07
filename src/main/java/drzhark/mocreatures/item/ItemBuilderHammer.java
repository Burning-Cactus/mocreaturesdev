package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemBuilderHammer extends MoCItem {

    public ItemBuilderHammer(Item.Properties builder) {
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
     * is being used TODO: Make right click animation work
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
        double coordY = player.getY() + player.getEyeHeight();
        double coordZ = player.getZ();
        double coordX = player.getX();

        for (int x = 3; x < 128; x++) {
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
                pos = new BlockPos(MathHelper.floor(newPosX), MathHelper.floor(newPosY), MathHelper.floor(newPosZ));
                if (!player.level.isEmptyBlock(pos)) {
                    return new ActionResult<ItemStack>(ActionResultType.FAIL, stack);
                }

                int blockInfo[] = obtainBlockAndMetadataFromBelt(player, true);
                if (blockInfo[0] != 0) {
                    if (!worldIn.isClientSide) {
                        Block block = Block.stateById(blockInfo[0]).getBlock();
                        player.level.setBlock(pos, block.defaultBlockState(), 3);
                        player.level.playSound(player, (float) newPosX + 0.5F, (float) newPosY + 0.5F, (float) newPosZ + 0.5F,
                                block.defaultBlockState().getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (block.defaultBlockState().getSoundType().getVolume() + 1.0F) / 2.0F, block.defaultBlockState().getSoundType().getPitch() * 0.8F);
                    }
//                    MoCreatures.proxy.hammerFX(player);
                    //entityplayer.setItemInUse(par1ItemStack, 200);
                }
                return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
            }
        }
        return new ActionResult<ItemStack>(ActionResultType.SUCCESS, stack);
    }

    /**
     * Finds a block from the belt inventory of player, passes the block ID and
     * Metadata and reduces the stack by 1 if not on Creative mode
     *
     * @param entityplayer
     * @return
     */
    private int[] obtainBlockAndMetadataFromBelt(PlayerEntity entityplayer, boolean remove) {
        for (int y = 0; y < 9; y++) {
            ItemStack slotStack = entityplayer.inventory.getItem(y);
            if (slotStack.isEmpty()) {
                continue;
            }
            Item itemTemp = slotStack.getItem();
            int metadata = slotStack.getDamageValue();
            if (itemTemp instanceof BlockItem) {
                if (remove && !entityplayer.isCreative()) {
                    slotStack.shrink(1);
                    if (slotStack.isEmpty()) {
                        entityplayer.inventory.setItem(y, ItemStack.EMPTY);
                    } else {
                        entityplayer.inventory.setItem(y, slotStack);
                    }
                }
                return new int[] {Item.getId(itemTemp), metadata};
            }
        }
        return new int[] {0, 0};
    }

    @Override
    public ActionResultType
            useOn(ItemUseContext context /*PlayerEntity playerIn, World worldIn, BlockPos pos, Hand hand, EnumFacing side, float hitX, float hitY, float hitZ*/) {
        return ActionResultType.FAIL;
    }
}
