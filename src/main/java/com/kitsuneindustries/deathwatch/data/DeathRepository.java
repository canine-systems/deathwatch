package com.kitsuneindustries.deathwatch.data;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import jakarta.data.repository.Repository;

@Repository
public interface DeathRepository {
    public static final Logger LOGGER = LogUtils.getLogger();

    void add(PlayerDeath death);
}
