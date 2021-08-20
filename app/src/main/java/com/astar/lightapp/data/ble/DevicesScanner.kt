package com.astar.lightapp.data.ble

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.*
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

interface DevicesScanner {

    fun startScan()
    fun stopScan()

    fun scanState(): StateFlow<DevicesScannerState>

    class Base : DevicesScanner {

        private val adapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        private val scanResults: MutableMap<String, BluetoothDevice> = mutableMapOf()
        private var scanCallback: ScanCallback? = null
        private var scanner: BluetoothLeScanner? = null

        private val scannerState = MutableStateFlow<DevicesScannerState>(DevicesScannerState.Found(emptyList()))

        private val filters: List<ScanFilter>
        private val settings: ScanSettings

        init {
            settings = buildSettings()
            filters = buildFilters()
        }

        override fun startScan() {
            if (scanCallback == null) {
                scanner = adapter.bluetoothLeScanner
                scanCallback = ScanResultsCallback()
                scanner?.startScan(filters, settings, scanCallback)
            } else {
                Log.e("Scan", "Already scan")
            }
        }

        override fun stopScan() {
            if (scanCallback != null) {
                scanner?.stopScan(scanCallback)
                scanCallback = null
                scanner = null
            }
        }

        override fun scanState(): StateFlow<DevicesScannerState> = scannerState

        private fun buildSettings() = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        private fun buildFilters(): List<ScanFilter> {
            return listOf<ScanFilter>(ScanFilter.Builder().build())
        }

        private fun emitDevices(scanResults: MutableMap<String, BluetoothDevice>) {
            scannerState.tryEmit(DevicesScannerState.Found(scanResults.values.toList()))
        }

        private inner class ScanResultsCallback : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                scanResults[result.device.address] = result.device
                emitDevices(scanResults)
            }

            override fun onBatchScanResults(results: MutableList<ScanResult>) {
                results.forEach { result ->
                    scanResults[result.device.address] = result.device
                }
                emitDevices(scanResults)
            }

            override fun onScanFailed(errorCode: Int) {
                scannerState.tryEmit(DevicesScannerState.Error(errorCode))
            }
        }
    }
}