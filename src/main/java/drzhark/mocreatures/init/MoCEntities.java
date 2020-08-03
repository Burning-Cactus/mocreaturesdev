package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCEntityData;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ambient.MoCEntityAnt;
import drzhark.mocreatures.entity.ambient.MoCEntityBee;
import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;
import drzhark.mocreatures.entity.ambient.MoCEntityCrab;
import drzhark.mocreatures.entity.ambient.MoCEntityCricket;
import drzhark.mocreatures.entity.ambient.MoCEntityDragonfly;
import drzhark.mocreatures.entity.ambient.MoCEntityFirefly;
import drzhark.mocreatures.entity.ambient.MoCEntityFly;
import drzhark.mocreatures.entity.ambient.MoCEntityMaggot;
import drzhark.mocreatures.entity.ambient.MoCEntityRoach;
import drzhark.mocreatures.entity.ambient.MoCEntitySnail;
import drzhark.mocreatures.entity.aquatic.MoCEntityAnchovy;
import drzhark.mocreatures.entity.aquatic.MoCEntityAngelFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityAngler;
import drzhark.mocreatures.entity.aquatic.MoCEntityBass;
import drzhark.mocreatures.entity.aquatic.MoCEntityClownFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityCod;
import drzhark.mocreatures.entity.aquatic.MoCEntityDolphin;
import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.entity.aquatic.MoCEntityGoldFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityHippoTang;
import drzhark.mocreatures.entity.aquatic.MoCEntityJellyFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityManderin;
import drzhark.mocreatures.entity.aquatic.MoCEntityMantaRay;
import drzhark.mocreatures.entity.aquatic.MoCEntityPiranha;
import drzhark.mocreatures.entity.aquatic.MoCEntitySalmon;
import drzhark.mocreatures.entity.aquatic.MoCEntityShark;
import drzhark.mocreatures.entity.aquatic.MoCEntityStingRay;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.entity.monster.MoCEntityCaveOgre;
import drzhark.mocreatures.entity.monster.MoCEntityFireOgre;
import drzhark.mocreatures.entity.monster.MoCEntityFlameWraith;
import drzhark.mocreatures.entity.monster.MoCEntityGolem;
import drzhark.mocreatures.entity.monster.MoCEntityGreenOgre;
import drzhark.mocreatures.entity.monster.MoCEntityHellRat;
import drzhark.mocreatures.entity.monster.MoCEntityHorseMob;
import drzhark.mocreatures.entity.monster.MoCEntityManticore;
import drzhark.mocreatures.entity.monster.MoCEntityMiniGolem;
import drzhark.mocreatures.entity.monster.MoCEntityRat;
import drzhark.mocreatures.entity.monster.MoCEntityScorpion;
import drzhark.mocreatures.entity.monster.MoCEntitySilverSkeleton;
import drzhark.mocreatures.entity.monster.MoCEntityWWolf;
import drzhark.mocreatures.entity.monster.MoCEntityWerewolf;
import drzhark.mocreatures.entity.monster.MoCEntityWraith;
import drzhark.mocreatures.entity.passive.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static drzhark.mocreatures.MoCConstants.MOD_ID;
import static drzhark.mocreatures.MoCConstants.MOD_PREFIX;

@ObjectHolder(MOD_ID)
public class MoCEntities {
    private static List<EntityType<?>> ENTITIES = new ArrayList<>();
    public static List<EntityType<?>> SPAWN_ENTITIES = new ArrayList<>();

    private static<T extends Entity> EntityType.Builder<T> makeBuilder(EntityType.IFactory<T> factory, EntityClassification classification, float size1, float size2) {
        return EntityType.Builder.create(factory, classification).size(size1, size2);
    }

    private static<T extends Entity> EntityType<T> buildType(String id, EntityType.IFactory<T> factory, EntityClassification classification, float size1, float size2) {
        return buildType(id, factory, classification, size1, size2, false);
    }

    private static<T extends Entity> EntityType<T> buildType(String id, EntityType.IFactory<T> factory, EntityClassification classification, float size1, float size2, boolean fireImmune) {
        EntityType.Builder<T> builder = makeBuilder(factory, classification, size1, size2);
        if(fireImmune)
            builder.immuneToFire();
        EntityType<T> type = builder.build(MOD_PREFIX + id);
        type.setRegistryName(id);
        ENTITIES.add(type);
        return type;
    }


    /**
     * Creature
     */
    public static final EntityType<MoCEntityBird> BIRD = buildType("bird", MoCEntityBird::new, EntityClassification.CREATURE, 0.4F, 0.3F);
    public static final EntityType<MoCEntityBlackBear> BEAR = buildType("bear", MoCEntityBlackBear::new, EntityClassification.CREATURE, 1.2F, 1.5F);
    public static final EntityType<MoCEntityBoar> BOAR = buildType("boar", MoCEntityBoar::new, EntityClassification.CREATURE, 0.9F, 0.8F);
    public static final EntityType<MoCEntityBunny> BUNNY = buildType("bunny", MoCEntityBunny::new, EntityClassification.CREATURE, 0.5F, 0.5F);
    public static final EntityType<MoCEntityCrocodile> CROCODILE = buildType("crocodile", MoCEntityCrocodile::new, EntityClassification.CREATURE, 1.4F, 0.6F);
    public static final EntityType<MoCEntityDuck> DUCK = buildType("duck", MoCEntityDuck::new, EntityClassification.CREATURE, 0.4F, 0.7F);
    public static final EntityType<MoCEntityDeer> DEER = buildType("deer", MoCEntityDeer::new, EntityClassification.CREATURE, 0.9F, 1.3F);
    public static final EntityType<MoCEntityElephant> ELEPHANT = buildType("elephant", MoCEntityElephant::new, EntityClassification.CREATURE, 1.1F, 3F);
    public static final EntityType<MoCEntityEnt> ENT = buildType("ent", MoCEntityEnt::new, EntityClassification.CREATURE,1.4F, 7F);
    public static final EntityType<MoCEntityFox> FOX = buildType("fox", MoCEntityFox::new, EntityClassification.CREATURE, 0.6F, 0.7F);
    public static final EntityType<MoCEntityGoat> GOAT = buildType("goat", MoCEntityGoat::new, EntityClassification.CREATURE, 0.8F, 1F);
    public static final EntityType<MoCEntityGrizzlyBear> GRIZZLY_BEAR = buildType("grizzly_bear", MoCEntityGrizzlyBear::new, EntityClassification.CREATURE, 1.2F, 1.5F);
    public static final EntityType<MoCEntityKitty> KITTY = buildType("kitty", MoCEntityKitty::new, EntityClassification.CREATURE, 0.7F, 0.5F);
    public static final EntityType<MoCEntityKomodo> KOMODO_DRAGON = buildType("komodo_dragon", MoCEntityKomodo::new, EntityClassification.CREATURE, 1.6F, 0.5F);
    public static final EntityType<MoCEntityLeoger> LEOGER = buildType("leoger", MoCEntityLeoger::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static final EntityType<MoCEntityLeopard> LEOPARD = buildType("leopard", MoCEntityLeopard::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static final EntityType<MoCEntityLiard> LIARD = buildType("liard", MoCEntityLiard::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static final EntityType<MoCEntityLion> LION = buildType("lion", MoCEntityLion::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static final EntityType<MoCEntityLiger> LIGER = buildType("liger", MoCEntityLiger::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static final EntityType<MoCEntityLither> LITHER = buildType("lither", MoCEntityLither::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static final EntityType<MoCEntityManticorePet> MANTICORE_PET = buildType("manticore_pet", MoCEntityManticorePet::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static final EntityType<MoCEntityMole> MOLE = buildType("mole", MoCEntityMole::new, EntityClassification.CREATURE, 1F, 0.5F);
    public static final EntityType<MoCEntityMouse> MOUSE = buildType("mouse", MoCEntityMouse::new, EntityClassification.CREATURE, 0.3F, 0.3F);
    public static final EntityType<MoCEntityOstrich> OSTRICH = buildType("ostrich", MoCEntityOstrich::new, EntityClassification.CREATURE, 1.0F, 1.6F);
    public static final EntityType<MoCEntityPandaBear> PANDA_BEAR = buildType("panda_bear", MoCEntityPandaBear::new, EntityClassification.CREATURE, 1.2F, 1.5F);
    public static final EntityType<MoCEntityPanthard> PANTHARD = buildType("panthard", MoCEntityPanthard::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static final EntityType<MoCEntityPanther> PANTHER = buildType("panther", MoCEntityPanther::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static final EntityType<MoCEntityPanthger> PANTHGER = buildType("panthger", MoCEntityPanthger::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static final EntityType<MoCEntityPetScorpion> PET_SCORPION = buildType("pet_scorpion", MoCEntityPetScorpion::new, EntityClassification.CREATURE, 1.4F, 0.9F);
    public static final EntityType<MoCEntityPolarBear> POLAR_BEAR = buildType("polar_bear", MoCEntityPolarBear::new, EntityClassification.CREATURE, 1.2F, 1.5F);
    public static final EntityType<MoCEntityRaccoon> RACCOON = buildType("raccoon", MoCEntityRaccoon::new, EntityClassification.CREATURE, 0.5F, 0.6F);
    public static final EntityType<MoCEntitySnake> SNAKE = buildType("snake", MoCEntitySnake::new, EntityClassification.CREATURE, 1.4F, 0.5F);
    public static final EntityType<MoCEntityTiger> TIGER = buildType("tiger", MoCEntityTiger::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static final EntityType<MoCEntityTurtle> TURTLE = buildType("turtle", MoCEntityTurtle::new, EntityClassification.CREATURE, 0.6F, 0.4F);
    public static final EntityType<MoCEntityTurkey> TURKEY = buildType("turkey", MoCEntityTurkey::new, EntityClassification.CREATURE, 0.8F, 1.0F);
    public static final EntityType<MoCEntityHorse> WILDHORSE = buildType("wildhorse", MoCEntityHorse::new, EntityClassification.CREATURE, 1.4F, 1.6F);
    public static final EntityType<MoCEntityWyvern> WYVERN = buildType("wyvern", MoCEntityWyvern::new, EntityClassification.CREATURE, 1.9F, 1.7F);
    
    /**
     * Mobs
     */
    public static final EntityType<MoCEntityCaveOgre> CAVE_OGRE = buildType("cave_ogre", MoCEntityCaveOgre::new, EntityClassification.MONSTER, 1.9F, 3F);
    public static final EntityType<MoCEntityFlameWraith> FLAME_WRAITH = buildType("flame_wraith", MoCEntityFlameWraith::new, EntityClassification.MONSTER, 1.5F, 1.5F, true);
    public static final EntityType<MoCEntityFireOgre> FIRE_OGRE = buildType("fire_ogre", MoCEntityFireOgre::new, EntityClassification.MONSTER, 1.9F, 3F, true);
    public static final EntityType<MoCEntityGreenOgre> GREEN_OGRE = buildType("green_ogre", MoCEntityGreenOgre::new, EntityClassification.MONSTER, 1.9F, 3F);
    public static final EntityType<MoCEntityGolem> BIG_GOLEM = buildType("big_golem", MoCEntityGolem::new, EntityClassification.MONSTER,1.5F, 4F);
    public static final EntityType<MoCEntityHorseMob> HORSEMOB = buildType("horsemob", MoCEntityHorseMob::new, EntityClassification.MONSTER, 1.4F, 1.6F);
    public static final EntityType<MoCEntityHellRat> HELLRAT = buildType("hellrat", MoCEntityHellRat::new, EntityClassification.MONSTER, 0.7F, 0.7F, true);
    public static final EntityType<MoCEntityManticore> MANTICORE = buildType("manticore", MoCEntityManticore::new, EntityClassification.MONSTER, 1.4F, 1.6F, true);
    public static final EntityType<MoCEntityMiniGolem> MINI_GOLEM = buildType("mini_golem", MoCEntityMiniGolem::new, EntityClassification.MONSTER, 1.0F, 1.0F);
    public static final EntityType<MoCEntityRat> RAT = buildType("rat", MoCEntityRat::new, EntityClassification.MONSTER, 0.5F, 0.5F);
    public static final EntityType<MoCEntitySilverSkeleton> SILVER_SKELETON = buildType("silver_skeleton", MoCEntitySilverSkeleton::new, EntityClassification.MONSTER, 0.9F, 1.4F);
    public static final EntityType<MoCEntityScorpion> SCORPION = buildType("scorpion", MoCEntityScorpion::new, EntityClassification.MONSTER, 1.4F, 0.9F);
    public static final EntityType<MoCEntityWerewolf> WEREWOLF = buildType("werewolf", MoCEntityWerewolf::new, EntityClassification.MONSTER, 0.9F, 1.6F);
    public static final EntityType<MoCEntityWraith> WRAITH = buildType("wraith", MoCEntityWraith::new, EntityClassification.MONSTER, 1.5F, 1.5F);
    public static final EntityType<MoCEntityWWolf> WWOLF = buildType("wwolf", MoCEntityWWolf::new, EntityClassification.MONSTER, 0.9F, 1.3F);
    
    /**
     * Aquatic
     */
    public static final EntityType<MoCEntityAnchovy> ANCHOVY = buildType("anchovy", MoCEntityAnchovy::new, EntityClassification.WATER_CREATURE,0.3F, 0.3F);
    public static final EntityType<MoCEntityAngelFish> ANGELFISH = buildType("angelfish", MoCEntityAngelFish::new, EntityClassification.WATER_CREATURE, 0.3F, 0.3F);
    public static final EntityType<MoCEntityAngler> ANGLER = buildType("angler", MoCEntityAngler::new, EntityClassification.WATER_CREATURE, 0.3F, 0.3F);
    public static final EntityType<MoCEntityBass> BASS = buildType("bass", MoCEntityBass::new, EntityClassification.WATER_CREATURE, 0.6F, 0.3F);
    public static final EntityType<MoCEntityClownFish> CLOWNFISH = buildType("clownfish", MoCEntityClownFish::new, EntityClassification.WATER_CREATURE, 0.3F, 0.3F);
    public static final EntityType<MoCEntityCod> COD = buildType("cod", MoCEntityCod::new, EntityClassification.WATER_CREATURE, 0.6F, 0.3F);
    public static final EntityType<MoCEntityDolphin> DOLPHIN = buildType("dolphin", MoCEntityDolphin::new, EntityClassification.WATER_CREATURE, 1.5F, 0.8F);
    public static final EntityType<MoCEntityFishy> FISHY = buildType("fishy", MoCEntityFishy::new, EntityClassification.WATER_CREATURE, 0.3F, 0.3F);
    public static final EntityType<MoCEntityGoldFish> GOLDFISH = buildType("goldfish", MoCEntityGoldFish::new, EntityClassification.WATER_CREATURE, 0.3F, 0.3F);
    public static final EntityType<MoCEntityHippoTang> HIPPOTANG = buildType("hippotang", MoCEntityHippoTang::new, EntityClassification.WATER_CREATURE, 0.3F, 0.3F);
    public static final EntityType<MoCEntityJellyFish> JELLYFISH = buildType("jellyfish", MoCEntityJellyFish::new, EntityClassification.WATER_CREATURE, 0.3F, 0.5F);
    public static final EntityType<MoCEntityManderin> MANDERIN = buildType("manderin", MoCEntityManderin::new, EntityClassification.WATER_CREATURE, 0.3F, 0.3F);
    public static final EntityType<MoCEntityPiranha> PIRANHA = buildType("piranha", MoCEntityPiranha::new, EntityClassification.WATER_CREATURE, 0.3F, 0.3F);
    public static final EntityType<MoCEntitySalmon> SALMON = buildType("salmon", MoCEntitySalmon::new, EntityClassification.WATER_CREATURE, 0.6F, 0.3F);
    public static final EntityType<MoCEntityMantaRay> MANTARAY = buildType("mantaray", MoCEntityMantaRay::new, EntityClassification.WATER_CREATURE, 1.8F, 1F);
    public static final EntityType<MoCEntityShark> SHARK = buildType("shark", MoCEntityShark::new, EntityClassification.WATER_CREATURE, 1.7F, 0.8F);
    public static final EntityType<MoCEntityStingRay> STINGRAY = buildType("stingray", MoCEntityStingRay::new, EntityClassification.WATER_CREATURE, 1.2F, 0.5F);
    
    /**
     * Ambients
     */
    public static final EntityType<MoCEntityAnt> ANT = buildType("ant", MoCEntityAnt::new, EntityClassification.AMBIENT, 0.2F, 0.2F);
    public static final EntityType<MoCEntityBee> BEE = buildType("bee", MoCEntityBee::new, EntityClassification.AMBIENT, 0.2F, 0.2F);
    public static final EntityType<MoCEntityButterfly> BUTTERFLY = buildType("butterfly", MoCEntityButterfly::new, EntityClassification.AMBIENT, 0.2F, 0.2F);
    public static final EntityType<MoCEntityCrab> CRAB = buildType("crab", MoCEntityCrab::new, EntityClassification.AMBIENT, 0.3F, 0.3F);
    public static final EntityType<MoCEntityCricket> CRICKET = buildType("cricket", MoCEntityCricket::new, EntityClassification.AMBIENT, 0.2F, 0.2F);
    public static final EntityType<MoCEntityDragonfly> DRAGONFLY = buildType("dragonfly", MoCEntityDragonfly::new, EntityClassification.AMBIENT, 0.2F, 0.2F);
    public static final EntityType<MoCEntityFirefly> FIREFLY = buildType("firefly", MoCEntityFirefly::new, EntityClassification.AMBIENT, 0.2F, 0.2F);
    public static final EntityType<MoCEntityFly> FLY = buildType("fly", MoCEntityFly::new, EntityClassification.AMBIENT, 0.2F, 0.2F);
    public static final EntityType<MoCEntityMaggot> MAGGOT = buildType("maggot", MoCEntityMaggot::new, EntityClassification.AMBIENT, 0.2F, 0.2F);
    public static final EntityType<MoCEntitySnail> SNAIL = buildType("snail", MoCEntitySnail::new, EntityClassification.AMBIENT, 0.2F, 0.2F);
    public static final EntityType<MoCEntityRoach> ROACH = buildType("roach", MoCEntityRoach::new, EntityClassification.AMBIENT, 0.2F, 0.2F);
    
    /**
     * Others
     */
    public static final EntityType<MoCEntityEgg> EGG = buildType("egg", MoCEntityEgg::new, EntityClassification.MISC, 0.25F, 0.25F);
    public static final EntityType<MoCEntityKittyBed> KITTY_BED = buildType("kittybed", MoCEntityKittyBed::new, EntityClassification.MISC, 1.0F, 0.3F);
    public static final EntityType<MoCEntityLitterBox> LITTERBOX = buildType("litterbox", MoCEntityLitterBox::new, EntityClassification.MISC, 1.0F, 0.3F);
    public static final EntityType<MoCEntityThrowableRock> TROCK = buildType("trock", MoCEntityThrowableRock::new, EntityClassification.MISC, 1F, 1F);

    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
            MoCreatures.LOGGER.info("Registering entities...");
            final IForgeRegistry<EntityType<?>> registry = event.getRegistry();
//            for(EntityType<?> type : ENTITIES) {
//                registry.register(type);
//            }
            registry.registerAll(
                    GOAT,
                    CAVE_OGRE,
                    FLAME_WRAITH,
                    FIRE_OGRE,
                    GREEN_OGRE,
                    BIG_GOLEM,
                    HORSEMOB,
                    HELLRAT,
                    MANTICORE,
                    MINI_GOLEM,
                    RAT,
                    SILVER_SKELETON,
                    SCORPION,
                    WEREWOLF,
                    WRAITH,
                    WWOLF
            );
/*
            // ambients
            MoCreatures.mocEntityMap.put("Ant", new MoCEntityData("Ant", 4, EntityClassification.AMBIENT, new SpawnListEntry(ANT, 7, 1, 4), new ArrayList<Type>(
                    Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
            MoCreatures.mocEntityMap.put("Bee", new MoCEntityData("Bee", 3, EntityClassification.AMBIENT, new SpawnListEntry(BEE, 6, 1, 2), new ArrayList<Type>(
                    Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("ButterFly", new MoCEntityData("ButterFly", 3, EntityClassification.AMBIENT, new SpawnListEntry(BUTTERFLY, 8, 1,
                    3), new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Crab", new MoCEntityData("Crab", 2, EntityClassification.AMBIENT, new SpawnListEntry(CRAB, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.WATER))));
            MoCreatures.mocEntityMap.put("Cricket", new MoCEntityData("Cricket", 2, EntityClassification.AMBIENT, new SpawnListEntry(CRICKET, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP))));
            MoCreatures.mocEntityMap.put("DragonFly", new MoCEntityData("DragonFly", 2, EntityClassification.AMBIENT, new SpawnListEntry(DRAGONFLY, 6, 1,
                    2), new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.BEACH))));
            MoCreatures.mocEntityMap.put("Firefly", new MoCEntityData("Firefly", 3, EntityClassification.AMBIENT, new SpawnListEntry(FIREFLY, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP))));
            MoCreatures.mocEntityMap.put("Fly", new MoCEntityData("Fly", 2, EntityClassification.AMBIENT, new SpawnListEntry(FLY, 8, 1, 2), new ArrayList<Type>(
                    Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
            MoCreatures.mocEntityMap.put("Maggot", new MoCEntityData("Maggot", 2, EntityClassification.AMBIENT, new SpawnListEntry(MAGGOT, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SWAMP))));
            MoCreatures.mocEntityMap.put("Snail", new MoCEntityData("Snail", 2, EntityClassification.AMBIENT, new SpawnListEntry(SNAIL, 7, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Roach", new MoCEntityData("Roach", 2, EntityClassification.AMBIENT, new SpawnListEntry(ROACH, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SWAMP))));

            // creatures
            MoCreatures.mocEntityMap.put("BlackBear", new MoCEntityData("BlackBear", 4, EntityClassification.CREATURE, new SpawnListEntry(BEAR, 6, 1, 3),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("GrizzlyBear", new MoCEntityData("GrizzlyBear", 4, EntityClassification.CREATURE, new SpawnListEntry(GRIZZLY_BEAR, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("WildPolarBear", new MoCEntityData("WildPolarBear", 4, EntityClassification.CREATURE, new SpawnListEntry(POLAR_BEAR, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.SNOWY))));
            MoCreatures.mocEntityMap.put("PandaBear", new MoCEntityData("PandaBear", 4, EntityClassification.CREATURE, new SpawnListEntry(PANDA_BEAR, 6, 1, 3),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Bird", new MoCEntityData("Bird", 4, EntityClassification.CREATURE, new SpawnListEntry(BIRD, 15, 2, 3),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Boar", new MoCEntityData("Boar", 3, EntityClassification.CREATURE, new SpawnListEntry(BOAR, 8, 2, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Bunny", new MoCEntityData("Bunny", 4, EntityClassification.CREATURE, new SpawnListEntry(BUNNY, 8, 2, 3),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SNOWY))));
            MoCreatures.mocEntityMap.put("Crocodile", new MoCEntityData("Crocodile", 2, EntityClassification.CREATURE, new SpawnListEntry(CROCODILE, 6, 1,
                    2), new ArrayList<Type>(Arrays.asList(Type.SWAMP))));
            MoCreatures.mocEntityMap.put("Deer", new MoCEntityData("Deer", 2, EntityClassification.CREATURE, new SpawnListEntry(DEER, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Duck", new MoCEntityData("Duck", 3, EntityClassification.CREATURE, new SpawnListEntry(DUCK, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Elephant", new MoCEntityData("Elephant", 3, EntityClassification.CREATURE,
                    new SpawnListEntry(ELEPHANT, 4, 1, 1),
                    new ArrayList<Type>(Arrays.asList(Type.SANDY, Type.FOREST, Type.PLAINS, Type.SNOWY))));
            MoCreatures.mocEntityMap.put("Ent", new MoCEntityData("Ent", 3, EntityClassification.CREATURE, new SpawnListEntry(ENT, 4, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Fox", new MoCEntityData("Fox", 2, EntityClassification.CREATURE, new SpawnListEntry(FOX, 8, 1, 1),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SNOWY))));
            MoCreatures.mocEntityMap.put("Goat", new MoCEntityData("Goat", 2, EntityClassification.CREATURE, new SpawnListEntry(GOAT, 8, 1, 3),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Kitty", new MoCEntityData("Kitty", 3, EntityClassification.CREATURE, new SpawnListEntry(KITTY, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.PLAINS))));
            MoCreatures.mocEntityMap.put("KomodoDragon", new MoCEntityData("KomodoDragon", 2, EntityClassification.CREATURE, new SpawnListEntry(KOMODO_DRAGON, 8,
                    1, 2), new ArrayList<Type>(Arrays.asList(Type.SWAMP))));
            MoCreatures.mocEntityMap.put("Leopard", new MoCEntityData("Leopard", 4, EntityClassification.CREATURE, new SpawnListEntry(LEOPARD, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.SNOWY))));
            MoCreatures.mocEntityMap.put("Lion", new MoCEntityData("Lion", 4, EntityClassification.CREATURE, new SpawnListEntry(LION, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.JUNGLE, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Mole", new MoCEntityData("Mole", 3, EntityClassification.CREATURE, new SpawnListEntry(MOLE, 7, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Mouse", new MoCEntityData("Mouse", 2, EntityClassification.CREATURE, new SpawnListEntry(MOUSE, 7, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Ostrich", new MoCEntityData("Ostrich", 3, EntityClassification.CREATURE, new SpawnListEntry(OSTRICH, 4, 1, 1),
                    new ArrayList<Type>(Arrays.asList(Type.SANDY, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Panther", new MoCEntityData("Panther", 4, EntityClassification.CREATURE, new SpawnListEntry(PANTHER, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN))));
            MoCreatures.mocEntityMap.put("Raccoon", new MoCEntityData("Raccoon", 2, EntityClassification.CREATURE, new SpawnListEntry(RACCOON, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put(
                    "Snake",
                    new MoCEntityData("Snake", 3, EntityClassification.CREATURE, new SpawnListEntry(SNAKE, 8, 1, 2), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
            MoCreatures.mocEntityMap.put("Tiger", new MoCEntityData("Tiger", 4, EntityClassification.CREATURE, new SpawnListEntry(TIGER, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Turkey", new MoCEntityData("Turkey", 2, EntityClassification.CREATURE, new SpawnListEntry(TURKEY, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Turtle", new MoCEntityData("Turtle", 3, EntityClassification.CREATURE, new SpawnListEntry(TURTLE, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.JUNGLE, Type.SWAMP))));
            MoCreatures.mocEntityMap.put("WildHorse", new MoCEntityData("WildHorse", 4, EntityClassification.CREATURE, new SpawnListEntry(WILDHORSE, 8, 1, 4),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Wyvern", new MoCEntityData("Wyvern", 3, EntityClassification.CREATURE, new SpawnListEntry(WYVERN, 8, 1, 3),
                    new ArrayList<Type>()));
            // water creatures
            MoCreatures.mocEntityMap.put("Bass", new MoCEntityData("Bass", 4, EntityClassification.WATER_CREATURE, new SpawnListEntry(
                    BASS, 10, 1, 4), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Cod", new MoCEntityData("Cod", 4, EntityClassification.WATER_CREATURE, new SpawnListEntry(
                    COD, 10, 1, 4), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Dolphin", new MoCEntityData("Dolphin", 3, EntityClassification.WATER_CREATURE, new SpawnListEntry(DOLPHIN, 6, 2,
                    4), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Fishy", new MoCEntityData("Fishy", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(FISHY, 12, 1, 6),
                    new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("JellyFish", new MoCEntityData("JellyFish", 4, EntityClassification.WATER_CREATURE, new SpawnListEntry(JELLYFISH,
                    8, 1, 4), new ArrayList<Type>(Arrays.asList(Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Salmon", new MoCEntityData("Salmon", 4, EntityClassification.WATER_CREATURE, new SpawnListEntry(
                    SALMON, 10, 1, 4), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Piranha", new MoCEntityData("Piranha", 4, EntityClassification.WATER_CREATURE, new SpawnListEntry(PIRANHA, 4, 1,
                    3), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("MantaRay", new MoCEntityData("MantaRay", 3, EntityClassification.WATER_CREATURE, new SpawnListEntry(MANTARAY,
                    10, 1, 2), new ArrayList<Type>(Arrays.asList(Type.OCEAN))));
            MoCreatures.mocEntityMap.put("StingRay", new MoCEntityData("StingRay", 3, EntityClassification.WATER_CREATURE, new SpawnListEntry(STINGRAY,
                    10, 1, 2), new ArrayList<Type>(Arrays.asList(Type.SWAMP, Type.WATER, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Shark", new MoCEntityData("Shark", 3, EntityClassification.WATER_CREATURE, new SpawnListEntry(SHARK, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Anchovy", new MoCEntityData("Anchovy", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(ANCHOVY,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("AngelFish", new MoCEntityData("AngelFish", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(ANGELFISH,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Angler", new MoCEntityData("Angler", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(ANGLER,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN))));
            MoCreatures.mocEntityMap.put("ClownFish", new MoCEntityData("ClownFish", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(CLOWNFISH,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN))));
            MoCreatures.mocEntityMap.put("GoldFish", new MoCEntityData("GoldFish", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(GOLDFISH,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.RIVER))));
            MoCreatures.mocEntityMap.put("HippoTang", new MoCEntityData("HippoTang", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(HIPPOTANG,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN))));
            MoCreatures.mocEntityMap.put("Manderin", new MoCEntityData("Manderin", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(MANDERIN,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN))));
            
            // monsters
            MoCreatures.mocEntityMap.put(
                    "BigGolem",
                    new MoCEntityData("BigGolem", 1, EntityClassification.MONSTER, new SpawnListEntry(BIG_GOLEM, 3, 1, 1), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND))));
            MoCreatures.mocEntityMap.put(
                    "FlameWraith",
                    new MoCEntityData("FlameWraith", 3, EntityClassification.MONSTER, new SpawnListEntry(FLAME_WRAITH, 5, 1, 2), new ArrayList<Type>(
                            Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.NETHER, Type.PLAINS,
                                    Type.SWAMP, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put("HellRat", new MoCEntityData("HellRat", 4, EntityClassification.MONSTER, new SpawnListEntry(HELLRAT, 6, 1, 4),
                    new ArrayList<Type>(Arrays.asList(Type.NETHER, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "HorseMob",
                    new MoCEntityData("HorseMob", 3, EntityClassification.MONSTER, new SpawnListEntry(HORSEMOB, 8, 1, 3), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "MiniGolem",
                    new MoCEntityData("MiniGolem", 2, EntityClassification.MONSTER, new SpawnListEntry(MINI_GOLEM, 6, 1, 3), new ArrayList<Type>(
                            Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND))));
            MoCreatures.mocEntityMap.put(
                    "GreenOgre",
                    new MoCEntityData("GreenOgre", 3, EntityClassification.MONSTER, new SpawnListEntry(GREEN_OGRE, 8, 1, 2), new ArrayList<Type>(Arrays.asList(
                            Type.SANDY, Type.FOREST, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                            Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "FireOgre",
                    new MoCEntityData("FireOgre", 3, EntityClassification.MONSTER, new SpawnListEntry(FIRE_OGRE, 6, 1, 2), new ArrayList<Type>(Arrays.asList(
                            Type.SANDY, Type.FOREST, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP,
                            Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "CaveOgre",
                    new MoCEntityData("CaveOgre", 3, EntityClassification.MONSTER, new SpawnListEntry(CAVE_OGRE, 5, 1, 2), new ArrayList<Type>(Arrays.asList(
                            Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                            Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "Rat",
                    new MoCEntityData("Rat", 2, EntityClassification.MONSTER, new SpawnListEntry(RAT, 7, 1, 2), new ArrayList<Type>(Arrays.asList(
                            Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
            MoCreatures.mocEntityMap.put(
                    "Scorpion",
                    new MoCEntityData("Scorpion", 3, EntityClassification.MONSTER, new SpawnListEntry(SCORPION, 6, 1, 3), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND))));
            MoCreatures.mocEntityMap.put(
                    "SilverSkeleton",
                    new MoCEntityData("SilverSkeleton", 4, EntityClassification.MONSTER, new SpawnListEntry(SILVER_SKELETON, 6, 1, 4),
                            new ArrayList<Type>(Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS,
                                    Type.SWAMP, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "Werewolf",
                    new MoCEntityData("Werewolf", 3, EntityClassification.MONSTER, new SpawnListEntry(WEREWOLF, 8, 1, 4), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND))));
            MoCreatures.mocEntityMap.put(
                    "Wraith",
                    new MoCEntityData("Wraith", 3, EntityClassification.MONSTER, new SpawnListEntry(WRAITH, 6, 1, 4), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "WWolf",
                    new MoCEntityData("WWolf", 3, EntityClassification.MONSTER, new SpawnListEntry(WWOLF, 8, 1, 3), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND))));
            MoCreatures.mocEntityMap.put(
                    "Manticore",
                    new MoCEntityData("Manticore", 3, EntityClassification.MONSTER, new SpawnListEntry(MANTICORE, 8, 1, 3), new ArrayList<Type>(
                            Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND, Type.NETHER, Type.DEAD, Type.SPOOKY))));

            for (MoCEntityData entityData : MoCreatures.mocEntityMap.values()) {
                if (entityData.getEntityName().equals("Wyvern")) {
                    continue;
                }
                SpawnListEntry spawnEntry = entityData.getSpawnListEntry();
                for (Type type : entityData.getBiomeTypes()) {
                    for (Biome biome : BiomeDictionary.getBiomes(type)) {
                        if (!biome.getSpawns(entityData.getType()).contains(spawnEntry)) {
                            biome.getSpawns(entityData.getType()).add(spawnEntry);
                        }
                    }
                }
            }*/
            MoCreatures.LOGGER.info("Entity registration complete.");
        }
    }
}
