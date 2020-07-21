package drzhark.mocreatures.network.message;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageEntityDive implements IMoCMessage {

    public MoCMessageEntityDive() {
    }

    public void encode(PacketBuffer buffer) {
    }

    public static MoCMessageEntityDive decode(PacketBuffer buffer) {
        return new MoCMessageEntityDive();
    }

    public static boolean onMessage(MoCMessageEntityDive message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
//        if (ctx.get().getServerHandler().player.getRidingEntity() != null && ctx.getServerHandler().player.getRidingEntity() instanceof IMoCEntity) {
//            ((IMoCEntity) ctx.getServerHandler().player.getRidingEntity()).makeEntityDive();
//        }
        return true;
    }
}
