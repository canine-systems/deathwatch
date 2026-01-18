package com.kitsuneindustries.deathwatch.data.persistence.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import net.minecraft.world.phys.Vec3;

@Converter
public class Vec3Converter implements AttributeConverter<Vec3, String> {

  @Override
  public String convertToDatabaseColumn(Vec3 vector) {
    return "Fuck.";
  }

  @Override
  public Vec3 convertToEntityAttribute(String dbData) {
    // ¯\_(ツ)_/¯
    return new Vec3(0, 0, 0);
  }

}
