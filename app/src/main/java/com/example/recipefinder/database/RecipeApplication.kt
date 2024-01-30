package com.example.recipefinder.database

import android.app.Application
import androidx.room.Room

class RecipeApplication: Application() {
    companion object{
        lateinit var database: RecipeDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, RecipeDatabase::class.java, "RecipesDatabase").build()
    }
}