package drzhark.mocreatures;

import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.thread.SidedThreadGroup;
import net.minecraftforge.fml.common.thread.SidedThreadGroups;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.UUID;

public class MoCPetData {

    private CompoundNBT ownerData = new CompoundNBT();
    private ListNBT tamedList = new ListNBT();
    private BitSet IDMap = new BitSet(Long.SIZE << 4);
    @SuppressWarnings("unused")
    private final UUID ownerUniqueId;
    private ArrayList<Integer> usedPetIds = new ArrayList<Integer>();

    public MoCPetData(IMoCTameable pet) { //TODO: the thread method isn't a very good way to set the owner ID. It would be best if we can find a way to make a world.isRemote check.
        this.ownerData.put("TamedList", this.tamedList);
        this.ownerUniqueId = Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER ? pet.getOwnerId() : Minecraft.getInstance().player.getUniqueID();
    }

    public MoCPetData(CompoundNBT nbt, UUID owner) {
        this.ownerData = nbt;
        this.tamedList = nbt.getList("TamedList", 10);
        this.ownerUniqueId = owner;
        this.loadPetDataMap(nbt.getCompound("PetIdData"));
    }

    public int addPet(IMoCTameable pet) {
        BlockPos coords = new BlockPos(((Entity) pet).chunkCoordX, ((Entity) pet).chunkCoordY, ((Entity) pet).chunkCoordZ);
        CompoundNBT petNBT = MoCTools.getEntityData((Entity) pet);
        if (this.tamedList != null) {
            int id = getNextFreePetId();
            petNBT.putInt("PetId", id);
            CompoundNBT petData = (CompoundNBT) petNBT.copy();
            petData.putInt("ChunkX", coords.getX());
            petData.putInt("ChunkY", coords.getY());
            petData.putInt("ChunkZ", coords.getZ());
            petData.putInt("Dimension", ((Entity) pet).world.dimension.getType().getId());
            this.tamedList.add(petData);
            this.ownerData.put("PetIdData", savePetDataMap());
            return id;
        } else {
            return -1;
        }
    }

    public boolean removePet(int id) {
        for (int i = 0; i < this.tamedList.size(); i++) {
            CompoundNBT nbt = this.tamedList.getCompound(i);
            if (nbt.contains("PetId") && nbt.getInt("PetId") == id) {
                this.tamedList.remove(i);
                this.usedPetIds.remove(new Integer(id));
                this.IDMap.clear(id); // clear bit so it can be reused again
                if (this.usedPetIds.size() == 0) {
                    this.IDMap.clear(); // fixes bug with ID 0 not able to be used again
                }
                this.ownerData.put("PetIdData", savePetDataMap());
                return true;
            }
        }
        return false;
    }

    public CompoundNBT getPetData(int id) {
        if (this.tamedList != null) {
            for (int i = 0; i < this.tamedList.size(); i++) {
                CompoundNBT nbt = this.tamedList.getCompound(i);
                if (nbt.contains("PetId") && nbt.getInt("PetId") == id) {
                    return nbt;
                }
            }
        }
        return null;
    }

    public CompoundNBT getOwnerRootNBT() {
        return this.ownerData;
    }

    public ListNBT getTamedList() {
        return this.tamedList;
    }

    public String getOwner() {
        if (this.ownerData != null) {
            return this.ownerData.getString("Owner");
        } else {
            return null;
        }
    }

    public boolean getInAmulet(int petId) {
        CompoundNBT petData = getPetData(petId);
        if (petData != null) {
            return petData.getBoolean("InAmulet");
        }
        return false;
    }

    public void setInAmulet(int petId, boolean flag) {
        CompoundNBT petData = getPetData(petId);
        if (petData != null) {
            petData.putBoolean("InAmulet", flag);
        }
    }

    /**
     * Return the next free pet ID.
     *
     * @return the next free pet ID
     */
    public int getNextFreePetId() {
        int next = 0;
        while (true) {
            next = this.IDMap.nextClearBit(next);
            if (this.usedPetIds.contains(new Integer(next))) {
                this.IDMap.set(next);
            } else {
                this.usedPetIds.add(new Integer(next));
                return next;
            }
        }
    }

    public CompoundNBT savePetDataMap() {
        int[] data = new int[(this.IDMap.length() + Integer.SIZE - 1) / Integer.SIZE];
        CompoundNBT dataMap = new CompoundNBT();
        for (int i = 0; i < data.length; i++) {
            int val = 0;
            for (int j = 0; j < Integer.SIZE; j++) {
                val |= this.IDMap.get(i * Integer.SIZE + j) ? (1 << j) : 0;
            }
            data[i] = val;
        }
        dataMap.putIntArray("PetIdArray", data);
        return dataMap;
    }

    public void loadPetDataMap(CompoundNBT compoundTag) {
        if (compoundTag == null) {
            this.IDMap.clear();
        } else {
            int[] intArray = compoundTag.getIntArray("PetIdArray");
            for (int i = 0; i < intArray.length; i++) {
                for (int j = 0; j < Integer.SIZE; j++) {
                    this.IDMap.set(i * Integer.SIZE + j, (intArray[i] & (1 << j)) != 0);
                }
            }
            // populate our usedPetIds
            int next = 0;
            while (true) {
                next = this.IDMap.nextClearBit(next);
                if (!this.usedPetIds.contains(new Integer(next))) {
                    this.usedPetIds.add(new Integer(next));
                } else {
                    break;
                }
            }
        }
    }
}
