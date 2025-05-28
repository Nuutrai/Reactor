package com.nuutrai.reactor.entity.lang

import com.nuutrai.reactor.data.DataManager
import com.nuutrai.reactor.entity.Sellable
import com.nuutrai.reactor.util.ChangeMode
import com.nuutrai.reactor.util.MultiTypeMap
import com.nuutrai.reactor.util.VecLoc
import org.bukkit.Material

abstract class CellEntity(id: String, block: Material) : Sellable(id, block) {
	override fun tick() {
		val map = MultiTypeMap()

		val neighbours = position!!.neighbours.toTypedArray<VecLoc>()
		val neighbourEntitiesArray = ArrayList<Sellable?>()

		val heatOutput = heat

		if (neighbours.isEmpty()) {
			DataManager.get(player)?.addHeat(heatOutput)
		}

		for (neighbour in neighbours) {
			neighbourEntitiesArray.add(entityHandler!!.get(neighbour))
		}

		health(1.0, ChangeMode.DECREASE)

		val neighbourEntities = neighbourEntitiesArray.toTypedArray()

		map.add("heat", heat)

		tick(neighbourEntities, map)
	}
}
