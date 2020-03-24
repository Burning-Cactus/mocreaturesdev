package drzhark.mocreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MoCBlockRock extends MoCBlock {

    public MoCBlockRock(Block.Properties properties) {
        super(properties
                .sound(SoundType.STONE)
                .hardnessAndResistance(1.5F, 10.0F)
                .harvestTool(ToolType.PICKAXE)
                .harvestLevel(1)
                .tickRandomly()
        );
    }
}
