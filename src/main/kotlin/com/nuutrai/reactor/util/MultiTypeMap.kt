@file:Suppress("UNCHECKED_CAST")

package com.nuutrai.reactor.util

import org.apache.commons.lang3.SerializationUtils
import java.io.Serializable
import kotlin.reflect.KClass

class MultiTypeMap {
	val map: MutableMap<String, ByteArray> = mutableMapOf()
		private set

	fun add(key: String, value: Any): Boolean {
		if (value is Serializable) {
			map.put(key, SerializationUtils.serialize(value))
			return true
		}
		return false
	}

	inline operator fun <reified T : Serializable> get(key: String): T {
		val value = SerializationUtils.deserialize<T>(map[key])

		if (value == null) {
			throw IllegalArgumentException("Object from MultiTypeMap is null: $key")
		}
		if (T::class.isInstance(value)) {
			return value
		}
		throw IllegalArgumentException("Object from MultiTypeMap does not exist: $key")
	}

	inline operator fun <reified T : Serializable> get(key: String, clazz: KClass<out T>): T {
		val value = SerializationUtils.deserialize<T>(map[key])

		if (value == null) {
			throw IllegalArgumentException("Object from MultiTypeMap is null: $key")
		}
		if (clazz.isInstance(value)) {
			return value
		}
		throw IllegalArgumentException("Object from MultiTypeMap does not exist: $key")
	}

}
