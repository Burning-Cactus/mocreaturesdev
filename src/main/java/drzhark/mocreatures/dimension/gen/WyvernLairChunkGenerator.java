package drzhark.mocreatures.dimension.gen;

import drzhark.mocreatures.dimension.feature.MoCWorldGenPortal;
import drzhark.mocreatures.dimension.feature.MoCTowerFeature;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.BlockFalling;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.NoiseGeneratorOctaves;
import net.minecraft.world.gen.NoiseGeneratorSimplex;
import net.minecraft.world.gen.feature.EndIslandFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.WorldGenEndIsland;
import net.minecraft.world.gen.feature.WorldGenLakes;

import java.util.List;
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
        return 0;
    }
}