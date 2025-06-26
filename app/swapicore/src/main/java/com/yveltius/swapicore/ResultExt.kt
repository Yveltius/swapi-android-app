package com.yveltius.swapicore

inline fun <reified T> Result<T>.finally(block: () -> Unit) {
    block()
}