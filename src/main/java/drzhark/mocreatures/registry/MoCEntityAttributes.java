package drzhark.mocreatures.registry;

import drzhark.mocreatures.configuration.MoCConfig;
import drzhark.mocreatures.entity.ambient.*;
import drzhark.mocreatures.entity.aquatic.*;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.entity.monster.*;
import drzhark.mocreatures.entity.passive.*;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;

public class MoCEntityAttributes {
    public static void init() {
        //Creatures
        /*register(MoCEntities.BIRD, MoCEntityBird.registerAttributes().func_233813_a_());
        register(MoCEntities.BLACK_BEAR, MoCEntityBlackBear.registerAttributes().func_233813_a_());
        register(MoCEntities.BOAR, MoCEntityBoar.registerAttributes().func_233813_a_());
        register(MoCEntities.BUNNY, MoCEntityBunny.registerAttributes().func_233813_a_());
        register(MoCEntities.CROCODILE, MoCEntityCrocodile.registerAttributes().func_233813_a_());
        register(MoCEntities.DUCK, MoCEntityDuck.registerAttributes().func_233813_a_());
        register(MoCEntities.DEER, MoCEntityDeer.registerAttributes().func_233813_a_());
        register(MoCEntities.ELEPHANT, MoCEntityElephant.registerAttributes().func_233813_a_());
        register(MoCEntities.ENT, MoCEntityEnt.registerAttributes().func_233813_a_());
        register(MoCEntities.FOX, MoCEntityFox.registerAttributes().func_233813_a_());*/
        register(MoCEntities.GOAT, MoCEntityGoat.registerAttributes().func_233813_a_());
        /*register(MoCEntities.GRIZZLY_BEAR, MoCEntityGrizzlyBear.registerAttributes().func_233813_a_());
        register(MoCEntities.KITTY, MoCEntityKitty.registerAttributes().func_233813_a_());
        register(MoCEntities.KOMODO_DRAGON, MoCEntityKomodo.registerAttributes().func_233813_a_());
        register(MoCEntities.LEOGER, MoCEntityLeoger.registerAttributes().func_233813_a_());
        register(MoCEntities.LEOPARD, MoCEntityLeopard.registerAttributes().func_233813_a_());
        register(MoCEntities.LIARD, MoCEntityLiard.registerAttributes().func_233813_a_());
        register(MoCEntities.LION, MoCEntityLion.registerAttributes().func_233813_a_());
        register(MoCEntities.LIGER, MoCEntityLiger.registerAttributes().func_233813_a_());
        register(MoCEntities.LITHER, MoCEntityLither.registerAttributes().func_233813_a_());
        register(MoCEntities.MANTICORE_PET, MoCEntityManticorePet.registerAttributes().func_233813_a_());
        register(MoCEntities.MOLE, MoCEntityMole.registerAttributes().func_233813_a_());
        register(MoCEntities.MOUSE, MoCEntityMouse.registerAttributes().func_233813_a_());
        register(MoCEntities.OSTRICH, MoCEntityOstrich.registerAttributes().func_233813_a_());
        register(MoCEntities.PANDA_BEAR, MoCEntityPandaBear.registerAttributes().func_233813_a_());
        register(MoCEntities.PANTHARD, MoCEntityPanthard.registerAttributes().func_233813_a_());
        register(MoCEntities.PANTHER, MoCEntityPanther.registerAttributes().func_233813_a_());
        register(MoCEntities.PANTHGER, MoCEntityPanthger.registerAttributes().func_233813_a_());
        register(MoCEntities.PET_SCORPION, MoCEntityPetScorpion.registerAttributes().func_233813_a_());
        register(MoCEntities.POLAR_BEAR, MoCEntityPolarBear.registerAttributes().func_233813_a_());
        register(MoCEntities.RACCOON, MoCEntityRaccoon.registerAttributes().func_233813_a_());
        register(MoCEntities.SNAKE, MoCEntitySnake.registerAttributes().func_233813_a_());
        register(MoCEntities.TIGER, MoCEntityTiger.registerAttributes().func_233813_a_());
        register(MoCEntities.TURTLE, MoCEntityTurtle.registerAttributes().func_233813_a_());
        register(MoCEntities.TURKEY, MoCEntityTurkey.registerAttributes().func_233813_a_());
        register(MoCEntities.WILDHORSE, MoCEntityHorse.registerAttributes().func_233813_a_());
        register(MoCEntities.WYVERN, MoCEntityWyvern.registerAttributes().func_233813_a_());*/

        //Mobs
        register(MoCEntities.CAVE_OGRE, MoCEntityCaveOgre.registerAttributes().func_233813_a_());
        register(MoCEntities.FIRE_OGRE, MoCEntityFireOgre.registerAttributes().func_233813_a_());
        register(MoCEntities.GREEN_OGRE, MoCEntityGreenOgre.registerAttributes().func_233813_a_());
        register(MoCEntities.FLAME_WRAITH, MoCEntityFlameWraith.registerAttributes().func_233813_a_());
        register(MoCEntities.WRAITH, MoCEntityWraith.registerAttributes().func_233813_a_());
        register(MoCEntities.HELLRAT, MoCEntityHellRat.registerAttributes().func_233813_a_());
        register(MoCEntities.RAT, MoCEntityRat.registerAttributes().func_233813_a_());
        register(MoCEntities.BIG_GOLEM, MoCEntityGolem.registerAttributes().func_233813_a_());
        register(MoCEntities.HORSEMOB, MoCEntityHorseMob.registerAttributes().func_233813_a_());
        register(MoCEntities.MANTICORE, MoCEntityManticore.registerAttributes().func_233813_a_());
        register(MoCEntities.MINI_GOLEM, MoCEntityMiniGolem.registerAttributes().func_233813_a_());
        register(MoCEntities.SILVER_SKELETON, MoCEntitySilverSkeleton.registerAttributes().func_233813_a_());
        register(MoCEntities.SCORPION, MoCEntityScorpion.registerAttributes().func_233813_a_());
        register(MoCEntities.WEREWOLF, MoCEntityWerewolf.registerAttributes().func_233813_a_());
        register(MoCEntities.WWOLF, MoCEntityWWolf.registerAttributes().func_233813_a_());

        //Aquatic
        /*register(MoCEntities.ANCHOVY, MoCEntityAnchovy.registerAttributes().func_233813_a_());
        register(MoCEntities.ANGELFISH, MoCEntityAngelFish.registerAttributes().func_233813_a_());
        register(MoCEntities.ANGLER, MoCEntityAngler.registerAttributes().func_233813_a_());
        register(MoCEntities.BASS, MoCEntityBass.registerAttributes().func_233813_a_());
        register(MoCEntities.CLOWNFISH, MoCEntityClownFish.registerAttributes().func_233813_a_());
        register(MoCEntities.COD, MoCEntityCod.registerAttributes().func_233813_a_());
        register(MoCEntities.DOLPHIN, MoCEntityDolphin.registerAttributes().func_233813_a_());
        register(MoCEntities.FISHY, MoCEntityFishy.registerAttributes().func_233813_a_());
        register(MoCEntities.GOLDFISH, MoCEntityGoldFish.registerAttributes().func_233813_a_());
        register(MoCEntities.HIPPOTANG, MoCEntityHippoTang.registerAttributes().func_233813_a_());
        register(MoCEntities.JELLYFISH, MoCEntityJellyFish.registerAttributes().func_233813_a_());
        register(MoCEntities.MANDERIN, MoCEntityManderin.registerAttributes().func_233813_a_());
        register(MoCEntities.PIRANHA, MoCEntityPiranha.registerAttributes().func_233813_a_());
        register(MoCEntities.SALMON, MoCEntitySalmon.registerAttributes().func_233813_a_());
        register(MoCEntities.MANTARAY, MoCEntityMantaRay.registerAttributes().func_233813_a_());
        register(MoCEntities.SHARK, MoCEntityShark.registerAttributes().func_233813_a_());
        register(MoCEntities.STINGRAY, MoCEntityStingRay.registerAttributes().func_233813_a_());*/

        //Ambients
        register(MoCEntities.ANT, MoCEntityAnt.registerAttributes().func_233813_a_());
        register(MoCEntities.BEE, MoCEntityBee.registerAttributes().func_233813_a_());
        register(MoCEntities.BUTTERFLY, MoCEntityButterfly.registerAttributes().func_233813_a_());
        register(MoCEntities.CRAB, MoCEntityCrab.registerAttributes().func_233813_a_());
        register(MoCEntities.CRICKET, MoCEntityCricket.registerAttributes().func_233813_a_());
        register(MoCEntities.DRAGONFLY, MoCEntityDragonfly.registerAttributes().func_233813_a_());
        register(MoCEntities.FIREFLY, MoCEntityFirefly.registerAttributes().func_233813_a_());
        register(MoCEntities.FLY, MoCEntityFly.registerAttributes().func_233813_a_());
        register(MoCEntities.MAGGOT, MoCEntityMaggot.registerAttributes().func_233813_a_());
        register(MoCEntities.SNAIL, MoCEntitySnail.registerAttributes().func_233813_a_());
        register(MoCEntities.ROACH, MoCEntityRoach.registerAttributes().func_233813_a_());

        //Others
        /*register(MoCEntities.EGG, MoCEntityEgg.registerAttributes().func_233813_a_());
        register(MoCEntities.KITTY_BED, MoCEntityKittyBed.registerAttributes().func_233813_a_());
        register(MoCEntities.LITTERBOX, MoCEntityLitterBox.registerAttributes().func_233813_a_());*/
    }

    private static<T extends LivingEntity> void register(EntityType<T> entityType, AttributeModifierMap map) {
        GlobalEntityTypeAttributes.put(entityType, map);
    }
}
