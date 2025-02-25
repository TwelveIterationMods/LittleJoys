package net.blay09.mods.littlejoys.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
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
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record FishingSpotRecipe(ResourceLocation identifier, EventCondition eventCondition, ResourceLocation lootTable,
                                Weight weight) implements Recipe<Container>, WeightedEntry {

    private static final Logger LOGGER = LoggerFactory.getLogger(FishingSpotRecipe.class);

    @Override
    public RecipeType<FishingSpotRecipe> getType() {
        return ModRecipeTypes.fishingSpotRecipeType;
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
    public RecipeSerializer<FishingSpotRecipe> getSerializer() {
        return ModRecipeTypes.fishingSpotRecipeSerializer;
    }

    @Override
    public Weight getWeight() {
        return weight;
    }

    public static class Serializer implements RecipeSerializer<FishingSpotRecipe> {
        @Override
        public FishingSpotRecipe fromJson(ResourceLocation identifier, JsonObject jsonObject) {
            final var eventCondition = EventConditionRegistry.CODEC.decode(JsonOps.INSTANCE, GsonHelper.getNonNull(jsonObject, "eventCondition"))
                    .getOrThrow(false, LOGGER::error)
                    .getFirst();
            final var lootTable = new ResourceLocation(GsonHelper.getAsString(jsonObject, "lootTable", BuiltInLootTables.EMPTY.toString()));
            final var weight = Weight.of(GsonHelper.getAsInt(jsonObject, "weight", 1));
            return new FishingSpotRecipe(identifier, eventCondition, lootTable, weight);
        }

        @Override
        public FishingSpotRecipe fromNetwork(ResourceLocation identifier, FriendlyByteBuf buf) {
            final var eventCondition = EventConditionRegistry.conditionFromNetwork(buf);
            final var lootTable = buf.readResourceLocation();
            final var weight = Weight.of(buf.readVarInt());
            return new FishingSpotRecipe(identifier, eventCondition, lootTable, weight);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, FishingSpotRecipe recipe) {
            EventConditionRegistry.conditionToNetwork(buf, recipe.eventCondition);
            buf.writeResourceLocation(recipe.lootTable);
            buf.writeVarInt(recipe.weight.asInt());
        }
    }
}
