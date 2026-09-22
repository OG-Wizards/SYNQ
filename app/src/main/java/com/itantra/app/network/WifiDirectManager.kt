package com.itantra.app.network

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.NetworkInfo
import android.net.wifi.p2p.WifiP2pConfig
import android.net.wifi.p2p.WifiP2pDeviceList
import android.net.wifi.p2p.WifiP2pInfo
import android.net.wifi.p2p.WifiP2pManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.itantra.app.model.Peer

class WifiDirectManager(
    private val context: Context
) {

    private val manager =
        context.getSystemService(
            Context.WIFI_P2P_SERVICE
        ) as WifiP2pManager

    private val channel =
        manager.initialize(
            context,
            context.mainLooper,
            null
        )

    private var receiver:
            BroadcastReceiver? = null

    private var started =
        false

    private var isConnecting = false
    private var isConnected = false
    private val attemptedAddresses = mutableSetOf<String>()

    var onPeers:
                (List<Peer>) -> Unit = {}

    var onConnected:
                (String) -> Unit = {}

    var onStatus:
                (String) -> Unit = {}

    // ---------------------------------------------------------
    // START
    // ---------------------------------------------------------

    fun start() {

        if (started) {
            return
        }

        if (!hasPermission()) {

            onStatus(
                "Nearby Wi-Fi permission required"
            )

            return
        }

        started = true

        receiver =
            object : BroadcastReceiver() {

                override fun onReceive(
                    receiverContext: Context,
                    intent: Intent
                ) {

                    when (
                        intent.action
                    ) {

                        // -------------------------------------
                        // WIFI P2P STATE
                        // -------------------------------------

                        WifiP2pManager
                            .WIFI_P2P_STATE_CHANGED_ACTION -> {

                            val enabled =
                                intent.getIntExtra(
                                    WifiP2pManager
                                        .EXTRA_WIFI_STATE,
                                    -1
                                ) ==
                                        WifiP2pManager
                                            .WIFI_P2P_STATE_ENABLED

                            if (enabled) {

                                onStatus(
                                    "Wi-Fi Direct enabled"
                                )

                            } else {

                                onStatus(
                                    "Wi-Fi Direct disabled"
                                )
                            }
                        }

                        // -------------------------------------
                        // PEERS CHANGED
                        // -------------------------------------

                        WifiP2pManager
                            .WIFI_P2P_PEERS_CHANGED_ACTION -> {

                            onStatus(
                                "Wi-Fi Direct peers updated"
                            )

                            requestPeers()
                        }

                        // -------------------------------------
                        // CONNECTION CHANGED
                        // -------------------------------------

                        WifiP2pManager
                            .WIFI_P2P_CONNECTION_CHANGED_ACTION -> {

                            val networkInfo =
                                if (
                                    Build.VERSION.SDK_INT >=
                                    Build.VERSION_CODES.TIRAMISU
                                ) {

                                    intent.getParcelableExtra(
                                        WifiP2pManager
                                            .EXTRA_NETWORK_INFO,
                                        NetworkInfo::class.java
                                    )

                                } else {

                                    @Suppress(
                                        "DEPRECATION"
                                    )

                                    intent.getParcelableExtra<NetworkInfo>(
                                        WifiP2pManager
                                            .EXTRA_NETWORK_INFO
                                    )
                                }

                            if (
                                networkInfo?.isConnected == true
                            ) {
                                isConnected = true
                                isConnecting = false

                                manager.requestConnectionInfo(
                                    channel
                                ) { info: WifiP2pInfo ->

                                    if (
                                        info.groupFormed
                                    ) {

                                        val host =
                                            if (
                                                info.isGroupOwner
                                            ) {

                                                "127.0.0.1"

                                             } else {

                                                info.groupOwnerAddress
                                                    ?.hostAddress
                                                    .orEmpty()
                                            }

                                        onConnected(
                                            host
                                        )

                                        onStatus(
                                            "Connected"
                                        )
                                    }
                                }

                            } else {
                                isConnected = false
                                isConnecting = false

                                onStatus(
                                    "Disconnected"
                                )
                            }
                        }

                        // -------------------------------------
                        // THIS DEVICE CHANGED
                        // -------------------------------------

                        WifiP2pManager
                            .WIFI_P2P_THIS_DEVICE_CHANGED_ACTION -> {

                            onStatus(
                                "Wi-Fi Direct device state changed"
                            )
                        }

                        // -------------------------------------
                        // DISCOVERY CHANGED
                        // -------------------------------------

                        WifiP2pManager
                            .WIFI_P2P_DISCOVERY_CHANGED_ACTION -> {

                            val discoveryState =
                                intent.getIntExtra(
                                    WifiP2pManager
                                        .EXTRA_DISCOVERY_STATE,
                                    -1
                                )

                            when (
                                discoveryState
                            ) {

                                WifiP2pManager
                                    .WIFI_P2P_DISCOVERY_STARTED -> {

                                    onStatus(
                                        "Scanning for Wi-Fi Direct peers…"
                                    )
                                }

                                WifiP2pManager
                                    .WIFI_P2P_DISCOVERY_STOPPED -> {

                                    onStatus(
                                        "Wi-Fi Direct scan stopped"
                                    )
                                }
                            }
                        }
                    }
                }
            }

        // -----------------------------------------------------
        // INTENT FILTER
        // -----------------------------------------------------

        val filter =
            IntentFilter().apply {

                addAction(
                    WifiP2pManager
                        .WIFI_P2P_STATE_CHANGED_ACTION
                )

                addAction(
                    WifiP2pManager
                        .WIFI_P2P_PEERS_CHANGED_ACTION
                )

                addAction(
                    WifiP2pManager
                        .WIFI_P2P_CONNECTION_CHANGED_ACTION
                )

                addAction(
                    WifiP2pManager
                        .WIFI_P2P_THIS_DEVICE_CHANGED_ACTION
                )

                addAction(
                    WifiP2pManager
                        .WIFI_P2P_DISCOVERY_CHANGED_ACTION
                )
            }

        // -----------------------------------------------------
        // REGISTER RECEIVER
        // -----------------------------------------------------

        if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {

            context.registerReceiver(
                receiver,
                filter,
                Context.RECEIVER_NOT_EXPORTED
            )

        } else {

            @Suppress(
                "DEPRECATION"
            )

            context.registerReceiver(
                receiver,
                filter
            )
        }

        onStatus(
            "Starting Wi-Fi Direct…"
        )

        discover()
    }

    // ---------------------------------------------------------
    // DISCOVER
    // ---------------------------------------------------------

    fun discover() {

        if (!hasPermission()) {

            onStatus(
                "Nearby Wi-Fi permission required"
            )

            return
        }

        attemptedAddresses.clear()

        onStatus(
            "Scanning"
        )

        manager.discoverPeers(
            channel,
            object :
                WifiP2pManager.ActionListener {

                override fun onSuccess() {

                    onStatus(
                        "Scanning"
                    )

                    requestPeers()
                }

                override fun onFailure(
                    reason: Int
                ) {

                    val message =
                        when (
                            reason
                        ) {

                            WifiP2pManager.BUSY ->
                                "Wi-Fi Direct busy"

                            WifiP2pManager.ERROR ->
                                "Wi-Fi Direct error"

                            WifiP2pManager.P2P_UNSUPPORTED ->
                                "Wi-Fi Direct unsupported"

                            else ->
                                "Peer scan unavailable ($reason)"
                        }

                    onStatus(
                        message
                    )
                }
            }
        )
    }

    // ---------------------------------------------------------
    // REQUEST PEERS
    // ---------------------------------------------------------

    private fun requestPeers() {

        if (!hasPermission()) {
            return
        }

        manager.requestPeers(
            channel
        ) { list: WifiP2pDeviceList ->

            publish(
                list
            )
        }
    }

    // ---------------------------------------------------------
    // CONNECT
    // ---------------------------------------------------------

    fun connect(
        peer: Peer
    ) {

        if (!hasPermission()) {

            onStatus(
                "Nearby Wi-Fi permission required"
            )

            return
        }

        if (isConnecting) {
            return
        }

        isConnecting = true
        attemptedAddresses.add(peer.deviceAddress)

        val config =
            WifiP2pConfig().apply {

                deviceAddress =
                    peer.deviceAddress
            }

        onStatus(
            "Connecting to ${peer.name}…"
        )

        manager.connect(
            channel,
            config,
            object :
                WifiP2pManager.ActionListener {

                override fun onSuccess() {

                    onStatus(
                        "Connecting to ${peer.name}…"
                    )
                }

                override fun onFailure(
                    reason: Int
                ) {
                    isConnecting = false

                    val message =
                        when (
                            reason
                        ) {

                            WifiP2pManager.BUSY ->
                                "Wi-Fi Direct busy"

                            WifiP2pManager.ERROR ->
                                "Wi-Fi Direct error"

                            WifiP2pManager.P2P_UNSUPPORTED ->
                                "Wi-Fi Direct unsupported"

                            else ->
                                "Connect failed ($reason)"
                        }

                    onStatus(
                        message
                    )
                }
            }
        )
    }

    // ---------------------------------------------------------
    // STOP
    // ---------------------------------------------------------

    fun stop() {

        receiver?.let {

            runCatching {

                context.unregisterReceiver(
                    it
                )
            }
        }

        receiver =
            null

        started =
            false
    }

    // ---------------------------------------------------------
    // PUBLISH PEERS
    // ---------------------------------------------------------

    private fun publish(
        list: WifiP2pDeviceList
    ) {

        val peers =
            list.deviceList.map {

                Peer(
                    name =
                        it.deviceName.ifBlank {
                            "Wi-Fi Direct Device"
                        },

                    address =
                        "Wi-Fi Direct",

                    deviceAddress =
                        it.deviceAddress,

                    status =
                        "Available"
                )
            }

        onPeers(
            peers
        )

        if (
            peers.isEmpty()
        ) {

            onStatus(
                "No peers found"
            )

        } else {

            onStatus(
                "${peers.size} peer(s) found"
            )

            if (!isConnected && !isConnecting) {
                val unattempted = peers.firstOrNull { it.deviceAddress !in attemptedAddresses }
                if (unattempted != null) {
                    connect(unattempted)
                }
            }
        }
    }

    // ---------------------------------------------------------
    // PERMISSION
    // ---------------------------------------------------------

    private fun hasPermission(): Boolean {

        return if (
            Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.TIRAMISU
        ) {

            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.NEARBY_WIFI_DEVICES
            ) ==
                    PackageManager.PERMISSION_GRANTED

        } else {

            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) ==
                    PackageManager.PERMISSION_GRANTED
        }
    }
}