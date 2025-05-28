package com.nuutrai.reactor.tick

import com.nuutrai.reactor.data.DataManager
import org.bukkit.Bukkit

class Ticker {
	fun tick() {
		for (player in Bukkit.getOnlinePlayers()) {
			DataManager.get(player)?.tick() ?: continue
		}
	}
}
