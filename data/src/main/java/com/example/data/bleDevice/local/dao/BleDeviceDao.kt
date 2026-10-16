package com.example.data.bleDevice.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.data.bleDevice.local.entity.BleDeviceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BleDeviceDao {

    @Query("SELECT * FROM ble_devices " + "ORDER BY lastSeenMillis DESC")
    fun getAllDevices(): Flow<List<BleDeviceEntity>>

    @Upsert
    suspend fun upsertDevices(devices: List<BleDeviceEntity>)

    @Query("DELETE FROM ble_devices")
    suspend fun clearHistory()
}