package drzhark.mocreatures.block;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import java.util.Random;

import javax.annotation.Nullable;

public class MoCBlockLeaf extends MoCBlock {

    public MoCBlockLeaf(Block.Properties builder) {
        super(builder
                .sound(SoundType.PLANT)
                .tickRandomly());
    }

    @Override
    public boolean isFullCube(BlockState state) {
        return false;
    }

    @Override
    public boolean isLeaves(BlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public int quantityDropped(Random random) {
        return random.nextInt(20) != 0 ? 0 : 1;
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, @Nullable ItemStack stack) {
        if (!worldIn.isRemote && !stack.isEmpty() && stack.getItem() == Items.SHEARS) {
            player.addStat(StatList.getBlockStats(this), 1);
            spawnAsEntity(worldIn, pos, new ItemStack(MoCBlocks.mocLeaf, 1, 0));
        } else {
            super.harvestBlock(worldIn, player, pos, state, te, stack);
        }
    }

    @Override
    public int quantityDropped(BlockState state, int fortune, Random random) {
        return 0;
    }

}
