package com.nuutrai.reactor.data;

import com.google.common.collect.Maps;
import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nuutrai.reactor.Reactor;
import com.nuutrai.reactor.player.PlayerData;
import com.nuutrai.reactor.player.PlayerDataWrapper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Scanner;

import static com.nuutrai.reactor.Reactor.logger;

public class DataManager {

    private static final Map<Player, PlayerData> playerDataMap = Maps.newHashMap();


    public static PlayerData get(Player player) {
        return playerDataMap.get(player);
    }

    public static void loadPlayerData(Player player) {

        Gson gson = new GsonBuilder()
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        logger.info(f.getName());
                        return f.getName().equals("referent");
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();


        String json = readFromFile(player);
        PlayerDataWrapper playerDataWrapper = gson.fromJson(json, PlayerDataWrapper.class);
        PlayerData playerData = new PlayerData(playerDataWrapper);
        playerData.setPlayer(player);
        playerData.selection = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE);
        playerDataMap.put(player, playerData);

    }

    public static void unloadPlayerData(Player player) {
        savePlayerData(player);
    }

    public static void savePlayerData(Player player) {
        PlayerData playerData = playerDataMap.get(player);
        PlayerDataWrapper playerDataWrapper = new PlayerDataWrapper(playerData);
        Gson gson = new GsonBuilder()
                .addDeserializationExclusionStrategy(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        logger.info(f.getName());
                        return f.getName().equals("value"); // Skip the problematic field
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false; // No classes skipped
                    }
                })
                .create();
        String json = gson.toJson(playerDataWrapper);
        writeToFile(player, json);
    }

    // File stuff

    private static void writeToFile(Player player, String string) {
        try {

            File file = new File(Reactor.dataFolder, player.getUniqueId() + ".json");

            if (!file.exists()) {
                initData(player);
            }

            FileWriter myWriter = new FileWriter(file);
            myWriter.write(string);
            myWriter.close();
            logger.info("Successfully wrote to the file.");

        } catch (IOException e) {
            logger.severe("An error occurred whilst writing to a file.");
        }
    }

    private static String readFromFile(Player player) {
        try {
            File file = new File(Reactor.dataFolder, player.getUniqueId() + ".json");

            if (!file.exists()) {
                initData(player);
            }

            Scanner myReader = new Scanner(file);
            StringBuilder data = new StringBuilder();
            while (myReader.hasNext()) {
                data.append(myReader.nextLine());
            }
            myReader.close();
            return data.toString();
        } catch (FileNotFoundException e) {
            logger.severe("An error occurred.");
            logger.severe(e.getMessage());
        }
        return null;
    }

    private static void initData(Player player) {

        PlayerData playerData = new PlayerData();
        playerDataMap.put(player, playerData);

        try {
            File myObj = new File(Reactor.dataFolder, player.getUniqueId() + ".json");
            if (myObj.createNewFile()) {
                logger.info("File created: " + myObj.getName());
                savePlayerData(player);
            } else {
                logger.info("File already exists.");
            }
        } catch (IOException e) {
            logger.severe("An error occurred whilst writing to a file.");
            logger.severe(e.getMessage());
        }
    }

}
