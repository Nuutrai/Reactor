package com.nuutrai.reactor.entity

import com.nuutrai.reactor.util.VecLoc
import java.io.Serializable

class EntityHandler : Serializable {
	val entityMap: MutableMap<VecLoc?, Sellable?> = mutableMapOf()
	val locations: ArrayList<VecLoc> = ArrayList()

	fun add(entity: Sellable, location: VecLoc?) {
		entity.entityHandler = this
		entityMap.put(location, entity)
		locations.add(location!!)
	}

	fun remove(location: VecLoc?) {
		entityMap.remove(location)
		locations.remove(location)
	}

	fun place() {
		for (loc in this.locations) {
			place(loc)
		}
	}

	fun place(loc: VecLoc) {
		val block = entityMap[loc]!!.block
		loc.toLocation().block.type = block!!
	}

	fun get(loc: VecLoc?): Sellable? {
		return entityMap[loc]
	}

	fun tick() {
		for (location in locations) {
			entityMap[location]!!.tick()
		}
	}
}
