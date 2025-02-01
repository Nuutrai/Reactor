package com.nuutrai.reactor.tick;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ticker {
    public void tick(){
        for (Player player : Bukkit.getOnlinePlayers()){
           PlayerData data = DataManager.get(player);
        }
    }
}
