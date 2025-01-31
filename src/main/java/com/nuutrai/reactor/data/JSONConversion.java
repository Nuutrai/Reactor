package com.nuutrai.reactor.data;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.nuutrai.reactor.entity.Sellable;
import com.nuutrai.reactor.util.VecLoc;

import java.util.*;

public class JSONConversion {

    public static void main(String[] args) {

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
            entry.addProperty("world", vecLoc.getWorld().toString());

            vecLocJson.add(entry);
        }

        return vecLocJson;

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
            Sellable sellable = Sellable.create(s, uuid, position.toLocation());
            sellable.setCurrentHealth(currentHealth);

            sellables.add(sellable);

        }

    }

    public static List<VecLoc> vecLocFromJson(JsonArray json) {
        List<VecLoc> vecLocs = new ArrayList<>();

        for (JsonElement vecLoc : json) {
            JsonObject vecLocObject = vecLoc.getAsJsonObject();
            int x = vecLocObject.get("x").getAsInt();
            int y = vecLocObject.get("y").getAsInt();
            int z = vecLocObject.get("z").getAsInt();
            String uuidAsString = vecLocObject.get("world").getAsString();
            UUID uuid = UUID.fromString(uuidAsString);

            vecLocs.add(new VecLoc(x, y, z, uuid));
        }

        return vecLocs;

    }

}
