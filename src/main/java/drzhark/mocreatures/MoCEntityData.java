package drzhark.mocreatures;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;

import java.util.List;

public class MoCEntityData {
/*

    private EntityClassification typeOfCreature;
    private SpawnListEntry spawnListEntry;
    private String entityName;
    private boolean canSpawn = true;
    private int entityId;
    private List<Type> biomeTypes;

    private int frequency = 8;
    private int minGroup = 1;
    private int maxGroup = 1;
    private int maxSpawnInChunk = 1;

    public MoCEntityData(String name, int maxchunk, EntityClassification type, SpawnListEntry spawnListEntry, List<Type> biomeTypes) {
        this.entityName = name;
        this.typeOfCreature = type;
        this.biomeTypes = biomeTypes;
        this.frequency = spawnListEntry.itemWeight;
        this.minGroup = spawnListEntry.minGroupCount;
        this.maxGroup = spawnListEntry.maxGroupCount;
        this.maxSpawnInChunk = maxchunk;
        this.spawnListEntry = spawnListEntry;
        MoCreatures.entityMap.put(spawnListEntry.entityType, this);
    }

    public MoCEntityData(String name, int id, int maxchunk, EntityClassification type, SpawnListEntry spawnListEntry, List<Type> biomeTypes) {
        this.entityId = id;
        this.entityName = name;
        this.typeOfCreature = type;
        this.biomeTypes = biomeTypes;
        this.frequency = spawnListEntry.itemWeight;
        this.minGroup = spawnListEntry.minGroupCount;
        this.maxGroup = spawnListEntry.maxGroupCount;
        this.maxSpawnInChunk = maxchunk;
        this.spawnListEntry = spawnListEntry;
        MoCreatures.entityMap.put(spawnListEntry.entityType, this);
    }

    public EntityType<?> getEntityType() {
        return this.spawnListEntry.entityType;
    }

    public EntityClassification getType() {
        if (this.typeOfCreature != null) {
            return this.typeOfCreature;
        }
        return null;
    }

    public void setType(EntityClassification type) {
        this.typeOfCreature = type;
    }

    public List<Type> getBiomeTypes() {
        return this.biomeTypes;
    }

    public int getEntityID() {
        return this.entityId;
    }

    public void setEntityID(int id) {
        this.entityId = id;
    }

    public int getFrequency() {
        return this.frequency;
    }

    public void setFrequency(int freq) {
        if (freq <= 0) {
            this.frequency = 0;
        } else {
            this.frequency = freq;
        }
    }

    public int getMinSpawn() {
        return this.minGroup;
    }

    public void setMinSpawn(int min) {
        if (min <= 0) {
            this.minGroup = 0;
        } else {
            this.minGroup = min;
        }
    }

    public int getMaxSpawn() {
        return this.maxGroup;
    }

    public void setMaxSpawn(int max) {
        if (max <= 0) {
            this.maxGroup = 0;
        } else {
            this.maxGroup = max;
        }
    }

    public int getMaxInChunk() {
        return this.maxSpawnInChunk;
    }

    public void setMaxInChunk(int max) {
        if (max <= 0) {
            this.maxSpawnInChunk = 0;
        } else {
            this.maxSpawnInChunk = max;
        }
    }

    public String getEntityName() {
        return this.entityName;
    }

    public void setEntityName(String name) {
        this.entityName = name;
    }

    public void setCanSpawn(boolean flag) {
        this.canSpawn = flag;
    }

    public boolean getCanSpawn() {
        return this.canSpawn;
    }

    public SpawnListEntry getSpawnListEntry() {
        return this.spawnListEntry;
    }
*/

}
