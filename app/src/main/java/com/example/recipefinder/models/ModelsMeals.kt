package com.example.recipefinder.models

import com.google.gson.annotations.SerializedName

class ModelsMeals {

    data class ApiResponseMeals(
        val meals: List<Meal>
    )

    data class ApiResponseMealDetail(
        @SerializedName("meals")
        val mealDetail: List<MealDetail>
    )

    data class Meal(
        @SerializedName("strMeal")
        val mealName: String,

        @SerializedName("strMealThumb")
        val image: String,


        @SerializedName("idMeal")
        val idMeal: Int

    ){
        override fun toString(): String {
            return "Meal(id ${idMeal}-> name $mealName img: $image"
        }
    }

    data class MealDetail(
        @SerializedName("strMeal")
        val mealName: String,

        @SerializedName("strMealThumb")
        val image: String,

        @SerializedName("strYoutube")
        val youtubeLink: String
    )
}