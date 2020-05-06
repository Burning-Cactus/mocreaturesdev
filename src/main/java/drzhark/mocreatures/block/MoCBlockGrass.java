package drzhark.mocreatures.block;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class MoCBlockGrass extends MoCBlock implements IGrowable {

    public MoCBlockGrass(Block.Properties builder) {
        super(builder
                .sound(SoundType.PLANT)
                .hardnessAndResistance(0.5F)
                .harvestTool(ToolType.SHOVEL)
                .harvestLevel(0)
                .tickRandomly()
        );
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean b) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, BlockState blockState) {
        return false;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random, BlockPos blockPos, BlockState blockState) {

    }

//    public void updateTick(World worldIn, BlockPos pos, BlockState state, Random rand) {
//        if (!worldIn.isRemote) {
//            if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity(worldIn, pos.up()) > 2) {
//                worldIn.setBlockState(pos, MoCBlocks.MOCDIRT.getDefaultState());
//            } else {
//                if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
//                    for (int i = 0; i < 4; ++i) {
//                        BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
//                        BlockState blockstate = worldIn.getBlockState(blockpos1.up());
//                        BlockState iblockstate1 = worldIn.getBlockState(blockpos1);
//
//                        if (iblockstate1.getBlock() == MoCBlocks.mocDirt && worldIn.getLightFromNeighbors(blockpos1.up()) >= 4
//                                && blockstate.getLightOpacity(worldIn, blockpos1.up()) <= 2) {
//                            worldIn.setBlockState(blockpos1, MoCBlocks.mocGrass.getDefaultState());
//                        }
//                    }
//                }
//            }
//        }
//    }
}
