package com.example.domain

import kotlinx.coroutines.flow.Flow

interface BleHistoryRepository {

    suspend fun getHistory(): Flow<List<BleDevice>>

    suspend fun saveDevices(devices: List<BleDevice>)

    suspend fun clearHistory()
}