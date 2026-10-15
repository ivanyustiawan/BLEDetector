package com.example.bledetector.scannerScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.BleController
import com.example.domain.BleDevice
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val bleController: BleController
) : ViewModel() {

    private var scanJob: Job? = null

    private val _state = MutableStateFlow<ScannerState>(ScannerState.Idle)
    val state: StateFlow<ScannerState> = combine(
        bleController.scannedDevices,
        _state
    ) { devices, currentState ->

        when (currentState) {
            ScannerState.Idle -> ScannerState.Idle

            ScannerState.Scanning -> ScannerState.Scanning

            ScannerState.Empty -> ScannerState.Empty

            is ScannerState.Success -> {
                if (devices.isEmpty()) {
                    ScannerState.Empty
                } else {
                    ScannerState.Success(devices)
                }
            }

            is ScannerState.Error -> currentState
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ScannerState.Idle
    )

    fun startScan() {
        if (_state.value == ScannerState.Scanning) return

        scanJob?.cancel()

        _state.value = ScannerState.Scanning
        bleController.startDiscovery()

        scanJob = viewModelScope.launch {
            delay(10_000)
            stopScan()
        }
    }

    fun stopScan() {
        scanJob?.cancel()
        scanJob = null

        bleController.stopDiscovery()

        val devices = bleController.scannedDevices.value
        _state.value = if (devices.isEmpty()) {
            ScannerState.Empty
        } else {
            ScannerState.Success(devices)
        }
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    val filteredDevices: StateFlow<List<BleDevice>> = combine(
        bleController.scannedDevices,
        searchQuery
    ) { devices, query ->
        if (query.isBlank()) {
            devices
        } else {
            devices.filter { device ->
                device.name.contains(query, ignoreCase = true) ||
                        device.address.contains(query, ignoreCase = true)
            }.sortedByDescending { it.rssi }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}