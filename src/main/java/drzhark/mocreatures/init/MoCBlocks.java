package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCItemGroup;
import drzhark.mocreatures.block.MoCBlock;
import drzhark.mocreatures.block.MoCBlockGrass;
import drzhark.mocreatures.block.MoCBlockLeaf;
import drzhark.mocreatures.block.MoCBlockTallGrass;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(MoCConstants.MOD_ID)
public class MoCBlocks {

    //Wyvern blocks
    public static final Block WYVERN_STONE = null;
    public static final Block WYVERN_GRASS = null;
    public static final Block WYVERN_DIRT = null;
    //Non terrain generator blocks
    public static final Block WYVERN_LEAVES = null;
    public static final Block WYVERN_LOG = null;
    public static final Block WYVERN_TALLGRASS = null;
    public static final Block WYVERN_PLANKS = null;
    //Ogre blocks
    public static final Block OGRE_STONE = null;
    public static final Block OGRE_GRASS = null;
    public static final Block OGRE_DIRT = null;
    //Non terrain generator blocks
    public static final Block OGRE_LEAVES = null;
    public static final Block OGRE_LOG = null;
    public static final Block OGRE_TALLGRASS = null;
    public static final Block OGRE_PLANKS = null;

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {

        /**
         * Register this mod's {@link Block}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerBlocks(final RegistryEvent.Register<Block> event) {
            final IForgeRegistry<Block> registry = event.getRegistry();

            final Block[] blocks = {
                    new MoCBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 10.0F).harvestTool(ToolType.PICKAXE).harvestLevel(1).tickRandomly()).setRegistryName("wyvern_stone"),
                    new MoCBlockGrass(Block.Properties.create(Material.PLANTS)).setRegistryName("wyvern_grass"),
                    new MoCBlock(Block.Properties.create(Material.EARTH).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.6F).tickRandomly()).setRegistryName("wyvern_dirt"),
                    new MoCBlockLeaf(Block.Properties.create(Material.LEAVES)).setRegistryName("wyvern_leaves"),
                    new MoCBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F).harvestTool(ToolType.AXE).harvestLevel(0)).setRegistryName("wyvern_log"),
                    new MoCBlockTallGrass(Block.Properties.create(Material.TALL_PLANTS)).setRegistryName("wyvern_tallgrass"),
                    new MoCBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)).setRegistryName("wyvern_planks"),
                    new MoCBlock(Block.Properties.create(Material.ROCK).sound(SoundType.STONE).hardnessAndResistance(1.5F, 10.0F).harvestTool(ToolType.PICKAXE).harvestLevel(1).tickRandomly()).setRegistryName("ogre_stone"),
                    new MoCBlockGrass(Block.Properties.create(Material.PLANTS)).setRegistryName("ogre_grass"),
                    new MoCBlock(Block.Properties.create(Material.EARTH).sound(SoundType.GROUND).harvestTool(ToolType.SHOVEL).harvestLevel(0).hardnessAndResistance(0.6F).tickRandomly()).setRegistryName("ogre_dirt"),
                    new MoCBlockLeaf(Block.Properties.create(Material.LEAVES)).setRegistryName("ogre_leaves"),
                    new MoCBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD).hardnessAndResistance(2.0F).harvestTool(ToolType.AXE).harvestLevel(0)).setRegistryName("ogre_log"),
                    new MoCBlockTallGrass(Block.Properties.create(Material.TALL_PLANTS)).setRegistryName("ogre_tallgrass"),
                    new MoCBlock(Block.Properties.create(Material.WOOD).sound(SoundType.WOOD)).setRegistryName("ogre_planks")
            };
            registry.registerAll(blocks);
        }


        /**
         * Register this mod's {@link BlockItem}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerItemBlocks(final RegistryEvent.Register<Item> event) {
            final BlockItem[] items = {
                    new BlockItem(WYVERN_STONE, new Item.Properties().group(MoCItemGroup.TABMOC)),
                    new BlockItem(WYVERN_GRASS, new Item.Properties().group(MoCItemGroup.TABMOC)),
                    new BlockItem(WYVERN_DIRT, new Item.Properties().group(MoCItemGroup.TABMOC)),
                    new BlockItem(WYVERN_LEAVES, new Item.Properties().group(MoCItemGroup.TABMOC)),
                    new BlockItem(WYVERN_LOG, new Item.Properties().group(MoCItemGroup.TABMOC)),
                    new BlockItem(WYVERN_TALLGRASS, new Item.Properties().group(MoCItemGroup.TABMOC)),
                    new BlockItem(WYVERN_PLANKS, new Item.Properties().group(MoCItemGroup.TABMOC)),
                    new BlockItem(OGRE_STONE, new Item.Properties().group(MoCItemGroup.TABMOC)),
                    new BlockItem(OGRE_GRASS, new Item.Properties().group(MoCItemGroup.TABMOC)),
                    new BlockItem(OGRE_DIRT, new Item.Properties().group(MoCItemGroup.TABMOC)),
                    new BlockItem(OGRE_LEAVES, new Item.Properties().group(MoCItemGroup.TABMOC)),
                    new BlockItem(OGRE_LOG, new Item.Properties().group(MoCItemGroup.TABMOC)),
                    new BlockItem(OGRE_TALLGRASS, new Item.Properties().group(MoCItemGroup.TABMOC)),
                    new BlockItem(OGRE_PLANKS, new Item.Properties().group(MoCItemGroup.TABMOC))
            };

            final IForgeRegistry<Item> registry = event.getRegistry();

            for (final BlockItem item : items) {
                final Block block = item.getBlock();
                final ResourceLocation registryName = block.getRegistryName();
                registry.register(item.setRegistryName(registryName));
//                if (!MoCreatures.isServer()) {
//                    final String name = item.getUnlocalizedName().replace("tile.", "").replace("MoC", "").toLowerCase();
//                    System.out.println("registering custom location " + name);
//                    ModelBakery.registerItemVariants(item, new ResourceLocation("mocreatures:wyvern_" + name));
//                    ModelBakery.registerItemVariants(item, new ResourceLocation("mocreatures:ogre_" + name));
//                    ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation("mocreatures:wyvern_" + name, "inventory"));
//                    ModelLoader.setCustomModelResourceLocation(item, 1, new ModelResourceLocation("mocreatures:ogre_" + name, "inventory"));
//                    ModelLoader.setCustomModelResourceLocation(item, 2, new ModelResourceLocation("mocreatures:wyvern_" + name, "variant=wyvern_lair"));
//                    ModelLoader.setCustomModelResourceLocation(item, 3, new ModelResourceLocation("mocreatures:ogre_" + name, "variant=ogre_lair"));
//                }
            }
        }
    }
}
