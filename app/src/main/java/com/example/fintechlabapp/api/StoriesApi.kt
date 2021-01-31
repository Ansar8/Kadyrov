package com.example.fintechlabapp.api

import com.example.fintechlabapp.BuildConfig
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Path

interface StoriesApi {

    @GET("{type}/{page}?json=true")
    suspend fun getStories(@Path("type") type: String, @Path("page") page: Int): StoryListResponse

    @GET("random?json=true")
    suspend fun getRandomStory(): StoryResponse


    companion object {
        private val json = Json { ignoreUnknownKeys = true }

        @Suppress("EXPERIMENTAL_API_USAGE")
        fun create(): StoriesApi {
            val client = OkHttpClient().newBuilder()
                .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                .build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
                .create(StoriesApi::class.java)
        }
    }
}