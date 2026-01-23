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
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

@jakarta.persistence.Entity // Specified out here to avoid confusion with Minecraft's Entity definition
public class PlayerDeath {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Date timestamp;

    @ManyToOne
    private Player victim;

    private String dimension;

    @Convert(converter = Vec3Converter.class)
    private Vec3 position;

    private String type;
    private String killer;
    private String message;

    @NotNull
    public UUID getId() {
        return id;
    }

    @NotNull
    public Date getTimestamp() {
        return timestamp;
    }

    @NotNull
    public Player getVictim() {
        return victim;
    }

    @NotNull
    public String getDimension() {
        return dimension;
    }

    @Nullable
    public String getType() {
        return type;
    }

    @Nullable
    public String getKiller() {
        return killer;
    }

    @Nullable
    public String getMessage() {
        return message;
    }

    public PlayerDeath() {
    }

    private PlayerDeath(Player victim, String dimension, Vec3 position, String type, String killer,
        String message) {
        this.id = UUID.randomUUID();
        this.timestamp = Calendar.getInstance().getTime();
        this.victim = victim;
        this.dimension = dimension;
        this.position = position;
        this.type = type;
        this.killer = killer;
        this.message = message;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append(String.format("%s - %s(%s) ", timestamp, victim.getDisplayName(), victim.getUUID()));
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

    public static Builder newBuilder(@Nonnull net.minecraft.world.entity.player.Player player) {
        return new Builder(player);
    }

    public static class Builder {
        private net.minecraft.world.entity.player.Player playerEntity;
        private DamageSource source;

        public Builder(@NotNull net.minecraft.world.entity.player.Player playerEntity) {
            this.playerEntity = playerEntity;
        }

        public Builder source(DamageSource source) {
            this.source = source;
            return this;
        }

        public PlayerDeath build() {
            Entity killer = source != null ? source.getEntity() : null;

            return new PlayerDeath(
                new Player(playerEntity),
                playerEntity.level().dimension().location().toString(), // Dimension name
                playerEntity.position(),
                source != null ? source.type().msgId() : null, // Damage type
                killer != null ? killer.getDisplayName().getString() : null, // killer
                playerEntity.getCombatTracker().getDeathMessage().getString() // Death message
            );
        }
    }

}
