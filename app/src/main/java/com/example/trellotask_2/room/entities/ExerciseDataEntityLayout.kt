package com.example.trellotask_2.room.entities

import androidx.room.ColumnInfo

class ExerciseDataEntityLayout(
    @ColumnInfo(name = "b1")
    var b1: String,

    @ColumnInfo(name = "b2")
    var b2: String,

    @ColumnInfo(name = "b3")
    var b3: String,

    @ColumnInfo(name = "b4")
    var b4: String,

    @ColumnInfo(name = "currentCount")
    var currentCount: Int,


    @ColumnInfo(name = "chosen_answer")
    val chosen_answer: String? = null,

    @ColumnInfo(name = "is_answer_correct")
    val is_answer_correct: Int? = null
) {


}