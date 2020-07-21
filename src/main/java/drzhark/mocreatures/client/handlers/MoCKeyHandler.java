package drzhark.mocreatures.client.handlers;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageEntityDive;
import drzhark.mocreatures.network.message.MoCMessageEntityJump;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

import javax.swing.*;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class MoCKeyHandler {

    int keyCount;
    private Screen localScreen;
    //static KeyBinding jumpBinding = new KeyBinding("jumpBind", Keyboard.KEY_F);
    //static KeyBinding jumpBinding = new KeyBinding("MoCreatures Jump", MoCClientProxy.mc.gameSettings.keyBindJump.getKeyCode(), "key.categories.movement");
    static KeyBinding diveBinding = new KeyBinding("key.mocreaturesdive", KeyConflictContext.IN_GAME, InputMappings.INPUT_INVALID, "key.categories.movement");
    static KeyBinding guiBinding = new KeyBinding("key.mocreaturesgui", KeyConflictContext.IN_GAME, InputMappings.INPUT_INVALID, "key.categories.misc"); //TODO fix bug that crashes game when invoking GUI

    public static void initKeys() {
        ClientRegistry.registerKeyBinding(diveBinding);
        ClientRegistry.registerKeyBinding(guiBinding);
    }

    //static KeyBinding dismountBinding = new KeyBinding("MoCreatures Dismount", Keyboard.KEY_F);

    public MoCKeyHandler() {
        //the first value is an array of KeyBindings, the second is whether or not the call
        //keyDown should repeat as long as the key is down
        //net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(jumpBinding);
        net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(diveBinding);
        net.minecraftforge.fml.client.registry.ClientRegistry.registerKeyBinding(guiBinding);
//        this.localScreen = MoCClientProxy.instance.MoCScreen;
    }

//    @SubscribeEvent
//    public void onInput(InputEvent event) {
//        int keyPressed = (Mouse.getEventButton() + -100);
//        if (keyPressed == -101) {
//            keyPressed = Keyboard.getEventKey();
//        }
//
//        PlayerEntity ep = Minecraft.getInstance().player;
//        if (ep == null) {
//            return;
//        }
//        if (FMLClientHandler.instance().getClient().ingameGUI.getChatGUI().getChatOpen()) {
//            return; // if chatting return
//        }
//        if (Keyboard.getEventKeyState() && ep.getRidingEntity() != null) {
//            Keyboard.enableRepeatEvents(true); // allow holding down key. Fixes flying
//        }

        // isKeyDown must be called with valid keys only. Mouse binds always use negative id's so we avoid them here.
//        boolean kbJump = Minecraft.getInstance().gameSettings.keyBindJump.getKey().getKeyCode() >= 0 ? Keyboard.isKeyDown(Minecraft.getInstance().gameSettings.keyBindJump.getKey().getKeyCode()) : keyPressed == Minecraft.getInstance().gameSettings.keyBindJump.getKey().getKeyCode();
//        boolean kbDive = diveBinding.getKey().getKeyCode() >= 0 ? Keyboard.isKeyDown(diveBinding.getKey().getKeyCode()) : keyPressed == diveBinding.getKey().getKeyCode();
//        boolean kbGui = guiBinding.getKeyCode() >= 0 ? Keyboard.isKeyDown(guiBinding.getKeyCode()) : keyPressed == guiBinding.getKeyCode();
        //boolean kbDismount = kb.keyDescription.equals("MoCreatures Dismount");

//        if (kbGui && ep.world.isRemote) {
//            if (Minecraft.getInstance().isGameFocused() && (this.localScreen == null)) {
//                GuiModScreen.show(MoCClientProxy.instance.MoCScreen.theWidget);
//            } else {
//                this.localScreen = null; // kill our instance
//            }
//        }

        /**
         * this avoids double jumping
         */
//        if (kbJump && ep.getRidingEntity() != null && ep.getRidingEntity() instanceof IMoCEntity) {
//            // keyCount = 0;
//            // jump code needs to be executed client/server simultaneously to take
//            ((IMoCEntity) ep.getRidingEntity()).makeEntityJump();
//            MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageEntityJump());
//        }
//
//        if (kbDive && ep.getRidingEntity() != null && ep.getRidingEntity() instanceof IMoCEntity) {
//            //  keyCount = 0;
//            // jump code needs to be executed client/server simultaneously to take
//            ((IMoCEntity) ep.getRidingEntity()).makeEntityDive();
//            MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageEntityDive());
//        }
//    }
}
