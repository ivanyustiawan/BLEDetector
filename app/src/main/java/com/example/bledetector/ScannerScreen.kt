package com.example.bledetector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ScannerScreen(
    viewModel: ScannerViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

    MaterialTheme {
        Scaffold(
            topBar = @Composable {
                ScannerToolbar(
                    onHistoryClick = {
                        // TODO: open history page
                    }
                )
            },
            contentWindowInsets = WindowInsets.safeDrawing
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = { viewModel.startScanning() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state is ScannerState.Scanning) {
                            Color(0xFFE53935)
                        } else {
                            Color(0xFF1976D2)
                        }
                    )
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = if (state is ScannerState.Scanning) {
                                Icons.Default.Stop
                            } else {
                                Icons.Default.Bluetooth
                            },
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                            tint = Color.White
                        )

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = if (state is ScannerState.Scanning) "STOP SCANNING" else "START SCANNING",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                when (val currentState = state) {
                    is ScannerState.Idle -> {}

                    is ScannerState.Empty -> {

                    }

                    is ScannerState.Scanning -> {
                        Spacer(modifier = Modifier.height(64.dp))
                        ScanningIndicator()
                    }

                    is ScannerState.Success -> {
                        Spacer(modifier = Modifier.height(16.dp))

                        Text(
                            text = "Found ${currentState.devices.size} devices",
                            style = MaterialTheme.typography.titleMedium
                        )

                        ScannerSearchBar(
                            query = "",
                            onQueryChange = {},
                            modifier = Modifier.padding(16.dp)
                        )

                        LazyColumn(
                            modifier = Modifier.padding(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            ),
                            verticalArrangement =
                                Arrangement.spacedBy(12.dp)
                        ) {
                            items(
                                items = currentState.devices,
                                key = { it.address }
                            ) { device ->
                                BleDeviceItem(device = device)
                            }
                        }
                    }

                    is ScannerState.Error -> {
//                        Text(
//                            text = currentState.message,
//                            color = MaterialTheme.colorScheme.error
//                        )
//
//                        Spacer(modifier = Modifier.height(8.dp))
//
//                        OutlinedButton(
//                            onClick = { viewModel.resetScanning() }
//                        ) {
//                            Text("Try Again")
//                        }
                    }


                }

            }
        }
    }
}

@Preview
@Composable
fun ScannerScreenPreview() {
    ScannerScreen()
}
