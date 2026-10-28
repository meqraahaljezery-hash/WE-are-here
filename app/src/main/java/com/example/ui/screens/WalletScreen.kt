package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.ContentPaste
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.LedgerType
import com.example.data.MinuteLedgerEntry
import com.example.data.Package
import com.example.i18n.Translations
import com.example.ui.MaqraahViewModel
import com.example.ui.components.GlassCard
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldGradientEnd
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.GoldWarm
import com.example.ui.theme.RoyalBlueBorder
import com.example.ui.theme.RoyalBlueCard
import com.example.ui.theme.RoyalBlueDeep
import com.example.ui.theme.RoyalBlueElevated

@Composable
fun WalletScreen(
    viewModel: MaqraahViewModel,
    modifier: Modifier = Modifier
) {
    val language by viewModel.currentLanguage.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val packages by viewModel.packages.collectAsState()
    val ledger by viewModel.minuteLedger.collectAsState()

    var selectedPackageForPurchase by remember { mutableStateOf<Package?>(null) }
    var senderName by remember { mutableStateOf(currentUser.name) }
    var transactionId by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf(currentUser.phone) }
    var selectedMethod by remember { mutableStateOf("Telebirr / CBE Bank") }

    val paymentMethods = listOf(
        "Telebirr / CBE Bank",
        "Al Rajhi Bank (الراجحي)",
        "Vodafone Cash",
        "International Wire"
    )

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
            // Minutes Balance Card
            item {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .border(1.5.dp, GoldPrimary, RoundedCornerShape(20.dp)),
                    color = Color.Transparent
                ) {
                    Box(
                        modifier = Modifier
                            .background(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF1B2F63),
                                        Color(0xFF0F1B38)
                                    )
                                )
                            )
                            .padding(20.dp)
                    ) {
                        Column {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccountBalanceWallet,
                                        contentDescription = null,
                                        tint = GoldPrimary,
                                        modifier = Modifier.size(24.dp)
                                    )
                                    Text(
                                        text = Translations.get("minute_balance", language),
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldLight
                                    )
                                }

                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(GoldPrimary.copy(alpha = 0.2f))
                                        .padding(horizontal = 10.dp, vertical = 4.dp)
                                ) {
                                    Text(
                                        text = currentUser.sanadTrack,
                                        color = GoldPrimary,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Text(
                                    text = "${currentUser.minutesBalance}",
                                    fontSize = 42.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                                Text(
                                    text = Translations.get("minutes", language),
                                    fontSize = 18.sp,
                                    color = GoldPrimary,
                                    fontWeight = FontWeight.SemiBold,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                            }

                            if (currentUser.minutesBalance < 15) {
                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(Color(0xFFEF4444).copy(alpha = 0.2f))
                                        .padding(8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Warning,
                                        contentDescription = null,
                                        tint = Color(0xFFEF4444),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Text(
                                        text = Translations.get("low_balance_alert", language),
                                        color = Color(0xFFFCA5A5),
                                        fontSize = 11.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Available Packages Section
            item {
                Text(
                    text = Translations.get("packages_title", language),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            items(packages, key = { it.id }) { pkg ->
                PackageCardItem(
                    pkg = pkg,
                    onBuy = {
                        selectedPackageForPurchase = pkg
                        senderName = currentUser.name
                        phoneNumber = currentUser.phone
                    }
                )
            }

            // Ledger History Section
            item {
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.History,
                        contentDescription = null,
                        tint = GoldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = Translations.get("ledger_history", language),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            items(ledger, key = { it.id }) { entry ->
                LedgerEntryRow(entry = entry)
            }
        }
    }

    // Manual Payment Submission Dialog
    selectedPackageForPurchase?.let { pkg ->
        AlertDialog(
            onDismissRequest = { selectedPackageForPurchase = null },
            containerColor = RoyalBlueDeep,
            title = {
                Text(
                    text = Translations.get("manual_payment_title", language),
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
                    Text(
                        text = "${pkg.nameAr} (${pkg.minutes} دقيقة) - ${pkg.price} ${pkg.currency}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )

                    Text(
                        text = Translations.get("manual_payment_desc", language),
                        fontSize = 11.sp,
                        color = GoldLight.copy(alpha = 0.8f),
                        lineHeight = 16.sp
                    )

                    OutlinedTextField(
                        value = senderName,
                        onValueChange = { senderName = it },
                        label = { Text(Translations.get("sender_name", language), fontSize = 11.sp) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("payment_sender_name_input"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = RoyalBlueBorder
                        )
                    )

                    OutlinedTextField(
                        value = transactionId,
                        onValueChange = { transactionId = it },
                        label = { Text(Translations.get("transaction_id", language), fontSize = 11.sp) },
                        trailingIcon = {
                            IconButton(onClick = { transactionId = "TX${System.currentTimeMillis().toString().takeLast(8)}" }) {
                                Icon(Icons.Default.ContentPaste, contentDescription = "Paste", tint = GoldPrimary)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("payment_tx_id_input"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = RoyalBlueBorder
                        )
                    )

                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = { phoneNumber = it },
                        label = { Text(Translations.get("phone_number", language), fontSize = 11.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = Color.White,
                            unfocusedTextColor = Color.White,
                            focusedBorderColor = GoldPrimary,
                            unfocusedBorderColor = RoyalBlueBorder
                        )
                    )

                    Column {
                        Text(
                            text = Translations.get("payment_method", language),
                            fontSize = 11.sp,
                            color = GoldLight
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            paymentMethods.take(2).forEach { method ->
                                val isSelected = selectedMethod == method
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (isSelected) GoldPrimary else RoyalBlueCard)
                                        .clickable { selectedMethod = method }
                                        .padding(horizontal = 8.dp, vertical = 6.dp)
                                ) {
                                    Text(
                                        text = method,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isSelected) RoyalBlueDeep else Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val tx = if (transactionId.isNotBlank()) transactionId else "TX${(10000000..99999999).random()}"
                        viewModel.submitManualPayment(
                            packageName = pkg.nameAr,
                            minutes = pkg.minutes,
                            amount = pkg.price,
                            senderName = senderName,
                            transactionId = tx,
                            phone = phoneNumber,
                            method = selectedMethod
                        )
                        selectedPackageForPurchase = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = RoyalBlueDeep
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.testTag("submit_manual_payment_button")
                ) {
                    Text(Translations.get("submit_request", language), fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                OutlinedButton(
                    onClick = { selectedPackageForPurchase = null },
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GoldLight)
                ) {
                    Text("إلغاء / Cancel")
                }
            }
        )
    }
}

@Composable
private fun PackageCardItem(
    pkg: Package,
    onBuy: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(
                1.5.dp,
                if (pkg.isPopular) GoldPrimary else RoyalBlueBorder,
                RoundedCornerShape(16.dp)
            ),
        color = if (pkg.isPopular) RoyalBlueElevated else RoyalBlueCard
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(if (pkg.isPopular) GoldPrimary else RoyalBlueDeep)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                ) {
                    Text(
                        text = pkg.badge,
                        color = if (pkg.isPopular) RoyalBlueDeep else GoldLight,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "$${pkg.price.toInt()}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = GoldPrimary
                    )
                    Text(
                        text = " / ${pkg.currency}",
                        fontSize = 11.sp,
                        color = GoldLight.copy(alpha = 0.7f),
                        modifier = Modifier.padding(bottom = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = pkg.nameAr,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = pkg.descriptionAr,
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.8f),
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "${pkg.minutes} دقيقة إقراء معتمدة",
                    fontSize = 12.sp,
                    color = EmeraldGreen,
                    fontWeight = FontWeight.Bold
                )

                Button(
                    onClick = onBuy,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = GoldPrimary,
                        contentColor = RoyalBlueDeep
                    ),
                    shape = RoundedCornerShape(10.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 6.dp),
                    modifier = Modifier.testTag("buy_package_${pkg.id}")
                ) {
                    Text("شراء الباقة", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
private fun LedgerEntryRow(entry: MinuteLedgerEntry) {
    val isCredit = entry.deltaMinutes > 0

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp)),
        color = RoyalBlueCard
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = entry.description,
                    fontSize = 12.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Medium
                )
                Spacer(modifier = Modifier.height(3.dp))
                Text(
                    text = entry.timestamp,
                    fontSize = 10.sp,
                    color = GoldLight.copy(alpha = 0.6f)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = if (isCredit) "+${entry.deltaMinutes} دقيقة" else "${entry.deltaMinutes} دقيقة",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isCredit) EmeraldGreen else Color(0xFFEF4444)
                )
                Text(
                    text = "الرصيد: ${entry.balanceAfter}",
                    fontSize = 10.sp,
                    color = GoldLight.copy(alpha = 0.7f)
                )
            }
        }
    }
}
