package com.yveltius.swapicore.entity.http

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URI

internal class JavaHttpClient : HttpClient {
    override suspend fun getJson(urlString: String): Result<String> {
        return withContext(Dispatchers.IO) {
            var connection: HttpURLConnection? = null
            try {
                val url = URI(urlString).toURL()
                connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 15_000
                connection.readTimeout = 15_000
                connection.setRequestProperty("Accept", "application/json")

                connection.connect()

                if (connection.responseCode != HttpURLConnection.HTTP_OK) {
                    val errorMessage = connection.errorStream.bufferedReader().use { reader ->
                        reader.readText()
                    }
                    throw Throwable("(${connection.responseCode}, ${connection.responseMessage}): $errorMessage")
                }

                val content = connection.inputStream.bufferedReader().use { reader ->
                    reader.readText()
                }

                Result.success(content)
            } catch (throwable: Throwable) {
                Result.failure(Throwable("Failed to accomplish task.\n${throwable.message}"))
            } finally {
                connection?.disconnect()
            }
        }
    }
}