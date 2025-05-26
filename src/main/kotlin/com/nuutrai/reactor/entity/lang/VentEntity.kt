package com.nuutrai.reactor.entity.lang

import com.nuutrai.reactor.entity.Sellable
import com.nuutrai.reactor.util.MultiTypeMap
import org.bukkit.Material

abstract class VentEntity(id: String, block: Material?) : Sellable(id, block) {
	override fun tick() {
		tick(arrayOf(), MultiTypeMap())
	}
}
