package com.yveltius.swapicore.ext

import kotlinx.serialization.json.Json

inline fun <reified T> String.fromJsonString(): Result<T> {
    return try {
        Result.success(Json.decodeFromString<T>(this))
    } catch (throwable: Throwable) {
        Result.failure(Throwable("Failed to deserialize string.\n${throwable.message}"))
    }
}

inline fun <reified T> T.toJsonString(): String {
    return Json.encodeToString(this)
}