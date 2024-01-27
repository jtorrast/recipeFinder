package com.example.recipefinder.models

import com.google.gson.annotations.SerializedName

class ModelsCategories {

    data class ApiResponseCategories(
        val categories: List<Category>
    )

    data class Category(
        @SerializedName("strCategory")
        val categoria: String
    )

}