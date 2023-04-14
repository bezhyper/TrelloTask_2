package com.example.trellotask_2.activities

import kotlin.random.Random
import android.content.Intent
import android.graphics.Color.*
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.lifecycle.asLiveData
import com.example.trellotask_2.retrofit.DataApi
import com.example.trellotask_2.room.MainDb

import com.example.trellotask_2.databinding.ActivityThirdBinding
import com.example.trellotask_2.room.entities.ExerciseDataEntityLayout
import com.example.trellotask_2.room.entities.UpdateExerciseDataInTuple
import com.example.trellotask_2.utils.Constants.Companion.BASE_URL
import kotlinx.coroutines.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ThirdActivity : AppCompatActivity() {
    lateinit var bindingClass: ActivityThirdBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingClass = ActivityThirdBinding.inflate(layoutInflater)

        setContentView(bindingClass.root)


        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
//
//
        val dataApi = retrofit.create(DataApi::class.java)
//
        val db = MainDb.getDb((this)) // создание базы данных

        var i = 1

        var currentButton: Button? = null

//        db.getDao().getExerciseWithFlow().asLiveData().observe(this) {
//            if(it.count == 1 || it.count == null){
//                bindingClass.buttonPrevEx.visibility = View.GONE
//            }
//            else{
//                bindingClass.buttonPrevEx.visibility = View.VISIBLE
//            }
//
//        }



        getData(dataApi, db)

        layOutExercise(i, db)

        fillOutLayoutDatabase(currentButton, db)







        bindingClass.buttonFromThirdToMain.setOnClickListener {
            val intent = Intent(this@ThirdActivity, MainActivity::class.java)
            startActivity(intent)

            CoroutineScope(Dispatchers.IO).launch {


                db.getDao().resetPrimaryKeyEntityRoom()
                db.getDao().resetPrimaryKeyEntityLayout() //    ?
            }



            CoroutineScope(Dispatchers.IO).launch {
                db.getDao().deleteAllInExercises()
                db.getDao().deleteAllInLayout()
            }
            // ПЕРЕНЕСТИ В onDestroy ?!
        }






        bindingClass.button11.setOnClickListener {

            currentButton = bindingClass.button11

            fillInGapInSentence(db)

            checkAnswerAndDisplay(currentButton!!, db, dataApi)




        }



        bindingClass.button22.setOnClickListener {

            currentButton = bindingClass.button22

            fillInGapInSentence(db)

            checkAnswerAndDisplay(currentButton!!, db, dataApi)


        }



        bindingClass.button33.setOnClickListener {

            currentButton = bindingClass.button33

            fillInGapInSentence(db)

            checkAnswerAndDisplay(currentButton!!, db, dataApi)


        }



        bindingClass.button44.setOnClickListener {

            currentButton = bindingClass.button44

            fillInGapInSentence(db)

            checkAnswerAndDisplay(currentButton!!, db, dataApi)



        }



        bindingClass.buttonNextEx.setOnClickListener {


            i++

            clearAnswerCheckTextView()

            unlockButtonsAndClearColor(currentButton)

            getData(dataApi, db)

            layOutExercise(i, db)

            fillOutLayoutDatabase(currentButton, db)

            showPrevExButton()


//            if (currentButton != null) {
//
//                bindingClass.button11.isEnabled = true
//                bindingClass.button22.isEnabled = true
//                bindingClass.button33.isEnabled = true
//                bindingClass.button44.isEnabled = true
//
//                if (checkArrayIndexOutOfBoundsException(examplesList, index)) {
//                    currentButton!!.setBackgroundColor(Color.WHITE)
//                }
//
//
//
//
//
//
//
//                if (checkArrayIndexOutOfBoundsException(examplesList, index)) {
//                    index++
//                    layOutExercise(examplesList, index)
//                } else {
//                    bindingClass.nextExButton.isEnabled = false
//                    bindingClass.answerCheck.text = "Задания закончились!"
//                    lockAllButtons()
//
//                }
//            }


        }


        bindingClass.buttonPrevEx.setOnClickListener {
            if (i != 1) {
                i--

                layOutPrevExercise(i, db)
            }

        }


    }

    private fun fillOutLayoutDatabase(currentButton: Button?, db: MainDb) {

        CoroutineScope(Dispatchers.IO).launch {

            delay(1000) // может стоит не через дилэй а через авэйт???!!!

            if (currentButton != null) {

                db.getDao()
                    .insertExerciseDataLayout(
                        ExerciseDataEntityLayout(
                            b1 = bindingClass.button11.text as String?,
                            b2 = bindingClass.button22.text as String?,
                            b3 = bindingClass.button33.text as String?,
                            b4 = bindingClass.button44.text as String?,
                            chosen_answer = currentButton.text.toString()
                        )
                    )
            }else{

                db.getDao()
                    .insertExerciseDataLayout(
                        ExerciseDataEntityLayout(
                            b1 = bindingClass.button11.text as String?,
                            b2 = bindingClass.button22.text as String?,
                            b3 = bindingClass.button33.text as String?,
                            b4 = bindingClass.button44.text as String?,

                        )
                    )



            }
        }

    }


    private fun clearAnswerCheckTextView() {
        bindingClass.answerCheck.text = ""
    }

    private fun getData(dataApi: DataApi, db: MainDb) {

        CoroutineScope(Dispatchers.IO).launch {

            val exerciseDataRetrofit = dataApi.getRandomExercise(1)
            val exerciseDataRoom = exerciseDataRetrofit.toExerciseData()

            db.getDao().insertExerciseData(exerciseDataRoom)




        }
    }


    private fun showPrevExButton() {
        bindingClass.buttonPrevEx.visibility = View.VISIBLE
    }


    private fun fillInGapInSentence(db: MainDb) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = db.getDao().getExerciseWithoutFlow()
            runOnUiThread {

                val fullSentence = data.sentens.replace("<пропуск>", data.correct)

                bindingClass.textSentence.text = fullSentence
            }

        }
    }


    private fun unlockButtonsAndClearColor(currentButton: Button?) {
        if (currentButton != null) {
            bindingClass.button11.isEnabled = true
            bindingClass.button22.isEnabled = true
            bindingClass.button33.isEnabled = true
            bindingClass.button44.isEnabled = true
//                currentButton.setBackgroundResource(android.R.drawable.btn_default) - ЦВЕТ из-за изменения темы теперь не подходит
            currentButton.setBackgroundColor(WHITE)
        }


    }


    private fun layOutExercise(i: Int, db: MainDb) {


        db.getDao().getExerciseWithFlow().asLiveData().observe(this) { data ->

            if ((data != null) && (data.chosen_answer == null) && (data.is_answer_correct == null)) {

                val fullSentence = data.sentens.replace("<пропуск>", "______")

                bindingClass.textSentence.text = fullSentence


                val answersList = mutableListOf(
                    data.correct,
                    data.wrong_1,
                    data.wrong_2,
                    data.wrong_3
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


    }

    private fun layOutPrevExercise(i: Int, db: MainDb) {

//        var currentCount =  CoroutineScope(Dispatchers.IO).launch{}

        CoroutineScope(Dispatchers.IO).launch {

            val data = db.getDao().getPrevExercise(i)


            val fullSentence = data.sentens.replace("<пропуск>", "______")

            bindingClass.textSentence.text = fullSentence


            val answersList = mutableListOf(
                data.correct,
                data.wrong_1,
                data.wrong_2,
                data.wrong_3
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


    private fun checkAnswerAndDisplay(currentButton: Button, db: MainDb, dataApi: DataApi) {

        CoroutineScope(Dispatchers.IO).launch {
            val data = db.getDao().getExerciseWithoutFlow()




            runOnUiThread {


                if (currentButton.text == data.correct) {
                    bindingClass.answerCheck.text = "Вы ответили верно!"
                    currentButton.setBackgroundColor(GREEN)
                    lockAllButtons()
                    CoroutineScope(Dispatchers.IO).launch {


                        val updatedData = UpdateExerciseDataInTuple(
                            count = data.count,
                            "${currentButton.text}",
                            is_answer_correct = 1
                        )
                        db.getDao().updateAnswerData(updatedData)


                    }
                    CoroutineScope(Dispatchers.IO).launch {
                        dataApi.postRightCompletedExercise(1, data.id!!)

                    }


                } else {
                    bindingClass.answerCheck.text = "Вы ответили неверно!"
                    currentButton.setBackgroundColor(RED)
                    lockAllButtons()
                    CoroutineScope(Dispatchers.IO).launch {

                        val updatedData = UpdateExerciseDataInTuple(
                            count = data.count,
                            "${currentButton.text}",
                            is_answer_correct = 0
                        )
                        db.getDao().updateAnswerData(updatedData)
                    }
                }

            }

        }

    }


    private fun lockAllButtons() {
        bindingClass.button11.isEnabled = false
        bindingClass.button22.isEnabled = false
        bindingClass.button33.isEnabled = false
        bindingClass.button44.isEnabled = false
    }


}


