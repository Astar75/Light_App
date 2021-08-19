package com.astar.lightapp.data.ble

interface DevicesScannerDataSource {

    fun startScan()
    fun stopScan()

    class Base(private val scanner: Scanner): DevicesScannerDataSource {
        override fun startScan() = scanner.startScan()
        override fun stopScan() = scanner.stopScan()
    }
}