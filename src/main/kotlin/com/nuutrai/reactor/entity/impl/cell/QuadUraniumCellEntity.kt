package com.nuutrai.reactor.entity.impl.cell

import com.nuutrai.reactor.Reactor
import com.nuutrai.reactor.entity.Sellable
import com.nuutrai.reactor.entity.lang.CellEntity
import com.nuutrai.reactor.util.MultiTypeMap
import org.bukkit.Material

class QuadUraniumCellEntity : CellEntity("uranium_quad", Material.EMERALD_BLOCK) {
	override fun tick(neighbours: Array<Sellable?>, params: MultiTypeMap) {
		Reactor.Companion.logger.info(
			"Cell at " + this.position!!.toLocation().blockX + ", " + this.position!!.toLocation()
				.blockY + ", " + this.position!!.toLocation().blockZ + " was ticked"
		)
	}

	override val sellAmount: Double
		get() = 0.0

	override fun sell() {
	}

	override fun clone(): Sellable {
		return QuadUraniumCellEntity()
	}
}
