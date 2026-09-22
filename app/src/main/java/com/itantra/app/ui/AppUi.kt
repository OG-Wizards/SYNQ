package com.itantra.app.ui

import android.app.ActivityManager
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Process
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.CallEnd
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Download
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.NetworkCheck
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.PhoneInTalk
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itantra.app.AppViewModel
import com.itantra.app.UiStrings
import com.itantra.app.model.CallState
import com.itantra.app.model.Group
import com.itantra.app.model.Peer
import com.itantra.app.model.TranslationModelStatus
import java.util.Locale

private val BackgroundDark = Color(0xFF0B0F14)
private val BackgroundLight = Color(0xFFF5F7FA)

private val CardDark = Color(0xFF151B22)
private val CardLight = Color.White

private val Accent = Color(0xFF35D07F)
private val Red = Color(0xFFE53935)
private val Orange = Color(0xFFFFA000)

private val TextPrimaryDark = Color.White
private val TextSecondaryDark = Color(0xFF9AA4AF)

private val TextPrimaryLight = Color(0xFF111827)
private val TextSecondaryLight = Color(0xFF667085)

@Composable
fun iTantraApp(vm: AppViewModel) {
    var tab by remember { mutableIntStateOf(0) }
    val darkTheme by vm.darkTheme.collectAsState()
    val language by vm.language.collectAsState()
    val activeEmergencyAlert by vm.activeEmergencyAlert.collectAsState()
    val s = remember(language) { UiStrings(language) }

    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Accent,
            background = BackgroundDark,
            surface = CardDark,
            onPrimary = Color.Black,
            onBackground = TextPrimaryDark,
            onSurface = TextPrimaryDark
        )
    } else {
        lightColorScheme(
            primary = Accent,
            background = BackgroundLight,
            surface = CardLight,
            onPrimary = Color.Black,
            onBackground = TextPrimaryLight,
            onSurface = TextPrimaryLight
        )
    }

    val background = if (darkTheme) BackgroundDark else BackgroundLight
    val cardColor = if (darkTheme) CardDark else CardLight
    val textPrimary = if (darkTheme) TextPrimaryDark else TextPrimaryLight
    val textSecondary = if (darkTheme) TextSecondaryDark else TextSecondaryLight

    MaterialTheme(colorScheme = colorScheme) {
        Scaffold(
            containerColor = background,
            bottomBar = {
                NavigationBar(containerColor = cardColor) {
                    val navigationItems = listOf(
                        s.tabWalkie to Icons.Default.Mic,
                        s.tabMesh to Icons.Default.Wifi,
                        s.tabMessages to Icons.Default.Message,
                        s.tabSos to Icons.Default.Warning,
                        s.tabSettings to Icons.Default.Settings
                    )

                    navigationItems.forEachIndexed { index, item ->
                        NavigationBarItem(
                            selected = tab == index,
                            onClick = { tab = index },
                            icon = {
                                Icon(imageVector = item.second, contentDescription = item.first)
                            },
                            label = { Text(item.first) }
                        )
                    }
                }
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(background)
                    .padding(paddingValues)
            ) {
                when (tab) {
                    0 -> Walkie(vm = vm, s = s, cardColor = cardColor, textPrimary = textPrimary, textSecondary = textSecondary)
                    1 -> MeshScreen(vm = vm, s = s, cardColor = cardColor, textPrimary = textPrimary, textSecondary = textSecondary)
                    2 -> Messages(vm = vm, s = s, cardColor = cardColor, textPrimary = textPrimary, textSecondary = textSecondary)
                    3 -> Sos(vm = vm, s = s, cardColor = cardColor, textPrimary = textPrimary, textSecondary = textSecondary)
                    4 -> Settings(
                        vm = vm,
                        s = s,
                        darkMode = darkTheme,
                        onDarkModeChanged = { vm.setDarkTheme(it) },
                        cardColor = cardColor,
                        textPrimary = textPrimary,
                        textSecondary = textSecondary
                    )
                }

                activeEmergencyAlert?.let { alert ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.88f))
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = CardDark),
                            shape = RoundedCornerShape(20.dp),
                            border = BorderStroke(3.dp, Red)
                        ) {
                            Column(
                                modifier = Modifier.padding(22.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Warning,
                                    contentDescription = null,
                                    tint = Red,
                                    modifier = Modifier.size(54.dp)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = alert.title,
                                    color = Red,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                if (alert.group.isNotBlank()) {
                                    Text(
                                        text = "Group: ${alert.group}",
                                        color = Accent,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                }
                                Text(
                                    text = "From: ${alert.sender}",
                                    color = TextSecondaryDark,
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.height(14.dp))
                                Card(
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = CardDefaults.cardColors(containerColor = BackgroundDark),
                                    shape = RoundedCornerShape(12.dp)
                                ) {
                                    Column(modifier = Modifier.padding(14.dp)) {
                                        Text(
                                            text = alert.message,
                                            color = TextPrimaryDark,
                                            fontSize = 16.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(12.dp))
                                Text(
                                    text = s.vibratingAlertPrompt,
                                    color = TextSecondaryDark,
                                    fontSize = 11.sp
                                )
                                Spacer(modifier = Modifier.height(18.dp))
                                Button(
                                    onClick = { vm.acknowledgeEmergencyAlert() },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(52.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = Red, contentColor = Color.White),
                                    shape = RoundedCornerShape(14.dp)
                                ) {
                                    Text(
                                        text = s.acknowledgeAlert,
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

/* =========================================================
   WALKIE
   ========================================================= */
@Composable
private fun Walkie(
    vm: AppViewModel,
    s: UiStrings,
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    var selectedMode by remember { mutableIntStateOf(0) }
    val language by vm.language.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(
            text = "iTantra",
            color = textPrimary,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = s.appTagline,
            color = textSecondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            ModeButton(
                label = s.modeWalkie,
                icon = Icons.Default.Mic,
                selected = selectedMode == 0,
                cardColor = cardColor,
                textPrimary = textPrimary,
                onClick = { selectedMode = 0 }
            )

            ModeButton(
                label = s.modePrivateCall,
                icon = Icons.Default.Call,
                selected = selectedMode == 1,
                cardColor = cardColor,
                textPrimary = textPrimary,
                onClick = { selectedMode = 1 }
            )

            ModeButton(
                label = s.modeGroups,
                icon = Icons.Default.Groups,
                selected = selectedMode == 2,
                cardColor = cardColor,
                textPrimary = textPrimary,
                onClick = { selectedMode = 2 }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LanguageCard(
            language = language,
            label = s.yourLanguage,
            cardColor = cardColor,
            textPrimary = textPrimary,
            textSecondary = textSecondary
        )

        Spacer(modifier = Modifier.height(16.dp))

        when (selectedMode) {
            0 -> WalkiePanel(vm = vm, s = s, cardColor = cardColor, textPrimary = textPrimary, textSecondary = textSecondary)
            1 -> CallPanel(vm = vm, s = s, cardColor = cardColor, textPrimary = textPrimary, textSecondary = textSecondary)
            2 -> GroupPanel(vm = vm, s = s, cardColor = cardColor, textPrimary = textPrimary, textSecondary = textSecondary)
        }
    }
}

@Composable
private fun RowScope.ModeButton(
    label: String,
    icon: ImageVector,
    selected: Boolean,
    cardColor: Color,
    textPrimary: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .weight(1f)
            .height(70.dp),
        shape = RoundedCornerShape(14.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (selected) Accent else cardColor,
            contentColor = if (selected) Color.Black else textPrimary
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(imageVector = icon, contentDescription = null)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = label, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}

@Composable
private fun LanguageCard(
    language: String,
    label: String,
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Language, contentDescription = null, tint = Accent)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = label, color = textSecondary, fontSize = 12.sp)
                Text(text = UiStrings.languageDisplayName(language), color = textPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
private fun WalkiePanel(
    vm: AppViewModel,
    s: UiStrings,
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    var active by remember { mutableStateOf(false) }
    val transcript by vm.transcript.collectAsState()
    val status by vm.status.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (active) s.listeningActive else status,
            color = if (active) Accent else textPrimary,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(
                    color = if (active) Accent else cardColor,
                    shape = CircleShape
                )
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = {
                            active = true
                            vm.walkieStart()
                            tryAwaitRelease()
                            active = false
                            vm.walkieStop(transcript)
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.Mic,
                    contentDescription = null,
                    modifier = Modifier.size(50.dp),
                    tint = if (active) Color.Black else textPrimary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (active) s.listeningActive else s.holdToTalk,
                    color = if (active) Color.Black else textPrimary,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (transcript.isNotBlank()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(text = s.recognizedSpeech, color = textSecondary, fontSize = 12.sp)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = transcript, color = textPrimary, fontSize = 16.sp)
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }

        Waveform(active = active)
    }
}

@Composable
private fun Waveform(active: Boolean = false) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        repeat(18) { index ->
            val height = if (active) {
                (10 + ((index * 7 + 3) % 25)).dp
            } else {
                (6 + (index % 4) * 4).dp
            }
            Box(
                modifier = Modifier
                    .padding(horizontal = 2.dp)
                    .width(4.dp)
                    .height(height)
                    .background(if (active) Accent else Accent.copy(alpha = 0.4f), RoundedCornerShape(4.dp))
            )
        }
    }
}

/* =========================================================
   PRIVATE CALL PANEL
   ========================================================= */
@Composable
private fun CallPanel(
    vm: AppViewModel,
    s: UiStrings,
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    val selectedPeer by vm.selectedPeer.collectAsState()
    val callState by vm.callState.collectAsState()
    val callingPeer by vm.callingPeer.collectAsState()
    val status by vm.status.collectAsState()

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = s.callTitle,
            color = textPrimary,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = s.callSubtitle,
            color = textSecondary,
            fontSize = 13.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "${s.selectedPeerLabel}: ${selectedPeer?.name ?: s.none}",
                    color = if (selectedPeer != null) Accent else textSecondary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                if (selectedPeer != null) {
                    Text(text = "${s.address}: ${selectedPeer!!.address}", color = textSecondary, fontSize = 12.sp)
                } else {
                    Text(text = s.selectPeerPrompt, color = textSecondary, fontSize = 12.sp)
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${s.statusLabel}: $status", color = textSecondary, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        when (callState) {
            CallState.IDLE -> {
                Button(
                    onClick = { vm.callStart() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color.Black),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Call, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = s.startPrivateCall, fontWeight = FontWeight.Bold)
                }
            }

            CallState.CALLING -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(1.dp, Orange)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.PhoneInTalk, contentDescription = null, tint = Orange, modifier = Modifier.size(40.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "${s.calling} ${callingPeer?.name ?: s.peer}…", color = textPrimary, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { vm.callEnd() },
                            colors = ButtonDefaults.buttonColors(containerColor = Red, contentColor = Color.White),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Text(s.cancel)
                        }
                    }
                }
            }

            CallState.INCOMING -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, Accent)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(imageVector = Icons.Default.Call, contentDescription = null, tint = Accent, modifier = Modifier.size(40.dp))
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "${s.incomingCall}: ${callingPeer?.name ?: s.peer}", color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        Spacer(modifier = Modifier.height(14.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Button(
                                onClick = { vm.callAccept() },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color.Black),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(s.accept, fontWeight = FontWeight.Bold)
                            }
                            Button(
                                onClick = { vm.callDecline() },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Red, contentColor = Color.White),
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Text(s.decline, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            }

            CallState.CONNECTED -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    shape = RoundedCornerShape(16.dp),
                    border = BorderStroke(2.dp, Accent)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = s.callInProgress, color = Accent, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "${s.liveAudioStreamWith} ${callingPeer?.name ?: s.peer}", color = textPrimary, fontWeight = FontWeight.SemiBold)
                        Spacer(modifier = Modifier.height(10.dp))
                        Waveform(active = true)
                        Spacer(modifier = Modifier.height(12.dp))
                        Button(
                            onClick = { vm.callEnd() },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Red, contentColor = Color.White),
                            shape = RoundedCornerShape(10.dp)
                        ) {
                            Icon(imageVector = Icons.Default.CallEnd, contentDescription = null)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(s.endCall, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            CallState.ENDED -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = cardColor),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(text = s.callEnded, color = textPrimary, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = { vm.callEnd() },
                            colors = ButtonDefaults.buttonColors(containerColor = cardColor, contentColor = textPrimary)
                        ) {
                            Text(s.close)
                        }
                    }
                }
            }
        }
    }
}

/* =========================================================
   GROUP PANEL
   ========================================================= */
@Composable
private fun GroupPanel(
    vm: AppViewModel,
    s: UiStrings,
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    var groupName by remember { mutableStateOf("") }
    var message by remember { mutableStateOf("") }
    var selectedGroup by remember { mutableStateOf<Group?>(null) }
    val groups by vm.groups.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = s.groupsTitle, color = textPrimary, fontSize = 22.sp, fontWeight = FontWeight.Bold)
        Text(text = s.groupsSubtitle, color = textSecondary, fontSize = 13.sp)

        Spacer(modifier = Modifier.height(14.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = groupName,
                onValueChange = { groupName = it },
                modifier = Modifier.weight(1f),
                singleLine = true,
                label = { Text(s.newGroupName) }
            )

            Button(
                onClick = {
                    if (groupName.isNotBlank()) {
                        vm.addGroup(groupName)
                        groupName = ""
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color.Black),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = s.create)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = s.selectGroup, color = textPrimary, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        if (groups.isEmpty()) {
            Text(text = s.noGroupsYet, color = textSecondary, fontSize = 13.sp)
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(
                    items = groups,
                    key = { group: Group -> group.id }
                ) { group: Group ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                color = if (selectedGroup?.id == group.id) Accent else cardColor,
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = group.name,
                            color = if (selectedGroup?.id == group.id) Color.Black else textPrimary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Button(
                            onClick = { selectedGroup = group },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (selectedGroup?.id == group.id) Color.Black else Accent,
                                contentColor = if (selectedGroup?.id == group.id) Color.White else Color.Black
                            )
                        ) {
                            Text(if (selectedGroup?.id == group.id) s.selected else s.select)
                        }
                        IconButton(onClick = { vm.deleteGroup(group.id) }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete", tint = Red)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (selectedGroup != null) {
            val currentGroup = selectedGroup!!
            var walkieActive by remember { mutableStateOf(false) }

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp),
                border = if (walkieActive) BorderStroke(2.dp, Accent) else BorderStroke(1.dp, Accent.copy(alpha = 0.3f))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (walkieActive) s.groupWalkieListening else "${currentGroup.name} · ${s.modeWalkie}",
                        color = if (walkieActive) Accent else textPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .size(110.dp)
                            .background(
                                color = if (walkieActive) Accent else cardColor,
                                shape = CircleShape
                            )
                            .border(BorderStroke(2.dp, if (walkieActive) Color.White else Accent), CircleShape)
                            .pointerInput(currentGroup) {
                                detectTapGestures(
                                    onPress = {
                                        walkieActive = true
                                        vm.groupWalkieStart(currentGroup)
                                        tryAwaitRelease()
                                        walkieActive = false
                                        vm.groupWalkieStop()
                                    }
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Default.Mic,
                                contentDescription = null,
                                modifier = Modifier.size(36.dp),
                                tint = if (walkieActive) Color.Black else Accent
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = if (walkieActive) s.listeningActive else s.groupWalkieHold,
                                color = if (walkieActive) Color.Black else textPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Waveform(active = walkieActive)
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    val alertText = if (message.isNotBlank()) message.trim() else "EMERGENCY ALERT: Immediate assistance required in group ${currentGroup.name}!"
                    vm.sendGroupSos(currentGroup, alertText)
                    message = ""
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Red, contentColor = Color.White),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Warning, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${s.groupSosButton} (${currentGroup.name})", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = message,
                onValueChange = { message = it },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = { Text("${s.groupMessage} (${currentGroup.name})") }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    if (message.isNotBlank()) {
                        vm.sendGroup(currentGroup, message.trim())
                        message = ""
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color.Black),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Send, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "${s.sendGroupAlert} (${currentGroup.name})", fontWeight = FontWeight.SemiBold)
            }
        } else {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.Groups, contentDescription = null, tint = Accent, modifier = Modifier.size(36.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = s.selectGroup,
                        color = textPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Select a group from the list above to activate group walkie-talkie & emergency alerts.",
                        color = textSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

/* =========================================================
   MESH
   ========================================================= */
@Composable
private fun MeshScreen(
    vm: AppViewModel,
    s: UiStrings,
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    val connected by vm.connected.collectAsState()
    val devices by vm.peers.collectAsState()
    val selectedPeer by vm.selectedPeer.collectAsState()
    val status by vm.status.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = s.meshLink, color = textPrimary, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(text = s.meshSubtitle, color = textSecondary, fontSize = 13.sp)
            }
            Button(
                onClick = { vm.scan() },
                colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color.Black),
                shape = RoundedCornerShape(10.dp)
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = null)
                Spacer(modifier = Modifier.width(4.dp))
                Text(s.scan)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = cardColor),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(12.dp)
                            .background(if (connected) Accent else Red, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = if (connected) s.connected else s.disconnected,
                        color = textPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(6.dp))
                Text(text = "${devices.size} ${s.devicesDiscovered}", color = textSecondary, fontSize = 13.sp)
                Text(text = status, color = Accent, fontSize = 12.sp)
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        Text(text = s.discoveredPeers, color = textPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        if (devices.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(14.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.Wifi, contentDescription = null, tint = textSecondary, modifier = Modifier.size(40.dp))
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = s.noPeersFound, color = textPrimary, fontWeight = FontWeight.Bold)
                    Text(text = s.tapScanPrompt, color = textSecondary, fontSize = 12.sp)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().weight(1f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = devices,
                    key = { peer: Peer -> peer.deviceAddress }
                ) { peer: Peer ->
                    val isSelected = selectedPeer?.deviceAddress == peer.deviceAddress
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        shape = RoundedCornerShape(14.dp),
                        border = if (isSelected) BorderStroke(1.5.dp, Accent) else null
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(imageVector = Icons.Default.PhoneAndroid, contentDescription = null, tint = Accent)
                            Spacer(modifier = Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(text = peer.name, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                                Text(text = "${peer.address} · ${peer.status}", color = textSecondary, fontSize = 11.sp)
                            }
                            Button(
                                onClick = { vm.selectPeer(peer) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (isSelected) Accent else cardColor,
                                    contentColor = if (isSelected) Color.Black else textPrimary
                                )
                            ) {
                                Text(if (isSelected) s.selected else s.select, fontSize = 11.sp)
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Button(
                                onClick = { vm.connect(peer) },
                                colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color.Black)
                            ) {
                                Text(s.connect, fontSize = 11.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}

/* =========================================================
   MESSAGES
   ========================================================= */
@Composable
private fun Messages(
    vm: AppViewModel,
    s: UiStrings,
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    val messages by vm.messages.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = s.messagesTitle, color = textPrimary, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                Text(text = s.messagesSubtitle, color = textSecondary, fontSize = 13.sp)
            }
            if (messages.isNotEmpty()) {
                Button(
                    onClick = { vm.clearMessages() },
                    colors = ButtonDefaults.buttonColors(containerColor = cardColor, contentColor = textSecondary)
                ) {
                    Text(s.clear, fontSize = 11.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (messages.isEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth().padding(30.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(imageVector = Icons.Default.Message, contentDescription = null, modifier = Modifier.size(50.dp), tint = textSecondary)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = s.noMessagesYet, color = textPrimary, fontWeight = FontWeight.Bold)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    items = messages
                ) { message: String ->
                    val isSos = message.startsWith("SOS", ignoreCase = true)
                    val isYou = message.startsWith("You:", ignoreCase = true) ||
                            message.startsWith("तुम्ही:", ignoreCase = true) ||
                            message.startsWith("आप:", ignoreCase = true) ||
                            message.startsWith("તમે:", ignoreCase = true) ||
                            message.startsWith("আপনি:", ignoreCase = true) ||
                            message.startsWith("நீங்கள்:", ignoreCase = true) ||
                            message.startsWith("మీరు:", ignoreCase = true) ||
                            message.startsWith("ನೀವು:", ignoreCase = true) ||
                            message.startsWith("നിങ്ങൾ:", ignoreCase = true) ||
                            message.startsWith("ਤੁਸੀਂ:", ignoreCase = true)

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = cardColor),
                        shape = RoundedCornerShape(14.dp),
                        border = if (isSos) BorderStroke(1.5.dp, Red) else if (isYou) BorderStroke(1.dp, Accent.copy(alpha = 0.5f)) else null
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            if (isSos) {
                                Icon(imageVector = Icons.Default.Warning, contentDescription = null, tint = Red)
                                Spacer(modifier = Modifier.width(8.dp))
                            }
                            Text(text = message, color = textPrimary, fontSize = 14.sp)
                        }
                    }
                }
            }
        }
    }
}

/* =========================================================
   SOS
   ========================================================= */
@Composable
private fun Sos(
    vm: AppViewModel,
    s: UiStrings,
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    var message by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Text(text = s.sosTitle, color = textPrimary, fontSize = 28.sp, fontWeight = FontWeight.Bold)
        Text(text = s.sosSubtitle, color = textSecondary, fontSize = 13.sp)

        Spacer(modifier = Modifier.height(18.dp))

        val alerts = listOf(
            s.alertCyclone,
            s.alertFlood,
            s.alertMedical,
            s.alertComms
        )

        alerts.forEach { alert ->
            Button(
                onClick = { vm.sendSos(alert) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = cardColor),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = alert, color = textPrimary, fontWeight = FontWeight.SemiBold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            label = { Text(s.customEmergencyMessage) }
        )

        Spacer(modifier = Modifier.height(14.dp))

        Button(
            onClick = {
                if (message.isNotBlank()) {
                    vm.sendSos(message)
                    message = ""
                }
            },
            modifier = Modifier.fillMaxWidth().height(52.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Red, contentColor = Color.White),
            shape = RoundedCornerShape(12.dp)
        ) {
            Icon(imageVector = Icons.Default.Warning, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = s.broadcastSos, fontWeight = FontWeight.Bold)
        }
    }
}

/* =========================================================
   SETTINGS
   ========================================================= */
@Composable
private fun Settings(
    vm: AppViewModel,
    s: UiStrings,
    darkMode: Boolean,
    onDarkModeChanged: (Boolean) -> Unit,
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    var showLanguages by remember { mutableStateOf(false) }
    var showAnalytics by remember { mutableStateOf(false) }
    val language by vm.language.collectAsState()
    val modelStatus by vm.modelStatus.collectAsState()

    if (showAnalytics) {
        AnalyticsScreen(
            vm = vm,
            s = s,
            cardColor = cardColor,
            textPrimary = textPrimary,
            textSecondary = textSecondary,
            onBack = { showAnalytics = false }
        )
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Text(text = s.settingsTitle, color = textPrimary, fontSize = 30.sp, fontWeight = FontWeight.Bold)
            Text(text = s.settingsSubtitle, color = textSecondary)
        }

        item {
            SettingSwitch(
                title = s.darkMode,
                subtitle = if (darkMode) s.darkAppearanceEnabled else s.lightAppearanceEnabled,
                checked = darkMode,
                cardColor = cardColor,
                textPrimary = textPrimary,
                textSecondary = textSecondary,
                onCheckedChange = onDarkModeChanged
            )
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.Language, contentDescription = null, tint = Accent)
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(text = s.language, color = textPrimary, fontWeight = FontWeight.Bold)
                            Text(text = UiStrings.languageDisplayName(language), color = textSecondary, fontSize = 13.sp)
                        }
                        Button(
                            onClick = { showLanguages = !showLanguages },
                            colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color.Black)
                        ) {
                            Text(if (showLanguages) s.close else s.change)
                        }
                    }

                    if (showLanguages) {
                        Spacer(modifier = Modifier.height(12.dp))
                        val languages = listOf(
                            "mr" to "मराठी (Marathi)",
                            "hi" to "हिन्दी (Hindi)",
                            "gu" to "ગુજરાતી (Gujarati)",
                            "bn" to "বাংলা (Bengali)",
                            "ta" to "தமிழ் (Tamil)",
                            "te" to "తెలుగు (Telugu)",
                            "kn" to "ಕನ್ನಡ (Kannada)",
                            "ml" to "മലയാളം (Malayalam)",
                            "pa" to "ਪੰਜਾਬੀ (Punjabi)",
                            "en" to "English"
                        )

                        languages.forEach { item ->
                            Button(
                                onClick = {
                                    vm.setLanguage(item.first)
                                    showLanguages = false
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 5.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (language == item.first) Accent else cardColor,
                                    contentColor = if (language == item.first) Color.Black else textPrimary
                                )
                            ) {
                                Text(item.second)
                            }
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = s.translationModel, color = textPrimary, fontWeight = FontWeight.Bold)
                        val statusLabel = when (modelStatus) {
                            TranslationModelStatus.READY -> s.modelReady
                            TranslationModelStatus.DOWNLOADING -> s.modelDownloading
                            TranslationModelStatus.NOT_INSTALLED -> s.modelNotInstalled
                            TranslationModelStatus.ERROR -> s.modelError
                        }
                        val statusColor = when (modelStatus) {
                            TranslationModelStatus.READY -> Accent
                            TranslationModelStatus.DOWNLOADING -> Orange
                            TranslationModelStatus.NOT_INSTALLED -> textSecondary
                            TranslationModelStatus.ERROR -> Red
                        }
                        Text(text = statusLabel, color = statusColor, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                    }

                    if (modelStatus != TranslationModelStatus.READY && language != "en") {
                        Button(
                            onClick = { vm.downloadCurrentModel() },
                            colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color.Black)
                        ) {
                            Icon(imageVector = Icons.Default.Download, contentDescription = null)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(s.download)
                        }
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Speed, contentDescription = null, tint = Accent)
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(text = s.realtimeDashboard, color = textPrimary, fontWeight = FontWeight.Bold)
                        Text(text = s.dashboardSubtitle, color = textSecondary, fontSize = 12.sp)
                    }
                    Button(
                        onClick = { showAnalytics = true },
                        colors = ButtonDefaults.buttonColors(containerColor = Accent, contentColor = Color.Black)
                    ) {
                        Text(s.open)
                    }
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = s.offlineAi, color = textPrimary, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = s.offlineAiPoint1, color = textSecondary, fontSize = 13.sp)
                    Text(text = s.offlineAiPoint2, color = textSecondary, fontSize = 13.sp)
                    Text(text = s.offlineAiPoint3, color = textSecondary, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = s.offlineAiFooter,
                        color = Accent,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingSwitch(
    title: String,
    subtitle: String,
    checked: Boolean,
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, color = textPrimary, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(3.dp))
                Text(text = subtitle, color = textSecondary, fontSize = 12.sp)
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = Accent)
            )
        }
    }
}

/* =========================================================
   ANALYTICS SCREEN
   ========================================================= */
@Composable
private fun AnalyticsScreen(
    vm: AppViewModel,
    s: UiStrings,
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color,
    onBack: () -> Unit
) {
    val connected by vm.connected.collectAsState()
    val devices by vm.peers.collectAsState()
    val language by vm.language.collectAsState()
    val translationLatencyMs by vm.translationLatencyMs.collectAsState()
    val ttsLatencyMs by vm.ttsLatencyMs.collectAsState()
    val e2eLatencyMs by vm.e2eLatencyMs.collectAsState()
    val lastPayloadBytes by vm.lastPayloadBytes.collectAsState()
    val processedMessages by vm.processedMessages.collectAsState()
    val lastActivityAt by vm.lastActivityAt.collectAsState()

    val context = androidx.compose.ui.platform.LocalContext.current
    val memoryMb = remember { getMemoryMb(context) }
    val cpuPercent = remember { getCpuPercent() }
    val internet = remember { isInternetAvailable(context) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(containerColor = cardColor, contentColor = textPrimary)
                ) {
                    Text(s.back)
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(text = s.dashboardTitle, color = textPrimary, fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(text = s.dashboardLiveReadings, color = textSecondary, fontSize = 12.sp)
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(12.dp)
                                .background(if (connected) Accent else Red, CircleShape)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = if (connected) s.connected else s.disconnected,
                            color = textPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(text = "${devices.size} ${s.devicesOnline}", color = textSecondary)
                    Text(text = s.liveMetricsNotice, color = Accent, fontSize = 12.sp)
                }
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = s.language,
                    value = UiStrings.languageDisplayName(language),
                    description = s.currentSelected,
                    cardColor = cardColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = s.stt,
                    value = "—",
                    description = s.speechToText,
                    cardColor = cardColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = s.translation,
                    value = formatMs(translationLatencyMs ?: 0L),
                    description = s.textToText,
                    cardColor = cardColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = s.ttsLatency,
                    value = formatMs(ttsLatencyMs ?: 0L),
                    description = s.textToSpeech,
                    cardColor = cardColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
            }
        }

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = s.e2eLatency,
                    value = formatSeconds(e2eLatencyMs ?: 0L),
                    description = s.speechToTts,
                    cardColor = cardColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
                MetricCard(
                    modifier = Modifier.weight(1f),
                    title = s.payload,
                    value = "${lastPayloadBytes} B",
                    description = s.latestPacket,
                    cardColor = cardColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary
                )
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = s.systemResources, color = textPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text = "${s.appRam}: ${memoryMb} MB", color = textPrimary)
                    Spacer(modifier = Modifier.height(6.dp))
                    ProgressBar(value = (memoryMb / 512f).coerceIn(0f, 1f))
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(text = "${s.cpu}: ${cpuPercent}%", color = textPrimary)
                    Spacer(modifier = Modifier.height(6.dp))
                    ProgressBar(value = (cpuPercent / 100f).coerceIn(0f, 1f))
                }
            }
        }

        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = cardColor),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(imageVector = Icons.Default.NetworkCheck, contentDescription = null, tint = Accent)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = s.networkAndActivity, color = textPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "${s.internet}: ${if (internet) s.online else s.offline}",
                        color = if (internet) Accent else Red,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "${s.processedMessages}: $processedMessages", color = textPrimary)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${s.lastActivity}: ${formatActivityTime(lastActivityAt ?: 0L, s)}",
                        color = textSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    modifier: Modifier,
    title: String,
    value: String,
    description: String,
    cardColor: Color,
    textPrimary: Color,
    textSecondary: Color
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = cardColor),
        shape = RoundedCornerShape(14.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, color = textSecondary, fontSize = 11.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = value, color = textPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text = description, color = textSecondary, fontSize = 10.sp)
        }
    }
}

@Composable
private fun ProgressBar(value: Float) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(Color(0xFF252D36), RoundedCornerShape(8.dp))
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(value)
                .height(8.dp)
                .background(Accent, RoundedCornerShape(8.dp))
        )
    }
}

/* =========================================================
   HELPERS
   ========================================================= */
private fun formatMs(value: Long): String {
    if (value <= 0L) return "—"
    return "$value ms"
}

private fun formatSeconds(value: Long): String {
    if (value <= 0L) return "—"
    return String.format(Locale.US, "%.2f sec", value / 1000.0)
}

private fun formatActivityTime(value: Long, s: UiStrings): String {
    if (value <= 0L) return s.noActivity
    val age = System.currentTimeMillis() - value
    return when {
        age < 1000L -> s.justNow
        age < 60_000L -> "${age / 1000L}s ago"
        age < 3_600_000L -> "${age / 60_000L}m ago"
        else -> "${age / 3_600_000L}h ago"
    }
}

private fun getMemoryMb(context: Context): Int = runCatching {
    val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val pid = Process.myPid()
    val processes = activityManager.getProcessMemoryInfo(intArrayOf(pid))
    if (processes.isNotEmpty()) {
        processes[0].totalPss / 1024
    } else {
        0
    }
}.getOrDefault(0)

private fun getCpuPercent(): Int = runCatching {
    val stat = java.io.File("/proc/${Process.myPid()}/stat").readText()
    val parts = stat.split(" ")
    if (parts.size > 15) {
        val utime = parts[13].toLong()
        val stime = parts[14].toLong()
        ((utime + stime) % 100).toInt()
    } else {
        0
    }
}.getOrDefault(0)

private fun isInternetAvailable(context: Context): Boolean = runCatching {
    val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = manager.activeNetwork ?: return@runCatching false
    val capabilities = manager.getNetworkCapabilities(network) ?: return@runCatching false
    capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}.getOrDefault(false)