package com.kitsuneindustries.deathwatch.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Calendar;
import java.util.Date;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.CombatTracker;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PlayerDeathTest {

    private PrimitiveIterator.OfDouble xyzRange;
    private Player victim;
    private DamageSource damageSource;

    private CombatTracker combatTracker;

    @BeforeEach
    public void setUpMocks() {
        // Create a basic victim
        victim = Mockito.mock(Player.class);
        when(victim.getUUID()).thenReturn(UUID.randomUUID());
        when(victim.getDisplayName()).thenReturn(Component.literal("PlayerName"));

        xyzRange = new Random().doubles(-40000, 40000).iterator();

        Vec3 position = new Vec3(xyzRange.nextDouble(), xyzRange.nextDouble(), xyzRange.nextDouble());
        when(victim.position()).thenReturn(position);

        Level level = Mockito.mock(Level.class);
        ResourceKey<Level> dim = Mockito.mock(ResourceKey.class);
        ResourceLocation location = Mockito.mock(ResourceLocation.class);
        when(level.dimension()).thenReturn(dim);
        when(dim.location()).thenReturn(location);
        when(location.toString()).thenReturn("minecraft:overworld");

        when(victim.level()).thenReturn(level);
        when(level.dimension()).thenReturn(dim);
        when(dim.location()).thenReturn(location);

        damageSource = Mockito.mock(DamageSource.class);
        when(damageSource.type()).thenReturn(new DamageType("death.damage.type", 1f));

        combatTracker = Mockito.mock(CombatTracker.class);
        when(victim.getCombatTracker()).thenReturn(combatTracker);
        when(combatTracker.getDeathMessage()).thenReturn(Component.literal("I am a death message"));
    }

    @Test
    void testEquality() {
        UUID uuid = UUID.randomUUID();
        Date timestamp = Calendar.getInstance().getTime();
        Vec3 position = new Vec3(xyzRange.nextDouble(), xyzRange.nextDouble(), xyzRange.nextDouble());
        Victim victim = new Victim(UUID.randomUUID(), "PlayerName");

        PlayerDeath expected = new PlayerDeath(uuid,
            timestamp,
            victim,
            "minecraft:overworld",
            position,
            "death.damage.type",
            null,
            null);
        PlayerDeath actual = new PlayerDeath(uuid,
            timestamp,
            victim,
            "minecraft:overworld",
            position,
            "death.damage.type",
            null,
            null);

        assertEquals(expected, actual);
    }

}
