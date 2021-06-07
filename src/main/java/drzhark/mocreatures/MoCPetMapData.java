package drzhark.mocreatures;

import com.google.common.collect.Maps;
import drzhark.mocreatures.entity.IMoCTameable;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.world.storage.WorldSavedData;

import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

public class MoCPetMapData extends WorldSavedData {

    private Map<UUID, MoCPetData> petMap = Maps.newHashMap();

    public MoCPetMapData(String par1Str) {
        super(par1Str);
        this.setDirty();
    }

    /**
     * Get a list of pets.
     */
    public MoCPetData getPetData(UUID ownerUniqueId) {
        return this.petMap.get(ownerUniqueId);
    }

    public Map<UUID, MoCPetData> getPetMap() {
        return this.petMap;
    }

    public boolean removeOwnerPet(IMoCTameable pet, int petId) {
        if (this.petMap.get(pet.getOwnerId()) != null) // required since getInteger will always return 0 if no key is found
        {
            if (this.petMap.get(pet.getOwnerId()).removePet(petId)) {
                this.setDirty();
                pet.setOwnerPetId(-1);
                return true;
            }
        }
        return false;
    }

    public void updateOwnerPet(IMoCTameable pet) {
        this.setDirty();
        if (pet.getOwnerPetId() == -1 || this.petMap.get(pet.getOwnerId()) == null) {
            UUID owner = pet.getOwnerId();
            MoCPetData petData = null;
            int id = -1;
            if (this.petMap.containsKey(owner)) {
                petData = this.petMap.get(owner);
                id = petData.addPet(pet);
            } else // create new pet data
            {
                petData = new MoCPetData(pet);
                id = petData.addPet(pet);
                this.petMap.put(owner, petData);
            }
            pet.setOwnerPetId(id);
        } else {
            // update pet data
            UUID owner = pet.getOwnerId();
            MoCPetData petData = this.getPetData(owner);
            CompoundNBT rootNBT = petData.getOwnerRootNBT();
            ListNBT tag = rootNBT.getList("TamedList", 10);
            int id = -1;
            id = pet.getOwnerPetId();

            for (int i = 0; i < tag.size(); i++) {
                CompoundNBT nbt = tag.getCompound(i);
                if (nbt.getInt("PetId") == id) {
                    // Update what we need for commands
                    nbt.put("Pos", this.newDoubleNBTList(new double[] {((Entity) pet).getX(), ((Entity) pet).getY(), ((Entity) pet).getZ()}));
                    nbt.putInt("ChunkX", ((Entity) pet).xChunk);
                    nbt.putInt("ChunkY", ((Entity) pet).yChunk);
                    nbt.putInt("ChunkZ", ((Entity) pet).zChunk);
//                    nbt.putInt("Dimension", ((Entity) pet).world.getDimension().getType().getId());
                    nbt.putInt("PetId", pet.getOwnerPetId());
                }
            }
        }
    }

    protected ListNBT newDoubleNBTList(double... par1ArrayOfDouble) {
        ListNBT nbttaglist = new ListNBT();
        double[] adouble = par1ArrayOfDouble;
        int i = par1ArrayOfDouble.length;

        for (int j = 0; j < i; ++j) {
            double d1 = adouble[j];
//            nbttaglist.add(new DoubleNBT(d1));
        }

        return nbttaglist;
    }

    public boolean isExistingPet(UUID owner, IMoCTameable pet) {
        MoCPetData petData = MoCreatures.instance.mapData.getPetData(owner);
        if (petData != null) {
            ListNBT tag = petData.getTamedList();
            for (int i = 0; i < tag.size(); i++) {
                CompoundNBT nbt = tag.getCompound(i);
                if (nbt.getInt("PetId") == pet.getOwnerPetId()) {
                    // found existing pet
                    return true;
                }
            }
        }
        return false;
    }

//    public void forceSave() {
//        if (DimensionManager.getWorld(0) != null) {
//            SaveHandler saveHandler = DimensionManager.getWorld(0).getSaveHandler();
//            if (saveHandler != null) {
//                try {
//                    File file1 = saveHandler.getMapFileFromName("mocreatures");
//
//                    if (file1 != null) {
//                        CompoundNBT nbttagcompound = new CompoundNBT();
//                        this.write(nbttagcompound);
//                        CompoundNBT nbttagcompound1 = new CompoundNBT();
//                        nbttagcompound1.put("data", nbttagcompound);
//                        FileOutputStream fileoutputstream = new FileOutputStream(file1);
//                        CompressedStreamTools.writeCompressed(nbttagcompound1, fileoutputstream);
//                        fileoutputstream.close();
//                    }
//                } catch (Exception exception) {
//                    exception.printStackTrace();
//                }
//            }
//        }
//    }

    /**
     * reads in data from the NBTTagCompound into this MapDataBase
     */
    @Override
    public void load(CompoundNBT par1NBTTagCompound) {
        Iterator<String> iterator = par1NBTTagCompound.getAllKeys().iterator();
        while (iterator.hasNext()) {
            String s = (String) iterator.next();
            CompoundNBT nbt = (CompoundNBT) par1NBTTagCompound.get(s);
            UUID ownerUniqueId = UUID.fromString(s);

            if (!this.petMap.containsKey(ownerUniqueId)) {
                this.petMap.put(ownerUniqueId, new MoCPetData(nbt, ownerUniqueId));
            }
        }
    }

    /**
     * write data to NBTTagCompound from this MapDataBase, similar to Entities
     * and TileEntities
     * @return 
     */
    @Override
    public CompoundNBT save(CompoundNBT par1NBTTagCompound) {
        for (Map.Entry<UUID, MoCPetData> ownerEntry : this.petMap.entrySet()) {
            try {
            if (ownerEntry.getKey()!= null)
            {
            par1NBTTagCompound.put(ownerEntry.getKey().toString(), ownerEntry.getValue().getOwnerRootNBT());
        }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return par1NBTTagCompound;
    }
}
