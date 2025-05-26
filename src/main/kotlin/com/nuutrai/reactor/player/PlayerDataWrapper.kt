package com.nuutrai.reactor.player

import com.nuutrai.reactor.entity.Sellable
import com.nuutrai.reactor.util.VecLoc
import java.io.Serializable

class PlayerDataWrapper : Serializable {
	var balance: Int = 0
		private set
    val entities: HashMap<VecLoc?, Sellable?>?
    val locations: ArrayList<VecLoc?>?
	var heat: Double = 0.0
		private set
	var power: Int = 0
		private set

	constructor(pd: PlayerData) {
		this.balance = pd.balance
		this.heat = pd.heat
		this.power = pd.power
		this.entities = pd.entities.entityMap
		this.locations = pd.entities.locations
	}

	constructor(
		balance: Int,
		entities: HashMap<VecLoc?, Sellable?>?,
		locations: ArrayList<VecLoc?>?,
		heat: Int,
		power: Int
	) {
		this.balance = balance
		this.entities = entities
		this.locations = locations
		this.heat = heat.toDouble()
		this.power = power
	}

	override fun toString(): String {
		return super.toString()
	}
}
