package com.kitsuneindustries.deathwatch.data.serialize;

import java.io.StringWriter;
import java.util.PrimitiveIterator;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import net.minecraft.world.phys.Vec3;
import tools.jackson.core.JsonGenerator;
import tools.jackson.core.json.JsonFactory;
import tools.jackson.databind.annotation.JsonDeserialize;
import tools.jackson.databind.annotation.JsonSerialize;

public class Vec3SerializationTest {
    private PrimitiveIterator.OfDouble xyzRange;

    @Nonnull
    @JsonSerialize(using = Vec3Serializer.class)
    @JsonDeserialize(using = Vec3Deserializer.class)
    Vec3 position;

    @BeforeEach
    public void setupVars() {
        position = new Vec3(xyzRange.nextDouble(), xyzRange.nextDouble(), xyzRange.nextDouble());
    }

    @Test
    void testSerialization() {
        Writer jsonWriter = new StringWriter();
        JsonGenerator jsonGenerator = new JsonFactory().createGenerator(jsonWriter);
    }
}