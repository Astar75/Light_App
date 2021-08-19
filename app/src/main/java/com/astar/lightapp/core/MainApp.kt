package com.astar.lightapp.core

import android.app.Application
import com.astar.lightapp.data.ble.DevicesScannerDataSource
import com.astar.lightapp.data.ble.Scanner

class MainApp: Application()  {
    override fun onCreate() {
        super.onCreate()

        val scanner = Scanner.Base()
        val devicesScannerDataSource = DevicesScannerDataSource.Base(scanner)
    }
}