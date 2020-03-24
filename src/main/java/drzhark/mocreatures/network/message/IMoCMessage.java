package drzhark.mocreatures.network.message;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public interface IMoCMessage {

    void encode(PacketBuffer buffer);
    void decode(PacketBuffer buffer);
}
