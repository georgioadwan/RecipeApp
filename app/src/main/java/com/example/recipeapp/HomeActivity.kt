package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.adapter.MainCategoryAdapter
import com.example.recipeapp.adapter.SubCategoryAdapter
import com.example.recipeapp.database.RecipeDatabase
import com.example.recipeapp.entities.CategoryItems
import com.example.recipeapp.entities.MealsItems
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HomeActivity : BaseActivity() {
    var arrMainCategory = ArrayList<CategoryItems>()
    var arrSubCategory = ArrayList<MealsItems>()

    var mainCategoryAdapter = MainCategoryAdapter()
    var subCategoryAdapter = SubCategoryAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        renderMainCategory()

//        val catRe = findViewById<View>(R.id.rv_main_category) as RecyclerView
//        catRe.adapter = mainCategoryAdapter
        mainCategoryAdapter.setClickListener(onClicked)
        subCategoryAdapter.setClickListener(onClickedSubItem)

    }

    private val onClicked = object : MainCategoryAdapter.OnItemClickListener {
        override fun onClicked(categoryName: String) {
            getMealDataFromDb(categoryName)
        }
    }

    private val onClickedSubItem = object : SubCategoryAdapter.OnItemClickListener {
        override fun onClicked(id: String) {
            var intent = Intent(this@HomeActivity, DetailActivity::class.java)
            intent.putExtra("id", id)
            startActivity(intent)
        }
    }

    private fun renderMainCategory () {
        val arrMainCategory = ArrayList<CategoryItems>(
        )
        val c = CategoryItems(1,"123","hdska", "hdlkjd", "kjshdkjhskjsdkcnkj")
        arrMainCategory.add(c)
        arrMainCategory.add(c)


        mainCategoryAdapter.setData(arrMainCategory)
        val rvMC = findViewById<RecyclerView>(R.id.rv_main_category)
        rvMC.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
        rvMC.adapter = mainCategoryAdapter
    }

    private suspend fun getDataFromDb() : ArrayList<CategoryItems> {
//        launch {
//            this.let {
                var cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getAllCategory()
                arrMainCategory = cat as ArrayList<CategoryItems>
                arrMainCategory.reverse()
                return arrMainCategory
                //getMealDataFromDb(arrMainCategory[0].strcategory)
//            }
//        }
    }

    private fun getMealDataFromDb(categoryName:String) {
        val tvCat = findViewById<TextView>(R.id.tvCategory)
        tvCat.text = "$categoryName Category"
        launch {
            this.let {
                var cat = RecipeDatabase.getDatabase(this@HomeActivity).recipeDao().getSpecificMealList(categoryName)
                arrSubCategory = cat as ArrayList<MealsItems>
                subCategoryAdapter.setData(arrSubCategory)
                val rvSC = findViewById<RecyclerView>(R.id.rv_sub_category)
                rvSC.layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
                rvSC.adapter = subCategoryAdapter
            }

        }
    }
}