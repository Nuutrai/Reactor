package com.nuutrai.reactor.listeners;

import com.nuutrai.reactor.player.Claim;
import com.nuutrai.reactor.player.ClaimHandler;
import com.nuutrai.reactor.player.PlayerData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        PlayerData.loadPlayerData(e.getPlayer());

    }

}
