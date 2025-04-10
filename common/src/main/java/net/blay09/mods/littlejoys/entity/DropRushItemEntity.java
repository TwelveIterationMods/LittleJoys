package net.blay09.mods.littlejoys.entity;

import net.blay09.mods.balm.api.Balm;
import net.minecraft.ChatFormatting;
import net.minecraft.core.UUIDUtil;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public class DropRushItemEntity extends ItemEntity {

    private static final EntityDataAccessor<CompoundTag> DATA_TARGET = SynchedEntityData.defineId(DropRushItemEntity.class, EntityDataSerializers.COMPOUND_TAG);
    private int ticksPassed;
    private int actualLifetime = 6000;
    private boolean pickedUp;

    public DropRushItemEntity(EntityType<? extends ItemEntity> entityType, Level level) {
        super(entityType, level);
    }

    public DropRushItemEntity(Level level, double posX, double posY, double posZ, ItemStack itemStack, double deltaX, double deltaY, double deltaZ) {
        this(ModEntities.dropRushItem.get(), level);
        this.setPos(posX, posY, posZ);
        this.setDeltaMovement(deltaX, deltaY, deltaZ);
        this.setItem(itemStack);
    }

    @SuppressWarnings("unchecked")
    public DropRushItemEntity(DropRushItemEntity other) {
        super((EntityType<? extends ItemEntity>) other.getType(), other.level());
        setItem(other.getItem().copy());
        copyPosition(other);
        ticksPassed = other.ticksPassed;
        actualLifetime = other.actualLifetime;
        pickedUp = other.pickedUp;
    }

    @Override
    public void tick() {
        super.tick();
        ticksPassed++;

        if (ticksPassed >= actualLifetime) {
            discard();
        }

        if (level().isClientSide && !onGround()) {
            level().addParticle(ParticleTypes.INSTANT_EFFECT, getX(), getY(), getZ(), 0f, 0f, 0f);
        }
    }

    @Override
    public void onClientRemoval() {
        super.onClientRemoval();
        if (pickedUp) {
            level().addAlwaysVisibleParticle(ParticleTypes.HAPPY_VILLAGER, getX(), getY() + 0.25f, getZ(), 0f, 0f, 0f);
        } else {
            level().addAlwaysVisibleParticle(ParticleTypes.POOF, getX(), getY() + 0.25f, getZ(), 0f, 0f, 0f);
            level().addAlwaysVisibleParticle(ParticleTypes.LARGE_SMOKE, getX(), getY() + 0.25f, getZ(), 0f, 0f, 0f);
        }
    }

    /**
     * We use this instead of lifetime because we abuse infinite lifetime to prevent the items from merging.
     */
    public void setActualLifetime(int actualLifetime) {
        this.actualLifetime = actualLifetime;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    @Override
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        super.onSyncedDataUpdated(key);
        if (key == DATA_TARGET) {
            getEntityData().get(DATA_TARGET).read("Target", UUIDUtil.CODEC).ifPresent(this::setTarget);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_TARGET, new CompoundTag());
    }

    @Override
    public boolean isCurrentlyGlowing() {
        final var player = Balm.getProxy().getClientPlayer();
        return player != null && Objects.equals(getTarget(), player.getUUID());
    }

    @Override
    public int getTeamColor() {
        return ChatFormatting.RED.getColor();
    }

    @Nullable
    public UUID getTarget() {
        return getEntityData().get(DATA_TARGET).read("Target", UUIDUtil.CODEC).orElse(null);
    }

    @Override
    public void setTarget(@Nullable UUID target) {
        super.setTarget(target);
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.storeNullable("Target", UUIDUtil.CODEC, target);
        getEntityData().set(DATA_TARGET, compoundTag);
    }

    @Override
    public ItemEntity copy() {
        return new DropRushItemEntity(this);
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (reason == RemovalReason.DISCARDED && getItem().isEmpty()) {
            setPickedUp(true);
        }
    }
}
