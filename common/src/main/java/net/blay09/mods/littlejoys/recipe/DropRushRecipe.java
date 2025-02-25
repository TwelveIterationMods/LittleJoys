package net.blay09.mods.littlejoys.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;
import net.blay09.mods.littlejoys.LittleJoysConfig;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public record DropRushRecipe(ResourceLocation identifier, EventCondition eventCondition, float chanceMultiplier, ResourceLocation lootTable, int rolls, float seconds, int range, Weight weight) implements Recipe<Container>, WeightedEntry {

    private static final Logger LOGGER = LoggerFactory.getLogger(DropRushRecipe.class);

    @Override
    public RecipeType<DropRushRecipe> getType() {
        return ModRecipeTypes.dropRushRecipeType;
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
    public RecipeSerializer<DropRushRecipe> getSerializer() {
        return ModRecipeTypes.dropRushRecipeSerializer;
    }

    @Override
    public Weight getWeight() {
        return weight;
    }

    public static class Serializer implements RecipeSerializer<DropRushRecipe> {
        @Override
        public DropRushRecipe fromJson(ResourceLocation identifier, JsonObject jsonObject) {
            final var eventCondition = EventConditionRegistry.CODEC.decode(JsonOps.INSTANCE, GsonHelper.getNonNull(jsonObject, "eventCondition"))
                    .getOrThrow(false, LOGGER::error)
                    .getFirst();
            final var lootTable = new ResourceLocation(GsonHelper.getAsString(jsonObject, "lootTable"));
            final var chanceMultiplier = GsonHelper.getAsFloat(jsonObject, "chanceMultiplier", 1f);
            final var rolls = GsonHelper.getAsInt(jsonObject, "rolls", 8);
            final var seconds = GsonHelper.getAsFloat(jsonObject, "seconds", 12.5f);
            final var range = GsonHelper.getAsInt(jsonObject, "range", 8);
            final var weight = Weight.of(GsonHelper.getAsInt(jsonObject, "weight", 1));
            return new DropRushRecipe(identifier, eventCondition, chanceMultiplier, lootTable, rolls, seconds, range, weight);
        }

        @Override
        public DropRushRecipe fromNetwork(ResourceLocation identifier, FriendlyByteBuf buf) {
            final var eventCondition = EventConditionRegistry.conditionFromNetwork(buf);
            final var chance = buf.readFloat();
            final var lootTable = buf.readResourceLocation();
            final var rolls = buf.readVarInt();
            final var seconds = buf.readFloat();
            final var range = buf.readVarInt();
            final var weight = Weight.of(buf.readVarInt());
            return new DropRushRecipe(identifier, eventCondition, chance, lootTable, rolls, seconds, range, weight);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, DropRushRecipe recipe) {
            EventConditionRegistry.conditionToNetwork(buf, recipe.eventCondition);
            buf.writeFloat(recipe.chanceMultiplier);
            buf.writeResourceLocation(recipe.lootTable);
            buf.writeVarInt(recipe.rolls);
            buf.writeFloat(recipe.seconds);
            buf.writeVarInt(recipe.range);
            buf.writeVarInt(recipe.weight.asInt());
        }
    }
}
