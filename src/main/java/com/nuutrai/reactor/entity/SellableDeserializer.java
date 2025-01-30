package com.nuutrai.reactor.entity;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

public class SellableDeserializer {
}


public class Id<T> {
    private final Class<T> clazz;
    private final long value;
    public Id(Class<T> clazz, long value) {
        this.clazz = clazz;
        this.value = value;
    }
    public long getValue() {
        return value;
    }
}


class IdDeserializer implements JsonDeserializer<Id> {

    @Override
    public Id deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        return new Id((Class) typeOfT, id.getValue());
    }

}