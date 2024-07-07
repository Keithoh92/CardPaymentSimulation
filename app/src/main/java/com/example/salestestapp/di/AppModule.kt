package com.example.salestestapp.di

import android.content.Context
import com.example.salestestapp.SalesTestAppApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return SalesTestAppApplication.appContext
    }
}