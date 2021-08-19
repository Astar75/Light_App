package com.astar.lightapp.data.ble

import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.*
import android.util.Log

interface Scanner {

    fun startScan()
    fun stopScan()

    class Base : Scanner {

        private val adapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        private val scanResults: MutableMap<String, ScanResult> = mutableMapOf()
        private var scanCallback: ScanCallback? = null
        private var scanner: BluetoothLeScanner? = null

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

        private fun buildSettings() = ScanSettings.Builder()
            .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
            .build()

        private fun buildFilters(): List<ScanFilter> {
            return listOf<ScanFilter>(ScanFilter.Builder().build())
        }

        private inner class ScanResultsCallback : ScanCallback() {
            override fun onScanResult(callbackType: Int, result: ScanResult) {
                scanResults[result.device.address] = result
            }

            override fun onBatchScanResults(results: MutableList<ScanResult>) {
                results.forEach { result ->
                    scanResults[result.device.address] = result
                }
            }

            override fun onScanFailed(errorCode: Int) {
                Log.e("ScanFailed", "Scan error $errorCode")
            }
        }
    }
}