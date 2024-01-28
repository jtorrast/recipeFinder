package com.example.recipefinder.adapters

import com.example.recipefinder.models.ModelsMeals
import com.example.recipefinder.pojos.MealPojo

interface OnClickListener {
    fun onClick(meal: MealPojo)
}