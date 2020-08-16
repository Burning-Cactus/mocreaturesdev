package drzhark.mocreatures.data.datagen.loot_tables;

import drzhark.mocreatures.registry.MoCBlocks;
import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.block.Block;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.loot.LootTable;

import javax.annotation.Nonnull;
import java.util.HashSet;
import java.util.Set;

public class MoCBlockLootTables extends BlockLootTables {
    private final Set<Block> blocks = new HashSet<>();

    @Override
    protected void registerLootTable(@Nonnull Block blockIn, @Nonnull LootTable.Builder table) {
        super.registerLootTable(blockIn, table);
        this.blocks.add(blockIn);
    }

    @Override
    protected void addTables() {
        registerDropSelfLootTable(MoCBlocks.WYVERN_DIRT);
        registerDropSelfLootTable(MoCBlocks.WYVERN_GRASS);
        registerDropSelfLootTable(MoCBlocks.WYVERN_LOG);
        registerDropSelfLootTable(MoCBlocks.WYVERN_STONE);
        registerDropSelfLootTable(MoCBlocks.WYVERN_PLANKS);
        registerDropSelfLootTable(MoCBlocks.OGRE_DIRT);
        registerDropSelfLootTable(MoCBlocks.OGRE_GRASS);
        registerDropSelfLootTable(MoCBlocks.OGRE_LOG);
        registerDropSelfLootTable(MoCBlocks.OGRE_STONE);
        registerDropSelfLootTable(MoCBlocks.OGRE_PLANKS);
    }

    @Override
    @MethodsReturnNonnullByDefault
    protected Iterable<Block> getKnownBlocks() {
        return blocks;
    }
}
