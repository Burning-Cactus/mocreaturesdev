package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class EntityAITools {

    protected static boolean IsNearPlayer(LivingEntity entityliving, double d) {
        PlayerEntity entityplayer1 = entityliving.level.getNearestPlayer(entityliving, d);
        if (entityplayer1 != null) {
            return true;
        }
        return false;
    }

    protected static PlayerEntity getIMoCTameableOwner(IMoCTameable pet) {
        if (pet.getOwnerId() == null) {
            return null;
        }

        for (int i = 0; i < ((LivingEntity) pet).level.players().size(); ++i) {
            PlayerEntity entityplayer = (PlayerEntity) ((LivingEntity) pet).level.players().get(i);

            if (pet.getOwnerId().equals(entityplayer.getUUID())) {
                return entityplayer;
            }
        }
        return null;
    }
}
