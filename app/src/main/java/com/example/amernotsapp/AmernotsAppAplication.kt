package com.example.amernotsapp

import android.app.Application
import com.example.amernotsapp.di.AppComponent
import com.example.amernotsapp.di.DaggerAppComponent
import com.example.amernotsapp.di.RegistrationComponent

class AmernotsAppAplication: Application() {

    lateinit var appComponent: AppComponent
    lateinit var registrationComponent: RegistrationComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .build()

//        registrationComponent = DaggerRegistrationComponent.builder()
//            .context(ctx = this)
//            .build()
    }
}