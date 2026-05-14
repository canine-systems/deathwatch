package com.kitsuneindustries.deathwatch.data.serialize;

import net.minecraft.world.phys.Vec3;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.deser.std.StdDeserializer;

public class Vec3Deserializer extends StdDeserializer<Vec3> {

    public Vec3Deserializer() {
        super(Vec3.class);
    }

    @Override
    public Vec3 deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {
        JsonNode node = p.readValueAsTree();
        double x = node.get("x").asDouble();
        double y = node.get("y").asDouble();
        double z = node.get("z").asDouble();
        return new Vec3(x, y, z);
    }

}
