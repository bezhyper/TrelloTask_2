package com.example.trellotask_2.room.entities

import androidx.room.*


@Entity(tableName = "exercises")
data class ExerciseDataEntityUnusable(

    @ColumnInfo(name = "correct")
    val correct: String,

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
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
    val wrong_3: String,
)




{
//
//    fun toExerciseData(): ExerciseData = ExerciseData(
//
//        correct = correct,
//
//
//        id = id,
//
//
//        sentens = sentens,
//
//
//        type_task = type_task,
//
//
//        wrong_1 = wrong_1,
//
//
//        wrong_2 = wrong_2,
//
//
//        wrong_3 = wrong_3
//    )
//
//    fun exerciseDataUpdateTuple(){
//
//
//
//    }




}













//    @ColumnInfo(name = "correct")
//    val correct: String,
//
//    @ColumnInfo(name = "id")
//    var id: Int? = null,
//
//    @ColumnInfo(name = "sentens")
//    var sentens: String,
//
//    @ColumnInfo(name = "typeTask")
//    val type_task: String,
//
//    @ColumnInfo(name = "wrong_1")
//    val wrong_1: String,
//
//    @ColumnInfo(name = "wrong_2")
//    val wrong_2: String,
//
//    @ColumnInfo(name = "wrong_3")
//    val wrong_3: String,


/*

@Entity(tableName = "cards", indices = [Index(value = ["data"], unique = true)])
data class CardEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val data: String
)

 */



