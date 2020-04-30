package drzhark.mocreatures.data.datagen;

import drzhark.mocreatures.data.datagen.lang.MoCEnUSLangProvider;
import drzhark.mocreatures.data.datagen.recipes.MoCSmeltingRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataGatherer {

    private static final Logger LOGGER = LogManager.getLogger();

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new MoCSmeltingRecipeProvider(generator));
        LOGGER.info("Smelting recipes added!");
        generator.addProvider(new MoCEnUSLangProvider(generator));
        LOGGER.info("Lang files added!");
    }
}
