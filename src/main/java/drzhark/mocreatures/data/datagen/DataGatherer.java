package drzhark.mocreatures.data.datagen;

import drzhark.mocreatures.data.datagen.recipes.MoCSmeltingRecipeProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class DataGatherer {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        event.getGenerator().addProvider(new MoCSmeltingRecipeProvider(event.getGenerator()));
    }
}
