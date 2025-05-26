package com.nuutrai.reactor.util

class Settable<T> {
	private var value: T? = null

	fun set(value: T?) {
		this.value = value
	}

	fun get(): T? {
		return value
	}

	val isNull: Boolean
		get() = value == null
}
