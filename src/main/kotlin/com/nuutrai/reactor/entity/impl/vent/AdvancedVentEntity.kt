package com.nuutrai.reactor.entity.impl.vent

import com.nuutrai.reactor.entity.Sellable
import com.nuutrai.reactor.entity.lang.VentEntity
import com.nuutrai.reactor.util.MultiTypeMap
import org.bukkit.Material

class AdvancedVentEntity : VentEntity("advanced_vent", Material.IRON_BLOCK) {
	override fun tick(neighbours: Array<Sellable?>, params: MultiTypeMap) {
	}

	override val sellAmount: Double
		get() = 0.0

	override fun sell() {
	}

	override fun clone(): Sellable {
		return AdvancedVentEntity()
	}
}
