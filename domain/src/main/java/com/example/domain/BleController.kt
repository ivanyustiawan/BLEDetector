package com.example.domain

import kotlinx.coroutines.flow.StateFlow

interface BleController {
    val scannedDevices: StateFlow<List<BleDevice>>

    fun startDiscovery()
    fun stopDiscovery()
    fun release()
}