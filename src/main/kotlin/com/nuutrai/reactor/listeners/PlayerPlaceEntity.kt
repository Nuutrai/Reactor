package com.nuutrai.reactor.listeners

import com.google.common.collect.Maps
import com.nuutrai.reactor.Reactor
import com.nuutrai.reactor.data.DataManager
import com.nuutrai.reactor.entity.Sellable
import com.nuutrai.reactor.util.FaceToDirection.get
import com.nuutrai.reactor.util.VecLoc
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.NamespacedKey
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.persistence.PersistentDataType
import java.time.Instant

class PlayerPlaceEntity : Listener {
	@EventHandler
	fun PlaceInteractEvent(e: PlayerInteractEvent) {
		val p = e.getPlayer()
		if (p.world != Bukkit.getWorld(p.uniqueId.toString())) return

		e.setCancelled(true)

		// Add raytrace at some point
		val block = e.clickedBlock
		if (block == null) return

		// Add cooldown
		if (!cooldowns.containsKey(p)) cooldowns.put(p, Instant.now().minusMillis(timeToWait.toLong()))
		if (cooldowns.get(p)!!.isAfter(Instant.now())) return
		cooldowns.put(p, Instant.now().plusMillis(timeToWait.toLong()))
		Bukkit.getScheduler().runTaskLater(Reactor.instance!!, Runnable {
			if (!cooldowns.containsKey(p)) return@Runnable
			if (cooldowns.get(p)!!.isAfter(Instant.now().minusMillis(timeToPurge.toLong()))) return@Runnable
			cooldowns.remove(p)
		}, timeToPurge.toLong())

		// End
		if (e.getAction().isRightClick) {
			val newLoc = VecLoc(block.location.add(get(e.getBlockFace())), p.uniqueId)

			if (newLoc.y != 121) return

			for (player in p.world.players) {
				val pLoc = VecLoc(player.location, p.uniqueId)
				if (pLoc.x == newLoc.x && pLoc.y == newLoc.y && pLoc.z == newLoc.z) return
			}

			// Checks
			val item = e.getItem()
			if (item == null || item.type == Material.AIR) return

			val key = NamespacedKey(Reactor.instance!!, "reactor-id")
			val id =
				item.itemMeta.persistentDataContainer.get<String?, String?>(key, PersistentDataType.STRING!!)
			if (id == null || id.isEmpty()) return

			// End
			place(id, p, newLoc)
		} else if (e.getAction().isLeftClick) {
			val newLoc = VecLoc(block.location, p.uniqueId)
			breakEntity(p, newLoc)
		}
	}

	companion object {
		private val cooldowns: HashMap<Player?, Instant?> = Maps.newHashMap<Player?, Instant?>()
		private const val timeToWait = 100
		private const val timeToPurge = 1000

		fun breakEntity(p: Player, vecloc: VecLoc) {
			val pd = DataManager.get(p)

			if (pd.entities.get(vecloc) == null) {
				p.inventory.setItem(4, ItemStack.of(Material.AIR))
				return
			}

			pd.entities.remove(vecloc)
			vecloc.toLocation().block.type = Material.AIR
		}

		fun place(id: String?, p: Player?, vecLoc: VecLoc) {
//        Cell cell = Cell.getCell(id).clone();

			val pd = DataManager.get(p)

			val copy = Sellable.get(id)
			val entity = Sellable.create(copy, p, vecLoc)

			Reactor.Companion.logger!!.info("" + pd.balance)
			Reactor.Companion.logger!!.info("" + entity.getType().cost)

			val block = entity.block
			val location = vecLoc.toLocation()
			location.block.setType(block)

			DataManager.get(p).addEntity(entity, vecLoc)
		}
	}
}
