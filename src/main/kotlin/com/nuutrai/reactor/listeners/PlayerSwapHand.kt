package com.nuutrai.reactor.listeners

import com.nuutrai.reactor.data.DataManager
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent

class PlayerSwapHand : Listener {
	@EventHandler
	fun onSwapHand(e: PlayerSwapHandItemsEvent) {
		e.isCancelled = true
		val pd = DataManager.get(e.getPlayer()) ?: return
		pd.isPaused = !pd.isPaused
	}
}
