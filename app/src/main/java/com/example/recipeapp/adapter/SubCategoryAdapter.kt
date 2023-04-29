package com.example.recipeapp.adapter

import com.example.recipeapp.R
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recipeapp.entities.MealsItems
import com.example.recipeapp.entities.Recipes


class SubCategoryAdapter: RecyclerView.Adapter<SubCategoryAdapter.RecipeViewHolder>() {
    var ctx : Context? = null
    var arrSubCategory = ArrayList<MealsItems>()
    class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

    fun setData(arrData : List<MealsItems>) {
        arrSubCategory = arrData as ArrayList<MealsItems>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        ctx = parent.context
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_sub_category, parent, false))
    }

    override fun getItemCount(): Int {
        return arrSubCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        class MainActivity : Activity() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.item_rv_sub_category)
                val txtDishName = findViewById<View>(R.id.tv_dish_name) as TextView
                txtDishName.text = arrSubCategory[position].strmeal
                Glide.with(ctx!!).load(arrSubCategory[position].strmealthumb).into(holder.itemView.findViewById(R.id.img_dish))
            }
        }
    }
}