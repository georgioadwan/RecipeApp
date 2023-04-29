package com.example.recipeapp.interfaces

import androidx.room.Query
import com.example.recipeapp.entities.Category
import com.example.recipeapp.entities.Meal
import retrofit2.Call
import retrofit2.http.GET

interface GetDataService {
    @GET("categories.php")
    fun getCategoryList(): Call<Category>

    @GET("filter.php")
    fun getMealList(@Query("c") category: String): Call<Meal>
}