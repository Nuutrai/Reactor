package com.nuutrai.reactor.player

import com.nuutrai.reactor.Reactor
import com.nuutrai.reactor.entity.EntityHandler
import com.nuutrai.reactor.entity.Sellable
import com.nuutrai.reactor.util.VecLoc
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.Serializable

class PlayerData : Serializable {
    var balance: Int = 0
    val entities: EntityHandler = EntityHandler()
	var heat: Double = 0.0
	var power: Int = 0
    var selection: ItemStack = ItemStack.of(Material.AIR)
    var isPaused: Boolean = true
	private var player: Player? = null

	constructor()

	constructor(playerDataWrapper: PlayerDataWrapper) {
		this.balance = playerDataWrapper.balance
		this.heat = playerDataWrapper.heat
		this.power = playerDataWrapper.power
		for (loc in playerDataWrapper.locations) {
			val s = playerDataWrapper.entities[loc] ?: continue
			this.entities.add(s, loc)
		}
	}

	fun addBalance(by: Int) {
		this.balance += by
	}

	fun removeBalance(by: Int) {
		this.balance += by
	}

	fun addEntity(s: Sellable, location: VecLoc) {
		entities.add(s, location)
	}

	fun removeEntity(location: VecLoc) {
		entities.remove(location)
	}

	fun addPower(power: Int) {
		this.power += power
	}

	fun addHeat(heat: Double) {
		this.heat += heat
	}

	fun loadAllEntities() {
		entities.place()
	}

	fun loadEntity(loc: VecLoc) {
		entities.place(loc)
	}

	fun loadEntity(loc: Location) {
		loadEntity(VecLoc(loc, player!!.uniqueId))
	}

	fun getPlayer(): Player {
		return player!!
	}

	fun setPlayer(player: Player) {
		this.player = player
	}

	fun tick() {
		if (!this.isPaused) {
			entities.tick()
		}

		Bukkit.getScheduler().runTask(Reactor.Companion.instance, Runnable {
			player!!.inventory.setItem(40, determinePauseItem())
		})

		update()
	}

	private fun update() {
	}

	fun determinePauseItem(): ItemStack {
		val pause: ItemStack
		var pauseColour = NamedTextColor.RED
		if (this.isPaused) {
			pause = ItemStack.of(Material.FIREWORK_STAR)
			pauseColour = NamedTextColor.GRAY
		} else {
			pause = ItemStack.of(Material.FIRE_CHARGE)
		}

		val pauseMeta = pause.itemMeta
		pauseMeta.displayName(Component.text("Pause", pauseColour).decoration(TextDecoration.ITALIC, false))
		pause.setItemMeta(pauseMeta)
		return pause
	}
}
