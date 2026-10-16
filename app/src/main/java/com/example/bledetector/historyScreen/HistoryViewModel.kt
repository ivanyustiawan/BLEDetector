package com.example.bledetector.historyScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.BleDevice
import com.example.domain.BleHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val historyRepository: BleHistoryRepository
) : ViewModel() {

    private val _historyDevices = MutableStateFlow<List<BleDevice>>(emptyList())

    val historyDevices: StateFlow<List<BleDevice>> = _historyDevices.asStateFlow()

    init {
        viewModelScope.launch {
            historyRepository.getHistory().collect { devices ->
                _historyDevices.value = devices
            }
        }
    }
}
