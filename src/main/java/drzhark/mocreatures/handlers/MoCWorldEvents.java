package drzhark.mocreatures.handlers;

import drzhark.mocreatures.registry.MoCEntities;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.world.MobSpawnInfoBuilder;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MoCWorldEvents {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public void onBiomeLoad(BiomeLoadingEvent event) {
        MobSpawnInfoBuilder spawns = event.getSpawns();
        switch (event.getCategory()) {
            case BEACH:
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.CRAB, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.DRAGONFLY, 6, 1, 2));
                break;
            case OCEAN:
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.CRAB, 8, 1, 2));
                break;
            case EXTREME_HILLS:
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.ANT, 7, 1, 4));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.BEE, 6, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.BUTTERFLY, 8, 1, 3));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.CRICKET, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.DRAGONFLY, 6, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.FIREFLY, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.FLY, 8, 1, 2));
                break;
            case FOREST:
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.ANT, 7, 1, 4));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.BEE, 6, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.BUTTERFLY, 8, 1, 3));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.CRICKET, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.DRAGONFLY, 6, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.FIREFLY, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.FLY, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.MAGGOT, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.SNAIL, 7, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.ROACH, 8, 1, 2));
                break;
            case JUNGLE:
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.ANT, 7, 1, 4));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.BEE, 6, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.BUTTERFLY, 8, 1, 3));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.CRICKET, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.DRAGONFLY, 6, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.FIREFLY, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.FLY, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.MAGGOT, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.SNAIL, 7, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.ROACH, 8, 1, 2));
                break;
            case MESA:
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.ANT, 7, 1, 4));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.BEE, 6, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.BUTTERFLY, 8, 1, 3));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.CRICKET, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.DRAGONFLY, 6, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.FIREFLY, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.FLY, 8, 1, 2));
                break;
            case PLAINS:
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.ANT, 7, 1, 4));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.BEE, 6, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.BUTTERFLY, 8, 1, 3));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.CRICKET, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.DRAGONFLY, 6, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.FIREFLY, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.FLY, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.MAGGOT, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.SNAIL, 7, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.ROACH, 8, 1, 2));
                break;
            case SWAMP:
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.ANT, 7, 1, 4));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.CRICKET, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.DRAGONFLY, 6, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.FIREFLY, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.FLY, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.MAGGOT, 8, 1, 2));
                spawns.addSpawn(EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntities.ROACH, 8, 1, 2));
                break;
        }
    }
}
