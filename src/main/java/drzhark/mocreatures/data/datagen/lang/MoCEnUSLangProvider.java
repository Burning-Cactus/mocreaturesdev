package drzhark.mocreatures.data.datagen.lang;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.data.DataGenerator;

public class MoCEnUSLangProvider extends MoCLangProvider {
    public MoCEnUSLangProvider(DataGenerator gen) {
        super(gen, "en_us");
    }

    @Override
    protected void addTranslations() {
        add(MoCBlocks.WYVERN_DIRT, "Wyvern Dirt");
        add(MoCBlocks.WYVERN_GRASS, "Wyvern Grass");
        add(MoCBlocks.WYVERN_LOG, "Wyvern Log");
        add(MoCBlocks.WYVERN_STONE, "Wyvern Stone");
        add(MoCBlocks.WYVERN_WOODPLANK, "Wyvern Wooden Planks");
        add(MoCBlocks.WYVERN_TALLGRASS, "Wyvern Tallgrass");
        add(MoCBlocks.WYVERN_LEAVES, "Wyvern Leaves");

        add(MoCBlocks.OGRE_DIRT, "Ogre Dirt");
        add(MoCBlocks.OGRE_GRASS, "Ogre Grass");
        add(MoCBlocks.OGRE_LEAVES, "Ogre Leaves");
        add(MoCBlocks.OGRE_LOG, "Ogre Log");
        add(MoCBlocks.OGRE_STONE, "Ogre Stone");
        add(MoCBlocks.OGRE_WOODPLANK, "Ogre Wooden Planks");
        add(MoCBlocks.OGRE_TALLGRASS, "Ogre Tallgrass");

        //TODO: Finish English translations

    }
}
