package com.nuutrai.reactor.world

import com.nuutrai.reactor.Reactor
import com.nuutrai.reactor.data.DataManager
import com.nuutrai.reactor.util.FileUtils
import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.WorldCreator
import org.bukkit.WorldType
import org.bukkit.entity.Player
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import java.util.concurrent.Callable
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

/**
 * TODO:
 * Make a world teleport command based on a player
 */
object WorldManager {
	private val log: Logger = LoggerFactory.getLogger(WorldManager::class.java)
	var plugin: Reactor = Reactor.instance
	var worlds: MutableSet<Player> = mutableSetOf()
	var generator: VoidGenerator = VoidGenerator(plugin)

    fun createWorld(player: Player): World? {
		val worldName = player.uniqueId.toString()

		val worldFolder = File(plugin.server.worldContainer, worldName)
		if (worldFolder.exists()) {
			FileUtils.deleteFolder(worldFolder)
		}

		val executor = Executors.newCachedThreadPool()

		val future = executor.submit(Callable {
			Reactor.Companion.logger.info("1")
			cloneWorld("init", worldName)
		})

		try {
			if (!future.get()!!) {
				Reactor.Companion.logger.info("2")
				Reactor.Companion.logger.severe("Womp womp")
				return null
			}
		} catch (e: InterruptedException) {
			e.printStackTrace()
		} catch (e: ExecutionException) {
			e.printStackTrace()
		}

		Reactor.Companion.logger.info("3")

		addWorld(worldName)

		worlds.add(player)
		val pd = DataManager.get(player) ?: throw IllegalArgumentException()
		pd.isPaused = true
		pd.loadAllEntities()

		Reactor.Companion.logger.info("Done")

		return Bukkit.getWorld(worldName)
	}

	fun init(): Boolean {
		val worldName = "init"

		val worldFolder = File(plugin.server.worldContainer, worldName)
		if (worldFolder.exists()) {
			return true
		}

		addWorld("init")

		return true
	}

	fun cloneWorld(oldName: String, newName: String): Boolean {
		val oldWorldFile = File(plugin.server.worldContainer, oldName)
		val newWorldFile = File(plugin.server.worldContainer, newName)
		val ignoreFiles: MutableList<String?> = ArrayList(mutableListOf<String?>("session.lock", "uid.dat"))

		if (newWorldFile.exists()) {
			Reactor.Companion.logger.warning("Folder for new world '$newName' already exists")
			return false
		}

		Reactor.Companion.logger.info("Copying files for world '$oldName'")
		if (!FileUtils.copyFolder(oldWorldFile, newWorldFile, ignoreFiles)) {
			Reactor.Companion.logger.warning("Failed to copy files for world '$newName', see the log info")
			return false
		}

		if (newWorldFile.exists()) {
			Reactor.Companion.logger.info("Succeeded at copying files")
			return true
		}

		return false
	}

	fun addWorld(name: String): Boolean {
		val c = WorldCreator(name)
		c.seed(0)
		c.generator(generator)
		c.environment(World.Environment.NORMAL)
		c.type(WorldType.FLAT)
		c.generateStructures(false)

		//        if (!doLoad(c, true)) {
//            logger.severe("Failed to Create/Load the world '" + name + "'");
//            return false;
//        }
		c.createWorld()

		return true
	}

    fun deleteWorld(player: Player): Boolean {
		val worldName = player.uniqueId.toString()
		val world = plugin.server.getWorld(worldName)
		if (world == null) {
			return false
		}

		plugin.server.unloadWorld(worldName, false)

		try {
			val worldFile = world.worldFolder
			Reactor.Companion.logger.finer("deleteWorld(): worldFile: ${worldFile.absolutePath}")
			FileUtils.deleteFolder(worldFile)
			Reactor.Companion.logger.info("World '$worldName' was DELETED.")
			worlds.remove(player)
			return true
		} catch (_: Throwable) {
			Reactor.Companion.logger.info("Whoa, not sure what happened here!")
			return false
		}
	}

	fun purge() {
		for (world in worlds) {
			deleteWorld(world)
		}
	}
}
