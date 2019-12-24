package com.erantal.assignment

import com.erantal.assignment.models.RestCountry
import retrofit2.Call
import retrofit2.http.GET

interface RestService {
    companion object {
        const val BASE_URL = "https://restcountries.eu"
    }

    @GET("/rest/v2")
    fun getAllCountries(): Call<ArrayList<RestCountry>>
}