package com.dalydays.advancedreboot

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private var mainActivityJob = Job()
    private val mainActivityScope = CoroutineScope(Dispatchers.IO + mainActivityJob)

    enum class RebootType {
        PowerOff,
        System,
        Recovery,
        Bootloader
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_power_off.setOnClickListener {
            confirmReboot("Are you sure you want to power off?", RebootType.PowerOff)
        }
        btn_reboot_system.setOnClickListener {
            confirmReboot("Are you sure you want to reboot the System?", RebootType.System)
        }
        btn_reboot_recovery.setOnClickListener {
            confirmReboot("Are you sure you want to reboot to Recovery mode?", RebootType.Recovery)
        }
        btn_reboot_bootloader.setOnClickListener {
            confirmReboot("Are you sure you want to reboot to Bootloader?", RebootType.Bootloader)
        }
    }

    private fun confirmReboot(message: String, type: RebootType) {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage(message)
        alertDialogBuilder.setPositiveButton("Yes") { _, _ ->
            when (type) {
                RebootType.PowerOff -> powerOff()
                RebootType.System -> rebootSystem()
                RebootType.Recovery -> rebootToRecovery()
                RebootType.Bootloader -> rebootToBootloader()
            }
        }
        alertDialogBuilder.setNegativeButton("Cancel") { _, _ ->
            // handle this route (do nothing)
        }
        alertDialogBuilder.show()
    }

    private fun powerOff() {
        mainActivityScope.launch {
            Runtime.getRuntime().exec("su -c reboot -p")
        }
    }

    private fun rebootSystem() {
        mainActivityScope.launch {
            Runtime.getRuntime().exec("su -c reboot")
        }
    }

    private fun rebootToRecovery() {
        mainActivityScope.launch {
            Runtime.getRuntime().exec("su -c reboot recovery")
        }
    }

    private fun rebootToBootloader() {
        mainActivityScope.launch {
            Runtime.getRuntime().exec("su -c reboot bootloader")
        }
    }
}
