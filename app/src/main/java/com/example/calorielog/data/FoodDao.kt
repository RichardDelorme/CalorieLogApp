package com.example.calorielog.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.calorielog.model.FoodEntry

@Dao
interface FoodDao {
    @Insert suspend fun insert(entry: FoodEntry)
    @Delete suspend fun delete(entry: FoodEntry)

    @Query("SELECT * FROM entries WHERE date = :date ORDER BY id DESC")
    fun entriesForDate(date: String): LiveData<List<FoodEntry>>

    // NEW: daily total
    @Query("SELECT COALESCE(SUM(calories),0) FROM entries WHERE date = :date")
    fun totalForDate(date: String): LiveData<Int>

    // NEW: range query for weekly view [inclusive]
    @Query("SELECT * FROM entries WHERE date BETWEEN :start AND :end ORDER BY date DESC, id DESC")
    fun entriesBetween(start: String, end: String): LiveData<List<FoodEntry>>

    // NEW: reset helpers
    @Query("DELETE FROM entries WHERE date = :date")
    suspend fun deleteByDate(date: String)

    @Query("DELETE FROM entries WHERE date BETWEEN :start AND :end")
    suspend fun deleteBetween(start: String, end: String)
}

