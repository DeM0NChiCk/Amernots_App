package com.example.amernotsapp.ui.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amernotsapp.data.api.model.request.ChangePasswordRequest
import com.example.amernotsapp.domain.usecase.ChangePasswordUseCase
import com.example.amernotsapp.ui.model.response.ChangeStatusMessageDataModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class BtmSheetDialogViewModel @AssistedInject constructor(
    @Assisted(ASSISTED_VALUE_KEY) private val assistedValue: String,
    private val changePasswordUseCase: ChangePasswordUseCase
): ViewModel() {

    private val _mesChangePasswordDataState: MutableLiveData<ChangeStatusMessageDataModel?> = MutableLiveData(null)
    val mesChangePasswordDataState: LiveData<ChangeStatusMessageDataModel?> = _mesChangePasswordDataState

    private val _errorState: MutableLiveData<Throwable> = MutableLiveData(null)
    val errorState: LiveData<Throwable> = _errorState

    init {
        Log.d("BtmSheetDialogViewModel", assistedValue)
    }

    fun changePassword(tokenAuthHeader: String, changePasswordRequest: ChangePasswordRequest) {
        viewModelScope.launch {
            runCatching {
                changePasswordUseCase(tokenAuthHeader, changePasswordRequest)
            }.onSuccess { mesChangePasswordDataState ->
                _mesChangePasswordDataState.postValue(mesChangePasswordDataState)
            }.onFailure { ex ->
                _errorState.value = ex
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(ASSISTED_VALUE_KEY) assistedValue: String): BtmSheetDialogViewModel
    }

    companion object{
        private const val ASSISTED_VALUE_KEY = "ASSISTED_STRING_VALUE"
    }
}