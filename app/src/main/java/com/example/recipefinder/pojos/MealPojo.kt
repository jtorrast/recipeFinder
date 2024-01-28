package com.example.recipefinder.pojos

import java.io.Serializable

class MealPojo(private var mealName: String ,private val image: String,private val idMeal: Int ): Serializable {

    fun getMealName(): String{
        return mealName
    }

    fun getImage(): String{
        return image
    }

    fun getIdMeal(): Int{
        return idMeal
    }

}