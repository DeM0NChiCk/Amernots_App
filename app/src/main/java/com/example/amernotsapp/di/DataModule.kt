package com.example.amernotsapp.di

import com.example.amernotsapp.BuildConfig
import com.example.amernotsapp.data.network.AmernotsApiService
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
                val modifiedUrl = chain.request().url.newBuilder()
                    .build()

                val request = chain.request().newBuilder().url(modifiedUrl).build()
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

}