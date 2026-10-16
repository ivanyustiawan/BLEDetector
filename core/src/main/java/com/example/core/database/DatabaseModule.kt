package com.example.data.bleDevice.local

import android.content.Context
import androidx.room.Room
import com.example.core.database.BleDatabase
import com.example.data.bleDevice.local.dao.BleDeviceDao
import com.example.data.bleDevice.local.repository.BleHistoryRepositoryImpl
import com.example.domain.BleHistoryRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBleDatabase(@ApplicationContext context: Context): BleDatabase {
        return Room.databaseBuilder(
            context,
            BleDatabase::class.java,
            "ble_detector.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBleDeviceDao(database: BleDatabase): BleDeviceDao {
        return database.bleDeviceDao()
    }

    @Provides
    @Singleton
    fun provideBleHistoryRepository(dao: BleDeviceDao): BleHistoryRepository {
        return BleHistoryRepositoryImpl(dao)
    }

}