package com.itantra.app

import android.app.Application
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.media.ToneGenerator
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioRecord
import android.media.AudioTrack
import android.media.MediaRecorder
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.common.model.RemoteModelManager
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.TranslateRemoteModel
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.Translator
import com.google.mlkit.nl.translate.TranslatorOptions
import com.itantra.app.data.GroupStore
import com.itantra.app.model.AppMode
import com.itantra.app.model.CallState
import com.itantra.app.model.Group
import com.itantra.app.model.Peer
import com.itantra.app.model.TranslationModelStatus
import com.itantra.app.network.LocalTransport
import com.itantra.app.network.WifiDirectManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import org.json.JSONObject
import android.media.Ringtone
import com.itantra.app.model.EmergencyAlertData
import kotlinx.coroutines.Job
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext
import java.util.Locale
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class AppViewModel(app: Application) : AndroidViewModel(app), TextToSpeech.OnInitListener {

    private val application = app

    // ---------------------------------------------------------
    // SPEECH TRANSCRIPT
    // ---------------------------------------------------------
    private val _transcript = MutableStateFlow("")
    val transcript: StateFlow<String> = _transcript.asStateFlow()

    // ---------------------------------------------------------
    // SPEECH RECOGNIZER
    // ---------------------------------------------------------
    private var speechRecognizer: SpeechRecognizer? = null
    private val speechHandler = Handler(Looper.getMainLooper())
    private var speechListening = false
    private var speechWaitingForResult = false
    private var speechLanguage = "en"
    private var latestPartialSpeech = ""

    // ---------------------------------------------------------
    // PREFERENCES
    // ---------------------------------------------------------
    private val prefs = app.getSharedPreferences("itantra_preferences", Application.MODE_PRIVATE)

    // ---------------------------------------------------------
    // NETWORK
    // ---------------------------------------------------------
    private val wifi = WifiDirectManager(app)
    private val transport = LocalTransport()
    private val groupsStore = GroupStore(app)

    // ---------------------------------------------------------
    // UI STATE
    // ---------------------------------------------------------
    private val _mode = MutableStateFlow(AppMode.WALKIE)
    val mode: StateFlow<AppMode> = _mode.asStateFlow()

    private val _peers = MutableStateFlow<List<Peer>>(emptyList())
    val peers: StateFlow<List<Peer>> = _peers.asStateFlow()

    private val _groups = MutableStateFlow(groupsStore.all())
    val groups: StateFlow<List<Group>> = _groups.asStateFlow()

    private val _messages = MutableStateFlow(demoMessages(prefs.getString("language", "en") ?: "en"))
    val messages: StateFlow<List<String>> = _messages.asStateFlow()

    private val _status = MutableStateFlow("Mesh standby · Wi-Fi Direct")
    val status: StateFlow<String> = _status.asStateFlow()

    private val _connected = MutableStateFlow(false)
    val connected: StateFlow<Boolean> = _connected.asStateFlow()

    private val _selectedPeer = MutableStateFlow<Peer?>(null)
    val selectedPeer: StateFlow<Peer?> = _selectedPeer.asStateFlow()

    private val _language = MutableStateFlow(prefs.getString("language", "en") ?: "en")
    val language: StateFlow<String> = _language.asStateFlow()

    private val _darkTheme = MutableStateFlow(prefs.getBoolean("dark_theme", true))
    val darkTheme: StateFlow<Boolean> = _darkTheme.asStateFlow()

    private val _aiServerUrl = MutableStateFlow("ON-DEVICE AI")
    val aiServerUrl: StateFlow<String> = _aiServerUrl.asStateFlow()

    // ---------------------------------------------------------
    // CALL STATE
    // ---------------------------------------------------------
    private val _callState = MutableStateFlow(CallState.IDLE)
    val callState: StateFlow<CallState> = _callState.asStateFlow()

    private val _callingPeer = MutableStateFlow<Peer?>(null)
    val callingPeer: StateFlow<Peer?> = _callingPeer.asStateFlow()

    // ---------------------------------------------------------
    // TRANSLATION MODEL MANAGEMENT
    // ---------------------------------------------------------
    private val modelManager = RemoteModelManager.getInstance()
    private val _modelStatus = MutableStateFlow(TranslationModelStatus.READY)
    val modelStatus: StateFlow<TranslationModelStatus> = _modelStatus.asStateFlow()

    // ---------------------------------------------------------
    // ANALYTICS
    // ---------------------------------------------------------
    private val _translationLatencyMs = MutableStateFlow<Long?>(null)
    val translationLatencyMs: StateFlow<Long?> = _translationLatencyMs.asStateFlow()

    private val _ttsLatencyMs = MutableStateFlow<Long?>(null)
    val ttsLatencyMs: StateFlow<Long?> = _ttsLatencyMs.asStateFlow()

    private val _e2eLatencyMs = MutableStateFlow<Long?>(null)
    val e2eLatencyMs: StateFlow<Long?> = _e2eLatencyMs.asStateFlow()

    private val _lastPayloadBytes = MutableStateFlow(0)
    val lastPayloadBytes: StateFlow<Int> = _lastPayloadBytes.asStateFlow()

    private val _processedMessages = MutableStateFlow(0)
    val processedMessages: StateFlow<Int> = _processedMessages.asStateFlow()

    private val _lastActivityAt = MutableStateFlow<Long?>(null)
    val lastActivityAt: StateFlow<Long?> = _lastActivityAt.asStateFlow()

    private val _activeEmergencyAlert = MutableStateFlow<EmergencyAlertData?>(null)
    val activeEmergencyAlert: StateFlow<EmergencyAlertData?> = _activeEmergencyAlert.asStateFlow()

    private var alertJob: Job? = null
    private var currentRingtone: Ringtone? = null
    private var targetWalkieGroup: Group? = null

    private val ttsStartTimes = ConcurrentHashMap<String, Long>()
    private val e2eStartTimes = ConcurrentHashMap<String, Long>()

    // ---------------------------------------------------------
    // CONNECTION & IDENTIFIERS
    // ---------------------------------------------------------
    private var connectedHost = ""
    private val deviceId = UUID.randomUUID().toString()
    private val seenPacketIds = ConcurrentHashMap.newKeySet<String>()

    // ---------------------------------------------------------
    // CALL AUDIO (PCM STREAMING)
    // ---------------------------------------------------------
    private var callRecording: AudioRecord? = null
    private var callPlaying: AudioTrack? = null
    private var callRecordThread: Thread? = null

    // ---------------------------------------------------------
    // TTS & TRANSLATION
    // ---------------------------------------------------------
    private var tts: TextToSpeech? = null
    private val translators = mutableMapOf<String, Translator>()

    init {
        tts = TextToSpeech(app, this)
        setupSpeechRecognizer()

        transport.onMessage = { raw, host ->
            handle(raw, host)
        }
        transport.start()

        wifi.onPeers = { peerList ->
            _peers.value = peerList
        }

        wifi.onStatus = { message ->
            _status.value = message
        }

        wifi.onConnected = { host ->
            connectedHost = host
            _connected.value = true
            if (host.isNotBlank() && host != "127.0.0.1") {
                transport.connect(host)
            }
        }

        wifi.start()
        checkModelStatus(_language.value)
        if (_language.value != "en") {
            downloadCurrentModel()
        }
    }

    // =========================================================
    // SPEECH RECOGNIZER
    // =========================================================
    private fun setupSpeechRecognizer() {
        speechHandler.post {
            if (!SpeechRecognizer.isRecognitionAvailable(application)) {
                _status.value = "Speech recognition unavailable"
                return@post
            }

            speechRecognizer = runCatching {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S &&
                    SpeechRecognizer.isOnDeviceRecognitionAvailable(application)
                ) {
                    SpeechRecognizer.createOnDeviceSpeechRecognizer(application)
                } else {
                    SpeechRecognizer.createSpeechRecognizer(application)
                }
            }.getOrElse {
                SpeechRecognizer.createSpeechRecognizer(application)
            }

            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {
                    speechListening = true
                    speechWaitingForResult = false
                    _status.value = UiStrings(speechLanguage).listening
                }

                override fun onBeginningOfSpeech() {
                    speechListening = true
                }

                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}

                override fun onEndOfSpeech() {
                    speechListening = false
                }

                override fun onError(error: Int) {
                    speechListening = false
                    speechWaitingForResult = false

                    val message = when (error) {
                        SpeechRecognizer.ERROR_AUDIO -> "Speech audio error"
                        SpeechRecognizer.ERROR_CLIENT -> "Speech recognizer restarted"
                        SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Microphone permission required"
                        SpeechRecognizer.ERROR_NETWORK -> "Speech recognition network error"
                        SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Speech recognition timeout"
                        SpeechRecognizer.ERROR_NO_MATCH -> "No speech recognized"
                        SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "Speech recognizer busy"
                        SpeechRecognizer.ERROR_SERVER -> "Speech recognition server error"
                        SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech detected"
                        else -> "Speech error: $error"
                    }
                    _status.value = message

                    if (error == SpeechRecognizer.ERROR_CLIENT) {
                        recreateSpeechRecognizer()
                    }
                }

                override fun onResults(results: Bundle?) {
                    speechListening = false
                    speechWaitingForResult = false

                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    val finalText = matches?.firstOrNull()?.trim().orEmpty()
                    val text = if (finalText.isNotBlank()) finalText else latestPartialSpeech.trim()
                    latestPartialSpeech = ""

                    if (text.isNotBlank()) {
                        _transcript.value = text
                        sendRecognizedWalkieText(text)
                    } else {
                        _status.value = UiStrings(_language.value).standby
                    }
                }

                override fun onPartialResults(partialResults: Bundle?) {
                    val matches = partialResults?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    val text = matches?.firstOrNull()?.trim().orEmpty()
                    if (text.isNotBlank()) {
                        latestPartialSpeech = text
                        _transcript.value = text
                    }
                }

                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        }
    }

    private fun recreateSpeechRecognizer() {
        speechHandler.post {
            runCatching { speechRecognizer?.cancel() }
            runCatching { speechRecognizer?.destroy() }
            speechRecognizer = null
            speechListening = false
            speechWaitingForResult = false
            latestPartialSpeech = ""
            setupSpeechRecognizer()
        }
    }

    private fun languageToSpeechLocale(code: String): String = when (code.lowercase()) {
        "mr" -> "mr-IN"
        "hi" -> "hi-IN"
        "gu" -> "gu-IN"
        "bn" -> "bn-IN"
        "ta" -> "ta-IN"
        "te" -> "te-IN"
        "kn" -> "kn-IN"
        "ml" -> "ml-IN"
        "pa" -> "pa-IN"
        "en" -> "en-IN"
        else -> Locale.forLanguageTag(code).toLanguageTag()
    }

    private fun startSpeechRecognition() {
        speechHandler.post {
            if (speechWaitingForResult || speechListening) return@post
            if (speechRecognizer == null) {
                setupSpeechRecognizer()
                return@post
            }
            latestPartialSpeech = ""
            val locale = languageToSpeechLocale(speechLanguage)

            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, locale)
                putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, locale)
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
                putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3)
                putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak now")
            }

            runCatching {
                speechWaitingForResult = true
                speechRecognizer?.startListening(intent)
            }.onFailure {
                speechWaitingForResult = false
                speechListening = false
                _status.value = "Unable to start speech recognition"
                recreateSpeechRecognizer()
            }
        }
    }

    private fun stopSpeechRecognition() {
        speechHandler.post {
            if (speechRecognizer == null) return@post
            if (!speechListening && !speechWaitingForResult) return@post
            speechWaitingForResult = true
            runCatching {
                speechRecognizer?.stopListening()
            }.onFailure {
                speechWaitingForResult = false
                speechListening = false
                _status.value = "Unable to stop speech recognition"
            }
        }
    }

    // =========================================================
    // TEXT TO SPEECH
    // =========================================================
    override fun onInit(status: Int) {
        if (status != TextToSpeech.SUCCESS) {
            _status.value = "TTS initialization failed"
            return
        }

        tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
            override fun onStart(utteranceId: String?) {
                if (utteranceId.isNullOrBlank()) return
                ttsStartTimes[utteranceId] = System.currentTimeMillis()
            }

            override fun onDone(utteranceId: String?) {
                if (utteranceId.isNullOrBlank()) return
                val now = System.currentTimeMillis()
                ttsStartTimes.remove(utteranceId)?.let { start ->
                    _ttsLatencyMs.value = (now - start).coerceAtLeast(0L)
                }
                e2eStartTimes.remove(utteranceId)?.let { start ->
                    _e2eLatencyMs.value = (now - start).coerceAtLeast(0L)
                }
            }

            override fun onError(utteranceId: String?) {
                if (utteranceId.isNullOrBlank()) return
                ttsStartTimes.remove(utteranceId)
                e2eStartTimes.remove(utteranceId)
            }
        })

        applyTtsLanguage()
    }

    private fun applyTtsLanguage() {
        val code = _language.value.ifBlank { "en" }
        val locale = when (code.lowercase()) {
            "mr" -> Locale("mr", "IN")
            "hi" -> Locale("hi", "IN")
            "gu" -> Locale("gu", "IN")
            "bn" -> Locale("bn", "IN")
            "ta" -> Locale("ta", "IN")
            "te" -> Locale("te", "IN")
            "kn" -> Locale("kn", "IN")
            "ml" -> Locale("ml", "IN")
            "pa" -> Locale("pa", "IN")
            "en" -> Locale("en", "IN")
            else -> Locale.forLanguageTag(code)
        }

        val result = tts?.setLanguage(locale)
        if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
            _status.value = "TTS language unavailable: $code"
        }
    }

    // =========================================================
    // LANGUAGE / THEME / TRANSLATION MODEL
    // =========================================================
    fun setLanguage(code: String) {
        _language.value = code
        prefs.edit().putString("language", code).apply()
        speechLanguage = code
        _messages.value = demoMessages(code)
        applyTtsLanguage()
        checkModelStatus(code)
        if (code != "en") {
            downloadCurrentModel()
        }
        _status.value = UiStrings(code).languageChanged
    }

    fun setDarkTheme(value: Boolean) {
        _darkTheme.value = value
        prefs.edit().putBoolean("dark_theme", value).apply()
    }

    fun setAiServerUrl(value: String) {
        _aiServerUrl.value = "ON-DEVICE AI"
        _status.value = "AI runs on-device · no server required"
    }

    fun checkAiServer() {
        _status.value = "On-device AI ready"
    }

    fun checkModelStatus(lang: String = _language.value) {
        if (lang == "en") {
            _modelStatus.value = TranslationModelStatus.READY
            return
        }
        val targetTag = TranslateLanguage.fromLanguageTag(lang)
        if (targetTag == null) {
            _modelStatus.value = TranslationModelStatus.NOT_INSTALLED
            return
        }
        val model = TranslateRemoteModel.Builder(targetTag).build()
        modelManager.isModelDownloaded(model)
            .addOnSuccessListener { downloaded ->
                _modelStatus.value = if (downloaded) TranslationModelStatus.READY else TranslationModelStatus.NOT_INSTALLED
            }
            .addOnFailureListener {
                _modelStatus.value = TranslationModelStatus.ERROR
            }
    }

    fun downloadCurrentModel() {
        val lang = _language.value
        if (lang == "en") return
        val targetTag = TranslateLanguage.fromLanguageTag(lang)
        if (targetTag == null) {
            _status.value = "Language not supported by on-device ML Kit"
            return
        }
        _modelStatus.value = TranslationModelStatus.DOWNLOADING
        _status.value = "Downloading model for $lang…"
        val model = TranslateRemoteModel.Builder(targetTag).build()
        val conditions = DownloadConditions.Builder().build()
        modelManager.download(model, conditions)
            .addOnSuccessListener {
                _modelStatus.value = TranslationModelStatus.READY
                _status.value = "Translation model ready: $lang"
            }
            .addOnFailureListener {
                _modelStatus.value = TranslationModelStatus.ERROR
                _status.value = "Model download failed"
            }
    }

    // =========================================================
    // MODE & PEERS
    // =========================================================
    fun setMode(m: AppMode) {
        _mode.value = m
    }

    fun selectPeer(p: Peer) {
        _selectedPeer.value = p
    }

    fun scan() {
        wifi.discover()
    }

    fun connect(p: Peer) {
        _selectedPeer.value = p
        wifi.connect(p)
        if (p.address.matches(Regex("\\d+\\.\\d+\\.\\d+\\.\\d+"))) {
            transport.connect(p.address)
        }
    }

    // =========================================================
    // WALKIE TALKIE
    // =========================================================
    fun walkieStart() {
        targetWalkieGroup = null
        speechLanguage = _language.value
        _transcript.value = ""
        latestPartialSpeech = ""
        _status.value = UiStrings(_language.value).listening
        startSpeechRecognition()
    }

    fun walkieStop(text: String) {
        stopSpeechRecognition()
    }

    fun groupWalkieStart(group: Group) {
        targetWalkieGroup = group
        speechLanguage = _language.value
        _transcript.value = ""
        latestPartialSpeech = ""
        _status.value = "${UiStrings(_language.value).groupWalkieListening} (${group.name})"
        startSpeechRecognition()
    }

    fun groupWalkieStop() {
        stopSpeechRecognition()
    }

    private fun sendRecognizedWalkieText(text: String) {
        if (text.isBlank()) {
            targetWalkieGroup = null
            _status.value = UiStrings(_language.value).standby
            return
        }

        _transcript.value = text
        val group = targetWalkieGroup
        targetWalkieGroup = null

        if (group != null) {
            sendGroup(group, text)
        } else {
            sendPacket(type = "WALKIE", text = text, sourceLanguage = _language.value)
            addMessage("${UiStrings(_language.value).you}: $text")
            _status.value = UiStrings(_language.value).standby
        }
    }

    // =========================================================
    // GROUP
    // =========================================================
    fun sendGroup(group: Group, text: String) {
        if (text.isBlank()) return
        sendPacket(
            type = "GROUP",
            text = text,
            group = group.name,
            sourceLanguage = _language.value
        )
        addMessage("${group.name}: $text")
        _status.value = "${UiStrings(_language.value).sentTo} ${group.name}"
    }

    fun sendGroupSos(group: Group, message: String) {
        if (message.isBlank()) return
        sendPacket(
            type = "SOS",
            text = message,
            group = group.name,
            sourceLanguage = _language.value
        )
        addMessage("${UiStrings(_language.value).sos} [${group.name}]: $message")
        _status.value = "${UiStrings(_language.value).sosSent} (${group.name})"
        triggerSingleHapticAlert()
    }

    // =========================================================
    // SOS
    // =========================================================
    fun sendSos(message: String) {
        if (message.isBlank()) return
        sendPacket(
            type = "SOS",
            text = message,
            sourceLanguage = _language.value
        )
        addMessage("${UiStrings(_language.value).sos}: $message")
        _status.value = UiStrings(_language.value).sosSent
        triggerSingleHapticAlert()
    }

    // =========================================================
    // PRIVATE CALL & PCM AUDIO STREAMING
    // =========================================================
    fun callStart() {
        val p = _selectedPeer.value
        if (p == null) {
            _status.value = "Select a peer first"
            return
        }

        _callingPeer.value = p
        _callState.value = CallState.CALLING
        _status.value = "${UiStrings(_language.value).calling} ${p.name}"

        val targetHost = if (p.address == "Wi-Fi Direct") connectedHost else p.address
        sendPacket(
            type = "CALL_START",
            text = "CALL",
            target = targetHost,
            sourceLanguage = _language.value
        )
    }

    fun callAccept() {
        val caller = _callingPeer.value
        val targetHost = if (caller?.address == "Wi-Fi Direct" || caller?.address.isNullOrBlank()) connectedHost else caller!!.address

        _callState.value = CallState.CONNECTED
        _status.value = "Call connected"

        sendPacket(
            type = "CALL_ACCEPT",
            text = "ACCEPT",
            target = targetHost,
            sourceLanguage = _language.value
        )
        startCallAudio(targetHost)
    }

    fun callDecline() {
        val caller = _callingPeer.value
        val targetHost = if (caller?.address == "Wi-Fi Direct" || caller?.address.isNullOrBlank()) connectedHost else caller!!.address

        sendPacket(
            type = "CALL_DECLINE",
            text = "DECLINE",
            target = targetHost,
            sourceLanguage = _language.value
        )
        stopCallAudio()
        _callState.value = CallState.ENDED
        _status.value = UiStrings(_language.value).callEnded
    }

    fun callEnd() {
        val p = _callingPeer.value ?: _selectedPeer.value
        val targetHost = if (p?.address == "Wi-Fi Direct" || p?.address.isNullOrBlank()) connectedHost else p!!.address

        sendPacket(
            type = "CALL_END",
            text = "END",
            target = targetHost,
            sourceLanguage = _language.value
        )
        stopCallAudio()
        _callState.value = CallState.ENDED
        _status.value = UiStrings(_language.value).callEnded
    }

    private fun startCallAudio(targetHost: String) {
        stopCallAudio()
        val sampleRate = 16000
        val recordBufferSize = AudioRecord.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_IN_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        ).coerceAtLeast(2048)

        val playBufferSize = AudioTrack.getMinBufferSize(
            sampleRate,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        ).coerceAtLeast(4096)

        runCatching {
            callPlaying = AudioTrack(
                AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                    .build(),
                AudioFormat.Builder()
                    .setSampleRate(sampleRate)
                    .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
                    .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
                    .build(),
                playBufferSize,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE
            ).apply { play() }

            callRecording = AudioRecord(
                MediaRecorder.AudioSource.VOICE_COMMUNICATION,
                sampleRate,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                recordBufferSize
            ).apply { startRecording() }

            callRecordThread = Thread {
                val buffer = ByteArray(1024)
                while (callRecording?.recordingState == AudioRecord.RECORDSTATE_RECORDING) {
                    val read = callRecording?.read(buffer, 0, buffer.size) ?: 0
                    if (read > 0) {
                        val base64 = Base64.encodeToString(buffer, 0, read, Base64.NO_WRAP)
                        sendPacket(
                            type = "AUDIO",
                            text = "",
                            target = targetHost,
                            data = base64
                        )
                    }
                }
            }.also {
                it.isDaemon = true
                it.start()
            }
        }.onFailure {
            Log.e("iTantra", "Failed to start call audio", it)
            _status.value = "Microphone/Speaker unavailable"
        }
    }

    private fun stopCallAudio() {
        runCatching {
            callRecording?.stop()
            callRecording?.release()
        }
        callRecording = null
        callRecordThread = null

        runCatching {
            callPlaying?.stop()
            callPlaying?.release()
        }
        callPlaying = null
    }

    private fun playIncomingAudio(base64: String) {
        if (base64.isBlank()) return
        runCatching {
            val pcm = Base64.decode(base64, Base64.NO_WRAP)
            callPlaying?.write(pcm, 0, pcm.size)
        }.onFailure {
            Log.e("iTantra", "Error writing audio to AudioTrack", it)
        }
    }

    // =========================================================
    // SEND PACKET
    // =========================================================
    private fun sendPacket(
        type: String,
        text: String,
        group: String = "",
        target: String = "",
        data: String = "",
        packetId: String = UUID.randomUUID().toString(),
        ttl: Int = 4,
        sourceLanguage: String = _language.value
    ) {
        seenPacketIds.add(packetId)
        val o = JSONObject().apply {
            put("id", packetId)
            put("origin", deviceId)
            put("sender", deviceId)
            put("ttl", ttl.coerceAtLeast(0))
            put("type", type)
            put("text", text)
            put("group", group)
            put("target", target)
            put("data", data)
            put("sourceLanguage", sourceLanguage)
            put("timestamp", System.currentTimeMillis())
        }

        val raw = o.toString()
        if (target.isNotBlank() && target != "ALL" && target != "Wi-Fi Direct") {
            transport.sendTo(raw, target)
        } else {
            transport.send(raw)
        }
    }

    // =========================================================
    // RECEIVE PACKET & MESH RELAY
    // =========================================================
    private fun handle(raw: String, host: String) {
        runCatching {
            val o = JSONObject(raw)
            val packetId = o.optString("id").ifBlank { UUID.randomUUID().toString() }
            if (!seenPacketIds.add(packetId)) {
                return@runCatching
            }

            val type = o.optString("type")
            val ttl = o.optInt("ttl", 0)

            when (type) {
                "WALKIE" -> {
                    val text = o.optString("text")
                    val source = o.optString("sourceLanguage", "en")
                    translateAndDisplay(text, source, host)
                    relayIfNeeded(o, ttl, host)
                }

                "GROUP" -> {
                    val text = o.optString("text")
                    val source = o.optString("sourceLanguage", "en")
                    val group = o.optString("group")
                    translateAndDisplay(text, source, host, group = group)
                    relayIfNeeded(o, ttl, host)
                }

                "SOS" -> {
                    val text = o.optString("text")
                    val source = o.optString("sourceLanguage", "en")
                    translateAndDisplay(text, source, host, sos = true)
                    relayIfNeeded(o, ttl, host)
                }

                "CALL_START" -> {
                    connectedHost = host
                    _connected.value = true
                    _callingPeer.value = Peer(name = host, address = host, deviceAddress = host)
                    _callState.value = CallState.INCOMING
                    _status.value = "${UiStrings(_language.value).incomingCall} from $host"
                }

                "CALL_ACCEPT" -> {
                    connectedHost = host
                    _connected.value = true
                    _callState.value = CallState.CONNECTED
                    _status.value = "Call connected with $host"
                    startCallAudio(host)
                }

                "CALL_DECLINE" -> {
                    stopCallAudio()
                    _callState.value = CallState.ENDED
                    _status.value = "Call declined"
                }

                "CALL_END" -> {
                    stopCallAudio()
                    _callState.value = CallState.ENDED
                    _status.value = UiStrings(_language.value).callEnded
                }

                "AUDIO" -> {
                    playIncomingAudio(o.optString("data"))
                }
            }
        }.onFailure {
            Log.e("iTantra", "Error handling packet from $host", it)
        }
    }

    private fun relayIfNeeded(original: JSONObject, ttl: Int, senderHost: String) {
        if (ttl <= 1) return
        val copy = JSONObject(original.toString()).apply {
            put("ttl", ttl - 1)
        }
        transport.sendExcept(copy.toString(), senderHost)
    }

    // =========================================================
    // TRANSLATION PIPELINE
    // =========================================================
    private fun translateAndDisplay(
        text: String,
        sourceLanguage: String,
        host: String,
        group: String = "",
        sos: Boolean = false
    ) {
        if (text.isBlank()) return
        val target = _language.value
        val startedAt = System.currentTimeMillis()
        _lastActivityAt.value = startedAt
        _lastPayloadBytes.value = text.toByteArray(Charsets.UTF_8).size
        _processedMessages.value += 1
        val e2eToken = UUID.randomUUID().toString()

        if (sourceLanguage.equals(target, ignoreCase = true)) {
            _translationLatencyMs.value = 0L
            displayAndSpeak(text, target, host, group, sos, e2eToken, startedAt)
            return
        }

        val source = TranslateLanguage.fromLanguageTag(sourceLanguage)
        val targetLang = TranslateLanguage.fromLanguageTag(target)

        if (source == null || targetLang == null) {
            _translationLatencyMs.value = 0L
            displayAndSpeak(text, target, host, group, sos, e2eToken, startedAt)
            return
        }

        val key = "$source->$targetLang"
        val translator = translators.getOrPut(key) {
            Translation.getClient(
                TranslatorOptions.Builder()
                    .setSourceLanguage(source)
                    .setTargetLanguage(targetLang)
                    .build()
            )
        }

        translator.downloadModelIfNeeded()
            .addOnSuccessListener {
                translator.translate(text)
                    .addOnSuccessListener { translated ->
                        _translationLatencyMs.value = (System.currentTimeMillis() - startedAt).coerceAtLeast(0L)
                        displayAndSpeak(translated, target, host, group, sos, e2eToken, startedAt)
                    }
                    .addOnFailureListener {
                        _translationLatencyMs.value = (System.currentTimeMillis() - startedAt).coerceAtLeast(0L)
                        displayAndSpeak(text, target, host, group, sos, e2eToken, startedAt)
                    }
            }
            .addOnFailureListener {
                _translationLatencyMs.value = (System.currentTimeMillis() - startedAt).coerceAtLeast(0L)
                displayAndSpeak(text, target, host, group, sos, e2eToken, startedAt)
            }
    }

    private fun displayAndSpeak(
        text: String,
        targetLang: String,
        host: String,
        group: String,
        sos: Boolean,
        e2eToken: String,
        startedAt: Long
    ) {
        val strings = UiStrings(targetLang)
        val prefix = when {
            sos -> strings.sosFromPeer
            group.isNotBlank() -> group
            else -> strings.peer
        }

        if (sos) {
            val alertData = EmergencyAlertData(
                title = strings.emergencySosAlertHeader,
                message = text,
                sender = host,
                group = group
            )
            triggerContinuousEmergencyAlert(alertData)
        }

        addMessage("$prefix: $text")
        speak(text, targetLang, e2eToken, startedAt)

        _status.value = when {
            sos -> strings.sosReceived
            group.isNotBlank() -> "${strings.receivedFrom} $group ($host)"
            else -> "${strings.receivedFrom} $host"
        }
    }

    // =========================================================
    // EMERGENCY SOS CONTINUOUS VIBRATION & AUDIBLE ALERT
    // =========================================================
    fun triggerContinuousEmergencyAlert(alertData: EmergencyAlertData) {
        _activeEmergencyAlert.value = alertData
        alertJob?.cancel()
        alertJob = viewModelScope.launch(Dispatchers.Default) {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = application.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                application.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }

            val sosTimings = longArrayOf(0, 250, 150, 250, 150, 250, 300, 600, 200, 600, 200, 600, 300, 250, 150, 250, 150, 250)

            withContext(Dispatchers.Main) {
                runCatching {
                    currentRingtone?.stop()
                    val alertUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                        ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                    currentRingtone = RingtoneManager.getRingtone(application, alertUri)?.apply {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            audioAttributes = AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_ALARM)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                                .build()
                        }
                        play()
                    }
                }
            }

            while (isActive) {
                runCatching {
                    if (vibrator?.hasVibrator() == true) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            val effect = VibrationEffect.createWaveform(sosTimings, -1)
                            vibrator.vibrate(effect)
                        } else {
                            @Suppress("DEPRECATION")
                            vibrator.vibrate(sosTimings, -1)
                        }
                    }
                }
                delay(3200)
                withContext(Dispatchers.Main) {
                    runCatching {
                        if (currentRingtone?.isPlaying == false) {
                            currentRingtone?.play()
                        }
                    }
                }
            }
        }
    }

    fun acknowledgeEmergencyAlert() {
        alertJob?.cancel()
        alertJob = null
        _activeEmergencyAlert.value = null

        runCatching {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = application.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                application.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }
            vibrator?.cancel()
        }

        runCatching {
            currentRingtone?.stop()
            currentRingtone = null
        }

        _status.value = UiStrings(_language.value).standby
    }

    private fun triggerSingleHapticAlert() {
        runCatching {
            val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vibratorManager = application.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as? VibratorManager
                vibratorManager?.defaultVibrator
            } else {
                @Suppress("DEPRECATION")
                application.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
            }

            if (vibrator?.hasVibrator() == true) {
                val sosTimings = longArrayOf(0, 250, 150, 250, 150, 250, 300, 600, 200, 600, 200, 600, 300, 250, 150, 250, 150, 250)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val effect = VibrationEffect.createWaveform(sosTimings, -1)
                    vibrator.vibrate(effect)
                } else {
                    @Suppress("DEPRECATION")
                    vibrator.vibrate(sosTimings, -1)
                }
            }

            val alertUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val ringtone = RingtoneManager.getRingtone(application, alertUri)
            if (ringtone != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ringtone.audioAttributes = AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_ALARM)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                }
                ringtone.play()
                viewModelScope.launch {
                    delay(2000)
                    if (ringtone.isPlaying) {
                        ringtone.stop()
                    }
                }
            }
        }.onFailure {
            Log.e("iTantra", "Failed to trigger single alert", it)
        }
    }

    // =========================================================
    // TTS SPEAK
    // =========================================================
    private fun speak(
        text: String,
        language: String,
        e2eToken: String? = null,
        e2eStartedAt: Long? = null
    ) {
        val locale = when (language.lowercase()) {
            "mr" -> Locale("mr", "IN")
            "hi" -> Locale("hi", "IN")
            "gu" -> Locale("gu", "IN")
            "bn" -> Locale("bn", "IN")
            "ta" -> Locale("ta", "IN")
            "te" -> Locale("te", "IN")
            "kn" -> Locale("kn", "IN")
            "ml" -> Locale("ml", "IN")
            "pa" -> Locale("pa", "IN")
            else -> Locale("en", "IN")
        }

        viewModelScope.launch(Dispatchers.Main) {
            tts?.setLanguage(locale)
            val utteranceId = "itantra-" + System.currentTimeMillis() + "-" + UUID.randomUUID()
            if (e2eToken != null && e2eStartedAt != null) {
                e2eStartTimes[utteranceId] = e2eStartedAt
            }
            tts?.speak(text, TextToSpeech.QUEUE_ADD, null, utteranceId)
        }
    }

    // =========================================================
    // MESSAGES
    // =========================================================
    private fun addMessage(text: String) {
        _messages.value = (listOf(text) + _messages.value).take(100)
    }

    fun clearMessages() {
        _messages.value = emptyList()
    }

    // =========================================================
    // GROUP MANAGEMENT
    // =========================================================
    fun addGroup(name: String) {
        if (name.isBlank()) return
        _groups.value = groupsStore.add(name.trim(), emptyList())
    }

    fun deleteGroup(id: Long) {
        _groups.value = groupsStore.delete(id)
    }

    // =========================================================
    // CLEANUP
    // =========================================================
    override fun onCleared() {
        speechHandler.post {
            runCatching { speechRecognizer?.cancel() }
            runCatching { speechRecognizer?.destroy() }
            speechRecognizer = null
        }

        translators.values.forEach {
            runCatching { it.close() }
        }
        translators.clear()

        tts?.stop()
        tts?.shutdown()

        alertJob?.cancel()
        alertJob = null
        runCatching {
            currentRingtone?.stop()
            currentRingtone = null
        }

        stopCallAudio()
        transport.stop()
        wifi.stop()

        super.onCleared()
    }

    // =========================================================
    // DEMO MESSAGES
    // =========================================================
    private fun demoMessages(language: String): List<String> = when (language) {
        "mr" -> listOf(
            "तुम्ही: नमस्कार! iTantra ऑफलाइन मोडमध्ये तयार आहे.",
            "Peer: कृपया येथे सुरक्षितपणे या.",
            "SOS: आपत्कालीन संदेश तयार आहे."
        )
        "hi" -> listOf(
            "आप: नमस्ते! iTantra ऑफलाइन मोड में तैयार है।",
            "Peer: कृपया यहाँ सुरक्षित रूप से आएँ।",
            "SOS: आपातकालीन संदेश तैयार है।"
        )
        "gu" -> listOf(
            "તમે: નમસ્તે! iTantra ઑફલાઇન મોડમાં તૈયાર છે.",
            "Peer: કૃપા કરીને અહીં સુરક્ષિત રીતે આવો.",
            "SOS: ઇમરજન્સી સંદેશ તૈયાર છે."
        )
        "bn" -> listOf(
            "আপনি: নমস্কার! iTantra অফলাইন মোডে প্রস্তুত।",
            "Peer: দয়া করে এখানে নিরাপদে আসুন।",
            "SOS: জরুরি বার্তা প্রস্তুত।"
        )
        "ta" -> listOf(
            "நீங்கள்: வணக்கம்! iTantra ஆஃப்லைன் முறையில் தயாராக உள்ளது.",
            "Peer: தயவுசெய்து இங்கே பாதுகாப்பாக வாருங்கள்.",
            "SOS: அவசர செய்தி தயாராக உள்ளது."
        )
        "te" -> listOf(
            "మీరు: నమస్కారం! iTantra ఆఫ్‌లైన్ మోడ్‌లో సిద్ధంగా ఉంది.",
            "Peer: దయచేసి ఇక్కడ సురక్షితంగా రండి.",
            "SOS: అత్యవసర సందేశం సిద్ధంగా ఉంది."
        )
        "kn" -> listOf(
            "ನೀವು: ನಮಸ್ಕಾರ! iTantra ಆಫ್‌ಲೈನ್ ಮೋಡ್‌ನಲ್ಲಿ ಸಿದ್ಧವಾಗಿದೆ.",
            "Peer: ದಯವಿಟ್ಟು ಇಲ್ಲಿ ಸುರಕ್ಷಿತವಾಗಿ ಬನ್ನಿ.",
            "SOS: ತುರ್ತು ಸಂದೇಶ ಸಿದ್ಧವಾಗಿದೆ."
        )
        "ml" -> listOf(
            "നിങ്ങൾ: നമസ്കാരം! iTantra ഓഫ്‌ലൈൻ മോഡിൽ തയ്യാറാണ്.",
            "Peer: ദയവായി ഇവിടെ സുരക്ഷിതമായി വരിക.",
            "SOS: അടിയന്തര സന്ദേശം തയ്യാറാണ്."
        )
        "pa" -> listOf(
            "ਤੁਸੀਂ: ਸਤਿ ਸ੍ਰੀ ਅਕਾਲ! iTantra ਔਫਲਾਈਨ ਮੋਡ ਵਿੱਚ ਤਿਆਰ ਹੈ।",
            "Peer: ਕਿਰਪਾ ਕਰਕੇ ਇੱਥੇ ਸੁਰੱਖਿਅਤ ਢੰਗ ਨਾਲ ਆਓ।",
            "SOS: ਐਮਰਜੈਂਸੀ ਸੁਨੇਹਾ ਤਿਆਰ ਹੈ।"
        )
        else -> listOf(
            "You: Hello! iTantra is ready in offline mode.",
            "Peer: Please come here safely.",
            "SOS: Emergency message is ready."
        )
    }
}
