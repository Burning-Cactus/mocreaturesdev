package drzhark.mocreatures.client;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.model.*;
import drzhark.mocreatures.client.particle.MoCEntityFXStar;
import drzhark.mocreatures.client.particle.MoCEntityFXUndead;
import drzhark.mocreatures.client.particle.MoCEntityFXVacuum;
import drzhark.mocreatures.client.particle.MoCEntityFXVanish;
import drzhark.mocreatures.client.renderer.entity.*;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCParticleTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCClientRegistry {

    @SubscribeEvent
    public static void registerRenderers(ModelRegistryEvent event) {
        //Ambients
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ANT, m-> new MoCRenderInsect<>(m, new MoCModelAnt<>(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BEE, m-> new MoCRenderInsect<>(m, new MoCModelBee<>(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BUTTERFLY, m-> new MoCRenderButterfly<>(m, new MoCModelButterfly<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CRAB, m-> new MoCRenderMoC<>(m, new MoCModelCrab<>(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CRICKET, m-> new MoCRenderCricket<>(m, new MoCModelCricket<>(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.DRAGONFLY, m-> new MoCRenderInsect<>(m, new MoCModelDragonfly<>(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FIREFLY, m-> new MoCRenderFirefly(m, new MoCModelFirefly<>(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FLY, m-> new MoCRenderInsect<>(m, new MoCModelFly<>(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MAGGOT, m-> new MoCRenderMoC<>(m, new MoCModelMaggot(), 0.0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.SNAIL, m-> new MoCRenderMoC<>(m, new MoCModelSnail<>(), 0.0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ROACH, m-> new MoCRenderInsect<>(m, new MoCModelRoach(), 0.0F));

        //Creatures
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BIRD, m -> new MoCRenderBird<>(m, new MoCModelBird<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BEAR, m -> new MoCRenderBear<>(m, new MoCModelBear<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.GRIZZLY_BEAR, m -> new MoCRenderBear<>(m, new MoCModelBear<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PANDA_BEAR, m -> new MoCRenderBear<>(m, new MoCModelBear<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.POLAR_BEAR, m -> new MoCRenderBear<>(m, new MoCModelBear<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BOAR, m -> new MoCRenderMoC<>(m, new MoCModelBoar<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BUNNY, m -> new MoCRenderBunny<>(m, new MoCModelBunny<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CROCODILE, m -> new MoCRenderCrocodile<>(m, new MoCModelCrocodile<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.DUCK, m -> new MoCRenderMoC<>(m, new MoCModelDuck<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.DEER, m -> new MoCRenderMoC<>(m, new MoCModelDeer<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ELEPHANT, m -> new MoCRenderMoC<>(m, new MoCModelElephant<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ENT, m -> new MoCRenderMoC<>(m, new MoCModelEnt<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FOX, m -> new MoCRenderMoC<>(m, new MoCModelFox(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.GOAT, m -> new MoCRenderGoat(m, new MoCModelGoat(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.KITTY, m -> new MoCRenderKitty(m, new MoCModelKitty(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.KOMODO_DRAGON, m -> new MoCRenderMoC<>(m, new MoCModelKomodo(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LEOGER, m -> new MoCRenderMoC<>(m, new MoCModelNewBigCat<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LEOPARD, m -> new MoCRenderMoC<>(m, new MoCModelNewBigCat<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LIARD, m -> new MoCRenderMoC<>(m, new MoCModelNewBigCat<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LION, m -> new MoCRenderMoC<>(m, new MoCModelNewBigCat<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LIGER, m -> new MoCRenderMoC<>(m, new MoCModelNewBigCat<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LITHER, m -> new MoCRenderMoC<>(m, new MoCModelNewBigCat<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MANTICORE_PET, m -> new MoCRenderMoC<>(m, new MoCModelNewBigCat<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PANTHARD, m -> new MoCRenderMoC<>(m, new MoCModelNewBigCat<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PANTHER, m -> new MoCRenderMoC<>(m, new MoCModelNewBigCat<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PANTHGER, m -> new MoCRenderMoC<>(m, new MoCModelNewBigCat<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.TIGER, m -> new MoCRenderMoC<>(m, new MoCModelNewBigCat<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MOLE, m -> new MoCRenderMoC<>(m, new MoCModelMole(),0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MOUSE, m -> new MoCRenderMoC<>(m, new MoCModelMouse(),0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.OSTRICH, m -> new MoCRenderOstrich(m, new MoCModelOstrich<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PET_SCORPION, m -> new MoCRenderPetScorpion(m, new MoCModelPetScorpion(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.RACCOON, m -> new MoCRenderMoC<>(m, new MoCModelRaccoon(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.SNAKE, m -> new MoCRenderSnake(m, new MoCModelSnake<>(), 0.0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.TURTLE, m -> new MoCRenderTurtle(m, new MoCModelTurtle<>(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.TURKEY, m -> new MoCRenderMoC<>(m, new MoCModelTurkey<>(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.WYVERN, m -> new MoCRenderMoC<>(m, new MoCModelWyvern<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.WILDHORSE, m -> new MoCRenderNewHorse<>(m, new MoCModelNewHorse<>()));

        //Mobs
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.HORSEMOB, m -> new MoCRenderHorseMob<>(m, new MoCModelNewHorseMob<>()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CAVE_OGRE, m -> new MoCRenderMoC<>(m, new MoCModelOgre<>(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FIRE_OGRE, m -> new MoCRenderMoC<>(m, new MoCModelOgre<>(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.GREEN_OGRE, m -> new MoCRenderMoC<>(m, new MoCModelOgre<>(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FLAME_WRAITH, m -> new MoCRenderWraith(m, new MoCModelWraith<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.WRAITH, m -> new MoCRenderWraith(m, new MoCModelWraith<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BIG_GOLEM, m -> new MoCRenderGolem(m, new MoCModelGolem(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.HELLRAT, m -> new MoCRenderHellRat(m, new MoCModelRat<>(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.RAT, m -> new MoCRenderRat<>(m, new MoCModelRat<>(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MANTICORE, m -> new MoCRenderMoC<>(m, new MoCModelManticore(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MINI_GOLEM, m -> new MoCRenderMoC<>(m, new MoCModelMiniGolem(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.SILVER_SKELETON, m -> new MoCRenderMoC<>(m, new MoCModelSilverSkeleton(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.SCORPION, m -> new MoCRenderScorpion(m, new MoCModelScorpion<>(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.WEREWOLF, m -> new MoCRenderWerewolf(m, new MoCModelWereHuman(), new MoCModelWere(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.WWOLF, m -> new MoCRenderWWolf(m, new MoCModelWolf<>(), 0.7F));

        //Aquatic
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ANCHOVY, m -> new MoCRenderMoC<>(m, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ANGELFISH, m -> new MoCRenderMoC<>(m, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ANGLER, m -> new MoCRenderMoC<>(m, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BASS, m -> new MoCRenderMoC<>(m, new MoCModelMediumFish(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CLOWNFISH, m -> new MoCRenderMoC<>(m, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.COD, m -> new MoCRenderMoC<>(m, new MoCModelMediumFish(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.DOLPHIN, m -> new MoCRenderDolphin<>(m, new MoCModelDolphin<>(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FISHY, m -> new MoCRenderFishy(m, new MoCModelFishy<>(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.GOLDFISH, m -> new MoCRenderMoC<>(m, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.HIPPOTANG, m -> new MoCRenderMoC<>(m, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.JELLYFISH, m -> new MoCRenderMoC<>(m, new MoCModelJellyFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MANDERIN, m -> new MoCRenderMoC<>(m, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PIRANHA, m -> new MoCRenderMoC<>(m, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.SALMON, m -> new MoCRenderMoC<>(m, new MoCModelMediumFish(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MANTARAY, m -> new MoCRenderMoC<>(m, new MoCModelRay<>(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.STINGRAY, m -> new MoCRenderMoC<>(m, new MoCModelRay<>(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.SHARK, m -> new MoCRenderShark(m, new MoCModelShark(), 0.6F));

        //Others
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.EGG, m -> new MoCRenderEgg<>(m, new MoCModelEgg<>(),0.0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.KITTY_BED, m -> new MoCRenderKittyBed(m, new MoCModelKittyBed(), new MoCModelKittyBed2(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LITTERBOX, m -> new MoCRenderLitterBox(m, new MoCModelLitterBox(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.TROCK, MoCRenderTRock::new);

    }

    /**
     * Registers the particle factories on the client side.
     */
    @SubscribeEvent
    public static void registerParticles(ParticleFactoryRegisterEvent event) {
        ParticleManager manager = Minecraft.getInstance().particles;
        manager.registerFactory(MoCParticleTypes.STAR_FX, MoCEntityFXStar.Factory::new);
        manager.registerFactory(MoCParticleTypes.UNDEAD_FX, MoCEntityFXUndead.Factory::new);
        manager.registerFactory(MoCParticleTypes.VACUUM_FX, MoCEntityFXVacuum.Factory::new);
        manager.registerFactory(MoCParticleTypes.VANISH_FX, MoCEntityFXVanish.Factory::new);
    }
}
