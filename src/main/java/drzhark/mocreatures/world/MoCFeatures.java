package drzhark.mocreatures.world;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.Features;

public class MoCFeatures {
    public static final ConfiguredFeature<?, ?> PATCH_WYVERN_GRASS = Registry.register(WorldGenRegistries.field_243653_e,
            new ResourceLocation(MoCConstants.MOD_ID, "patch_grass_wyvern"),
            Feature.RANDOM_PATCH.withConfiguration(Features.Configs.field_243977_a).withPlacement(Features.Placements.field_244002_m));
}
