package com.example.amernotsapp.di

import com.example.amernotsapp.ui.screen.fragment.AdditionalInformationNewsFragment
import com.example.amernotsapp.ui.screen.fragment.BtmSheetDialogChangePassword
import com.example.amernotsapp.ui.screen.fragment.NewslineFragment
import com.example.amernotsapp.ui.screen.fragment.ProfileFragment
import com.example.amernotsapp.ui.screen.fragment.SignInFragment
import com.example.amernotsapp.ui.screen.fragment.SignUpFragment
import com.example.amernotsapp.ui.vm.AdditInfoNewsFragmentViewModel
import com.example.amernotsapp.ui.vm.BtmSheetDialogViewModel
import com.example.amernotsapp.ui.vm.NewslineFragmnetViewModel
import com.example.amernotsapp.ui.vm.ProfileFragmentViewModel
import com.example.amernotsapp.ui.vm.SignInFragmentViewModel
import com.example.amernotsapp.ui.vm.SignUpFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, AppBindsModule::class, DomainModule::class])
interface AppComponent {

    fun signUpNewUserViewModel(): SignUpFragmentViewModel.Factory
    fun injectSignUp(fragment: SignUpFragment)

    fun signInViewModel(): SignInFragmentViewModel.Factory
    fun injectSignIn(fragment: SignInFragment)

    fun profileViewModel(): ProfileFragmentViewModel.Factory
    fun injectProfile(fragment: ProfileFragment)

    fun injectNewsline(fragment: NewslineFragment)
    fun newslineViewModel(): NewslineFragmnetViewModel.Factory

    fun additInfoNewsViewModel(): AdditInfoNewsFragmentViewModel.Factory
    fun injectAdditionalInfoNews(fragment: AdditionalInformationNewsFragment)

    fun injectBtmShtDialogChangePassword(fragment: BtmSheetDialogChangePassword)
    fun btmShtDialogChangePasswordViewModel(): BtmSheetDialogViewModel.Factory
}