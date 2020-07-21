package drzhark.mocreatures.data.datagen.recipes;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.init.MoCItems;
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
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MoCItems.CRAB_RAW), MoCItems.CRAB_COOKED, 0.35F, 200).addCriterion("has_crabraw", this.hasItem(MoCItems.CRAB_RAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.CRAB_RAW), MoCItems.CRAB_COOKED, 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_crabraw", this.hasItem(MoCItems.CRAB_RAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.CRAB_RAW), MoCItems.CRAB_COOKED, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_crabraw", this.hasItem(MoCItems.CRAB_RAW)).build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MoCItems.RAT_RAW), MoCItems.RAT_COOKED, 0.35F, 200).addCriterion("has_ratraw", this.hasItem(MoCItems.RAT_RAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.RAT_RAW), MoCItems.RAT_COOKED, 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_ratraw", this.hasItem(MoCItems.RAT_RAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.RAT_RAW), MoCItems.RAT_COOKED, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ratraw", this.hasItem(MoCItems.RAT_RAW)).build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MoCItems.OSTRICH_RAW), MoCItems.OSTRICH_COOKED, 0.35F, 200).addCriterion("has_ostrichraw", this.hasItem(MoCItems.OSTRICH_RAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.OSTRICH_RAW), MoCItems.OSTRICH_COOKED, 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_ostrichraw", this.hasItem(MoCItems.OSTRICH_RAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.OSTRICH_RAW), MoCItems.OSTRICH_COOKED, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ostrichraw", this.hasItem(MoCItems.OSTRICH_RAW)).build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MoCItems.TURKEY_RAW), MoCItems.TURKEY_COOKED, 0.35F, 200).addCriterion("has_turkeyraw", this.hasItem(MoCItems.TURKEY_RAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.TURKEY_RAW), MoCItems.TURKEY_COOKED, 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_turkeyraw", this.hasItem(MoCItems.TURKEY_RAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.TURKEY_RAW), MoCItems.TURKEY_COOKED, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_turkeyraw", this.hasItem(MoCItems.TURKEY_COOKED)).build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MoCItems.MOCEGG), MoCItems.OMELET, 0.35F, 200).addCriterion("has_mocegg", this.hasItem(MoCItems.MOCEGG)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.MOCEGG), MoCItems.OMELET, 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_mocegg", this.hasItem(MoCItems.MOCEGG)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.MOCEGG), MoCItems.OMELET, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_mocegg", this.hasItem(MoCItems.MOCEGG)).build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.EGG), MoCItems.OMELET, 0.35F, 200).addCriterion("has_egg", this.hasItem(Items.EGG)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.EGG), MoCItems.OMELET, 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_egg", this.hasItem(Items.EGG)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.EGG), MoCItems.OMELET, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_egg", this.hasItem(Items.EGG)).build(consumer);
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    @Override
    public String getName() {
        return MoCConstants.MOD_ID + ": Smelting recipes";
    }
}
