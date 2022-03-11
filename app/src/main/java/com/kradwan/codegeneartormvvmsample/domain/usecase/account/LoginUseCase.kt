package com.kradwan.codegeneartormvvmsample.domain.usecase.account

import androidx.lifecycle.LiveData
import com.kradwan.codegeneartormvvmsample.data.model.account.login.LoginRequest
import com.kradwan.codegeneartormvvmsample.domain.repository.AccountRepository
import com.kradwan.codegeneartormvvmsample.domain.usecase.BaseUseCase
import com.kradwan.codegeneartormvvmsample.domain.usecase.RequestSetting
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountStateEvent
import com.kradwan.codegeneartormvvmsample.presentation.account.AccountViewState
import com.kradwan.codegeneartormvvmsample.presentation.state.DataState
import javax.inject.Inject

class LoginUseCase @Inject constructor(val repo: AccountRepository) {

    suspend fun login(request: LoginRequest): LiveData<DataState<AccountViewState>> {

        return repo.login(request)
    }
}


class LoginUseCase2 @Inject constructor(val repo: AccountRepository) {

  suspend  fun login(request: RequestSetting<LoginRequest>): LiveData<DataState<AccountViewState>> {

        return repo.login2(request)
    }
}

