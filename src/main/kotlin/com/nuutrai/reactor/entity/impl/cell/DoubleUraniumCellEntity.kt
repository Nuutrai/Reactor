package com.nuutrai.reactor.entity.impl.cell

import com.nuutrai.reactor.entity.Pairable
import com.nuutrai.reactor.entity.Sellable
import com.nuutrai.reactor.entity.lang.CellEntity
import com.nuutrai.reactor.util.MultiTypeMap
import org.bukkit.Material

class DoubleUraniumCellEntity : CellEntity("uranium_double", Material.EMERALD_BLOCK), Pairable {
	override fun tick(neighbours: Array<Sellable?>, params: MultiTypeMap?) {
		TODO()
	}

	override val sellAmount: Double
		get() = 0.0

	override fun sell() {
	}

	override fun clone(): Sellable {
		return DoubleUraniumCellEntity()
	}
}
