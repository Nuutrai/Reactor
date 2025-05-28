package com.nuutrai.reactor.store

import com.google.common.collect.MultimapBuilder
import com.nuutrai.reactor.Reactor
import com.nuutrai.reactor.data.DataManager
import com.nuutrai.reactor.item.Buyable
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.attribute.Attribute
import org.bukkit.attribute.AttributeModifier
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

object Store {

    fun setup(player: Player) {
		val inventory: Inventory = player.inventory

		val items = getItems(player)

		for (i in items.indices) {
			inventory.setItem(i, items[i])
		}
	}

	fun getItems(player: Player?): ArrayList<ItemStack?> {
		val items = ArrayList<ItemStack?>()

		for (i in 0..2) {
			items.add(background)
		}

		val playerData = DataManager.get(player)!!

		val power = ItemStack(Material.WIND_CHARGE)
		val powerMeta = power.itemMeta
		powerMeta.displayName(
			Component.text(playerData.power.toString() + " Power", NamedTextColor.AQUA)
				.decoration(TextDecoration.ITALIC, false)
		)
		power.setItemMeta(powerMeta)
		items.add(power)

		items.add(ItemStack.of(Material.AIR))
		val heat = ItemStack(Material.BLAZE_POWDER)
		val heatMeta = heat.itemMeta
		heatMeta.displayName(
			Component.text(playerData.heat.toString() + " Heat", NamedTextColor.RED)
				.decoration(TextDecoration.ITALIC, false)
		)
		heat.setItemMeta(heatMeta)
		items.add(heat)

		for (i in 0..2) {
			items.add(background)
		}

		/*
         Type-s-Stage
        */
		val cs1 = ItemStack(Buyable.get("uranium_single").item!!)
		val cs2 = ItemStack(Buyable.get("uranium_double").item!!)
		val cs3 = ItemStack(Buyable.get("uranium_quad").item!!)

		items.add(cs1)
		items.add(cs2)
		items.add(cs3)

		for (i in 0..3) {
			items.add(background)
		}

		val vs1 = ItemStack(Buyable.get("basic_vent").item!!)
		val vs2 = ItemStack(Buyable.get("advanced_vent").item!!)

		items.add(vs1)
		items.add(vs2)

		for (i in 0..17) {
			items.add(background)
		}

		for (i in 0..3) {
			items.add(ItemStack.of(Material.AIR))
		}

		items.add(playerData.determinePauseItem())

		return items
	}

	private val background: ItemStack
		get() {
			val item = ItemStack(Material.GRAY_STAINED_GLASS_PANE)
			val itemMeta = item.itemMeta
			itemMeta.isHideTooltip = true
			itemMeta.attributeModifiers = MultimapBuilder.hashKeys().hashSetValues().build<Attribute?, AttributeModifier?>()
			item.setItemMeta(itemMeta)
			return item
		}
}
