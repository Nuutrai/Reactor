package com.nuutrai.reactor.listeners;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.store.Store;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        DataManager.loadPlayerData(e.getPlayer());

        Player p = e.getPlayer();

        p.setAllowFlight(true);

        Store.setup(p);

        if (p.getName().equals("Nuutrai")) {
//            Claim claim = new Claim(p);
//            claim.newEntity(Cell.);
//            DataManager.get(p).setClaim(new Claim(p));
        }

    }

}
