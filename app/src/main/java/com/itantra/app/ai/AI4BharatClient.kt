package com.itantra.app.ai

import android.util.Base64
import org.json.JSONObject
import java.io.BufferedReader
import java.io.ByteArrayOutputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class AI4BharatClient(private val baseUrl: String) {
    private val root = baseUrl.trim().trimEnd('/')

    fun translate(text: String, source: String, target: String): String {
        val response = postJson("/translate", JSONObject().apply {
            put("text", text); put("source", source); put("target", target)
        }.toString())
        return JSONObject(response).optString("text", text)
    }

    fun synthesize(text: String, language: String): ByteArray {
        val response = postJson("/tts", JSONObject().apply {
            put("text", text); put("language", language)
        }.toString())
        val encoded = JSONObject(response).optString("audio_base64")
        if (encoded.isBlank()) error("AI4Bharat TTS returned empty audio")
        return Base64.decode(encoded, Base64.DEFAULT)
    }

    fun isHealthy(): Boolean = runCatching {
        val conn = (URL("$root/health").openConnection() as HttpURLConnection).apply {
            requestMethod = "GET"; connectTimeout = 1500; readTimeout = 1500
        }
        conn.inputStream.use { it.readBytes() }
        conn.disconnect()
        true
    }.getOrDefault(false)

    private fun postJson(path: String, body: String): String {
        val conn = (URL("$root$path").openConnection() as HttpURLConnection).apply {
            requestMethod = "POST"
            connectTimeout = 8000
            readTimeout = 60000
            doOutput = true
            setRequestProperty("Content-Type", "application/json; charset=utf-8")
            setRequestProperty("Accept", "application/json")
        }
        conn.outputStream.use { it.write(body.toByteArray(Charsets.UTF_8)) }
        val code = conn.responseCode
        val stream = if (code in 200..299) conn.inputStream else conn.errorStream
        val result = stream?.use { BufferedReader(InputStreamReader(it)).readText() }.orEmpty()
        conn.disconnect()
        if (code !in 200..299) error("AI4Bharat HTTP $code: $result")
        return result
    }
}
