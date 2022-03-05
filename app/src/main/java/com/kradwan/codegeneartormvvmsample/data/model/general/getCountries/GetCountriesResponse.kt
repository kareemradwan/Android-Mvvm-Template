package com.kradwan.codegeneartormvvmsample.data.model.general.getCountries

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetCountriesResponse(

    @SerializedName("StatusMessage")
    @Expose
    val StatusMessage: String = "",

    )