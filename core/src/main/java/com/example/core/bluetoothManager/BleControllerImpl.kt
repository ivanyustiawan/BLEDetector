package com.example.core.bluetoothManager

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.IntentFilter
import com.example.domain.BleController
import com.example.domain.BleDevice
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.collections.plus

@SuppressLint("MissingPermission")
class BleControllerImpl @Inject constructor(
    private val context: Context
) : BleController {

    private val bluetoothManager by lazy {
        context.getSystemService(BluetoothManager::class.java)
    }
    private val bluetoothAdapter by lazy {
        bluetoothManager?.adapter
    }

    private val _scannedDevices = MutableStateFlow<List<BleDevice>>(emptyList())

    override val scannedDevices: StateFlow<List<BleDevice>>
        get() = _scannedDevices.asStateFlow()

    private val foundDeviceReceiver = FoundDeviceReceiver { device, rssi ->
        _scannedDevices.update { devices ->
            val newDevice = device.toBleDevice(rssi = rssi)
            (devices.filterNot { it.address == newDevice.address } + newDevice)
                .sortedByDescending { it.rssi }
        }
    }


    override fun startDiscovery() {
        context.registerReceiver(
            foundDeviceReceiver,
            IntentFilter(BluetoothDevice.ACTION_FOUND)
        )
        bluetoothAdapter?.startDiscovery()
    }

    override fun stopDiscovery() {
        bluetoothAdapter?.cancelDiscovery()
    }

    override fun release() {
        context.unregisterReceiver(foundDeviceReceiver)
    }

}