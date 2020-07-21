package drzhark.mocreatures.data.datagen.tags;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;

public class MoCBlockTagsProvider extends BlockTagsProvider {
    public MoCBlockTagsProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerTags() {
        this.getBuilder(BlockTags.PLANKS).add(MoCBlocks.WYVERN_WOODPLANK).add(MoCBlocks.OGRE_WOODPLANK);
        this.getBuilder(BlockTags.LOGS).add(MoCBlocks.WYVERN_LOG).add(MoCBlocks.OGRE_LOG);
        this.getBuilder(BlockTags.LEAVES).add(MoCBlocks.WYVERN_LEAVES).add(MoCBlocks.OGRE_LEAVES);
    }
}
