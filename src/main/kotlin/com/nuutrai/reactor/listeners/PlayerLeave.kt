package com.nuutrai.reactor.listeners

import com.nuutrai.reactor.Reactor
import com.nuutrai.reactor.data.DataManager
import com.nuutrai.reactor.world.WorldManager.deleteWorld
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent

class PlayerLeave : Listener {
	@EventHandler
	fun onPlayerLeave(e: PlayerQuitEvent) {
		val p = e.getPlayer()
		DataManager.unloadPlayerData(p)

		Bukkit.getScheduler().runTaskLater(Reactor.instance!!, Runnable {
			deleteWorld(p)
		}, 10)
	}
}
