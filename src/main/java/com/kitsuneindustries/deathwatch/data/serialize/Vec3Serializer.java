package com.kitsuneindustries.deathwatch.data.serialize;

import net.minecraft.world.phys.Vec3;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ser.std.StdSerializer;

public class Vec3Serializer extends StdSerializer<Vec3> {

    public Vec3Serializer() {
        super(Vec3.class);
    }

    @Override
    public void serialize(Vec3 value, JsonGenerator gen, SerializationContext ctxt) throws JacksonException {
        gen.writeStartObject();
        gen.writeNumberProperty("x", value.x());
        gen.writeNumberProperty("y", value.y());
        gen.writeNumberProperty("z", value.z());
        gen.writeEndObject();
    }

}
