package drzhark.mocreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraftforge.common.IShearable;


public class MoCBlockLeaf extends MoCBlock implements IShearable {

    public MoCBlockLeaf(Block.Properties builder) {
        super(builder
                .sound(SoundType.PLANT)
                .tickRandomly()
                .hardnessAndResistance(0.2F)
        );
    }

    //TODO: Look into making leaves decay when not connected to blocks

//    @Override
//    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, @Nullable ItemStack stack) {
//        if (!worldIn.isRemote && !stack.isEmpty() && stack.getItem() == Items.SHEARS) {
//            player.addStat(StatList.getBlockStats(this), 1);
//            spawnAsEntity(worldIn, pos, new ItemStack(MoCBlocks.mocLeaf, 1, 0));
//        } else {
//            super.harvestBlock(worldIn, player, pos, state, te, stack);
//        }
//    }
}
