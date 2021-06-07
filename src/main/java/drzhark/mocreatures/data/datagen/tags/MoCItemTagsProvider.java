package drzhark.mocreatures.data.datagen.tags;

import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;

public class MoCItemTagsProvider extends ItemTagsProvider {
    public MoCItemTagsProvider(DataGenerator generatorIn, BlockTagsProvider provider) {
        super(generatorIn, provider);
    }

    @Override
    protected void addTags() {
        this.copy(BlockTags.PLANKS, ItemTags.PLANKS);
        this.copy(BlockTags.LOGS, ItemTags.LOGS);
        this.copy(BlockTags.LEAVES, ItemTags.LEAVES);
    }
}
