package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class ItemBuilderHammer extends MoCItem {

    public ItemBuilderHammer() {
        super(new Item.Properties().maxStackSize(1).maxDamage(2048));
    }

    /**
     * Returns True is the item is renderer in full 3D when hold.
     */
    @Override
    public boolean isFull3D() {
        return true;
    }

    /**
     * returns the action that specifies what animation to play when the items
     * is being used
     */
    @Override
    public EnumAction getItemUseAction(ItemStack par1ItemStack) {
        return EnumAction.BLOCK;
    }

    /**
     * How long it takes to use or consume an item
     */
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return 72000;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity player, Hand hand) {
        final ItemStack stack = player.getHeldItem(hand);
        double coordY = player.getPosY() + player.getEyeHeight();
        double coordZ = player.getPosZ();
        double coordX = player.getPosX();

        for (int x = 3; x < 128; x++) {
            double newPosY = coordY - Math.cos((player.rotationPitch - 90F) / 57.29578F) * x;
            double newPosX =
                    coordX + Math.cos((MoCTools.realAngle(player.rotationYaw - 90F) / 57.29578F))
                            * (Math.sin((player.rotationPitch - 90F) / 57.29578F) * x);
            double newPosZ =
                    coordZ + Math.sin((MoCTools.realAngle(player.rotationYaw - 90F) / 57.29578F))
                            * (Math.sin((player.rotationPitch - 90F) / 57.29578F) * x);
            BlockPos pos = new BlockPos(MathHelper.floor(newPosX), MathHelper.floor(newPosY), MathHelper.floor(newPosZ));
            BlockState blockstate = player.world.getBlockState(pos);

            if (blockstate.getBlock() != Blocks.AIR) {

                newPosY = coordY - Math.cos((player.rotationPitch - 90F) / 57.29578F) * (x - 1);
                newPosX =
                        coordX + Math.cos((MoCTools.realAngle(player.rotationYaw - 90F) / 57.29578F))
                                * (Math.sin((player.rotationPitch - 90F) / 57.29578F) * (x - 1));
                newPosZ =
                        coordZ + Math.sin((MoCTools.realAngle(player.rotationYaw - 90F) / 57.29578F))
                                * (Math.sin((player.rotationPitch - 90F) / 57.29578F) * (x - 1));
                pos = new BlockPos(MathHelper.floor(newPosX), MathHelper.floor(newPosY), MathHelper.floor(newPosZ));
                if (!player.world.isAirBlock(pos)) {
                    return new ActionResult<ItemStack>(ActionResultType.FAIL, stack);
                }

                int blockInfo[] = obtainBlockAndMetadataFromBelt(player, true);
                if (blockInfo[0] != 0) {
                    if (!worldIn.isRemote) {
                        Block block = Block.getStateById(blockInfo[0]).getBlock();
                        player.world.setBlockState(pos, block.getDefaultState(), 3);
                        player.world.playSound(player, (float) newPosX + 0.5F, (float) newPosY + 0.5F, (float) newPosZ + 0.5F,
                                block.getDefaultState().getSoundType().getPlaceSound(), SoundCategory.BLOCKS, (block.getDefaultState().getSoundType().getVolume() + 1.0F) / 2.0F, block.getDefaultState().getSoundType().getPitch() * 0.8F);
                    }
                    MoCreatures.proxy.hammerFX(player);
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
            ItemStack slotStack = entityplayer.inventory.getStackInSlot(y);
            if (slotStack.isEmpty()) {
                continue;
            }
            Item itemTemp = slotStack.getItem();
            int metadata = slotStack.getDamage();
            if (itemTemp instanceof BlockItem) {
                if (remove && !entityplayer.getCapability(/*Check for creative mode*/)) {
                    slotStack.shrink(1);
                    if (slotStack.isEmpty()) {
                        entityplayer.inventory.setInventorySlotContents(y, ItemStack.EMPTY);
                    } else {
                        entityplayer.inventory.setInventorySlotContents(y, slotStack);
                    }
                }
                return new int[] {Item.getIdFromItem(itemTemp), metadata};
            }
        }
        return new int[] {0, 0};
    }

    @Override
    public ActionResultType
            onItemUse(ItemUseContext context /*PlayerEntity playerIn, World worldIn, BlockPos pos, Hand hand, EnumFacing side, float hitX, float hitY, float hitZ*/) {
        return ActionResultType.FAIL;
    }
}
