package com.example.amernotsapp.ui.vm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.amernotsapp.domain.usecase.GetNeswlineUseCase
import com.example.amernotsapp.ui.model.response.NewslineDataModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch

class NewslineFragmnetViewModel @AssistedInject constructor(
    @Assisted(ASSISTED_VALUE_KEY) private val assistedValue: String,
    private val getNeswlineUseCase: GetNeswlineUseCase
):ViewModel() {

    private val _getNewslineDataState: MutableLiveData<NewslineDataModel?> = MutableLiveData(null)
    val getNewslineDataState: LiveData<NewslineDataModel?> = _getNewslineDataState

    private val _errorState: MutableLiveData<Throwable> = MutableLiveData(null)
    val errorState: LiveData<Throwable> = _errorState

    init {
        Log.d("NewslineFragmentViewModel", assistedValue)
    }

    fun requestGetNewsline(tokenAuthHeader: String) {
        viewModelScope.launch {
            runCatching {
                getNeswlineUseCase(tokenAuthHeader)
            }.onSuccess { getNewslineDataState ->
                _getNewslineDataState.postValue(getNewslineDataState)
            }.onFailure { ex ->
                _errorState.value = ex
            }
        }
    }

    @AssistedFactory
    interface Factory{
        fun create(@Assisted(ASSISTED_VALUE_KEY) assistedValue: String): NewslineFragmnetViewModel
    }


    companion object {
        private const val ASSISTED_VALUE_KEY = "ASSISTED_STRING_VALUE"
    }
}