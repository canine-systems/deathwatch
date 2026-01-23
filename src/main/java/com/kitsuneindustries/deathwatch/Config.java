package com.kitsuneindustries.deathwatch;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.ConfigValue<String> JDBC_URL = BUILDER
        .comment("JDBC compatable URL for the database to store deaths in",
            "You can specify the world save folder by using '{{SAVE}}'")
        .worldRestart()
        .define("jdbcUrl", "jdbc:h2:mem:test");
    // .define("jdbcUrl", "jdbc:h2:{{SAVE}}/deathwatch.db");

    public static final ModConfigSpec.ConfigValue<String> JDBC_USER = BUILDER
        .comment("Database username")
        .worldRestart()
        .define("jdbcUser", "");

    public static final ModConfigSpec.ConfigValue<String> JDBC_PASS = BUILDER
        .comment("Database password")
        .worldRestart()
        .define("jdbcPassword", "");

    public static final ModConfigSpec.BooleanValue SHOW_SQL = BUILDER
        .comment("Show SQL in log file")
        .worldRestart()
        .define("showSql", true);

    public static final ModConfigSpec.BooleanValue FORMAT_SQL = BUILDER
        .comment("Format SQL in log file")
        .worldRestart()
        .define("formatSql", true);

    public static final ModConfigSpec.BooleanValue HIGHLIGHT_SQL = BUILDER
        .comment("Highlight SQL in log file")
        .worldRestart()
        .define("highlightSql", true);

    public static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
        .comment("Whether to log the dirt block on common setup")
        .define("logDirtBlock", true);

    public static final ModConfigSpec.IntValue MAGIC_NUMBER = BUILDER
        .comment("A magic number")
        .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
        .comment("What you want the introduction message to be for the magic number")
        .define("magicNumberIntroduction", "The magic number is... ");

    // a list of strings that are treated as resource locations for items
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
        .comment("A list of items to log on common setup.")
        .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    public static String JDBC_URL_FORMAT() {
        // Minecraft.getInstance().gameDirectory;

        return JDBC_URL.get().replace("{{SAVE}}", "/tmp");
    }

    /*
     * I am grumpy about not being able to do this...
     */
    /*
     * public static class JdbcURLValue extends ConfigValue<String> implements
     * Supplier<String> {
     *
     * JdbcURLValue(Builder parent, List<String> path, Supplier<String>
     * defaultSupplier) {
     * super(parent, path, defaultSupplier);
     * }
     *
     * @Override
     * public String getRaw(com.electronwill.nightconfig.core.Config config,
     * List<String> path,
     * Supplier<String> defaultSupplier) {
     * return config.getOrElse(path, () -> defaultSupplier.get());
     * }
     * }
     */
}
