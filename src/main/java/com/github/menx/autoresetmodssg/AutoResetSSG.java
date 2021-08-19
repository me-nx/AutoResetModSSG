package com.github.menx.autoresetmodssg;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class AutoResetSSG implements ModInitializer {
    public static final String MOD_ID = "autoresetssg";
    public static final String MOD_NAME = "Auto Reset Mod SSG";
    private static final long DEFAULT_SEED = 1;
    public static boolean isPlaying = false;
    public static boolean isCreatingWorld = false;
    public static long seed;
    public static Logger LOGGER = LogManager.getLogger();

    public static void log(Level level, String message) {
        LOGGER.log(level, "[" + MOD_NAME + "] " + message);
    }

    public static int getNextAttempt() {
        try {
            File file = new File("attempts_setseed.txt");
            int value;
            if (file.exists()) {
                Scanner fileReader = new Scanner(file);
                String string = fileReader.nextLine().trim();
                fileReader.close();
                try {
                    value = Integer.parseInt(string);
                } catch (NumberFormatException ignored) {
                    value = 0;
                }
            } else {
                value = 0;
            }
            value++;
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(Integer.toString(value));
            fileWriter.close();
            return value;
        } catch (IOException ignored) {
            return -1;
        }
    }

    @Override
    public void onInitialize() {
        log(Level.INFO, "Initializing");
        seed = getSeed();
        log(Level.INFO, "Seed: " + String.valueOf(seed));
    }

    public long getSeed() {
        try {
            File seedFile = new File("seed.txt");
            boolean existed = !seedFile.createNewFile();
            if (existed)
            {
                Scanner scanner = new Scanner(seedFile);
                if(scanner.hasNextLong())
                {
                    return scanner.nextLong();
                }
                else
                {
                    log(Level.ERROR, "No seed found in seed.txt");
                    return DEFAULT_SEED;
                }
            }
            else
            {
                return DEFAULT_SEED;
            }
        }
        catch (IOException e)
        {
            log(Level.ERROR, e.toString());
            return DEFAULT_SEED;
        }
    }
}