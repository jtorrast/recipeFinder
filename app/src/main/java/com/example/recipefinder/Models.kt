package com.example.recipefinder

import com.google.gson.annotations.SerializedName

class Models {

    data class ApiResponse(
        val categories: List<Category>
    )

    data class Category(
        @SerializedName("strCategory")
        val categoria: String
    )

}