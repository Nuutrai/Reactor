package com.nuutrai.reactor.listeners;

import com.nuutrai.reactor.player.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {

        PlayerData.unloadPlayerData(e.getPlayer());

    }

}
