package drzhark.mocreatures;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import drzhark.mocreatures.client.MoCClientTickHandler;
import drzhark.mocreatures.client.handlers.MoCKeyHandler;
import drzhark.mocreatures.client.renderer.texture.MoCTextures;
import drzhark.mocreatures.configuration.MoCConfig;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.dimension.DimensionType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLCommonLaunchHandler;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@Mod(MoCConstants.MOD_ID)
public class MoCreatures {

//    @Instance(MoCConstants.MOD_ID)
    public static MoCreatures instance;

    public static final Logger LOGGER = LogManager.getLogger(MoCConstants.MOD_ID);
    public static final ItemGroup tabMoC = new MoCItemGroup("MoCreaturesTab");
    public MoCPetMapData mapData;
    public static boolean isCustomSpawnerLoaded = false;
    public static GameProfile MOCFAKEPLAYER = new GameProfile(UUID.fromString("6E379B45-1111-2222-3333-2FE1A88BCD66"), "[MoCreatures]");

    public static final DimensionType WYVERN_LAIR = null;
    public static int WyvernLairDimensionID; //17;

    public static Map<String, MoCEntityData> mocEntityMap = new TreeMap<String, MoCEntityData>(String.CASE_INSENSITIVE_ORDER);
    public static Map<EntityType<?>, MoCEntityData> entityMap = new HashMap<EntityType<?>, MoCEntityData>();
    public static Map<Integer, Class<? extends LivingEntity>> instaSpawnerMap = new HashMap<Integer, Class<? extends LivingEntity>>();
    public static final String MOC_LOGO = TextFormatting.WHITE + "[" + TextFormatting.AQUA + "Mo'Creatures" + TextFormatting.WHITE + "]";
    public static final MoCTextures MOCTEXTURES = new MoCTextures();


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
        bus.addListener(this::onServerStart);

        MoCreatures.instance = this;
    }

    private void setup(FMLCommonSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new MoCEventHooks());
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.register(new MoCClientTickHandler()));
        DistExecutor.runWhenOn(Dist.CLIENT, () -> () -> MinecraftForge.EVENT_BUS.register(new MoCKeyHandler()));
    }

    //TODO: Register commands properly in 1.15
    private void onServerStart(FMLServerStartingEvent event) {
        CommandDispatcher dispatcher = event.getCommandDispatcher();
//        proxy.initGUI();
//        event.registerServerCommand(new CommandMoCreatures());
//        event.registerServerCommand(new CommandMoCTP());
//        event.registerServerCommand(new CommandMoCPets());
//        if (isServer()) {
//            if (FMLCommonHandler.instance().getMinecraftServerInstance().isDedicatedServer()) {
//                event.registerServerCommand(new CommandMoCSpawn());
//            }
//        }
    }

//
//    //how to check for client: if(FMLCommonHandler.instance().getSide().isRemote())
//
//    @EventHandler
//    public void load(FMLInitializationEvent event) {
//        WyvernLairDimensionID = proxy.WyvernDimension;
//        proxy.mocSettingsConfig.save();
//        proxy.registerRenderers();
//        proxy.registerRenderInformation();
//        WYVERN_LAIR = DimensionType.register("Wyvern Lair", "_wyvern_lair", WyvernLairDimensionID, WorldProviderWyvernEnd.class, false);
//        DimensionManager.registerDimension(WyvernLairDimensionID, WYVERN_LAIR);
//    }
//
//    @EventHandler
//    public void postInit(FMLPostInitializationEvent event) {
//        isCustomSpawnerLoaded = Loader.isModLoaded("CustomSpawner");
//    }

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
        return MOCTEXTURES.getTexture(texture);
    }

}
