package com.example.bledetector

import com.example.domain.BleDevice


object DummyBleDevices {

    private val currentTime = System.currentTimeMillis()

    val devices = listOf(
        BleDevice(
            address = "AA:BB:CC:11:22:33",
            name = "JBL Speaker",
            rssi = -42,
            lastSeenMillis = currentTime
        ),
        BleDevice(
            address = "AA:BB:CC:11:22:44",
            name = "Mi Band 8",
            rssi = -55,
            lastSeenMillis = currentTime - 1_000
        ),
        BleDevice(
            address = "AA:BB:CC:11:22:55",
            name = "Samsung Galaxy Watch",
            rssi = -65,
            lastSeenMillis = currentTime - 2_000
        ),
        BleDevice(
            address = "AA:BB:CC:11:22:66",
            name = "Unknown Device",
            rssi = -78,
            lastSeenMillis = currentTime - 3_000
        ),
        BleDevice(
            address = "AA:BB:CC:11:22:77",
            name = "null",
            rssi = -90,
            lastSeenMillis = currentTime - 5_000
        )
    ).sortedByDescending { it.rssi }
}
