package com.example.amernotsapp.di

import com.example.amernotsapp.ui.screen.fragment.RegFragment
import com.example.amernotsapp.ui.vm.RegFragmentViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class, AppBindsModule::class, DomainModule::class])
interface AppComponent {

    fun regViewModel(): RegFragmentViewModel.Factory

    fun inject(fragment: RegFragment)
}