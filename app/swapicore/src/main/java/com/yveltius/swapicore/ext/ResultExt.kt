package com.yveltius.swapicore.ext

inline fun <reified T> Result<T>.finally(block: () -> Unit) {
    block()
}