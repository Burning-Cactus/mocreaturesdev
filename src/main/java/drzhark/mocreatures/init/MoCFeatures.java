package drzhark.mocreatures.init;


import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.dimension.feature.MoCTowerFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(MoCConstants.MOD_ID)
public class MoCFeatures {

    public static final Feature<NoFeatureConfig> WYVERN_TOWER = new MoCTowerFeature(NoFeatureConfig::deserialize);

    public static void registerFeatures(RegistryEvent.Register<Feature<?>> event) {
        event.getRegistry().registerAll(
                WYVERN_TOWER.setRegistryName("wyvern_tower")
        );
    }

}
