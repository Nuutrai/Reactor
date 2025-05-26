package com.nuutrai.reactor.data

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.nuutrai.reactor.entity.Sellable
import com.nuutrai.reactor.entity.Sellable.Companion.get
import com.nuutrai.reactor.player.PlayerDataWrapper
import com.nuutrai.reactor.util.VecLoc
import org.bukkit.entity.Player

object JSONConversion {

	fun playerDataToJson(pd: PlayerDataWrapper): JsonObject {
		val playerDataJson = JsonObject()

		playerDataJson.addProperty("balance", pd.balance)
		playerDataJson.addProperty("heat", pd.heat)
		playerDataJson.addProperty("power", pd.power)

		val entityMap = pd.entities

		val locations = JsonArray()
		val entities = JsonObject()

		for (vecLoc in pd.locations!!) {
			val hash = vecLoc.hashCode().toString()
			val s = entityMap!!.get(vecLoc) ?: continue
			locations.add(vecLocToJson(vecLoc!!))
			entities.add(hash, JSONConversion.sellableToJson(s))
		}

		playerDataJson.add("entities", entities)
		playerDataJson.add("locations", locations)

		return playerDataJson
	}

	fun sellableToJson(sellable: Sellable): JsonArray {
		val sellableJson = JsonArray()

		val entry = JsonObject()
		entry.addProperty("id", sellable.id)
		//            entry.addProperty("player", s.getPlayer().toString());
		entry.addProperty("currentHealth", sellable.currentHealth)
		entry.add("position", vecLocToJson(sellable.position!!))

		sellableJson.add(entry)

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

		val entityMap = HashMap<VecLoc?, Sellable?>()
		val locationsSet = ArrayList<VecLoc?>()

		val locationsArray = json.getAsJsonArray("locations")
		val entitiesObject = json.getAsJsonObject("entities")

		for (locElement in locationsArray) {
			val vecLoc: VecLoc = vecLocFromJson(locElement.getAsJsonArray(), player)
			locationsSet.add(vecLoc)

			val hash = vecLoc.hashCode().toString()
			if (entitiesObject.has(hash)) {
				val sellable: Sellable? = sellableFromJson(entitiesObject.getAsJsonArray(hash), player)
				entityMap.put(vecLoc, sellable)
			}
		}

		return PlayerDataWrapper(balance, entityMap, locationsSet, heat, power)
	}

	fun sellableFromJson(sellableElement: JsonElement, player: Player): Sellable {

		val sellableObject = sellableElement.getAsJsonObject()

		val id = sellableObject.get("id").asString
		//            String uuidAsString = sellableObject.get("player").getAsString();
//            UUID uuid = UUID.fromString(uuidAsString);
		val currentHealth = sellableObject.get("currentHealth").asDouble
		val position: VecLoc? =
			vecLocFromJson(sellableObject.get("position"), player)

		val s = get(id)
		val sellable = Sellable.create(s!!, player, position)
		sellable.currentHealth = currentHealth


		return sellable
	}

	fun vecLocFromJson(vecLoc: JsonElement, player: Player): VecLoc {

			val vecLocObject = vecLoc.getAsJsonObject()
			val x = vecLocObject.get("x").asInt
			val y = vecLocObject.get("y").asInt
			val z = vecLocObject.get("z").asInt

			//            String uuidAsString = vecLocObject.get("world").getAsString();
//            UUID uuid = UUID.fromString(uuidAsString);

		return VecLoc(x, y, z, player.uniqueId)
	}
}
