package drzhark.mocreatures.network.message;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageEntityJump implements IMoCMessage {

    public MoCMessageEntityJump() {
    }

    public void encode(PacketBuffer buffer) {
    }

    public static MoCMessageEntityJump decode(PacketBuffer buffer) {
        return new MoCMessageEntityJump();
    }

    public static boolean onMessage(MoCMessageEntityJump message, Supplier<NetworkEvent.Context> ctx) {
//        if (ctx.getServerHandler().player.getRidingEntity() != null && ctx.getServerHandler().player.getRidingEntity() instanceof IMoCEntity) {
//            ((IMoCEntity) ctx.getServerHandler().player.getRidingEntity()).makeEntityJump();
//        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageEntityJump");
    }
}
