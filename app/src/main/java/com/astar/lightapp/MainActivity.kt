package com.astar.lightapp

import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.astar.lightapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()
        showMessageEnableBluetooth()
    }

    private fun showMessageEnableBluetooth() {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        if (!adapter.isEnabled) {
            AlertDialog.Builder(this).apply {
                setTitle(getString(R.string.enable_bluetooth_title))
                setMessage(getString(R.string.enable_bluetooth_message))
                setNegativeButton(getString(R.string.cancel)) { _, _ -> finish() }
                setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                    enableBluetooth()
                    dialog.dismiss()
                }
                setCancelable(false)
            }.show()
        }
    }

    private fun enableBluetooth() {
        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        enableBluetoothResult.launch(intent)
    }

    private val enableBluetoothResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode != RESULT_OK) {
            showMessageEnableBluetooth()
        }
    }
}