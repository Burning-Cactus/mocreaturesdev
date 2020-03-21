package drzhark.mocreatures.block;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.state.EnumProperty;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MoCBlock extends Block {

    public static final EnumProperty<EnumType> VARIANT = EnumProperty.create("variant", EnumType.class);

    public MoCBlock(Block.Properties builder) {
        super(builder);
    }

//    public MoCBlock(String name, Material material) {
//        super(Block.Properties.create(material));
//        this.setRegistryName(MoCConstants.MOD_ID, name);
//        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.WYVERN_LAIR));
//    }

    @Override
    public int damageDropped(BlockState state) {
        return ((EnumType) state.getValue(VARIANT)).getMetadata();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(ItemGroup tab, NonNullList<ItemStack> items) {
        for (int ix = 0; ix < MoCBlocks.multiBlockNames.size(); ix++) {
            items.add(new ItemStack(this, 1, ix));
        }
    }

    @Override
    public BlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, MoCBlock.EnumType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(BlockState state) {
        return ((MoCBlock.EnumType) state.getValue(VARIANT)).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

    public static enum EnumType implements IStringSerializable {
        WYVERN_LAIR(0, "wyvern_lair"), OGRE_LAIR(1, "ogre_lair");

        private static final MoCBlock.EnumType[] META_LOOKUP = new MoCBlock.EnumType[values().length];
        private final int meta;
        private final String name;
        private final String unlocalizedName;

        private EnumType(int meta, String name) {
            this(meta, name, name);
        }

        private EnumType(int meta, String name, String unlocalizedName) {
            this.meta = meta;
            this.name = name;
            this.unlocalizedName = unlocalizedName;
        }

        public int getMetadata() {
            return this.meta;
        }

        @Override
        public String toString() {
            return this.name;
        }

        public static MoCBlock.EnumType byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        @Override
        public String getName() {
            return this.name;
        }

        public String getUnlocalizedName() {
            return this.unlocalizedName;
        }

        static {
            MoCBlock.EnumType[] var0 = values();
            int var1 = var0.length;

            for (int var2 = 0; var2 < var1; ++var2) {
                MoCBlock.EnumType var3 = var0[var2];
                META_LOOKUP[var3.getMetadata()] = var3;
            }
        }
    }
}
