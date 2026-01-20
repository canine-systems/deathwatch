package com.kitsuneindustries.deathwatch.data;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.kitsuneindustries.deathwatch.data.persistence.converter.Vec3Converter;

import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

@jakarta.persistence.Entity // Specified out here to avoid confusion with Minecraft's Entity definition
public class PlayerDeath {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Nonnull
    private UUID id;

    @Nonnull
    private Date timestamp;

    @Nonnull
    private UUID playerUUID;

    @Nonnull
    private String playerName;

    @Nonnull
    private String dimension;

    @Convert(converter = Vec3Converter.class)
    @Nonnull
    private Vec3 position;

    @Nullable
    private String type;

    @Nullable
    private String killer;

    @Nullable
    private String message;

    protected PlayerDeath() {
    }

    private PlayerDeath(UUID playerUUID, String playerName, String dimension, Vec3 position, String type, String killer,
        String message) {
        this.id = UUID.randomUUID();
        this.timestamp = Calendar.getInstance().getTime();
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.dimension = dimension;
        this.position = position;
        this.type = type;
        this.killer = killer;
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("%s - %s(%s) ", timestamp, playerName, playerUUID));
        builder.append(String.format("died at [%f, %f, %f] ", position.x(), position.y(), position.z()));

        if (dimension != null) {
            builder.append(String.format("in %s ", dimension));
        }

        if (type != null) {
            builder.append(String.format("Damage type: %s ", type));
        }

        if (killer != null) {
            builder.append(String.format("Killer: %s ", killer));
        }

        if (message != null) {
            builder.append(String.format("Message: %s ", message));
        }

        return builder.toString();
    }

    public static Builder newBuilder(@Nonnull Player player) {
        return new Builder(player);
    }

    public static class Builder {
        private Player player;
        private DamageSource source;

        public Builder(@Nonnull Player player) {
            this.player = player;
        }

        public Builder source(DamageSource source) {
            this.source = source;
            return this;
        }

        public PlayerDeath build() {
            Entity killer = source != null ? source.getEntity() : null;

            return new PlayerDeath(player.getUUID(), player.getDisplayName().getString(),
                player.level().dimension().location().toString(), // Dimension name
                player.position(),
                source != null ? source.type().msgId() : null, // Damage type
                killer != null ? killer.getDisplayName().getString() : null, // killer
                player.getCombatTracker().getDeathMessage().getString() // Death message
            );
        }
    }

}
