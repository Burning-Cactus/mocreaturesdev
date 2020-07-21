package drzhark.mocreatures.network.message;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.util.MoCLog;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

public class MoCMessageInstaSpawn implements IMoCMessage {

    public int entityId;
    public int numberToSpawn;

    public MoCMessageInstaSpawn() {
    }

    public MoCMessageInstaSpawn(int entityId, int numberToSpawn) {
        this.entityId = entityId;
        this.numberToSpawn = numberToSpawn;
    }

    public void encode(PacketBuffer buffer) {
        buffer.writeInt(this.entityId);
        buffer.writeInt(this.numberToSpawn);
    }

    public static MoCMessageInstaSpawn decode(PacketBuffer buffer) {
        return new MoCMessageInstaSpawn(buffer.readInt(), buffer.readInt());
    }

    public static boolean onMessage(MoCMessageInstaSpawn message, Supplier<NetworkEvent.Context> ctx) {
//        if ((MoCreatures.proxy.getProxyMode() == 1 && MoCreatures.proxy.allowInstaSpawn) || MoCreatures.proxy.getProxyMode() == 2) { // make sure the client has admin rights on server!
//            MoCTools.spawnNearPlayer(ctx.getServerHandler().player, message.entityId, message.numberToSpawn);
//            if (MoCConfig.COMMON_CONFIG.GLOBAL.debug.get()) {
//                MoCLog.logger.info("Player " + ctx.getServerHandler().player.getName() + " used MoC instaspawner and got "
//                        + message.numberToSpawn + " creatures spawned");
//            }
//        } else {
//            if (MoCConfig.COMMON_CONFIG.GLOBAL.debug.get()) {
//                MoCLog.logger.info("Player " + ctx.getServerHandler().player.getName()
//                        + " tried to use MoC instaspawner, but the allowInstaSpawn setting is set to " + MoCreatures.proxy.allowInstaSpawn);
//            }
//        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageInstaSpawn - entityId:%s, numberToSpawn:%s", this.entityId, this.numberToSpawn);
    }
}
