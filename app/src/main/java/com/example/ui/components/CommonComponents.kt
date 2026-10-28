package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FiberManualRecord
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Stars
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserRole
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.RoyalBlueBorder
import com.example.ui.theme.RoyalBlueCard
import com.example.ui.theme.RoyalBlueDeep
import com.example.ui.theme.RoyalBlueElevated

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = RoyalBlueCard.copy(alpha = 0.85f),
    borderColor: Color = GoldPrimary.copy(alpha = 0.25f),
    cornerRadius: Dp = 16.dp,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .border(1.dp, borderColor, RoundedCornerShape(cornerRadius)),
        color = backgroundColor,
        shadowElevation = 4.dp
    ) {
        content()
    }
}

@Composable
fun AvatarBadge(
    letter: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    isOnline: Boolean = true,
    isTeacher: Boolean = false
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(size)
                .clip(CircleShape)
                .background(
                    Brush.radialGradient(
                        colors = listOf(
                            backgroundColor,
                            backgroundColor.copy(alpha = 0.7f)
                        )
                    )
                )
                .border(1.5.dp, GoldPrimary.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = letter,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = (size.value * 0.42).sp
            )
        }

        if (isTeacher) {
            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(16.dp)
                    .clip(CircleShape)
                    .background(GoldPrimary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Verified,
                    contentDescription = "Verified Sheikh",
                    tint = RoyalBlueDeep,
                    modifier = Modifier.size(12.dp)
                )
            }
        }

        if (isOnline) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .size(14.dp)
                    .clip(CircleShape)
                    .background(RoyalBlueDeep)
                    .padding(2.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(EmeraldGreen)
                )
            }
        }
    }
}

@Composable
fun AudioWaveformBubble(
    durationSec: Int,
    waveform: List<Float>,
    modifier: Modifier = Modifier,
    isFromMe: Boolean = false
) {
    var isPlaying by remember { mutableStateOf(false) }
    val infiniteTransition = rememberInfiniteTransition(label = "audio_bars")
    val animatedScale by infiniteTransition.animateFloat(
        initialValue = 0.4f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "waveform_anim"
    )

    Row(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(if (isFromMe) RoyalBlueElevated else RoyalBlueCard)
            .padding(horizontal = 12.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(
            onClick = { isPlaying = !isPlaying },
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(GoldPrimary)
                .testTag("audio_play_pause_button")
        ) {
            Icon(
                imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                contentDescription = if (isPlaying) "Pause" else "Play",
                tint = RoyalBlueDeep,
                modifier = Modifier.size(20.dp)
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Waveform bars
        Row(
            modifier = Modifier
                .weight(1f)
                .height(28.dp),
            horizontalArrangement = Arrangement.spacedBy(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val bars = if (waveform.isNotEmpty()) waveform else listOf(0.3f, 0.6f, 0.9f, 0.5f, 0.7f, 0.4f, 0.8f, 0.6f, 0.9f, 0.4f, 0.7f, 0.3f)
            bars.forEachIndexed { index, heightFactor ->
                val currentHeight = if (isPlaying) {
                    (heightFactor * animatedScale * 22f).coerceIn(4f, 24f).dp
                } else {
                    (heightFactor * 20f).coerceIn(4f, 22f).dp
                }
                Box(
                    modifier = Modifier
                        .width(3.dp)
                        .height(currentHeight)
                        .clip(RoundedCornerShape(1.5.dp))
                        .background(if (isPlaying) GoldPrimary else GoldLight.copy(alpha = 0.7f))
                )
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = String.format("%02d:%02d", durationSec / 60, durationSec % 60),
            color = GoldLight,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun TajweedBadge(
    ruleText: String,
    modifier: Modifier = Modifier,
    colorHex: Long = 0xFFD4AF37
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(Color(colorHex).copy(alpha = 0.18f))
            .border(1.dp, Color(colorHex).copy(alpha = 0.5f), RoundedCornerShape(8.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Icon(
            imageVector = Icons.Default.Stars,
            contentDescription = null,
            tint = Color(colorHex),
            modifier = Modifier.size(12.dp)
        )
        Text(
            text = ruleText,
            color = Color(colorHex),
            fontSize = 11.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun RolePill(
    role: UserRole,
    modifier: Modifier = Modifier
) {
    val (label, bg, fg) = when (role) {
        UserRole.STUDENT -> Triple("طالب مقرأة", RoyalBlueElevated, GoldLight)
        UserRole.TEACHER -> Triple("شيخ مقرئ", EmeraldGreen.copy(alpha = 0.2f), EmeraldGreen)
        UserRole.SUPERVISOR -> Triple("مشرف", RoyalBlueBorder, Color(0xFF38BDF8))
        UserRole.ADMIN -> Triple("إدارة عليا", GoldDark.copy(alpha = 0.3f), GoldPrimary)
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bg)
            .border(0.8.dp, fg.copy(alpha = 0.4f), RoundedCornerShape(12.dp))
            .padding(horizontal = 8.dp, vertical = 3.dp)
    ) {
        Text(
            text = label,
            color = fg,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun QuickActionsFAB(
    onJoinLiveRoom: () -> Unit,
    onBuyPackage: () -> Unit,
    onVoiceRecitation: () -> Unit,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (expanded) {
            FloatingActionButton(
                onClick = {
                    expanded = false
                    onVoiceRecitation()
                },
                containerColor = EmeraldGreen,
                contentColor = Color.White,
                modifier = Modifier
                    .size(44.dp)
                    .testTag("fab_voice_recitation")
            ) {
                Icon(Icons.Default.Mic, contentDescription = "Voice Recitation", modifier = Modifier.size(20.dp))
            }

            FloatingActionButton(
                onClick = {
                    expanded = false
                    onBuyPackage()
                },
                containerColor = GoldPrimary,
                contentColor = RoyalBlueDeep,
                modifier = Modifier
                    .size(44.dp)
                    .testTag("fab_buy_package")
            ) {
                Icon(Icons.Default.ShoppingBag, contentDescription = "Buy Minutes", modifier = Modifier.size(20.dp))
            }

            FloatingActionButton(
                onClick = {
                    expanded = false
                    onJoinLiveRoom()
                },
                containerColor = Color(0xFF0EA5E9),
                contentColor = Color.White,
                modifier = Modifier
                    .size(44.dp)
                    .testTag("fab_join_live_room")
            ) {
                Icon(Icons.Default.VolumeUp, contentDescription = "Join Live Room", modifier = Modifier.size(20.dp))
            }
        }

        FloatingActionButton(
            onClick = { expanded = !expanded },
            containerColor = GoldPrimary,
            contentColor = RoyalBlueDeep,
            modifier = Modifier
                .size(54.dp)
                .testTag("fab_quick_actions")
        ) {
            Icon(
                imageVector = Icons.Default.Speed,
                contentDescription = "Quick Actions",
                modifier = Modifier.size(26.dp)
            )
        }
    }
}
