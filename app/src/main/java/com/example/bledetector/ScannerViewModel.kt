package com.example.bledetector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.BleDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow<ScannerState>(ScannerState.Idle)
    val state: StateFlow<ScannerState> = _state.asStateFlow()

    fun startScanning() {
        if (_state.value is ScannerState.Scanning) return
        viewModelScope.launch {
            _state.value = ScannerState.Scanning
            try {
                delay(1500)
                val devices = DummyBleDevices.devices

                _state.value = if (devices.isNotEmpty()) {
                    ScannerState.Success(devices)
                } else {
                    ScannerState.Empty
                }
            } catch (e: Exception) {
                _state.value = ScannerState.Error(
                    e.message ?: "Failed to scan Bluetooth devices"
                )
            }
        }
    }

    fun resetScanning() {
        _state.value = ScannerState.Idle
    }
}