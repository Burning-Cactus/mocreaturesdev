package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageAppear {

    public int entityId;

    public MoCMessageAppear() {
    }

    public MoCMessageAppear(int entityId) {
        this.entityId = entityId;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
    }

    public static MoCMessageAppear decode(PacketBuffer buffer) {
        return new MoCMessageAppear(buffer.readInt());
    }

    public static boolean onMessage(MoCMessageAppear message, Supplier<NetworkEvent.Context> ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageAppear - entityId:%s", this.entityId);
    }
}
