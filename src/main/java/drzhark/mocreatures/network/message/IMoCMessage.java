package drzhark.mocreatures.network.message;

import net.minecraft.network.PacketBuffer;

public interface IMoCMessage {
    void encode(PacketBuffer buffer);
    String toString();
}
