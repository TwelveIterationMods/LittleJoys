package net.blay09.mods.littlejoys.recipe;

import net.blay09.mods.balm.api.recipe.BalmRecipes;
import net.minecraft.world.item.crafting.RecipeBookCategory;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public class ModRecipeTypes {

    public static RecipeType<DigSpotRecipe> digSpotRecipeType;
    public static RecipeSerializer<DigSpotRecipe> digSpotRecipeSerializer;
    public static RecipeBookCategory digSpotRecipeBookCategory;

    public static RecipeType<FishingSpotRecipe> fishingSpotRecipeType;
    public static RecipeSerializer<FishingSpotRecipe> fishingSpotRecipeSerializer;
    public static RecipeBookCategory fishingSpotRecipeBookCategory;

    public static RecipeType<GoldRushRecipe> goldRushRecipeType;
    public static RecipeSerializer<GoldRushRecipe> goldRushRecipeSerializer;
    public static RecipeBookCategory goldRushRecipeBookCategory;

    public static RecipeType<DropRushRecipe> dropRushRecipeType;
    public static RecipeSerializer<DropRushRecipe> dropRushRecipeSerializer;
    public static RecipeBookCategory dropRushRecipeBookCategory;

    public static void initialize(BalmRecipes recipes) {
        recipes.registerRecipeType((identifier) -> digSpotRecipeType = new RecipeType<>() {
            @Override
            public String toString() {
                return identifier.getPath();
            }
        }, id("dig_spot"));
        recipes.registerRecipeSerializer(() -> digSpotRecipeSerializer = new DigSpotRecipe.Serializer(), id("dig_spot"));
        recipes.registerRecipeBookCategory(() -> digSpotRecipeBookCategory = new RecipeBookCategory(), id("dig_spot"));

        recipes.registerRecipeType((identifier) -> fishingSpotRecipeType = new RecipeType<>() {
            @Override
            public String toString() {
                return identifier.getPath();
            }
        }, id("fishing_spot"));
        recipes.registerRecipeSerializer(() -> fishingSpotRecipeSerializer = new FishingSpotRecipe.Serializer(), id("fishing_spot"));
        recipes.registerRecipeBookCategory(() -> fishingSpotRecipeBookCategory = new RecipeBookCategory(), id("fishing_spot"));

        recipes.registerRecipeType((identifier) -> goldRushRecipeType = new RecipeType<>() {
            @Override
            public String toString() {
                return identifier.getPath();
            }
        }, id("gold_rush"));
        recipes.registerRecipeSerializer(() -> goldRushRecipeSerializer = new GoldRushRecipe.Serializer(), id("gold_rush"));
        recipes.registerRecipeBookCategory(() -> goldRushRecipeBookCategory = new RecipeBookCategory(), id("gold_rush"));

        recipes.registerRecipeType((identifier) -> dropRushRecipeType = new RecipeType<>() {
            @Override
            public String toString() {
                return identifier.getPath();
            }
        }, id("drop_rush"));
        recipes.registerRecipeSerializer(() -> dropRushRecipeSerializer = new DropRushRecipe.Serializer(), id("drop_rush"));
        recipes.registerRecipeBookCategory(() -> dropRushRecipeBookCategory = new RecipeBookCategory(), id("drop_rush"));
    }
}
