package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageExplode implements IMoCMessage {

    public int entityId;

    public MoCMessageExplode() {
    }

    public MoCMessageExplode(int entityId) {
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

    public boolean onMessage(MoCMessageExplode message, Supplier<NetworkEvent.Context> ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageExplode - entityId:%s", this.entityId);
    }
}
