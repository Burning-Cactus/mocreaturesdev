package drzhark.mocreatures.data.datagen.loot_tables;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootParameterSet;
import net.minecraft.world.storage.loot.LootParameterSets;
import net.minecraft.world.storage.loot.LootTable;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class MoCLootTableProvider extends LootTableProvider {
    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> lootTables =
            ImmutableList.of(Pair.of(MoCBlockLootTables::new, LootParameterSets.BLOCK), Pair.of(MoCEntityLootTables::new, LootParameterSets.ENTITY));
    public MoCLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }
}
