package com.nuutrai.reactor.tick;

import com.nuutrai.reactor.data.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class Ticker {
    public void tick() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            DataManager.get(player).tick();
        }
    }
}
