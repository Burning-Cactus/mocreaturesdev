package drzhark.mocreatures.dimension.gen;

import net.minecraft.world.gen.GenerationSettings;

public class WyvernLairGenSettings extends GenerationSettings {
    public final boolean generateFalls;
    public final int grassPerChunk;
    public final int flowersPerChunk;
    public final int mushroomsPerChunk;
    public final int treesPerChunk;

    public WyvernLairGenSettings() {
        this.generateFalls = true;
        this.grassPerChunk = 1;
        this.flowersPerChunk = -999;
        this.mushroomsPerChunk = 20;
        this.treesPerChunk = 4;
    }

}
