package com.kradwan.codegeneartormvvmsample.presentation._base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.kradwan.codegeneartormvvmsample.data.session.SessionManager
import javax.inject.Inject

abstract class BaseFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }


    fun hideSoftKeyboard(view: View) {
        val inputMethodManager = requireActivity().getSystemService(
            Context.INPUT_METHOD_SERVICE
        ) as InputMethodManager
        inputMethodManager
            .hideSoftInputFromWindow(view.windowToken, 0)
    }

    @Inject
    lateinit var sessionManager: SessionManager

}