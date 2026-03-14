package com.kitsuneindustries.deathwatch.data;

import java.util.UUID;

public class Victim {
    UUID uuid;

    String displayName;

    public UUID getUUID() {
        return uuid;
    }

    public String getDisplayName() {
        return displayName;
    }

    Victim(net.minecraft.world.entity.player.Player playerEntity) {
        this.uuid = playerEntity.getUUID();
        this.displayName = playerEntity.getDisplayName().getString();
    }

    Victim(UUID uuid, String displayName) {
        this.uuid = uuid;
        this.displayName = displayName;
    }
}
