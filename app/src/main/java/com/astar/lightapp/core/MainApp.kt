package com.astar.lightapp.core

import android.app.Application
import com.astar.lightapp.data.ble.DevicesScannerDataSource
import com.astar.lightapp.data.ble.DevicesScanner

class MainApp: Application()  {
    override fun onCreate() {
        super.onCreate()

        val scanner = DevicesScanner.Base()
        val devicesScannerDataSource = DevicesScannerDataSource.Base(scanner)
    }
}