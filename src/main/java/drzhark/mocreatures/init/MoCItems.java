package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
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
import drzhark.mocreatures.util.MoCArmorMaterial;
import drzhark.mocreatures.util.MoCItemTier;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.DyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@ObjectHolder(MoCConstants.MOD_ID)
public class MoCItems {

    public static final Set<Item> ITEMS = new HashSet<>();


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

    public static final Item HEARTDARKNESS = null;
    public static final Item HEARTFIRE = null;
    public static final Item HEARTUNDEAD = null;
    public static final Item UNICORNHORN = null;

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

    public static final Item SCROLLFREEDOM = null;
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

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
    public static class RegistrationHandler {
        /**
         * Register this mod's {@link Item}s.
         *
         * @param event The event
         */
        @SubscribeEvent
        public static void registerItems(final RegistryEvent.Register<Item> event) {
            List<Item> items = new ArrayList<>(Arrays.asList(
                    new MoCItemHorseSaddle().setRegistryName("horsesaddle"),                    //HORSESADLLE
                    new MoCItem(new Item.Properties()).setRegistryName("sharkteeth"),           //SHARKTEETH
                    new MoCItemHayStack(new Item.Properties()).setRegistryName("haystack"),     //HAYSTACK
                    new MoCItemSugarLump(new Item.Properties()).setRegistryName("sugarlump"),   //SUGARLUMP
                    new MoCItemEgg(new Item.Properties()).setRegistryName("mocegg"),            //MOCEGG
                    new MoCItem(new Item.Properties()).setRegistryName("bigcatclaw"),           //BIGCATCLAW
                    new MoCItemWhip().setRegistryName("whip"),                                  //WHIP
                    new MoCItem(new Item.Properties()).setRegistryName("medallion"),            //MEDALLION
                    new MoCItemLitterBox().setRegistryName("litterbox"),                        //LITTERBOX
                    new MoCItem(new Item.Properties()).setRegistryName("woolball"),             //WOOLBALL
                    new MoCItem(new Item.Properties()).setRegistryName("petfood"),              //PETFOOD
                    new MoCItem(new Item.Properties()).setRegistryName("hidecroc"),             //HIDECROC
                    new MoCItemArmor(MoCArmorMaterial.CROCARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()).setRegistryName("platecroc"),
                    new MoCItemArmor(MoCArmorMaterial.CROCARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()).setRegistryName("helmetcroc"),
                    new MoCItemArmor(MoCArmorMaterial.CROCARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()).setRegistryName("legscroc"),
                    new MoCItemArmor(MoCArmorMaterial.CROCARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()).setRegistryName("bootscroc"),
                    new MoCItem(new Item.Properties()).setRegistryName("fur"),
                    new MoCItem(new Item.Properties().food(MoCFoods.OMELET)).setRegistryName("omelet"),
                    new MoCItem(new Item.Properties().food(MoCFoods.TURTLE_RAW)).setRegistryName("turtle_raw"),
                    new MoCItem(new Item.Properties().food(MoCFoods.TURTLE_SOUP)).setRegistryName("turtle_soup"),
                    new ItemStaffPortal().setRegistryName("staffportal"),                       //STAFFPORTAL
                    new ItemStaffTeleport().setRegistryName("staffteleport"),                   //STAFFTELEPORT
                    new ItemBuilderHammer().setRegistryName("builderhammer"),                   //BUILDERHAMMER

                    new MoCItemSword(ItemTier.IRON, new Item.Properties()).setRegistryName("nunchaku"),
                    new MoCItemSword(ItemTier.IRON, new Item.Properties()).setRegistryName("sai"),
                    new MoCItemSword(ItemTier.IRON, new Item.Properties()).setRegistryName("bo"),
                    new MoCItemSword(ItemTier.IRON, new Item.Properties()).setRegistryName("katana"),
                    new MoCItemSword(ItemTier.IRON, new Item.Properties()).setRegistryName("sharksword"),
                    new MoCItemSword(MoCItemTier.SILVER, new Item.Properties()).setRegistryName("silversword"),

                    new MoCItem(new Item.Properties()).setRegistryName("essencedarkness"),      //ESSENCEDARKNESS
                    new MoCItem(new Item.Properties()).setRegistryName("essencefire"),          //ESSENCEFIRE
                    new MoCItem(new Item.Properties()).setRegistryName("essenceundead"),        //ESSENCEUNDEAD
                    new MoCItem(new Item.Properties()).setRegistryName("essencelight"),         //ESSENCELIGHT

                    new MoCItemHorseAmulet().setRegistryName("amuletbone"),                     //AMULETBONE
                    new MoCItemHorseAmulet().setRegistryName("amuletbonefull"),
                    new MoCItemHorseAmulet().setRegistryName("amuletghost"),
                    new MoCItemHorseAmulet().setRegistryName("amuletghostfull"),
                    new MoCItemHorseAmulet().setRegistryName("amuletfairy"),
                    new MoCItemHorseAmulet().setRegistryName("amuletfairyfull"),
                    new MoCItemHorseAmulet().setRegistryName("amuletpegasus"),
                    new MoCItemHorseAmulet().setRegistryName("amuletpegasusfull"),
                    new MoCItemPetAmulet(new Item.Properties()).setRegistryName("fishnet"),
                    new MoCItemPetAmulet(new Item.Properties()).setRegistryName("fishnetfull"),
                    new MoCItemPetAmulet(new Item.Properties(), 1).setRegistryName("petamulet"),
                    new MoCItemPetAmulet(new Item.Properties(), 1).setRegistryName("petamuletfull"),

                    new MoCItemArmor(MoCArmorMaterial.FURARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()).setRegistryName("chestfur"),
                    new MoCItemArmor(MoCArmorMaterial.FURARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()).setRegistryName("helmetfur"),
                    new MoCItemArmor(MoCArmorMaterial.FURARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties()).setRegistryName("legsfur"),
                    new MoCItemArmor(MoCArmorMaterial.FURARMOR, 4, EquipmentSlotType.FEET, new Item.Properties()).setRegistryName("bootsfur"),

                    new MoCItem(new Item.Properties()).setRegistryName("heartdarkness"),
                    new MoCItem(new Item.Properties()).setRegistryName("heartfire"),
                    new MoCItem(new Item.Properties()).setRegistryName("heartundead"),
                    new MoCItem(new Item.Properties()).setRegistryName("unicornhorn"),
                    new MoCItem(new Item.Properties().food(MoCFoods.OSTRICH_RAW)).setRegistryName("ostrich_raw"),
                    new MoCItem(new Item.Properties().food(MoCFoods.OSTRICH_COOKED)).setRegistryName("ostrich_cooked"),
                    new MoCItem(new Item.Properties()).setRegistryName("horsearmorcrystal"),
                    new MoCItemRecord(0,  MoCSoundEvents.ITEM_RECORD_SHUFFLING, new Item.Properties()).setRegistryName("recordshuffle"),

                    new MoCItem(new Item.Properties()).setRegistryName("animalhide"),
                    new MoCItem(new Item.Properties().food(MoCFoods.TURKEY_RAW)).setRegistryName("turkey_raw"),
                    new MoCItem(new Item.Properties().food(MoCFoods.TURKEY_COOKED)).setRegistryName("turkey_cooked"),
                    new MoCItemArmor(MoCArmorMaterial.HIDEARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()).setRegistryName("chesthide"),
                    new MoCItemArmor(MoCArmorMaterial.HIDEARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()).setRegistryName("helmethide"),
                    new MoCItemArmor(MoCArmorMaterial.HIDEARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties()).setRegistryName("legshide"),
                    new MoCItemArmor(MoCArmorMaterial.HIDEARMOR, 4, EquipmentSlotType.FEET, new Item.Properties()).setRegistryName("bootshide"),
                    new MoCItem(new Item.Properties().food(MoCFoods.RAT_RAW)).setRegistryName("rat_raw"),
                    new MoCItem(new Item.Properties().food(MoCFoods.RAT_COOKED)).setRegistryName("rat_cooked"),
                    new MoCItem(new Item.Properties().food(MoCFoods.RAT_BURGER)).setRegistryName("rat_burger"),

                    new MoCItem(new Item.Properties()).setRegistryName("chitincave"),
                    new MoCItem(new Item.Properties()).setRegistryName("chitinfrost"),
                    new MoCItem(new Item.Properties()).setRegistryName("chitinnether"),
                    new MoCItem(new Item.Properties()).setRegistryName("chitin"),

                    new MoCItemSword(ItemTier.IRON, 0, 0, 1, false, new Item.Properties()).setRegistryName("scorpsworddirt"),
                    new MoCItemSword(ItemTier.IRON, 0, 0, 2, false, new Item.Properties()).setRegistryName("scorpswordfrost"),
                    new MoCItemSword(ItemTier.IRON, 0, 0, 3, false, new Item.Properties()).setRegistryName("scorpswordnether"),
                    new MoCItemSword(ItemTier.IRON, 0, 0, 4, false, new Item.Properties()).setRegistryName("scorpswordcave"),

                    new MoCItemArmor(MoCArmorMaterial.SCORPDARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()).setRegistryName("scorpplatedirt"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPDARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()).setRegistryName("scorphelmetdirt"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPDARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties()).setRegistryName("scorplegsdirt"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPDARMOR, 4, EquipmentSlotType.FEET, new Item.Properties()).setRegistryName("scorpbootsdirt"),

                    new MoCItemArmor(MoCArmorMaterial.SCORPFARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()).setRegistryName("scorphelmetfrost"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPFARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()).setRegistryName("scorpplatefrost"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPFARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties()).setRegistryName("scorplegsfrost"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPFARMOR, 4, EquipmentSlotType.FEET, new Item.Properties()).setRegistryName("scorpbootsfrost"),

                    new MoCItemArmor(MoCArmorMaterial.SCORPNARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()).setRegistryName("scorphelmetnether"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPNARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()).setRegistryName("scorpplatenether"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPNARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties()).setRegistryName("scorplegsnether"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPNARMOR, 4, EquipmentSlotType.FEET, new Item.Properties()).setRegistryName("scorpplatenether"),

                    new MoCItemArmor(MoCArmorMaterial.SCORPCARMOR, 4, EquipmentSlotType.HEAD, new Item.Properties()).setRegistryName("scorphelmetecave"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPCARMOR, 4, EquipmentSlotType.CHEST, new Item.Properties()).setRegistryName("scorpplatecave"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPCARMOR, 4, EquipmentSlotType.LEGS, new Item.Properties()).setRegistryName("scorplegscave"),
                    new MoCItemArmor(MoCArmorMaterial.SCORPCARMOR, 4, EquipmentSlotType.FEET, new Item.Properties()).setRegistryName("scorpbootscave"),

                    new MoCItemWeapon(ItemTier.GOLD, 1, true, new Item.Properties()).setRegistryName("scorpstingdirt"),
                    new MoCItemWeapon(ItemTier.GOLD, 2, true, new Item.Properties()).setRegistryName("scorpstingfrost"),
                    new MoCItemWeapon(ItemTier.GOLD, 3, true, new Item.Properties()).setRegistryName("scorpstingnether"),
                    new MoCItemWeapon(ItemTier.GOLD, 4, true, new Item.Properties()).setRegistryName("scorpstingcave"),

                    new MoCItemWeapon(ItemTier.WOOD, new Item.Properties()).setRegistryName("tuskswood"),
                    new MoCItemWeapon(ItemTier.IRON, new Item.Properties()).setRegistryName("tusksiron"),
                    new MoCItemWeapon(ItemTier.DIAMOND, new Item.Properties()).setRegistryName("tusksdiamond"),
                    new MoCItem(new Item.Properties()).setRegistryName("elephantchest"),
                    new MoCItem(new Item.Properties()).setRegistryName("elephantgarment"),
                    new MoCItem(new Item.Properties()).setRegistryName("elephantharness"),
                    new MoCItem(new Item.Properties()).setRegistryName("elephanthowdah"),
                    new MoCItem(new Item.Properties()).setRegistryName("mammothplatform"),

                    new MoCItem(new Item.Properties()).setRegistryName("scrollfreedom"),
                    new MoCItem(new Item.Properties()).setRegistryName("scrollofsale"),
                    new MoCItem(new Item.Properties()).setRegistryName("scrollofowner"),
                    new MoCItem(new Item.Properties().food(MoCFoods.CRAB_RAW)).setRegistryName("crab_raw"),
                    new MoCItem(new Item.Properties().food(MoCFoods.CRAB_COOKED)).setRegistryName("crab_cooked")
            ));

            final IForgeRegistry<Item> registry = event.getRegistry();

//            for (int i = 0; i < 16; i++) {
//                String s = DyeColor.byMetadata(i).getUnlocalizedName().toLowerCase();
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
        }
    }
}
