package drzhark.mocreatures.network.message;

import drzhark.mocreatures.MoCPetData;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.IMoCTameable;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;

public class MoCMessageUpdatePetName implements IMoCMessage {

    public String name;
    public int entityId;

    public MoCMessageUpdatePetName() {
    }

    public MoCMessageUpdatePetName(int entityId) {
        this.entityId = entityId;
    }

    public MoCMessageUpdatePetName(String name, int entityId) {
        this.entityId = entityId;
        this.name = name;
    }

    public void encode(PacketBuffer buffer) {
//        ByteBufUtils.writeUTF8String(buffer, this.name);
//        ByteBufUtils.writeVarInt(buffer, this.entityId, 5);
        buffer.writeString(name);
        buffer.writeVarInt(entityId);
    } //TODO: Figure out how to update from ByteBufUtils

    public static MoCMessageUpdatePetName decode(PacketBuffer buffer) {
//        this.name = ByteBufUtils.readUTF8String(buffer);
//        this.entityId = ByteBufUtils.readVarInt(buffer, 5);
        new MoCMessageUpdatePetName(buffer.readString(), buffer.readVarInt());
    }

    public static boolean onMessage(MoCMessageUpdatePetName message, Supplier<NetworkEvent.Context> ctx) {
        Entity pet = null;
        List<Entity> entList = ctx.getServerHandler().player.world.loadedEntityList;
        UUID ownerUniqueId = null;

        for (Entity ent : entList) {
            if (ent.getEntityId() == message.entityId && ent instanceof IMoCTameable) {
                ((IMoCEntity) ent).setPetName(message.name);
                ownerUniqueId = ((IMoCEntity) ent).getOwnerId();
                pet = ent;
                break;
            }
        }
        // update petdata
        MoCPetData petData = MoCreatures.instance.mapData.getPetData(ownerUniqueId);
        if (petData != null && pet != null && ((IMoCTameable) pet).getOwnerPetId() != -1) {
            int id = ((IMoCTameable) pet).getOwnerPetId();
            ListNBT tag = petData.getOwnerRootNBT().getList("TamedList", 10);
            for (int i = 0; i < tag.size(); i++) {
                CompoundNBT nbt = tag.getCompound(i);
                if (nbt.getInt("PetId") == id) {
                    nbt.putString("Name", message.name);
                    ((IMoCTameable) pet).setPetName(message.name);
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return String.format("MoCMessageUpdatePetName - entityId:%s, name:%s", this.entityId, this.name);
    }
}
