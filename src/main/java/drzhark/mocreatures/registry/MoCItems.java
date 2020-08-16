package drzhark.mocreatures.registry;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCItemGroup;
import drzhark.mocreatures.item.*;
import drzhark.mocreatures.util.MoCArmorMaterial;
import drzhark.mocreatures.util.MoCItemTier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ObjectHolder(MoCConstants.MOD_ID)
public class MoCItems {
    public static final Item RECORDSHUFFLE = null;
    public static final Item HORSESADDLE = null;

    public static final Item SHARKTEETH = null;
    public static final Item HAYSTACK = null;
    public static final Item SUGARLUMP = null;
    public static final Item MOCEGG = null;
    public static final Item BIGCATCLAW = null;
    public static final Item WHIP = null;
    public static final Item STAFFPORTAL = null;
    public static final Item STAFFTELEPORT = null;

    public static final Item MEDALLION = null;
    //public static final MoCItemKittyBed[] KITTYBED = new MoCItemKittyBed[16]; TODO: Make the kittybed work. 99% sure I'll register different kittybed items for each color.
    public static final Item LITTERBOX = null;
    public static final Item WOOLBALL = null;

    public static final Item PETFOOD = null;
    public static final Item BUILDERHAMMER = null;

    public static final Item HIDECROC = null;
    public static final Item FUR = null;

    public static final Item ESSENCEDARKNESS = null;
    public static final Item ESSENCEFIRE = null;
    public static final Item ESSENCEUNDEAD = null;
    public static final Item ESSENCELIGHT = null;

    public static final Item HEARTDARKNESS = null;
    public static final Item HEARTFIRE = null;
    public static final Item HEARTUNDEAD = null;
    public static final Item UNICORNHORN = null;

    public static final Item AMULETBONE = null;
    public static final Item AMULETBONEFULL = null;
    public static final Item AMULETGHOST = null;
    public static final Item AMULETGHOSTFULL = null;
    public static final Item AMULETFAIRY = null;
    public static final Item AMULETFAIRYFULL = null;
    public static final Item AMULETPEGASUS = null;
    public static final Item AMULETPEGASUSFULL = null;
    public static final Item FISHNET = null;
    public static final Item FISHNETFULL = null;
    public static final Item PETAMULET = null;
    public static final Item PETAMULETFULL = null;

    public static final Item HORSEARMORCRYSTAL = null;

    public static final Item ANIMALHIDE = null;
    public static final Item CHITINCAVE = null;
    public static final Item CHITINFROST = null;
    public static final Item CHITINNETHER = null;
    public static final Item CHITIN = null;

    // Weapons
    public static final Item NUNCHAKU = null;
    public static final Item SAI = null;
    public static final Item BO = null;
    public static final Item KATANA = null;
    public static final Item SHARKSWORD = null;
    public static final Item SILVERSWORD = null;

    public static final Item SCORPSWORDCAVE = null;
    public static final Item SCORPSWORDFROST = null;
    public static final Item SCORPSWORDNETHER = null;
    public static final Item SCORPSWORDDIRT = null;
    public static final Item SCORPSTINGCAVE = null;
    public static final Item SCORPSTINGFROST = null;
    public static final Item SCORPSTINGNETHER = null;
    public static final Item SCORPSTINGDIRT = null;

    public static final Item TUSKSWOOD = null;
    public static final Item TUSKSIRON = null;
    public static final Item TUSKSDIAMOND = null;

    // Armors
    public static final Item PLATECROC = null;
    public static final Item HELMETCROC = null;
    public static final Item LEGSCROC = null;
    public static final Item BOOTSCROC = null;

    public static final Item CHESTFUR = null;
    public static final Item HELMETFUR = null;
    public static final Item LEGSFUR = null;
    public static final Item BOOTSFUR = null;

    public static final Item CHESTHIDE = null;
    public static final Item HELMETHIDE = null;
    public static final Item LEGSHIDE = null;
    public static final Item BOOTSHIDE = null;

    public static final Item SCORPPLATEDIRT = null;
    public static final Item SCORPHELMETDIRT = null;
    public static final Item SCORPLEGSDIRT = null;
    public static final Item SCORPBOOTSDIRT = null;

    public static final Item SCORPPLATEFROST = null;
    public static final Item SCORPHELMETFROST = null;
    public static final Item SCORPLEGSFROST = null;
    public static final Item SCORPBOOTSFROST = null;

    public static final Item SCORPPLATECAVE = null;
    public static final Item SCORPHELMETCAVE = null;
    public static final Item SCORPLEGSCAVE = null;
    public static final Item SCORPBOOTSCAVE = null;

    public static final Item SCORPPLATENETHER = null;
    public static final Item SCORPHELMETNETHER = null;
    public static final Item SCORPLEGSNETHER = null;
    public static final Item SCORPBOOTSNETHER = null;

    public static final Item ELEPHANTHARNESS = null;
    public static final Item ELEPHANTCHEST = null;
    public static final Item ELEPHANTGARMENT = null;
    public static final Item ELEPHANTHOWDAH = null;
    public static final Item MAMMOTHPLATFORM = null;

    public static final Item SCROLLOFFREEDOM = null;
    public static final Item SCROLLOFSALE = null;
    public static final Item SCROLLOFOWNER = null;

    // foods
    public static final Item TURKEY_RAW = null;
    public static final Item TURKEY_COOKED = null;
    public static final Item CRAB_RAW = null;
    public static final Item CRAB_COOKED = null;
    public static final Item OMELET = null;
    public static final Item OSTRICH_RAW = null;
    public static final Item OSTRICH_COOKED = null;
    public static final Item RAT_BURGER = null;
    public static final Item RAT_COOKED = null;
    public static final Item RAT_RAW = null;
    public static final Item TURTLE_RAW = null;
    public static final Item TURTLE_SOUP = null;

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {
        /**
         * Register this mod's {@link Item}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            List<Item> items = new ArrayList<>(Arrays.asList(
                    new MoCItemHorseSaddle(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(32)).setRegistryName("horsesaddle"),                    //HORSESADLLE
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("sharkteeth"),           //SHARKTEETH
                    new MoCItemHayStack(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("haystack"),     //HAYSTACK
                    new MoCItemSugarLump(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("sugarlump"),   //SUGARLUMP
                    new MoCItemEgg(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("mocegg"),            //MOCEGG
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("bigcatclaw"),           //BIGCATCLAW
                    new MoCItemWhip(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(1).maxDamage(24)).setRegistryName("whip"),                                  //WHIP
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("medallion"),            //MEDALLION
                    new MoCItemLitterBox(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(16)).setRegistryName("litterbox"),                        //LITTERBOX
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("woolball"),             //WOOLBALL
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("petfood"),              //PETFOOD
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("hidecroc"),             //HIDECROC
                    new MoCItemArmor(MoCArmorMaterial.CROCARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("platecroc"),
                    new MoCItemArmor(MoCArmorMaterial.CROCARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("helmetcroc"),
                    new MoCItemArmor(MoCArmorMaterial.CROCARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("legscroc"),
                    new MoCItemArmor(MoCArmorMaterial.CROCARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("bootscroc"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("fur"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC).food(MoCFoods.OMELET)).setRegistryName("omelet"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC).food(MoCFoods.TURTLE_RAW)).setRegistryName("turtle_raw"),
                    new MoCItemTurtleSoup(new Item.Properties().group(MoCItemGroup.TABMOC).food(MoCFoods.TURTLE_SOUP)).setRegistryName("turtle_soup"),
                    new ItemStaffPortal(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(1).maxDamage(3)).setRegistryName("staffportal"),                       //STAFFPORTAL
                    new ItemStaffTeleport(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(1).maxDamage(128)).setRegistryName("staffteleport"),                   //STAFFTELEPORT
                    new ItemBuilderHammer(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(1).maxDamage(2048)).setRegistryName("builderhammer"),                   //BUILDERHAMMER

                    new MoCItemSword(ItemTier.IRON, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("nunchaku"),
                    new MoCItemSword(ItemTier.IRON, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("sai"),
                    new MoCItemSword(ItemTier.IRON, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("bo"),
                    new MoCItemSword(ItemTier.IRON, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("katana"),
                    new MoCItemSword(ItemTier.IRON, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("sharksword"),
                    new MoCItemSword(MoCItemTier.SILVER, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("silversword"),

                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("essencedarkness"),      //ESSENCEDARKNESS
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("essencefire"),          //ESSENCEFIRE
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("essenceundead"),        //ESSENCEUNDEAD
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("essencelight"),         //ESSENCELIGHT

                    new MoCItemHorseAmulet(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(1)).setRegistryName("amuletbone"),                     //AMULETBONE
                    new MoCItemHorseAmulet(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(1)).setRegistryName("amuletbonefull"),
                    new MoCItemHorseAmulet(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(1)).setRegistryName("amuletghost"),
                    new MoCItemHorseAmulet(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(1)).setRegistryName("amuletghostfull"),
                    new MoCItemHorseAmulet(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(1)).setRegistryName("amuletfairy"),
                    new MoCItemHorseAmulet(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(1)).setRegistryName("amuletfairyfull"),
                    new MoCItemHorseAmulet(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(1)).setRegistryName("amuletpegasus"),
                    new MoCItemHorseAmulet(new Item.Properties().group(MoCItemGroup.TABMOC).maxStackSize(1)).setRegistryName("amuletpegasusfull"),
                    new MoCItemPetAmulet(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("fishnet"),
                    new MoCItemPetAmulet(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("fishnetfull"),
                    new MoCItemPetAmulet(new Item.Properties().group(MoCItemGroup.TABMOC), 1).setRegistryName("petamulet"),
                    new MoCItemPetAmulet(new Item.Properties().group(MoCItemGroup.TABMOC), 1).setRegistryName("petamuletfull"),

                    new MoCItemArmor(MoCArmorMaterial.FURARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("chestfur"),
                    new MoCItemArmor(MoCArmorMaterial.FURARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("helmetfur"),
                    new MoCItemArmor(MoCArmorMaterial.FURARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("legsfur"),
                    new MoCItemArmor(MoCArmorMaterial.FURARMOR, 4, EquipmentSlotType.FEET, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("bootsfur"),

                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("heartdarkness"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("heartfire"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("heartundead"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("unicornhorn"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC).food(MoCFoods.OSTRICH_RAW)).setRegistryName("ostrich_raw"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC).food(MoCFoods.OSTRICH_COOKED)).setRegistryName("ostrich_cooked"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("horsearmorcrystal"),
                    new MoCItemRecord(0,  MoCSoundEvents.ITEM_RECORD_SHUFFLING, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("recordshuffle"),

                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("animalhide"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC).food(MoCFoods.TURKEY_RAW)).setRegistryName("turkey_raw"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC).food(MoCFoods.TURKEY_COOKED)).setRegistryName("turkey_cooked"),
                    new MoCItemArmor(MoCArmorMaterial.HIDEARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("chesthide"),
                    new MoCItemArmor(MoCArmorMaterial.HIDEARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("helmethide"),
                    new MoCItemArmor(MoCArmorMaterial.HIDEARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("legshide"),
                    new MoCItemArmor(MoCArmorMaterial.HIDEARMOR, 4, EquipmentSlotType.FEET, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("bootshide"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC).food(MoCFoods.RAT_RAW)).setRegistryName("rat_raw"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC).food(MoCFoods.RAT_COOKED)).setRegistryName("rat_cooked"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC).food(MoCFoods.RAT_BURGER)).setRegistryName("rat_burger"),

                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("chitincave"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("chitinfrost"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("chitinnether"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("chitin"),

                    new MoCItemSword(ItemTier.IRON, 0, 0, 1, false, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpsworddirt"),
                    new MoCItemSword(ItemTier.IRON, 0, 0, 2, false, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpswordfrost"),
                    new MoCItemSword(ItemTier.IRON, 0, 0, 3, false, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpswordnether"),
                    new MoCItemSword(ItemTier.IRON, 0, 0, 4, false, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpswordcave"),

                    new MoCItemArmor(MoCArmorMaterial.SCORPDARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpplatedirt"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPDARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorphelmetdirt"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPDARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorplegsdirt"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPDARMOR, 4, EquipmentSlotType.FEET, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpbootsdirt"),

                    new MoCItemArmor(MoCArmorMaterial.SCORPFARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorphelmetfrost"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPFARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpplatefrost"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPFARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorplegsfrost"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPFARMOR, 4, EquipmentSlotType.FEET, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpbootsfrost"),

                    new MoCItemArmor(MoCArmorMaterial.SCORPNARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorphelmetnether"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPNARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpplatenether"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPNARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorplegsnether"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPNARMOR, 4, EquipmentSlotType.FEET, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpbootsnether"),

                    new MoCItemArmor(MoCArmorMaterial.SCORPCARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorphelmetcave"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPCARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpplatecave"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPCARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorplegscave"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPCARMOR, 4, EquipmentSlotType.FEET, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpbootscave"),

                    new MoCItemWeapon(ItemTier.GOLD, 1, true, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpstingdirt"),
                    new MoCItemWeapon(ItemTier.GOLD, 2, true, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpstingfrost"),
                    new MoCItemWeapon(ItemTier.GOLD, 3, true, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpstingnether"),
                    new MoCItemWeapon(ItemTier.GOLD, 4, true, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scorpstingcave"),

                    new MoCItemWeapon(ItemTier.WOOD, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("tuskswood"),
                    new MoCItemWeapon(ItemTier.IRON, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("tusksiron"),
                    new MoCItemWeapon(ItemTier.DIAMOND, new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("tusksdiamond"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("elephantchest"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("elephantgarment"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("elephantharness"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("elephanthowdah"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("mammothplatform"),

                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scrolloffreedom"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scrollofsale"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC)).setRegistryName("scrollofowner"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC).food(MoCFoods.CRAB_RAW)).setRegistryName("crab_raw"),
                    new MoCItem(new Item.Properties().group(MoCItemGroup.TABMOC).food(MoCFoods.CRAB_COOKED)).setRegistryName("crab_cooked")
            ));

            final IForgeRegistry<Item> registry = event.getRegistry();
            for(Item item : items) {
                registry.register(item);
            }
        }
    }
}
