package drzhark.mocreatures;

import drzhark.customspawner.utils.CMSUtils;
import drzhark.mocreatures.util.MoCLog;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.passive.horse.HorseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
import net.minecraft.world.server.ServerWorld;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MoCDespawner {

    public static boolean debug = false;
    public static int despawnLightLevel = 7;
    public static int despawnTickRate = 111;
    public List<Biome> biomeList = new ArrayList<Biome>();
    private List<Class<? extends LivingEntity>> vanillaClassList;

    public MoCDespawner() {
        this.biomeList = new ArrayList<Biome>();
        try {
            Iterator<Biome> iterator = Biome.BIOMES.iterator();
            while (iterator.hasNext()) {
                Biome biome = iterator.next();
                if (biome == null) {
                    continue;
                }

                this.biomeList.add(biome);
            }

            this.vanillaClassList = new ArrayList<Class<? extends LivingEntity>>();
            this.vanillaClassList.add(ChickenEntity.class);
            this.vanillaClassList.add(CowEntity.class);
            this.vanillaClassList.add(PigEntity.class);
            this.vanillaClassList.add(SheepEntity.class);
            this.vanillaClassList.add(WolfEntity.class);
            this.vanillaClassList.add(SquidEntity.class);
            this.vanillaClassList.add(OcelotEntity.class);
            this.vanillaClassList.add(BatEntity.class);
            this.vanillaClassList.add(HorseEntity.class);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    //New DesPawner stuff
    protected final static int
            entityDespawnCheck(ServerWorld world, LivingEntity entityliving, int minDespawnLightLevel, int maxDespawnLightLevel) {
        if (entityliving instanceof WolfEntity && ((WolfEntity) entityliving).isTamed()) {
            return 0;
        }
        if (entityliving instanceof OcelotEntity && ((OcelotEntity) entityliving).isTamed()) {
            return 0;
        }
        if (!isValidDespawnLightLevel(entityliving, world, minDespawnLightLevel, maxDespawnLightLevel)) {
            return 0;
        }

        PlayerEntity entityplayer = world.getClosestPlayer(entityliving, -1D);
        if (entityplayer != null) //entityliving.canDespawn() &&
        {
            double d = ((Entity) (entityplayer)).getPosX() - entityliving.getPosX();
            double d1 = ((Entity) (entityplayer)).getPosY() - entityliving.getPosY();
            double d2 = ((Entity) (entityplayer)).getPosZ() - entityliving.getPosZ();
            double d3 = d * d + d1 * d1 + d2 * d2;
            if (d3 > 16384D) {
                entityliving.setDead();
                return 1;
            }
            if (entityliving.getIdleTime() > 600 && world.rand.nextInt(800) == 0) {
                if (d3 < 1024D) {
                    //entityliving.attackEntityFrom(DamageSource.generic, 0);
                } else {
                    entityliving.setDead();
                    return 1;
                }
            }
        }
        return 0;
    }

    public final static int despawnVanillaAnimals(ServerWorld world, int minDespawnLightLevel, int maxDespawnLightLevel) {
        int count = 0;
        for (int j = 0; j < world.loadedEntityList.size(); j++) {
            Entity entity = (Entity) world.loadedEntityList.get(j);
            if (!(entity instanceof LivingEntity)) {
                continue;
            }
            if ((entity instanceof HorseEntity || entity instanceof CowEntity || entity instanceof SheepEntity || entity instanceof PigEntity
                    || entity instanceof OcelotEntity || entity instanceof ChickenEntity || entity instanceof SquidEntity
                    || entity instanceof WolfEntity || entity instanceof MooshroomEntity)) {
                count += entityDespawnCheck(world, (LivingEntity) entity, minDespawnLightLevel, maxDespawnLightLevel);
            }
        }
        return count;
    }

    public final int countEntities(Class<? extends LivingEntity> class1, World world) {
        int i = 0;
        for (int j = 0; j < world.loadedEntityList.size(); j++) {
            Entity entity = world.loadedEntityList.get(j);
            if (class1.isAssignableFrom(entity.getClass())) {
                i++;
            }
        }

        return i;
    }

    public static boolean isValidLightLevel(Entity entity, ServerWorld world, int lightLevel, boolean checkAmbientLightLevel) {
        if (entity.isCreatureType(EntityClassification.MONSTER, false)) {
            return true;
        } else if (entity.isCreatureType(EntityClassification.AMBIENT, false) && !checkAmbientLightLevel) {
            return true;
        } else if (!entity.isCreatureType(EntityClassification.CREATURE, false)) {
            return true;
        }
        int x = MathHelper.floor(entity.getPosX());
        int y = MathHelper.floor(entity.getBoundingBox().minY);
        int z = MathHelper.floor(entity.getPosZ());
        int i = 0;
        if (y >= 0) {
            if (y >= 256) {
                y = 255;
            }
            i = getLightFromNeighbors(world.getChunk(x >> 4, z >> 4), x & 15, y, z & 15);
        }
        if (i > lightLevel) {
            if (debug) {
                MoCLog.logger.info("Denied spawn! for " + entity.getName() + ". LightLevel over threshold of " + lightLevel
                        + " in dimension " + world.dimension.getType().getId() + " at coords " + x + ", " + y + ", " + z);
            }
        }
        return i <= lightLevel;
    }

    public static boolean isValidDespawnLightLevel(Entity entity, World world, int minDespawnLightLevel, int maxDespawnLightLevel) {
        int x = MathHelper.floor(entity.getPosX());
        int y = MathHelper.floor(entity.getBoundingBox().minY);
        int z = MathHelper.floor(entity.getPosZ());
        int blockLightLevel = 0;
        if (y >= 0) {
            if (y >= 256) {
                y = 255;
            }
            blockLightLevel = CMSUtils.getLightFromNeighbors(world.getChunk(x >> 4, z >> 4), x & 15, y, z & 15);
        }
        if (blockLightLevel < minDespawnLightLevel && maxDespawnLightLevel != -1) {
            //if (debug) CMSUtils.getEnvironment(world).envLog.logger.info("Denied spawn! for " + entity.getName() + blockLightLevel + " under minimum threshold of " + minDespawnLightLevel + " in dimension " + world.provider.getDimensionType().getId() + " at coords " + x + ", " + y + ", " + z);
            return false;
        } else if (blockLightLevel > maxDespawnLightLevel && maxDespawnLightLevel != -1) {
            //if (debug) CMSUtils.getEnvironment(world).envLog.logger.info("Denied spawn! for " + entity.getName() + blockLightLevel + " over maximum threshold of " + maxDespawnLightLevel + " in dimension " + world.provider.getDimensionType().getId() + " at coords " + x + ", " + y + ", " + z);
            return false;
        }
        return true;
    }

    /**
     * Gets the amount of light on a block without taking into account sunlight
     */
    public static int getLightFromNeighbors(Chunk chunk, int x, int y, int z) {
        ExtendedBlockStorage extendedblockstorage = chunk.getBlockStorageArray()[y >> 4];

        if (extendedblockstorage == null) {
            return 0;
        } else {
            return extendedblockstorage.getBlockLight(x, y & 15, z);
        }
    }
}
