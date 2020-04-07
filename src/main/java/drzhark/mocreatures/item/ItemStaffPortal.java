package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.dimension.MoCDirectTeleporter;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

public class ItemStaffPortal extends MoCItem {

    public ItemStaffPortal() {
        super(new Item.Properties().maxStackSize(1).maxDamage(3));
    }

    private int portalPosX;
    private int portalPosY;
    private int portalPosZ;
    private int portalDimension;

    @Override
    public ActionResultType
            onItemUse(ItemUseContext context /*PlayerEntity player, World worldIn, BlockPos pos, Hand hand, Direction side, float hitX, float hitY, float hitZ*/) {
        PlayerEntity player = context.getPlayer();
        World worldIn = context.getWorld();
        BlockPos pos = context.getPos();
        Hand hand = context.getHand();
        Direction side = context.getFace();
        Vec3d hit = context.getHitVec();
        final ItemStack stack = player.getHeldItem(hand);
        if (worldIn.isRemote) {
            return ActionResultType.FAIL;
        }
        final boolean hasMending = EnchantmentHelper.getEnchantmentLevel(Enchantments.MENDING, stack) > 0;
        final boolean hasUnbreaking = EnchantmentHelper.getEnchantmentLevel(Enchantments.UNBREAKING, stack) > 0;
        if (hasMending || hasUnbreaking) {
            String enchantments = "unbreaking";
            if (hasMending && hasUnbreaking) {
                enchantments = "mending, unbreaking";
            } else if (hasMending) {
                enchantments = "mending";
            }
            player.sendMessage(new TranslationTextComponent(MoCreatures.MOC_LOGO +
                TextFormatting.RED + " Detected illegal enchantment(s) '" + TextFormatting.GREEN + enchantments + 
                    TextFormatting.RED + "' on Staff Portal!\nThe item has been removed from your inventory."));
            player.inventory.deleteStack(stack);
            return ActionResultType.SUCCESS;
        }

        if (stack.getTag() == null) {
            stack.setTag(new CompoundNBT());
        }

        CompoundNBT nbtcompound = stack.getTag();

        ServerPlayerEntity playerMP = (ServerPlayerEntity) player;
        if (player.getRidingEntity() != null || player.isBeingRidden()) {
            return ActionResultType.FAIL;
        } else {
            if (player.dimension != MoCreatures.WyvernLairDimensionID) {
                this.portalDimension = player.dimension;
                this.portalPosX = (int) player.getPosX();
                this.portalPosY = (int) player.getPosY();
                this.portalPosZ = (int) player.getPosZ();
                writeToNBT(nbtcompound);

                BlockPos var2 = playerMP.server.getWorld(MoCreatures.WyvernLairDimensionID).getSpawnCoordinate();

                if (var2 != null) {
                    playerMP.connection.setPlayerLocation(var2.getX(), var2.getY(), var2.getZ(), 0.0F, 0.0F);
                }
                playerMP.server.getPlayerList().transferPlayerToDimension(playerMP, MoCreatures.WyvernLairDimensionID,
                        new MoCDirectTeleporter(playerMP.server.getWorld(MoCreatures.WyvernLairDimensionID)));
                stack.damageItem(1, player);
                return ActionResultType.SUCCESS;
            } else {
                //on the WyvernLair!
                if ((player.getPosX() > 1.5D || player.getPosX() < -1.5D) || (player.getPosZ() > 2.5D || player.getPosZ() < -2.5D)) {
                    return ActionResultType.FAIL;
                }
                readFromNBT(nbtcompound);

                boolean foundSpawn = false;
                if (this.portalPosX == 0 && this.portalPosY == 0 && this.portalPosZ == 0) //dummy staff
                {
                    BlockPos var2 = playerMP.server.getWorld(0).getSpawnPoint();

                    if (var2 != null) {
                        for (int i1 = 0; i1 < 60; i1++) {
                            BlockState blockstate = playerMP.server.getWorld(0).getBlockState(pos.add(0, i1, 0));
                            BlockState blockstate1 = playerMP.server.getWorld(0).getBlockState(pos.add(0, i1 + 1, 0));

                            if (blockstate.getBlock() == Blocks.AIR && blockstate1.getBlock() == Blocks.AIR) {
                                playerMP.connection.setPlayerLocation(var2.getX(), (double) var2.getY() + i1 + 1, var2.getZ(), 0.0F,
                                        0.0F);
                                if (MoCreatures.proxy.debug) {
                                    System.out.println("MoC Staff teleporter found location at spawn");
                                }
                                foundSpawn = true;
                                break;
                            }
                        }

                        if (!foundSpawn) {
                            if (MoCreatures.proxy.debug) {
                                System.out.println("MoC Staff teleporter couldn't find an adequate teleport location at spawn");
                            }
                            return ActionResultType.FAIL;
                        }
                    } else {
                        if (MoCreatures.proxy.debug) {
                            System.out.println("MoC Staff teleporter couldn't find spawn point");
                        }
                        return ActionResultType.FAIL;
                    }
                } else {
                    playerMP.connection.setPlayerLocation(this.portalPosX, (this.portalPosY) + 1D, this.portalPosZ, 0.0F, 0.0F);
                }

                stack.damageItem(1, player);
                playerMP.server.getPlayerList().transferPlayerToDimension(playerMP, this.portalDimension,
                        new MoCDirectTeleporter(playerMP.server.getWorld(0)));
                return ActionResultType.SUCCESS;
            }
        }
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

    public void readFromNBT(CompoundNBT nbt) {
        this.portalPosX = nbt.getInt("portalPosX");
        this.portalPosY = nbt.getInt("portalPosY");
        this.portalPosZ = nbt.getInt("portalPosZ");
        this.portalDimension = nbt.getInt("portalDimension");
    }

    public void writeToNBT(CompoundNBT nbt) {
        nbt.putInt("portalPosX", this.portalPosX);
        nbt.putInt("portalPosY", this.portalPosY);
        nbt.putInt("portalPosZ", this.portalPosZ);
        nbt.putInt("portalDimension", this.portalDimension);
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return false;
    }
}
