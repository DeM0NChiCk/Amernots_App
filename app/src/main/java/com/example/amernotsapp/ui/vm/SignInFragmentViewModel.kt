package com.example.amernotsapp.ui.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amernotsapp.data.api.model.request.SignInRequest
import com.example.amernotsapp.domain.usecase.SignInUseCase
import com.example.amernotsapp.ui.model.response.TokenAuthDataModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class SignInFragmentViewModel @AssistedInject constructor(
    @Assisted(ASSISTED_VALUE_KEY) private val assistedValue: String,
    private val signInUseCase: SignInUseCase,
) : ViewModel() {
    private val _signInDataState: MutableLiveData<TokenAuthDataModel?> = MutableLiveData(null)
    val signInDataState: LiveData<TokenAuthDataModel?> = _signInDataState

    private val _errorState: MutableLiveData<Throwable> = MutableLiveData(null)
    val errorState: LiveData<Throwable> = _errorState

    init {
        Log.d("SignInViewModelTag", assistedValue)
    }

    fun requestTokenAuthBySignIn(signInRequest: SignInRequest) {
        viewModelScope.launch {
            runCatching {
                signInUseCase(signInRequest)
            }.onSuccess { signInDataState ->
                _signInDataState.postValue(signInDataState)
            }.onFailure { ex ->
                _errorState.value = ex
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(ASSISTED_VALUE_KEY) assistedValue: String): SignInFragmentViewModel
    }

    companion object {
        private const val ASSISTED_VALUE_KEY = "ASSISTED_STRING_VALUE"
    }
}