package com.example.trellotask_2.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.trellotask_2.room.entities.ExerciseDataEntityRoom

@Database(entities = [ExerciseDataEntityRoom::class], version = 2)
abstract class MainDb : RoomDatabase() {



    abstract fun getDao(): Dao

    companion object {

        fun getDb(context: Context): MainDb {
            return Room.databaseBuilder(
                context.applicationContext,
                MainDb::class.java,
                "exercisesData.db"
            ).allowMainThreadQueries()
                .fallbackToDestructiveMigration()
                .build()

        }

    }
}