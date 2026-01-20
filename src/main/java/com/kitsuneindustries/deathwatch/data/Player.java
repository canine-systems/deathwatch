package com.kitsuneindustries.deathwatch.data;

import java.util.UUID;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

@jakarta.persistence.Entity
public class Player {

    @Id
    UUID uuid;

    @NotNull
    String displayName;

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
