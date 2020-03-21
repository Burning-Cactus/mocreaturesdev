package drzhark.mocreatures.block;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MoCBlockRock extends MoCBlock {

    public MoCBlockRock(Block.Properties properties) {
        super(properties
                .sound(SoundType.STONE)
                .tickRandomly());
    }
}
