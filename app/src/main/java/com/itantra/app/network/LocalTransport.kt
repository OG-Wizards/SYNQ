package com.itantra.app.network

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.CopyOnWriteArraySet

class LocalTransport(
    private val port: Int = 8988
) {

    private val scope =
        CoroutineScope(
            SupervisorJob() +
                    Dispatchers.IO
        )

    private var server: ServerSocket? =
        null

    private val clients =
        CopyOnWriteArraySet<Socket>()

    var onMessage:
                (String, String) -> Unit =
        { _, _ -> }

    // ---------------------------------------------------------
    // START SERVER
    // ---------------------------------------------------------

    fun start() {

        if (server != null) {
            return
        }

        scope.launch {

            runCatching {

                val socketServer =
                    ServerSocket(port)

                server =
                    socketServer

                Log.d(
                    "iTantra",
                    "Transport listening on $port"
                )

                while (
                    !socketServer.isClosed
                ) {

                    val socket =
                        socketServer.accept()

                    clients.add(
                        socket
                    )

                    Log.d(
                        "iTantra",
                        "Client connected: " +
                                socket.inetAddress.hostAddress
                    )

                    listen(
                        socket
                    )
                }

            }.onFailure {

                Log.e(
                    "iTantra",
                    "Transport server error",
                    it
                )
            }
        }
    }

    // ---------------------------------------------------------
    // CONNECT
    // ---------------------------------------------------------

    fun connect(
        host: String
    ) {

        if (
            host.isBlank()
        ) {
            return
        }

        scope.launch {

            runCatching {

                val existing =
                    clients.firstOrNull {

                        it.inetAddress
                            ?.hostAddress ==
                                host &&
                                !it.isClosed
                    }

                if (
                    existing != null
                ) {
                    return@runCatching
                }

                val socket =
                    Socket(
                        host,
                        port
                    )

                clients.add(
                    socket
                )

                Log.d(
                    "iTantra",
                    "Connected to $host:$port"
                )

                listen(
                    socket
                )

            }.onFailure {

                Log.e(
                    "iTantra",
                    "Connection failed: $host",
                    it
                )
            }
        }
    }

    // ---------------------------------------------------------
    // SEND TO EVERY CONNECTED DEVICE
    // ---------------------------------------------------------

    fun send(
        text: String
    ) {

        if (
            text.isBlank()
        ) {
            return
        }

        scope.launch {

            clients
                .toList()
                .forEach { socket ->

                    sendToSocket(
                        socket,
                        text
                    )
                }
        }
    }

    // ---------------------------------------------------------
    // SEND TO EVERY DEVICE EXCEPT ONE
    // ---------------------------------------------------------

    fun sendExcept(
        text: String,
        excludedHost: String
    ) {

        if (
            text.isBlank()
        ) {
            return
        }

        scope.launch {

            clients
                .toList()
                .forEach { socket ->

                    val host =
                        socket.inetAddress
                            ?.hostAddress
                            .orEmpty()

                    if (
                        host != excludedHost
                    ) {

                        sendToSocket(
                            socket,
                            text
                        )
                    }
                }
        }
    }

    // ---------------------------------------------------------
    // SEND TO SPECIFIC DEVICE
    // ---------------------------------------------------------

    fun sendTo(
        text: String,
        host: String
    ) {

        if (
            text.isBlank() ||
            host.isBlank()
        ) {
            return
        }

        scope.launch {

            runCatching {

                val socket =
                    clients.firstOrNull {

                        it.inetAddress
                            ?.hostAddress ==
                                host &&
                                !it.isClosed
                    }

                if (
                    socket != null
                ) {

                    sendToSocket(
                        socket,
                        text
                    )

                } else {

                    val newSocket = Socket(host, port)
                    clients.add(newSocket)
                    listen(newSocket)
                    sendToSocket(newSocket, text)
                }

            }.onFailure {

                Log.e(
                    "iTantra",
                    "Target send failed: $host",
                    it
                )
            }
        }
    }

    // ---------------------------------------------------------
    // SEND TO SOCKET
    // ---------------------------------------------------------

    private fun sendToSocket(
        socket: Socket,
        text: String
    ) {

        runCatching {

            PrintWriter(
                socket.getOutputStream(),
                true
            ).println(
                text
            )

        }.onFailure {

            clients.remove(
                socket
            )

            runCatching {
                socket.close()
            }
        }
    }

    // ---------------------------------------------------------
    // RECEIVE
    // ---------------------------------------------------------

    private fun listen(
        socket: Socket
    ) {

        scope.launch {

            runCatching {

                BufferedReader(
                    InputStreamReader(
                        socket.getInputStream()
                    )
                ).useLines { lines ->

                    lines.forEach { message ->

                        val host =
                            socket.inetAddress
                                ?.hostAddress
                                .orEmpty()

                        Log.d(
                            "iTantra",
                            "Received from $host: $message"
                        )

                        onMessage(
                            message,
                            host
                        )
                    }
                }

            }.onFailure {

                Log.e(
                    "iTantra",
                    "Socket listener error",
                    it
                )
            }

            clients.remove(
                socket
            )

            runCatching {
                socket.close()
            }
        }
    }

    // ---------------------------------------------------------
    // STOP
    // ---------------------------------------------------------

    fun stop() {

        runCatching {
            server?.close()
        }

        server =
            null

        clients
            .toList()
            .forEach {

                runCatching {
                    it.close()
                }
            }

        clients.clear()
    }
}