package com.practice.bank.common.json

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.json.Json

object JsonUtil {
    private val json = Json {
        prettyPrint = true
        ignoreUnknownKeys = true
    }

    fun <T> encodeToJson(v: T, serializer: SerializationStrategy<T>): String {
        return json.encodeToString(serializer, v)
    }

    fun <T> decodeFromJson(v: String, deserializer: DeserializationStrategy<T>): T {
        return json.decodeFromString(deserializer, v)
    }
}