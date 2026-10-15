package com.example.bledetector.bluetoothManager

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import com.example.domain.BleDevice

@SuppressLint("MissingPermission")
fun BluetoothDevice.toBleDevice(
    rssi: Int,
    lastSeenMillis: Long = System.currentTimeMillis()
): BleDevice {
    return BleDevice(
        address = address,
        name = name ?: "Unknown Device",
        rssi = rssi,
        lastSeenMillis = lastSeenMillis
    )
}