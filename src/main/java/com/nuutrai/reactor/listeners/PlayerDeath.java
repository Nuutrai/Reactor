package com.nuutrai.reactor.listeners;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.store.Store;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import static com.nuutrai.reactor.Reactor.instance;

public class PlayerDeath implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.setDroppedExp(0);

        DataManager.loadPlayerData(e.getPlayer());

        Player p = e.getPlayer();

        Store.setup(p);

        World world = DataManager.get(p).getPlayer().getWorld();
        Bukkit.getScheduler().runTask(instance, () -> {
            Bukkit.getScheduler().runTaskLater(instance, () -> {
                p.teleport(new Location(world, 0, 121, 0));
                p.setAllowFlight(true);
            }, 20);
        });
    }
}
