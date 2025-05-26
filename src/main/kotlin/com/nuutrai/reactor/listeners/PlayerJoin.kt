package com.nuutrai.reactor.listeners

import com.nuutrai.reactor.Reactor
import com.nuutrai.reactor.data.DataManager
import com.nuutrai.reactor.store.Store.setup
import com.nuutrai.reactor.world.WorldManager.createWorld
import org.bukkit.Bukkit
import org.bukkit.GameRule
import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

class PlayerJoin : Listener {
	@EventHandler
	fun onPlayerJoin(e: PlayerJoinEvent) {
		DataManager.loadPlayerData(e.getPlayer())

		val p = e.getPlayer()

		setup(p)

		Bukkit.getScheduler().runTask(Reactor.Companion.instance!!, Runnable {
			val world = createWorld(p)
			world!!.setGameRule<Boolean?>(GameRule.MOB_GRIEFING, false)
			world.setGameRule<Boolean?>(GameRule.DO_MOB_SPAWNING, false)
			world.setGameRule<Boolean?>(GameRule.DO_MOB_LOOT, false)
			Bukkit.getScheduler().runTaskLater(Reactor.Companion.instance!!, Runnable {
				p.teleport(Location(world, 0.0, 121.0, 0.0))
				p.setAllowFlight(true)
			}, 20)
		})

		if (p.getName() == "Nuutrai") {
//            Claim claim = new Claim(p);
//            claim.newEntity(Cell.);
//            DataManager.get(p).setClaim(new Claim(p));
		}
	}
}
