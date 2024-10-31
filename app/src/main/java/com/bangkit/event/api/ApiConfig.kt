package com.bangkit.event.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {

    private const val API_Link = "https://event-api.dicoding.dev/"

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(API_Link)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun create(): ApiService = retrofit.create(ApiService::class.java)
}
