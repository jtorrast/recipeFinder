package com.example.recipefinder.fragments

import com.example.recipefinder.models.ModelsMeals
import com.example.recipefinder.pojos.MealPojo

interface MealListener {

    fun onmealSelected(meal: MealPojo)
}