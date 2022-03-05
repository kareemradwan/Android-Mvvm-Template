package com.kradwan.codegeneartormvvmsample.data.model.general.getCountries

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GetCountriesRequest(@SerializedName("languageId") @Expose val languageId: Int = 1)