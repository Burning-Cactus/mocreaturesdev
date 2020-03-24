package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageExplode {

    public int entityId;

    public MoCMessageExplode() {
    }

    public MoCMessageExplode(int entityId) {
        this.entityId = entityId;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
    }

    public static MoCMessageExplode decode(PacketBuffer buffer) {
        return new MoCMessageExplode(buffer.readInt());
    }

    public static boolean onMessage(MoCMessageExplode message, Supplier<NetworkEvent.Context> ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageExplode - entityId:%s", this.entityId);
    }
}
