package com.kitsuneindustries.deathwatch.data;

import java.util.Objects;
import java.util.UUID;

import javax.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties({ "icon" })
public record Victim(
    @Nonnull UUID uuid,
    @Nonnull String displayName) {
    public Victim {
        Objects.requireNonNull(uuid, "UUID must be provided");
        Objects.requireNonNull(displayName, "Display name must be provided");
    }
}
