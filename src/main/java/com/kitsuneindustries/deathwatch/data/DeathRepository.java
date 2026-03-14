package com.kitsuneindustries.deathwatch.data;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;

import com.kitsuneindustries.deathwatch.NotImplementedException;
import com.mojang.logging.LogUtils;

public class DeathRepository {
    public static final Logger LOGGER = LogUtils.getLogger();

    public void add(PlayerDeath death) {
        LOGGER.info(death.toString());
    }

    List<PlayerDeath> getAll() {
        throw new NotImplementedException("getAll() has not been implemented. It probably will be eventually.");
    }

    List<PlayerDeath> getByPlayerUUID(UUID playerUUID) {
        throw new NotImplementedException(
            "getPlayerByUUID() has not been implemented. It probably will be eventually.");
    }
}
