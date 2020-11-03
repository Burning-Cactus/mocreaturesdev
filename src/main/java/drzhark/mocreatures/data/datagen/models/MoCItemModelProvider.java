package drzhark.mocreatures.data.datagen.models;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.registry.MoCBlocks;
import drzhark.mocreatures.registry.MoCItems;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

@SuppressWarnings("all")
public class MoCItemModelProvider extends ItemModelProvider {
    public MoCItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, MoCConstants.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.simpleItem(MoCItems.RECORDSHUFFLE);
        this.simpleItem(MoCItems.HORSESADDLE);
        this.simpleItem(MoCItems.SHARKTEETH);
        this.simpleItem(MoCItems.HAYSTACK);
        simpleItem(MoCItems.SUGARLUMP);
        simpleItem(MoCItems.MOCEGG);
        simpleItem(MoCItems.BIGCATCLAW);
        simpleItem(MoCItems.WHIP);
        simpleItem(MoCItems.STAFFPORTAL);
        simpleItem(MoCItems.STAFFTELEPORT);
        simpleItem(MoCItems.MEDALLION);
//        simpleItem(
        simpleItem(MoCItems.LITTERBOX);
        simpleItem(MoCItems.WOOLBALL);
        simpleItem(MoCItems.PETFOOD);
        handheldItem(MoCItems.BUILDERHAMMER);
        simpleItem(MoCItems.HIDECROC);
        simpleItem(MoCItems.FUR);
        simpleItem(MoCItems.ESSENCEDARKNESS);
        simpleItem(MoCItems.ESSENCEFIRE);
        simpleItem(MoCItems.ESSENCEUNDEAD);
        simpleItem(MoCItems.ESSENCELIGHT);
        simpleItem(MoCItems.HEARTDARKNESS);
        simpleItem(MoCItems.HEARTFIRE);
        simpleItem(MoCItems.HEARTUNDEAD);
        simpleItem(MoCItems.UNICORNHORN);
        simpleItem(MoCItems.AMULETBONE);
        simpleItem(MoCItems.AMULETBONEFULL);
        simpleItem(MoCItems.AMULETFAIRY);
        simpleItem(MoCItems.AMULETFAIRYFULL);
        simpleItem(MoCItems.AMULETGHOST);
        simpleItem(MoCItems.AMULETGHOSTFULL);
        simpleItem(MoCItems.AMULETPEGASUS);
        simpleItem(MoCItems.AMULETPEGASUSFULL);
        simpleItem(MoCItems.FISHNET);
        simpleItem(MoCItems.FISHNETFULL);
        simpleItem(MoCItems.PETAMULET);
        simpleItem(MoCItems.PETAMULETFULL);
        simpleItem(MoCItems.HORSEARMORCRYSTAL);
        simpleItem(MoCItems.ANIMALHIDE);
        simpleItem(MoCItems.CHITINCAVE);
        simpleItem(MoCItems.CHITINFROST);
        simpleItem(MoCItems.CHITINNETHER);
        simpleItem(MoCItems.CHITIN);
        handheldItem(MoCItems.NUNCHAKU);
        handheldItem(MoCItems.SAI);
        handheldItem(MoCItems.BO);
        handheldItem(MoCItems.KATANA);
        handheldItem(MoCItems.SHARKSWORD);
        handheldItem(MoCItems.SILVERSWORD);
        handheldItem(MoCItems.SCORPSWORDCAVE);
        handheldItem(MoCItems.SCORPSWORDFROST);
        handheldItem(MoCItems.SCORPSWORDNETHER);
        handheldItem(MoCItems.SCORPSWORDDIRT);
        handheldItem(MoCItems.SCORPSTINGCAVE);
        handheldItem(MoCItems.SCORPSTINGFROST);
        handheldItem(MoCItems.SCORPSTINGNETHER);
        handheldItem(MoCItems.SCORPSTINGDIRT);
        simpleItem(MoCItems.TUSKSWOOD);
        simpleItem(MoCItems.TUSKSIRON);
        simpleItem(MoCItems.TUSKSDIAMOND);
        simpleItem(MoCItems.PLATECROC);
        simpleItem(MoCItems.HELMETCROC);
        simpleItem(MoCItems.LEGSCROC);
        simpleItem(MoCItems.BOOTSCROC);
        simpleItem(MoCItems.CHESTFUR);
        simpleItem(MoCItems.HELMETFUR);
        simpleItem(MoCItems.LEGSFUR);
        simpleItem(MoCItems.BOOTSFUR);
        simpleItem(MoCItems.CHESTHIDE);
        simpleItem(MoCItems.HELMETHIDE);
        simpleItem(MoCItems.LEGSHIDE);
        simpleItem(MoCItems.BOOTSHIDE);
        simpleItem(MoCItems.SCORPPLATEDIRT);
        simpleItem(MoCItems.SCORPHELMETDIRT);
        simpleItem(MoCItems.SCORPLEGSDIRT);
        simpleItem(MoCItems.SCORPBOOTSDIRT);
        simpleItem(MoCItems.SCORPPLATECAVE);
        simpleItem(MoCItems.SCORPHELMETCAVE);
        simpleItem(MoCItems.SCORPLEGSCAVE);
        simpleItem(MoCItems.SCORPBOOTSCAVE);
        simpleItem(MoCItems.SCORPPLATEFROST);
        simpleItem(MoCItems.SCORPHELMETFROST);
        simpleItem(MoCItems.SCORPLEGSFROST);
        simpleItem(MoCItems.SCORPBOOTSFROST);
        simpleItem(MoCItems.SCORPPLATENETHER);
        simpleItem(MoCItems.SCORPHELMETNETHER);
        simpleItem(MoCItems.SCORPLEGSNETHER);
        simpleItem(MoCItems.SCORPBOOTSNETHER);
        simpleItem(MoCItems.ELEPHANTHARNESS);
        simpleItem(MoCItems.ELEPHANTCHEST);
        simpleItem(MoCItems.ELEPHANTGARMENT);
        simpleItem(MoCItems.ELEPHANTHOWDAH);
        simpleItem(MoCItems.MAMMOTHPLATFORM);
        simpleItem(MoCItems.SCROLLOFFREEDOM);
        simpleItem(MoCItems.SCROLLOFOWNER);
        simpleItem(MoCItems.SCROLLOFSALE);
        simpleItem(MoCItems.TURKEY_RAW);
        simpleItem(MoCItems.TURKEY_COOKED);
        simpleItem(MoCItems.CRAB_RAW);
        simpleItem(MoCItems.CRAB_COOKED);
        simpleItem(MoCItems.OMELET);
        simpleItem(MoCItems.OSTRICH_RAW);
        simpleItem(MoCItems.OSTRICH_COOKED);
        simpleItem(MoCItems.RAT_RAW);
        simpleItem(MoCItems.RAT_COOKED);
        simpleItem(MoCItems.RAT_BURGER);
        simpleItem(MoCItems.TURTLE_RAW);
        simpleItem(MoCItems.TURTLE_SOUP);
        tallGrass(MoCBlocks.WYVERN_TALLGRASS);
        tallGrass(MoCBlocks.OGRE_TALLGRASS);
        for(SpawnEggItem item : MoCItems.RegistrationHandler.SPAWN_EGGS) {
            withExistingParent(item.getRegistryName().getPath(), new ResourceLocation("item/template_spawn_egg"));
        }
    }

    private void simpleItem(Item item) {
        singleTexture(item.getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(MoCConstants.MOD_ID, "item/" + item.getRegistryName().getPath()));
    }

    private void handheldItem(Item item) {
        singleTexture(item.getRegistryName().getPath(), new ResourceLocation("item/handheld"), "layer0", new ResourceLocation(MoCConstants.MOD_ID, "item/" + item.getRegistryName().getPath()));
    }

    private void tallGrass(Block block) {
        singleTexture(block.getRegistryName().getPath(), new ResourceLocation("item/generated"), "layer0", new ResourceLocation(MoCConstants.MOD_ID, "block/" + block.getRegistryName().getPath()));
    }
}
