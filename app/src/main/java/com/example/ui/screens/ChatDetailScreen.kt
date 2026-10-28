package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.ChatType
import com.example.data.Message
import com.example.data.UserRole
import com.example.i18n.Translations
import com.example.ui.MaqraahViewModel
import com.example.ui.Screen
import com.example.ui.components.AudioWaveformBubble
import com.example.ui.components.AvatarBadge
import com.example.ui.components.TajweedBadge
import com.example.ui.theme.ChatBubbleInDark
import com.example.ui.theme.ChatBubbleOutDark
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.RoyalBlueBorder
import com.example.ui.theme.RoyalBlueCard
import com.example.ui.theme.RoyalBlueDeep
import com.example.ui.theme.RoyalBlueElevated

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    viewModel: MaqraahViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val language by viewModel.currentLanguage.collectAsState()
    val conversations by viewModel.filteredConversations.collectAsState()
    val activeConvId by viewModel.activeConversationId.collectAsState()
    val messages by viewModel.activeMessages.collectAsState()
    val isRecordingAudio by viewModel.isRecordingAudio.collectAsState()

    val currentConv = conversations.find { it.id == activeConvId } ?: conversations.firstOrNull()

    var inputText by remember { mutableStateOf("") }
    var showAyahDialog by remember { mutableStateOf(false) }

    val sampleAyat = listOf(
        "بِسْمِ اللَّهِ الرَّحْمَٰنِ الرَّحِيمِ * الْحَمْدُ لِلَّهِ رَبِّ الْعَالَمِينَ * الرَّحْمَٰنِ الرَّحِيمِ * مَالِكِ يَوْمِ الدِّينِ" to "سورة الفاتحة",
        "اللَّهُ لَا إِلَٰهَ إِلَّا هُوَ الْحَيُّ الْقَيُّومُ ۚ لَا تَأْخُذُهُ سِنَةٌ وَلَا نَوْمٌ ۚ لَّهُ مَا فِي السَّمَاوَاتِ وَمَا فِي الْأَرْضِ" to "آية الكرسي",
        "إِنَّا فَتَحْنَا لَكَ فَتْحًا مُّبِينًا * لِّيَغْفِرَ لَكَ اللَّهُ مَا تَقَدَّمَ مِن ذَنبِكَ وَمَا تَأَخَّرَ" to "سورة الفتح",
        "وَاتَّقُوا يَوْمًا تُرْجَعُونَ فِيهِ إِلَى اللَّهِ ۖ ثُمَّ تُوَفَّىٰ كُلُّ نَفْسٍ مَّا كَسَبَتْ وَهُمْ لَا يُظْلَمُونَ" to "سورة البقرة"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoyalBlueDeep)
    ) {
        // Chat Header
        TopAppBar(
            title = {
                currentConv?.let { conv ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        AvatarBadge(
                            letter = conv.avatarLetter,
                            backgroundColor = Color(conv.avatarColor),
                            size = 40.dp,
                            isOnline = conv.onlineStatus == "teaching" || conv.onlineStatus == "online",
                            isTeacher = conv.type == ChatType.PRIVATE && conv.avatarLetter == "أ"
                        )
                        Column {
                            Text(
                                text = conv.title,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                text = if (conv.type == ChatType.GROUP) {
                                    "${conv.participantsCount} مشارك • حلقة إقراء"
                                } else {
                                    Translations.get("status_teaching", language)
                                },
                                fontSize = 11.sp,
                                color = GoldLight.copy(alpha = 0.8f)
                            )
                        }
                    }
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("chat_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = GoldPrimary
                    )
                }
            },
            actions = {
                currentConv?.liveRoomId?.let { roomId ->
                    IconButton(
                        onClick = { viewModel.enterLiveRoom(roomId) },
                        modifier = Modifier.testTag("chat_live_room_action")
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = "Live Room",
                            tint = EmeraldGreen
                        )
                    }
                }
                IconButton(onClick = { viewModel.showToast("خيارات المحادثة / Options") }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "Options",
                        tint = GoldLight
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = RoyalBlueCard
            )
        )

        // Banner if group has active live room
        if (currentConv?.isLiveRoomActive == true && currentConv.liveRoomId != null) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { viewModel.enterLiveRoom(currentConv.liveRoomId!!) }
                    .testTag("chat_top_live_banner"),
                color = EmeraldGreen.copy(alpha = 0.15f),
                border = androidx.compose.foundation.BorderStroke(1.dp, EmeraldGreen.copy(alpha = 0.5f))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(10.dp)
                                .clip(CircleShape)
                                .background(EmeraldGreen)
                        )
                        Text(
                            text = Translations.get("live_room_in_progress", language),
                            fontSize = 12.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Text(
                        text = Translations.get("join_live_room", language),
                        color = GoldPrimary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // Messages List
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .testTag("chat_messages_list"),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(messages, key = { it.id }) { msg ->
                MessageBubbleItem(msg = msg)
            }
        }

        // Recording indicator bar
        AnimatedVisibility(
            visible = isRecordingAudio,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF991B1B).copy(alpha = 0.3f))
                    .border(1.dp, Color(0xFFEF4444), RoundedCornerShape(0.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Mic,
                        contentDescription = null,
                        tint = Color(0xFFEF4444),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = Translations.get("recording", language),
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
                IconButton(
                    onClick = { viewModel.toggleAudioRecording() },
                    modifier = Modifier.testTag("stop_recording_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.Stop,
                        contentDescription = "Stop",
                        tint = Color.White
                    )
                }
            }
        }

        // Input Field Bar
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = RoyalBlueCard
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // Quick Ayah insertion button
                IconButton(
                    onClick = { showAyahDialog = true },
                    modifier = Modifier.testTag("quick_ayah_button")
                ) {
                    Icon(
                        imageVector = Icons.Default.AutoStories,
                        contentDescription = "Ayah",
                        tint = GoldPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }

                OutlinedTextField(
                    value = inputText,
                    onValueChange = { inputText = it },
                    placeholder = {
                        Text(
                            Translations.get("type_message", language),
                            color = GoldLight.copy(alpha = 0.6f),
                            fontSize = 13.sp
                        )
                    },
                    modifier = Modifier
                        .weight(1f)
                        .testTag("chat_input_field"),
                    shape = RoundedCornerShape(22.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = RoyalBlueDeep,
                        unfocusedContainerColor = RoyalBlueDeep,
                        focusedBorderColor = GoldPrimary,
                        unfocusedBorderColor = RoyalBlueBorder,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White
                    ),
                    maxLines = 4
                )

                if (inputText.isNotBlank()) {
                    IconButton(
                        onClick = {
                            viewModel.sendMessage(inputText)
                            inputText = ""
                        },
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(GoldPrimary)
                            .testTag("chat_send_button")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Send",
                            tint = RoyalBlueDeep,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                } else {
                    IconButton(
                        onClick = { viewModel.toggleAudioRecording() },
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .background(if (isRecordingAudio) Color(0xFFEF4444) else GoldPrimary)
                            .testTag("chat_mic_record_button")
                    ) {
                        Icon(
                            imageVector = if (isRecordingAudio) Icons.Default.Stop else Icons.Default.Mic,
                            contentDescription = "Record Recitation",
                            tint = if (isRecordingAudio) Color.White else RoyalBlueDeep,
                            modifier = Modifier.size(22.dp)
                        )
                    }
                }
            }
        }
    }

    // Ayah Picker Dialog
    if (showAyahDialog) {
        AlertDialog(
            onDismissRequest = { showAyahDialog = false },
            containerColor = RoyalBlueDeep,
            title = {
                Text(
                    text = Translations.get("quick_ayah", language),
                    color = GoldPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    sampleAyat.forEach { (ayah, ref) ->
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    viewModel.sendMessage("📖 [$ref]:\n$ayah")
                                    showAyahDialog = false
                                },
                            color = RoyalBlueCard,
                            shape = RoundedCornerShape(10.dp),
                            border = androidx.compose.foundation.BorderStroke(1.dp, RoyalBlueBorder)
                        ) {
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = ref,
                                    fontSize = 11.sp,
                                    color = GoldLight,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = ayah,
                                    fontSize = 13.sp,
                                    color = Color.White,
                                    lineHeight = 20.sp
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { showAyahDialog = false },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = RoyalBlueDeep
                    )
                ) {
                    Text("إلغاء / Cancel")
                }
            }
        )
    }
}

@Composable
private fun MessageBubbleItem(msg: Message) {
    val isOut = msg.isFromMe

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isOut) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .clip(
                    RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isOut) 16.dp else 2.dp,
                        bottomEnd = if (isOut) 2.dp else 16.dp
                    )
                )
                .background(if (isOut) ChatBubbleOutDark else ChatBubbleInDark)
                .border(
                    width = 1.dp,
                    color = if (isOut) GoldPrimary.copy(alpha = 0.35f) else RoyalBlueBorder,
                    shape = RoundedCornerShape(
                        topStart = 16.dp,
                        topEnd = 16.dp,
                        bottomStart = if (isOut) 16.dp else 2.dp,
                        bottomEnd = if (isOut) 2.dp else 16.dp
                    )
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxWidth(0.85f)
        ) {
            Column {
                if (!isOut) {
                    Text(
                        text = msg.senderName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = GoldLight
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }

                if (msg.isAudioRecitation) {
                    AudioWaveformBubble(
                        durationSec = msg.audioDurationSec,
                        waveform = msg.audioWaveform,
                        isFromMe = isOut
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                }

                Text(
                    text = msg.content,
                    fontSize = 14.sp,
                    color = Color.White,
                    lineHeight = 20.sp
                )

                msg.tajweedFeedback?.let { fb ->
                    Spacer(modifier = Modifier.height(6.dp))
                    TajweedBadge(
                        ruleText = fb.ruleName,
                        colorHex = fb.colorHex
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Row(
                    modifier = Modifier.align(Alignment.End),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = msg.timestamp,
                        fontSize = 10.sp,
                        color = GoldLight.copy(alpha = 0.6f)
                    )
                    if (isOut) {
                        Icon(
                            imageVector = Icons.Default.DoneAll,
                            contentDescription = "Read",
                            tint = Color(0xFF38BDF8),
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}
