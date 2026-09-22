package com.itantra.app.model

data class Peer(
    val name: String,
    val address: String,
    val deviceAddress: String,
    val status: String = "Available"
)

data class Group(
    val id: Long,
    val name: String,
    val members: List<String>
)

enum class AppMode {
    WALKIE,
    CALL,
    GROUP
}

enum class CallState {
    IDLE,
    CALLING,
    INCOMING,
    CONNECTED,
    ENDED
}

enum class TranslationModelStatus {
    READY,
    DOWNLOADING,
    NOT_INSTALLED,
    ERROR
}

data class EmergencyAlertData(
    val title: String,
    val message: String,
    val sender: String,
    val group: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

