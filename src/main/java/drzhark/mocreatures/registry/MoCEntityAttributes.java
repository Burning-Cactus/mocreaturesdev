package drzhark.mocreatures.registry;

import drzhark.mocreatures.entity.ambient.*;
import drzhark.mocreatures.entity.aquatic.*;
import drzhark.mocreatures.entity.monster.*;
import drzhark.mocreatures.entity.passive.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;

public class MoCEntityAttributes {
    public static void init() {
        //Creatures
        register(MoCEntities.BIRD, MoCEntityBird.registerAttributes().build());
        register(MoCEntities.BLACK_BEAR, MoCEntityBlackBear.registerAttributes().build());
        register(MoCEntities.BOAR, MoCEntityBoar.registerAttributes().build());
        register(MoCEntities.BUNNY, MoCEntityBunny.registerAttributes().build());
        register(MoCEntities.CROCODILE, MoCEntityCrocodile.registerAttributes().build());
        register(MoCEntities.DUCK, MoCEntityDuck.registerAttributes().build());
        register(MoCEntities.DEER, MoCEntityDeer.registerAttributes().build());
        register(MoCEntities.ELEPHANT, MoCEntityElephant.registerAttributes().build());
        register(MoCEntities.ENT, MoCEntityEnt.registerAttributes().build());
        register(MoCEntities.FOX, MoCEntityFox.registerAttributes().build());
        register(MoCEntities.GOAT, MoCEntityGoat.registerAttributes().build());
        register(MoCEntities.GRIZZLY_BEAR, MoCEntityGrizzlyBear.registerAttributes().build());
        register(MoCEntities.KITTY, MoCEntityKitty.registerAttributes().build());
        register(MoCEntities.KOMODO_DRAGON, MoCEntityKomodo.registerAttributes().build());
        register(MoCEntities.LEOGER, MoCEntityLeoger.registerAttributes().build());
        register(MoCEntities.LEOPARD, MoCEntityLeopard.registerAttributes().build());
        register(MoCEntities.LIARD, MoCEntityLiard.registerAttributes().build());
        register(MoCEntities.LION, MoCEntityLion.registerAttributes().build());
        register(MoCEntities.LIGER, MoCEntityLiger.registerAttributes().build());
        register(MoCEntities.LITHER, MoCEntityLither.registerAttributes().build());
        register(MoCEntities.MANTICORE_PET, MoCEntityManticorePet.registerAttributes().build());
        register(MoCEntities.MOLE, MoCEntityMole.registerAttributes().build());
        register(MoCEntities.MOUSE, MoCEntityMouse.registerAttributes().build());
        register(MoCEntities.OSTRICH, MoCEntityOstrich.registerAttributes().build());
        register(MoCEntities.PANDA_BEAR, MoCEntityPandaBear.registerAttributes().build());
        register(MoCEntities.PANTHARD, MoCEntityPanthard.registerAttributes().build());
        register(MoCEntities.PANTHER, MoCEntityPanther.registerAttributes().build());
        register(MoCEntities.PANTHGER, MoCEntityPanthger.registerAttributes().build());
        register(MoCEntities.PET_SCORPION, MoCEntityPetScorpion.registerAttributes().build());
        register(MoCEntities.POLAR_BEAR, MoCEntityPolarBear.registerAttributes().build());
        register(MoCEntities.RACCOON, MoCEntityRaccoon.registerAttributes().build());
        register(MoCEntities.SNAKE, MoCEntitySnake.registerAttributes().build());
        register(MoCEntities.TIGER, MoCEntityTiger.registerAttributes().build());
        register(MoCEntities.TURTLE, MoCEntityTurtle.registerAttributes().build());
        register(MoCEntities.TURKEY, MoCEntityTurkey.registerAttributes().build());
        register(MoCEntities.WILDHORSE, MoCEntityHorse.registerAttributes().build());
        register(MoCEntities.WYVERN, MoCEntityWyvern.registerAttributes().build());

        //Mobs
        register(MoCEntities.CAVE_OGRE, MoCEntityCaveOgre.registerAttributes().build());
        register(MoCEntities.FIRE_OGRE, MoCEntityFireOgre.registerAttributes().build());
        register(MoCEntities.GREEN_OGRE, MoCEntityGreenOgre.registerAttributes().build());
        register(MoCEntities.FLAME_WRAITH, MoCEntityFlameWraith.registerAttributes().build());
        register(MoCEntities.WRAITH, MoCEntityWraith.registerAttributes().build());
        register(MoCEntities.HELLRAT, MoCEntityHellRat.registerAttributes().build());
        register(MoCEntities.RAT, MoCEntityRat.registerAttributes().build());
        register(MoCEntities.BIG_GOLEM, MoCEntityGolem.registerAttributes().build());
        register(MoCEntities.HORSEMOB, MoCEntityHorseMob.registerAttributes().build());
        register(MoCEntities.MANTICORE, MoCEntityManticore.registerAttributes().build());
        register(MoCEntities.MINI_GOLEM, MoCEntityMiniGolem.registerAttributes().build());
        register(MoCEntities.SILVER_SKELETON, MoCEntitySilverSkeleton.registerAttributes().build());
        register(MoCEntities.SCORPION, MoCEntityScorpion.registerAttributes().build());
        register(MoCEntities.WEREWOLF, MoCEntityWerewolf.registerAttributes().build());
        register(MoCEntities.WWOLF, MoCEntityWWolf.registerAttributes().build());

        //Aquatic
        register(MoCEntities.ANCHOVY, MoCEntityAnchovy.registerAttributes().build());
        register(MoCEntities.ANGELFISH, MoCEntityAngelFish.registerAttributes().build());
        register(MoCEntities.ANGLER, MoCEntityAngler.registerAttributes().build());
        register(MoCEntities.BASS, MoCEntityBass.registerAttributes().build());
        register(MoCEntities.CLOWNFISH, MoCEntityClownFish.registerAttributes().build());
        register(MoCEntities.COD, MoCEntityCod.registerAttributes().build());
        register(MoCEntities.DOLPHIN, MoCEntityDolphin.registerAttributes().build());
        register(MoCEntities.FISHY, MoCEntityFishy.registerAttributes().build());
        register(MoCEntities.GOLDFISH, MoCEntityGoldFish.registerAttributes().build());
        register(MoCEntities.HIPPOTANG, MoCEntityHippoTang.registerAttributes().build());
        register(MoCEntities.JELLYFISH, MoCEntityJellyFish.registerAttributes().build());
        register(MoCEntities.MANDERIN, MoCEntityManderin.registerAttributes().build());
        register(MoCEntities.PIRANHA, MoCEntityPiranha.registerAttributes().build());
        register(MoCEntities.SALMON, MoCEntitySalmon.registerAttributes().build());
        register(MoCEntities.MANTARAY, MoCEntityMantaRay.registerAttributes().build());
        register(MoCEntities.SHARK, MoCEntityShark.registerAttributes().build());
        register(MoCEntities.STINGRAY, MoCEntityStingRay.registerAttributes().build());

        //Ambients
        register(MoCEntities.ANT, AntEntity.registerAttributes().build());
        register(MoCEntities.BEE, MoCBeeEntity.registerAttributes().build());
        register(MoCEntities.BUTTERFLY, ButterflyEntity.registerAttributes().build());
        register(MoCEntities.CRAB, CrabEntity.registerAttributes().build());
        register(MoCEntities.CRICKET, CricketEntity.registerAttributes().build());
        register(MoCEntities.DRAGONFLY, DragonflyEntity.registerAttributes().build());
        register(MoCEntities.FIREFLY, FireflyEntity.registerAttributes().build());
        register(MoCEntities.FLY, FlyEntity.registerAttributes().build());
        register(MoCEntities.MAGGOT, MaggotEntity.registerAttributes().build());
        register(MoCEntities.SNAIL, SnailEntity.registerAttributes().build());
        register(MoCEntities.ROACH, RoachEntity.registerAttributes().build());

        //Others
        /*register(MoCEntities.EGG, MoCEntityEgg.registerAttributes().build());
        register(MoCEntities.KITTY_BED, MoCEntityKittyBed.registerAttributes().build());
        register(MoCEntities.LITTERBOX, MoCEntityLitterBox.registerAttributes().build());*/
    }

    private static<T extends LivingEntity> void register(EntityType<T> entityType, AttributeModifierMap map) {
        GlobalEntityTypeAttributes.put(entityType, map);
    }
}
