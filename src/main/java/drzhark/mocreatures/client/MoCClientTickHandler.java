package drzhark.mocreatures.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MoCClientTickHandler {

    private void onTickInGame() {
    }

    boolean inMenu;
    int lastTickRun;

    private void onTickInGui(Screen curScreen) {
        // handle GUI tick stuff here
        this.inMenu = true;
        this.lastTickRun = 0;
    }

    @SubscribeEvent
    public void tickEnd(TickEvent.ClientTickEvent event) {
        if (event.type.equals(TickEvent.Type.CLIENT)) {
            Screen curScreen = Minecraft.getInstance().currentScreen;
            if (curScreen != null) {
                onTickInGui(curScreen);
            } else {
                this.inMenu = false;
                onTickInGame();
            }
        }
    }
}
