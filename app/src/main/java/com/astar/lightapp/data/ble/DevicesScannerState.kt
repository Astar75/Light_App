package com.astar.lightapp.data.ble

import android.bluetooth.BluetoothDevice

sealed class DevicesScannerState {
    data class Found(val devices: List<BluetoothDevice>): DevicesScannerState()
    data class Error(val errorCode: Int): DevicesScannerState()
}