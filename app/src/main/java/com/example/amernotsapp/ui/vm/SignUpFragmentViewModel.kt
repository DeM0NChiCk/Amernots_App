package com.example.amernotsapp.ui.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amernotsapp.data.api.model.request.SignUpRequest
import com.example.amernotsapp.domain.usecase.SignUpNewUserUseCase
import com.example.amernotsapp.ui.model.response.TokenAuthDataModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class SignUpFragmentViewModel @AssistedInject constructor(
    @Assisted(ASSISTED_VALUE_KEY) private val assistedValue: String,
    private val signUpNewUserUseCase: SignUpNewUserUseCase,
): ViewModel() {

    private val _signUpNewUserDataState: MutableLiveData<TokenAuthDataModel?> = MutableLiveData(null)
    val signUpNewUserDataState: LiveData<TokenAuthDataModel?> = _signUpNewUserDataState

    private val _errorState: MutableLiveData<Throwable> = MutableLiveData(null)
    val errorState: LiveData<Throwable> = _errorState

    init {
        Log.d("SignUpNewUSerViewModelTag", assistedValue)
    }

    fun requestTokenAuthByRegistration(signUpRequest: SignUpRequest) {
        viewModelScope.launch {
            runCatching {
                signUpNewUserUseCase(signUpRequest)
            }.onSuccess {signUpNewUserDataState ->
                _signUpNewUserDataState.postValue(signUpNewUserDataState)
            }.onFailure { ex ->
                _errorState.value = ex
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(ASSISTED_VALUE_KEY) assistedValue: String): SignUpFragmentViewModel
    }


    companion object {
        private const val ASSISTED_VALUE_KEY = "ASSISTED_STRING_VALUE"
    }
}