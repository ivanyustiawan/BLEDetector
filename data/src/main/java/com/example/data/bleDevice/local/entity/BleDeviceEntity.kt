package com.example.data.bleDevice.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ble_devices")
data class BleDeviceEntity(
    @PrimaryKey
    val address: String,
    val name: String,
    val rssi: Int,
    val lastSeenMillis: Long
)