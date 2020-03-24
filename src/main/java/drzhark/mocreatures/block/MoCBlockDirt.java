package drzhark.mocreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public class MoCBlockDirt extends MoCBlock {

    public MoCBlockDirt(Block.Properties builder) {
        super(builder.sound(SoundType.GROUND)
                .harvestTool(ToolType.SHOVEL)
                .harvestLevel(0)
                .hardnessAndResistance(0.6F)
                .tickRandomly()
        );
    }
}
