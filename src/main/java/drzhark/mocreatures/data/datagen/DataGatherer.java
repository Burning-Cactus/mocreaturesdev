package drzhark.mocreatures.data.datagen;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.data.datagen.lang.MoCEnUSLangProvider;
import drzhark.mocreatures.data.datagen.loot_tables.MoCLootTableProvider;
import drzhark.mocreatures.data.datagen.models.MoCBlockStateProvider;
import drzhark.mocreatures.data.datagen.models.MoCItemModelProvider;
import drzhark.mocreatures.data.datagen.recipes.MoCCraftingRecipeProvider;
import drzhark.mocreatures.data.datagen.recipes.MoCSmeltingRecipeProvider;
import drzhark.mocreatures.data.datagen.tags.MoCBlockTagsProvider;
import drzhark.mocreatures.data.datagen.tags.MoCItemTagsProvider;
import drzhark.mocreatures.item.MoCItem;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGatherer {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
//        generator.addProvider(new MoCSmeltingRecipeProvider(generator));
        LOGGER.info("Smelting recipes added!");
        generator.addProvider(new MoCEnUSLangProvider(generator));
        LOGGER.info("Lang files added!");
        MoCBlockTagsProvider blockTags = new MoCBlockTagsProvider(generator);
        generator.addProvider(blockTags);
        generator.addProvider(new MoCItemTagsProvider(generator, blockTags));
        generator.addProvider(new MoCLootTableProvider(generator));
        generator.addProvider(new MoCBlockStateProvider(generator, helper));
        generator.addProvider(new MoCItemModelProvider(generator, helper));
    }
}
