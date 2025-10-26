package com.example.calorielog.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.calorielog.model.FoodEntry

@Dao
interface FoodDao {
    @Insert suspend fun insert(entry: FoodEntry)
    @Query("SELECT * FROM entries WHERE date = :date ORDER BY id DESC")
    fun entriesForDate(date: String): LiveData<List<FoodEntry>>
}
