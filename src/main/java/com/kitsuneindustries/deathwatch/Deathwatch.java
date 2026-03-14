package com.kitsuneindustries.deathwatch;

import org.slf4j.Logger;

import com.kitsuneindustries.deathwatch.data.DeathRepository;
import com.kitsuneindustries.deathwatch.data.PlayerDeath;
import com.mojang.logging.LogUtils;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(value = Deathwatch.MODID)
public class Deathwatch {

    // Define mod id in a common place for everything to reference
    public static final String MODID = "deathwatch";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    DeathRepository graveyard = new DeathRepository();

    // The constructor for the mod class is the first code that is run when your mod
    // is loaded.
    // FML will recognize some parameter types like IEventBus or ModContainer and
    // pass them in automatically.
    public Deathwatch(IEventBus modEventBus, ModContainer modContainer) {
        // Register our mod's ModConfigSpec so that FML can create and load the config
        // file for us
        modContainer.registerConfig(ModConfig.Type.COMMON,
            Config.SPEC);

        // Register ourselves for server and other game events we are interested in.
        NeoForge.EVENT_BUS.register(this);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        LOGGER.info("Hi from Deathwatch!");
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
    }

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide()) {
            // Don't do anything client side.
            return;
        }
        if (!entity.getType().equals(EntityType.PLAYER)) {
            // Only log players.
            return;
        }

        // We're running server side and the entity was a player! Let's record it.
        Player player = (Player) entity;
        DamageSource source = event.getSource();
        PlayerDeath death = PlayerDeath.newBuilder(player).source(source).build();

        try {
            graveyard.add(death);
            // LOGGER.info("Logging Death: {}", death);
        } catch (Exception e) {
            LOGGER.error("Failed to log player death", e);
        }

        /*
         * entity.getServer().getPlayerList().broadcastSystemMessage(
         * MutableComponent.create(PlainTextContents.create(death.toString())), false);
         */
    }
}