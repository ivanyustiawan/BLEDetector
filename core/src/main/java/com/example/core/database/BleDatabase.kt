package com.example.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.bleDevice.local.dao.BleDeviceDao
import com.example.data.bleDevice.local.entity.BleDeviceEntity

@Database(
    entities = [BleDeviceEntity::class],
    version = 1,
    exportSchema = false
)
abstract class BleDatabase : RoomDatabase() {

    abstract fun bleDeviceDao(): BleDeviceDao
}