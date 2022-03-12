package com.kradwan.codegeneartormvvmsample.data.session

import android.app.Application
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager
@Inject
constructor(
    val application: Application,
    private val sharedPreferences: SharedPreferences,
    private val sharedPrefsEditor: SharedPreferences.Editor
) {


    fun getLang(): String = sharedPreferences.getString("lang", "Nothing") ?: "Nothing"

    fun setLang(lang: String) = sharedPrefsEditor.putString("lang", lang).commit()

}
