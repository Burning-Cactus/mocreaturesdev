package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageAttachedEntity implements IMoCMessage {

    public int sourceEntityId;
    public int targetEntityId;

    public MoCMessageAttachedEntity() {
    }

    public MoCMessageAttachedEntity(int sourceEntityId, int targetEntityId) {
        this.sourceEntityId = sourceEntityId;
        this.targetEntityId = targetEntityId;
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.sourceEntityId);
        buffer.writeInt(this.targetEntityId);
    }

    @Override
    public void decode(PacketBuffer buffer) {
        this.sourceEntityId = buffer.readInt();
        this.targetEntityId = buffer.readInt();
    }

    public Boolean onMessage(MoCMessageAttachedEntity message, Supplier<NetworkEvent.Context> ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageAttachedEntity - sourceEntityId:%s, targetEntityId:%s", this.sourceEntityId, this.targetEntityId);
    }
}
