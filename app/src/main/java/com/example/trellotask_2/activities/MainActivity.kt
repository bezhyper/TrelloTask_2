package com.example.trellotask_2.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trellotask_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {


    lateinit var bindingClass: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        bindingClass = ActivityMainBinding.inflate(layoutInflater)

        setContentView(bindingClass.root)

//        val retrofit = Retrofit.Builder()
//            .baseUrl("http://10.0.2.2:8000")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        val dataApi = retrofit.create(DataApi::class.java)



        bindingClass.buttonToThird.setOnClickListener {
            val intent = Intent(this@MainActivity, ThirdActivity::class.java)
            startActivity(intent)
        }


//        bindingClass.buttonGetData.setOnClickListener {
//
//            CoroutineScope(Dispatchers.IO).launch {
//
////                dataApi.postCompletedTask(8,88)
//
//
//                val exercise = dataApi.getRandomTask(3)
//
//                runOnUiThread {
//
//                    bindingClass.textTest.text = exercise.toString()
//
//
//                }
//
//
//            }

        }


    }



