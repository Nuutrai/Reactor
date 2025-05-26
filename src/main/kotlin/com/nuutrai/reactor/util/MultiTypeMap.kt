@file:Suppress("UNCHECKED_CAST")

package com.nuutrai.reactor.util

import com.google.common.collect.Maps
import org.apache.commons.lang3.SerializationUtils
import java.io.Serializable

class MultiTypeMap {
	private val map: MutableMap<String?, ByteArray?> = Maps.newHashMap()

	fun add(key: String?, value: Any?): Boolean {
		if (value is Serializable) {
			map.put(key, SerializationUtils.serialize(value))
			return true
		}
		return false
	}

	fun <T : Serializable?> get(key: String?, clazz: Class<out T?>): T? {
		val value = SerializationUtils.deserialize<Any?>(map[key])

		if (clazz.isInstance(value)) {
			return value as T
		}

		return null
	}
}
