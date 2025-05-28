package com.nuutrai.reactor.data

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.nuutrai.reactor.Reactor
import com.nuutrai.reactor.data.JSONConversion.playerDataFromJson
import com.nuutrai.reactor.data.JSONConversion.playerDataToJson
import com.nuutrai.reactor.player.PlayerData
import com.nuutrai.reactor.player.PlayerDataWrapper
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File
import java.io.FileNotFoundException
import java.io.FileWriter
import java.io.IOException
import java.util.*

object DataManager {
	private val playerDataMap: MutableMap<Player, PlayerData> = mutableMapOf()

    fun get(player: Player?): PlayerData? {
		return playerDataMap[player]
	}

	fun loadPlayerData(player: Player) {
		val jsonString = readFromFile(player)

		val gson = Gson()

		val json = gson.fromJson(jsonString, JsonObject::class.java)

		val playerDataWrapper = playerDataFromJson(json, player)
		val playerData = PlayerData(playerDataWrapper)
		playerData.setPlayer(player)
		playerData.selection = ItemStack.of(Material.GRAY_STAINED_GLASS_PANE)
		playerDataMap.put(player, playerData)
	}

	fun unloadPlayerData(player: Player) {
		savePlayerData(player)
	}

	fun savePlayerData(player: Player) {
		val playerData: PlayerData = playerDataMap[player]!!
		val playerDataWrapper = PlayerDataWrapper(playerData)

		val json = playerDataToJson(playerDataWrapper)

		val gson = Gson()

		val jsonString = gson.toJson(json)
		writeToFile(player, jsonString)
	}

	// File stuff
	private fun writeToFile(player: Player, string: String) {
		try {
			val file = File(Reactor.dataFolder, player.uniqueId.toString() + ".json")

			if (!file.exists()) {
				initData(player)
			}

			val myWriter = FileWriter(file)
			myWriter.write(string)
			myWriter.close()
			Reactor.Companion.logger.info("Successfully wrote to the file.")
		} catch (_: IOException) {
			Reactor.Companion.logger.severe("An error occurred whilst writing to a file.")
		}
	}

	private fun readFromFile(player: Player): String? {
		try {
			val file = File(Reactor.dataFolder, player.uniqueId.toString() + ".json")

			if (!file.exists()) {
				initData(player)
			}

			val myReader = Scanner(file)
			val data = StringBuilder()
			while (myReader.hasNext()) {
				data.append(myReader.nextLine())
			}
			myReader.close()
			return data.toString()
		} catch (e: FileNotFoundException) {
			Reactor.Companion.logger.severe("An error occurred.")
			Reactor.Companion.logger.severe(e.message)
		}
		return null
	}

	private fun initData(player: Player) {
		val playerData = PlayerData()
		playerData.setPlayer(player)
		playerDataMap.put(player, playerData)

		try {
			val myObj = File(Reactor.dataFolder, player.uniqueId.toString() + ".json")
			if (myObj.createNewFile()) {
				Reactor.Companion.logger.info("File created: " + myObj.getName())
				savePlayerData(player)
			} else {
				Reactor.Companion.logger.info("File already exists.")
			}
		} catch (e: IOException) {
			Reactor.Companion.logger.severe("An error occurred whilst writing to a file.")
			Reactor.Companion.logger.severe(e.message)
		}
	}
}
