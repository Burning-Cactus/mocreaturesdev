package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder(MoCConstants.MOD_ID)
@Deprecated //Moving to data generators for json recipes
public class MoCRecipes {

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerRecipes(final RegistryEvent.Register<IRecipe> event) {



            GameRegistry.addSmelting(Items.EGG, new ItemStack(MoCItems.omelet, 1), 0F);
        }
    }
}
