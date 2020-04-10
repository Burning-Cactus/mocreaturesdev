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

    public static final SimpleChannel INSTANCE = NetworkRegistry
            .newSimpleChannel(new ResourceLocation(MoCConstants.MOD_ID, "channel"),
                    () -> PROTOCOL,
                    PROTOCOL::equals,
                    PROTOCOL::equals);

//    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel("MoCreatures");

    public static void init() {
        int id = 0;
        INSTANCE.registerMessage(id++, MoCMessageAnimation.class, MoCMessageAnimation::encode, MoCMessageAnimation::decode, MoCMessageAnimation::onMessage);
        INSTANCE.registerMessage(id++, MoCMessageAppear.class, MoCMessageAppear::encode, MoCMessageAppear::decode, MoCMessageAppear::onMessage);
        INSTANCE.registerMessage(id++, MoCMessageAttachedEntity.class, MoCMessageAttachedEntity::encode, MoCMessageAttachedEntity::decode, MoCMessageAttachedEntity::onMessage);
        INSTANCE.registerMessage(id++, MoCMessageEntityDive.class, MoCMessageEntityDive::encode, MoCMessageEntityDive::decode, MoCMessageEntityDive::onMessage);
        INSTANCE.registerMessage(id++, MoCMessageEntityJump.class, MoCMessageEntityJump::encode, MoCMessageEntityJump::decode, MoCMessageEntityJump::onMessage);
        INSTANCE.registerMessage(id++, MoCMessageExplode.class, MoCMessageExplode::encode, MoCMessageExplode::decode, MoCMessageExplode::onMessage);
        INSTANCE.registerMessage(id++, MoCMessageHealth.class, MoCMessageHealth::encode, MoCMessageHealth::decode, MoCMessageHealth::onMessage);
        INSTANCE.registerMessage(id++, MoCMessageHeart.class, MoCMessageHeart::encode, MoCMessageHeart::decode, MoCMessageHeart::onMessage);
        INSTANCE.registerMessage(id++, MoCMessageInstaSpawn.class, MoCMessageInstaSpawn::encode, MoCMessageInstaSpawn::decode, MoCMessageInstaSpawn::onMessage);
        INSTANCE.registerMessage(id++, MoCMessageNameGUI.class, MoCMessageNameGUI::encode, MoCMessageNameGUI::decode, MoCMessageNameGUI::onMessage);
        INSTANCE.registerMessage(id++, MoCMessageShuffle.class, MoCMessageShuffle::encode, MoCMessageShuffle::decode, MoCMessageShuffle::onMessage);
        INSTANCE.registerMessage(id++, MoCMessageTwoBytes.class, MoCMessageTwoBytes::encode, MoCMessageTwoBytes::decode, MoCMessageTwoBytes::onMessage);
        INSTANCE.registerMessage(id++, MoCMessageUpdatePetName.class, MoCMessageUpdatePetName::encode, MoCMessageUpdatePetName::decode, MoCMessageUpdatePetName::onMessage);
        INSTANCE.registerMessage(id++, MoCMessageVanish.class, MoCMessageVanish::encode, MoCMessageVanish::decode, MoCMessageVanish::onMessage);
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

}
