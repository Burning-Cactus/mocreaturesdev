package drzhark.mocreatures.block;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ToolType;

import java.util.Random;

public class MoCBlockLog extends MoCBlock {

    public MoCBlockLog(Block.Properties builder) {
        super(builder
                .sound(SoundType.WOOD)
                .hardnessAndResistance(2.0F)
                .harvestTool(ToolType.AXE)
                .harvestLevel(0)
        );
    }

    /**
     * Returns the quantity of items to drop on block destruction.
     */
    @Override
    public int quantityDropped(Random par1Random) {
        return 1;
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    public Item getItemDropped(int par1, Random par2Random, int par3) {
        return Item.getItemFromBlock(MoCBlocks.mocLog);
    }

    @Override
    public boolean canSustainLeaves(BlockState state, IBlockAccess world, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos) {
        return true;
    }
}
