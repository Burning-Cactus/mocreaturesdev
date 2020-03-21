package drzhark.mocreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MoCBlockPlanks extends MoCBlock {

    public MoCBlockPlanks(Block.Properties builder) {
        super(builder
                .sound(SoundType.WOOD));
    }
}
