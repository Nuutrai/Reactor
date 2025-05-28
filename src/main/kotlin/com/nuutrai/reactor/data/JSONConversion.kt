package com.nuutrai.reactor.data

import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.nuutrai.reactor.entity.Sellable
import com.nuutrai.reactor.player.PlayerDataWrapper
import com.nuutrai.reactor.util.VecLoc
import org.bukkit.entity.Player

object JSONConversion {
	fun playerDataToJson(pd: PlayerDataWrapper): JsonObject {
		val playerDataJson = JsonObject()

		playerDataJson.addProperty("balance", pd.balance)
		playerDataJson.addProperty("heat", pd.heat)
		playerDataJson.addProperty("power", pd.power)

		val entityMap: MutableMap<VecLoc, Sellable> = pd.entities

		val locations = JsonArray()
		val entities = JsonObject()

		for (vecLoc in pd.locations) {
			val hash = vecLoc.hashCode().toString()
			val s = entityMap[vecLoc]
			locations.add(vecLocToJson(vecLoc))
			entities.add(hash, sellableToJson(mutableSetOf(s!!)))
		}

		playerDataJson.add("entities", entities)
		playerDataJson.add("locations", locations)

		return playerDataJson
	}

	fun sellableToJson(sellableCollection: MutableCollection<Sellable>): JsonArray {
		val sellableJson = JsonArray()

		for (s in sellableCollection) {
			val entry = JsonObject()
			entry.addProperty("id", s.id)
			//            entry.addProperty("player", s.getPlayer().toString());
			entry.addProperty("currentHealth", s.currentHealth)
			entry.add("position", vecLocToJson(s.position!!))

			sellableJson.add(entry)
		}

		return sellableJson
	}

	fun vecLocToJson(vecLoc: VecLoc): JsonArray {
		val vecLocJson = JsonArray()

		val entry = JsonObject()
		entry.addProperty("x", vecLoc.x)
		entry.addProperty("y", vecLoc.y)
		entry.addProperty("z", vecLoc.z)

		//        entry.addProperty("world", vecLoc.getUUID().toString());
		vecLocJson.add(entry)

		return vecLocJson
	}

	fun playerDataFromJson(json: JsonObject, player: Player): PlayerDataWrapper {
		val balance = json.get("balance").asInt
		val heat = json.get("heat").asInt
		val power = json.get("power").asInt

		val entityMap = HashMap<VecLoc, Sellable>()
		val locationsSet = ArrayList<VecLoc>()

		val locationsArray = json.getAsJsonArray("locations")
		val entitiesObject = json.getAsJsonObject("entities")

		for (locElement in locationsArray) {
			val vecLoc: VecLoc = vecLocFromJson(locElement.getAsJsonArray(), player)[0]
			locationsSet.add(vecLoc)

			val hash = vecLoc.hashCode().toString()
			if (entitiesObject.has(hash)) {
				val sellables = sellableFromJson(entitiesObject.getAsJsonArray(hash), player)
				if (sellables.isEmpty()) continue
				entityMap.put(vecLoc, sellables[0])
			}
		}

		return PlayerDataWrapper(balance, entityMap, locationsSet, heat, power)
	}

	fun sellableFromJson(json: JsonArray, player: Player): MutableList<Sellable> {
		val sellables: MutableList<Sellable> = ArrayList()

		for (sellableElement in json) {
			val sellableObject = sellableElement.getAsJsonObject()

			val id = sellableObject.get("id").asString
			//            String uuidAsString = sellableObject.get("player").getAsString();
//            UUID uuid = UUID.fromString(uuidAsString);
			val currentHealth = sellableObject.get("currentHealth").asDouble
			val position: VecLoc =
				vecLocFromJson((sellableObject.get("position") as JsonArray), player)[0]

			val s = Sellable.get(id!!)
			val sellable = Sellable.create(s!!, player, position)
			sellable.currentHealth = currentHealth

			sellables.add(sellable)
		}

		return sellables
	}

	fun vecLocFromJson(json: JsonArray, player: Player): MutableList<VecLoc> {
		val vecLocs: MutableList<VecLoc> = ArrayList()

		for (vecLoc in json) {
			val vecLocObject = vecLoc.getAsJsonObject()
			val x = vecLocObject.get("x").asInt
			val y = vecLocObject.get("y").asInt
			val z = vecLocObject.get("z").asInt

			//            String uuidAsString = vecLocObject.get("world").getAsString();
//            UUID uuid = UUID.fromString(uuidAsString);
			vecLocs.add(VecLoc(x, y, z, player.uniqueId))
		}

		return vecLocs
	}
}