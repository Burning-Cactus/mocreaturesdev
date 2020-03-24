package drzhark.mocreatures.network.message;

import com.sun.xml.internal.ws.api.message.Packet;
import drzhark.mocreatures.entity.IMoCEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageEntityDive implements IMoCMessage {

    public MoCMessageEntityDive() {
    }

    @Override
    public void encode(PacketBuffer buffer) {
    }

    @Override
    public void decode(PacketBuffer buffer) {
    }

    public boolean onMessage(MoCMessageEntityDive message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get(); context.
        if (ctx.getServerHandler().player.getRidingEntity() != null && ctx.getServerHandler().player.getRidingEntity() instanceof IMoCEntity) {
            ((IMoCEntity) ctx.getServerHandler().player.getRidingEntity()).makeEntityDive();
        }
        return null;
    }
}
