package com.kitsuneindustries.deathwatch.data;

import java.util.UUID;

import jakarta.persistence.Basic;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@jakarta.persistence.Entity
public class Player {

    @Id
    UUID uuid;

    @Basic(optional = false)
    String displayName;

    public UUID getUUID() {
        return uuid;
    }

    @NotNull
    public String getDisplayName() {
        return displayName;
    }

    /*
     * public net.minecraft.world.entity.player.Player toMinecraftPlayer() {
     * net.minecraft.world.entity.player.Player }
     */

    Player() {
    }

    Player(@NotNull net.minecraft.world.entity.player.Player playerEntity) {
        this.uuid = playerEntity.getUUID();
        this.displayName = playerEntity.getDisplayName().getString();
    }

    Player(@NotNull UUID uuid, @NotNull String displayName) {
        this.uuid = uuid;
        this.displayName = displayName;
    }
}
