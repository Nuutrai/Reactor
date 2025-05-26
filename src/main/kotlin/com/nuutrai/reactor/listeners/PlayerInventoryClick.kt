package com.nuutrai.reactor.listeners

import com.nuutrai.reactor.Reactor
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.persistence.PersistentDataType

class PlayerInventoryClick : Listener {

	@EventHandler
	fun onJoin(e: InventoryClickEvent){
		val player = if (e.whoClicked !is Player) return else e.whoClicked as Player

		if (e.clickedInventory !== player.inventory) return

		e.isCancelled = true

		val item = e.getCurrentItem()

		if (item == null || item.type == Material.AIR) return

		val key = NamespacedKey(Reactor.instance!!, "reactor-id")
		val id = item.itemMeta.persistentDataContainer.get<String, String>(key, PersistentDataType.STRING!!)

		if (id == null || id.isEmpty()) {
			return
		}

		player.inventory.setItem(4, item)

		Reactor.Companion.logger!!.info(id)
	}
}
