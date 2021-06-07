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
    protected void addTags() {
        this.tag(BlockTags.PLANKS).add(MoCBlocks.WYVERN_PLANKS, MoCBlocks.OGRE_PLANKS);
        this.tag(BlockTags.LOGS).add(MoCBlocks.WYVERN_LOG, MoCBlocks.OGRE_LOG);
        this.tag(BlockTags.LEAVES).add(MoCBlocks.WYVERN_LEAVES, MoCBlocks.OGRE_LEAVES);
    }
}
