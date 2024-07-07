package com.example.salestestapp.di

import android.content.Context
import com.example.salestestapp.util.StringResHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext

@Module
@InstallIn(ViewModelComponent::class)
object ViewModelModule {

    @Provides
    fun provideStringResHelper(@ApplicationContext context: Context,) = StringResHelper(context)

}