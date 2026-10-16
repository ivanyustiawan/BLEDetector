package com.example.bledetector

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.example.bledetector.historyScreen.HistoryScreen

@Composable
fun MainApp() {

    var showHistory by rememberSaveable {
        mutableStateOf(false)
    }

    if (showHistory) {
        HistoryScreen(
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