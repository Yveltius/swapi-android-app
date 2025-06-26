package com.yveltius.swapicore.entity.http

internal interface HttpClient {
    suspend fun getJson(urlString: String): Result<String>
}