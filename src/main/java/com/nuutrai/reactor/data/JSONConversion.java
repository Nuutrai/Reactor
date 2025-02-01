package com.nuutrai.reactor.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.player.PlayerDataWrapper;
import com.nuutrai.reactor.util.VecLoc;

import java.util.*;

import static com.nuutrai.reactor.Reactor.logger;

public class JSONConversion {

    public static JsonObject playerDataToJson(PlayerDataWrapper pd) {
        JsonObject playerDataJson = new JsonObject();

        playerDataJson.addProperty("balance", pd.getBalance());
        playerDataJson.addProperty("heat", pd.getHeat());
        playerDataJson.addProperty("power", pd.getPower());
        playerDataJson.addProperty("paused", pd.isPaused());

        HashMap<VecLoc, Sellable> entityMap = pd.getEntities();

        JsonArray locations = new JsonArray();
        JsonObject entities = new JsonObject();

        for (VecLoc vecLoc: pd.getLocations()) {
            String hash = String.valueOf(vecLoc.hashCode());
            Sellable s = entityMap.get(vecLoc);
            locations.add(vecLocToJson(Collections.singleton(vecLoc)));
            entities.add(hash, sellableToJson(Collections.singleton(s)));
        }

        playerDataJson.add("entities", entities);
        playerDataJson.add("locations", locations);

        return playerDataJson;
    }

    public static JsonArray sellableToJson(Collection<Sellable> sellableCollection) {

        JsonArray sellableJson = new JsonArray();

        for (Sellable s: sellableCollection) {
            JsonObject entry = new JsonObject();
            entry.addProperty("id", s.getId());
            entry.addProperty("player", s.getPlayer().toString());
            entry.addProperty("currentHealth", s.getCurrentHealth());
            entry.add("position", vecLocToJson(Collections.singleton(s.getPosition())));

            sellableJson.add(entry);

        }

        return sellableJson;

    }

    public static JsonArray vecLocToJson(Collection<VecLoc> vecLocs) {
        JsonArray vecLocJson = new JsonArray();

        for (VecLoc vecLoc: vecLocs) {
            JsonObject entry = new JsonObject();
            entry.addProperty("x", vecLoc.getX());
            entry.addProperty("y", vecLoc.getY());
            entry.addProperty("z", vecLoc.getZ());
            entry.addProperty("world", vecLoc.getUUID().toString());

            vecLocJson.add(entry);
        }

        return vecLocJson;

    }

    public static PlayerDataWrapper playerDataFromJson(JsonObject json) {
        int balance = json.get("balance").getAsInt();
        int heat = json.get("heat").getAsInt();
        int power = json.get("power").getAsInt();
        boolean paused = json.get("paused").getAsBoolean();

        HashMap<VecLoc, Sellable> entityMap = new HashMap<>();
        ArrayList<VecLoc> locationsSet = new ArrayList<>();

        JsonArray locationsArray = json.getAsJsonArray("locations");
        JsonObject entitiesObject = json.getAsJsonObject("entities");

        for (JsonElement locElement : locationsArray) {
            VecLoc vecLoc = vecLocFromJson(locElement.getAsJsonArray()).getFirst();
            locationsSet.add(vecLoc);

            String hash = String.valueOf(vecLoc.hashCode());
            if (entitiesObject.has(hash)) {
                Sellable sellable = sellableFromJson(entitiesObject.getAsJsonArray(hash)).getFirst();
                entityMap.put(vecLoc, sellable);
            }
        }

        return new PlayerDataWrapper(balance, entityMap, locationsSet, heat, power, paused);
    }

    public static List<Sellable> sellableFromJson(JsonArray json) {
        List<Sellable> sellables = new ArrayList<>();

        for (JsonElement sellableElement: json) {
            JsonObject sellableObject = sellableElement.getAsJsonObject();

            String id = sellableObject.get("id").getAsString();
            String uuidAsString = sellableObject.get("player").getAsString();
            UUID uuid = UUID.fromString(uuidAsString);
            double currentHealth = sellableObject.get("currentHealth").getAsDouble();
            VecLoc position = vecLocFromJson((JsonArray) sellableObject.get("position")).getFirst();

            Sellable s = Sellable.get(id);
            Sellable sellable = Sellable.create(s, uuid, position);
            sellable.setCurrentHealth(currentHealth);

            sellables.add(sellable);

        }

        return sellables;

    }

    public static List<VecLoc> vecLocFromJson(JsonArray json) {
        List<VecLoc> vecLocs = new ArrayList<>();

        for (JsonElement vecLoc : json) {
            JsonObject vecLocObject = vecLoc.getAsJsonObject();
            int x = vecLocObject.get("x").getAsInt();
            int y = vecLocObject.get("y").getAsInt();
            int z = vecLocObject.get("z").getAsInt();
            String uuidAsString = vecLocObject.get("world").getAsString();
            System.out.println(uuidAsString);
            UUID uuid = UUID.fromString(uuidAsString);

            vecLocs.add(new VecLoc(x, y, z, uuid));
        }

        return vecLocs;

    }

}
