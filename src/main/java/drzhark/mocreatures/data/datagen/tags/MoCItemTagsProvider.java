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
    protected void registerTags() {
        this.func_240521_a_(BlockTags.PLANKS, ItemTags.PLANKS);
        this.func_240521_a_(BlockTags.LOGS, ItemTags.LOGS);
        this.func_240521_a_(BlockTags.LEAVES, ItemTags.LEAVES);
    }
}
