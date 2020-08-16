package drzhark.mocreatures.dimension;

import com.google.common.collect.ImmutableSet;
import drzhark.mocreatures.registry.MoCBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;

import java.util.Set;

public class WyvernLairBiomeProvider extends BiomeProvider {

    /** The biome generator object. */
    private Biome biomeGenerator;

    private static final Set<Biome> biomes = ImmutableSet.of(MoCBiomes.WYVERNBIOME);

    public WyvernLairBiomeProvider(WyvernLairBiomeProviderSettings settings) {
        super(biomes);
        biomeGenerator = MoCBiomes.WYVERNBIOME;
    }

    @Override
    public Biome getNoiseBiome(int i, int i1, int i2) {
        return this.biomeGenerator;
    }

    /**
     * Returns the Biome related to the x, z position on the world.
     */
//    public Biome getBiomeGenAt(int par1, int par2) {
//        return this.biomeGenerator;
//    }

    /**
     * Returns an array of biomes for the location input.
     */
//    @Override
//    public Biome[] getBiomesForGeneration(Biome[] biomes, int xStart, int zStart, int xSize, int zSize) {
//        return getBiomes(biomes, xStart, zStart, xSize, zSize, true);
//    }

    /**
     * Returns a list of temperatures to use for the specified blocks. Args:
     * listToReuse, x, y, width, length
     */
//    public float[] getTemperatures(float[] par1ArrayOfFloat, int xStart, int zStart, int xSize, int zSize) {
//        if (par1ArrayOfFloat == null || par1ArrayOfFloat.length < xSize * zSize) {
//            par1ArrayOfFloat = new float[xSize * zSize];
//        }
//
//        Arrays.fill(par1ArrayOfFloat, 0, xSize * zSize, this.hellTemperature);
//        return par1ArrayOfFloat;
//    }

    /**
     * Returns biomes to use for the blocks and loads the other data like
     * temperature and humidity onto the WorldChunkManager Args: oldBiomeList,
     * x, z, width, depth
     */
//    @Override
//    public Biome[] getBiomes(@Nullable Biome[] biomes, int xStart, int zStart, int xSize, int zSize) {
//        return getBiomes(biomes, xStart, zStart, xSize, zSize, true);
//    }
//
//    /**
//     * Return a list of biomes for the specified blocks. Args: listToReuse, x,
//     * y, width, length, cacheFlag (if false, don't check biomeCache to avoid
//     * infinite loop in BiomeCacheBlock)
//     */
//    @Override
//    public Biome[] getBiomes(@Nullable Biome[] biomes, int xStart, int zStart, int xSize, int zSize, boolean par6) {
//        if (biomes == null || biomes.length < xSize * zSize) {
//            biomes = new Biome[xSize * zSize];
//        }
//
//        Arrays.fill(biomes, 0, xSize * zSize, this.biomeGenerator);
//        return biomes;
//    }

    /**
     * findBiomePosition()
     * Finds a valid position within a range, that is in one of the listed
     * biomes. Searches {par1,par2} +-par3 blocks. Strongly favors positive y
     * positions.
     */
//    @Override
//    public BlockPos func_225531_a_(int x, int z, int range, List<Biome> biomes, Random random) {
//        return biomes.contains(this.biomeGenerator) ? new BlockPos(x - range + random.nextInt(range * 2 + 1), 0, z - range
//                + random.nextInt(range * 2 + 1)) : null;
//    }

}
