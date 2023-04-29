package com.example.recipeapp.interfaces

import com.example.recipeapp.entities.Category
import com.example.recipeapp.entities.Meal
import com.example.recipeapp.entities.MealResponse
import retrofit2.Call
import retrofit2.http.GET

interface GetDataService {
    @GET("categories.php")
    fun getCategoryList(): Call<Category>

    @GET("filter.php")
    fun getMealList(@retrofit2.http.Query("c") category: String): Call<Meal>

    @GET("lookup.php")
    fun getSpecificItem(@retrofit2.http.Query("i") id: Int): Call<MealResponse>
}