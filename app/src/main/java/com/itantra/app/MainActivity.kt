package com.itantra.app

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.viewmodel.compose.viewModel
import com.itantra.app.ui.iTantraApp

class MainActivity: ComponentActivity(){
    private val permissionLauncher=registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()){}
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        requestPermissionsIfNeeded()
        setContent{ val vm:AppViewModel=viewModel(); iTantraApp(vm) }
    }
    private fun requestPermissionsIfNeeded(){
        val list=mutableListOf(Manifest.permission.RECORD_AUDIO)
        if(Build.VERSION.SDK_INT>=33) list+=Manifest.permission.NEARBY_WIFI_DEVICES else list+=Manifest.permission.ACCESS_FINE_LOCATION
        permissionLauncher.launch(list.toTypedArray())
    }
}
