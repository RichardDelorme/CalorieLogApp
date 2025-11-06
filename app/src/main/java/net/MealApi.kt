package com.example.calorielog.net

import com.squareup.moshi.Json
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

data class Meal(val strMeal: String?, val strMealThumb: String?)
data class MealsResponse(@Json(name="meals") val meals: List<Meal>?)

interface MealApi {
    @GET("random.php")
    suspend fun randomMeal(): MealsResponse

    companion object {
        fun create(): MealApi = Retrofit.Builder()
            .baseUrl("https://www.themealdb.com/api/json/v1/1/")
            .addConverterFactory(MoshiConverterFactory.create())
            .build().create(MealApi::class.java)
    }
}
