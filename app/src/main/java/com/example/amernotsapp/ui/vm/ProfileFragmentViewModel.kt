package com.example.amernotsapp.ui.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amernotsapp.domain.usecase.GetProfileUseCase
import com.example.amernotsapp.ui.model.response.ProfileDataModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class ProfileFragmentViewModel @AssistedInject constructor(
    @Assisted(ASSISTED_VALUE_KEY) private val assistedValue: String,
    private val getProfileUseCase: GetProfileUseCase
): ViewModel() {

    private val _getProfileDataState: MutableLiveData<ProfileDataModel?> = MutableLiveData(null)
    val getProfileDataState: LiveData<ProfileDataModel?> = _getProfileDataState

    private val _errorState: MutableLiveData<Throwable> = MutableLiveData(null)
    val errorState: LiveData<Throwable> = _errorState

    init {
        Log.d("ProfileFragmentViewModel", assistedValue)
    }

    fun requestGetProfile(tokenAuthHeader: String) {
        viewModelScope.launch {
            runCatching {
                getProfileUseCase(tokenAuthHeader)
            }.onSuccess { getProfileDataState ->
                _getProfileDataState.postValue(getProfileDataState)
            }.onFailure { ex ->
                _errorState.value = ex
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(ASSISTED_VALUE_KEY) assistedValue: String): ProfileFragmentViewModel
    }

    companion object {
        private const val ASSISTED_VALUE_KEY = "ASSISTED_STRING_VALUE"
    }
}