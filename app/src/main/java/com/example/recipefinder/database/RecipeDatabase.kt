package com.example.recipefinder.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.recipefinder.dao.RecipesDAO
import com.example.recipefinder.entities.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 1)
abstract class RecipeDatabase: RoomDatabase() {
    abstract fun recipesDao(): RecipesDAO

}