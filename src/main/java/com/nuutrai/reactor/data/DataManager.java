package com.nuutrai.reactor.data;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.player.PlayerData;
import org.bukkit.entity.Player;

import java.util.Map;

public class DataManager {

    private static Map<Player, PlayerData> playerDataMap = Maps.newHashMap();


    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance(Player player) {
        return balance;
    }

    public void unloadPlayerData(Player player) {

        // Put more handler stuff here

        savePlayerData(player);

        return;
    }

    public void savePlayerData(Player player) {
        return;
    }

}
