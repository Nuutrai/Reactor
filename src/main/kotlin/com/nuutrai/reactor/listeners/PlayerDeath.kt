package com.nuutrai.reactor.listeners

import com.nuutrai.reactor.Reactor
import com.nuutrai.reactor.data.DataManager
import com.nuutrai.reactor.store.Store.setup
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent

class PlayerDeath : Listener {
	@EventHandler
	fun onPlayerDeath(e: PlayerDeathEvent) {
		e.droppedExp = 0

		DataManager.loadPlayerData(e.player)

		val p = e.player

		setup(p)

		val world = DataManager.get(p)?.getPlayer()?.world ?: return
		Bukkit.getScheduler().runTask(Reactor.Companion.instance!!, Runnable {
			Bukkit.getScheduler().runTaskLater(Reactor.Companion.instance!!, Runnable {
				p.teleport(Location(world, 0.0, 121.0, 0.0))
				p.allowFlight = true
			}, 20)
		})
	}
}
