package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AuditLogEntry
import com.example.data.PaymentRequest
import com.example.data.PaymentStatus
import com.example.i18n.Translations
import com.example.ui.MaqraahViewModel
import com.example.ui.components.GlassCard
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.RoyalBlueBorder
import com.example.ui.theme.RoyalBlueCard
import com.example.ui.theme.RoyalBlueDeep
import com.example.ui.theme.RoyalBlueElevated

@Composable
fun AdminScreen(
    viewModel: MaqraahViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.currentLanguage.collectAsState()
    val paymentRequests by viewModel.paymentRequests.collectAsState()
    val auditLogs by viewModel.auditLogs.collectAsState()

    val pendingPayments = paymentRequests.filter { it.status == PaymentStatus.PENDING }

    var broadcastTitle by remember { mutableStateOf("") }
    var broadcastContent by remember { mutableStateOf("") }
    var showBroadcastDialog by remember { mutableStateOf(false) }

    var rejectingRequest by remember { mutableStateOf<PaymentRequest?>(null) }
    var rejectionReasonInput by remember { mutableStateOf("بيانات الحوالة غير متطابقة مع كشف الحساب") }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(RoyalBlueDeep)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header stats
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AdminStatCard(
                        title = Translations.get("stat_students", language),
                        value = "1,250",
                        icon = Icons.Default.People,
                        color = Color(0xFF38BDF8),
                        modifier = Modifier.weight(1f)
                    )
                    AdminStatCard(
                        title = Translations.get("stat_teachers", language),
                        value = "28",
                        icon = Icons.Default.School,
                        color = EmeraldGreen,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    AdminStatCard(
                        title = Translations.get("stat_minutes", language),
                        value = "48,920",
                        icon = Icons.Default.Timer,
                        color = GoldPrimary,
                        modifier = Modifier.weight(1f)
                    )
                    AdminStatCard(
                        title = Translations.get("stat_pending_payments", language),
                        value = "${pendingPayments.size}",
                        icon = Icons.Default.MonetizationOn,
                        color = if (pendingPayments.isNotEmpty()) Color(0xFFEF4444) else EmeraldGreen,
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Quick Broadcast Action
            item {
                Button(
                    onClick = { showBroadcastDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = RoyalBlueDeep
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(46.dp)
                        .testTag("admin_broadcast_action_btn")
                ) {
                    Icon(Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(Translations.get("broadcast_title", language), fontWeight = FontWeight.Bold, fontSize = 13.sp)
                }
            }

            // Pending Payments Review Queue
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${Translations.get("review_payments", language)} (${pendingPayments.size})",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            if (pendingPayments.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(RoyalBlueCard)
                            .padding(20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "لا توجد طلبات دفع معلقة، كافة الحسابات مدققة",
                            color = GoldLight.copy(alpha = 0.6f),
                            fontSize = 13.sp
                        )
                    }
                }
            } else {
                items(pendingPayments, key = { it.id }) { req ->
                    PendingPaymentCard(
                        req = req,
                        onApprove = { viewModel.adminApprovePayment(req.id) },
                        onReject = { rejectingRequest = req }
                    )
                }
            }

            // Audit Logs Section
            item {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(Icons.Default.History, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(20.dp))
                    Text(
                        text = Translations.get("audit_log_title", language),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            items(auditLogs.take(8), key = { it.id }) { log ->
                AuditLogRow(log = log)
            }
        }
    }

    // Broadcast Dialog
    if (showBroadcastDialog) {
        AlertDialog(
            onDismissRequest = { showBroadcastDialog = false },
            containerColor = RoyalBlueDeep,
            title = {
                Text(
                    text = Translations.get("broadcast_title", language),
                    color = GoldPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedTextField(
                        value = broadcastTitle,
                        onValueChange = { broadcastTitle = it },
                        placeholder = { Text("عنوان الإعلان / Announcement Title", fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = RoyalBlueBorder
                        )
                    )

                    OutlinedTextField(
                        value = broadcastContent,
                        onValueChange = { broadcastContent = it },
                        placeholder = { Text("نص الإعلان الرسمي لكافة المشايخ والطلاب...", fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        maxLines = 4,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = RoyalBlueBorder
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (broadcastTitle.isNotBlank() && broadcastContent.isNotBlank()) {
                            viewModel.broadcastAnnouncement(broadcastTitle, broadcastContent)
                            showBroadcastDialog = false
                            broadcastTitle = ""
                            broadcastContent = ""
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = RoyalBlueDeep
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(Translations.get("broadcast_btn", language), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { showBroadcastDialog = false },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight)
                ) {
                    Text("إلغاء")
                }
            }
        )
    }

    // Reject Dialog
    rejectingRequest?.let { req ->
        AlertDialog(
            onDismissRequest = { rejectingRequest = null },
            containerColor = RoyalBlueDeep,
            title = {
                Text(
                    text = "رفض طلب الحوالة: ${req.transactionId}",
                    color = Color(0xFFEF4444),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text(
                        text = "أدخل سبب الرفض ليتم إشعار الطالب به:",
                        fontSize = 12.sp,
                        color = Color.White
                    )
                    OutlinedTextField(
                        value = rejectionReasonInput,
                        onValueChange = { rejectionReasonInput = it },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = Color(0xFFEF4444),
                            unfocusedBorderColor = RoyalBlueBorder
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        viewModel.adminRejectPayment(req.id, rejectionReasonInput)
                        rejectingRequest = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF4444),
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Text(Translations.get("reject", language), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { rejectingRequest = null },
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight)
                ) {
                    Text("إلغاء")
                }
            }
        )
    }
}

@Composable
private fun AdminStatCard(
    title: String,
    value: String,
    icon: ImageVector,
    color: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, color.copy(alpha = 0.3f), RoundedCornerShape(14.dp)),
        color = RoyalBlueCard
    ) {
        Column(
            modifier = Modifier.padding(14.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Text(
                text = title,
                fontSize = 10.sp,
                color = GoldLight.copy(alpha = 0.7f),
                maxLines = 1
            )
        }
    }
}

@Composable
private fun PendingPaymentCard(
    req: PaymentRequest,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .border(1.dp, GoldPrimary.copy(alpha = 0.5f), RoundedCornerShape(14.dp)),
        color = RoyalBlueCard
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = req.studentName,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Text(
                    text = "$${req.amount.toInt()} (${req.minutes} دقيقة)",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldPrimary
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "المعاملة: ${req.transactionId} • ${req.paymentMethod}",
                fontSize = 11.sp,
                color = GoldLight.copy(alpha = 0.8f)
            )

            Text(
                text = "الهاتف: ${req.studentPhone} • ${req.submittedAt}",
                fontSize = 10.sp,
                color = Color.White.copy(alpha = 0.6f)
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = onApprove,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = EmeraldGreen,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(1f)
                        .height(36.dp)
                        .testTag("approve_payment_${req.id}")
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("اعتماد وتفعيل", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                Button(
                    onClick = onReject,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF4444).copy(alpha = 0.2f),
                        contentColor = Color(0xFFEF4444)
                    ),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .weight(0.6f)
                        .height(36.dp)
                        .testTag("reject_payment_${req.id}")
                ) {
                    Text("رفض", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun AuditLogRow(log: AuditLogEntry) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp)),
        color = RoyalBlueCard
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${log.actorName} ➔ ${log.action}",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = GoldLight
                )
                Text(
                    text = log.timestamp,
                    fontSize = 9.sp,
                    color = Color.White.copy(alpha = 0.5f)
                )
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = "${log.target} | ${log.details}",
                fontSize = 11.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }
}
