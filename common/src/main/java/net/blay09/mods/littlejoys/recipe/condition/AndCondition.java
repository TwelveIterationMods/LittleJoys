package net.blay09.mods.littlejoys.recipe.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.blay09.mods.littlejoys.api.EventCondition;
import net.blay09.mods.littlejoys.api.EventContext;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

public record AndCondition(List<EventCondition> conditions) implements EventCondition {

    public static final MapCodec<AndCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EventConditionRegistry.LIST_CODEC.fieldOf("conditions").forGetter(AndCondition::conditions)
    ).apply(instance, AndCondition::new));

    @Override
    public boolean test(EventContext context) {
        for (EventCondition condition : conditions) {
            if (!condition.test(context)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeCollection(conditions, EventConditionRegistry::conditionToNetwork);
    }

    public static AndCondition fromNetwork(FriendlyByteBuf buf) {
        final var conditions = buf.readCollection(ArrayList::new, EventConditionRegistry::conditionFromNetwork);
        return new AndCondition(conditions);
    }
}
