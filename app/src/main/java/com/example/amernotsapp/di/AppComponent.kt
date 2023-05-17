package com.example.amernotsapp.di

import com.example.amernotsapp.ui.screen.fragment.SignInFragment
import com.example.amernotsapp.ui.screen.fragment.SignUpFragment
import com.example.amernotsapp.ui.vm.SIgnInFragmentViewModel
import com.example.amernotsapp.ui.vm.SignUpFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, AppBindsModule::class, DomainModule::class])
interface AppComponent {

    fun signUpNewUserViewModel(): SignUpFragmentViewModel.Factory

    fun injectSignUp(fragment: SignUpFragment)

    fun signInViewModel(): SIgnInFragmentViewModel.Factory

    fun injectSignIn(fragment: SignInFragment)
}