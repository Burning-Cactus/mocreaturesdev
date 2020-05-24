package drzhark.mocreatures.dimension;

import drzhark.mocreatures.dimension.gen.WyvernLairChunkGenerator;
import drzhark.mocreatures.dimension.gen.WyvernLairGenSettings;
import drzhark.mocreatures.init.MoCBiomeProviderTypes;
import drzhark.mocreatures.init.MoCBlocks;
import drzhark.mocreatures.init.MoCChunkGeneratorTypes;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;

import javax.annotation.Nullable;
import java.util.Random;


/**
 * This class will resemble the EndDimension class in several ways.
 */
public class WyvernLairDimension extends Dimension {
    public WyvernLairDimension(World worldIn, DimensionType type) {
        super(worldIn, type, 1);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        WyvernLairGenSettings settings = MoCChunkGeneratorTypes.BASIC_WYVERN_LAIR.createSettings();
        settings.setDefaultBlock(MoCBlocks.WYVERN_DIRT.getDefaultState());
        settings.setDefaultFluid(Blocks.WATER.getDefaultState());
        return MoCChunkGeneratorTypes.BASIC_WYVERN_LAIR.create(this.world, MoCBiomeProviderTypes.WYVERN_BIOME_PROVIDER.create(MoCBiomeProviderTypes.WYVERN_BIOME_PROVIDER.createSettings(this.world.getWorldInfo())), settings);
    }

    @Nullable
    @Override
    public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
//        Random random = new Random(this.world.getSeed());
        return null;
    }

    @Nullable
    @Override
    public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
        return this.findSpawn(new ChunkPos(posX >> 4, posZ >> 4), checkValid);
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0;
    }

    @Override
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        return null;
    }

    @Override
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public boolean doesXZShowFog(int x, int z) {
        return false;
    }
}
