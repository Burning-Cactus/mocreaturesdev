package drzhark.mocreatures.data.datagen.tags;

import drzhark.mocreatures.registry.MoCBlocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;

public class MoCBlockTagsProvider extends BlockTagsProvider {
    public MoCBlockTagsProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerTags() {
        this.func_240522_a_(BlockTags.PLANKS).func_240534_a_(MoCBlocks.WYVERN_PLANKS, MoCBlocks.OGRE_PLANKS);
        this.func_240522_a_(BlockTags.LOGS).func_240534_a_(MoCBlocks.WYVERN_LOG, MoCBlocks.OGRE_LOG);
        this.func_240522_a_(BlockTags.LEAVES).func_240534_a_(MoCBlocks.WYVERN_LEAVES, MoCBlocks.OGRE_LEAVES);
    }
}
