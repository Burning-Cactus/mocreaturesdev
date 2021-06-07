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
    protected void add(@Nonnull Block blockIn, @Nonnull LootTable.Builder table) {
        super.add(blockIn, table);
        this.blocks.add(blockIn);
    }

    @Override
    protected void addTables() {
        dropSelf(MoCBlocks.WYVERN_DIRT);
        dropSelf(MoCBlocks.WYVERN_GRASS);
        dropSelf(MoCBlocks.WYVERN_LOG);
        dropSelf(MoCBlocks.WYVERN_STONE);
        dropSelf(MoCBlocks.WYVERN_PLANKS);
        dropSelf(MoCBlocks.OGRE_DIRT);
        dropSelf(MoCBlocks.OGRE_GRASS);
        dropSelf(MoCBlocks.OGRE_LOG);
        dropSelf(MoCBlocks.OGRE_STONE);
        dropSelf(MoCBlocks.OGRE_PLANKS);
    }

    @Override
    @MethodsReturnNonnullByDefault
    protected Iterable<Block> getKnownBlocks() {
        return blocks;
    }
}
