package com.kitsuneindustries.deathwatch.data;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.kitsuneindustries.deathwatch.data.serialize.Vec3Deserializer;
import com.kitsuneindustries.deathwatch.data.serialize.Vec3Serializer;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

@JsonSerialize
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record PlayerDeath(
    @Nonnull UUID uuid,
    long timestamp,
    @Nonnull Victim victim,
    @Nonnull String dimension,
    @Nonnull @JsonSerialize(using = Vec3Serializer.class) @JsonDeserialize(using = Vec3Deserializer.class) Vec3 position,
    @Nonnull String type,
    @Nullable String killer,
    @Nullable String message) {
    public PlayerDeath {
        Objects.requireNonNull(uuid, "UUID must be provided");
        Objects.requireNonNull(victim, "Victim must be provided");
        Objects.requireNonNull(dimension, "Dimension must be provided");
        // Unfortunately, for a while the json logs dropped position, so this will
        // prevent this class from reading older logs
        Objects.requireNonNull(position, "Position must be provided");
        Objects.requireNonNull(type, "Death type must be provided");

    }

    public static Builder newBuilder(@Nonnull net.minecraft.world.entity.player.Player player) {
        return new Builder(player);
    }

    public static class Builder {
        private net.minecraft.world.entity.player.Player playerEntity;
        private DamageSource source;

        public Builder(net.minecraft.world.entity.player.Player playerEntity) {
            this.playerEntity = playerEntity;
        }

        public Builder source(DamageSource source) {
            this.source = source;
            return this;
        }

        public PlayerDeath build() {
            Entity killer = source != null ? source.getEntity() : null;
            Victim victim = new Victim(
                playerEntity.getUUID(),
                playerEntity.getDisplayName().getString());

            return new PlayerDeath(
                UUID.randomUUID(),
                Instant.now().toEpochMilli(),
                victim,
                playerEntity.level().dimension().location().toString(), // Dimension name
                playerEntity.position(), // Player Position
                source != null ? source.type().msgId() : null, // Damage type
                killer != null ? killer.getDisplayName().getString() : null, // killer
                playerEntity.getCombatTracker().getDeathMessage().getString() // Death message
            );
        }
    }
}
