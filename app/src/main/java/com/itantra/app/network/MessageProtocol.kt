package com.itantra.app.network

data class NetworkMessage(
    val type: String,
    val sender: String,
    val text: String,
    val target: String = "ALL"
)