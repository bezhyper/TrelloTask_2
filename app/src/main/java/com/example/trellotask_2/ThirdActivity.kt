package com.example.trellotask_2

import kotlin.random.Random
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.trellotask_2.databinding.ActivitySecondBinding
import com.example.trellotask_2.databinding.ActivityThirdBinding
import com.example.trellotask_2.exercisesDataClasses.GapInSentenceData

class ThirdActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityThirdBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        bindingClass = ActivityThirdBinding.inflate(layoutInflater)

        setContentView(bindingClass.root)

        bindingClass.buttonFromThirdToMain.setOnClickListener {
            val intent = Intent(this@ThirdActivity, MainActivity::class.java)
            startActivity(intent)
        }

          val example1 = GapInSentenceData(
                  "Mario _____ essere attento con quello che dice",
                  "Deve", "dominare", "devono", "Devi"
              )



              bindingClass.textSentence.text = example1.sentence




              var answersList = mutableListOf(
                  example1.rightAnswer,
                  example1.answer1,
                  example1.answer2,
                  example1.answer3
              )


              var n = answersList.size

              var randomNumber = Random.nextInt(0, n)

              bindingClass.button11.text = answersList[randomNumber]

              n -= 1
              answersList.removeAt(randomNumber)


              randomNumber = Random.nextInt(0, n)

              bindingClass.button22.text = answersList[randomNumber]

              n -= 1
              answersList.removeAt(randomNumber)


              randomNumber = Random.nextInt(0, n)

              bindingClass.button33.text = answersList[randomNumber]

              n -= 1

              answersList.removeAt(randomNumber)


              bindingClass.button44.text = answersList[0]





    }
}