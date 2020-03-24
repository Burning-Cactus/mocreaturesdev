package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageAnimation implements IMoCMessage {

    public int entityId;
    public int animationType;

    public MoCMessageAnimation() {
    }

    public MoCMessageAnimation(int entityId, int animationType) {
        this.entityId = entityId;
        this.animationType = animationType;
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.animationType);
    }

    @Override
    public void decode(PacketBuffer buffer) {
        this.entityId = buffer.readInt();
        this.animationType = buffer.readInt();
    }

    public boolean onMessage(MoCMessageAnimation message, Supplier<NetworkEvent.Context> ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageAnimation - entityId:%s, animationType:%s", this.entityId, this.animationType);
    }
}
