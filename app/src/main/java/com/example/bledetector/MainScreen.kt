package com.example.bledetector

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.bledetector.historyScreen.HistoryScreen
import com.example.bledetector.scannerScreen.ScannerViewModel
import androidx.compose.runtime.collectAsState

@Composable
fun MainApp(viewModel: ScannerViewModel = hiltViewModel()) {

    var showHistory by rememberSaveable {
        mutableStateOf(false)
    }

    if (showHistory) {
        HistoryScreen(
            devices = viewModel.filteredDevices.collectAsState().value,
            onBackClick = {
                showHistory = false
            }
        )
    } else {
        ScannerScreen(
            onHistoryClick = {
                showHistory = true
            }
        )
    }
}