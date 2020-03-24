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
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.DyeColor;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
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

    static ArmorMaterial scorpARMOR; // = EnumHelper.addArmorMaterial("crocARMOR", "crocARMOR", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
    static ArmorMaterial furARMOR; // = EnumHelper.addArmorMaterial("furARMOR", "furARMOR", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
    static ArmorMaterial hideARMOR; // = EnumHelper.addArmorMaterial("hideARMOR", "hideARMOR", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
    static ArmorMaterial scorpdARMOR; // = EnumHelper.addArmorMaterial("scorpdARMOR", "scorpdARMOR", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
    static ArmorMaterial scorpfARMOR; // = EnumHelper.addArmorMaterial("scorpfARMOR", "scorpdARMOR", 18, new int[] {2, 7, 6, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
    static ArmorMaterial scorpnARMOR; // = EnumHelper.addArmorMaterial("scorpnARMOR", "scorpdARMOR", 20, new int[] {3, 7, 6, 3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
    static ArmorMaterial scorpcARMOR; // = EnumHelper.addArmorMaterial("scorpcARMOR", "scorpdARMOR", 15, new int[] {2, 6, 5, 2}, 12, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);
    static ArmorMaterial silverARMOR; // = EnumHelper.addArmorMaterial("silverARMOR", "scorpdARMOR", 15, new int[] {2, 6, 5, 2}, 15, SoundEvents.ITEM_ARMOR_EQUIP_GENERIC, 0);

    public static final MoCItemRecord RECORDSHUFFLE = new MoCItemRecord(new Item.Properties(), MoCSoundEvents.ITEM_RECORD_SHUFFLING).setRegistryName("recordshuffle");
    public static final MoCItem HORSESADDLE = (MoCItem) new MoCItemHorseSaddle().setRegistryName("horsesaddle");

    public static final MoCItem SHARKTEETH = new MoCItem(new Item.Properties()).setRegistryName("sharkteeth");
    public static final MoCItem HAYSTACK = new MoCItemHayStack("haystack");
    public static final MoCItemSugarLump SUGARLUMP = new MoCItemSugarLump("sugarlump");
    public static final MoCItem MOCEGG = new MoCItemEgg("mocegg");
    public static final MoCItem BIGCATCLAW = new MoCItem("bigcatclaw");
    public static final MoCItem WHIP = new MoCItemWhip("whip");
    public static final MoCItem STAFFPORTAL = new ItemStaffPortal("staffportal");
    public static final MoCItem STAFFTELEPORT = new ItemStaffTeleport("staffteleport");

    public static final MoCItem MEDALLION = new MoCItem("medallion");
    public static final MoCItemKittyBed[] KITTYBED = new MoCItemKittyBed[16];
    public static final MoCItem LITTERBOX = new MoCItemLitterBox("kittylitter");
    public static final MoCItem WOOLBALL = new MoCItem("woolball");

    public static final MoCItem PETFOOD = new MoCItem("petfood");
    public static final MoCItem BUILDERHAMMER = new ItemBuilderHammer("builderhammer");

    public static final MoCItem HIDECROC = new MoCItem("reptilehide");
    public static final MoCItem FUR = new MoCItem("fur");

    public static final MoCItem ESSENCEDARKNESS = new MoCItem("essencedarkness");
    public static final MoCItem ESSENCEFIRE = new MoCItem("essencefire");
    public static final MoCItem ESSENCEUNDEAD = new MoCItem("essenceundead");
    public static final MoCItem ESSENCELIGHT = new MoCItem("essencelight");

    public static final MoCItem AMULETBONE = new MoCItemHorseAmulet("amuletbone");
    public static final MoCItem AMULETBONEFULL = new MoCItemHorseAmulet("amuletbonefull");
    public static final MoCItem AMULETGHOST = new MoCItemHorseAmulet("amuletghost");
    public static final MoCItem AMULETGHOSTFULL = new MoCItemHorseAmulet("amuletghostfull");
    public static final MoCItem AMULETFAIRY = new MoCItemHorseAmulet("amuletfairy");
    public static final MoCItem AMULETFAIRYFULL = new MoCItemHorseAmulet("amuletfairyfull");
    public static final MoCItem AMULETPEGASUS = new MoCItemHorseAmulet("amuletpegasus");
    public static final MoCItem AMULETPEGASUSFULL = new MoCItemHorseAmulet("amuletpegasusfull");
    public static final MoCItem FISHNET = new MoCItemPetAmulet("fishnet");
    public static final MoCItem FISHNETFULL = new MoCItemPetAmulet("fishnetfull");
    public static final MoCItem PETAMULET = new MoCItemPetAmulet("petamulet", 1);
    public static final MoCItem PETAMULETFULL = new MoCItemPetAmulet("petamuletfull", 1);

    public static final MoCItem HEARTDARKNESS = new MoCItem("heartdarkness");
    public static final MoCItem HEARTFIRE = new MoCItem("heartfire");
    public static final MoCItem HEARTUNDEAD = new MoCItem("heartundead");
    public static final MoCItem UNICORNHORN = new MoCItem("unicornhorn");

    public static final MoCItem HORSEARMORCRYSTAL = new MoCItem("horsearmorcrystal");

    public static final MoCItem ANIMALHIDE = new MoCItem("hide");
    public static final MoCItem CHITINCAVE = new MoCItem("chitinblack");
    public static final MoCItem CHITINFROST = new MoCItem("chitinfrost");
    public static final MoCItem CHITINNETHER = new MoCItem("chitinnether");
    public static final MoCItem CHITIN = new MoCItem("chitin");

    // Weapons
    public static final MoCItemSword NUNCHAKU = new MoCItemSword("nunchaku", Item.ToolMaterial.IRON);
    public static final MoCItemSword SAI = new MoCItemSword("sai", Item.ToolMaterial.IRON);
    public static final MoCItemSword BO = new MoCItemSword("bo", Item.ToolMaterial.IRON);
    public static final MoCItemSword KATANA = new MoCItemSword("katana", Item.ToolMaterial.IRON);
    public static final MoCItemSword SHARKSWORD = new MoCItemSword("sharksword", Item.ToolMaterial.IRON);
    public static final MoCItemSword SILVERSWORD = new MoCItemSword("silversword", EnumHelper.addToolMaterial("SILVER", 0, 250, 6.0F, 4, 15));

    public static final MoCItemSword SCORPSWORDCAVE = new MoCItemSword("scorpswordcave", ToolMaterial.IRON, 4, false);
    public static final MoCItemSword SCORPSWORDFROST = new MoCItemSword("scorpswordfrost", ToolMaterial.IRON, 2, false);
    public static final MoCItemSword SCORPSWORDNETHER = new MoCItemSword("scorpswordnether", ToolMaterial.IRON, 3, false);
    public static final MoCItemSword SCORPSWORDDIRT = new MoCItemSword("scorpsworddirt", ToolMaterial.IRON, 1, false);
    public static final MoCItemWeapon SCORPSTINGCAVE = new MoCItemWeapon("scorpstingcave", ToolMaterial.GOLD, 4, true);
    public static final MoCItemWeapon SCORPSTINGFROST = new MoCItemWeapon("scorpstingfrost", ToolMaterial.GOLD, 2, true);
    public static final MoCItemWeapon SCORPSTINGNETHER = new MoCItemWeapon("scorpstingnether", ToolMaterial.GOLD, 3, true);
    public static final MoCItemWeapon SCORPSTINGDIRT = new MoCItemWeapon("scorpstingdirt", ToolMaterial.GOLD, 1, true);

    public static final MoCItemWeapon TUSKSWOOD = new MoCItemWeapon("tuskswood", ToolMaterial.WOOD);
    public static final MoCItemWeapon TUSKSIRON = new MoCItemWeapon("tusksiron", ToolMaterial.IRON);
    public static final MoCItemWeapon TUSKSDIAMOND = new MoCItemWeapon("tusksdiamond", ToolMaterial.DIAMOND);

    // Armors
    public static final MoCItemArmor PLATECROC = new MoCItemArmor("reptileplate", scorpARMOR, 4, EntityEquipmentSlot.CHEST);
    public static final MoCItemArmor HELMETCROC = new MoCItemArmor("reptilehelmet", scorpARMOR, 4, EntityEquipmentSlot.HEAD);
    public static final MoCItemArmor LEGSCROC = new MoCItemArmor("reptilelegs", scorpARMOR, 4, EntityEquipmentSlot.LEGS);
    public static final MoCItemArmor BOOTSCROC = new MoCItemArmor("reptileboots", scorpARMOR, 4, EntityEquipmentSlot.FEET);

    public static final MoCItemArmor CHESTFUR = new MoCItemArmor("furchest", furARMOR, 4, EntityEquipmentSlot.CHEST);
    public static final MoCItemArmor HELMETFUR = new MoCItemArmor("furhelmet", furARMOR, 4, EntityEquipmentSlot.HEAD);
    public static final MoCItemArmor LEGSFUR = new MoCItemArmor("furlegs", furARMOR, 4, EntityEquipmentSlot.LEGS);
    public static final MoCItemArmor BOOTSFUR = new MoCItemArmor("furboots", furARMOR, 4, EntityEquipmentSlot.FEET);

    public static final MoCItemArmor CHESTHIDE = new MoCItemArmor("hidechest", hideARMOR, 4, EntityEquipmentSlot.CHEST);
    public static final MoCItemArmor HELMETHIDE = new MoCItemArmor("hidehelmet", hideARMOR, 4, EntityEquipmentSlot.HEAD);
    public static final MoCItemArmor LEGSHIDE = new MoCItemArmor("hidelegs", hideARMOR, 4, EntityEquipmentSlot.LEGS);
    public static final MoCItemArmor BOOTSHIDE = new MoCItemArmor("hideboots", hideARMOR, 4, EntityEquipmentSlot.FEET);

    public static final MoCItemArmor SCORPPLATEDIRT = new MoCItemArmor("scorpplatedirt", scorpARMOR, 4, EntityEquipmentSlot.CHEST);
    public static final MoCItemArmor SCORPHELMETDIRT = new MoCItemArmor("scorphelmetdirt", scorpARMOR, 4, EntityEquipmentSlot.HEAD);
    public static final MoCItemArmor SCORPLEGSDIRT = new MoCItemArmor("scorplegsdirt", scorpARMOR, 4, EntityEquipmentSlot.LEGS);
    public static final MoCItemArmor SCORPBOOTSDIRT = new MoCItemArmor("scorpbootsdirt", scorpARMOR, 4, EntityEquipmentSlot.FEET);

    public static final MoCItemArmor SCORPPLATEFROST = new MoCItemArmor("scorpplatefrost", scorpARMOR, 4, EntityEquipmentSlot.CHEST);
    public static final MoCItemArmor SCORPHELMETFROST = new MoCItemArmor("scorphelmetfrost", scorpARMOR, 4, EntityEquipmentSlot.HEAD);
    public static final MoCItemArmor SCORPLEGSFROST = new MoCItemArmor("scorplegsfrost", scorpARMOR, 4, EntityEquipmentSlot.LEGS);
    public static final MoCItemArmor SCORPBOOTSFROST = new MoCItemArmor("scorpbootsfrost", scorpARMOR, 4, EntityEquipmentSlot.FEET);

    public static final MoCItemArmor SCORPPLATECAVE = new MoCItemArmor("scorpplatecave", scorpARMOR, 4, EntityEquipmentSlot.CHEST);
    public static final MoCItemArmor SCORPHELMETCAVE = new MoCItemArmor("scorphelmetcave", scorpARMOR, 4, EntityEquipmentSlot.HEAD);
    public static final MoCItemArmor SCORPLEGSCAVE = new MoCItemArmor("scorplegscave", scorpARMOR, 4, EntityEquipmentSlot.LEGS);
    public static final MoCItemArmor SCORPBOOTSCAVE = new MoCItemArmor("scorpbootscave", scorpARMOR, 4, EntityEquipmentSlot.FEET);

    public static final MoCItemArmor SCORPPLATENETHER = new MoCItemArmor("scorpplatenether", scorpARMOR, 4, EntityEquipmentSlot.CHEST);
    public static final MoCItemArmor SCORPHELMETNETHER = new MoCItemArmor("scorphelmetnether", scorpARMOR, 4, EntityEquipmentSlot.HEAD);
    public static final MoCItemArmor SCORPLEGSNETHER = new MoCItemArmor("scorplegsnether", scorpARMOR, 4, EntityEquipmentSlot.LEGS);
    public static final MoCItemArmor SCORPBOOTSNETHER = new MoCItemArmor("scorpbootsnether", scorpARMOR, 4, EntityEquipmentSlot.FEET);

    public static final MoCItem ELEPHANTHARNESS = new MoCItem("elephantharness");
    public static final MoCItem ELEPHANTCHEST = new MoCItem("elephantchest");
    public static final MoCItem ELEPHANTGARMENT = new MoCItem("elephantgarment");
    public static final MoCItem ELEPHANTHOWDAH = new MoCItem("elephanthowdah");
    public static final MoCItem MAMMOTHPLATFORM = new MoCItem("mammothplatform");

    public static final MoCItem SCROLLFREEDOM = new MoCItem("scrolloffreedom");
    public static final MoCItem SCROLLOFSALE = new MoCItem("scrollofsale");
    public static final MoCItem SCROLLOFOWNER = new MoCItem("scrollofowner");

    // foods
    public static final MoCItemFood COOKEDTURKEY = new MoCItemFood("turkeycooked", 8, 0.6F, false);
    public static final MoCItemFood CRABRAW = (MoCItemFood) new MoCItemFood("crabraw", 2, 0.3F, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30, 0), 0.8F);
    public static final MoCItemFood CRABCOOKED = new MoCItemFood("crabcooked", 6, 0.6F, false);
    public static final MoCItemFood OMELET = new MoCItemFood("omelet", 4, 0.6F, false);
    public static final MoCItemFood OSTRICHRAW = (MoCItemFood) new MoCItemFood("ostrichraw", 2, 0.3F, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30, 0), 0.8F);
    public static final MoCItemFood OSTRICHCOOKED = new MoCItemFood("ostrichcooked", 6, 0.6F, false);
    public static final MoCItemFood RATBURGER = new MoCItemFood("ratburger", 8, 0.6F, false);
    public static final MoCItemFood RATCOOKED = new MoCItemFood("ratcooked", 4, 0.6F, false);
    public static final MoCItemFood RATRAW = (MoCItemFood) new MoCItemFood("ratraw", 2, 0.3F, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30, 0), 0.8F);
    public static final MoCItemFood RAWTURKEY = (MoCItemFood) new MoCItemFood("turkeyraw", 3, 0.3F, false).setPotionEffect(new PotionEffect(MobEffects.HUNGER, 30, 0), 0.8F);
    public static final MoCItemFood TURTLERAW = new MoCItemFood("turtleraw", 2, 0.3F, false);
    public static final MoCItemFood TURTLESOUP = new MoCItemTurtleSoup("turtlesoup", 6, 0.6F, false);

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
                    HORSESADDLE,
                    SHARKTEETH,
                    HAYSTACK,
                    SUGARLUMP,
                    MOCEGG,
                    BIGCATCLAW,
                    WHIP,
                    MEDALLION,
                    LITTERBOX,
                    WOOLBALL,
                    PETFOOD,
                    HIDECROC,
                    PLATECROC,
                    HELMETCROC,
                    LEGSCROC,
                    BOOTSCROC,
                    FUR,
                    OMELET,
                    TURTLERAW,
                    TURTLESOUP,
                    STAFFPORTAL,
                    STAFFTELEPORT,
                    BUILDERHAMMER,

                    NUNCHAKU,
                    SAI,
                    BO,
                    KATANA,
                    SHARKSWORD,
                    SILVERSWORD,

                    ESSENCEDARKNESS,
                    ESSENCEFIRE,
                    ESSENCEUNDEAD,
                    ESSENCELIGHT,

                    AMULETBONE,
                    AMULETBONEFULL,
                    AMULETGHOST,
                    AMULETGHOSTFULL,
                    AMULETFAIRY,
                    AMULETFAIRYFULL,
                    AMULETPEGASUS,
                    AMULETPEGASUSFULL,
                    FISHNET,
                    FISHNETFULL,
                    PETAMULET,
                    PETAMULETFULL,

                    CHESTFUR,
                    HELMETFUR,
                    LEGSFUR,
                    BOOTSFUR,

                    HEARTDARKNESS,
                    HEARTFIRE,
                    HEARTUNDEAD,
                    OSTRICHRAW,
                    OSTRICHCOOKED,
                    UNICORNHORN,
                    HORSEARMORCRYSTAL,
                    RECORDSHUFFLE,

                    ANIMALHIDE,
                    RAWTURKEY,
                    COOKEDTURKEY,
                    CHESTHIDE,
                    HELMETHIDE,
                    LEGSHIDE,
                    BOOTSHIDE,
                    RATRAW,
                    RATCOOKED,
                    RATBURGER,

                    CHITINCAVE,
                    CHITINFROST,
                    CHITINNETHER,
                    CHITIN,

                    SCORPSWORDDIRT,
                    SCORPSWORDFROST,
                    SCORPSWORDCAVE,
                    SCORPSWORDNETHER,

                    SCORPPLATEDIRT,
                    SCORPHELMETDIRT,
                    SCORPLEGSDIRT,
                    SCORPBOOTSDIRT,
                    SCORPPLATEFROST,
                    SCORPHELMETFROST,
                    SCORPLEGSFROST,
                    SCORPBOOTSFROST,
                    SCORPPLATENETHER,
                    SCORPHELMETNETHER,
                    SCORPLEGSNETHER,
                    SCORPBOOTSNETHER,
                    SCORPHELMETCAVE,
                    SCORPPLATECAVE,
                    SCORPLEGSCAVE,
                    SCORPBOOTSCAVE,

                    SCORPSTINGDIRT,
                    SCORPSTINGFROST,
                    SCORPSTINGCAVE,
                    SCORPSTINGNETHER,

                    TUSKSWOOD,
                    TUSKSIRON,
                    TUSKSDIAMOND,
                    ELEPHANTCHEST,
                    ELEPHANTGARMENT,
                    ELEPHANTHARNESS,
                    ELEPHANTHOWDAH,
                    MAMMOTHPLATFORM,

                    SCROLLFREEDOM,
                    SCROLLOFSALE,
                    SCROLLOFOWNER,
                    CRABRAW,
                    CRABCOOKED
            ));

            final IForgeRegistry<Item> registry = event.getRegistry();

            for (int i = 0; i < 16; i++) {
                String s = DyeColor.byMetadata(i).getUnlocalizedName().toLowerCase();
                if (s.equalsIgnoreCase("lightBlue")) s = "light_blue";
                kittybed[i] = new MoCItemKittyBed("kittybed_" + s, i);
                registry.register(kittybed[i]);
                if (!MoCreatures.isServer()) {
                    ModelLoader.setCustomModelResourceLocation(kittybed[i], 0, new ModelResourceLocation(MoCConstants.MOD_PREFIX + kittybed[i].getUnlocalizedName().replace("item.",  ""), "inventory"));
                }
            }

            for (final Item item : items) {
                registry.register(item);
                ITEMS.add(item);
                if (!MoCreatures.isServer()) {
                    ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(MoCConstants.MOD_PREFIX + item.getUnlocalizedName().replace("item.",  ""), "inventory"));
                }
                if (item instanceof MoCItemEgg) {
                    for (int i = 0; i < 91; i++) {
                        if (!MoCreatures.isServer()) {
                            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(MoCConstants.MOD_PREFIX + "mocegg", "inventory"));
                        }
                    }
                }
            }
        }
    }
}
