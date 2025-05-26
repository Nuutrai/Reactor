package com.nuutrai.reactor.listeners

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerItemDamageEvent

class PlayerDamage : Listener {
	@EventHandler
	fun onDamage(e: PlayerItemDamageEvent) {
		e.isCancelled = true
	}
}
