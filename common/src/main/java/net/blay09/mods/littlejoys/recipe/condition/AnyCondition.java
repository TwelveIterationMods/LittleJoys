package net.blay09.mods.littlejoys.recipe.condition;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.blay09.mods.littlejoys.api.EventCondition;
import net.blay09.mods.littlejoys.api.EventContext;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;

public record AnyCondition(List<EventCondition> conditions) implements EventCondition {

    public static final MapCodec<AnyCondition> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            EventConditionRegistry.LIST_CODEC.fieldOf("conditions").forGetter(AnyCondition::conditions)
    ).apply(instance, AnyCondition::new));

    @Override
    public boolean test(EventContext context) {
        for (EventCondition condition : conditions) {
            if (condition.test(context)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf) {
        buf.writeCollection(conditions, EventConditionRegistry::conditionToNetwork);
    }

    public static AnyCondition fromNetwork(FriendlyByteBuf buf) {
        final var conditions = buf.readCollection(ArrayList::new, EventConditionRegistry::conditionFromNetwork);
        return new AnyCondition(conditions);
    }
}
