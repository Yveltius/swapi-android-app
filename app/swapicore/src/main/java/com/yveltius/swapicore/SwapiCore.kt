package com.yveltius.swapicore

import com.yveltius.swapicore.data.person.JavaPersonService
import com.yveltius.swapicore.ext.toJsonString
import kotlinx.coroutines.runBlocking

// this is just for testing SwapiCore functionality without needing to run the UI side
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