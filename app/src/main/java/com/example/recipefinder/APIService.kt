package com.example.recipefinder

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getCategories(@Url url: String): Response<Models.ApiResponse>



}