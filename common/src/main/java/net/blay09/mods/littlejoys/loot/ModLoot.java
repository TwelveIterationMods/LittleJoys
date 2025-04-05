package net.blay09.mods.littlejoys.loot;

import net.blay09.mods.balm.api.loot.BalmLootTables;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public class ModLoot {

    public static void initialize(BalmLootTables lootTables) {
        lootTables.registerLootModifier(id("dig_spot"), new DigSpotLootModifier());
        lootTables.registerLootModifier(id("fishing_spot"), new FishingSpotLootModifier());
    }

}
