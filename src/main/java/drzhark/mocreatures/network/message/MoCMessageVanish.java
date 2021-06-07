package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.network.MoCMessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class MoCMessageVanish implements IMoCMessage {

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
//        MoCMessageHandler.handleMessage(message, ctx);
//        return true;
        NetworkEvent.Context context = ctx.get();
        Iterable<Entity> entList = Minecraft.getInstance().level.entitiesForRendering();
        for (Entity ent : entList) {
            if (ent.getId() == message.entityId && ent instanceof MoCEntityHorse) {
                ((MoCEntityHorse) ent).setVanishC((byte) 1);
                break;
            }
        }
        context.setPacketHandled(true);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageVanish - entityId:%s", this.entityId);
    }
}
