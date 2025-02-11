package com.nuutrai.reactor.util;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;
import java.util.Map;

public class MultiTypeMap {

    private final Map<String, byte[]> map = Maps.newHashMap();

    public boolean add(String key, Object value) {
        if (value instanceof Serializable s) {
            map.put(key, SerializationUtils.serialize(s));
            return true;
        }
        return false;
    }

    @SuppressWarnings("unchecked")
    public <T extends Serializable> T get(String key, Class<? extends T> clazz) {
        Object value = SerializationUtils.deserialize(map.get(key));

        if (clazz.isInstance(value)) {
            return (T) value;
        }

        return null;
    }

}
