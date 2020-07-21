package drzhark.mocreatures.data.datagen.loot_tables;

import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.data.loot.BlockLootTables;

public class MoCBlockLootTables extends BlockLootTables {
    @Override
    protected void addTables() {
        registerDropSelfLootTable(MoCBlocks.WYVERN_DIRT);
        registerDropSelfLootTable(MoCBlocks.WYVERN_GRASS);
        registerDropSelfLootTable(MoCBlocks.WYVERN_LOG);
        registerDropSelfLootTable(MoCBlocks.WYVERN_STONE);
        registerDropSelfLootTable(MoCBlocks.WYVERN_WOODPLANK);
        registerDropSelfLootTable(MoCBlocks.OGRE_DIRT);
        registerDropSelfLootTable(MoCBlocks.OGRE_GRASS);
        registerDropSelfLootTable(MoCBlocks.OGRE_LOG);
        registerDropSelfLootTable(MoCBlocks.OGRE_STONE);
        registerDropSelfLootTable(MoCBlocks.OGRE_WOODPLANK);
    }
}
