package com.itantra.app.network

import android.content.Context
import android.net.nsd.NsdManager
import android.net.nsd.NsdServiceInfo
import android.util.Log
import com.itantra.app.model.Peer

class LanDiscovery(private val context: Context) {
    companion object {
        private const val TAG = "iTantra-LAN"
        private const val SERVICE_TYPE = "_itantra._tcp."
        private const val SERVICE_NAME = "iTantra"
        private const val PORT = 8988
    }

    private val nsdManager = context.getSystemService(Context.NSD_SERVICE) as NsdManager
    private var registrationListener: NsdManager.RegistrationListener? = null
    private var discoveryListener: NsdManager.DiscoveryListener? = null

    var onPeerFound: (Peer) -> Unit = {}
    var onPeerLost: (String) -> Unit = {}
    var onStatus: (String) -> Unit = {}

    fun start(deviceName: String = "iTantra Device") {
        registerService(deviceName)
        startDiscovery()
    }

    private fun registerService(deviceName: String) {
        val serviceInfo = NsdServiceInfo().apply {
            serviceName = "$SERVICE_NAME-$deviceName"
            serviceType = SERVICE_TYPE
            port = PORT
        }

        registrationListener = object : NsdManager.RegistrationListener {
            override fun onServiceRegistered(info: NsdServiceInfo) {
                Log.d(TAG, "LAN service registered: ${info.serviceName}")
                onStatus("LAN discovery active")
            }
            override fun onRegistrationFailed(info: NsdServiceInfo, errorCode: Int) {
                Log.e(TAG, "Registration failed: $errorCode")
                onStatus("LAN registration failed ($errorCode)")
            }
            override fun onServiceUnregistered(info: NsdServiceInfo) {
                Log.d(TAG, "LAN service unregistered")
            }
            override fun onUnregistrationFailed(info: NsdServiceInfo, errorCode: Int) {
                Log.e(TAG, "Unregistration failed: $errorCode")
            }
        }

        runCatching {
            nsdManager.registerService(serviceInfo, NsdManager.PROTOCOL_DNS_SD, registrationListener)
        }.onFailure {
            Log.e(TAG, "Unable to register LAN service", it)
        }
    }

    private fun startDiscovery() {
        if (discoveryListener != null) return

        discoveryListener = object : NsdManager.DiscoveryListener {
            override fun onStartDiscoveryFailed(serviceType: String, errorCode: Int) {
                Log.e(TAG, "Discovery start failed: $errorCode")
                onStatus("LAN discovery failed ($errorCode)")
                discoveryListener = null
            }
            override fun onStopDiscoveryFailed(serviceType: String, errorCode: Int) {
                Log.e(TAG, "Discovery stop failed: $errorCode")
            }
            override fun onDiscoveryStarted(serviceType: String) {
                Log.d(TAG, "LAN discovery started")
                onStatus("Searching nearby iTantra devices…")
            }
            override fun onDiscoveryStopped(serviceType: String) {
                Log.d(TAG, "LAN discovery stopped")
            }
            override fun onServiceFound(serviceInfo: NsdServiceInfo) {
                if (serviceInfo.serviceType != SERVICE_TYPE) return
                if (serviceInfo.serviceName.startsWith(SERVICE_NAME)) resolveService(serviceInfo)
            }
            override fun onServiceLost(serviceInfo: NsdServiceInfo) {
                onPeerLost(serviceInfo.serviceName)
            }
        }

        runCatching {
            nsdManager.discoverServices(SERVICE_TYPE, NsdManager.PROTOCOL_DNS_SD, discoveryListener)
        }.onFailure {
            Log.e(TAG, "Unable to start LAN discovery", it)
        }
    }

    private fun resolveService(serviceInfo: NsdServiceInfo) {
        nsdManager.resolveService(serviceInfo, object : NsdManager.ResolveListener {
            override fun onResolveFailed(info: NsdServiceInfo, errorCode: Int) {
                Log.e(TAG, "Resolve failed: $errorCode")
            }
            override fun onServiceResolved(info: NsdServiceInfo) {
                val host = info.host?.hostAddress ?: return
                val name = info.serviceName.removePrefix("$SERVICE_NAME-")
                onPeerFound(
                    Peer(
                        name = name.ifBlank { "iTantra Device" },
                        address = host,
                        deviceAddress = host,
                        status = "LAN Available"
                    )
                )
            }
        })
    }

    fun stop() {
        discoveryListener?.let { runCatching { nsdManager.stopServiceDiscovery(it) } }
        discoveryListener = null
        registrationListener?.let { runCatching { nsdManager.unregisterService(it) } }
        registrationListener = null
        onStatus("LAN discovery stopped")
    }
}
