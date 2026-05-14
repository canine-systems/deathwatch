package com.kitsuneindustries.deathwatch.data;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.PrimitiveIterator;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.minecraft.world.phys.Vec3;
import tools.jackson.databind.ObjectMapper;

public class PlayerDeathTest {

    private PrimitiveIterator.OfDouble xyzRange;
    private UUID uuid;
    private long timestamp;
    private Vec3 position;
    private Victim victim;

    @BeforeEach
    public void setupVars() {
        xyzRange = new Random().doubles(-40000, 40000).iterator();
        uuid = UUID.randomUUID();
        timestamp = Instant.now().toEpochMilli();
        position = new Vec3(xyzRange.nextDouble(), xyzRange.nextDouble(), xyzRange.nextDouble());
        victim = new Victim(UUID.randomUUID(), "PlayerName");
    }

    @Test
    void testEquality() {
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

    @Test
    void testSerializeDeserialize() {
        ObjectMapper om = new ObjectMapper();

        PlayerDeath expected = new PlayerDeath(uuid,
            timestamp,
            victim,
            "minecraft:overworld",
            position,
            "death.attack.mom",
            "Your Mom",
            victim.displayName() + " was slaughtered by Your Mom");

        String jsonString = om.writeValueAsString(expected);

        PlayerDeath actual = om.readValue(jsonString, PlayerDeath.class);

        assertEquals(expected, actual, "Deserialized version doesn't match original");
    }
}
