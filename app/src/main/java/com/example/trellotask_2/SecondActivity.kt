package com.example.trellotask_2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trellotask_2.databinding.ActivityMainBinding
import com.example.trellotask_2.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivitySecondBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        bindingClass = ActivitySecondBinding.inflate(layoutInflater)

        setContentView(bindingClass.root)

        bindingClass.buttonFromSecondToMain.setOnClickListener {
            val intent = Intent(this@SecondActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }
}