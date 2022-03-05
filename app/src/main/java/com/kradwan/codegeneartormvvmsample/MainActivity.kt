package com.kradwan.codegeneartormvvmsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.presentation.MainActivityViewModel
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountStateEvent

class MainActivity : AppCompatActivity() {


    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        viewModel.dataState.observe(this) { dataState ->
            Log.d("DDDD", "DDDD Response Success: $dataState")

            dataState?.data?.let {
                Log.d("DDDD", "DDDD Response Success: $it")
            }
            dataState?.error?.let {
                Log.d("DDDD", "DDDD Response Error: $it")

            }
        }


        viewModel.setStateEvent(AccountStateEvent.Login(LoginRequest("email1", "password1")))
        viewModel.setStateEvent(AccountStateEvent.GetCountries)
        viewModel.setStateEvent(AccountStateEvent.Login(LoginRequest("email2", "password2")))
        viewModel.setStateEvent(AccountStateEvent.GetCountries)
        viewModel.setStateEvent(AccountStateEvent.Login(LoginRequest("email3", "password3")))
        viewModel.setStateEvent(AccountStateEvent.GetCountries)


    }


}