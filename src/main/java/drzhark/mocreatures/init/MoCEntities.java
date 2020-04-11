package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
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
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static drzhark.mocreatures.MoCConstants.MOD_ID;
import static drzhark.mocreatures.MoCConstants.MOD_PREFIX;

@ObjectHolder(MOD_ID)
public class MoCEntities {

    //All IDs are now namespaced

    public static List<EntityType<?>> ENTITIES = new ArrayList<>();
    public static List<EntityType<?>> SPAWN_ENTITIES = new ArrayList<>();

    private static<T extends Entity> EntityType.Builder makeBuilder(EntityType.IFactory<T> factory, EntityClassification classification, float size1, float size2) {
        EntityType.Builder builder = EntityType.Builder.create(factory, classification).size(size1, size2);
        return builder;
    }

    private static<T extends Entity> EntityType<?> buildType(String id, EntityType.IFactory<T> factory, EntityClassification classification, float size1, float size2) {
        return buildType(id, factory, classification, size1, size2, false);
    }

    private static<T extends Entity> EntityType<?> buildType(String id, EntityType.IFactory<T> factory, EntityClassification classification, float size1, float size2, boolean fireImmune) {
        EntityType.Builder builder = makeBuilder(factory, classification, size1, size2);
        if(fireImmune==true)
            builder.immuneToFire();
        return builder.build(MOD_PREFIX + id);
    }


    /**
     * Creature
     */
    public static EntityType BIRD = buildType("bird", MoCEntityBird::new, EntityClassification.CREATURE, 0.4F, 0.3F);
    public static EntityType BEAR = buildType("bear", MoCEntityBear::new, EntityClassification.CREATURE, 1.2F, 1.5F);
    public static EntityType BOAR = buildType("boar", MoCEntityBoar::new, EntityClassification.CREATURE, 0.9F, 0.8F);
    public static EntityType BUNNY = buildType("bunny", MoCEntityBunny::new, EntityClassification.CREATURE, 0.5F, 0.5F);
    public static EntityType CROCODILE = buildType("crocodile", MoCEntityCrocodile::new, EntityClassification.CREATURE, 1.4F, 0.6F));
    public static EntityType DUCK = buildType("duck", MoCEntityDuck::new, EntityClassification.CREATURE, 0.4F, 0.7F);
    public static EntityType DEER = buildType("deer", MoCEntityDeer::new, EntityClassification.CREATURE, 0.9F, 1.3F);
    public static EntityType ELEPHANT = buildType("elephant", MoCEntityElephant::new, EntityClassification.CREATURE, 1.1F, 3F);
    public static EntityType ENT = buildType("ent", MoCEntityEnt::new, EntityClassification.CREATURE,1.4F, 7F);
    public static EntityType FOX = buildType("fox", MoCEntityFox::new, EntityClassification.CREATURE, 0.6F, 0.7F);
    public static EntityType GOAT = buildType("goat", MoCEntityGoat::new, EntityClassification.CREATURE, 0.8F, 1F);
    public static EntityType GRIZZLY_BEAR = buildType("grizzlybear", MoCEntityGrizzlyBear::new, EntityClassification.CREATURE, 1.2F, 1.5F);
    public static EntityType KITTY = buildType("kitty", MoCEntityKitty::new, EntityClassification.CREATURE, 0.7F, 0.5F);
    public static EntityType KOMODO_DRAGON = buildType("komododragon", MoCEntityKomodo::new, EntityClassification.CREATURE, 1.6F, 0.5F);
    public static EntityType LEOGER = buildType("leoger", MoCEntityLeoger::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static EntityType LEOPARD = buildType("leopard", MoCEntityLeopard::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static EntityType LIARD = buildType("liard", MoCEntityLiard::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static EntityType LION = buildType("lion", MoCEntityLion::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static EntityType LIGER = buildType("liger", MoCEntityLiger::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static EntityType LITHER = buildType("lither", MoCEntityLither::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static EntityType MANTICORE_PET = buildType("manticorepet", MoCEntityManticorePet::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static EntityType MOLE = buildType("mole", MoCEntityMole::new, EntityClassification.CREATURE, 1F, 0.5F);
    public static EntityType MOUSE = buildType("mouse", MoCEntityMouse::new, EntityClassification.CREATURE, 0.3F, 0.3F);
    public static EntityType OSTRICH = buildType("ostrich", MoCEntityOstrich::new, EntityClassification.CREATURE, 1.0F, 1.6F);
    public static EntityType PANDA_BEAR = buildType("pandabear", MoCEntityPandaBear::new, EntityClassification.CREATURE, 1.2F, 1.5F);
    public static EntityType PANTHARD = buildType("panthard", MoCEntityPanthard::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static EntityType PANTHER = buildType("panther", MoCEntityPanther::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static EntityType PANTHGER = buildType("panthger", MoCEntityPanthger::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static EntityType PET_SCORPION = buildType("petscorpion", MoCEntityPetScorpion::new, EntityClassification.CREATURE, 1.4F, 0.9F);
    public static EntityType POLAR_BEAR = buildType("wildpolarbear", MoCEntityPolarBear::new, EntityClassification.CREATURE, 1.2F, 1.5F);
    public static EntityType RACCOON = buildType("raccoon", MoCEntityRaccoon::new, EntityClassification.CREATURE, 0.5F, 0.6F);
    public static EntityType SNAKE = buildType("snake", MoCEntitySnake::new, EntityClassification.CREATURE, 1.4F, 0.5F);
    public static EntityType TIGER = buildType("tiger", MoCEntityTiger::new, EntityClassification.CREATURE, 1.4F, 1.3F);
    public static EntityType TURTLE = buildType("turtle", MoCEntityTurtle::new, EntityClassification.CREATURE, 0.6F, 0.4F);
    public static EntityType TURKEY = buildType("turkey", MoCEntityTurkey::new, EntityClassification.CREATURE, 0.8F, 1.0F);
    public static EntityType WILDHORSE = buildType("wildhorse", MoCEntityHorse::new, EntityClassification.CREATURE, 1.4F, 1.6F);
    public static EntityType WYVERN = buildType("wyvern", MoCEntityWyvern::new, EntityClassification.CREATURE, 1.9F, 1.7F);
    
    /**
     * Mobs
     */
    public static EntityType CAVE_OGRE = buildType("caveogre", MoCEntityCaveOgre::new, EntityClassification.MONSTER, 1.9F, 3F);
    public static EntityType FLAME_WRAITH = buildType("flamewraith", MoCEntityFlameWraith::new, EntityClassification.MONSTER, 1.5F, 1.5F, true);
    public static EntityType FIRE_OGRE = buildType("fireogre", MoCEntityFireOgre::new, EntityClassification.MONSTER, 1.9F, 3F, true);
    public static EntityType GREEN_OGRE = buildType("greenogre", MoCEntityGreenOgre::new, EntityClassification.MONSTER, 1.9F, 3F);
    public static EntityType BIG_GOLEM = buildType("biggolem", MoCEntityGolem::new, EntityClassification.MONSTER,1.5F, 4F);
    public static EntityType HORSEMOB = buildType("horsemob", MoCEntityHorseMob::new, EntityClassification.MONSTER, 1.4F, 1.6F);
    public static EntityType HELLRAT = buildType("hellrat", MoCEntityHellRat::new, EntityClassification.MONSTER, 0.7F, 0.7F, true);
    public static EntityType MANTICORE = buildType("manticore", MoCEntityManticore::new, EntityClassification.MONSTER, 1.4F, 1.6F, true);
    public static EntityType MINI_GOLEM = buildType("minigolem", MoCEntityMiniGolem::new, EntityClassification.MONSTER, 1.0F, 1.0F);
    public static EntityType RAT = buildType("rat", MoCEntityRat::new, EntityClassification.MONSTER, 0.5F, 0.5F);
    public static EntityType SILVER_SKELETON = buildType("silverskeleton", MoCEntitySilverSkeleton::new, EntityClassification.MONSTER, 0.9F, 1.4F);
    public static EntityType SCORPION = buildType("scorpion", MoCEntityScorpion::new, EntityClassification.MONSTER, 1.4F, 0.9F);
    public static EntityType WEREWOLF = buildType("werewolf", MoCEntityWerewolf::new, EntityClassification.MONSTER, 0.9F, 1.6F);
    public static EntityType WRAITH = buildType("wraith", MoCEntityWraith::new, EntityClassification.MONSTER, 1.5F, 1.5F);
    public static EntityType WWOLF = buildType("wwolf", MoCEntityWWolf::new, EntityClassification.MONSTER, 0.9F, 1.3F);
    
    /**
     * Aquatic
     */
    public static EntityType ANCHOVY = createEntityEntry(MoCEntityAnchovy.class, "Anchovy", 5665535, 205);
    public static EntityType ANGELFISH = createEntityEntry(MoCEntityAngelFish.class, "AngelFish", 5665535, 7434694);
    public static EntityType ANGLER = createEntityEntry(MoCEntityAngler.class, "Angler", 5665535, 10);
    public static EntityType BASS = createEntityEntry(MoCEntityBass.class, "Bass", 33023, 2372490);
    public static EntityType CLOWNFISH = createEntityEntry(MoCEntityClownFish.class, "ClownFish", 5665535, 14772545);
    public static EntityType COD = createEntityEntry(MoCEntityCod.class, "Cod", 33023, 16622);
    public static EntityType DOLPHIN = createEntityEntry(MoCEntityDolphin.class, "Dolphin", 33023, 15631086);//, 0x2600, 0x052500);
    public static EntityType FISHY = createEntityEntry(MoCEntityFishy.class, "Fishy", 5665535, 65407);//, 0x2600, 0x052500);
    public static EntityType GOLDFISH = createEntityEntry(MoCEntityGoldFish.class, "GoldFish", 5665535, 15656192);
    public static EntityType HIPPOTANG = createEntityEntry(MoCEntityHippoTang.class, "HippoTang", 5665535, 2037680);
    public static EntityType JELLYFISH = createEntityEntry(MoCEntityJellyFish.class, "JellyFish", 33023, 14772545);//, 0x2600, 0x052500);
    public static EntityType MANDERIN = createEntityEntry(MoCEntityManderin.class, "Manderin", 5665535, 12623485);
    public static EntityType PIRANHA = createEntityEntry(MoCEntityPiranha.class, "Piranha", 33023, 16711680);
    public static EntityType SALMON = createEntityEntry(MoCEntitySalmon.class, "Salmon", 33023, 12623485);
    public static EntityType MANTARAY = createEntityEntry(MoCEntityMantaRay.class, "MantaRay", 33023, 9141102);//14772545, 9141102);
    public static EntityType SHARK = createEntityEntry(MoCEntityShark.class, "Shark", 33023, 9013643);//, 0x2600, 0x052500);
    public static EntityType STINGRAY = createEntityEntry(MoCEntityStingRay.class, "StingRay", 33023, 6053069);//14772545, 9141102);
    
    /**
     * Ambients
     */
    public static EntityType ANT = createEntityEntry(MoCEntityAnt.class, "Ant", 65407, 12623485);
    public static EntityType BEE = createEntityEntry(MoCEntityBee.class, "Bee", 65407, 15656192);//, 0x2600, 0x052500);
    public static EntityType BUTTERFLY = createEntityEntry(MoCEntityButterfly.class, "ButterFly", 65407, 7434694);//, 0x22600, 0x012500);
    public static EntityType CRAB = createEntityEntry(MoCEntityCrab.class, "Crab", 65407, 13749760);
    public static EntityType CRICKET = createEntityEntry(MoCEntityCricket.class, "Cricket", 65407, 16622);//, 0x2600, 0x052500);
    public static EntityType DRAGONFLY = createEntityEntry(MoCEntityDragonfly.class, "DragonFly", 65407, 14020607);//, 0x2600, 0x052500);
    public static EntityType FIREFLY = createEntityEntry(MoCEntityFirefly.class, "Firefly", 65407, 9320590);//, 0x2600, 0x052500);
    public static EntityType FLY = createEntityEntry(MoCEntityFly.class, "Fly", 65407, 1);//, 0x2600, 0x052500);
    public static EntityType MAGGOT = createEntityEntry(MoCEntityMaggot.class, "Maggot", 65407, 9141102);
    public static EntityType SNAIL = createEntityEntry(MoCEntitySnail.class, "Snail", 65407, 14772545);//, 0x2600, 0x052500);
    public static EntityType ROACH = createEntityEntry(MoCEntityRoach.class, "Roach", 65407, 13749760);
    
    /**
     * Others
     */
    public static EntityType EGG = buildType("egg", MoCEntityEgg::new, EntityClassification.MISC, 0.25F, 0.25F);
    public static EntityType KITTY_BED = createEntityEntry(MoCEntityKittyBed.class, "KittyBed");
    public static EntityType LITTERBOX = createEntityEntry(MoCEntityLitterBox.class, "LitterBox");
    public static EntityType TROCK = createEntityEntry(MoCEntityThrowableRock.class, "TRock");

    @Mod.EventBusSubscriber(modid = MOD_ID)
    public static class RegistrationHandler {

        @SubscribeEvent
        public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
            MoCreatures.LOGGER.info("Registering entities...");
            final IForgeRegistry<EntityType<?>> registry = event.getRegistry();
            for (EntityEntry entry : ENTITIES) {
                // TODO: Re-enable when registry works properly
                //registry.register(entry);
                registerEntity(entry.getEntityClass(), entry.getName());
            }
            for (EntityEntry entry : SPAWN_ENTITIES) {
                // TODO: Re-enable when registry works properly
                //registry.register(entry);
                registerEntity(entry.getEntityClass(), entry.getName(), entry.getEgg().primaryColor, entry.getEgg().secondaryColor);
            }

            // ambients
            MoCreatures.mocEntityMap.put("Ant", new MoCEntityData("Ant", 4, EntityClassification.AMBIENT, new SpawnListEntry(MoCEntityAnt.class, 7, 1, 4), new ArrayList<Type>(
                    Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
            MoCreatures.mocEntityMap.put("Bee", new MoCEntityData("Bee", 3, EntityClassification.AMBIENT, new SpawnListEntry(MoCEntityBee.class, 6, 1, 2), new ArrayList<Type>(
                    Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("ButterFly", new MoCEntityData("ButterFly", 3, EntityClassification.AMBIENT, new SpawnListEntry(MoCEntityButterfly.class, 8, 1,
                    3), new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Crab", new MoCEntityData("Crab", 2, EntityClassification.AMBIENT, new SpawnListEntry(MoCEntityCrab.class, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.WATER))));
            MoCreatures.mocEntityMap.put("Cricket", new MoCEntityData("Cricket", 2, EntityClassification.AMBIENT, new SpawnListEntry(MoCEntityCricket.class, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP))));
            MoCreatures.mocEntityMap.put("DragonFly", new MoCEntityData("DragonFly", 2, EntityClassification.AMBIENT, new SpawnListEntry(MoCEntityDragonfly.class, 6, 1,
                    2), new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.BEACH))));
            MoCreatures.mocEntityMap.put("Firefly", new MoCEntityData("Firefly", 3, EntityClassification.AMBIENT, new SpawnListEntry(MoCEntityFirefly.class, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP))));
            MoCreatures.mocEntityMap.put("Fly", new MoCEntityData("Fly", 2, EntityClassification.AMBIENT, new SpawnListEntry(MoCEntityFly.class, 8, 1, 2), new ArrayList<Type>(
                    Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
            MoCreatures.mocEntityMap.put("Maggot", new MoCEntityData("Maggot", 2, EntityClassification.AMBIENT, new SpawnListEntry(MoCEntityMaggot.class, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SWAMP))));
            MoCreatures.mocEntityMap.put("Snail", new MoCEntityData("Snail", 2, EntityClassification.AMBIENT, new SpawnListEntry(MoCEntitySnail.class, 7, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Roach", new MoCEntityData("Roach", 2, EntityClassification.AMBIENT, new SpawnListEntry(MoCEntityRoach.class, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SWAMP))));

            // creatures
            MoCreatures.mocEntityMap.put("BlackBear", new MoCEntityData("BlackBear", 4, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityBlackBear.class, 6, 1, 3),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("GrizzlyBear", new MoCEntityData("GrizzlyBear", 4, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityGrizzlyBear.class, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("WildPolarBear", new MoCEntityData("WildPolarBear", 4, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityPolarBear.class, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.SNOWY))));
            MoCreatures.mocEntityMap.put("PandaBear", new MoCEntityData("PandaBear", 4, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityPandaBear.class, 6, 1, 3),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Bird", new MoCEntityData("Bird", 4, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityBird.class, 15, 2, 3),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Boar", new MoCEntityData("Boar", 3, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityBoar.class, 8, 2, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Bunny", new MoCEntityData("Bunny", 4, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityBunny.class, 8, 2, 3),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SNOWY))));
            MoCreatures.mocEntityMap.put("Crocodile", new MoCEntityData("Crocodile", 2, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityCrocodile.class, 6, 1,
                    2), new ArrayList<Type>(Arrays.asList(Type.SWAMP))));
            MoCreatures.mocEntityMap.put("Deer", new MoCEntityData("Deer", 2, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityDeer.class, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Duck", new MoCEntityData("Duck", 3, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityDuck.class, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Elephant", new MoCEntityData("Elephant", 3, EntityClassification.CREATURE,
                    new SpawnListEntry(MoCEntityElephant.class, 4, 1, 1),
                    new ArrayList<Type>(Arrays.asList(Type.SANDY, Type.FOREST, Type.PLAINS, Type.SNOWY))));
            MoCreatures.mocEntityMap.put("Ent", new MoCEntityData("Ent", 3, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityEnt.class, 4, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Fox", new MoCEntityData("Fox", 2, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityFox.class, 8, 1, 1),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SNOWY))));
            MoCreatures.mocEntityMap.put("Goat", new MoCEntityData("Goat", 2, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityGoat.class, 8, 1, 3),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Kitty", new MoCEntityData("Kitty", 3, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityKitty.class, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.PLAINS))));
            MoCreatures.mocEntityMap.put("KomodoDragon", new MoCEntityData("KomodoDragon", 2, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityKomodo.class, 8,
                    1, 2), new ArrayList<Type>(Arrays.asList(Type.SWAMP))));
            MoCreatures.mocEntityMap.put("Leopard", new MoCEntityData("Leopard", 4, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityLeopard.class, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.HILLS, Type.JUNGLE, Type.MOUNTAIN, Type.SNOWY))));
            MoCreatures.mocEntityMap.put("Lion", new MoCEntityData("Lion", 4, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityLion.class, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.JUNGLE, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Mole", new MoCEntityData("Mole", 3, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityMole.class, 7, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Mouse", new MoCEntityData("Mouse", 2, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityMouse.class, 7, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Ostrich", new MoCEntityData("Ostrich", 3, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityOstrich.class, 4, 1, 1),
                    new ArrayList<Type>(Arrays.asList(Type.SANDY, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Panther", new MoCEntityData("Panther", 4, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityPanther.class, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MOUNTAIN))));
            MoCreatures.mocEntityMap.put("Raccoon", new MoCEntityData("Raccoon", 2, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityRaccoon.class, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put(
                    "Snake",
                    new MoCEntityData("Snake", 3, EntityClassification.CREATURE, new SpawnListEntry(MoCEntitySnake.class, 8, 1, 2), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.HILLS, Type.JUNGLE, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
            MoCreatures.mocEntityMap.put("Tiger", new MoCEntityData("Tiger", 4, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityTiger.class, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Turkey", new MoCEntityData("Turkey", 2, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityTurkey.class, 8, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Turtle", new MoCEntityData("Turtle", 3, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityTurtle.class, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.JUNGLE, Type.SWAMP))));
            MoCreatures.mocEntityMap.put("WildHorse", new MoCEntityData("WildHorse", 4, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityHorse.class, 8, 1, 4),
                    new ArrayList<Type>(Arrays.asList(Type.FOREST, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS))));
            MoCreatures.mocEntityMap.put("Wyvern", new MoCEntityData("Wyvern", 3, EntityClassification.CREATURE, new SpawnListEntry(MoCEntityWyvern.class, 8, 1, 3),
                    new ArrayList<Type>()));
            // water creatures
            MoCreatures.mocEntityMap.put("Bass", new MoCEntityData("Bass", 4, EntityClassification.WATER_CREATURE, new SpawnListEntry(
                    MoCEntityBass.class, 10, 1, 4), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Cod", new MoCEntityData("Cod", 4, EntityClassification.WATER_CREATURE, new SpawnListEntry(
                    MoCEntityCod.class, 10, 1, 4), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Dolphin", new MoCEntityData("Dolphin", 3, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityDolphin.class, 6, 2,
                    4), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Fishy", new MoCEntityData("Fishy", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityFishy.class, 12, 1, 6),
                    new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("JellyFish", new MoCEntityData("JellyFish", 4, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityJellyFish.class,
                    8, 1, 4), new ArrayList<Type>(Arrays.asList(Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Salmon", new MoCEntityData("Salmon", 4, EntityClassification.WATER_CREATURE, new SpawnListEntry(
                    MoCEntitySalmon.class, 10, 1, 4), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Piranha", new MoCEntityData("Piranha", 4, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityPiranha.class, 4, 1,
                    3), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("MantaRay", new MoCEntityData("MantaRay", 3, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityMantaRay.class,
                    10, 1, 2), new ArrayList<Type>(Arrays.asList(Type.OCEAN))));
            MoCreatures.mocEntityMap.put("StingRay", new MoCEntityData("StingRay", 3, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityStingRay.class,
                    10, 1, 2), new ArrayList<Type>(Arrays.asList(Type.SWAMP, Type.WATER, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Shark", new MoCEntityData("Shark", 3, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityShark.class, 6, 1, 2),
                    new ArrayList<Type>(Arrays.asList(Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Anchovy", new MoCEntityData("Anchovy", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityAnchovy.class,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("AngelFish", new MoCEntityData("AngelFish", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityAngelFish.class,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN, Type.RIVER))));
            MoCreatures.mocEntityMap.put("Angler", new MoCEntityData("Angler", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityAngler.class,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN))));
            MoCreatures.mocEntityMap.put("ClownFish", new MoCEntityData("ClownFish", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityClownFish.class,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN))));
            MoCreatures.mocEntityMap.put("GoldFish", new MoCEntityData("GoldFish", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityGoldFish.class,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.RIVER))));
            MoCreatures.mocEntityMap.put("HippoTang", new MoCEntityData("HippoTang", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityHippoTang.class,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN))));
            MoCreatures.mocEntityMap.put("Manderin", new MoCEntityData("Manderin", 6, EntityClassification.WATER_CREATURE, new SpawnListEntry(MoCEntityManderin.class,
                    12, 1, 6), new ArrayList<Type>(Arrays.asList(Type.BEACH, Type.SWAMP, Type.WATER, Type.OCEAN))));
            
            // monsters
            MoCreatures.mocEntityMap.put(
                    "BigGolem",
                    new MoCEntityData("BigGolem", 1, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityGolem.class, 3, 1, 1), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND))));
            MoCreatures.mocEntityMap.put(
                    "FlameWraith",
                    new MoCEntityData("FlameWraith", 3, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityFlameWraith.class, 5, 1, 2), new ArrayList<Type>(
                            Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.NETHER, Type.PLAINS,
                                    Type.SWAMP, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put("HellRat", new MoCEntityData("HellRat", 4, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityHellRat.class, 6, 1, 4),
                    new ArrayList<Type>(Arrays.asList(Type.NETHER, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "HorseMob",
                    new MoCEntityData("HorseMob", 3, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityHorseMob.class, 8, 1, 3), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "MiniGolem",
                    new MoCEntityData("MiniGolem", 2, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityMiniGolem.class, 6, 1, 3), new ArrayList<Type>(
                            Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND))));
            MoCreatures.mocEntityMap.put(
                    "GreenOgre",
                    new MoCEntityData("GreenOgre", 3, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityGreenOgre.class, 8, 1, 2), new ArrayList<Type>(Arrays.asList(
                            Type.SANDY, Type.FOREST, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                            Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "FireOgre",
                    new MoCEntityData("FireOgre", 3, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityFireOgre.class, 6, 1, 2), new ArrayList<Type>(Arrays.asList(
                            Type.SANDY, Type.FOREST, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP,
                            Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "CaveOgre",
                    new MoCEntityData("CaveOgre", 3, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityCaveOgre.class, 5, 1, 2), new ArrayList<Type>(Arrays.asList(
                            Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                            Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "Rat",
                    new MoCEntityData("Rat", 2, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityRat.class, 7, 1, 2), new ArrayList<Type>(Arrays.asList(
                            Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND))));
            MoCreatures.mocEntityMap.put(
                    "Scorpion",
                    new MoCEntityData("Scorpion", 3, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityScorpion.class, 6, 1, 3), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.NETHER, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND))));
            MoCreatures.mocEntityMap.put(
                    "SilverSkeleton",
                    new MoCEntityData("SilverSkeleton", 4, EntityClassification.MONSTER, new SpawnListEntry(MoCEntitySilverSkeleton.class, 6, 1, 4),
                            new ArrayList<Type>(Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS,
                                    Type.SWAMP, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "Werewolf",
                    new MoCEntityData("Werewolf", 3, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityWerewolf.class, 8, 1, 4), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND))));
            MoCreatures.mocEntityMap.put(
                    "Wraith",
                    new MoCEntityData("Wraith", 3, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityWraith.class, 6, 1, 4), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
            MoCreatures.mocEntityMap.put(
                    "WWolf",
                    new MoCEntityData("WWolf", 3, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityWWolf.class, 8, 1, 3), new ArrayList<Type>(Arrays
                            .asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND))));
            MoCreatures.mocEntityMap.put(
                    "Manticore",
                    new MoCEntityData("Manticore", 3, EntityClassification.MONSTER, new SpawnListEntry(MoCEntityManticore.class, 8, 1, 3), new ArrayList<Type>(
                            Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP,
                                    Type.WASTELAND, Type.NETHER, Type.DEAD, Type.SPOOKY))));

            for (MoCEntityData entityData : MoCreatures.mocEntityMap.values()) {
                if (entityData.getEntityName().equals("Wyvern")) {
                    continue;
                }
                SpawnListEntry spawnEntry = entityData.getSpawnListEntry();
                for (Type type : entityData.getBiomeTypes()) {
                    for (Biome biome : BiomeDictionary.getBiomes(type)) {
                        if (!biome.getSpawnableList(entityData.getType()).contains(spawnEntry)) {
                            biome.getSpawnableList(entityData.getType()).add(spawnEntry);
                        }
                    }
                }
            }
            MoCreatures.LOGGER.info("Entity registration complete.");
            MoCreatures.proxy.readMocConfigValues();
        }
    }
}
