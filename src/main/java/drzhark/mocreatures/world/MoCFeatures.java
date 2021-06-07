package drzhark.mocreatures.world;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;

public class MoCFeatures {
    public static final ConfiguredFeature<?, ?> PATCH_WYVERN_GRASS = Registry.register(WorldGenRegistries.CONFIGURED_FEATURE,
            new ResourceLocation(MoCConstants.MOD_ID, "patch_grass_wyvern"),
            Feature.RANDOM_PATCH.configured(Features.Configs.DEFAULT_GRASS_CONFIG).decorated(Features.Placements.HEIGHTMAP_DOUBLE_SQUARE));
}
