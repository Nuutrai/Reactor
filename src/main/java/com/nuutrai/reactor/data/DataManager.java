package com.nuutrai.reactor.data;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.Reactor;
import com.nuutrai.reactor.player.Claim;
import com.nuutrai.reactor.player.ClaimHandler;
import com.nuutrai.reactor.player.PlayerData;
import org.apache.commons.lang3.SerializationUtils;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import static com.nuutrai.reactor.Reactor.logger;

public class DataManager {

    private static Map<Player, PlayerData> playerDataMap = Maps.newHashMap();


    public static PlayerData getPlayerData(Player player) {
        return playerDataMap.get(player);
    }

    public void loadPlayerData(Player player) {

        // Put handler stuff here

        byte[] deserialized = Base64.getDecoder().decode(readFromFile(player));
        PlayerData playerData = SerializationUtils.deserialize(deserialized);
        Claim claim = playerData.getClaim();

        if (claim != null)
            ClaimHandler.add(claim);
    }

    public void unloadPlayerData(Player player) {

        // Put more handler stuff here

        savePlayerData(player);

        PlayerData playerData = playerDataMap.get(player);
        Claim claim = playerData.getClaim();
        if (claim != null)
            ClaimHandler.remove(claim);
    }

    public void savePlayerData(Player player) {

        PlayerData playerData = playerDataMap.get(player);
        byte[] serialized = SerializationUtils.serialize(playerData);
        String encodedData = Base64.getEncoder().encodeToString(serialized);
        writeToFile(player, encodedData);
    }

    public void initData(Player player) {
        try {
            File myObj = new File(Reactor.dataFolder, player.getUniqueId() + ".pd");
            if (myObj.createNewFile()) {
                System.out.println("File created: " + myObj.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            logger.severe("An error occurred whilst writing to a file.");
        }
    }

    public void writeToFile(Player player, String string) {
        try {

            File file = new File(Reactor.dataFolder, player.getUniqueId() + ".pd");

            if (!file.exists()) {
                initData(player);
            }

            FileWriter myWriter = new FileWriter(file);
            myWriter.write(string);
            myWriter.close();
            System.out.println("Successfully wrote to the file.");

        } catch (IOException e) {
            logger.severe("An error occurred whilst writing to a file.");
        }
    }

    public String readFromFile(Player player) {
        try {
            File myObj = new File(Reactor.dataFolder, player.getUniqueId() + ".pd");
            Scanner myReader = new Scanner(myObj);
            String data = myReader.nextLine();
            myReader.close();
            return data;
        } catch (FileNotFoundException e) {
            logger.severe("An error occurred.");
        }
        return null;
    }

}
