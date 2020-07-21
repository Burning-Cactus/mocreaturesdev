package drzhark.mocreatures.dimension;

import net.minecraft.world.WorldType;
import net.minecraft.world.biome.provider.IBiomeProviderSettings;
import net.minecraft.world.storage.WorldInfo;

public class WyvernLairBiomeProviderSettings implements IBiomeProviderSettings {
    private final long seed;

    public WyvernLairBiomeProviderSettings(WorldInfo worldInfo) {
        seed = worldInfo.getSeed();
    }

    public long getSeed() {
        return this.seed;
    }

}
