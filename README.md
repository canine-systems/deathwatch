# deathwatch

Deathwatch is a Minecraft mod which tracks player deaths on a server.

Currently, it logs to a collection of files. Eventually, it might support a database.

Files are named `deathwatch/YYYY/MM/DD.jsonl`. Each line is a JSON object containing one death.

This format is done to avoid the long-term stability problems encountered with the [original deathwatch from a decade ago](https://github.com/kayila/deathwatch) which logged to a single CSV file.

Officially supports:
- Minecraft v1.21.1
- Neoforge 21.1.216
