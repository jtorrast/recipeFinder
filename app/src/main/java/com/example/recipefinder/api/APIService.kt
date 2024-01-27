package com.example.recipefinder.api

import com.example.recipefinder.models.ModelsCategories
import com.example.recipefinder.models.ModelsMeals
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface APIService {
    @GET
    suspend fun getCategories(@Url url: String): Response<ModelsCategories.ApiResponseCategories>

    @GET
    suspend fun getMealsByCategory(@Url url: String): Response<ModelsMeals.ApiResponseMeals>



}