package com.nuutrai.reactor.listeners;

import com.nuutrai.reactor.Reactor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import static com.nuutrai.reactor.Reactor.logger;

public class PlayerInventoryClick implements Listener {

    @EventHandler
    public void PlayerInventoryClick(InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player player))
            return;
        if (e.getClickedInventory() != player.getInventory())
            return;

        e.setCancelled(true);

        ItemStack item = e.getCurrentItem();

        if (item == null || item.getType() == Material.AIR)
            return;

        NamespacedKey key = new NamespacedKey(Reactor.instance, "reactor-id");
        String id = item.getItemMeta().getPersistentDataContainer().get(key, PersistentDataType.STRING);

        if (id == null || id.isEmpty()) {
            return;
        }

        logger.info(id);
    }

}
