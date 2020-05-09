package drzhark.mocreatures.dimension;

import drzhark.mocreatures.dimension.feature.MoCBigTreeFeature;
import drzhark.mocreatures.dimension.feature.WorldGenWyvernGrass;
import drzhark.mocreatures.init.MoCBlocks;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.ShrubFeature;
import net.minecraft.world.gen.feature.VinesFeature;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class WyvernLairBiome extends Biome {

    private MoCBigTreeFeature wyvernGenBigTree;
    private ShrubFeature worldGenShrub;

    public WyvernLairBiome(Biome.Builder biomeProperties) {
        super(biomeProperties
                .surfaceBuilder()
                .setHeightVariation(1.5F));
        this.getSpawns(EntityClassification.CREATURE).clear();
        this.getSpawns(EntityClassification.MONSTER).clear();
        this.getSpawns(EntityClassification.WATER_CREATURE).clear();
        this.getSpawns(EntityClassification.CREATURE).add(new SpawnListEntry(MoCEntities.BUNNY, 6, 2, 3));
        this.getSpawns(EntityClassification.CREATURE).add(new SpawnListEntry(MoCEntities.DRAGONFLY, 8, 2, 3));
        this.getSpawns(EntityClassification.CREATURE).add(new SpawnListEntry(MoCEntities.SNAKE, 6, 1, 2));
        this.getSpawns(EntityClassification.CREATURE).add(new SpawnListEntry(MoCEntities.WYVERN, 10, 1, 4));
        this.topBlock = MoCBlocks.WYVERN_GRASS.getDefaultState();
        this. = MoCBlocks.WYVERN_DIRT;
        this.wyvernGenBigTree = new MoCBigTreeFeature(false, MoCBlocks.WYVERN_LOG.getDefaultState(), MoCBlocks.WYVERN_LEAVES.getDefaultState(), 2, 30, 10);
        this.worldGenShrub = new ShrubFeature(Blocks.DIRT.getDefaultState(), Blocks.AIR.getDefaultState());
        this.decorator = new BiomeWyvernDecorator();
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    @Override
    public AbstractTreeFeature getRandomTreeFeature(Random par1Random) {
        if (par1Random.nextInt(10) == 0) {
            return this.wyvernGenBigTree;
        } else {
            return this.worldGenShrub;
        }
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random par1Random) {
        return new WorldGenWyvernGrass(MoCBlocks.WYVERN_TALLGRASS.getDefaultState());
    }

    @Override
    public void decorate(World par1World, Random par2Random, BlockPos pos) {
        super.decorate(par1World, par2Random, pos);

        VinesFeature var5 = new VinesFeature();

        for (int var6 = 0; var6 < 50; ++var6) {
            int var7 = par2Random.nextInt(16) + 8;
            byte var8 = 64;
            int var9 = par2Random.nextInt(16) + 8;
            var5.generate(par1World, par2Random, pos.add(var7, var8, var9));
        }
    }

    @OnlyIn(Dist.CLIENT)
    public int getSkyColor() {
        return 0;
    }
}
