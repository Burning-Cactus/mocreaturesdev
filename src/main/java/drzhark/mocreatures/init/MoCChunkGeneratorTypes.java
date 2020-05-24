package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.dimension.gen.WyvernLairChunkGenerator;
import drzhark.mocreatures.dimension.gen.WyvernLairGenSettings;
import net.minecraft.world.gen.ChunkGeneratorType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(MoCConstants.MOD_ID)
@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCChunkGeneratorTypes {

    public static final ChunkGeneratorType<WyvernLairGenSettings, WyvernLairChunkGenerator> BASIC_WYVERN_LAIR = new ChunkGeneratorType<>(WyvernLairChunkGenerator::new, true, WyvernLairGenSettings::new);


    @SubscribeEvent
    public static void registerChunkGenerators(RegistryEvent.Register<ChunkGeneratorType<?, ?>> event) {
        event.getRegistry().registerAll(
                BASIC_WYVERN_LAIR.setRegistryName("basic_wyvern_lair")
        );
    }

}
