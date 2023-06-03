package com.example.amernotsapp.ui.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amernotsapp.domain.usecase.GetNewsByIdUseCase
import com.example.amernotsapp.ui.model.response.NewsByIdDataModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class AdditInfoNewsFragmentViewModel @AssistedInject constructor(
    @Assisted(ASSISTED_VALUE_KEY) private val assistedValue: String,
    private val getNewsByIdUseCase: GetNewsByIdUseCase
): ViewModel() {

    private val _getNewsByIdDataState: MutableLiveData<NewsByIdDataModel?> = MutableLiveData(null)
    val getNewsByIdDataState: LiveData<NewsByIdDataModel?> = _getNewsByIdDataState

    private val _errorState: MutableLiveData<Throwable> = MutableLiveData(null)
    val errorState: LiveData<Throwable> = _errorState

    init {
        Log.d("AdditInfoNewsFragmentViewModel", assistedValue)
    }

    fun requestGetNewsById(tokenAuthHeader: String, newsId: String) {
        viewModelScope.launch {
            runCatching {
                getNewsByIdUseCase(tokenAuthHeader, newsId)
            }.onSuccess { getNewsByIdDataState ->
                _getNewsByIdDataState.postValue(getNewsByIdDataState)
            }.onFailure { ex ->
                _errorState.value = ex
            }
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted(ASSISTED_VALUE_KEY) assistedValue: String): AdditInfoNewsFragmentViewModel
    }

    companion object {
        private const val ASSISTED_VALUE_KEY = "ASSISTED_STRING_VALUE"
    }
}