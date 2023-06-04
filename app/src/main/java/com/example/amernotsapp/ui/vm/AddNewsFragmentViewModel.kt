package com.example.amernotsapp.ui.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amernotsapp.data.api.model.request.AddNewsRequest
import com.example.amernotsapp.data.api.model.request.DaDataCheckAddressRequest
import com.example.amernotsapp.domain.usecase.AddNewsUseCase
import com.example.amernotsapp.domain.usecase.CheckAddressUseCase
import com.example.amernotsapp.ui.model.response.ChangeStatusMessageDataModel
import com.example.amernotsapp.ui.model.response.DaDataSuggestionsDataModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class AddNewsFragmentViewModel @AssistedInject constructor(
    @Assisted(ASSISTED_VALUE_KEY) private val assistedValue: String,
    private val addNewsUseCase: AddNewsUseCase,
    private val checkAddressUseCase: CheckAddressUseCase
): ViewModel() {

    private val _addNewsDataState: MutableLiveData<ChangeStatusMessageDataModel?> = MutableLiveData(null)
    val addNewsDataState: LiveData<ChangeStatusMessageDataModel?> = _addNewsDataState

    private val _checkAddressDataState: MutableLiveData<DaDataSuggestionsDataModel?> = MutableLiveData(null)
    val checkAddressDataState: LiveData<DaDataSuggestionsDataModel?> = _checkAddressDataState

    private val _errorState: MutableLiveData<Throwable> = MutableLiveData(null)
    val errorState: LiveData<Throwable> = _errorState

    init {
        Log.d("AddNewsFragmentViewModel", assistedValue)
    }

    fun requestAddNews(tokenAuth: String, addNewsRequest: AddNewsRequest) {
        viewModelScope.launch {
            runCatching {
                addNewsUseCase(tokenAuth, addNewsRequest)
            }.onSuccess { addNewsDataState ->
                _addNewsDataState.postValue(addNewsDataState)
            }.onFailure { ex ->
                _errorState.value = ex
            }
        }
    }

    fun requestCheckAddress(apiKey: String, daDataCheckAddressRequest: DaDataCheckAddressRequest) {
        viewModelScope.launch{
            runCatching{
                checkAddressUseCase(apiKey, daDataCheckAddressRequest)
            }.onSuccess {checkAddressDataState ->
                _checkAddressDataState.postValue(checkAddressDataState)
            }.onFailure { ex ->
                _errorState.value = ex
            }
        }
    }



    @AssistedFactory
    interface Factory {
        fun create(@Assisted(ASSISTED_VALUE_KEY) assistedValue: String): AddNewsFragmentViewModel
    }

    companion object {
        private const val ASSISTED_VALUE_KEY = "ASSISTED_STRING_VALUE"
    }
}