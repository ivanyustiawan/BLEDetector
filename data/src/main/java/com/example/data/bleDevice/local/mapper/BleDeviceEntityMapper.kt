package com.example.data.bleDevice.local.mapper

import com.example.data.bleDevice.local.entity.BleDeviceEntity
import com.example.domain.BleDevice

fun BleDevice.toEntity() = BleDeviceEntity(
    address = address,
    name = name,
    rssi = rssi,
    lastSeenMillis = lastSeenMillis
)

fun BleDeviceEntity.toBleDevice() = BleDevice(
    address = address,
    name = name,
    rssi = rssi,
    lastSeenMillis = lastSeenMillis
)