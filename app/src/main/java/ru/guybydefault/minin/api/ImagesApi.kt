package ru.guybydefault.minin.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import ru.guybydefault.minin.domain.ImagesResponse

interface ImagesApi {

    @GET("/hot/{page}?json=true")
    suspend fun getHotImages(@Path("page") page: Int): Response<ImagesResponse>

    @GET("/latest/{page}?json=true")
    suspend fun getLatestImages(@Path("page") page: Int) : Response<ImagesResponse>

    @GET("/top/{page}?json=true")
    suspend fun getTopImages(@Path("page") page: Int) : Response<ImagesResponse>
}