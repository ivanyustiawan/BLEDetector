package com.example.data.bleDevice.local.repository

import com.example.data.bleDevice.local.dao.BleDeviceDao
import com.example.data.bleDevice.local.mapper.toBleDevice
import com.example.data.bleDevice.local.mapper.toEntity
import com.example.domain.BleDevice
import com.example.domain.BleHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BleHistoryRepositoryImpl @Inject constructor(
    private val dao: BleDeviceDao
) : BleHistoryRepository {

    override suspend fun getHistory(): Flow<List<BleDevice>> {
        return dao.getAllDevices().map { entities ->
            entities
                .map { it.toBleDevice() }
                .sortedByDescending { it.lastSeenMillis }
        }
    }

    override suspend fun saveDevices(devices: List<BleDevice>) {
        dao.upsertDevices(
            devices.map { it.toEntity() }
        )
    }

    override suspend fun clearHistory() {
        dao.clearHistory()
    }
}