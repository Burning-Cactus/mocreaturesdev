package drzhark.mocreatures.network.message;

import drzhark.mocreatures.network.MoCMessageHandler;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageNameGUI {

    public int entityId;

    public MoCMessageNameGUI() {
    }

    public MoCMessageNameGUI(int entityId) {
        this.entityId = entityId;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
    }

    public static MoCMessageNameGUI decode(PacketBuffer buffer) {
        return new MoCMessageNameGUI(buffer.readInt());
    }

    public static boolean onMessage(MoCMessageNameGUI message, Supplier<NetworkEvent.Context> ctx) {
        if (ctx == Dist.CLIENT) {
            handleClientMessage(message, ctx);
        }
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public void handleClientMessage(MoCMessageNameGUI message, Supplier<NetworkEvent.Context> ctx) {
        MoCMessageHandler.handleMessage(message, ctx);
    }

    @Override
    public String toString() {
        return String.format("MoCMessageNameGUI - entityId:%s", this.entityId);
    }
}
