# deathwatch

Deathwatch is a Minecraft mod which tracks player deaths on a server.

Currently, it logs to a collection of files. Eventually, it might support a database.

Files are named `deathwatch/YYYY/MM/DD.jsonl`. Each line is a JSON object containing one death.

This format is done to avoid the long-term stability problems encountered with the [original deathwatch from a decade ago](https://github.com/kayila/deathwatch) which logged to a single CSV file.

Officially supports:
- Minecraft v1.21.1
- Neoforge 21.1.216

## File format

This is an example file, which was saved as `deathwatch/2026/05/14.jsonl`:

```
{"uuid":"02497743-5bd3-4086-a054-53beaa2b11b6","timestamp":1778781191870,"victim":{"uuid":"031d654f-749b-3baf-825e-31e0971759d3","displayName":"duckinator"},"dimension":"minecraft:overworld","position":{"x":-242.2411240405922,"y":117.87208890136314,"z":-532.9048343498525},"type":"create.mechanical_saw","killer":null,"message":"duckinator got cut in half by a Mechanical Saw"}
{"uuid":"c212613c-52c0-48b4-b179-e21b48b58f39","timestamp":1778781229164,"victim":{"uuid":"031d654f-749b-3baf-825e-31e0971759d3","displayName":"duckinator"},"dimension":"minecraft:overworld","position":{"x":-237.88298975056122,"y":117.0,"z":-528.5443939636036},"type":"mob","killer":"Enderman","message":"duckinator was slain by Enderman"}
```

Each line is a JSON object, with the following keys:

- `uuid` (string): the UUID of the death.
- `timestamp` (number): the date and time the death happened.
- `victim` (object): an object representing the player that was killed.
- `dimension` (string): the dimension the death occurred in (e.g. "minecraft:overworld").
- `position` (object): an object representing the location the player died.
- `type` (string): the type of death
- `killer` (string or null): the entity that caused the death (e.g. "Skeleton" if a skeleton killed the player). may be `null`.
- `message` (string): the death message provided by Minecraft

The `victim` objects have the following keys:

- `uuid` (string): the Minecraft UUID.
- `displayName` (string): the Minecraft display name.

The `position` objects have the following keys:

- `x` (number): the X coordinate.
- `y` (number): the Y coordinate.
- `z` (number): the Z coordinate.
