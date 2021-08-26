**WARNING:** This mod is **not** valid for speedruns on http://speedrun.com/mc.

## Installation

1. Install Fabric mod loader
2. Put .jar file from https://github.com/me-nx/AutoResetModSSG/releases in mods folder
3. Start Minecraft
4. Enter seed in seed.txt
5. Restart the game

## Usage

Press the iron boots button on the main menu to start the world resetting mode.

To reset, simply leave the world (either the normal save and quit or the fast reset button).

To exit the resetting mode, press the "Stop Resets & Quit" button at the bottom left of the options menu.

To change the seed, edit seed.txt file

## Credits

This mod is a set seed version of DuncanRuns [AutoResetMod](https://github.com/DuncanRuns/AutoResetMod). I just tweaked it to load a seed.

## Info and Compatibility

Original AutoResetMod (random seed) is compatible (https://github.com/DuncanRuns/AutoResetMod/releases).

For 1.16.1, Closet Witch's Fast Reset mod is compatible (https://github.com/jan-leila/FastReset/releases).

Note for resource pack creators: the "Stop Resets & Quit" button can be changed by customizing menu.stop_resets in your language file if you like.

## Building

Java 16 or higher is required
```console
./gradlew build
```

Jar files are saved to build/libs/

## Changelog

### v1.0.0
- Forked from https://github.com/DuncanRuns/AutoResetMod
- Added loading seed from seed.txt
