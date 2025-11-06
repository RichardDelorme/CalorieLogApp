package com.example.calorielog

import org.junit.Assert.assertEquals
import org.junit.Test

class CaloriesTest {
    @Test
    fun sumCalories_isCorrect() {
        val list = listOf(200, 350, 450)
        assertEquals(1000, list.sum())
    }
}
