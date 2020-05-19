package drzhark.mocreatures.dimension;

import drzhark.mocreatures.dimension.feature.MoCBigTreeFeature;
import drzhark.mocreatures.dimension.feature.MoCFeatures;
import drzhark.mocreatures.dimension.feature.WorldGenTower;
import drzhark.mocreatures.init.MoCBlocks;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.gen.feature.ShrubFeature;
import net.minecraft.world.gen.placement.IPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Random;

public class WyvernLairBiome extends Biome {

    private MoCBigTreeFeature wyvernGenBigTree;
    private ShrubFeature worldGenShrub;

    public WyvernLairBiome(Biome.Builder biomeProperties) {
        super(biomeProperties
                .surfaceBuilder(SurfaceBuilder.DEFAULT, SurfaceBuilder.END_STONE_CONFIG)
                .precipitation(RainType.NONE)
                .depth(0.1F)
                .scale(0.2F)
                .waterColor(4159204)
                .waterFogColor(329011));
        this.getSpawns(EntityClassification.CREATURE).clear();
        this.getSpawns(EntityClassification.MONSTER).clear();
        this.getSpawns(EntityClassification.WATER_CREATURE).clear();
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(MoCEntities.BUNNY, 6, 2, 3));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(MoCEntities.DRAGONFLY, 8, 2, 3));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(MoCEntities.SNAKE, 6, 1, 2));
        this.addSpawn(EntityClassification.CREATURE, new SpawnListEntry(MoCEntities.WYVERN, 10, 1, 4));
        this.addFeature(GenerationStage.Decoration.SURFACE_STRUCTURES, MoCFeatures.WYVERN_TOWER.withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG).withPlacement(Placement.NOPE.configure(IPlacementConfig.NO_PLACEMENT_CONFIG)));
//        this.wyvernGenBigTree = new MoCBigTreeFeature(false, MoCBlocks.WYVERN_LOG.getDefaultState(), MoCBlocks.WYVERN_LEAVES.getDefaultState(), 2, 30, 10);
//        this.worldGenShrub = new ShrubFeature(Blocks.DIRT.getDefaultState(), Blocks.AIR.getDefaultState());
    }
//
//    /**
//     * Gets a WorldGen appropriate for this biome.
//     */
//    @Override
//    public AbstractTreeFeature getRandomTreeFeature(Random par1Random) {
//        if (par1Random.nextInt(10) == 0) {
//            return this.wyvernGenBigTree;
//        } else {
//            return this.worldGenShrub;
//        }
//    }
//
//    /**
//     * Gets a WorldGen appropriate for this biome.
//     */
//    @Override
//    public WorldGenerator getRandomWorldGenForGrass(Random par1Random) {
//        return new WorldGenWyvernGrass(MoCBlocks.WYVERN_TALLGRASS.getDefaultState());
//    }
//
//    @Override
//    public void decorate(World par1World, Random par2Random, BlockPos pos) {
//        super.decorate(par1World, par2Random, pos);
//
//        VinesFeature var5 = new VinesFeature();
//
//        for (int var6 = 0; var6 < 50; ++var6) {
//            int var7 = par2Random.nextInt(16) + 8;
//            byte var8 = 64;
//            int var9 = par2Random.nextInt(16) + 8;
//            var5.generate(par1World, par2Random, pos.add(var7, var8, var9));
//        }
//    }

    @OnlyIn(Dist.CLIENT)
    public int getSkyColor() {
        return 0;
    }
}
