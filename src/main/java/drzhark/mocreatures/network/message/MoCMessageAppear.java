package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageAppear implements IMoCMessage {

    public int entityId;

    public MoCMessageAppear() {
    }

    public MoCMessageAppear(int entityId) {
        this.entityId = entityId;
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
    }

    @Override
    public void decode(PacketBuffer buffer) {
        this.entityId = buffer.readInt();
    }

    public boolean onMessage(MoCMessageAppear message, Supplier<NetworkEvent.Context> ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageAppear - entityId:%s", this.entityId);
    }
}
