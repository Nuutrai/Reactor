package com.nuutrai.reactor.listeners;

import com.nuutrai.reactor.Reactor;
import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {

        Player p = e.getPlayer();
        DataManager.unloadPlayerData(p);

        Bukkit.getScheduler().runTaskLater(Reactor.instance, () -> {
            WorldManager.deleteWorld(p);
        }, 10);

    }

}
