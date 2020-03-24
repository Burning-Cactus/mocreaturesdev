package drzhark.mocreatures.network;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.gui.helpers.MoCGUIEntityNamer;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.network.message.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

import java.util.List;
import java.util.function.Supplier;

public class MoCMessageHandler {

    public static final String PROTOCOL = "1";

    public static final SimpleChannel CHANNEL = NetworkRegistry
            .newSimpleChannel(new ResourceLocation(MoCConstants.MOD_ID, "channel"),
                    () -> PROTOCOL,
                    PROTOCOL::equals,
                    PROTOCOL::equals);

//    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("MoCreatures");

    public static void init() {
//        INSTANCE.registerMessage(MoCMessageAnimation.class, MoCMessageAnimation.class, 0, Dist.CLIENT);
//        INSTANCE.registerMessage(MoCMessageAppear.class, MoCMessageAppear.class, 1, Dist.CLIENT);
//        INSTANCE.registerMessage(MoCMessageAttachedEntity.class, MoCMessageAttachedEntity.class, 2, Dist.CLIENT);
//        INSTANCE.registerMessage(MoCMessageEntityDive.class, MoCMessageEntityDive.class, 3, Dist.DEDICATED_SERVER);
//        INSTANCE.registerMessage(MoCMessageEntityJump.class, MoCMessageEntityJump.class, 4, Dist.DEDICATED_SERVER);
//        INSTANCE.registerMessage(MoCMessageExplode.class, MoCMessageExplode.class, 5, Dist.CLIENT);
//        INSTANCE.registerMessage(MoCMessageHealth.class, MoCMessageHealth.class, 6, Dist.CLIENT);
//        INSTANCE.registerMessage(MoCMessageHeart.class, MoCMessageHeart.class, 7, Dist.CLIENT);
//        INSTANCE.registerMessage(MoCMessageInstaSpawn.class, MoCMessageInstaSpawn.class, 8, Dist.DEDICATED_SERVER);
//        INSTANCE.registerMessage(MoCMessageNameGUI.class, MoCMessageNameGUI.class, 9, Dist.CLIENT);
//        INSTANCE.registerMessage(MoCMessageUpdatePetName.class, MoCMessageUpdatePetName.class, 10, Dist.DEDICATED_SERVER);
//        INSTANCE.registerMessage(MoCMessageShuffle.class, MoCMessageShuffle.class, 11, Dist.CLIENT);
//        INSTANCE.registerMessage(MoCMessageTwoBytes.class, MoCMessageTwoBytes.class, 12, Dist.CLIENT);
//        INSTANCE.registerMessage(MoCMessageVanish.class, MoCMessageVanish.class, 13, Dist.CLIENT);
    }

    // Wrap client message handling due to 1.8 clients receiving packets on Netty thread
    // This solves random NPE issues when attempting to access world data on client
    @SuppressWarnings("rawtypes")
    public static void handleMessage(IMoCMessage message, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.side == Dist.CLIENT) {
            FMLCommonHandler.instance().getWorldThread(FMLCommonHandler.instance().getClientToServerNetworkManager().getNetHandler()).addScheduledTask(new ClientPacketTask(message, ctx));
        }
    }

    // redirects client received packets to main thread to avoid NPEs
    public static class ClientPacketTask implements Runnable {

        @SuppressWarnings("rawtypes")
        private IMoCMessage message;
        @SuppressWarnings("unused")
        private Supplier<NetworkEvent.Context> ctx;

        @SuppressWarnings("rawtypes")
        public ClientPacketTask(IMoCMessage message, Supplier<NetworkEvent.Context> ctx) {
            this.message = message;
            this.ctx = ctx;
        }

        @Override
        public void run() {
            if (this.message instanceof MoCMessageAnimation) {
                MoCMessageAnimation message = (MoCMessageAnimation) this.message;
                List<Entity> entList = MoCClientProxy.mc.player.world.loadedEntityList;
                for (Entity ent : entList) {
                    if (ent.getEntityId() == message.entityId && ent instanceof IMoCEntity) {
                        ((IMoCEntity) ent).performAnimation(message.animationType);
                        break;
                    }
                }
                return;
            } else if (this.message instanceof MoCMessageAppear) {
                MoCMessageAppear message = (MoCMessageAppear) this.message;
                List<Entity> entList = MoCClientProxy.mc.player.world.loadedEntityList;
                for (Entity ent : entList) {
                    if (ent.getEntityId() == message.entityId && ent instanceof MoCEntityHorse) {
                        ((MoCEntityHorse) ent).MaterializeFX();
                        break;
                    }
                }
                return;
            } else if (this.message instanceof MoCMessageAttachedEntity) {
                MoCMessageAttachedEntity message = (MoCMessageAttachedEntity) this.message;
                Object var2 = MoCClientProxy.mc.player.world.getEntityByID(message.sourceEntityId);
                Entity var3 = MoCClientProxy.mc.player.world.getEntityByID(message.targetEntityId);

                if (var2 != null) {
                    ((Entity) var2).startRiding(var3);
                }
                return;
            } else if (this.message instanceof MoCMessageExplode) {
                MoCMessageExplode message = (MoCMessageExplode) this.message;
                List<Entity> entList = MoCClientProxy.mc.player.world.loadedEntityList;
                for (Entity ent : entList) {
                    if (ent.getEntityId() == message.entityId && ent instanceof MoCEntityOgre) {
                        ((MoCEntityOgre) ent).performDestroyBlastAttack();
                        break;
                    }
                }
                return;
            } else if (this.message instanceof MoCMessageHealth) {
                MoCMessageHealth message = (MoCMessageHealth) this.message;
                List<Entity> entList = MoCClientProxy.mc.player.world.loadedEntityList;
                for (Entity ent : entList) {
                    if (ent.getEntityId() == message.entityId && ent instanceof LivingEntity) {
                        ((LivingEntity) ent).setHealth(message.health);
                        break;
                    }
                }
                return;
            } else if (this.message instanceof MoCMessageHeart) {
                MoCMessageHeart message = (MoCMessageHeart) this.message;
                Entity entity = null;
                while (entity == null) {
                    entity = MoCClientProxy.mc.player.world.getEntityByID(message.entityId);
                    if (entity != null) {
                        if (entity instanceof IMoCTameable) {
                            ((IMoCTameable)entity).spawnHeart();
                        }
                    }
                }
                return;
            } else if (this.message instanceof MoCMessageShuffle) {
                MoCMessageShuffle message = (MoCMessageShuffle) this.message;
                List<Entity> entList = MoCClientProxy.mc.player.world.loadedEntityList;
                for (Entity ent : entList) {
                    if (ent.getEntityId() == message.entityId && ent instanceof MoCEntityHorse) {
                        if (message.flag) {
                            //((MoCEntityHorse) ent).shuffle();
                        } else {
                            ((MoCEntityHorse) ent).shuffleCounter = 0;
                        }
                        break;
                    }
                }
                return;
            } else if (this.message instanceof MoCMessageTwoBytes) {
                MoCMessageTwoBytes message = (MoCMessageTwoBytes) this.message;
                Entity ent = MoCClientProxy.mc.player.world.getEntityByID(message.entityId);
                if (ent != null && ent instanceof MoCEntityGolem) {
                    ((MoCEntityGolem) ent).saveGolemCube(message.slot, message.value);
                }
                return;
            } else if (this.message instanceof MoCMessageVanish) {
                MoCMessageVanish message = (MoCMessageVanish) this.message;
                List<Entity> entList = MoCClientProxy.mc.player.world.loadedEntityList;
                for (Entity ent : entList) {
                    if (ent.getEntityId() == message.entityId && ent instanceof MoCEntityHorse) {
                        ((MoCEntityHorse) ent).setVanishC((byte) 1);
                        break;
                    }
                }
                return;
            } else if (this.message instanceof MoCMessageNameGUI) {
                MoCMessageNameGUI message = (MoCMessageNameGUI) this.message;
                Entity entity = MoCClientProxy.mc.player.world.getEntityByID(message.entityId);
                MoCClientProxy.mc.displayGuiScreen(new MoCGUIEntityNamer(((IMoCEntity) entity), ((IMoCEntity) entity).getPetName()));
                return;
            }
        }
    }
}
