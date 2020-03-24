package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageVanish {

    public int entityId;

    public MoCMessageVanish() {
    }

    public MoCMessageVanish(int entityId) {
        this.entityId = entityId;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
    }

    public static MoCMessageVanish decode(PacketBuffer buffer) {
        return new MoCMessageVanish(buffer.readInt());
    }

    public static boolean onMessage(MoCMessageVanish message, Supplier<NetworkEvent.Context> ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageVanish - entityId:%s", this.entityId);
    }
}
