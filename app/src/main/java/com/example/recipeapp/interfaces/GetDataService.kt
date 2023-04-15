package com.example.recipeapp.interfaces

import com.example.recipeapp.entities.Category
import retrofit2.Call
import retrofit2.http.GET

interface GetDataService {
    @GET("/cateogories.php")
    fun getCategoryList(): Call<List<Category>>
}