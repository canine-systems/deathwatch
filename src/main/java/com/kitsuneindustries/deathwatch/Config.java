package com.kitsuneindustries.deathwatch;

import net.neoforged.neoforge.common.ModConfigSpec;

// Config class using NeoForge's config APIs.
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<String> JSON_FOLDER = BUILDER
        .comment("Folder to save JSON files in.")
        .worldRestart()
        .define("jsonFolder", "deathwatch");

    static final ModConfigSpec SPEC = BUILDER.build();
}
