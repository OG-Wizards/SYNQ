package com.itantra.app.network

import android.util.Log
import org.json.JSONObject
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * Reusable mesh relay component retained from the completed mesh work.
 *
 * The current AppViewModel already performs equivalent TTL/duplicate relay
 * handling inline, so this class is kept as a future extraction point and
 * is not attached to the current message pipeline twice.
 */
class MeshRelay(private val transport: LocalTransport) {

    companion object {
        private const val TAG = "iTantraMesh"
        private const val MAX_HOPS = 8
        private const val PACKET = "MESH"
    }

    private val seen = ConcurrentHashMap.newKeySet<String>()

    var onMessage: (MeshMessage) -> Unit = {}

    fun send(type: String, payload: String) {
        val message = MeshMessage(
            id = UUID.randomUUID().toString(),
            origin = "local",
            type = type,
            payload = payload,
            hops = 0
        )
        seen.add(message.id)
        transport.send(encode(message))
        Log.d(TAG, "Mesh send ${message.id}")
    }

    fun receive(raw: String, senderHost: String) {
        val message = decode(raw) ?: return
        if (message.id.isBlank() || !seen.add(message.id)) return

        onMessage(message)

        if (message.hops >= MAX_HOPS) return

        transport.sendExcept(
            encode(message.copy(hops = message.hops + 1)),
            senderHost
        )
    }

    private fun encode(message: MeshMessage): String =
        JSONObject().apply {
            put("packet", PACKET)
            put("id", message.id)
            put("origin", message.origin)
            put("type", message.type)
            put("payload", message.payload)
            put("hops", message.hops)
        }.toString()

    private fun decode(raw: String): MeshMessage? =
        runCatching {
            val json = JSONObject(raw)
            if (json.optString("packet") != PACKET) return null
            MeshMessage(
                id = json.optString("id"),
                origin = json.optString("origin"),
                type = json.optString("type"),
                payload = json.optString("payload"),
                hops = json.optInt("hops", 0)
            )
        }.getOrNull()
}

data class MeshMessage(
    val id: String,
    val origin: String,
    val type: String,
    val payload: String,
    val hops: Int
)
