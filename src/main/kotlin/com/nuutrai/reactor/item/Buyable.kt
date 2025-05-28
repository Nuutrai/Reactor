package com.nuutrai.reactor.item

import com.google.common.collect.Maps
import com.nuutrai.reactor.Reactor
import com.nuutrai.reactor.data.DataManager
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType

/**
 *
 * @id The type or identifier of any reactor
 * @cost The cost of the item
 * @name The name shown in the store
 * @description The description shown in the store
 * @heat The amount of heat produced in a tick
 * @health The max amount of heat for an item
 * @item The item displayed in the store
 */
abstract class Buyable {
    val id: String
    val cost: Int
	private val name: String?
	private val description: String?
    val heat: Double
    val power: Int
	val item: ItemStack?

	/**
	 * Health represents both durability & heat content.
	 */
    val health: Double

	constructor(
		id: String,
		cost: Int,
		name: String?,
		description: String?,
		heat: Double,
		power: Int,
		health: Double,
		item: ItemStack?
	) {
		this.id = id
		this.cost = cost
		this.name = name
		this.description = description
		this.heat = heat
		this.power = power
		this.item = item
		this.health = health
	}

	constructor(
		id: String,
		cost: Int,
		name: String,
		description: String?,
		heat: Double,
		power: Int,
		health: Double,
		colour: NamedTextColor?,
		item: Material
	) {
		this.heat = heat
		this.health = health
		this.power = power
		val itemWithMeta = ItemStack.of(item)
		val meta = itemWithMeta.itemMeta
		val key = NamespacedKey(Reactor.instance!!, "reactor-id")
		meta.persistentDataContainer.set<String?, String?>(key, PersistentDataType.STRING, id)

		meta.displayName(Component.text(name, colour).decoration(TextDecoration.ITALIC, false))

		itemWithMeta.setItemMeta(meta)

		this.id = id
		this.cost = cost
		this.name = name
		this.description = description
		this.item = itemWithMeta
	}

	// Needed here? Probably put in store stuff
	fun buy(player: Player, item: Buyable) {
		val balance = DataManager.get(player)?.balance ?: return
		DataManager.get(player)?.balance = balance - item.cost
	}


	fun equals(b: Buyable): Boolean {
		return this.id == b.id
	}

	companion object {
		private val BUYABLES: MutableMap<String, Buyable> = mutableMapOf()

        fun get(id: String): Buyable {
			return BUYABLES[id]!!
		}

		@JvmStatic
		fun add(b: Buyable) {
			BUYABLES.put(b.id, b)
		}
	}
}
