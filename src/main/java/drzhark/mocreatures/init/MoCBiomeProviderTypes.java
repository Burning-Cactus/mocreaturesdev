package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.dimension.WyvernLairBiomeProvider;
import drzhark.mocreatures.dimension.WyvernLairBiomeProviderSettings;
import net.minecraft.world.biome.provider.BiomeProviderType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(MoCConstants.MOD_ID)
@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCBiomeProviderTypes {
    public static final BiomeProviderType<WyvernLairBiomeProviderSettings, WyvernLairBiomeProvider> WYVERN_BIOME_PROVIDER = new BiomeProviderType<>(WyvernLairBiomeProvider::new, WyvernLairBiomeProviderSettings::new);

    public static void registerBiomeProviders(RegistryEvent.Register<BiomeProviderType<?, ?>> event) {
        event.getRegistry().registerAll(
                WYVERN_BIOME_PROVIDER.setRegistryName("wyvern_biome_provider")
        );
    }
}
