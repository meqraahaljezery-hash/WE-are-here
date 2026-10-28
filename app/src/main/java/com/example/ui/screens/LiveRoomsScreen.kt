package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.FrontHand
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ScreenShare
import androidx.compose.material.icons.filled.StopScreenShare
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material.icons.filled.VideocamOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LiveRoom
import com.example.data.UserRole
import com.example.i18n.Translations
import com.example.ui.MaqraahViewModel
import com.example.ui.components.AvatarBadge
import com.example.ui.components.GlassCard
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldWarm
import com.example.ui.theme.RoyalBlueBorder
import com.example.ui.theme.RoyalBlueCard
import com.example.ui.theme.RoyalBlueDeep
import com.example.ui.theme.RoyalBlueElevated

@Composable
fun LiveRoomsListScreen(
    viewModel: MaqraahViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.currentLanguage.collectAsState()
    val liveRooms by viewModel.liveRooms.collectAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoyalBlueDeep)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(12.dp))

        // Header Card
        GlassCard(
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(GoldPrimary.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.VolumeUp,
                            contentDescription = null,
                            tint = GoldPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Column {
                        Text(
                            text = Translations.get("live_rooms_title", language),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                        Text(
                            text = Translations.get("live_rooms_desc", language),
                            fontSize = 11.sp,
                            color = GoldLight.copy(alpha = 0.8f)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .testTag("live_rooms_list"),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(liveRooms, key = { it.id }) { room ->
                LiveRoomCardItem(
                    room = room,
                    onEnter = { viewModel.enterLiveRoom(room.id) }
                )
            }
        }
    }
}

@Composable
private fun LiveRoomCardItem(
    room: LiveRoom,
    onEnter: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, GoldPrimary.copy(alpha = 0.3f), RoundedCornerShape(16.dp))
            .clickable(onClick = onEnter)
            .testTag("live_room_card_${room.id}"),
        color = RoyalBlueCard
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
                        text = "مباشر الآن / Live",
                        color = EmeraldGreen,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Groups,
                        contentDescription = null,
                        tint = GoldLight,
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "${room.activeCount} حاضر",
                        color = GoldLight,
                        fontSize = 11.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = room.riwayahName,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${room.sheikhName} (${room.sheikhRole})",
                fontSize = 12.sp,
                color = GoldPrimary,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(10.dp))

            HorizontalDivider(color = RoyalBlueBorder.copy(alpha = 0.5f))

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "القائم بالعرض:",
                        fontSize = 10.sp,
                        color = GoldLight.copy(alpha = 0.6f)
                    )
                    Text(
                        text = room.activeReciterName ?: "لا يوجد رادود حالياً (الباب مفتوح)",
                        fontSize = 12.sp,
                        color = if (room.activeReciterName != null) GoldLight else Color.White.copy(alpha = 0.5f),
                        fontWeight = FontWeight.SemiBold
                    )
                }

                Button(
                    onClick = onEnter,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = RoyalBlueDeep
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("enter_room_button_${room.id}")
                ) {
                    Text("دخول المقرأة", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiveRoomDetailScreen(
    viewModel: MaqraahViewModel,
    onBack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val language by viewModel.currentLanguage.collectAsState()
    val roomState by viewModel.currentLiveRoom.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val isSessionActive by viewModel.isLiveSessionActive.collectAsState()
    val elapsedSeconds by viewModel.activeSessionSeconds.collectAsState()

    val room = roomState ?: return

    val isInQueue = room.waitingQueue.any { it.studentId == currentUser.id }
    val userQueuePosition = room.waitingQueue.indexOfFirst { it.studentId == currentUser.id } + 1

    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseAlpha by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulse_anim"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoyalBlueDeep)
    ) {
        TopAppBar(
            title = {
                Column {
                    Text(
                        text = room.riwayahName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = "${room.activeCount} مشارك • بث صوتي مباشر",
                        fontSize = 10.sp,
                        color = EmeraldGreen
                    )
                }
            },
            navigationIcon = {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.testTag("live_room_back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = GoldPrimary
                    )
                }
            },
            actions = {
                Button(
                    onClick = onBack,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF4444).copy(alpha = 0.2f),
                        contentColor = Color(0xFFEF4444)
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 10.dp, vertical = 4.dp),
                    modifier = Modifier.testTag("leave_live_room_button")
                ) {
                    Text(Translations.get("leave_room", language), fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = RoyalBlueCard
            )
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Sheikh Stage Card
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.5.dp, GoldPrimary, RoundedCornerShape(16.dp)),
                    color = RoyalBlueCard
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        // Sheikh Avatar with animated halo
                        Box(
                            modifier = Modifier
                                .size(80.dp)
                                .clip(CircleShape)
                                .border(3.dp, GoldPrimary.copy(alpha = pulseAlpha), CircleShape)
                                .background(RoyalBlueElevated),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "أ",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldLight
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = room.sheikhName,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        Text(
                            text = room.sheikhRole,
                            fontSize = 11.sp,
                            color = GoldLight.copy(alpha = 0.8f)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        // Live audio status pill
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(RoyalBlueDeep)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Icon(
                                imageVector = if (room.isSheikhMuted) Icons.Default.MicOff else Icons.Default.Mic,
                                contentDescription = null,
                                tint = if (room.isSheikhMuted) Color(0xFFEF4444) else EmeraldGreen,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = if (room.isSheikhMuted) "صوت الشيخ مكتوم" else "صوت الشيخ مباشر",
                                fontSize = 11.sp,
                                color = Color.White
                            )
                        }

                        // Teacher controls if current user is Teacher
                        if (currentUser.role == UserRole.TEACHER || currentUser.role == UserRole.ADMIN) {
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                OutlinedButton(
                                    onClick = { viewModel.repository.toggleSheikhMic() },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight)
                                ) {
                                    Text(if (room.isSheikhMuted) "تشغيل اللاقط" else "كتم اللاقط", fontSize = 11.sp)
                                }
                                OutlinedButton(
                                    onClick = { viewModel.repository.toggleMushafShare() },
                                    shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight)
                                ) {
                                    Text(if (room.isMushafShared) "إخفاء المصحف" else "مشاركة المصحف", fontSize = 11.sp)
                                }
                            }
                        }
                    }
                }
            }

            // Displayed Ayah from Mushaf
            if (room.isMushafShared) {
                item {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .border(1.dp, GoldPrimary.copy(alpha = 0.5f), RoundedCornerShape(16.dp)),
                        color = Color(0xFF0F1E40)
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AutoStories,
                                        contentDescription = null,
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = Translations.get("mushaf_display", language),
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldLight
                                    )
                                }
                                Text(
                                    text = room.currentSurahRef,
                                    fontSize = 11.sp,
                                    color = GoldPrimary,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Text(
                                text = room.currentAyahArabic,
                                fontSize = 16.sp,
                                lineHeight = 28.sp,
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            // Active Reciter / In-Session Timer
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(14.dp))
                        .background(RoyalBlueCard)
                        .border(1.dp, if (isSessionActive) EmeraldGreen else RoyalBlueBorder, RoundedCornerShape(14.dp)),
                    color = RoyalBlueCard
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = Translations.get("current_reciter", language),
                                fontSize = 11.sp,
                                color = GoldLight.copy(alpha = 0.7f)
                            )
                            Text(
                                text = room.activeReciterName ?: "في انتظار صعود الطالب التالي",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (room.activeReciterName != null) GoldPrimary else Color.White.copy(alpha = 0.6f)
                            )
                        }

                        if (isSessionActive) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(EmeraldGreen.copy(alpha = 0.2f))
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Timer,
                                    contentDescription = null,
                                    tint = EmeraldGreen,
                                    modifier = Modifier.size(16.dp)
                                )
                                Text(
                                    text = String.format("%02d:%02d", elapsedSeconds / 60, elapsedSeconds % 60),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = EmeraldGreen
                                )
                            }
                        }
                    }
                }
            }

            // Waiting Queue Section
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${Translations.get("waiting_queue", language)} (${room.waitingQueue.size})",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )

                        if (isInQueue) {
                            Text(
                                text = "${Translations.get("turn_position", language)} #$userQueuePosition",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold,
                                color = GoldPrimary
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    if (room.waitingQueue.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(12.dp))
                                .background(RoyalBlueCard)
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "لا يوجد طلاب في قائمة الانتظار، اضغط أدناه لطلب العرض",
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.6f)
                            )
                        }
                    } else {
                        room.waitingQueue.forEachIndexed { idx, item ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 3.dp),
                                color = if (item.studentId == currentUser.id) RoyalBlueElevated else RoyalBlueCard,
                                shape = RoundedCornerShape(10.dp),
                                border = if (item.studentId == currentUser.id) androidx.compose.foundation.BorderStroke(1.dp, GoldPrimary) else null
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 12.dp, vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                    ) {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                                .background(if (idx == 0) GoldPrimary else RoyalBlueDeep),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = "${idx + 1}",
                                                fontSize = 11.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = if (idx == 0) RoyalBlueDeep else Color.White
                                            )
                                        }
                                        Column {
                                            Text(
                                                text = item.studentName,
                                                fontSize = 13.sp,
                                                fontWeight = FontWeight.Bold,
                                                color = Color.White
                                            )
                                            Text(
                                                text = "${item.riwayah} • ${item.surahTarget}",
                                                fontSize = 10.sp,
                                                color = GoldLight.copy(alpha = 0.6f)
                                            )
                                        }
                                    }

                                    // If Teacher, option to start session
                                    if (currentUser.role == UserRole.TEACHER && idx == 0) {
                                        Button(
                                            onClick = { viewModel.teacherStartSession(item) },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = EmeraldGreen,
                                                contentColor = Color.White
                                            ),
                                            shape = RoundedCornerShape(8.dp),
                                            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                                            modifier = Modifier.testTag("teacher_start_student_btn")
                                        ) {
                                            Text("بدء العرض", fontSize = 10.sp, fontWeight = FontWeight.Bold)
                                        }
                                    } else {
                                        Text(
                                            text = item.joinedTimestamp,
                                            fontSize = 10.sp,
                                            color = GoldLight.copy(alpha = 0.5f)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        // Bottom Action Bar: Raise Hand / Cancel Turn
        Surface(
            modifier = Modifier.fillMaxWidth(),
            color = RoyalBlueCard
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Button(
                    onClick = { viewModel.toggleRaiseHand(room.id) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .testTag("toggle_raise_hand_button"),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isInQueue) Color(0xFFEF4444) else GoldPrimary,
                        contentColor = if (isInQueue) Color.White else RoyalBlueDeep
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.FrontHand,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (isInQueue) Translations.get("cancel_turn", language) else Translations.get("raise_hand", language),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}
