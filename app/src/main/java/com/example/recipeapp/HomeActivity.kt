package com.example.recipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.adapter.MainCategoryAdapter
import com.example.recipeapp.adapter.SubCategoryAdapter
import com.example.recipeapp.entities.Recipes

class HomeActivity : BaseActivity() {
    var arrMainCategory = ArrayList<Recipes>()
    var arrSubCategory = ArrayList<Recipes>()

    var mainCategoryAdapter = MainCategoryAdapter()
    var subCategoryAdapter = SubCategoryAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //Temp data to test recycler view
        arrMainCategory.add(Recipes(1,"Beef"))
        arrMainCategory.add(Recipes(2, "Chicken"))
        arrMainCategory.add(Recipes(3, "Dessert"))
        arrMainCategory.add(Recipes(4, "Lamb"))

        mainCategoryAdapter.setData(arrMainCategory)

        //Temp data to test recycler view
        arrSubCategory.add(Recipes(1,"Beef and mustard pie"))
        arrSubCategory.add(Recipes(2, "Chicken and mushroom hotspot"))
        arrSubCategory.add(Recipes(3, "Banana pancake"))
        arrSubCategory.add(Recipes(4, "Kapsalon"))

        subCategoryAdapter.setData(arrSubCategory)

        val rvMC = findViewById<RecyclerView>(R.id.rv_main_category)
        rvMC.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvMC.adapter = mainCategoryAdapter

        val rvSC = findViewById<RecyclerView>(R.id.rv_sub_category)
        rvSC.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvSC.adapter = subCategoryAdapter

    }
}