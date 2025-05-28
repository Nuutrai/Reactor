package com.nuutrai.reactor.entity

import com.google.common.collect.Maps
import com.nuutrai.reactor.item.Buyable
import com.nuutrai.reactor.util.ChangeMode
import com.nuutrai.reactor.util.MultiTypeMap
import com.nuutrai.reactor.util.VecLoc
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player

/**
 *
 * @id The id of the sellable (same as buyable)
 * @block The block type that will be placed
 * @maxHealth The max health
 * @player The associated player
 * @position The position (VecLoc) of the sellable
 * @currentHealth The current health of the sellable (To be determined at the end of the tick)
 */
abstract class Sellable(val id: String, val block: Material) {
	val maxHealth: Double
	var player: Player? = null
		private set
	var position: VecLoc? = null
		private set
    var currentHealth: Double = 0.0
    var entityHandler: EntityHandler? = null

	/*
	public Sellable(String id, Material block, Player player, Location position) {
		this.id = id;
		this.player = player.getUniqueId();
		this.position = new VecLoc(position, player.getUniqueId());
		this.block = block;
		this.maxHealth = getType().getHealth();
	}
	*/
	init {
		this.maxHealth = this.type!!.health
	}

	val type: Buyable
		get() = Buyable.get(id)

	val power: Int
		get() = this.type!!.power

	val heat: Double
		get() = this.type!!.heat

	val health: Double
		get() = this.type!!.health

	protected val heatToCostRatio: Float
		get() = (this.heat / this.type!!.cost).toFloat()

	fun health(by: Double, changeMode: ChangeMode) {
		when (changeMode) {
			ChangeMode.SET -> {
				currentHealth = by
			}
			ChangeMode.DECREASE -> {
				currentHealth -= by
			}
			ChangeMode.ADD -> {
				currentHealth += by
			}
		}
	}

	abstract fun tick(neighbours: Array<Sellable?>, params: MultiTypeMap)

	abstract fun tick()

	abstract val sellAmount: Double

	abstract fun sell()

	fun explode() {
		delete(false)
	}

	fun delete(isDepleted: Boolean) {
		if (isDepleted) {
			// Do stuff for turning into *nothing*
		}
	}

	abstract fun clone(): Sellable

	fun equals(s: Sellable): Boolean {
		return id == s.id
	}

	companion object {
		private val SELLABLES = mutableMapOf<String, Sellable>()

		fun create(sellable: Sellable, player: Player, position: Location): Sellable {
			return create(sellable, player, VecLoc(position, player.uniqueId))
		}

        fun create(sellable: Sellable, player: Player?, position: VecLoc?): Sellable {
			val s = sellable.clone()

			s.player = player
			s.position = position

			return s
		}

        fun get(id: String): Sellable? {
			return SELLABLES[id]
		}

		@JvmStatic
		fun add(s: Sellable) {
			SELLABLES.put(s.type.id, s)
		}
	}
}
