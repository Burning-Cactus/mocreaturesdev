package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.block.*;
import drzhark.mocreatures.item.ItemBuilderHammer;
import drzhark.mocreatures.item.ItemStaffPortal;
import drzhark.mocreatures.item.ItemStaffTeleport;
import drzhark.mocreatures.item.MoCItem;
import drzhark.mocreatures.item.MoCItemArmor;
import drzhark.mocreatures.item.MoCItemEgg;
import drzhark.mocreatures.item.MoCItemFood;
import drzhark.mocreatures.item.MoCItemHayStack;
import drzhark.mocreatures.item.MoCItemHorseAmulet;
import drzhark.mocreatures.item.MoCItemHorseSaddle;
import drzhark.mocreatures.item.MoCItemKittyBed;
import drzhark.mocreatures.item.MoCItemLitterBox;
import drzhark.mocreatures.item.MoCItemPetAmulet;
import drzhark.mocreatures.item.MoCItemRecord;
import drzhark.mocreatures.item.MoCItemSugarLump;
import drzhark.mocreatures.item.MoCItemSword;
import drzhark.mocreatures.item.MoCItemTurtleSoup;
import drzhark.mocreatures.item.MoCItemWeapon;
import drzhark.mocreatures.item.MoCItemWhip;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MoCItems {

    public static ArmorMaterial scorpARMOR;
    public static ArmorMaterial furARMOR;
    public static ArmorMaterial hideARMOR;
    public static ArmorMaterial scorpdARMOR;
    public static ArmorMaterial scorpfARMOR;
    public static ArmorMaterial scorpnARMOR;
    public static ArmorMaterial scorpcARMOR;
    public static ArmorMaterial silverARMOR;

    public static final DeferredRegister<Item> ITEMS = new DeferredRegister<>(ForgeRegistries.ITEMS, MoCConstants.MOD_ID);

    public static final RegistryObject<Item> HORSESADDLE = ITEMS.register("horsesaddle", () -> new MoCItemHorseSaddle());
    public static final RegistryObject<Item> RECORDSHUFFLE = ITEMS.register("recordshuffle", () -> new MoCItemRecord(new Item.Properties(), MoCSoundEvents.ITEM_RECORD_SHUFFLING));

    public static final RegistryObject<Item> SHARKTEETH = ITEMS.register("sharkteeth", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> HAYSTACK = ITEMS.register("haystack", () -> new MoCItemHayStack(new Item.Properties()));
    public static final RegistryObject<Item> SUGARLUMP = ITEMS.register("sugarlump", () -> new MoCItemSugarLump(new Item.Properties()));
    public static final RegistryObject<Item> MOCEGG = ITEMS.register("mocegg", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> BIGCATCLAW = ITEMS.register("bigcatclaw", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> WHIP = ITEMS.register("whip", MoCItemWhip::new);
    public static final RegistryObject<Item> STAFFPORTAL = ITEMS.register("staffportal", ItemStaffPortal::new);
    public static final RegistryObject<Item> STAFFTELEPORT = ITEMS.register("staffteleport", ItemStaffTeleport::new);

    public static final RegistryObject<Item> MEDALLION = ITEMS.register("medallion", () -> new MoCItem(new Item.Properties()));
    //TODO: Insert Kittybed here
    public static final RegistryObject<Item> LITTERBOX = ITEMS.register("litterbox", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> WOOLBALL = ITEMS.register("woolball", () -> new MoCItem(new Item.Properties()));

    public static final RegistryObject<Item> PETFOOD = ITEMS.register("petfood", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> BUILDERHAMMER = ITEMS.register("builderhammer", ItemBuilderHammer::new);

    //Armor materials
    public static final RegistryObject<Item> HIDECROC = ITEMS.register("hidecroc", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> FUR = ITEMS.register("fur", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> HIDE = ITEMS.register("hide", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> CHITINCAVE = ITEMS.register("chitinblack", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> CHITINFROST = ITEMS.register("chitinfrost", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> CHITINNETHER = ITEMS.register("chitinnether", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> CHITIN = ITEMS.register("chitin", () -> new MoCItem(new Item.Properties()));

    //Essences
    public static final RegistryObject<Item> ESSENCEDARKNESS = ITEMS.register("essencedarkness", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> ESSENCEFIRE = ITEMS.register("essencefire", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> ESSENCEUNDEAD = ITEMS.register("essenceundead", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> ESSENCELIGHT = ITEMS.register("essencelight", () -> new MoCItem(new Item.Properties()));

    //Horse Amulets
    public static final RegistryObject<Item> AMULETBONE = ITEMS.register("amuletbone", MoCItemHorseAmulet::new);
    public static final RegistryObject<Item> AMULETBONEFULL = ITEMS.register("amuletbonefull", MoCItemHorseAmulet::new);
    public static final RegistryObject<Item> AMULETGHOST = ITEMS.register("amuletghost", MoCItemHorseAmulet::new);
    public static final RegistryObject<Item> AMULETGHOSTFULL = ITEMS.register("amuletghostfull", MoCItemHorseAmulet::new);
    public static final RegistryObject<Item> AMULETFAIRY = ITEMS.register("amuletfairy", MoCItemHorseAmulet::new);
    public static final RegistryObject<Item> AMULETFAIRYFULL = ITEMS.register("amuletfairyfull", MoCItemHorseAmulet::new);
    public static final RegistryObject<Item> AMULETPEGASUS = ITEMS.register("amuletpegasus", MoCItemHorseAmulet::new);
    public static final RegistryObject<Item> AMULETPEGASUSFULL = ITEMS.register("amuletpegasusfull", MoCItemHorseAmulet::new);
    public static final RegistryObject<Item> PETAMULET = ITEMS.register("petamulet", () -> new MoCItemPetAmulet());
    public static final RegistryObject<Item> PETAMULETFULL = ITEMS.register("petamuletfull", () -> new MoCItemPetAmulet());
    public static final RegistryObject<Item> FISHNET = ITEMS.register("fishnet", () -> new MoCItemPetAmulet());
    public static final RegistryObject<Item> FISHNETFULL = ITEMS.register("fishnetfull", () -> new MoCItemPetAmulet());

    //Hearts
    public static final RegistryObject<Item> HEARTDARKNESS = ITEMS.register("heartdarkness", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> HEARTFIRE = ITEMS.register("heartfire", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> HEARTUNDEAD = ITEMS.register("heartundead", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> UNICORNHORN = ITEMS.register("unicornhorn", () -> new MoCItem(new Item.Properties()));

    //Horse Armor
    public static final RegistryObject<Item> HORSEARMORCRYSTAL = ITEMS.register("horsearmorcrystal", () -> new MoCItem(new Item.Properties()));

    //Weapons
    public static final RegistryObject<Item> NUNCHAKU = ITEMS.register("nunchaku", () -> new MoCItemSword(ItemTier.IRON, new Item.Properties()));
    public static final RegistryObject<Item> SAI = ITEMS.register("sai", () -> new MoCItemSword(ItemTier.IRON, new Item.Properties()));
    public static final RegistryObject<Item> BO = ITEMS.register("bo", () -> new MoCItemSword(ItemTier.IRON, new Item.Properties()));
    public static final RegistryObject<Item> KATANA = ITEMS.register("katana", () -> new MoCItemSword(ItemTier.IRON, new Item.Properties()));
    public static final RegistryObject<Item> SHARKSWORD = ITEMS.register("sharksword", () -> new MoCItemSword(ItemTier.IRON, new Item.Properties()));
    public static final RegistryObject<Item> SILVERSWORD = ITEMS.register("silversword", () -> new MoCItemSword(ItemTier.IRON, new Item.Properties()));
//    public static final MoCItemSword silversword = new MoCItemSword("silversword", EnumHelper.addToolMaterial("SILVER", 0, 250, 6.0F, 4, 15));
    //TODO: Keeping the old method here for reference, need to add silver as a material later.
    public static final RegistryObject<Item> SCORPSWORDCAVE = ITEMS.register("scorpswordcave", () -> new MoCItemSword(ItemTier.IRON, new Item.Properties()));
    public static final RegistryObject<Item> SCORPSWORDFROST = ITEMS.register("scorpswordfrost", () -> new MoCItemSword(ItemTier.IRON, new Item.Properties()));
    public static final RegistryObject<Item> SCORPSWORDNETHER = ITEMS.register("scorpswordnether", () -> new MoCItemSword(ItemTier.IRON, new Item.Properties()));
    public static final RegistryObject<Item> SCORPSWORDDIRT = ITEMS.register("scorpsworddirt", () -> new MoCItemSword(ItemTier.IRON, new Item.Properties()));
    //TODO: Add effects to the scorpion swords

    public static final RegistryObject<Item> SCORPSTINGCAVE = ITEMS.register("scorpstingcave", () -> new MoCItemWeapon(ItemTier.GOLD, new Item.Properties()));
    public static final RegistryObject<Item> SCORPSTINGFROST = ITEMS.register("scorpstingfrost", () -> new MoCItemWeapon(ItemTier.GOLD, new Item.Properties()));
    public static final RegistryObject<Item> SCORPSTINGNETHER = ITEMS.register("scorpstingnether", () -> new MoCItemWeapon(ItemTier.GOLD, new Item.Properties()));
    public static final RegistryObject<Item> SCORPSTINGDIRT = ITEMS.register("scorpstingdirt", () -> new MoCItemWeapon(ItemTier.GOLD, new Item.Properties()));

    //Tusks
    public static final RegistryObject<Item> TUSKSWOOD = ITEMS.register("tuskswood", () -> new MoCItemWeapon(ItemTier.WOOD, new Item.Properties()));
    public static final RegistryObject<Item> TUSKSIRON = ITEMS.register("tusksiron", () -> new MoCItemWeapon(ItemTier.IRON, new Item.Properties()));
    public static final RegistryObject<Item> TUSKSDIAMOND = ITEMS.register("tusksdiamond", () -> new MoCItemWeapon(ItemTier.DIAMOND, new Item.Properties()));

    //Armor
    public static final RegistryObject<Item> PLATECROC = ITEMS.register("reptileplate", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()));
    public static final RegistryObject<Item> HELMETCROC = ITEMS.register("reptilehelmet", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()));
    public static final RegistryObject<Item> LEGSCROC = ITEMS.register("reptilelegs", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties()));
    public static final RegistryObject<Item> BOOTSCROC = ITEMS.register("reptileboots", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.FEET, new Item.Properties()));

    public static final RegistryObject<Item> CHESTFUR = ITEMS.register("furchest", () -> new MoCItemArmor(furARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties());
    public static final RegistryObject<Item> HELMETFUR = ITEMS.register("furhelmet", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()));
    public static final RegistryObject<Item> LEGSFUR = ITEMS.register("furlegs", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties()));
    public static final RegistryObject<Item> BOOTSFUR = ITEMS.register("furboots", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.FEET, new Item.Properties()));

    public static final RegistryObject<Item> CHESTHIDE = ITEMS.register("hidechest", () -> new MoCItemArmor(hideARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()));
    public static final RegistryObject<Item> HELMETHIDE = ITEMS.register("hidehelmet", () -> new MoCItemArmor(hideARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()));
    public static final RegistryObject<Item> LEGSHIDE = ITEMS.register("hidelegs", () -> new MoCItemArmor(hideARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties()));
    public static final RegistryObject<Item> BOOTSHIDE = ITEMS.register("hideboots", () -> new MoCItemArmor(hideARMOR, 4, EquipmentSlotType.FEET, new Item.Properties()));

    public static final RegistryObject<Item> SCORPPLATEDIRT = ITEMS.register("scorpplatedirt", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()));
    public static final RegistryObject<Item> SCORPHELMETDIRT = ITEMS.register("scorphelmetdirt", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()));
    public static final RegistryObject<Item> SCORPLEGSDIRT = ITEMS.register("scorplegsdirt", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties()));
    public static final RegistryObject<Item> SCORPBOOTSDIRT = ITEMS.register("scorpbootsdirt", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.FEET, new Item.Properties()));

    public static final RegistryObject<Item> SCORPPLATEFROST = ITEMS.register("scorpplatefrost", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()));
    public static final RegistryObject<Item> SCORPHELMETFROST = ITEMS.register("scorphelmetfrost", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()));
    public static final RegistryObject<Item> SCORPLEGSFROST = ITEMS.register("scorplegsfrost", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties()));
    public static final RegistryObject<Item> SCORPBOOTSFROST = ITEMS.register("scorpbootsfrost", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.FEET, new Item.Properties()));

    public static final RegistryObject<Item> SCORPPLATECAVE = ITEMS.register("scorpplatecave", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()));
    public static final RegistryObject<Item> SCORPHELMETCAVE = ITEMS.register("scorphelmetcave", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()));
    public static final RegistryObject<Item> SCORPLEGSCAVE = ITEMS.register("scorplegscave", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties()));
    public static final RegistryObject<Item> SCORPBOOTSCAVE = ITEMS.register("scorpbootscave", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.FEET, new Item.Properties()));

    public static final RegistryObject<Item> SCORPPLATENETHER = ITEMS.register("scorpplatenether", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()));
    public static final RegistryObject<Item> SCORPHELMETNETHER = ITEMS.register("scorphelmetnether", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()));
    public static final RegistryObject<Item> SCORPLEGSNETHER = ITEMS.register("scorplegsnether", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties()));
    public static final RegistryObject<Item> SCORPBOOTSNETHER = ITEMS.register("scorpbootsnether", () -> new MoCItemArmor(scorpARMOR, 4, EquipmentSlotType.FEET, new Item.Properties()));


    //Elephant stuff!
    public static final RegistryObject<Item> ELEPHANTHARNESS = ITEMS.register("elephantharness", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> ELEPHANTCHEST = ITEMS.register("elephantchest", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> ELEPHANTGARMENT = ITEMS.register("elephantgarment", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> ELEPHANTHOWDAH = ITEMS.register("elepenthowdah", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> MAMMOTHPLATFORM = ITEMS.register("mammothplatform", () -> new MoCItem(new Item.Properties()));

    //Scrolls
    public static final RegistryObject<Item> SCROLLFREEDOM = ITEMS.register("scrollfreedom", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> SCROLLOFSALE = ITEMS.register("scrollofsale", () -> new MoCItem(new Item.Properties()));
    public static final RegistryObject<Item> SCROLLOFOWNER = ITEMS.register("scrollofowner", () -> new MoCItem(new Item.Properties()));


    //TODO: Register and implement Food items



    //BlockItems
    public static final RegistryObject<Item> WYVERN_STONE = ITEMS.register("wyvern_stone", () -> new BlockItem(MoCBlocks.WYVERN_STONE.get(), new Item.Properties().group(MoCreatures.tabMoC)));
    public static final RegistryObject<Item> WYVERN_GRASS = ITEMS.register("wyvern_grass", () -> new BlockItem(MoCBlocks.WYVERN_GRASS.get(), new Item.Properties().group(MoCreatures.tabMoC)));
    public static final RegistryObject<Item> WYVERN_DIRT = ITEMS.register("wyvern_dirt", () -> new BlockItem(MoCBlocks.WYVERN_DIRT.get(), new Item.Properties().group(MoCreatures.tabMoC)));
    public static final RegistryObject<Item> WYVERN_LEAVES = ITEMS.register("wyvern_leaves", () -> new BlockItem(MoCBlocks.WYVERN_LEAVES.get(), new Item.Properties().group(MoCreatures.tabMoC)));
    public static final RegistryObject<Item> WYVERN_LOG = ITEMS.register("wyvern_log", () -> new BlockItem(MoCBlocks.WYVERN_LOG.get(), new Item.Properties().group(MoCreatures.tabMoC)));
    public static final RegistryObject<Item> WYVERN_TALLGRASS = ITEMS.register("wyvern_tallgrass", () -> new BlockItem(MoCBlocks.WYVERN_TALLGRASS.get(), new Item.Properties().group(MoCreatures.tabMoC)));
    public static final RegistryObject<Item> WYVERN_WOODPLANK = ITEMS.register("wyvern_woodplank", () -> new BlockItem(MoCBlocks.WYVERN_WOODPLANK.get(), new Item.Properties().group(MoCreatures.tabMoC)));
    public static final RegistryObject<Item> OGRE_STONE = ITEMS.register("ogre_stone", () -> new BlockItem(MoCBlocks.OGRE_STONE.get(), new Item.Properties().group(MoCreatures.tabMoC)));
    public static final RegistryObject<Item> OGRE_GRASS = ITEMS.register("ogre_grass", () -> new BlockItem(MoCBlocks.OGRE_GRASS.get(), new Item.Properties().group(MoCreatures.tabMoC)));
    public static final RegistryObject<Item> OGRE_DIRT = ITEMS.register("ogre_dirt", () -> new BlockItem(MoCBlocks.OGRE_DIRT.get(), new Item.Properties().group(MoCreatures.tabMoC)));
    public static final RegistryObject<Item> OGRE_LEAVES = ITEMS.register("ogre_leaves", () -> new BlockItem(MoCBlocks.OGRE_LEAVES.get(), new Item.Properties().group(MoCreatures.tabMoC)));
    public static final RegistryObject<Item> OGRE_LOG = ITEMS.register("ogre_log", () -> new BlockItem(MoCBlocks.OGRE_LOG.get(), new Item.Properties().group(MoCreatures.tabMoC)));
    public static final RegistryObject<Item> OGRE_TALLGRASS = ITEMS.register("ogre_tallgrass", () -> new BlockItem(MoCBlocks.OGRE_TALLGRASS.get(), new Item.Properties().group(MoCreatures.tabMoC)));
    public static final RegistryObject<Item> OGRE_WOODPLANK = ITEMS.register("ogre_woodplank", () -> new BlockItem(MoCBlocks.OGRE_WOODPLANK.get(), new Item.Properties().group(MoCreatures.tabMoC)));


//    public static final Set<Item> ITEMS = new HashSet<>();
//
//    static ArmorMaterial scorpARMOR = EnumHelper.addArmorMaterial("crocARMOR", "crocARMOR", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
//    static ArmorMaterial furARMOR = EnumHelper.addArmorMaterial("furARMOR", "furARMOR", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
//    static ArmorMaterial hideARMOR = EnumHelper.addArmorMaterial("hideARMOR", "hideARMOR", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
//    static ArmorMaterial scorpdARMOR = EnumHelper.addArmorMaterial("scorpdARMOR", "scorpdARMOR", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
//    static ArmorMaterial scorpfARMOR = EnumHelper.addArmorMaterial("scorpfARMOR", "scorpdARMOR", 18, new int[] {2, 7, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
//    static ArmorMaterial scorpnARMOR = EnumHelper.addArmorMaterial("scorpnARMOR", "scorpdARMOR", 20, new int[] {3, 7, 6, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
//    static ArmorMaterial scorpcARMOR = EnumHelper.addArmorMaterial("scorpcARMOR", "scorpdARMOR", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
//    static ArmorMaterial silverARMOR = EnumHelper.addArmorMaterial("silverARMOR", "scorpdARMOR", 15, new int[] {2, 6, 5, 2}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
//
//    public static final MoCItemRecord recordshuffle = new MoCItemRecord("recordshuffle", MoCSoundEvents.ITEM_RECORD_SHUFFLING);
//    public static final MoCItem horsesaddle = new MoCItemHorseSaddle("horsesaddle");
//
//    public static final MoCItem sharkteeth = new MoCItem("sharkteeth");
//    public static final MoCItem haystack = new MoCItemHayStack("haystack");
//    public static final MoCItemSugarLump sugarlump = new MoCItemSugarLump("sugarlump");
//    public static final MoCItem mocegg = new MoCItemEgg("mocegg");
//    public static final MoCItem bigcatclaw = new MoCItem("bigcatclaw");
//    public static final MoCItem whip = new MoCItemWhip("whip");
//    public static final MoCItem staffPortal = new ItemStaffPortal("staffportal");
//    public static final MoCItem staffTeleport = new ItemStaffTeleport("staffteleport");
//
//    public static final MoCItem medallion = new MoCItem("medallion");
//    public static final MoCItemKittyBed[] kittybed = new MoCItemKittyBed[16];
//    public static final MoCItem litterbox = new MoCItemLitterBox("kittylitter");
//    public static final MoCItem woolball = new MoCItem("woolball");
//
//    public static final MoCItem petfood = new MoCItem("petfood");
//    public static final MoCItem builderHammer = new ItemBuilderHammer("builderhammer");
//
//    public static final MoCItem hideCroc = new MoCItem("reptilehide");
//    public static final MoCItem fur = new MoCItem("fur");
//
//    public static final MoCItem essencedarkness = new MoCItem("essencedarkness");
//    public static final MoCItem essencefire = new MoCItem("essencefire");
//    public static final MoCItem essenceundead = new MoCItem("essenceundead");
//    public static final MoCItem essencelight = new MoCItem("essencelight");
//
//    public static final MoCItem amuletbone = new MoCItemHorseAmulet("amuletbone");
//    public static final MoCItem amuletbonefull = new MoCItemHorseAmulet("amuletbonefull");
//    public static final MoCItem amuletghost = new MoCItemHorseAmulet("amuletghost");
//    public static final MoCItem amuletghostfull = new MoCItemHorseAmulet("amuletghostfull");
//    public static final MoCItem amuletfairy = new MoCItemHorseAmulet("amuletfairy");
//    public static final MoCItem amuletfairyfull = new MoCItemHorseAmulet("amuletfairyfull");
//    public static final MoCItem amuletpegasus = new MoCItemHorseAmulet("amuletpegasus");
//    public static final MoCItem amuletpegasusfull = new MoCItemHorseAmulet("amuletpegasusfull");
//    public static final MoCItem fishnet = new MoCItemPetAmulet("fishnet");
//    public static final MoCItem fishnetfull = new MoCItemPetAmulet("fishnetfull");
//    public static final MoCItem petamulet = new MoCItemPetAmulet("petamulet", 1);
//    public static final MoCItem petamuletfull = new MoCItemPetAmulet("petamuletfull", 1);
//
//    public static final MoCItem heartdarkness = new MoCItem("heartdarkness");
//    public static final MoCItem heartfire = new MoCItem("heartfire");
//    public static final MoCItem heartundead = new MoCItem("heartundead");
//    public static final MoCItem unicornhorn = new MoCItem("unicornhorn");
//
//    public static final MoCItem horsearmorcrystal = new MoCItem("horsearmorcrystal");
//
//    public static final MoCItem animalHide = new MoCItem("hide");
//    public static final MoCItem chitinCave = new MoCItem("chitinblack");
//    public static final MoCItem chitinFrost = new MoCItem("chitinfrost");
//    public static final MoCItem chitinNether = new MoCItem("chitinnether");
//    public static final MoCItem chitin = new MoCItem("chitin");
//
//    // Weapons
//    public static final MoCItemSword nunchaku = new MoCItemSword("nunchaku", Item.ToolMaterial.IRON);
//    public static final MoCItemSword sai = new MoCItemSword("sai", Item.ToolMaterial.IRON);
//    public static final MoCItemSword bo = new MoCItemSword("bo", Item.ToolMaterial.IRON);
//    public static final MoCItemSword katana = new MoCItemSword("katana", Item.ToolMaterial.IRON);
//    public static final MoCItemSword sharksword = new MoCItemSword("sharksword", Item.ToolMaterial.IRON);
//    public static final MoCItemSword silversword = new MoCItemSword("silversword", EnumHelper.addToolMaterial("SILVER", 0, 250, 6.0F, 4, 15));
//
//    public static final MoCItemSword scorpSwordCave = new MoCItemSword("scorpswordcave", ToolMaterial.IRON, 4, false);
//    public static final MoCItemSword scorpSwordFrost = new MoCItemSword("scorpswordfrost", ToolMaterial.IRON, 2, false);
//    public static final MoCItemSword scorpSwordNether = new MoCItemSword("scorpswordnether", ToolMaterial.IRON, 3, false);
//    public static final MoCItemSword scorpSwordDirt = new MoCItemSword("scorpsworddirt", ToolMaterial.IRON, 1, false);
//    public static final MoCItemWeapon scorpStingCave = new MoCItemWeapon("scorpstingcave", ToolMaterial.GOLD, 4, true);
//    public static final MoCItemWeapon scorpStingFrost = new MoCItemWeapon("scorpstingfrost", ToolMaterial.GOLD, 2, true);
//    public static final MoCItemWeapon scorpStingNether = new MoCItemWeapon("scorpstingnether", ToolMaterial.GOLD, 3, true);
//    public static final MoCItemWeapon scorpStingDirt = new MoCItemWeapon("scorpstingdirt", ToolMaterial.GOLD, 1, true);
//
//    public static final MoCItemWeapon tusksWood = new MoCItemWeapon("tuskswood", ToolMaterial.WOOD);
//    public static final MoCItemWeapon tusksIron = new MoCItemWeapon("tusksiron", ToolMaterial.IRON);
//    public static final MoCItemWeapon tusksDiamond = new MoCItemWeapon("tusksdiamond", ToolMaterial.DIAMOND);
//
//    // Armors
//    public static final MoCItemArmor plateCroc = new MoCItemArmor("reptileplate", scorpARMOR, 4, EntityEquipmentSlot.CHEST);
//    public static final MoCItemArmor helmetCroc = new MoCItemArmor("reptilehelmet", scorpARMOR, 4, EntityEquipmentSlot.HEAD);
//    public static final MoCItemArmor legsCroc = new MoCItemArmor("reptilelegs", scorpARMOR, 4, EntityEquipmentSlot.LEGS);
//    public static final MoCItemArmor bootsCroc = new MoCItemArmor("reptileboots", scorpARMOR, 4, EntityEquipmentSlot.FEET);
//
//    public static final MoCItemArmor chestFur = new MoCItemArmor("furchest", furARMOR, 4, EntityEquipmentSlot.CHEST);
//    public static final MoCItemArmor helmetFur = new MoCItemArmor("furhelmet", furARMOR, 4, EntityEquipmentSlot.HEAD);
//    public static final MoCItemArmor legsFur = new MoCItemArmor("furlegs", furARMOR, 4, EntityEquipmentSlot.LEGS);
//    public static final MoCItemArmor bootsFur = new MoCItemArmor("furboots", furARMOR, 4, EntityEquipmentSlot.FEET);
//
//    public static final MoCItemArmor chestHide = new MoCItemArmor("hidechest", hideARMOR, 4, EntityEquipmentSlot.CHEST);
//    public static final MoCItemArmor helmetHide = new MoCItemArmor("hidehelmet", hideARMOR, 4, EntityEquipmentSlot.HEAD);
//    public static final MoCItemArmor legsHide = new MoCItemArmor("hidelegs", hideARMOR, 4, EntityEquipmentSlot.LEGS);
//    public static final MoCItemArmor bootsHide = new MoCItemArmor("hideboots", hideARMOR, 4, EntityEquipmentSlot.FEET);
//
//    public static final MoCItemArmor scorpPlateDirt = new MoCItemArmor("scorpplatedirt", scorpARMOR, 4, EntityEquipmentSlot.CHEST);
//    public static final MoCItemArmor scorpHelmetDirt = new MoCItemArmor("scorphelmetdirt", scorpARMOR, 4, EntityEquipmentSlot.HEAD);
//    public static final MoCItemArmor scorpLegsDirt = new MoCItemArmor("scorplegsdirt", scorpARMOR, 4, EntityEquipmentSlot.LEGS);
//    public static final MoCItemArmor scorpBootsDirt = new MoCItemArmor("scorpbootsdirt", scorpARMOR, 4, EntityEquipmentSlot.FEET);
//
//    public static final MoCItemArmor scorpPlateFrost = new MoCItemArmor("scorpplatefrost", scorpARMOR, 4, EntityEquipmentSlot.CHEST);
//    public static final MoCItemArmor scorpHelmetFrost = new MoCItemArmor("scorphelmetfrost", scorpARMOR, 4, EntityEquipmentSlot.HEAD);
//    public static final MoCItemArmor scorpLegsFrost = new MoCItemArmor("scorplegsfrost", scorpARMOR, 4, EntityEquipmentSlot.LEGS);
//    public static final MoCItemArmor scorpBootsFrost = new MoCItemArmor("scorpbootsfrost", scorpARMOR, 4, EntityEquipmentSlot.FEET);
//
//    public static final MoCItemArmor scorpPlateCave = new MoCItemArmor("scorpplatecave", scorpARMOR, 4, EntityEquipmentSlot.CHEST);
//    public static final MoCItemArmor scorpHelmetCave = new MoCItemArmor("scorphelmetcave", scorpARMOR, 4, EntityEquipmentSlot.HEAD);
//    public static final MoCItemArmor scorpLegsCave = new MoCItemArmor("scorplegscave", scorpARMOR, 4, EntityEquipmentSlot.LEGS);
//    public static final MoCItemArmor scorpBootsCave = new MoCItemArmor("scorpbootscave", scorpARMOR, 4, EntityEquipmentSlot.FEET);
//
//    public static final MoCItemArmor scorpPlateNether = new MoCItemArmor("scorpplatenether", scorpARMOR, 4, EntityEquipmentSlot.CHEST);
//    public static final MoCItemArmor scorpHelmetNether = new MoCItemArmor("scorphelmetnether", scorpARMOR, 4, EntityEquipmentSlot.HEAD);
//    public static final MoCItemArmor scorpLegsNether = new MoCItemArmor("scorplegsnether", scorpARMOR, 4, EntityEquipmentSlot.LEGS);
//    public static final MoCItemArmor scorpBootsNether = new MoCItemArmor("scorpbootsnether", scorpARMOR, 4, EntityEquipmentSlot.FEET);
//
//    public static final MoCItem elephantHarness = new MoCItem("elephantharness");
//    public static final MoCItem elephantChest = new MoCItem("elephantchest");
//    public static final MoCItem elephantGarment = new MoCItem("elephantgarment");
//    public static final MoCItem elephantHowdah = new MoCItem("elephanthowdah");
//    public static final MoCItem mammothPlatform = new MoCItem("mammothplatform");
//
//    public static final MoCItem scrollFreedom = new MoCItem("scrolloffreedom");
//    public static final MoCItem scrollOfSale = new MoCItem("scrollofsale");
//    public static final MoCItem scrollOfOwner = new MoCItem("scrollofowner");
//
//    // foods
//    public static final MoCItemFood cookedTurkey = new MoCItemFood("turkeycooked", 8, 0.6F, false);
//    public static final MoCItemFood crabraw = (MoCItemFood) new MoCItemFood("crabraw", 2, 0.3F, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30, 0), 0.8F);
//    public static final MoCItemFood crabcooked = new MoCItemFood("crabcooked", 6, 0.6F, false);
//    public static final MoCItemFood omelet = new MoCItemFood("omelet", 4, 0.6F, false);
//    public static final MoCItemFood ostrichraw = (MoCItemFood) new MoCItemFood("ostrichraw", 2, 0.3F, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30, 0), 0.8F);
//    public static final MoCItemFood ostrichcooked = new MoCItemFood("ostrichcooked", 6, 0.6F, false);
//    public static final MoCItemFood ratBurger = new MoCItemFood("ratburger", 8, 0.6F, false);
//    public static final MoCItemFood ratCooked = new MoCItemFood("ratcooked", 4, 0.6F, false);
//    public static final MoCItemFood ratRaw = (MoCItemFood) new MoCItemFood("ratraw", 2, 0.3F, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30, 0), 0.8F);
//    public static final MoCItemFood rawTurkey = (MoCItemFood) new MoCItemFood("turkeyraw", 3, 0.3F, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30, 0), 0.8F);
//    public static final MoCItemFood turtleraw = new MoCItemFood("turtleraw", 2, 0.3F, false);
//    public static final MoCItemFood turtlesoup = new MoCItemTurtleSoup("turtlesoup", 6, 0.6F, false);
//
//    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
//    public static class RegistrationHandler {
//        /**
//         * Register this mod's {@link Item}s.
//         *
//         * @param event The event
//         */
//        @SubscribeEvent
//        public static void registerItems(final RegistryEvent.Register<Item> event) {
//            List<Item> items = new ArrayList(Arrays.asList(
//                    horsesaddle,
//                    sharkteeth,
//                    haystack,
//                    sugarlump,
//                    mocegg,
//                    bigcatclaw,
//                    whip,
//                    medallion,
//                    litterbox,
//                    woolball,
//                    petfood,
//                    hideCroc,
//                    plateCroc,
//                    helmetCroc,
//                    legsCroc,
//                    bootsCroc,
//                    fur,
//                    omelet,
//                    turtleraw,
//                    turtlesoup,
//                    staffPortal,
//                    staffTeleport,
//                    builderHammer,
//
//                    nunchaku,
//                    sai,
//                    bo,
//                    katana,
//                    sharksword,
//                    silversword,
//
//                    essencedarkness,
//                    essencefire,
//                    essenceundead,
//                    essencelight,
//
//                    amuletbone,
//                    amuletbonefull,
//                    amuletghost,
//                    amuletghostfull,
//                    amuletfairy,
//                    amuletfairyfull,
//                    amuletpegasus,
//                    amuletpegasusfull,
//                    fishnet,
//                    fishnetfull,
//                    petamulet,
//                    petamuletfull,
//
//                    chestFur,
//                    helmetFur,
//                    legsFur,
//                    bootsFur,
//
//                    heartdarkness,
//                    heartfire,
//                    heartundead,
//                    ostrichraw,
//                    ostrichcooked,
//                    unicornhorn,
//                    horsearmorcrystal,
//                    recordshuffle,
//
//                    animalHide,
//                    rawTurkey,
//                    cookedTurkey,
//                    chestHide,
//                    helmetHide,
//                    legsHide,
//                    bootsHide,
//                    ratRaw,
//                    ratCooked,
//                    ratBurger,
//
//                    chitinCave,
//                    chitinFrost,
//                    chitinNether,
//                    chitin,
//
//                    scorpSwordDirt,
//                    scorpSwordFrost,
//                    scorpSwordCave,
//                    scorpSwordNether,
//
//                    scorpPlateDirt,
//                    scorpHelmetDirt,
//                    scorpLegsDirt,
//                    scorpBootsDirt,
//                    scorpPlateFrost,
//                    scorpHelmetFrost,
//                    scorpLegsFrost,
//                    scorpBootsFrost,
//                    scorpPlateNether,
//                    scorpHelmetNether,
//                    scorpLegsNether,
//                    scorpBootsNether,
//                    scorpHelmetCave,
//                    scorpPlateCave,
//                    scorpLegsCave,
//                    scorpBootsCave,
//
//                    scorpStingDirt,
//                    scorpStingFrost,
//                    scorpStingCave,
//                    scorpStingNether,
//
//                    tusksWood,
//                    tusksIron,
//                    tusksDiamond,
//                    elephantChest,
//                    elephantGarment,
//                    elephantHarness,
//                    elephantHowdah,
//                    mammothPlatform,
//
//                    scrollFreedom,
//                    scrollOfSale,
//                    scrollOfOwner,
//                    crabraw,
//                    crabcooked
//            ));
//
//            final IForgeRegistry<Item> registry = event.getRegistry();
//
//            for (int i = 0; i < 16; i++) {
//                String s = EnumDyeColor.byMetadata(i).getUnlocalizedName().toLowerCase();
//                if (s.equalsIgnoreCase("lightBlue")) s = "light_blue";
//                kittybed[i] = new MoCItemKittyBed("kittybed_" + s, i);
//                registry.register(kittybed[i]);
//                if (!MoCreatures.isServer()) {
//                    ModelLoader.setCustomModelResourceLocation(kittybed[i], 0, new ModelResourceLocation(MoCConstants.MOD_PREFIX + kittybed[i].getUnlocalizedName().replace("item.",  ""), "inventory"));
//                }
//            }
//
//            for (final Item item : items) {
//                registry.register(item);
//                ITEMS.add(item);
//                if (!MoCreatures.isServer()) {
//                    ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(MoCConstants.MOD_PREFIX + item.getUnlocalizedName().replace("item.",  ""), "inventory"));
//                }
//                if (item instanceof MoCItemEgg) {
//                    for (int i = 0; i < 91; i++) {
//                        if (!MoCreatures.isServer()) {
//                            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(MoCConstants.MOD_PREFIX + "mocegg", "inventory"));
//                        }
//                    }
//                }
//            }
//        }
//    }
}
