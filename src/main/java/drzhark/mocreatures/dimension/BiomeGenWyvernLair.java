package drzhark.mocreatures.dimension;

import drzhark.mocreatures.entity.ambient.MoCEntityDragonfly;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import drzhark.mocreatures.entity.passive.MoCEntitySnake;
import drzhark.mocreatures.entity.passive.MoCEntityWyvern;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.ShrubFeature;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenShrub;
import net.minecraft.world.gen.feature.WorldGenVines;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BiomeGenWyvernLair extends Biome {

    private MoCWorldGenBigTree wyvernGenBigTree;
    private WorldGenShrub worldGenShrub;

    public BiomeGenWyvernLair(Biome.Builder biomeProperties) {
        super(biomeProperties);
        this.getSpawns(EntityClassification.CREATURE).clear();
        this.getSpawns(EntityClassification.MONSTER).clear();
        this.getSpawns(EntityClassification.WATER_CREATURE).clear();
        this.getSpawns(EntityClassification.CREATURE).add(new SpawnListEntry(MoCEntityBunny.class, 6, 2, 3));
        this.getSpawns(EntityClassification.CREATURE).add(new SpawnListEntry(MoCEntityDragonfly.class, 8, 2, 3));
        this.getSpawns(EntityClassification.CREATURE).add(new SpawnListEntry(MoCEntitySnake.class, 6, 1, 2));
        this.getSpawns(EntityClassification.CREATURE).add(new SpawnListEntry(MoCEntityWyvern.class, 10, 1, 4));
        this.topBlock = MoCBlocks.WYVERN_GRASS.get().getDefaultState();
        this. = MoCBlocks.WYVERN_DIRT.get();
        this.wyvernGenBigTree = new MoCWorldGenBigTree(false, MoCBlocks.mocLog.getDefaultState(), MoCBlocks.mocLeaf.getDefaultState(), 2, 30, 10);
        this.worldGenShrub = new ShrubFeature(Blocks.DIRT.getDefaultState(), Blocks.AIR.getDefaultState());
        this.decorator = new BiomeWyvernDecorator();
    }

    /**
     * Gets a WorldGen appropriate for this biome.
     */
    @Override
    public WorldGenAbstractTree getRandomTreeFeature(Random par1Random) {
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
        return new WorldGenWyvernGrass(MoCBlocks.WYVERN_TALLGRASS.get().getDefaultState());
    }

    @Override
    public void decorate(World par1World, Random par2Random, BlockPos pos) {
        super.decorate(par1World, par2Random, pos);

        WorldGenVines var5 = new WorldGenVines();

        for (int var6 = 0; var6 < 50; ++var6) {
            int var7 = par2Random.nextInt(16) + 8;
            byte var8 = 64;
            int var9 = par2Random.nextInt(16) + 8;
            var5.generate(par1World, par2Random, pos.add(var7, var8, var9));
        }
    }
}
