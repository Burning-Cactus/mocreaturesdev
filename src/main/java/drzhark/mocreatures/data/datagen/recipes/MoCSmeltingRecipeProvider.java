package drzhark.mocreatures.data.datagen.recipes;

import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.item.MoCItem;
import drzhark.mocreatures.item.MoCItemHorseAmulet;
import net.minecraft.block.Blocks;
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
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MoCItems.CRABRAW), MoCItems.CRABCOOKED, 0.35F, 200).addCriterion("has_crabraw", this.hasItem(MoCItems.CRABRAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.CRABRAW), MoCItems.CRABCOOKED, 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_crabraw", this.hasItem(MoCItems.CRABRAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.CRABRAW), MoCItems.CRABCOOKED, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_crabraw", this.hasItem(MoCItems.CRABRAW)).build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MoCItems.RATRAW), MoCItems.RATCOOKED, 0.35F, 200).addCriterion("has_ratraw", this.hasItem(MoCItems.RATRAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.RATRAW), MoCItems.RATCOOKED, 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_ratraw", this.hasItem(MoCItems.RATRAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.RATRAW), MoCItems.RATCOOKED, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ratraw", this.hasItem(MoCItems.RATRAW)).build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MoCItems.OSTRICHRAW), MoCItems.OSTRICHCOOKED, 0.35F, 200).addCriterion("has_ostrichraw", this.hasItem(MoCItems.OSTRICHRAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.OSTRICHRAW), MoCItems.OSTRICHCOOKED, 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_ostrichraw", this.hasItem(MoCItems.OSTRICHRAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.OSTRICHRAW), MoCItems.OSTRICHCOOKED, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_ostrichraw", this.hasItem(MoCItems.OSTRICHRAW)).build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MoCItems.TURKEYRAW), MoCItems.TURKEYCOOKED, 0.35F, 200).addCriterion("has_turkeyraw", this.hasItem(MoCItems.TURKEYRAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.TURKEYRAW), MoCItems.TURKEYCOOKED, 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_turkeyraw", this.hasItem(MoCItems.TURKEYRAW)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.TURKEYRAW), MoCItems.TURKEYCOOKED, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_turkeyraw", this.hasItem(MoCItems.TURKEYCOOKED)).build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(MoCItems.MOCEGG), MoCItems.OMELET, 0.35F, 200).addCriterion("has_mocegg", this.hasItem(MoCItems.MOCEGG)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.MOCEGG), MoCItems.OMELET, 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_mocegg", this.hasItem(MoCItems.MOCEGG)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(MoCItems.MOCEGG), MoCItems.OMELET, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_mocegg", this.hasItem(MoCItems.MOCEGG)).build(consumer);

        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(Items.EGG), MoCItems.OMELET, 0.35F, 200).addCriterion("has_egg", this.hasItem(Items.EGG)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.EGG), MoCItems.OMELET, 0.35F, 100, IRecipeSerializer.SMOKING).addCriterion("has_egg", this.hasItem(Items.EGG)).build(consumer);
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(Items.EGG), MoCItems.OMELET, 0.35F, 400, IRecipeSerializer.CAMPFIRE_COOKING).addCriterion("has_egg", this.hasItem(Items.EGG)).build(consumer);
    }
}
