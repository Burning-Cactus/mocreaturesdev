package drzhark.mocreatures.network.message;

import drzhark.mocreatures.client.MoCClientProxy;
import drzhark.mocreatures.client.gui.helpers.MoCGUIEntityNamer;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.network.MoCMessageHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageNameGUI implements IMoCMessage {

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
//        if (ctx == Dist.CLIENT) {
//            handleClientMessage(message, ctx);
//        }
        NetworkEvent.Context context = ctx.get();
        if(context.getDirection().getReceptionSide().isClient()) {
            Entity entity = Minecraft.getInstance().world.getEntityByID(message.entityId);
            Minecraft.getInstance().displayGuiScreen(new MoCGUIEntityNamer(((IMoCEntity) entity), ((IMoCEntity) entity).getPetName()));
            context.setPacketHandled(true);
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
