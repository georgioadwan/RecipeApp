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
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.recipeapp.entities.CategoryItems
import com.bumptech.glide.Glide



class MainCategoryAdapter: RecyclerView.Adapter<MainCategoryAdapter.RecipeViewHolder>() {

    var listener: OnItemClickListener? = null
    var ctx: Context? = null
    var arrMainCategory = ArrayList<CategoryItems>()

    class RecipeViewHolder(view: View): RecyclerView.ViewHolder(view) {

    }

    fun setData(arrData : List<CategoryItems>) {
        arrMainCategory = arrData as ArrayList<CategoryItems>
    }

    fun setClickListener(listener1: OnItemClickListener) {
        listener = listener1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        ctx = parent.context
        return RecipeViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_rv_main_category, parent, false))
    }

    override fun getItemCount(): Int {
        return arrMainCategory.size
    }

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        class MainActivity : Activity() {
            override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                setContentView(R.layout.item_rv_main_category)
                val txtDishName = findViewById<TextView>(R.id.tv_dish_name)
                txtDishName.setText("1, 2981038129, jdsjdklsakd, jdklsajdklsajdkla")
                txtDishName.invalidate()
                txtDishName.requestLayout()//arrMainCategory[position].strcategory
                Glide.with(ctx!!).load(arrMainCategory[position].strcategorythumb).into(holder.itemView.findViewById(R.id.img_dish))
                holder.itemView.rootView.setOnClickListener {
                    listener!!.onClicked(arrMainCategory[position].strcategory)
                }
            }
        }
    }

    interface OnItemClickListener {
        fun onClicked(categoryName:String)
    }
}