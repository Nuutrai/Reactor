package com.nuutrai.reactor.listeners;

import com.google.common.collect.Maps;
import com.nuutrai.reactor.Reactor;
import com.nuutrai.reactor.data.DataManager;
import com.nuutrai.reactor.entity.Cell;
import com.nuutrai.reactor.entity.Sellable;
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

/**
 * TODO:
 * <p>
 * Add check for player's world before attempting place
 * <p>
 * Add check for y level cause I don't want to do the
 */

public class PlayerPlaceEntity implements Listener {

    private static HashMap<Player, Instant> cooldowns = Maps.newHashMap();
    private static final int timeToWait = 200;
    private static final int timeToPurge = 1000;

    @EventHandler
    public void PlaceInteractEvent(PlayerInteractEvent e) {
        // Checks
        ItemStack item = e.getItem();
        if (item == null || item.getType() == Material.AIR)
            return;

        NamespacedKey key = new NamespacedKey(Reactor.instance, "reactor-id");
        String id = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);
        if (id == null || id.isEmpty())
            return;

        if (e.getAction() != Action.RIGHT_CLICK_BLOCK) {
            if (e.getClickedBlock().getType() != null)
                logger.info(e.getClickedBlock().getType().name());
            return;
        }
        Block block = e.getClickedBlock();
        if (block == null)
            return;
        // End

        // Add cooldown
        Player p = e.getPlayer();
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

        VecLoc newLoc = new VecLoc(block.getLocation().add(FaceToDirection.get(e.getBlockFace())), p.getUniqueId());
        place(id, p, newLoc);
    }

    public static void place(String id, Player p, VecLoc vecLoc) {
//        Cell cell = Cell.getCell(id).clone();

        PlayerData pd = DataManager.get(p);

        logger.info("" + pd.getBalance());

        Sellable copy = Sellable.get(id);
        Material block = copy.getBlock();
        Sellable entity = copy.clone();
        logger.info("" + entity.getType().getCost());
        Location location = vecLoc.toLocation();
        location.getBlock().setType(block);
        DataManager.get(p).addEntity(entity, vecLoc);
    }

}
