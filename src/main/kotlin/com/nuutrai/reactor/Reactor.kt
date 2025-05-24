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
		Companion.logger = this.getLogger()
		Companion.dataFolder = this.getDataFolder()
		manager = instance!!.getLifecycleManager()
		ticker = Ticker()

		Companion.logger!!.info("Reactor startup initiated")

		ensureDataFolder()

		Companion.logger!!.info("Data folder: " + Companion.dataFolder)

		WorldManager.init()

		try {
			registerListeners("com.nuutrai.reactor.listeners")
		} catch (e: IOException) {
			Companion.logger!!.severe("Something went horribly wrong whilst loading events!")
		}

		try {
			registerReactorElements<Buyable?>("item", Buyable::class.java)
		} catch (e: IOException) {
			Companion.logger!!.severe("Something went horribly wrong whilst loading buyables!")
		}

		try {
			registerReactorElements<Sellable?>("entity", Sellable::class.java)
		} catch (e: IOException) {
			Companion.logger!!.severe("Something went horribly wrong whilst loading sellables!")
		}

		try {
			RegisterCommands.load()
		} catch (e: InvocationTargetException) {
			Companion.logger!!.severe("Something went incredibly wrong whist loading commands!")
		} catch (e: IllegalAccessException) {
			Companion.logger!!.severe("Something went incredibly wrong whist loading commands!")
		}

		Bukkit.getScheduler().runTaskTimerAsynchronously(instance!!, Runnable {
			ticker!!.tick()
		}, 1, 0)

		WorldManager.purge()

		Companion.logger!!.info("Reactor startup complete")
	}

	override fun onDisable() {
		WorldManager.purge()
	}

	private fun ensureDataFolder() {
		if (getDataFolder().exists()) return
		getDataFolder().mkdir()
	}

	@kotlin.Throws(IOException::class)
	private fun <T> registerReactorElements(subpackageName: String, parentClass: Class<T?>) {
		val classPath = ClassPath.from(this.getClassLoader())

		val packageName = "com.nuutrai.reactor." + subpackageName + ".impl"

		for (classInfo in classPath.getTopLevelClassesRecursive(packageName)) {
			val clazz = classInfo.load()

			if (parentClass.isAssignableFrom(clazz) && clazz != parentClass) {
				try {
					val constructor: Constructor<*> = clazz.getDeclaredConstructor()
					constructor.setAccessible(true)
					val instance = constructor.newInstance() as T?

					// Call the static add() method on the parent class
					parentClass.getMethod("add", parentClass).invoke(null, instance)

					Companion.logger!!.info("Registered " + parentClass.getSimpleName() + ": " + clazz.getSimpleName())
				} catch (e: NoSuchMethodException) {
					Companion.logger!!.severe("Failed to register " + parentClass.getSimpleName() + ": " + clazz.getSimpleName())
					e.printStackTrace()
				} catch (e: InstantiationException) {
					Companion.logger!!.severe("Failed to register " + parentClass.getSimpleName() + ": " + clazz.getSimpleName())
					e.printStackTrace()
				} catch (e: IllegalAccessException) {
					Companion.logger!!.severe("Failed to register " + parentClass.getSimpleName() + ": " + clazz.getSimpleName())
					e.printStackTrace()
				} catch (e: InvocationTargetException) {
					Companion.logger!!.severe("Failed to register " + parentClass.getSimpleName() + ": " + clazz.getSimpleName())
					e.printStackTrace()
				}
			}
		}
	}

	@kotlin.Throws(IOException::class)
	private fun registerListeners(packageName: String) {
		// Get all classes in the specified package using Guava's ClassPath
		val classPath = ClassPath.from(this.getClassLoader())
		Companion.logger!!.info(classPath.getTopLevelClassesRecursive(packageName).toString())
		Companion.logger!!.info("" + classPath.getTopLevelClassesRecursive(packageName).size)
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
					getServer().getPluginManager().registerEvents(listener, this)
					Companion.logger!!.info("Registered listener: " + clazz.getSimpleName())
				} catch (e: NoSuchMethodException) {
					Companion.logger!!.severe("Failed to register listener: " + clazz.getSimpleName())
					e.printStackTrace()
				} catch (e: InstantiationException) {
					Companion.logger!!.severe("Failed to register listener: " + clazz.getSimpleName())
					e.printStackTrace()
				} catch (e: IllegalAccessException) {
					Companion.logger!!.severe("Failed to register listener: " + clazz.getSimpleName())
					e.printStackTrace()
				} catch (e: InvocationTargetException) {
					Companion.logger!!.severe("Failed to register listener: " + clazz.getSimpleName())
					e.printStackTrace()
				}
			}
		}
	}

	companion object {
		@kotlin.jvm.JvmField
        var instance: Reactor? = null
		var HALTTICK: Boolean = false
		@kotlin.jvm.JvmField
        var logger: Logger? = null
		@kotlin.jvm.JvmField
        var dataFolder: File? = null
		@kotlin.jvm.JvmField
        var manager: LifecycleEventManager<Plugin?>? = null
	}
}
