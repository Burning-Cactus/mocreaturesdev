package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageShuffle implements IMoCMessage {

    public int entityId;
    public boolean flag;

    public MoCMessageShuffle() {
    }

    public MoCMessageShuffle(int entityId, boolean flag) {
        this.entityId = entityId;
        this.flag = flag;
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeBoolean(this.flag);
    }

    @Override
    public void decode(PacketBuffer buffer) {
        this.entityId = buffer.readInt();
        this.flag = buffer.readBoolean();
    }

    public boolean onMessage(MoCMessageShuffle message, Supplier<NetworkEvent.Context> ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageShuffle - entityId:%s, flag:%s", this.entityId, this.flag);
    }
}
