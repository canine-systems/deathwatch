package com.kitsuneindustries.deathwatch.data;

import static java.util.Objects.requireNonNull;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.annotation.Nonnull;

import org.spongepowered.include.com.google.common.base.Objects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public class PlayerDeath {

    private UUID id; // JSON representation can probably use uuid.toString(),
                     // UUID.fromString(uuid_str)
    private Date timestamp;

    private Victim victim; // needed for victim.getDisplayName(), victim.getUUID();

    private String dimension;

    private Vec3 position; // needs JSON representation

    private String type;
    private String killer;
    private String message;

    public Date getTimestamp() {
        return timestamp;
    }

    public Victim getVictim() {
        return victim;
    }

    public String getDimension() {
        return dimension;
    }

    public Vec3 getPosition() {
        return position;
    }

    public String getType() {
        return type;
    }

    public String getKiller() {
        return killer;
    }

    public String getMessage() {
        return message;
    }

    private PlayerDeath(Victim victim, String dimension, Vec3 position, String type, String killer,
        String message) {
        this.id = requireNonNull(UUID.randomUUID(), "UUID cannot be null");
        this.timestamp = requireNonNull(Calendar.getInstance().getTime(), "Must have a timestamp");
        this.victim = requireNonNull(victim);
        this.dimension = requireNonNull(dimension);
        this.position = requireNonNull(position);
        this.type = requireNonNull(type);
        this.killer = killer;
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (getClass() != obj.getClass())) {
            return false;
        }

        // Since we know they're the same class...
        PlayerDeath other = (PlayerDeath) obj;
        return this.id.equals(other.id)
            && this.timestamp.equals(other.timestamp)
            && this.victim.equals(other.victim)
            && this.dimension.equals(other.dimension)
            && this.position.equals(other.position)
            && Objects.equal(this.type, other.type)
            && Objects.equal(this.killer, other.killer)
            && Objects.equal(this.message, other.message);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        // builder.append(String.format("%s - %s(%s) ", timestamp,
        // victim.getDisplayName(), victim.getUUID()));
        builder.append(String.format("%s - %s(%s) ", timestamp, this.victim.getDisplayName(), this.victim.getUUID()));
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

        public Builder(net.minecraft.world.entity.player.Player playerEntity) {
            this.playerEntity = playerEntity;
        }

        public Builder source(DamageSource source) {
            this.source = source;
            return this;
        }

        public PlayerDeath build() {
            Entity killer = source != null ? source.getEntity() : null;

            return new PlayerDeath(
                new Victim(playerEntity),
                playerEntity.level().dimension().location().toString(), // Dimension name
                playerEntity.position(),
                source != null ? source.type().msgId() : null, // Damage type
                killer != null ? killer.getDisplayName().getString() : null, // killer
                playerEntity.getCombatTracker().getDeathMessage().getString() // Death message
            );
        }
    }

}
