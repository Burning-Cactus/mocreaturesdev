package drzhark.mocreatures;

import com.mojang.authlib.GameProfile;
import drzhark.mocreatures.client.handlers.MoCKeyHandler;
import drzhark.mocreatures.client.renderer.texture.MoCTextures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.handlers.MoCEventHooks;
import drzhark.mocreatures.handlers.MoCWorldEvents;
import drzhark.mocreatures.registry.MoCEntities;
import drzhark.mocreatures.registry.MoCEntityAttributes;
import drzhark.mocreatures.registry.MoCItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.UUID;

@Mod(MoCConstants.MOD_ID)
public class MoCreatures {

    public static MoCreatures instance;

    public static final Logger LOGGER = LogManager.getLogger(MoCConstants.MOD_ID);
    public MoCPetMapData mapData;
    public static boolean isCustomSpawnerLoaded = false;
    public static GameProfile MOCFAKEPLAYER = new GameProfile(UUID.fromString("6E379B45-1111-2222-3333-2FE1A88BCD66"), "[MoCreatures]");

//    public static final DimensionType WYVERN_LAIR = null;

//    public static Map<String, MoCEntityData> mocEntityMap = new TreeMap<String, MoCEntityData>(String.CASE_INSENSITIVE_ORDER);
//    public static Map<Integer, Class<? extends LivingEntity>> instaSpawnerMap = new HashMap<Integer, Class<? extends LivingEntity>>();
    public static final String MOC_LOGO = TextFormatting.WHITE + "[" + TextFormatting.AQUA + "Mo'Creatures" + TextFormatting.WHITE + "]";
    public static final MoCTextures MOCTEXTURES = new MoCTextures();
    public static final String ENTITY_TEXTURES = "textures/entity/";

    public static final RegistryKey<DimensionType> WYVERN_LAIR_TYPE = RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY, new ResourceLocation(MoCConstants.MOD_ID, "wyvern_lair"));
    public static final RegistryKey<World> WYVERN_LAIR = RegistryKey.create(Registry.DIMENSION_REGISTRY, new ResourceLocation(MoCConstants.MOD_ID, "wyvern_lair"));
    public static final RegistryKey<Biome> WYVERN_LAIR_BIOME = RegistryKey.create(Registry.BIOME_REGISTRY, new ResourceLocation(MoCConstants.MOD_ID, "wyvern_biome"));


    public MoCreatures() {
        LOGGER.info("Setting up configs...");
        {
            final Pair<MoCConfig.ClientConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(MoCConfig.ClientConfig::new);
            ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, specPair.getRight()); //Client
            MoCConfig.CLIENT_CONFIG = specPair.getLeft();
        }
        {
            final Pair<MoCConfig.CommonConfig, ForgeConfigSpec> specPair = new ForgeConfigSpec.Builder().configure(MoCConfig.CommonConfig::new);
            ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, specPair.getRight()); //Common
            MoCConfig.COMMON_CONFIG = specPair.getLeft();
        }
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        bus.addListener(this::setup);
        bus.addListener(this::clientSetup);

        MoCreatures.instance = this;
    }

    private void setup(FMLCommonSetupEvent event) {
        MoCEntityAttributes.init();
        MoCEntities.registerEntitySpawns();
        event.enqueueWork(MoCItems::registerDispenserBehavior);

        MinecraftForge.EVENT_BUS.register(new MoCEventHooks());
        MinecraftForge.EVENT_BUS.register(new MoCWorldEvents());
    }

    private void clientSetup(FMLClientSetupEvent event) {
//        MinecraftForge.EVENT_BUS.register(new MoCClientTickHandler());
        MinecraftForge.EVENT_BUS.register(new MoCKeyHandler());
    }

    public static void burnPlayer(PlayerEntity player) {
        //TODO 4FIX
        //if (!mc.world.isRemote)
        //{
        //    inst.burned = true;
        //}
    }

    public static void freezePlayer(PlayerEntity player) {
        //TODO 4FIX
        //if (!mc.world.isRemote)
        //{
        //    inst.freezed = true;
        //    inst.freezedcounter = 0;
        //}
    }

    public static void poisonPlayer(PlayerEntity player) {
        //TODO 4FIX
        //if (!mc.world.isRemote)
        //{
        //    inst.poisoned = true;
        //    inst.poisoncounter = 0;
        //}
    }

    public static void showCreaturePedia(PlayerEntity player, String s) {
        //TODO 4FIX        mc.displayGuiScreen(new MoCGUIPedia(s, 256, 256));
    }

    public static void showCreaturePedia(String s) {
        //TODO 4FIX        EntityPlayer entityplayer = mc.player;
        //showCreaturePedia(entityplayer, s);
    }

//    public static void updateSettings() {
//        proxy.readGlobalConfigValues();
//    }

    //TODO: Fix the isServer() method (might not be necessary anymore, use world.isRemote or some other means
//    public static boolean isServer() {
//        return (FMLCommonLaunchHandler.getDist() == Dist.DEDICATED_SERVER);
//    }

    public static ResourceLocation getTexture(String texture) {
        return new ResourceLocation(MoCConstants.MOD_ID, ENTITY_TEXTURES + texture);
//        return MOCTEXTURES.getTexture(texture);
    }

}
