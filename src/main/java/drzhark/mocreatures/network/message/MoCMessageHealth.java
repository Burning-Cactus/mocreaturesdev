package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.network.MoCMessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.function.Supplier;

public class MoCMessageHealth implements IMoCMessage {

    public int entityId;
    public float health;

    public MoCMessageHealth() {
    }

    public MoCMessageHealth(int entityId, float health) {
        this.entityId = entityId;
        this.health = health;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeFloat(this.health);
    }

    public static MoCMessageHealth decode(PacketBuffer buffer) {
        return new MoCMessageHealth(buffer.readInt(), buffer.readFloat());
    }

    public static boolean onMessage(MoCMessageHealth message, Supplier<NetworkEvent.Context> ctx) {
//        MoCMessageHandler.handleMessage(message, ctx);
//        return true;
        NetworkEvent.Context context = ctx.get();
        Iterable<Entity> entList = Minecraft.getInstance().world.getAllEntities();
        for (Entity ent : entList) {
            if (ent.getEntityId() == message.entityId && ent instanceof LivingEntity) {
                ((LivingEntity) ent).setHealth(message.health);
                break;
            }
        }
        context.setPacketHandled(true);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageHealth - entityId:%s, health:%s", this.entityId, this.health);
    }
}
