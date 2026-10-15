package com.example.bledetector

import android.content.Context
import com.example.core.bluetoothManager.BleControllerImpl
import com.example.domain.BleController
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BleModule {

    @Provides
    @Singleton
    fun provideBleController(
        @ApplicationContext context: Context
    ): BleController {
        return BleControllerImpl(context)
    }
}