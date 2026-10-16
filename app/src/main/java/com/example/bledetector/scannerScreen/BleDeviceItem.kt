package com.example.bledetector

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bluetooth
import androidx.compose.material.icons.filled.NetworkCell
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardDefaults.cardColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.BleDevice
import com.example.core.helper.DateHelper
import java.util.Locale

@Composable
fun BleDeviceItem(
    device: BleDevice,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Bluetooth,
                contentDescription = "Bluetooth device",
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = device.name ?: "Unknown Device",
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = device.address,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Text(
                    text = "RSSI: ${device.rssi} dBm",
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = "Last seen: ${DateHelper.formatLastSeen(device.lastSeenMillis)}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            SignalStrengthIndicator(rssi = device.rssi)
        }
    }
}

@Composable
private fun SignalStrengthIndicator(rssi: Int) {
    val (label, color, distance) = when {
        rssi >= -30 -> Triple(
            "Very Strong",
            Color(0xFF2E7D32),
            "< 1 meter"
        )

        rssi >= -50 -> Triple(
            "Strong",
            Color(0xFF43A047),
            "1–3 meter"
        )

        rssi >= -70 -> Triple(
            "Good",
            Color(0xFFF9A825),
            "3–10 meter"
        )

        rssi >= -80 -> Triple(
            "Weak",
            Color(0xFFEF6C00),
            "10–20 meter"
        )

        rssi >= -90 -> Triple(
            "Very Weak",
            Color(0xFFC62828),
            "> 20 meter"
        )

        else -> Triple(
            "Lost",
            Color(0xFF757575),
            "Out of range"
        )
    }

    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.NetworkCell,
            contentDescription = "Signal strength: $label",
            modifier = Modifier.size(24.dp),
            tint = color
        )

        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = color
        )

        Text(
            text = "Distance: $distance",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(
    name = "Strong Signal",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
private fun BleDeviceItemStrongPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            BleDeviceItem(
                device = BleDevice(
                    address = "AA:BB:CC:11:22:33",
                    name = "JBL Speaker",
                    rssi = -42,
                    lastSeenMillis = System.currentTimeMillis()
                )
            )
        }
    }
}

@Preview(
    name = "Medium Signal",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
private fun BleDeviceItemMediumPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            BleDeviceItem(
                device = BleDevice(
                    address = "AA:BB:CC:11:22:44",
                    name = "Mi Band 8",
                    rssi = -60,
                    lastSeenMillis = System.currentTimeMillis()
                )
            )
        }
    }
}

@Preview(
    name = "Weak Signal - Unknown Device",
    showBackground = true,
    backgroundColor = 0xFFF5F5F5
)
@Composable
private fun BleDeviceItemWeakPreview() {
    MaterialTheme {
        Surface(modifier = Modifier.padding(16.dp)) {
            BleDeviceItem(
                device = BleDevice(
                    address = "AA:BB:CC:11:22:55",
                    name = "null",
                    rssi = -85,
                    lastSeenMillis = System.currentTimeMillis()
                )
            )
        }
    }
}
