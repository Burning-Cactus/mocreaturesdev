package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.IMoCTameable;
import drzhark.mocreatures.network.MoCMessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageHeart implements IMoCMessage {

    public int entityId;

    public MoCMessageHeart() {
    }

    public MoCMessageHeart(int entityId) {
        this.entityId = entityId;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
    }

    public static MoCMessageHeart decode(PacketBuffer buffer) {
        return new MoCMessageHeart(buffer.readInt());
    }

    public static boolean onMessage(MoCMessageHeart message, Supplier<NetworkEvent.Context> ctx) {
//        MoCMessageHandler.handleMessage(message, ctx);
//        return true;
        NetworkEvent.Context context = ctx.get();
        Entity entity = null;
        while (entity == null) {
            entity = Minecraft.getInstance().level.getEntity(message.entityId);
            if (entity != null) {
                if (entity instanceof IMoCTameable) {
                    ((IMoCTameable)entity).spawnHeart();
                }
            }
        }
        context.setPacketHandled(true);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageHeart - entityId:%s", this.entityId);
    }
}
