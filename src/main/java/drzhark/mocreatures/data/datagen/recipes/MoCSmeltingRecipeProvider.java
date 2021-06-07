package drzhark.mocreatures.data.datagen.recipes;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.registry.MoCItems;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Consumer;

public final class MoCSmeltingRecipeProvider extends RecipeProvider {
    public MoCSmeltingRecipeProvider(DataGenerator generator) {
        super(generator);
    }

    @Override
    protected void buildShapelessRecipes(Consumer<IFinishedRecipe> consumer) {
        CookingRecipeBuilder.smelting(Ingredient.of(MoCItems.CRAB_RAW), MoCItems.CRAB_COOKED, 0.35F, 200).unlockedBy("has_crabraw", this.has(MoCItems.CRAB_RAW)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(MoCItems.CRAB_RAW), MoCItems.CRAB_COOKED, 0.35F, 100, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_crabraw", this.has(MoCItems.CRAB_RAW)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(MoCItems.CRAB_RAW), MoCItems.CRAB_COOKED, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_crabraw", this.has(MoCItems.CRAB_RAW)).save(consumer);

        CookingRecipeBuilder.smelting(Ingredient.of(MoCItems.RAT_RAW), MoCItems.RAT_COOKED, 0.35F, 200).unlockedBy("has_ratraw", this.has(MoCItems.RAT_RAW)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(MoCItems.RAT_RAW), MoCItems.RAT_COOKED, 0.35F, 100, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_ratraw", this.has(MoCItems.RAT_RAW)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(MoCItems.RAT_RAW), MoCItems.RAT_COOKED, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ratraw", this.has(MoCItems.RAT_RAW)).save(consumer);

        CookingRecipeBuilder.smelting(Ingredient.of(MoCItems.OSTRICH_RAW), MoCItems.OSTRICH_COOKED, 0.35F, 200).unlockedBy("has_ostrichraw", this.has(MoCItems.OSTRICH_RAW)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(MoCItems.OSTRICH_RAW), MoCItems.OSTRICH_COOKED, 0.35F, 100, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_ostrichraw", this.has(MoCItems.OSTRICH_RAW)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(MoCItems.OSTRICH_RAW), MoCItems.OSTRICH_COOKED, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_ostrichraw", this.has(MoCItems.OSTRICH_RAW)).save(consumer);

        CookingRecipeBuilder.smelting(Ingredient.of(MoCItems.TURKEY_RAW), MoCItems.TURKEY_COOKED, 0.35F, 200).unlockedBy("has_turkeyraw", this.has(MoCItems.TURKEY_RAW)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(MoCItems.TURKEY_RAW), MoCItems.TURKEY_COOKED, 0.35F, 100, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_turkeyraw", this.has(MoCItems.TURKEY_RAW)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(MoCItems.TURKEY_RAW), MoCItems.TURKEY_COOKED, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_turkeyraw", this.has(MoCItems.TURKEY_COOKED)).save(consumer);

        CookingRecipeBuilder.smelting(Ingredient.of(MoCItems.MOCEGG), MoCItems.OMELET, 0.35F, 200).unlockedBy("has_mocegg", this.has(MoCItems.MOCEGG)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(MoCItems.MOCEGG), MoCItems.OMELET, 0.35F, 100, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_mocegg", this.has(MoCItems.MOCEGG)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(MoCItems.MOCEGG), MoCItems.OMELET, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_mocegg", this.has(MoCItems.MOCEGG)).save(consumer);

        CookingRecipeBuilder.smelting(Ingredient.of(Items.EGG), MoCItems.OMELET, 0.35F, 200).unlockedBy("has_egg", this.has(Items.EGG)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(Items.EGG), MoCItems.OMELET, 0.35F, 100, IRecipeSerializer.SMOKING_RECIPE).unlockedBy("has_egg", this.has(Items.EGG)).save(consumer);
        CookingRecipeBuilder.cooking(Ingredient.of(Items.EGG), MoCItems.OMELET, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING_RECIPE).unlockedBy("has_egg", this.has(Items.EGG)).save(consumer);
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    @Override
    public String getName() {
        return MoCConstants.MOD_ID + ": Smelting recipes";
    }
}
