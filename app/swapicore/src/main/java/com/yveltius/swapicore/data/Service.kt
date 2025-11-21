package com.yveltius.swapicore.data

import com.yveltius.swapicore.entity.http.HttpClient
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

internal open class Service(
    protected val httpClient: HttpClient
) {
    suspend fun <T> makeRequest(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        measureExecutionTime: Boolean = true,
        onFailureTag: String,
        onFailureMessage: String,
        block: suspend (scope: CoroutineScope) -> Result<T>,
    ): Result<T> {
        return withContext(dispatcher) {
            try {
                val startTime = System.nanoTime()
                val result = block(this@withContext).getOrThrow()
                val endTime = System.nanoTime()

                if (measureExecutionTime) {
                    val elapsedTime = endTime - startTime
                    println("Execution time: ${elapsedTime / 1_000_000_000.0}s -> ${elapsedTime}ns")
                }
                // log result
                Result.success(result)
            } catch (throwable: Throwable) {
                Result.failure(Throwable("$onFailureTag: $onFailureMessage\n${throwable.message}"))
            }
        }
    }
}