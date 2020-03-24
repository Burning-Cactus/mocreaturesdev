package drzhark.mocreatures.network.message;

import drzhark.mocreatures.entity.IMoCEntity;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageEntityJump implements IMoCMessage {

    public MoCMessageEntityJump() {
    }

    @Override
    public void encode(PacketBuffer buffer) {
    }

    @Override
    public void decode(PacketBuffer buffer) {
    }

    public boolean onMessage(MoCMessageEntityJump message, Supplier<NetworkEvent.Context> ctx) {
        if (ctx.getServerHandler().player.getRidingEntity() != null && ctx.getServerHandler().player.getRidingEntity() instanceof IMoCEntity) {
            ((IMoCEntity) ctx.getServerHandler().player.getRidingEntity()).makeEntityJump();
        }
        return null;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageEntityJump");
    }
}
