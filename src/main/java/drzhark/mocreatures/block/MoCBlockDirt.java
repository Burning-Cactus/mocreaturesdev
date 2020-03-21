package drzhark.mocreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MoCBlockDirt extends MoCBlock {

    public MoCBlockDirt(Block.Properties builder) {
        super(builder.sound(SoundType.GROUND).tickRandomly());
    }
}
