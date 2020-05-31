package drzhark.mocreatures.dimension.gen;

import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.NoiseChunkGenerator;

import java.util.Random;

public class WyvernLairChunkGenerator extends NoiseChunkGenerator<WyvernLairGenSettings>
{
    /** RNG. */
    private final Random rand;

    public WyvernLairChunkGenerator(IWorld worldIn, BiomeProvider biomeProvider, WyvernLairGenSettings settings) {
        super(worldIn, biomeProvider, 8, 4, 128, settings, true);
        this.rand = new Random(worldIn.getSeed());
    }

    @Override
    protected double[] getBiomeNoiseColumn(int noiseX, int noiseZ) {
        return new double[0];
    }

    @Override
    protected double func_222545_a(double p_222545_1_, double p_222545_3_, int p_222545_5_) {
        return 0;
    }

    @Override
    protected void fillNoiseColumn(double[] noiseColumn, int noiseX, int noiseZ) {

    }

    @Override
    public int getGroundHeight() {
        return 50;
    }
}