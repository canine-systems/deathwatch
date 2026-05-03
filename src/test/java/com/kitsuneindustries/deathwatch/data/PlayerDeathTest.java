package com.kitsuneindustries.deathwatch.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public class PlayerDeathTest {

    private Player victim;
    private DamageSource damageSource;

    @BeforeEach
    public void setUpMocks() {
        victim = Mockito.mock(Player.class);
        Mockito.when(victim.getUUID()).thenReturn(UUID.randomUUID());
        Mockito.when(victim.getDisplayName()).thenReturn(Component.literal("PlayerName"));

        PrimitiveIterator.OfDouble xyzRange = new Random().doubles(-40000, 40000).iterator();

        Vec3 position = new Vec3(xyzRange.nextDouble(), xyzRange.nextDouble(), xyzRange.nextDouble());
        Mockito.when(victim.position()).thenReturn(position);

        Level level = Mockito.mock(Level.class);
        ResourceKey<Level> dim = Mockito.mock(ResourceKey.class);
        ResourceLocation location = Mockito.mock(ResourceLocation.class);

        Mockito.when(victim.level()).thenReturn(level);
        Mockito.when(level.dimension()).thenReturn(dim);
        Mockito.when(dim.location()).thenReturn(location);

        damageSource = Mockito.mock(DamageSource.class);
    }

    @Test
    void testEquality() {
        PlayerDeath death = PlayerDeath.newBuilder(victim).build();
        PlayerDeath death2 = PlayerDeath.newBuilder(victim).build();
        assertEquals(death, death2);
    }

}
