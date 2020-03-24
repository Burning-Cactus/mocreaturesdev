package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageAnimation {

    public int entityId;
    public int animationType;

    public MoCMessageAnimation() {
    }

    public MoCMessageAnimation(int entityId, int animationType) {
        this.entityId = entityId;
        this.animationType = animationType;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.animationType);
    }

    public static MoCMessageAnimation decode(PacketBuffer buffer) {
        return new MoCMessageAnimation(buffer.readInt(), buffer.readInt());
    }

    public static boolean onMessage(MoCMessageAnimation message, Supplier<NetworkEvent.Context> ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageAnimation - entityId:%s, animationType:%s", this.entityId, this.animationType);
    }
}
