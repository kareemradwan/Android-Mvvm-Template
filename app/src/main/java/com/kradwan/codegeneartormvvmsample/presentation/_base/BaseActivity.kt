package com.kradwan.codegeneartormvvmsample.presentation._base

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.fragment.NavHostFragment
import com.kradwan.codegeneartormvvmsample.data.session.SessionManager
import javax.inject.Inject


open class BaseActivity : AppCompatActivity() {


    fun hideSoftKeyboard(view: View) {
        val inputMethodManager = getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager
            .hideSoftInputFromWindow(view.windowToken, 0)
    }

    @Inject
    lateinit var sessionManager: SessionManager


}