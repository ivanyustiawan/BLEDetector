package com.example.bledetector.scannerScreen

import com.example.domain.BleDevice

sealed interface ScannerState {
    data object Idle : ScannerState
    data object Scanning : ScannerState
    data object Empty : ScannerState

    data class Success(
        val devices: List<BleDevice>
    ) : ScannerState

    data class Error(
        val message: String
    ) : ScannerState
}