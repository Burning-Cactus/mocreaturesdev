package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.entity.monster.MoCEntityOgre;
import drzhark.mocreatures.network.MoCMessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class MoCMessageExplode implements IMoCMessage {

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
//        MoCMessageHandler.handleMessage(message, ctx);
//        return true;
        NetworkEvent.Context context = ctx.get();
        if(context.getDirection().getReceptionSide().isClient()) {
            Iterable<Entity> entList = Minecraft.getInstance().level.entitiesForRendering();
            for(Entity ent : entList) {
                if(ent.getId() == message.entityId && ent instanceof MoCEntityOgre) {
                    ((MoCEntityOgre) ent).performDestroyBlastAttack();
                    break;
                }
            }
        }
        context.setPacketHandled(true);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageExplode - entityId:%s", this.entityId);
    }
}
