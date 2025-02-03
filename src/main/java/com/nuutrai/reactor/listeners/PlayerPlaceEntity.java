package com.nuutrai.reactor.listeners;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.Reactor;
import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.player.PlayerData;
import com.nuutrai.reactor.util.FaceToDirection;
import com.nuutrai.reactor.util.VecLoc;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.time.Instant;
import java.util.HashMap;

import static com.nuutrai.reactor.Reactor.logger;

public class PlayerPlaceEntity implements Listener {

    private static HashMap<Player, Instant> cooldowns = Maps.newHashMap();
    private static final int timeToWait = 200;
    private static final int timeToPurge = 1000;

    @EventHandler
    public void PlaceInteractEvent(PlayerInteractEvent e) {

        Player p = e.getPlayer();
        if (!p.getWorld().equals(Bukkit.getWorld(p.getUniqueId().toString())))
            return;

        e.setCancelled(true);

        // Add raytrace at some point
        Block block = e.getClickedBlock();
        if (block == null)
            return;

        // Add cooldown
        if (!cooldowns.containsKey(p))
            cooldowns.put(p, Instant.now().minusMillis(timeToWait));
        if (cooldowns.get(p).isAfter(Instant.now()))
            return;
        cooldowns.put(p, Instant.now().plusMillis(timeToWait));
        Bukkit.getScheduler().runTaskLater(Reactor.instance, () -> {
            if (!cooldowns.containsKey(p))
                return;
            if (cooldowns.get(p).isAfter(Instant.now().minusMillis(timeToPurge)))
                return;
            cooldowns.remove(p);
        }, timeToPurge);
        // End

        if (e.getAction().isRightClick()) {

            VecLoc newLoc = new VecLoc(block.getLocation().add(FaceToDirection.get(e.getBlockFace())), p.getUniqueId());

            if (newLoc.getY() != 121)
                return;

            for (Player player: p.getWorld().getPlayers()) {
                VecLoc pLoc = new VecLoc(player.getLocation(), p.getUniqueId());
                if (pLoc.getX() == newLoc.getX() && pLoc.getY() == newLoc.getY() && pLoc.getZ() == newLoc.getZ())
                    return;
            }

            // Checks
            ItemStack item = e.getItem();
            if (item == null || item.getType() == Material.AIR)
                return;

            NamespacedKey key = new NamespacedKey(Reactor.instance, "reactor-id");
            String id = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
            if (id == null || id.isEmpty())
                return;
            // End

            place(id, p, newLoc);

        } else if (e.getAction().isLeftClick()) {
            VecLoc newLoc = new VecLoc(block.getLocation(), p.getUniqueId());
            breakEntity(p, newLoc);
        }
    }

    public static void breakEntity(Player p, VecLoc vecloc) {
        PlayerData pd = DataManager.get(p);

        if (pd.getEntities().getSellable(vecloc) == null) {
            p.getInventory().setItem(4, ItemStack.of(Material.AIR));
            return;
        }

        pd.getEntities().remove(vecloc);
        vecloc.toLocation().getBlock().setType(Material.AIR);

    }

    public static void place(String id, Player p, VecLoc vecLoc) {
//        Cell cell = Cell.getCell(id).clone();

        PlayerData pd = DataManager.get(p);

        Sellable copy = Sellable.get(id);
        Sellable entity = Sellable.create(copy, p, vecLoc);

        logger.info("" + pd.getBalance());
        logger.info("" + entity.getType().getCost());

        Material block = entity.getBlock();
        Location location = vecLoc.toLocation();
        location.getBlock().setType(block);

        DataManager.get(p).addEntity(entity, vecLoc);
    }

}
