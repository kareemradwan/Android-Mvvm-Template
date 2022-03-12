package com.kradwan.codegeneartormvvmsample.domain.usecase.general

import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.domain.repository.AccountRepository
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation._base.DataState
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(val repo: AccountRepository) {


    suspend fun execute(): LiveData<DataState<AccountViewState>> {

        return repo.getCountries()

    }


}