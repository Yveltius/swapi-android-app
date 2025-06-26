package com.yveltius.swapicore

import com.yveltius.swapicore.data.person.JavaPersonService
import kotlinx.coroutines.runBlocking

fun main(args: Array<String>) {
    runBlocking {
        JavaPersonService().getPeople()
            .onSuccess {
                println(it.first().toJsonString())
            }.onFailure {
                println(it)
            }
    }
}