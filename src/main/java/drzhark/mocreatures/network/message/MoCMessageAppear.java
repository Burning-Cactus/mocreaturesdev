package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.network.MoCMessageHandler;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.Iterator;
import java.util.List;
import java.util.function.Supplier;

public class MoCMessageAppear implements IMoCMessage {

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
//        MoCMessageHandler.handleMessage(message, ctx);
//        return true;
        NetworkEvent.Context context = ctx.get();
        if (context.getDirection().getReceptionSide().isClient()) {
            context.enqueueWork(() -> {
                Iterable<Entity> entList = Minecraft.getInstance().level.entitiesForRendering();
                for (Entity ent : entList) {
                    if (ent.getId() == message.entityId && ent instanceof MoCEntityHorse) {
                        ((MoCEntityHorse) ent).MaterializeFX();
                        break;
                    }
                }
            });
        }
        context.setPacketHandled(true);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageAppear - entityId:%s", this.entityId);
    }
}
