package drzhark.mocreatures.handlers;

import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MoCPlayerTracker {

    @SubscribeEvent
    public void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        PlayerEntity player = event.getPlayer();
        if (player.getRidingEntity() != null && (player.getRidingEntity() instanceof IMoCTameable)) {
            IMoCTameable mocEntity = (IMoCTameable) player.getRidingEntity();
            mocEntity.setRiderDisconnecting(true);
        }
    }
}
