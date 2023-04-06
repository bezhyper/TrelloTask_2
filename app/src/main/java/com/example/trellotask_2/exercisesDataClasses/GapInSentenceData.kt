package com.example.trellotask_2.exercisesDataClasses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity (tableName = "exercises")
data class GapInSentenceData(

    @ColumnInfo(name = "correct")
    val correct: String,

    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,

    @ColumnInfo(name = "sentens")
    var sentens: String,

    @ColumnInfo(name = "typeTask")
    val type_task: String,

    @ColumnInfo(name = "wrong_1")
    val wrong_1: String,

    @ColumnInfo(name = "wrong_2")
    val wrong_2: String,

    @ColumnInfo(name = "wrong_3")
    val wrong_3: String




)


