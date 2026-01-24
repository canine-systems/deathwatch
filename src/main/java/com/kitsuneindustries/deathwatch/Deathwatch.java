package com.kitsuneindustries.deathwatch;

import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.server.Server;
import org.slf4j.Logger;

import com.kitsuneindustries.deathwatch.command.DeathwatchCommand;
import com.kitsuneindustries.deathwatch.data.DeathRepository;
import com.kitsuneindustries.deathwatch.data.PersistenceHelper;
import com.kitsuneindustries.deathwatch.data.PlayerDeath;
import com.kitsuneindustries.deathwatch.web.DeathwatchServlet;
import com.mojang.logging.LogUtils;

import jakarta.inject.Inject;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(value = Deathwatch.MODID)
public class Deathwatch {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "deathwatch";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    @Inject
    DeathRepository graveyard;

    // The constructor for the mod class is the first code that is run when your mod
    // is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and
    // pass them in automatically.
    public Deathwatch(IEventBus modEventBus, ModContainer modContainer) {

        // Register our mod's ModConfigSpec so that FML can create and load the config
        // file for us
        modContainer.registerConfig(ModConfig.Type.COMMON,
            Config.SPEC);

        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        // Register ourselves for server and other game events we are interested in.
        NeoForge.EVENT_BUS.register(this);

        // Register our commands
        DeathwatchCommand.init(modEventBus);

        /*
         * // Register the Deferred Register to the mod event bus so blocks get
         * registered BLOCKS.register(modEventBus); // Register the Deferred Register to
         * the mod event bus so items get registered ITEMS.register(modEventBus); //
         * Register the Deferred Register to the mod event bus so tabs get registered
         * CREATIVE_MODE_TABS.register(modEventBus);
         *
         * // Register the item to a creative tab
         * modEventBus.addListener(this::addCreative);
         */
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        // Some common setup code
        LOGGER.info("HELLO FROM COMMON SETUP");

        /*
         * if (Config.LOG_DIRT_BLOCK.getAsBoolean()) { LOGGER.info("DIRT BLOCK >> {}",
         * BuiltInRegistries.BLOCK.getKey(Blocks.DIRT)); }
         *
         * LOGGER.info("{}{}", Config.MAGIC_NUMBER_INTRODUCTION.get(),
         * Config.MAGIC_NUMBER.getAsInt());
         *
         * Config.ITEM_STRINGS.get().forEach((item) -> LOGGER.info("ITEM >> {}", item));
         */
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("Hi from Deathwatch!");

        Server webserver = new Server(8080);
        ServletContextHandler context = new ServletContextHandler();
        context.setContextPath("/");
        context.addServlet(DeathwatchServlet.class, "/*");
        webserver.setHandler(context);

        try {
            webserver.start();
        } catch (Exception e) {
            LOGGER.error("Error starting embedded web server", e);
        }
        PersistenceHelper.setup();
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) {
            // Don't do anything client side.
            // LOGGER.debug("Running client side, not logging.");
            return;
        }
        if (!entity.getType().equals(EntityType.PLAYER)) {
            // Only log players.
            // LOGGER.debug("Non-player entity died.");
            return;
        }

        // We're running server side and the entity was a player! Let's record it.
        Player player = (Player) entity;
        DamageSource source = event.getSource();
        PlayerDeath death = PlayerDeath.newBuilder(player).source(source).build();

        try {
            graveyard.add(death);
            LOGGER.info("Logging Death: {}", death);
        } catch (Exception e) {
            LOGGER.error("Failed to log player death", e);
        }

        entity.getServer().getPlayerList().broadcastSystemMessage(
            MutableComponent.create(PlainTextContents.create(death.toString())), false);
    }
}
