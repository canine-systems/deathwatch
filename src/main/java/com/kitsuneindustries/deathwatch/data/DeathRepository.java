package com.kitsuneindustries.deathwatch.data;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import jakarta.data.repository.Find;
import jakarta.data.repository.Insert;
import jakarta.data.repository.Query;
import jakarta.data.repository.Repository;

@Repository
public interface DeathRepository {
    public static final Logger LOGGER = LogUtils.getLogger();

    @Insert
    void add(PlayerDeath death);

    @Find
    List<PlayerDeath> getAll();

    @Query("from PlayerDeath d join Player p where p.uuid = :playerUUID")
    List<PlayerDeath> getByPlayerUUID(UUID playerUUID);
}
