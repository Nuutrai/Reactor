package com.nuutrai.reactor.listeners;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.player.Claim;
import com.nuutrai.reactor.player.ClaimHandler;
import com.nuutrai.reactor.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.xml.crypto.Data;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        DataManager.loadPlayerData(e.getPlayer());

    }

}
