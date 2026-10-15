package com.example.domain

data class BleDevice(
    val address: String,
    val name: String,
    val rssi: Int,
    val lastSeenMillis: Long,
)