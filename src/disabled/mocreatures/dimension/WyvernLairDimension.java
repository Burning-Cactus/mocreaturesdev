package drzhark.mocreatures.dimension;

import drzhark.mocreatures.client.renderer.MoCSkyRenderer;
import drzhark.mocreatures.dimension.gen.WyvernLairGenSettings;
import drzhark.mocreatures.registry.MoCBiomeProviderTypes;
import drzhark.mocreatures.registry.MoCBlocks;
import drzhark.mocreatures.registry.MoCChunkGeneratorTypes;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;


/**
 * This class will resemble the EndDimension class in several ways.
 */
public class WyvernLairDimension extends Dimension {
    public WyvernLairDimension(World worldIn, DimensionType type) {
        super(worldIn, type, 1);
        setCustomSky();
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        WyvernLairGenSettings settings = MoCChunkGeneratorTypes.BASIC_WYVERN_LAIR.createSettings();
        settings.setDefaultBlock(MoCBlocks.WYVERN_DIRT.getDefaultState());
        settings.setDefaultFluid(Blocks.WATER.getDefaultState());
        return MoCChunkGeneratorTypes.BASIC_WYVERN_LAIR.create(this.world, MoCBiomeProviderTypes.WYVERN_BIOME_PROVIDER.create(MoCBiomeProviderTypes.WYVERN_BIOME_PROVIDER.createSettings(this.world.getWorldInfo())), settings);
    }

    private void setCustomSky() {
        if (!this.world.isRemote) {
            return;
        }
        setSkyRenderer(new MoCSkyRenderer());
    }

    /**
     * Gets the hard-coded portal location to use when entering this dimension.
     */
    @Override
    public BlockPos getSpawnCoordinate() {
        return new BlockPos(0, 70, 0);
    }

    @Nullable
    @Override
    public BlockPos findSpawn(ChunkPos chunkPosIn, boolean checkValid) {
        return null;
    }

    @Nullable
    @Override
    public BlockPos findSpawn(int posX, int posZ, boolean checkValid) {
        return this.findSpawn(new ChunkPos(posX >> 4, posZ >> 4), checkValid);
    }

    @OnlyIn(Dist.CLIENT)
    /**
     * Returns array with sunrise/sunset colors
     */
    @Override
    public float[] calcSunriseSunsetColors(float par1, float par2) {
        return null;
    }

    @Override
    public float calculateCelestialAngle(long worldTime, float partialTicks) {
        return 0;
    }

    @Override
    /**
     * Returns 'true' if in the "main surface world", but 'false' if in the
     * Nether or End dimensions.
     */
    public boolean isSurfaceWorld() {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    /**
     * the y level at which clouds are rendered.
     */
    public float getCloudHeight() {
        return 76.0F;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean isSkyColored() {
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    /**
     * Return Vec3D with biome specific fog color
     */
    public Vec3d getFogColor(float celestialAngle, float partialTicks) {
        float var4 = MathHelper.cos(celestialAngle * (float) Math.PI * 2.0F) * 2.0F + 0.5F;

        if (var4 < 0.0F) {
            var4 = 0.0F;
        }

        if (var4 > 1.0F) {
            var4 = 1.0F;
        }

        float var5 = 0 / 255.0F;
        float var6 = 98 / 255.0F;
        float var7 = 73 / 255.0F;

        var5 *= var4 * 0.0F + 0.15F;
        var6 *= var4 * 0.0F + 0.15F;
        var7 *= var4 * 0.0F + 0.15F;
        return new Vec3d(var5, var6, var7);
    }

    @Override
    /**
     * True if the player can respawn in this dimension (true = overworld, false
     * = nether).
     */
    public boolean canRespawnHere() {
        return false;
    }

    @Override
    public double getMovementFactor() {
        return 1.0;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    /**
     * Returns true if the given X,Z coordinate should show environmental fog.
     */
    public boolean doesXZShowFog(int x, int z) {
        return true;
    }

    public String getSunTexture() {
        return "/mocreatures.twinsuns.png";
    }
}
