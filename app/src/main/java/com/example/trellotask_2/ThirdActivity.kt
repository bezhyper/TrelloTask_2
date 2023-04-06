package com.example.trellotask_2

import kotlin.random.Random
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

import com.example.trellotask_2.databinding.ActivityThirdBinding
import com.example.trellotask_2.exercisesDataClasses.Data
import com.example.trellotask_2.exercisesDataClasses.GapInSentenceData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ThirdActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityThirdBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingClass = ActivityThirdBinding.inflate(layoutInflater)

        setContentView(bindingClass.root)


        val retrofit = Retrofit.Builder()
            .baseUrl("http://127.0.0.1:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val dataApi = retrofit.create(DataApi::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val exercise = dataApi.getRandomTask(1)

//            runOnUiThread {
//
//
//            }
        }


//        val db = MainDb.getDb((this)) // создание базы данных

        bindingClass.buttonFromThirdToMain.setOnClickListener {
            val intent = Intent(this@ThirdActivity, MainActivity::class.java)
            startActivity(intent)
        }


//        val example1 = GapInSentenceData(
//            null,
//            "Mario _____ essere attento con quello che dice",
//            "Deve",
//            "dominare",
//            "devono",
//            "Devi",
//            "eewwfwfw"
//        )
//
//        val example2 = GapInSentenceData(
//            null,
//            "Prima di uscire, _____ studiare",
//            "Dobbiamo",
//            "devono",
//            "Devi",
//            "Devo",
//            "eewwe"
//        )
//
//
//        val examplesList = listOf(example1, example2)

//        Thread {
//
//            db.getDao().insertExerciseData(example1)
//            db.getDao().insertExerciseData(example2)
//        }.start()


        var index = 0





        layOutExercise(examplesList as MutableList<GapInSentenceData>, index)


//
        var currentButton: Button? = null







        bindingClass.button11.setOnClickListener {

            currentButton = bindingClass.button11

            fillSentencesGap(examplesList, index)

            checkAnswerAndDisplay(currentButton!!, examplesList, index)


        }







        bindingClass.button22.setOnClickListener {

            currentButton = bindingClass.button22


            val fullSentence =
                examplesList[index].sentence.replace("_____", examplesList[index].correctAnswer)

            bindingClass.textSentence.text = fullSentence


            checkAnswerAndDisplay(currentButton!!, examplesList, index)
        }



        bindingClass.button33.setOnClickListener {

            currentButton = bindingClass.button33

            val fullSentence =
                examplesList[index].sentence.replace("_____", examplesList[index].correctAnswer)

            bindingClass.textSentence.text = fullSentence



            checkAnswerAndDisplay(currentButton!!, examplesList, index)
        }



        bindingClass.button44.setOnClickListener {

            currentButton = bindingClass.button44


            val fullSentence =
                examplesList[index].sentence.replace("_____", examplesList[index].correctAnswer)

            bindingClass.textSentence.text = fullSentence



            checkAnswerAndDisplay(currentButton!!, examplesList, index)

        }






        bindingClass.nextExButton.setOnClickListener {


            if (currentButton != null) {

                bindingClass.button11.isEnabled = true
                bindingClass.button22.isEnabled = true
                bindingClass.button33.isEnabled = true
                bindingClass.button44.isEnabled = true

                if (checkArrayIndexOutOfBoundsException(examplesList, index)) {
                    currentButton!!.setBackgroundColor(Color.WHITE)
                }







                if (checkArrayIndexOutOfBoundsException(examplesList, index)) {
                    index++
                    layOutExercise(examplesList, index)
                } else {
                    bindingClass.nextExButton.isEnabled = false
                    bindingClass.answerCheck.text = "Задания закончились!"
                    lockAllButtons()

                }

                showPrevExButton()



            }
        }

    }

    private fun showPrevExButton() {
        bindingClass.buttonPrevEx.visibility = View.VISIBLE
    }

    //----------------------------------------------------------------------------------------------------
    private fun checkArrayIndexOutOfBoundsException(
        examplesList: MutableList<GapInSentenceData>,
        index: Int
    ): Boolean {
        return (index + 1) != examplesList.size
    }


    private fun fillSentencesGap(examplesList: MutableList<GapInSentenceData>, index: Int) {

        val fullSentence =
            examplesList[index].sentence.replace("_____", examplesList[index].correctAnswer)

        bindingClass.textSentence.text = fullSentence

    }

//    private fun unlockButtonsAndClearColor(currentButton: Button?) {
//        if (currentButton != null) {
//            bindingClass.button11.isEnabled = true
//            bindingClass.button22.isEnabled = true
//            bindingClass.button33.isEnabled = true
//            bindingClass.button44.isEnabled = true
//            currentButton.setBackgroundResource(android.R.drawable.btn_default)
//        }
//
//
//    }

    private fun layOutExercise(examplesList: MutableList<GapInSentenceData>, index: Int) {
        bindingClass.textSentence.text = examplesList[index].sentence


        val answersList = mutableListOf(

            examplesList[index].correctAnswer,
            examplesList[index].answer1,
            examplesList[index].answer2,
            examplesList[index].answer3
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

    private fun checkAnswerAndDisplay(
        currentButton: Button,
        examplesList: MutableList<GapInSentenceData>,
        index: Int
    ) {

        if (currentButton.text == examplesList[index].correctAnswer) {
            bindingClass.answerCheck.text = "Вы ответили верно!"
            currentButton.setBackgroundColor(Color.GREEN)
            lockAllButtons()

        } else {
            bindingClass.answerCheck.text = "Вы ответили неверно!"
            currentButton.setBackgroundColor(Color.RED)
            lockAllButtons()
        }

    }

    private fun lockAllButtons() {
        bindingClass.button11.isEnabled = false
        bindingClass.button22.isEnabled = false
        bindingClass.button33.isEnabled = false
        bindingClass.button44.isEnabled = false
    }
-------------------------------------------------------------------------------------------

}



//-------------------------------------------------------------------------------------------

//
//
//
//
//
////                    if (currentButton!!.text == examplesList[index].rightAnswer){
////                        bindingClass.answerCheck.text = "Вы ответили верно!"
////
////                } else {
////                        bindingClass.answerCheck.text = "Вы ответили неверно!"
////                    }
//
//
//
//
//


// Здесь начинается Старый код newest


/*for (index in examplesList.indices) {

    bindingClass.textSentence.text = examplesList[index].sentence


    var answersList = mutableListOf(

        examplesList[index].rightAnswer,
        examplesList[index].answer1,
        examplesList[index].answer2,
        examplesList[index].answer3
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



    var currentButton: Button? = null



    bindingClass.button11.setOnClickListener {

        currentButton = bindingClass.button11
    }

    bindingClass.button22.setOnClickListener {

        currentButton = bindingClass.button22
    }

    bindingClass.button33.setOnClickListener {

        currentButton = bindingClass.button33
    }

    bindingClass.button44.setOnClickListener {

        currentButton = bindingClass.button44
    }



        if (currentButton == null) {
            bindingClass.answerCheck.text = "+"
        }
*/


/*          if (currentButton != null) {
              if (currentButton!!.text == examplesList[index].rightAnswer)
                  bindingClass.answerCheck.text = "Вы ответили верно!"
              continue
          } else {
              bindingClass.answerCheck.text = "Вы ответили неверно!"
              continue

          }
*/


// Здесь начинается старый код оlder


//        bindingClass.textSentence.text = example1.sentence
//
//
//        var answersList = mutableListOf(
//            example1.rightAnswer,
//            example1.answer1,
//            example1.answer2,
//            example1.answer3
//        )
//
//
//        var n = answersList.size
//
//        var randomNumber = Random.nextInt(0, n)
//
//        bindingClass.button11.text = answersList[randomNumber]
//
//        n -= 1
//        answersList.removeAt(randomNumber)
//
//
//        randomNumber = Random.nextInt(0, n)
//
//        bindingClass.button22.text = answersList[randomNumber]
//
//        n -= 1
//        answersList.removeAt(randomNumber)
//
//
//        randomNumber = Random.nextInt(0, n)
//
//        bindingClass.button33.text = answersList[randomNumber]
//
//        n -= 1
//
//        answersList.removeAt(randomNumber)
//
//
//        bindingClass.button44.text = answersList[0]
//
//
//
//        bindingClass.button11.setOnClickListener {
//            if (bindingClass.button11.text == example1.rightAnswer) {
//


