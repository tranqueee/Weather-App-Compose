package com.example.weather.di

import android.content.Context
import com.example.weather.data.ForecastRepositoryImpl
import com.example.weather.data.Retrofit.ManagerAPI
import com.example.weather.data.Retrofit.Forecast.GetForecast
import com.example.weather.models.ForecastRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(@ApplicationContext context: Context): FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    @Singleton
    fun provideResponse():GetForecast = ManagerAPI().response

    @Provides
    @Singleton
    fun provideForecastRepository(@ApplicationContext context: Context, response: GetForecast, fusedLocationProviderClient: FusedLocationProviderClient): ForecastRepository = ForecastRepositoryImpl(context, response, fusedLocationProviderClient)
}