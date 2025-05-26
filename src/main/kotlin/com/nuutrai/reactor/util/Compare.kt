package com.nuutrai.reactor.util

import org.bukkit.entity.Player
import java.util.*

object Compare {
	fun player(player1: Player, player2: UUID?): Boolean {
		return player1.getUniqueId() == player2
	}
}
