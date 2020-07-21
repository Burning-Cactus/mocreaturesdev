package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.dimension.WyvernLairDimension;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.ModDimension;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.RegisterDimensionsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.function.BiFunction;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCDimensions {


    public static final ModDimension WYVERN_LAIR = new ModDimension() {
        @Override
        public BiFunction<World, DimensionType, ? extends Dimension> getFactory() {
            return WyvernLairDimension::new;
        }
    };

//    public static final ModDimension OGRE_LAIR = null;

    public static DimensionType wyvernLair() {
        return DimensionType.byName(new ResourceLocation(MoCConstants.MOD_ID + "wyvern_lair"));
    }

//    public static DimensionType ogreLair() {
//        return null;
//    }

    public static final ResourceLocation WYVERN_LAIR_ID = new ResourceLocation(MoCConstants.MOD_ID + "wyvern_lair");
//    public static final ResourceLocation OGRE_LAIR_ID = new ResourceLocation(MoCConstants.MOD_ID + "ogre_lair");

    @SubscribeEvent
    public static void registerModDimensions(RegistryEvent.Register<ModDimension> event) {
        IForgeRegistry register = event.getRegistry();
        register.register(WYVERN_LAIR.setRegistryName(WYVERN_LAIR_ID));
    }

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerDimensionTypes(RegisterDimensionsEvent event) {
            if(wyvernLair() == null)
                DimensionManager.registerDimension(WYVERN_LAIR_ID, WYVERN_LAIR, null, false);
        }

    }

}
