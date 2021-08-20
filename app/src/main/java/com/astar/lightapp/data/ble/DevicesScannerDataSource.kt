package com.astar.lightapp.data.ble

import kotlinx.coroutines.flow.StateFlow

interface DevicesScannerDataSource {

    fun startScan()
    fun stopScan()
    fun scanState(): StateFlow<DevicesScannerState>

    class Base(private val scanner: DevicesScanner): DevicesScannerDataSource {
        override fun startScan() = scanner.startScan()
        override fun stopScan() = scanner.stopScan()
        override fun scanState() = scanner.scanState()
    }
}