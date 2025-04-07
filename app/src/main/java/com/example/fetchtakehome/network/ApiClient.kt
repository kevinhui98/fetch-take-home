package com.example.fetchtakehome.network

import android.util.Log
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import com.example.fetchtakehome.model.Item
import okhttp3.OkHttpClient
import okhttp3.Request
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object ApiClient {
    private val ktorClient = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }
    private val okHttpClient = OkHttpClient()
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun fetchItems(): List<Item> {
        return try {
            ktorClient.get("https://fetch-hiring.s3.amazonaws.com/hiringjson").body()
        } catch (e: Exception) {
            Log.e("ApiClient", "Ktor fetch failed, falling back to OkHttp", e)

            try {
                withContext(Dispatchers.IO) {
                    val request = Request.Builder()
                        .url("https://fetch-hiring.s3.amazonaws.com/hiring.json")
                        .build()

                    val response = okHttpClient.newCall(request).execute()
                    Log.i("fetchItem", "$response")
                    if (response.isSuccessful) {
                        val body = response.body?.string()
                        Log.i("fetchItem", "$body")
                        if (body != null) {
                            json.decodeFromString<List<Item>>(body)
                        } else {
                            emptyList()
                        }
                    } else {
                        Log.e("ApiClient", "OkHttp failed with code: ${response.code}")
                        emptyList()
                    }
                }
            } catch (e: Exception) {
                Log.e("ApiClient", "OkHttp fallback also failed", e)
                emptyList()
            }
        }
    }
}

