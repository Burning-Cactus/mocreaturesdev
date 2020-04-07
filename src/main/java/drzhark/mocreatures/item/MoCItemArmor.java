package drzhark.mocreatures.item;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCProxy;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MoCItemArmor extends ArmorItem {

    public MoCItemArmor(ArmorMaterial materialIn, int renderIndex, EquipmentSlotType equipmentSlotIn, Item.Properties builder) {
        super(materialIn, equipmentSlotIn, builder);
//        super(materialIn, renderIndex, equipmentSlotIn);
//        this.setCreativeTab(MoCreatures.tabMoC);
//        this.setRegistryName(MoCConstants.MOD_ID, name);
//        this.setUnlocalizedName(name);
    }

    @Override
    public String getArmorTexture(ItemStack itemstack, Entity entity, EquipmentSlotType slot, String type) {
        String tempArmorTexture = "croc_1.png";
        if ((itemstack.getItem() == MoCItems.HELMETCROC) || (itemstack.getItem() == MoCItems.PLATECROC)
                || (itemstack.getItem() == MoCItems.BOOTSCROC)) {
            tempArmorTexture = "croc_1.png";
        }
        if (itemstack.getItem() == MoCItems.LEGSCROC) {
            tempArmorTexture = "croc_2.png";
        }

        if ((itemstack.getItem() == MoCItems.HELMETFUR) || (itemstack.getItem() == MoCItems.CHESTFUR)
                || (itemstack.getItem() == MoCItems.BOOTSFUR)) {
            tempArmorTexture = "fur_1.png";
        }
        if (itemstack.getItem() == MoCItems.LEGSFUR) {
            tempArmorTexture = "fur_2.png";
        }

        if ((itemstack.getItem() == MoCItems.HELMETHIDE) || (itemstack.getItem() == MoCItems.CHESTHIDE)
                || (itemstack.getItem() == MoCItems.BOOTSHIDE)) {
            tempArmorTexture = "hide_1.png";
        }
        if (itemstack.getItem() == MoCItems.LEGSHIDE) {
            tempArmorTexture = "hide_2.png";
        }

        if ((itemstack.getItem() == MoCItems.SCORPHELMETDIRT) || (itemstack.getItem() == MoCItems.SCORPPLATEDIRT)
                || (itemstack.getItem() == MoCItems.SCORPBOOTSDIRT)) {
            tempArmorTexture = "scorpd_1.png";
        }
        if (itemstack.getItem() == MoCItems.SCORPLEGSDIRT) {
            tempArmorTexture = "scorpd_2.png";
        }

        if ((itemstack.getItem() == MoCItems.SCORPHELMETFROST) || (itemstack.getItem() == MoCItems.SCORPPLATEFROST)
                || (itemstack.getItem() == MoCItems.SCORPBOOTSFROST)) {
            tempArmorTexture = "scorpf_1.png";
        }
        if (itemstack.getItem() == MoCItems.SCORPLEGSFROST) {
            tempArmorTexture = "scorpf_2.png";
        }

        if ((itemstack.getItem() == MoCItems.SCORPHELMETCAVE) || (itemstack.getItem() == MoCItems.SCORPPLATECAVE)
                || (itemstack.getItem() == MoCItems.SCORPBOOTSCAVE)) {
            tempArmorTexture = "scorpc_1.png";
        }
        if (itemstack.getItem() == MoCItems.SCORPLEGSCAVE) {
            tempArmorTexture = "scorpc_2.png";
        }

        if ((itemstack.getItem() == MoCItems.SCORPHELMETNETHER) || (itemstack.getItem() == MoCItems.SCORPPLATENETHER)
                || (itemstack.getItem() == MoCItems.SCORPBOOTSNETHER)) {
            tempArmorTexture = "scorpn_1.png";
        }
        if (itemstack.getItem() == MoCItems.SCORPLEGSNETHER) {
            tempArmorTexture = "scorpn_2.png";
        }

        return "mocreatures:" + MoCProxy.ARMOR_TEXTURE + tempArmorTexture;
    }

    /**
     * Called to tick armor in the armor slot. Override to do something
     *
     * @param world
     * @param player
     * @param itemStack
     */
    @Override
    public void onArmorTick(World world, PlayerEntity player, ItemStack itemStack) {
        if (world.rand.nextInt(50) == 0 && player.getItemStackFromSlot(EquipmentSlotType.FEET) != null) {
            ItemStack stack = player.getItemStackFromSlot(EquipmentSlotType.FEET);
            if (!stack.isEmpty() && stack.getItem() instanceof MoCItemArmor) {
                MoCTools.updatePlayerArmorEffects(player);
            }
        }
    }
}
