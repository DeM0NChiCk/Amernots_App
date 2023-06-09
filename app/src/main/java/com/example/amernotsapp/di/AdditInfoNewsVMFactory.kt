package com.example.amernotsapp.di

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

class AdditInfoNewsVMFactory <T : ViewModel>(
    savedStateRegistryOwner: SavedStateRegistryOwner,
    private val create: (stateHandle: SavedStateHandle) -> T,
): AbstractSavedStateViewModelFactory(savedStateRegistryOwner, null) {
    @Suppress("WRONG_TYPE_PARAMETER_NULLABILITY_FOR_JAVA_OVERRIDE", "UNCHECKED_CAST")
    override fun <T : ViewModel?> create(key: String, modelClass: Class<T>, handle: SavedStateHandle): T {
        return create.invoke(handle) as T
    }
}