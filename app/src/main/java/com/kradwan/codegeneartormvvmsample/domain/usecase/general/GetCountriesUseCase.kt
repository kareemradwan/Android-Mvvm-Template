package com.kradwan.codegeneartormvvmsample.domain.usecase.general

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kradwan.codegeneartormvvmsample.domain.repository.AccountRepository
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(val repo: AccountRepository) {


    fun execute(): LiveData<DataState<AccountViewState>> {

        return repo.getCountries()
    }


}