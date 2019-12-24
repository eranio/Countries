package com.erantal.assignment.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class RestCountry() : Serializable {

    @SerializedName("name")
    var name: String? = null

    @SerializedName("alpha3Code")
    var alpha3Code: String? = null

    @SerializedName("area")
    var area: Double? = null

    @SerializedName("borders")
    var borders: ArrayList<String>? = null

    @SerializedName("nativeName")
    var nativeName: String? = null

    @SerializedName("flag")
    var flag: String? = null
}