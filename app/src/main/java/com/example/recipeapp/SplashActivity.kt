package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import com.example.recipeapp.database.RecipeDatabase
import com.example.recipeapp.entities.Category
import com.example.recipeapp.entities.Meal
import com.example.recipeapp.entities.MealsItems
import com.example.recipeapp.interfaces.GetDataService
import com.example.recipeapp.retrofitclient.RetrofitClientInstance
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : BaseActivity(), EasyPermissions.RationaleCallbacks, EasyPermissions.PermissionCallbacks {
    private var READ_STORAGE_PERM = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        readStorageTask()

        val button = findViewById<View>(R.id.btnGetStarted)
        button.setOnClickListener {
            var intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun getCategories() {
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getCategoryList()
        call.enqueue(object : Callback<Category>{
            override fun onFailure(call: Call<Category>, t: Throwable) {
                Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse (
                call: Call<Category>,
                response: Response<Category>
            ){
                for (arr in response.body()!!.categoryItems!!) {
                    getMeal(arr.strcategory)
                }
                insertDataIntoRoomDb(response.body())
            }
        })
    }

    fun getMeal(categoryName: String) {
        val service = RetrofitClientInstance.retrofitInstance!!.create(GetDataService::class.java)
        val call = service.getMealList(categoryName)
        call.enqueue(object : Callback<Meal>{
            override fun onFailure(call: Call<Meal>, t: Throwable) {
                val lder = findViewById<ProgressBar>(R.id.loader)
                lder.visibility = View.INVISIBLE
                Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse (
                call: Call<Meal>,
                response: Response<Meal>
            ){
                insertMealDataIntoRoomDb(categoryName, response.body())
            }
        })
    }

    fun insertDataIntoRoomDb(category: Category?) {
        launch {
            this.let {
                for (arr in category!!.categoryItems!!) {
                    RecipeDatabase.getDatabase(this@SplashActivity)
                        .recipeDao().insertCategory(arr)
                }
            }
        }
    }

    fun insertMealDataIntoRoomDb(categoryName: String, meal: Meal?) {
        launch {
            this.let {
                for (arr in meal!!.mealsItem!!) {
                    var mealItemModel = MealsItems(
                        arr.id,
                        arr.idMeal,
                        categoryName,
                        arr.strMeal,
                        arr.strMealThumb
                    )
                    RecipeDatabase.getDatabase(this@SplashActivity)
                        .recipeDao().insertMeal(mealItemModel)
                    Log.d("mealData", arr.toString())
                }
                val getStartedBtn = findViewById<Button>(R.id.btnGetStarted)
                getStartedBtn.visibility = View.VISIBLE
            }
        }
    }

    fun clearDataBase() {
        launch {
            this.let {
                RecipeDatabase.getDatabase(this@SplashActivity).recipeDao().clearDb()
            }
        }
    }

    private fun hasReadStoragePermission():Boolean {
        return EasyPermissions.hasPermissions(this, android.Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private fun readStorageTask() {
        if (hasReadStoragePermission()) {
            clearDataBase()
            getCategories()
        } else {
            EasyPermissions.requestPermissions(
                this,
                "This app needs access to your storage,",
                READ_STORAGE_PERM,
                android.Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }
    override fun onRationaleAccepted(requestCode: Int) {

    }

    override fun onRationaleDenied(requestCode: Int) {

    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this,perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}