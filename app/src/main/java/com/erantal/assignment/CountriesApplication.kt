package com.erantal.assignment

import android.app.Application

class CountriesApplication : Application() {
    val countriesMap = HashMap<String, Pair<String,String>>()
}