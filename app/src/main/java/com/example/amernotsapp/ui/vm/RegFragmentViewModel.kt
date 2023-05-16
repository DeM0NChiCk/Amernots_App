package com.example.amernotsapp.ui.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amernotsapp.data.model.request.RegRequest
import com.example.amernotsapp.domain.usecase.RegNewUserUseCase
import com.example.amernotsapp.ui.model.response.TokenAuthDataModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class RegFragmentViewModel @AssistedInject constructor(
    @Assisted(ASSISTED_VALUE_KEY) private val assistedValue: String,
    private val regNewUserUseCase: RegNewUserUseCase,
): ViewModel() {

    private val _regNewUserDataState: MutableLiveData<TokenAuthDataModel?> = MutableLiveData(null)
    val regNewUserDataState: LiveData<TokenAuthDataModel?> = _regNewUserDataState

    private val _errorState: MutableLiveData<Throwable> = MutableLiveData(null)
    val errorState: LiveData<Throwable> = _errorState

    init {
        Log.d("RegViewModelTag", assistedValue)
    }

    fun requestTokenAuthByRegistration(regRequest: RegRequest) {
        viewModelScope.launch {
            runCatching {
                regNewUserUseCase(regRequest)
            }.onSuccess {regNewUserDataState ->
                _regNewUserDataState.postValue(regNewUserDataState)
            }.onFailure { ex ->
                _errorState.value = ex
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(ASSISTED_VALUE_KEY) assistedValue: String): RegFragmentViewModel
    }


    companion object {
        private const val ASSISTED_VALUE_KEY = "ASSISTED_STRING_VALUE"
    }
}