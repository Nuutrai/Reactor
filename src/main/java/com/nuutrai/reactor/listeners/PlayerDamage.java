package com.nuutrai.reactor.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;

public class PlayerDamage implements Listener {
    @EventHandler
    public void onDamage(PlayerItemDamageEvent e) {
        e.setCancelled(true);
    }
}
