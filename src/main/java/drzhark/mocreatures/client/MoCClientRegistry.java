package drzhark.mocreatures.client;

import drzhark.mocreatures.client.model.*;
import drzhark.mocreatures.client.renderer.entity.*;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class MoCClientRegistry {

    @SubscribeEvent
    public void registerRenderers(final ModelRegistryEvent event) {
        //Ambients
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ANT, m-> new MoCRenderMoC<>(m, new MoCModelAnt<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BEE, m-> new MoCRenderMoC<>(m, new MoCModelBee<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BUTTERFLY, m-> new MoCRenderButterfly<>(m, new MoCModelButterfly<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CRAB, m-> new MoCRenderMoC<>(m, new MoCModelCrab<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CRICKET, m-> new MoCRenderCricket<>(m, new MoCModelCricket<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.DRAGONFLY, m-> new MoCRenderMoC<>(m, new MoCModelDragonfly<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FIREFLY, m-> new MoCRenderFirefly<>(m, new MoCModelFirefly<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FLY, m-> new MoCRenderMoC<>(m, new MoCModelFly<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MAGGOT, m-> new MoCRenderMoC<>(m, new MoCModelMaggot(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.SNAIL, m-> new MoCRenderMoC<>(m, new MoCModelSnail(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ROACH, m-> new MoCRenderMoC<>(m, new MoCModelRoach(), 0.5F));

        //Creatures
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BIRD, m -> new MoCRenderBird<>(m, new MoCModelBird<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BEAR, m -> new MoCRenderBear<>(m, new MoCModelBear<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BOAR, m -> new MoCRenderMoC<>(m, new MoCModelBoar<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BUNNY, m -> new MoCRenderBunny<>(m, new MoCModelBunny<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CROCODILE, m -> new MoCRenderCrocodile(m, new MoCModelCrocodile<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.DUCK, m -> new MoCRenderMoC<>(m, new MoCModelDuck<>(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.DEER, m -> new MoCRenderMoC<>(m, new MoCModelDeer<>(), 0.5F));
    }

}
