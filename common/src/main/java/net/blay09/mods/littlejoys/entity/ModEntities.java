package net.blay09.mods.littlejoys.entity;

import net.blay09.mods.balm.api.DeferredObject;
import net.blay09.mods.balm.api.entity.BalmEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.Level;

import static net.blay09.mods.littlejoys.LittleJoys.id;

public class ModEntities {
    public static DeferredObject<EntityType<DropRushItemEntity>> dropRushItem;

    public static void initialize(BalmEntities entities) {
        dropRushItem = entities.registerEntity(
                id("drop_rush_item"),
                EntityType.Builder.of((EntityType<DropRushItemEntity> type, Level level) -> new DropRushItemEntity(type, level), MobCategory.MISC)
                        .sized(0.25f, 0.25f)
                        .clientTrackingRange(6)
                        .updateInterval(20)
                        .noSave());
    }

}
