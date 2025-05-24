package com.nuutrai.reactor.listeners;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.player.PlayerData;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class PlayerSwapHand implements Listener {

    @EventHandler
    public void onSwapHand(PlayerSwapHandItemsEvent e) {

        e.setCancelled(true);
        PlayerData pd = DataManager.get(e.getPlayer());
        pd.setPaused(!pd.isPaused());

    }

}
