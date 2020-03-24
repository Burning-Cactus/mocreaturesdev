package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageTwoBytes implements IMoCMessage {

    public int entityId;
    public byte slot;
    public byte value;

    public MoCMessageTwoBytes() {
    }

    public MoCMessageTwoBytes(int entityId, byte slot, byte value) {
        this.entityId = entityId;
        this.slot = slot;
        this.value = value;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeByte(this.slot);
        buffer.writeByte(this.value);
    }

    public static MoCMessageTwoBytes decode(PacketBuffer buffer) {
        return new MoCMessageTwoBytes(buffer.readInt(), buffer.readByte(), buffer.readByte());
    }

    public static boolean onMessage(MoCMessageTwoBytes message, Supplier<NetworkEvent.Context> ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageTwoBytes - entityId:%s, slot:%s, value:%s", this.entityId, this.slot, this.value);
    }
}
