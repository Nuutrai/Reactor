@file:Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS", "UnstableApiUsage")

package com.nuutrai.reactor

import com.google.common.reflect.ClassPath
import com.nuutrai.reactor.commands.RegisterCommands
import com.nuutrai.reactor.entity.Sellable
import com.nuutrai.reactor.item.Buyable
import com.nuutrai.reactor.tick.Ticker
import com.nuutrai.reactor.world.WorldManager
import io.papermc.paper.plugin.lifecycle.event.LifecycleEventManager
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin
import java.io.File
import java.io.IOException
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.util.logging.Logger

/**
 * TODO:
 *
 *
 * Add breaking cells and others
 *
 *
 * Fix throwing/dropping items
 *
 *
 * Add check for player's world before attempting place
 *
 *
 * Add check to make sure there aren't any entities in the player's place area
 *
 *
 * Add check for y level cause I don't want to do the 3d thing
 *
 *
 * Make everything work like..
 *
 *
 * Add Raytracing
 *
 *
 * Asynchronous entity ticking (REMEMBER NO BUKKIT API IN ASYNC)
 *
 *
 * Add list of all current entities (map with VecLoc) for easy player lookup
 *
 *
 * Look into making more items
 */
class Reactor : JavaPlugin() {
	var ticker: Ticker? = null

	override fun onEnable() {
		// Plugin startup logic

		instance = this
		Companion.logger = this.logger
		Companion.dataFolder = this.dataFolder
		manager = instance.lifecycleManager
		ticker = Ticker()

		Companion.logger.info("Reactor startup initiated")

		ensureDataFolder()

		Companion.logger.info("Data folder: " + Companion.dataFolder)

		WorldManager.init()

		try {
			registerListeners("com.nuutrai.reactor.listeners")
		} catch (_: IOException) {
			Companion.logger.severe("Something went horribly wrong whilst loading events!")
		}

		try {
			registerReactorElements("item", Buyable::class.java)
		} catch (_: IOException) {
			Companion.logger.severe("Something went horribly wrong whilst loading buyables!")
		}

		try {
			registerReactorElements("entity", Sellable::class.java)
		} catch (_: IOException) {
			Companion.logger.severe("Something went horribly wrong whilst loading sellables!")
		}

		try {
			RegisterCommands.load()
		} catch (_: InvocationTargetException) {
			Companion.logger.severe("Something went incredibly wrong whist loading commands!")
		} catch (_: IllegalAccessException) {
			Companion.logger.severe("Something went incredibly wrong whist loading commands!")
		}

		Bukkit.getScheduler().runTaskTimerAsynchronously(instance!!, Runnable {
			ticker!!.tick()
		}, 1, 0)

		WorldManager.purge()

		Companion.logger.info("Reactor startup complete")
	}

	override fun onDisable() {
		WorldManager.purge()
	}

	private fun ensureDataFolder() {
		if (dataFolder.exists()) return
		dataFolder.mkdir()
	}

	@Throws(IOException::class)
	private fun <T> registerReactorElements(subpackageName: String, parentClass: Class<T>) {
		val classPath = ClassPath.from(this.classLoader)

		val packageName = "com.nuutrai.reactor.$subpackageName.impl"

		for (classInfo in classPath.getTopLevelClassesRecursive(packageName)) {
			val clazz = classInfo.load()

			if (parentClass.isAssignableFrom(clazz) && clazz != parentClass) {
				try {
					val constructor: Constructor<*> = clazz.getDeclaredConstructor()
					constructor.setAccessible(true)
					val instance = constructor.newInstance()

					parentClass.getMethod("add", parentClass).invoke(null, instance)

					Companion.logger.info("Registered " + parentClass.getSimpleName() + ": " + clazz.getSimpleName())
				} catch (e: NoSuchMethodException) {
					Companion.logger.severe("Failed to register " + parentClass.getSimpleName() + ": " + clazz.getSimpleName())
					e.printStackTrace()
				} catch (e: InstantiationException) {
					Companion.logger.severe("Failed to register " + parentClass.getSimpleName() + ": " + clazz.getSimpleName())
					e.printStackTrace()
				} catch (e: IllegalAccessException) {
					Companion.logger.severe("Failed to register " + parentClass.getSimpleName() + ": " + clazz.getSimpleName())
					e.printStackTrace()
				} catch (e: InvocationTargetException) {
					Companion.logger.severe("Failed to register " + parentClass.getSimpleName() + ": " + clazz.getSimpleName())
					e.printStackTrace()
				}
			}
		}
	}

	@Throws(IOException::class)
	private fun registerListeners(packageName: String) {
		// Get all classes in the specified package using Guava's ClassPath
		val classPath = ClassPath.from(this.classLoader)
		Companion.logger.info(classPath.getTopLevelClassesRecursive(packageName).toString())
		Companion.logger.info("" + classPath.getTopLevelClassesRecursive(packageName).size)
		for (classInfo in classPath.getTopLevelClassesRecursive(packageName)) {
			val clazz = classInfo.load()

			// Check if the class is a subclass of Listener
			if (Listener::class.java.isAssignableFrom(clazz)) {
				try {
					// Ensure the class has a no-arg constructor
					val constructor: Constructor<*> = clazz.getDeclaredConstructor()
					constructor.setAccessible(true)
					val listener = constructor.newInstance() as Listener

					// Register the listener with Bukkit
					server.pluginManager.registerEvents(listener, this)
					Companion.logger.info("Registered listener: " + clazz.getSimpleName())
				} catch (e: NoSuchMethodException) {
					Companion.logger.severe("Failed to register listener: " + clazz.getSimpleName())
					e.printStackTrace()
				} catch (e: InstantiationException) {
					Companion.logger.severe("Failed to register listener: " + clazz.getSimpleName())
					e.printStackTrace()
				} catch (e: IllegalAccessException) {
					Companion.logger.severe("Failed to register listener: " + clazz.getSimpleName())
					e.printStackTrace()
				} catch (e: InvocationTargetException) {
					Companion.logger.severe("Failed to register listener: " + clazz.getSimpleName())
					e.printStackTrace()
				}
			}
		}
	}

	companion object {
        lateinit var instance: Reactor
		var HALTTICK: Boolean = false
        lateinit var logger: Logger
        lateinit var dataFolder: File
		lateinit var manager: LifecycleEventManager<Plugin>
	}
}
