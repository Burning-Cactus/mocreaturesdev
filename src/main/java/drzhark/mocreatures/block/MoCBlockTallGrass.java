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

    protected static final AxisAlignedBB AABB = new AxisAlignedBB(0.1D, 0.0D, 0.1D, 0.9D, 0.8D, 0.9D);
    protected static final VoxelShape SHAPE = VoxelShapes.create(AABB);

    public MoCBlockTallGrass(Block.Properties builder) {
        super(builder
                .sound(SoundType.PLANT));
    }

    public MoCBlockTallGrass(Block.Properties builder, boolean lighted) {
        this(builder);
//        if (lighted) {
//            this.setLightLevel(0.8F);
//        }
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        Block block = state.getBlock();
        return block == MoCBlocks.WYVERN_GRASS || block == MoCBlocks.WYVERN_DIRT || block == MoCBlocks.OGRE_GRASS || block == MoCBlocks.OGRE_DIRT;
    }

    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canGrow(IBlockReader iBlockReader, BlockPos blockPos, BlockState blockState, boolean b) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World world, Random random, BlockPos blockPos, BlockState blockState) {
        return true;
    }

    @Override
    public void grow(ServerWorld serverWorld, Random random, BlockPos blockPos, BlockState blockState) {

    }
}
