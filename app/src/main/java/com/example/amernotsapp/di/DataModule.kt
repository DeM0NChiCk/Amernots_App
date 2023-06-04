package com.example.amernotsapp.di

import com.example.amernotsapp.BuildConfig
import com.example.amernotsapp.data.api.network.AmernotsApiService
import com.example.amernotsapp.data.api.network.DaDataService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    fun okHttpClient(): OkHttpClient{
        val client = OkHttpClient.Builder()
            .addInterceptor { chain ->

                val request = chain.request().newBuilder()
                    .build()
                chain.proceed(request)
            }
        if (BuildConfig.DEBUG){
            client.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        }
        return client.build()
    }

    @Provides
    @Singleton
    fun provideAmernotsApi(okHttpClient: OkHttpClient): AmernotsApiService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BuildConfig.AMERNOTS_API_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitBuilder.create(AmernotsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDaData(okHttpClient: OkHttpClient): DaDataService {
        val retrofitBuilder = Retrofit.Builder()
            .baseUrl(BuildConfig.DA_DATA_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitBuilder.create(DaDataService::class.java)
    }
}