package drzhark.mocreatures.configuration;

import drzhark.mocreatures.MoCConstants;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
public class MoCConfig {

    public static CommonConfig COMMON_CONFIG;
    public static ClientConfig CLIENT_CONFIG;


    public static class CommonConfig {
        public General GENERAL = new General();
        public Global GLOBAL = new Global();
        public Ownership OWNERSHIP = new Ownership();

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("General settings for Mo' Creatures.").push("General Settings");
            {
                builder.comment("Settings for how creatures behave.").push("Creature General Settings");
                {
                    GENERAL.creatureSettings.attackHorses = builder.comment("Allows creatures to attack horses.").define("AttackHorses", false);
                    GENERAL.creatureSettings.attackWolves = builder.comment("Allows creatures to attack wolves.").define("AttackWolves", false);
                    GENERAL.creatureSettings.destroyDrops = builder.define("DestroyDrops", false);
                    GENERAL.creatureSettings.easyBreeding = builder.comment("Makes horse breeding simpler").define("EasyBreeding", false);
                    GENERAL.creatureSettings.elephantBulldozer = builder.define("ElephantBulldozer", true);
                    GENERAL.creatureSettings.enableHunters = builder.comment("Allows creatures to attack other creatures. Not recommended if despawning is off.").define("EnableHunters", true);
                    GENERAL.creatureSettings.killAllVillagers = builder.define("KillAllVillagers", false);
                    GENERAL.creatureSettings.motherWyvernEggDropChance = builder.comment("A value of 33 means mother wyverns have a 33% chance to drop an egg.").defineInRange("MotherWyvernEggDropChance", 33, 0, 100);
                    GENERAL.creatureSettings.wyvernEggDropChance = builder.comment("A value of 10 means wyverns have a 10% chance to drop an egg.").defineInRange("WyvernEggDropChance", 10, 0, 100);
                    GENERAL.creatureSettings.ostrichEggDropChance = builder.comment("A value of 3 means ostriches have a 3% chance to drop an egg.").defineInRange("OstrichEggDropChance", 3, 0, 100);
                    GENERAL.creatureSettings.rareItemDropChance = builder.comment("A value of 25 means Horses/Ostriches/Scorpions/etc. have a 25% chance to drop a rare item such as a heart of darkness, unicorn, bone when killed. Raise the value if you want higher drop rates.").defineInRange("RareItemDropChance", 25, 0, 100);
                    GENERAL.creatureSettings.staticBed = builder.define("StaticBed", true);
                    GENERAL.creatureSettings.staticLitter = builder.define("StaticLitter", true);
                    GENERAL.creatureSettings.zebraChance = builder.comment("The percent for spawning a zebra.").defineInRange("ZebraChance", 10, 0, 100);
                    GENERAL.creatureSettings.despawnLightLevel = builder.comment("The maximum light level threshold used to determine whether or not to despawn a farm animal. Note: Configure this value in CMS if it is installed.").defineInRange("DespawnLightLevel", 2, 0, 20);
                }
                builder.pop();

                builder.comment("Settings for how monsters behave.").push("Monster General Settings");
                {
                    GENERAL.monsterSettings.caveOgreChance = builder.comment("The chance percentage of spawning Cave Ogres at a depth of 50 in The Overworld.").defineInRange("CaveOgreChance", 75, 0, 100);
                    GENERAL.monsterSettings.caveOgreStrength = builder.comment("The block destruction radius of Cave Ogres").defineInRange("CaveOgreStrength", 3.0, 0.0, 10.0);
                    GENERAL.monsterSettings.fireOgreChance = builder.comment("The chance percentage of spawning Fire Ogres in The Overworld").defineInRange("FireOgreChance", 25, 0, 100);
                    GENERAL.monsterSettings.fireOgreStrength = builder.comment("The block destruction radius of Fire Ogres").defineInRange("FireOgreStrength", 2.0, 0.0, 10.0);
                    GENERAL.monsterSettings.ogreAttackRange = builder.comment("The block radius where ogres 'smell' players").defineInRange("OgreAttackRange", 12, 0, 16);
                    GENERAL.monsterSettings.ogreStrength = builder.comment("The block destruction radius of Green Ogres").defineInRange("OgreStrength", 2.5, 0.0, 10.0);
                    GENERAL.monsterSettings.golemDestroyBlocks = builder.comment("Allows Big Golems to break blocks").define("GolemDestroyBlocks", true);
                }
                builder.pop();

                builder.comment("Settings for how water mobs behave.").push("Water Mob General Settings");
                {
                    GENERAL.waterMobSettings.attackDolphins = builder.comment("Allows water creatures to attack dolphins.").define("AttackDolphins", false);
                }
                builder.pop();
            }
            builder.pop();

            builder.push("Global Settings");
            {
                GLOBAL.allowInstaSpawn = builder.comment("Allows you to instantly spawn MoCreatures from GUI").define("allowInstaSpawn", false);
                GLOBAL.debug = builder.comment("Turns on verbose logging").define("debug", false);
                GLOBAL.forceDespawns = builder.comment("If true, it will force despawns on all creatures including vanilla for a more dynamic experience while exploring world. If false, all passive mocreatures will not despawn to prevent other creatures from taking over. Note: if you experience issues with farm animals despawning, adjust despawnLightLevel. If CMS is installed, this setting must remain true if you want MoCreatures to despawn.")
                        .define("forceDespawns", false);
                GLOBAL.particleFX = builder.defineInRange("particleFX", 3, 0, 3);
            }
            builder.pop();

            builder.push("Ownership Settings");
            {
                OWNERSHIP.enableOwnership = builder.comment("Assigns player as owner for each creature they tame. Only the owner can interact with the tamed creature.").define("enableOwnership", false);
                OWNERSHIP.enableResetOwnerScroll = builder.comment("Allows players to remove a tamed creature's owner essentially untaming it.").define("enableResetOwnerScroll", false);
                OWNERSHIP.maxTamedPerOP = builder.comment("Max tamed creatures an op can have. Requires enableOwnership to be set to true.").defineInRange("maxTamedPerOP", 20, 0, 50);
                OWNERSHIP.maxTamedPerPlayer = builder.comment("Max tamed creatures a player can have. Requires enableOwnership to be set to true.").defineInRange("maxTamedPerPlayer", 10, 0, 50);
            }
            builder.pop();
        }
        public static class General {

            public Creature creatureSettings = new Creature();
            public Monster monsterSettings = new Monster();
            public WaterMob waterMobSettings = new WaterMob();

            public static class Creature {
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

            public static class Monster {
                public ForgeConfigSpec.IntValue caveOgreChance;
                public ForgeConfigSpec.DoubleValue caveOgreStrength;
                public ForgeConfigSpec.IntValue fireOgreChance;
                public ForgeConfigSpec.DoubleValue fireOgreStrength;
                public ForgeConfigSpec.IntValue ogreAttackRange;
                public ForgeConfigSpec.DoubleValue ogreStrength;
                public ForgeConfigSpec.BooleanValue golemDestroyBlocks;
            }

            public static class WaterMob {
                public ForgeConfigSpec.BooleanValue attackDolphins;
            }
        }

        public static class Global {
            public ForgeConfigSpec.BooleanValue allowInstaSpawn;
            public ForgeConfigSpec.BooleanValue debug;
            public ForgeConfigSpec.BooleanValue forceDespawns;
            public ForgeConfigSpec.IntValue particleFX;
        }

        public static class Ownership {
            public ForgeConfigSpec.BooleanValue enableOwnership;
            public ForgeConfigSpec.BooleanValue enableResetOwnerScroll;
            public ForgeConfigSpec.IntValue maxTamedPerOP;
            public ForgeConfigSpec.IntValue maxTamedPerPlayer;
        }
    }




    public static class ClientConfig {
        public ClientConfig(ForgeConfigSpec.Builder builder) {
            builder.comment("Client settings for Mo' Creatures.").push("Client Settings");
            {
                animateTextures = builder.comment("Animate Textures").define("animateTextures", true);
                displayPetHealth = builder.comment("Shows Pet Health").define("displayPetHealth", true);
                displayPetIcons = builder.comment("Shows Pet Emotes").define("displayPetIcons", true);
                displayPetName = builder.comment("Shows Pet Name").define("displayPetName", true);
            }
            builder.pop();
        }
        public ForgeConfigSpec.BooleanValue animateTextures;
        public ForgeConfigSpec.BooleanValue displayPetHealth;
        public ForgeConfigSpec.BooleanValue displayPetIcons;
        public ForgeConfigSpec.BooleanValue displayPetName;
    }
}
