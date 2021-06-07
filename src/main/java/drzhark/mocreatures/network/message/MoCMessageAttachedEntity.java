package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
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

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.sourceEntityId);
        buffer.writeInt(this.targetEntityId);
    }

    public static MoCMessageAttachedEntity decode(PacketBuffer buffer) {
        return new MoCMessageAttachedEntity(buffer.readInt(), buffer.readInt());
    }

    public static Boolean onMessage(MoCMessageAttachedEntity message, Supplier<NetworkEvent.Context> ctx) {
//        MoCMessageHandler.handleMessage(message, ctx);
//        return true;

        NetworkEvent.Context context = ctx.get();
        if(context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> {
                Object var2 = Minecraft.getInstance().level.getEntity(message.sourceEntityId);
                Entity var3 = Minecraft.getInstance().level.getEntity(message.targetEntityId);
                if (var2 != null) {
                    ((Entity) var2).startRiding(var3);
                }
            });
        }
        context.setPacketHandled(true);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageAttachedEntity - sourceEntityId:%s, targetEntityId:%s", this.sourceEntityId, this.targetEntityId);
    }
}
