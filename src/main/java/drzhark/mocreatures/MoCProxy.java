package drzhark.mocreatures;

import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MoCProxy {

    //TODO: Proxies are no longer necessary in 1.14+


    //CONFIG VARIABLES
    // Client Only
    public boolean displayPetHealth;
    public boolean displayPetName;
    public boolean displayPetIcons;
    public boolean animateTextures;

    public boolean attackDolphins;
    public boolean attackWolves;
    public boolean attackHorses;
    public boolean staticBed;
    public boolean staticLitter;

    public boolean easyBreeding;
    public boolean destroyDrops;
    public boolean enableOwnership;
    public boolean enableResetOwnership;
    public boolean elephantBulldozer;
    public boolean killallVillagers;

    // griefing options
    public boolean golemDestroyBlocks;

    public int itemID;
    //new blocks IDs
    public int blockDirtID;
    public int blockGrassID;
    public int blockStoneID;
    public int blockLeafID;
    public int blockLogID;
    public int blockTallGrassID;
    public int blockPlanksID;
    public int WyvernDimension;
    public int WyvernBiomeID;

    public int maxTamed;
    public int maxOPTamed;
    public int zebraChance;
    public int ostrichEggDropChance;
    public int rareItemDropChance;
    public int wyvernEggDropChance;
    public int motherWyvernEggDropChance;
    public int particleFX;
    // defaults
    public int frequency = 6;
    public int minGroup = 1;
    public int maxGroup = 2;
    public int maxSpawnInChunk = 3;
    public float strength = 1;
    public int minDespawnLightLevel = 2;
    public int maxDespawnLightLevel = 7;

    public boolean forceDespawns;
    public boolean enableHunters;
    public boolean debug = false;
    public boolean allowInstaSpawn;
    public boolean needsUpdate = false;
    public boolean worldInitDone = false;
    public int activeScreen = -1;


    //-----------------THE FOLLOWING ARE CLIENT SIDE ONLY, NOT TO BE USED IN SERVER AS THEY AFFECT ONLY DISPLAY / SOUNDS

//
//    public boolean getAnimateTextures() {
//        return false;
//    }
//
//    public boolean getDisplayPetName() {
//        return this.displayPetName;
//    }
//
//    public boolean getDisplayPetIcons() {
//        return this.displayPetIcons;
//    }
//
//    public boolean getDisplayPetHealth() {
//        return this.displayPetHealth;
//    }

}
