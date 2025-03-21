package com.nuutrai.reactor.listeners;

import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.store.Store;
import com.nuutrai.reactor.world.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static com.nuutrai.reactor.Reactor.instance;

public class PlayerJoin implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {

        DataManager.loadPlayerData(e.getPlayer());

        Player p = e.getPlayer();

        Store.setup(p);

        Bukkit.getScheduler().runTask(instance, () -> {
            World world = WorldManager.createWorld(p);
            world.setGameRule(GameRule.MOB_GRIEFING, false);
            world.setGameRule(GameRule.DO_MOB_SPAWNING, false);
            world.setGameRule(GameRule.DO_MOB_LOOT, false);
            Bukkit.getScheduler().runTaskLater(instance, () -> {
                p.teleport(new Location(world, 0, 121, 0));
                p.setAllowFlight(true);
            }, 20);
        });

        if (p.getName().equals("Nuutrai")) {
//            Claim claim = new Claim(p);
//            claim.newEntity(Cell.);
//            DataManager.get(p).setClaim(new Claim(p));
        }
    }
}
