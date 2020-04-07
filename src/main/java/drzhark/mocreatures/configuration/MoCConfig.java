package drzhark.mocreatures.configuration;

import drzhark.mocreatures.MoCConstants;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
public class MoCConfig {

    public static CommonConfig COMMON_CONFIG;
    public static ClientConfig CLIENT_CONFIG;


    public static class CommonConfig {
        public static General GENERAL;
        public static Global GLOBAL;
        public static Ownership OWNERSHIP;


        public CommonConfig(ForgeConfigSpec.Builder builder) {

        }
        public static class General {

            public Creature creatureSettings = new Creature();
            public Monster monsterSettings = new Monster();
            public WaterMob waterMobSettings = new WaterMob();

            public class Creature {
                public ForgeConfigSpec.BooleanValue attackHorses; //Allow creatures to attack horses
                public ForgeConfigSpec.BooleanValue attackWolves; //Allow creatures to attack wolves
                public ForgeConfigSpec.BooleanValue destroyDrops;
                public ForgeConfigSpec.BooleanValue easyBreeding; //Make horse breeding simpler
                public ForgeConfigSpec.BooleanValue elephantBulldozer;
                public ForgeConfigSpec.BooleanValue enableHunters;
                public ForgeConfigSpec.BooleanValue killAllVillagers;
                public ForgeConfigSpec.IntValue motherWyvernEggDropChance;
                public ForgeConfigSpec.IntValue ostrichEggDropChance;
                public ForgeConfigSpec.IntValue rareItemDropChance;
                public ForgeConfigSpec.BooleanValue staticBed;
                public ForgeConfigSpec.BooleanValue staticLitter;
                public ForgeConfigSpec.IntValue wyvernEggDropChance;
                public ForgeConfigSpec.IntValue zebraChance;
                public ForgeConfigSpec.IntValue despawnLightLevel;
            }

            public class Monster {
                public ForgeConfigSpec.IntValue caveOgreChance;
                public ForgeConfigSpec.DoubleValue caveOgreStrength;
                public ForgeConfigSpec.IntValue fireOgreChance;
                public ForgeConfigSpec.DoubleValue fireOgreStrength;
                public ForgeConfigSpec.IntValue ogreAttackRange;
                public ForgeConfigSpec.DoubleValue ogreStrength;
                public ForgeConfigSpec.BooleanValue golemDestroyBlocks;
            }

            public class WaterMob {
                public ForgeConfigSpec.BooleanValue attackDolphins;
            }
        }

        public static class Global {
            public ForgeConfigSpec.BooleanValue allowInstaSpawn;
            public ForgeConfigSpec.BooleanValue animateTextures;
            public ForgeConfigSpec.BooleanValue debug;
            public ForgeConfigSpec.BooleanValue displayPetHealth;
            public ForgeConfigSpec.BooleanValue displayPetIcons;
            public ForgeConfigSpec.BooleanValue displayPetName;
            public ForgeConfigSpec.BooleanValue forceDespawns;
            public ForgeConfigSpec.IntValue particleFX;
        }

        public static class Ownership {
            public ForgeConfigSpec.BooleanValue enableOwnership;
            public ForgeConfigSpec.BooleanValue enableResetOwnerScroll;
            public ForgeConfigSpec.IntValue maxTamedPerOP;
            public ForgeConfigSpec.IntValue maxTamedPerPlayer;
        }

        public static class SpawnSettings {

        }
    }




    public static class ClientConfig {
        public ClientConfig(ForgeConfigSpec.Builder builder) {

        }

    }
}
