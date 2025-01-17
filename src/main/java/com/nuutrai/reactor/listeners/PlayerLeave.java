package com.nuutrai.reactor.listeners;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.player.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.xml.crypto.Data;

public class PlayerLeave implements Listener {

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {

        DataManager.unloadPlayerData(e.getPlayer());

    }

}
