package drzhark.mocreatures.registry;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCItemGroup;
import drzhark.mocreatures.item.*;
import drzhark.mocreatures.util.MoCArmorMaterial;
import drzhark.mocreatures.util.MoCItemTier;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.dispenser.IBlockSource;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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

    //Spawn eggs
    public static final Item BIRD_SPAWN_EGG = null;
    public static final Item BLACK_BEAR_SPAWN_EGG = null;
    public static final Item BOAR_SPAWN_EGG = null;
    public static final Item BUNNY_SPAWN_EGG = null;
    public static final Item CROCODILE_SPAWN_EGG = null;
    public static final Item DEER_SPAWN_EGG = null;
    public static final Item DUCK_SPAWN_EGG = null;
    public static final Item ELEPHANT_SPAWN_EGG = null;
    public static final Item ENT_SPAWN_EGG = null;
    public static final Item FOX_SPAWN_EGG = null;
    public static final Item GOAT_SPAWN_EGG = null;
    public static final Item GRIZZLY_BEAR_SPAWN_EGG = null;
    public static final Item KITTY_SPAWN_EGG = null;
    public static final Item KOMODO_DRAGON_SPAWN_EGG = null;
    public static final Item LEOGER_SPAWN_EGG = null;
    public static final Item LEOPARD_SPAWN_EGG = null;
    public static final Item LIARD_SPAWN_EGG = null;
    public static final Item LION_SPAWN_EGG = null;
    public static final Item LIGER_SPAWN_EGG = null;
    public static final Item LITHER_SPAWN_EGG = null;
//    public static final Item MANTICORE_PET_SPAWN_EGG = null;
    public static final Item MOLE_SPAWN_EGG = null;
    public static final Item MOUSE_SPAWN_EGG = null;
    public static final Item OSTRICH_SPAWN_EGG = null;
    public static final Item PANDA_BEAR_SPAWN_EGG = null;
    public static final Item PANTHARD_SPAWN_EGG = null;
    public static final Item PANTHER_SPAWN_EGG = null;
    public static final Item PANTHGER_SPAWN_EGG = null;
//    public static final Item PET_SCORPION_SPAWN_EGG = null;
    public static final Item POLAR_BEAR_SPAWN_EGG = null;
    public static final Item RACCOON_SPAWN_EGG = null;
    public static final Item SNAKE_SPAWN_EGG = null;
    public static final Item TIGER_SPAWN_EGG = null;
    public static final Item TURTLE_SPAWN_EGG = null;
    public static final Item TURKEY_SPAWN_EGG = null;
    public static final Item WILDHORSE_SPAWN_EGG = null;
    public static final Item WYVERN_SPAWN_EGG = null;

    public static final Item BIG_GOLEM_SPAWN_EGG = null;
    public static final Item CAVE_OGRE_SPAWN_EGG = null;
    public static final Item FIRE_OGRE_SPAWN_EGG = null;
    public static final Item FLAME_WRAITH_SPAWN_EGG = null;
    public static final Item GREEN_OGRE_SPAWN_EGG = null;
    public static final Item HORSEMOB_SPAWN_EGG = null;
    public static final Item HELLRAT_SPAWN_EGG = null;
    public static final Item MANTICORE_SPAWN_EGG = null;
    public static final Item MINI_GOLEM_SPAWN_EGG = null;
    public static final Item RAT_SPAWN_EGG = null;
    public static final Item SILVER_SKELETON_SPAWN_EGG = null;
    public static final Item SCORPION_SPAWN_EGG = null;
    public static final Item WEREWOLF_SPAWN_EGG = null;
    public static final Item WRAITH_SPAWN_EGG = null;
    public static final Item WWOLF_SPAWN_EGG = null;

    public static final Item ANCHOVY_SPAWN_EGG = null;
    public static final Item ANGELFISH_SPAWN_EGG = null;
    public static final Item ANGLER_SPAWN_EGG = null;
    public static final Item BASS_SPAWN_EGG = null;
    public static final Item CLOWNFISH_SPAWN_EGG = null;
    public static final Item COD_SPAWN_EGG = null;
    public static final Item DOLPHIN_SPAWN_EGG = null;
    public static final Item FISHY_SPAWN_EGG = null;
    public static final Item GOLDFISH_SPAWN_EGG = null;
    public static final Item HIPPOTANG_SPAWN_EGG = null;
    public static final Item JELLYFISH_SPAWN_EGG = null;
    public static final Item MANDERIN_SPAWN_EGG = null;
    public static final Item PIRANHA_SPAWN_EGG = null;
    public static final Item SALMON_SPAWN_EGG = null;
    public static final Item MANTARAY_SPAWN_EGG = null;
    public static final Item SHARK_SPAWN_EGG = null;
    public static final Item STINGRAY_SPAWN_EGG = null;

    public static final Item ANT_SPAWN_EGG = null;
    public static final Item BEE_SPAWN_EGG = null;
    public static final Item BUTTERFLY_SPAWN_EGG = null;
    public static final Item CRAB_SPAWN_EGG = null;
    public static final Item CRICKET_SPAWN_EGG = null;
    public static final Item DRAGONFLY_SPAWN_EGG = null;
    public static final Item FIREFLY_SPAWN_EGG = null;
    public static final Item FLY_SPAWN_EGG = null;
    public static final Item MAGGOT_SPAWN_EGG = null;
    public static final Item SNAIL_SPAWN_EGG = null;
    public static final Item ROACH_SPAWN_EGG = null;

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {

        public static final List<SpawnEggItem> SPAWN_EGGS = new ArrayList<>();

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

            Item[] spawnEggs = {
                    new SpawnEggItem(MoCEntities.BIRD, 14020607, 14020607, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("bird_spawn_egg"),
                    new SpawnEggItem(MoCEntities.BLACK_BEAR, 10, 1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("black_bear_spawn_egg"),
                    new SpawnEggItem(MoCEntities.BOAR, 14772545, 9141102, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("boar_spawn_egg"),
                    new SpawnEggItem(MoCEntities.BUNNY, 12623485, 9141102, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("bunny_spawn_egg"),
                    new SpawnEggItem(MoCEntities.CROCODILE, 16711680, 65407, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("crocodile_spawn_egg"),
                    new SpawnEggItem(MoCEntities.DEER, 14021607, 33023, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("deer_spawn_egg"),
                    new SpawnEggItem(MoCEntities.DUCK, 14021607, 15656192, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("duck_spawn_egg"),
                    new SpawnEggItem(MoCEntities.ELEPHANT, 14772545, 23423, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("elephant_spawn_egg"),
                    new SpawnEggItem(MoCEntities.ENT, 12623485, 16711680, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("ent_spawn_egg"),
                    new SpawnEggItem(MoCEntities.FOX, 14772545, 5253242, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("fox_spawn_egg"),
                    new SpawnEggItem(MoCEntities.GOAT, 7434694, 6053069, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("goat_spawn_egg"),
                    new SpawnEggItem(MoCEntities.GRIZZLY_BEAR, 14772545, 1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("grizzly_bear_spawn_egg"),
                    new SpawnEggItem(MoCEntities.KITTY, 12623485, 5253242, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("kitty_spawn_egg"),
                    new SpawnEggItem(MoCEntities.KOMODO_DRAGON, 16711680, 23423, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("komodo_dragon_spawn_egg"),
                    new SpawnEggItem(MoCEntities.LEOGER, 14772545, 14772545, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("leoger_spawn_egg"),
                    new SpawnEggItem(MoCEntities.LEOPARD, 13749760, 10, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("leopard_spawn_egg"),
                    new SpawnEggItem(MoCEntities.LIARD, 15313474, 13749760, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("liard_spawn_egg"),
                    new SpawnEggItem(MoCEntities.LION, 15313474, 13749760, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("lion_spawn_egg"),
                    new SpawnEggItem(MoCEntities.LIGER, 15313474, 12623485, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("liger_spawn_egg"),
                    new SpawnEggItem(MoCEntities.LITHER, 15313474, 14772545, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("lither_spawn_egg"),
                    //Manticore pet spawn egg
                    new SpawnEggItem(MoCEntities.MOLE, 14020607, 16711680, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("mole_spawn_egg"),
                    new SpawnEggItem(MoCEntities.MOUSE, 14772545, 0, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("mouse_spawn_egg"),
                    new SpawnEggItem(MoCEntities.OSTRICH, 14020607, 9639167, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("ostrich_spawn_egg"),
                    new SpawnEggItem(MoCEntities.PANDA_BEAR, 10, 9141102, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("panda_bear_spawn_egg"),
                    new SpawnEggItem(MoCEntities.PANTHARD, 10, 13749760, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("panthard_spawn_egg"),
                    new SpawnEggItem(MoCEntities.PANTHER, 10, 205, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("panther_spawn_egg"),
                    new SpawnEggItem(MoCEntities.PANTHGER, 10, 14772545, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("panthger_spawn_egg"),
                    //Pet scorpion spawn egg
                    new SpawnEggItem(MoCEntities.POLAR_BEAR, 14020607, 9141102, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("polar_bear_spawn_egg"),
                    new SpawnEggItem(MoCEntities.RACCOON, 14772545, 13749760, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("raccoon_spawn_egg"),
                    new SpawnEggItem(MoCEntities.SNAKE, 14020607, 13749760, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("snake_spawn_egg"),
                    new SpawnEggItem(MoCEntities.TIGER, 14772545, 0, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("tiger_spawn_egg"),
                    new SpawnEggItem(MoCEntities.TURTLE, 14772545, 9320590, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("turtle_spawn_egg"),
                    new SpawnEggItem(MoCEntities.TURKEY, 14020607, 16711680, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("turkey_spawn_egg"),
                    new SpawnEggItem(MoCEntities.WILDHORSE, 12623485, 15656192, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("wildhorse_spawn_egg"),
                    new SpawnEggItem(MoCEntities.WYVERN, 14772545, 65407, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("wyvern_spawn_egg"),

                    new SpawnEggItem(MoCEntities.BIG_GOLEM, 16711680, 16622, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("big_golem_spawn_egg"),
                    new SpawnEggItem(MoCEntities.CAVE_OGRE, 16711680, 33023, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("cave_ogre_spawn_egg"),
                    new SpawnEggItem(MoCEntities.FIRE_OGRE, 16711680, 9320595, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("fire_ogre_spawn_egg"),
                    new SpawnEggItem(MoCEntities.GREEN_OGRE, 16711680, 65407, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("green_ogre_spawn_egg"),
                    new SpawnEggItem(MoCEntities.FLAME_WRAITH, 16711680, 12623485, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("flame_wraith_spawn_egg"),
                    new SpawnEggItem(MoCEntities.HORSEMOB, 16711680, 9320590, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("horsemob_spawn_egg"),
                    new SpawnEggItem(MoCEntities.HELLRAT, 16711680, 14772545, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("hellrat_spawn_egg"),
                    new SpawnEggItem(MoCEntities.MANTICORE, 16711680, 0, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("manticore_spawn_egg"),
                    new SpawnEggItem(MoCEntities.MINI_GOLEM, 16711680, 13749760, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("mini_golem_spawn_egg"),
                    new SpawnEggItem(MoCEntities.RAT, 16711680, 9141102, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("rat_spawn_egg"),
                    new SpawnEggItem(MoCEntities.SILVER_SKELETON, 16711680, 33023, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("silver_skeleton_spawn_egg"),
                    new SpawnEggItem(MoCEntities.SCORPION, 16711680, 6053069, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("scorpion_spawn_egg"),
                    new SpawnEggItem(MoCEntities.WEREWOLF, 16711680, 7434694, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("werewolf_spawn_egg"),
                    new SpawnEggItem(MoCEntities.WRAITH, 16711680, 0, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("wraith_spawn_egg"),
                    new SpawnEggItem(MoCEntities.WWOLF, 16711680, 13749760, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("wwolf_spawn_egg"),

                    new SpawnEggItem(MoCEntities.ANCHOVY, 5665535, 205, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("anchovy_spawn_egg"),
                    new SpawnEggItem(MoCEntities.ANGELFISH, 5665535, 7434694, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("angelfish_spawn_egg"),
                    new SpawnEggItem(MoCEntities.ANGLER, 5665535, 10, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("angler_spawn_egg"),
                    new SpawnEggItem(MoCEntities.BASS, 33023, 2372490, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("bass_spawn_egg"),
                    new SpawnEggItem(MoCEntities.CLOWNFISH, 5665535, 14772545, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("clownfish_spawn_egg"),
                    new SpawnEggItem(MoCEntities.COD, 33023, 16622, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("cod_spawn_egg"),
                    new SpawnEggItem(MoCEntities.DOLPHIN, 33023, 15631086, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("dolphin_spawn_egg"),
                    new SpawnEggItem(MoCEntities.FISHY, 5665535, 65407, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("fishy_spawn_egg"),
                    new SpawnEggItem(MoCEntities.GOLDFISH, 5665535, 15656192, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("goldfish_spawn_egg"),
                    new SpawnEggItem(MoCEntities.HIPPOTANG, 5665535, 2037680, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("hippotang_spawn_egg"),
                    new SpawnEggItem(MoCEntities.JELLYFISH, 33023, 14772545, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("jellyfish_spawn_egg"),
                    new SpawnEggItem(MoCEntities.MANDERIN, 5665535, 12623485, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("manderin_spawn_egg"),
                    new SpawnEggItem(MoCEntities.PIRANHA, 33023, 16711680, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("piranha_spawn_egg"),
                    new SpawnEggItem(MoCEntities.SALMON, 33023, 12623485, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("salmon_spawn_egg"),
                    new SpawnEggItem(MoCEntities.MANTARAY, 33023, 9141102, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("mantaray_spawn_egg"),
                    new SpawnEggItem(MoCEntities.SHARK, 33023, 9013643, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("shark_spawn_egg"),
                    new SpawnEggItem(MoCEntities.STINGRAY, 33023, 6053069, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("stingray_spawn_egg"),

                    new SpawnEggItem(MoCEntities.ANT, 65407, 12623485, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("ant_spawn_egg"),
                    new SpawnEggItem(MoCEntities.BEE, 65407, 15656192, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("bee_spawn_egg"),
                    new SpawnEggItem(MoCEntities.BUTTERFLY, 65407, 7434694, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("butterfly_spawn_egg"),
                    new SpawnEggItem(MoCEntities.CRAB, 65407, 13749760, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("crab_spawn_egg"),
                    new SpawnEggItem(MoCEntities.CRICKET, 65407, 16622, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("cricket_spawn_egg"),
                    new SpawnEggItem(MoCEntities.DRAGONFLY, 65407, 14020607, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("dragonfly_spawn_egg"),
                    new SpawnEggItem(MoCEntities.FIREFLY, 65407, 9320590, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("firefly_spawn_egg"),
                    new SpawnEggItem(MoCEntities.FLY, 65407, 1, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("fly_spawn_egg"),
                    new SpawnEggItem(MoCEntities.MAGGOT, 65407, 9141102, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("maggot_spawn_egg"),
                    new SpawnEggItem(MoCEntities.SNAIL, 65407, 14772545, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("snail_spawn_egg"),
                    new SpawnEggItem(MoCEntities.ROACH, 65407, 13749760, new Item.Properties().group(ItemGroup.MISC)).setRegistryName("roach_spawn_egg")
            };

            final IForgeRegistry<Item> registry = event.getRegistry();
            for(Item item : items) {
                registry.register(item);
            }
            for(Item item : spawnEggs) {
                registry.register(item);
                SPAWN_EGGS.add((SpawnEggItem) item);
            }
        }
    }

    /**
     * Register the dispenser behavior of spawn eggs.
     */
    public static void registerDispenserBehavior() {
        DefaultDispenseItemBehavior defaultdispenseitembehavior = new DefaultDispenseItemBehavior() {
            /**
             * Dispense the specified stack
             */
            public ItemStack dispenseStack(IBlockSource source, ItemStack stack) {
                Direction direction = source.getBlockState().get(DispenserBlock.FACING);
                EntityType<?> entitytype = ((SpawnEggItem)stack.getItem()).getType(stack.getTag());
                entitytype.spawn(source.getWorld(), stack, (PlayerEntity)null, source.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
                stack.shrink(1);
                return stack;
            }
        };
        for(SpawnEggItem spawneggitem : RegistrationHandler.SPAWN_EGGS) {
            DispenserBlock.registerDispenseBehavior(spawneggitem, defaultdispenseitembehavior);
        }
    }
}
