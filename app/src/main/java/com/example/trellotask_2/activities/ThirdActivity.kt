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
import com.example.trellotask_2.room.entities.ExerciseDataEntityRoom
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

        val dataApi = retrofit.create(DataApi::class.java)

        val db = MainDb.getDb((this)) // создание базы данных

        var i = 1

        var currentButton: Button? = null



        getData(dataApi, db)

        layOutExercise(i, db)

        fillOutLayoutDatabase(db)





        bindingClass.buttonFromThirdToMain.setOnClickListener {
            val intent = Intent(this@ThirdActivity, MainActivity::class.java)
            startActivity(intent)

            CoroutineScope(Dispatchers.IO).launch {


                db.getDao().resetPrimaryKeyEntityRoom()

            }

            CoroutineScope(Dispatchers.IO).launch {
                db.getDao().resetPrimaryKeyEntityLayout()

            }


            CoroutineScope(Dispatchers.IO).launch {
                db.getDao().deleteAllInExercises()

            }
            CoroutineScope(Dispatchers.IO).launch {
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

            fillOutLayoutDatabase(db)

            showPrevExButton()


        }


        bindingClass.buttonPrevEx.setOnClickListener {

            unlockButtonsAndClearColor(currentButton) // +






            clearAnswerCheckTextView()// +

            if (i != 1) {
                i--

                if (i == 1)
                    hidePrevExButton() //+

                layOutPrevExercise(i, db) // ?
            }

        }


    }

    private fun hidePrevExButton() {
        bindingClass.buttonPrevEx.visibility = View.GONE
    }

    private fun fillOutLayoutDatabase(db: MainDb) {

        CoroutineScope(Dispatchers.IO).launch {

//            delay(500) // может стоит не через дилэй а через авэйт???!!!


            delay(600)
            // Думаю, по крайней мере значение можно будет уменьшить после переноса инициализации инстанций
            db.getDao()
                .insertExerciseDataLayout(
                    ExerciseDataEntityLayout(
                        sentens = bindingClass.textSentence.text as String?,
                        b1 = bindingClass.button11.text as String?,
                        b2 = bindingClass.button22.text as String?,
                        b3 = bindingClass.button33.text as String?,
                        b4 = bindingClass.button44.text as String?,
                    )
                )

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

            val fullSentence = data.sentens.replace("<пропуск>", data.correct)
            runOnUiThread {

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

            bindingClass.button11.setBackgroundColor(WHITE)
            bindingClass.button22.setBackgroundColor(WHITE)
            bindingClass.button33.setBackgroundColor(WHITE)
            bindingClass.button44.setBackgroundColor(WHITE)
        }


    }


    private fun layOutExercise(i: Int, db: MainDb) {

//        db.getDao().getExerciseWithFlow().asLiveData().observe(layOutExercise(i,db))
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


        CoroutineScope(Dispatchers.IO).launch {

            val data = db.getDao().getExerciseLayout(i)




            runOnUiThread {

//                fillInGapInPrevSentence(i, db)
//                fillInGapInSentence(db)

                bindingClass.button11.text = data.b1


                bindingClass.button22.text = data.b2


                bindingClass.button33.text = data.b3


                bindingClass.button44.text = data.b4

                if ((data.is_answer_correct == 1) && (data.chosen_answer != null)) {
                    when (data.chosen_answer) {
                        bindingClass.button11.text -> bindingClass.button11.setBackgroundColor(GREEN)
                        bindingClass.button22.text -> bindingClass.button22.setBackgroundColor(GREEN)
                        bindingClass.button33.text -> bindingClass.button33.setBackgroundColor(GREEN)
                        bindingClass.button44.text -> bindingClass.button44.setBackgroundColor(GREEN)
                    }
                } else {
                    when (data.chosen_answer) {
                        bindingClass.button11.text -> bindingClass.button11.setBackgroundColor(RED)
                        bindingClass.button22.text -> bindingClass.button22.setBackgroundColor(RED)
                        bindingClass.button33.text -> bindingClass.button33.setBackgroundColor(RED)
                        bindingClass.button44.text -> bindingClass.button44.setBackgroundColor(RED)

                    }

                    if ((data.is_answer_correct == 1) && (data.chosen_answer != null))
                        bindingClass.answerCheck.text = "Вы ответили верно!"
                    else if ((data.is_answer_correct == 0) && (data.chosen_answer != null))
                        bindingClass.answerCheck.text = "Вы ответили неверно!"
                }
            }

        }
    }

    private fun fillInGapInPrevSentence(i: Int, db: MainDb) {
        CoroutineScope(Dispatchers.IO).launch {
            val data = db.getDao().getPrevExercise(i)

            val fullSentence = data.sentens.replace("<пропуск>", data.correct)
            runOnUiThread {

                bindingClass.textSentence.text = fullSentence
            }

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
                        db.getDao().updateAnswerDataRoom(updatedData)

                        db.getDao().updateAnswerDataLayout(updatedData)


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
                        db.getDao().updateAnswerDataRoom(updatedData)

                        db.getDao().updateAnswerDataLayout(updatedData)

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


