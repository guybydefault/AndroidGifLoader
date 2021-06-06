package ru.guybydefault.minin.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object Retrofit {
    const val BASE_URL = "http://developerslife.ru"

    val retrofit: Retrofit
    val imagesApi: ImagesApi

    init {
        val client = OkHttpClient.Builder().build()

        retrofit = Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()

        imagesApi = retrofit.create(ImagesApi::class.java)
    }
}
