package com.nuutrai.reactor.entity.impl.vent

import com.nuutrai.reactor.entity.Sellable
import com.nuutrai.reactor.entity.lang.VentEntity
import com.nuutrai.reactor.util.ChangeMode
import com.nuutrai.reactor.util.MultiTypeMap
import org.bukkit.Material

class BasicVentEntity : VentEntity("basic_vent", Material.IRON_BLOCK) {
	override fun tick(neighbours: Array<Sellable?>, params: MultiTypeMap?) {
		System.out.printf("Decreased health of %s from %s to %s%n", position.toString(), health, health - heat)
		health(heat, ChangeMode.DECREASE)
	}

	override val sellAmount: Double
		get() = 0.0

	override fun sell() {
	}

	override fun clone(): Sellable {
		return BasicVentEntity()
	}
}
