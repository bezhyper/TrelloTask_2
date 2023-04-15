package com.example.trellotask_2.room

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.trellotask_2.room.entities.ExerciseDataEntityLayout
import com.example.trellotask_2.room.entities.ExerciseDataEntityRoom
import com.example.trellotask_2.room.entities.ExerciseDataInTuple
import com.example.trellotask_2.room.entities.UpdateExerciseDataInTuple
import kotlinx.coroutines.flow.Flow


@androidx.room.Dao
interface Dao {

    @Insert
    fun insertExerciseData(data: ExerciseDataEntityRoom)

    @Query("SELECT * FROM exercises")
    fun getAllData(): List<ExerciseDataEntityRoom>

    //    select *from getLastRecord ORDER BY id DESC LIMIT 1
    @Query("SELECT * FROM exercises ORDER BY count DESC LIMIT 1")
    fun getExerciseWithFlow(): Flow<ExerciseDataEntityRoom>
//    getExercise: ExerciseDataInTuple

    @Query("SELECT * FROM exercises ORDER BY count DESC LIMIT 1")
    fun getExerciseWithoutFlow(): ExerciseDataEntityRoom

    @Query("SELECT * FROM exercises  WHERE count = :i")
    fun getPrevExercise(i: Int): ExerciseDataInTuple


    @Query("SELECT * FROM layout  WHERE count = :i")
    fun getExerciseLayout(i: Int): ExerciseDataEntityLayout



    @Update(entity = ExerciseDataEntityRoom::class)
    fun updateAnswerDataRoom(updateData: UpdateExerciseDataInTuple)

    @Update(entity = ExerciseDataEntityLayout::class)
    fun updateAnswerDataLayout(updateData: UpdateExerciseDataInTuple)


    @Update
    fun updateLayoutData(data: ExerciseDataEntityLayout)

    @Query("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'exercises'")
    fun resetPrimaryKeyEntityRoom()

    @Query("UPDATE sqlite_sequence SET seq = 0 WHERE name = 'layout'")
    fun resetPrimaryKeyEntityLayout()

    @Insert
    fun insertExerciseDataLayout(data: ExerciseDataEntityLayout)



    @Query("DELETE FROM exercises")
    fun deleteAllInExercises()

    @Query("DELETE FROM layout")
    fun deleteAllInLayout()


}