package net.blay09.mods.littlejoys.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.blay09.mods.littlejoys.recipe.condition.AboveStateCondition;
import net.blay09.mods.littlejoys.api.EventCondition;
import net.blay09.mods.littlejoys.recipe.condition.EventConditionRegistry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.random.Weight;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record DigSpotRecipe(ResourceLocation identifier, EventCondition eventCondition, ResourceLocation lootTable,
                            Weight weight) implements Recipe<Container>, WeightedEntry {

    private static final Logger LOGGER = LoggerFactory.getLogger(DigSpotRecipe.class);

    @Override
    public RecipeType<DigSpotRecipe> getType() {
        return ModRecipeTypes.digSpotRecipeType;
    }

    @Override
    public boolean matches(Container container, Level level) {
        return false;
    }

    @Override
    public ItemStack assemble(Container container, RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return identifier;
    }

    @Override
    public RecipeSerializer<DigSpotRecipe> getSerializer() {
        return ModRecipeTypes.digSpotRecipeSerializer;
    }

    @Override
    public Weight getWeight() {
        return weight;
    }

    public static class Serializer implements RecipeSerializer<DigSpotRecipe> {
        @Override
        public DigSpotRecipe fromJson(ResourceLocation identifier, JsonObject jsonObject) {
            final var eventCondition = EventConditionRegistry.CODEC.decode(JsonOps.INSTANCE, GsonHelper.getNonNull(jsonObject, "eventCondition"))
                    .getOrThrow(false, LOGGER::error)
                    .getFirst();
            final var lootTable = new ResourceLocation(GsonHelper.getAsString(jsonObject, "lootTable"));
            final var weight = Weight.of(GsonHelper.getAsInt(jsonObject, "weight", 1));
            return new DigSpotRecipe(identifier, eventCondition, lootTable, weight);
        }

        @Override
        public DigSpotRecipe fromNetwork(ResourceLocation identifier, FriendlyByteBuf buf) {
            final var eventCondition = EventConditionRegistry.conditionFromNetwork(buf);
            final var lootTable = buf.readResourceLocation();
            final var weight = Weight.of(buf.readInt());
            return new DigSpotRecipe(identifier, eventCondition, lootTable, weight);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, DigSpotRecipe recipe) {
            EventConditionRegistry.conditionToNetwork(buf, recipe.eventCondition);
            buf.writeResourceLocation(recipe.lootTable);
            buf.writeInt(recipe.weight.asInt());
        }
    }
}
