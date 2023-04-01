package com.example.trellotask_2

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

        bindingClass.buttonToSecond.setOnClickListener {
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            startActivity(intent)
        }

        bindingClass.buttonToThird.setOnClickListener {
            val intent = Intent(this@MainActivity, ThirdActivity::class.java)
            startActivity(intent)
        }


        /*var jenyaQuestion = Data("Бездарь","Лесби","Сосед","Безмен","jenya")
        val img: String = jenyaQuestion.image
        bindingClass.imageView.setImageResource(R.drawable.jenya)*/


    }
}