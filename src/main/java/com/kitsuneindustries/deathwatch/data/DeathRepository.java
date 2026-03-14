package com.kitsuneindustries.deathwatch.data;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;

import com.kitsuneindustries.deathwatch.Config;
import com.kitsuneindustries.deathwatch.NotImplementedException;
import com.mojang.logging.LogUtils;

import tools.jackson.databind.ObjectMapper;

public class DeathRepository {
    public static final Logger LOGGER = LogUtils.getLogger();

    public void add(PlayerDeath death) {
        this.appendToJsonLog(death);
        LOGGER.info(death.toString());
    }

    private void appendToJsonLog(PlayerDeath death) {
        String json_str = new ObjectMapper().writeValueAsString(death);

        // What an absolute fucking atrocity, just to append a string followed by a
        // newline to a file?!
        // JAVA, WHY ARE YOU LIKE THIS?!
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(this.getFilePath(), true)))) {
            out.println(json_str);
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Failed to append PlayerDeath to log: {}", e.getMessage());
            return;
        }
    }

    private String getFilePath() {
        Date date = Calendar.getInstance().getTime();

        // <4-digit year>/<2-digit month>/<2-digit day>.jsonl
        String path_end = new SimpleDateFormat("yyyy/MM/dd").format(date) + ".jsonl";

        Path file = Path.of(".", Config.JSON_FOLDER.get(), path_end);

        Path dir = file.getParent();
        // Ensure ./<JSON FOLDER>/yyyy/MM exists.
        dir.toFile().mkdirs();

        return file.toString();
    }

    List<PlayerDeath> getAll() {
        throw new NotImplementedException("getAll() has not been implemented. It probably will be eventually.");
    }

    List<PlayerDeath> getByPlayerUUID(UUID playerUUID) {
        throw new NotImplementedException(
            "getPlayerByUUID() has not been implemented. It probably will be eventually.");
    }
}
