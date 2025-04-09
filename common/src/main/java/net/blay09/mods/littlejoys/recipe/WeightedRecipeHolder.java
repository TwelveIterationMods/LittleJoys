package net.blay09.mods.littlejoys.recipe;

import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;

public record WeightedRecipeHolder<T extends Recipe<?> & WeightedEntry>(RecipeHolder<T> recipeHolder) implements WeightedEntry {
    @Override
    public Weight getWeight() {
        return recipeHolder.value().getWeight();
    }
}
