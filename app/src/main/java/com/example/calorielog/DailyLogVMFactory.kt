package com.example.calorielog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calorielog.data.AppDatabase

@Suppress("UNCHECKED_CAST")
class DailyLogVMFactory(private val db: AppDatabase) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DailyLogViewModel::class.java)) {
            val dao = db.foodDao()
            return DailyLogViewModel(dao, androidx.lifecycle.SavedStateHandle()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

