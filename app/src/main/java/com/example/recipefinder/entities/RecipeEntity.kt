package com.example.recipefinder.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class RecipeEntity(
    @PrimaryKey
    val id: Int,
    val mealName: String?,
    val image: String?,
    val youtubeLink: String?
)
