package drzhark.mocreatures.data.datagen.models;


import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.registry.MoCBlocks;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class MoCBlockStateProvider extends BlockStateProvider {
    public MoCBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, MoCConstants.MOD_ID, exFileHelper);
    }

    @Override
    @SuppressWarnings("all")
    protected void registerStatesAndModels() {
        this.simpleBlock(MoCBlocks.WYVERN_DIRT);
        this.simpleBlock(MoCBlocks.WYVERN_PLANKS);
        this.simpleBlock(MoCBlocks.WYVERN_STONE);
        this.simpleBlock(MoCBlocks.WYVERN_LEAVES);
        this.simpleBlock(MoCBlocks.OGRE_DIRT);
        this.simpleBlock(MoCBlocks.OGRE_PLANKS);
        this.simpleBlock(MoCBlocks.OGRE_STONE);
        this.simpleBlock(MoCBlocks.OGRE_LEAVES);
        this.logBlock((RotatedPillarBlock) MoCBlocks.WYVERN_LOG);
        this.logBlock((RotatedPillarBlock) MoCBlocks.OGRE_LOG);

        this.simpleBlockItem(MoCBlocks.WYVERN_DIRT, models().getExistingFile(MoCBlocks.WYVERN_DIRT.getRegistryName()));
        this.simpleBlockItem(MoCBlocks.WYVERN_PLANKS, models().getExistingFile(MoCBlocks.WYVERN_PLANKS.getRegistryName()));
        this.simpleBlockItem(MoCBlocks.WYVERN_STONE, models().getExistingFile(MoCBlocks.WYVERN_STONE.getRegistryName()));
        this.simpleBlockItem(MoCBlocks.WYVERN_LEAVES, models().getExistingFile(MoCBlocks.WYVERN_LEAVES.getRegistryName()));
        this.simpleBlockItem(MoCBlocks.OGRE_DIRT, models().getExistingFile(MoCBlocks.OGRE_DIRT.getRegistryName()));
        this.simpleBlockItem(MoCBlocks.OGRE_PLANKS, models().getExistingFile(MoCBlocks.OGRE_PLANKS.getRegistryName()));
        this.simpleBlockItem(MoCBlocks.OGRE_STONE, models().getExistingFile(MoCBlocks.OGRE_STONE.getRegistryName()));
        this.simpleBlockItem(MoCBlocks.OGRE_LEAVES, models().getExistingFile(MoCBlocks.OGRE_LEAVES.getRegistryName()));
        this.simpleBlockItem(MoCBlocks.WYVERN_LOG, models().getExistingFile(MoCBlocks.WYVERN_LOG.getRegistryName()));
        this.simpleBlockItem(MoCBlocks.OGRE_LOG, models().getExistingFile(MoCBlocks.OGRE_LOG.getRegistryName()));
    }
}
