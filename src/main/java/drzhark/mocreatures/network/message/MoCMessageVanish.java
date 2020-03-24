package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageVanish implements IMoCMessage {

    public int entityId;

    public MoCMessageVanish() {
    }

    public MoCMessageVanish(int entityId) {
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

    public boolean onMessage(MoCMessageVanish message, Supplier<NetworkEvent.Context> ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageVanish - entityId:%s", this.entityId);
    }
}
