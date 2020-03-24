package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

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

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeFloat(this.health);
    }

    @Override
    public void decode(PacketBuffer buffer) {
        this.entityId = buffer.readInt();
        this.health = buffer.readFloat();
    }

    public boolean onMessage(MoCMessageHealth message, Supplier<NetworkEvent.Context> ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageHealth - entityId:%s, health:%s", this.entityId, this.health);
    }
}
