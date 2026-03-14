# deathwatch

Deathwatch is a Minecraft mod which tracks player deaths on a server.

Currently, it logs to a collection of files. Eventually, it might support a database.

Files are named `deathwatch/YYYY/MM/DD.jsonl`. Each line is a JSON object containing one death.

This format is done to avoid the long-term stability problems encountered with the [original deathwatch from a decade ago](https://github.com/kayila/deathwatch) which logged to a single CSV file.

Officially supports:
- Minecraft v1.21.1
- Neoforge 21.1.216

## File format

This is an example file, which was saved as `deathwatch/2026/03/14.jsonl`:

```
{"dimension":"minecraft:overworld","killer":null,"message":"duckinator fell from a high place","timestamp":"2026-03-14T21:19:23.252Z","type":"fall","victim":{"UUID":"031d654f-749b-3baf-825e-31e0971759d3","displayName":"duckinator"}}
{"dimension":"minecraft:overworld","killer":null,"message":"duckinator discovered the floor was lava","timestamp":"2026-03-14T21:20:08.860Z","type":"hotFloor","victim":{"UUID":"031d654f-749b-3baf-825e-31e0971759d3","displayName":"duckinator"}}
{"dimension":"minecraft:overworld","killer":"Skeleton","message":"duckinator was shot by Skeleton","timestamp":"2026-03-14T21:20:48.360Z","type":"arrow","victim":{"UUID":"031d654f-749b-3baf-825e-31e0971759d3","displayName":"duckinator"}}
```

Each line is a JSON object, with the following keys:

- `dimension`: the dimension the death occurred in (e.g. "minecraft:overworld")
- `killer`: the entity that caused the death (e.g. "Skeleton" if a skeleton killed the player). may be `null`.
- `message`: the death message provided by Minecraft
- `timestamp`: the date and time the death happened
- `type`: the type of death
- `victim`: an object representing the player

The `victim` objects have the following keys:

- `UUID`: the Minecraft UUID
- `displayName`: the Minecraft display name

Aside from the `victim`, every value is a string.
