@file:Suppress("UNCHECKED_CAST")

package com.nuutrai.reactor.util

import org.apache.commons.lang3.SerializationUtils
import java.io.Serializable
import kotlin.reflect.KClass

class MultiTypeMap {
	private val map: MutableMap<String, ByteArray> = mutableMapOf()

	fun add(key: String, value: Any): Boolean {
		if (value is Serializable) {
			map.put(key, SerializationUtils.serialize(value))
			return true
		}
		return false
	}

	fun <T : Serializable> get(key: String, clazz: KClass<out T>): T? {
		val value = SerializationUtils.deserialize<T>(map[key])

		if (clazz.isInstance(value)) {
			return value as T
		}

		return null
	}
}
