package com.example.recipeapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.recipeapp.entities.Category
import com.example.recipeapp.entities.CategoryItems
import com.example.recipeapp.entities.Recipes

@Dao
interface RecipeDao {

    @Query("SELECT * FROM CategoryItems ORDER BY id DESC")
    suspend fun getAllCategory(): List<CategoryItems>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCategory (categoryItems: CategoryItems?)

    @Query("DELETE FROM categoryitems")
    suspend fun clearDb()
}