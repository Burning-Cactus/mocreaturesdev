package drzhark.mocreatures.block;

import drzhark.mocreatures.registry.MoCBlocks;
import net.minecraft.block.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;

import java.util.Random;

public class MoCBlockTallGrass extends BushBlock implements IForgeShearable, IGrowable {

//    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);
//    protected static final VoxelShape SHAPE = VoxelShapes.create(AABB);
protected static final VoxelShape SHAPE = Block.box(2.0D, 0.0D, 2.0D, 14.0D, 13.0D, 14.0D);
    public MoCBlockTallGrass(AbstractBlock.Properties builder) {
        super(builder
                .sound(SoundType.GRASS)
                .lightLevel((f) -> {
                    return 1;
                }));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return state.is(MoCBlocks.WYVERN_GRASS) || state.is(MoCBlocks.WYVERN_DIRT) || state.is(MoCBlocks.OGRE_GRASS) || state.is(MoCBlocks.OGRE_DIRT);
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isValidBonemealTarget(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean b) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void performBonemeal(ServerWorld serverWorld, Random random, BlockPos blockPos, BlockState blockState) {

    }
}
