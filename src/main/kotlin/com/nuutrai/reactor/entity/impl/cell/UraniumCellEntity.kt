package com.nuutrai.reactor.entity.impl.cell

import com.nuutrai.reactor.Reactor
import com.nuutrai.reactor.data.DataManager
import com.nuutrai.reactor.entity.Pairable
import com.nuutrai.reactor.entity.Sellable
import com.nuutrai.reactor.entity.lang.CellEntity
import com.nuutrai.reactor.entity.lang.VentEntity
import com.nuutrai.reactor.util.ChangeMode
import com.nuutrai.reactor.util.MultiTypeMap
import org.bukkit.Material

class UraniumCellEntity : CellEntity("uranium_single", Material.EMERALD_BLOCK), Pairable {
	override fun tick(neighbours: Array<Sellable?>, params: MultiTypeMap) {
		val heatOutput: Double = params.get("heat", Double::class)!!

		var adjacentVents = 0
		for (neighbour in neighbours) {
			if ((neighbour ?: return) is VentEntity) {
				adjacentVents++
			}
		}
		for (neighbour in neighbours) {
			if ((neighbour ?: return) is VentEntity) {
				neighbour.health(heat/adjacentVents, ChangeMode.ADD)
			}
		}

		Reactor.logger.info("Ticked Cell ${position.toString()}, increased power by $power")
		DataManager.get(player)?.addPower(power)
	}

	override val sellAmount: Double
		get() = 0.0

	override fun sell() {
	}

	override fun clone(): Sellable {
		return UraniumCellEntity()
	}
}
