package com.example.amernotsapp.di

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.amernotsapp.AmernotsAppAplication


fun Context.appComponent(): AppComponent{
    return when (this) {
        is AmernotsAppAplication -> appComponent
        else -> this.applicationContext.appComponent()
    }
}

inline fun <reified T : ViewModel> Fragment.lazyViewModel(
    noinline create: (stateHandle: SavedStateHandle) -> T
) = viewModels<T> {
    SignUpVMFactory(this,create)
    SignInVMFactory(this,create)
    ProfileVMFactory(this, create)
    NewslineVMFactory(this, create)
    AdditInfoNewsVMFactory(this, create)
    BtmShtDialogVMFactory(this, create)
    AddNewsVMFactory(this, create)
}
