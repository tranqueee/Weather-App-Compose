package com.example.weather.di

import com.example.weather.domain.useCases.ForecastByCityUseCase
import com.example.weather.domain.useCases.ForecastByLocationUseCase
import com.example.weather.models.ForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class DomainModule {

    @Provides
    fun providesForecastByCityUseCase(forecastRepository: ForecastRepository): ForecastByCityUseCase = ForecastByCityUseCase(forecastRepository)

    @Provides
    fun providesForecastByLocationUseCase(
        forecastRepository: ForecastRepository): ForecastByLocationUseCase = ForecastByLocationUseCase(forecastRepository)
}