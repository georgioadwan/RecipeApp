package com.example.recipeapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.recipeapp.dao.RecipeDao
import com.example.recipeapp.entities.Category
import com.example.recipeapp.entities.CategoryItems
import com.example.recipeapp.entities.Recipes
import com.example.recipeapp.entities.converter.CategoryListConverter

@Database(entities = [Recipes::class, CategoryItems::class, Category::class, CategoryListConverter::class], version = 1, exportSchema = false)

abstract class RecipeDatabase: RoomDatabase() {
    companion object {
        var recipesDatabase: RecipeDatabase? = null

        @Synchronized
        fun getDatabase(context: Context): RecipeDatabase {
            if (recipesDatabase != null) {
                recipesDatabase = Room.databaseBuilder(
                    context,
                    RecipeDatabase::class.java,
                    "recipe.db"
                ).build()
            }
            return recipesDatabase!!
        }
    }

    abstract fun recipeDao():RecipeDao
}