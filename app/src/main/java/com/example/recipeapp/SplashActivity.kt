package com.example.recipeapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.recipeapp.entities.Category
import com.example.recipeapp.interfaces.GetDataService
import com.example.recipeapp.retrofitclient.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.CacheResponse

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val button = findViewById<View>(R.id.btnGetStarted)
        button.setOnClickListener {
            var intent = Intent(this@SplashActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    fun getCategories() {
        val service = RetrofitClientInstance.retrofitInstance.create(GetDataService::class.java)
        val call = service.getCategoryList()
        call.enqueue(object : Callback<List<Category>>{
            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                val loader = findViewById<ProgressBar>(R.id.loader)
                loader.visibility = View.INVISIBLE
                Toast.makeText(this@SplashActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse (
                call: Call<List<Category>>,
                response: Response<List<Category>>
            ){
                insertDataIntoRoomDb(response.body())
            }
        })
    }

    fun insertDataIntoRoomDb(category: List<Category>) {

    }
}