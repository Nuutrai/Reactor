package com.nuutrai.reactor.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerDropItemEvent

class PlayerDrop : Listener {
	@EventHandler
	fun onItemDrop(e: PlayerDropItemEvent) {
		e.isCancelled = true
	}
}
