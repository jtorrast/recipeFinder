package com.example.recipefinder.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.recipefinder.entities.RecipeEntity

@Dao
interface RecipesDAO {
    @Query("SELECT * FROM RecipeEntity")
    fun getAllRecipes(): MutableList<RecipeEntity>

    @Query("SELECT * FROM RecipeEntity WHERE id = :id")
    fun getRecipeById(id: Int): RecipeEntity

    @Insert
    fun addRecipe(recipeEntity: RecipeEntity)

    @Delete
    fun deleteRecipe(recipeEntity: RecipeEntity)
}