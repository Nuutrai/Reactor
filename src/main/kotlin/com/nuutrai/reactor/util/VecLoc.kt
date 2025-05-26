package com.nuutrai.reactor.util

import com.nuutrai.reactor.Reactor
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.World
import java.io.Serializable
import java.util.*

class VecLoc : Serializable {
	//    public String getId() {
	//        return String.valueOf(hashCode());
	//    }
    @JvmField
    var x: Int
	@JvmField
    var y: Int
	@JvmField
    var z: Int
	var uUID: UUID
		private set

	constructor(x: Int, y: Int, z: Int, world: UUID) {
		this.uUID = world
		this.z = z
		this.y = y
		this.x = x
	}

	constructor(location: Location, world: UUID) {
		this.x = location.getBlockX()
		this.y = location.getBlockY()
		this.z = location.getBlockZ()
		this.uUID = world
	}

	fun toLocation(): Location {
		try {
			return Location(getWorld(), x.toDouble(), y.toDouble(), z.toDouble())
		} catch (e: NullPointerException) {
			Reactor.Companion.logger!!.severe("BUILD FALLBACK DUMB DUMB")
		}
		return Location(Bukkit.getWorlds().getFirst(), x.toDouble(), y.toDouble(), z.toDouble())
	}

	fun toLocation(world: UUID): Location {
		return Location(getWorld(world), x.toDouble(), y.toDouble(), z.toDouble())
	}

	fun setWorld(world: UUID) {
		this.uUID = world
	}

	fun getWorld(): World? {
		return getWorld(this.uUID)
	}

	val neighbours: MutableList<VecLoc?>
		get() {
			val locs = ArrayList<VecLoc?>()

			locs.add(VecLoc(x + 1, y, z, this.uUID))
			locs.add(VecLoc(x - 1, y, z, this.uUID))
			locs.add(VecLoc(x, y, z + 1, this.uUID))
			locs.add(VecLoc(x, y, z - 1, this.uUID))

			return locs
		}

	override fun equals(`object`: Any?): Boolean {
		if (this === `object`) return true
		if (`object` == null || javaClass != `object`.javaClass) return false
		val vecLoc = `object` as VecLoc
		return x == vecLoc.x && y == vecLoc.y && z == vecLoc.z && this.uUID == vecLoc.uUID
	}

	override fun hashCode(): Int {
		return Objects.hash(x, y, z, this.uUID)
	}

	override fun toString(): String {
		return String.format("VecLoc@%s,%s,%s", this.x, this.y, this.z)
	}

	companion object {
		fun getWorld(world: UUID): World? {
			return Bukkit.getWorld(world.toString())
		}
	}
}
