package com.example.calorielog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.switchMap
import com.example.calorielog.data.FoodDao
import com.example.calorielog.model.FoodEntry
import kotlinx.coroutines.launch
import java.time.LocalDate

class DailyLogViewModel(
    private val dao: FoodDao,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Keep today as the default date; you can change it with setDate(...)
    private val _date: MutableLiveData<String> =
        savedStateHandle.getLiveData("date", LocalDate.now().toString())
    val date: LiveData<String> = _date

    // Entries for the selected date
    val entries: LiveData<List<FoodEntry>> = _date.switchMap { dateString: String ->
        dao.entriesForDate(dateString)  // returns LiveData<List<FoodEntry>>
    }

    // Total calories for the selected date
    val total: LiveData<Int> = _date.switchMap { dateString: String ->
        dao.totalForDate(dateString)    // returns LiveData<Int>
    }

    // Change active date (used later if you add a date picker)
    fun setDate(newDate: String) {
        if (_date.value != newDate) _date.value = newDate
    }

    // Clear all entries for the active date
    fun resetDay() {
        val current = _date.value ?: LocalDate.now().toString()
        viewModelScope.launch { dao.deleteByDate(current) }
    }
}
