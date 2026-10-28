package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.UserRole
import com.example.i18n.AppLanguage
import com.example.i18n.Translations
import com.example.ui.components.QuickActionsFAB
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.ChatDetailScreen
import com.example.ui.screens.ChatListScreen
import com.example.ui.screens.LiveRoomDetailScreen
import com.example.ui.screens.LiveRoomsListScreen
import com.example.ui.screens.TeacherScreen
import com.example.ui.screens.WalletScreen
import com.example.ui.theme.AlJazariTheme
import com.example.ui.theme.EmeraldGreen
import com.example.ui.theme.GoldDark
import com.example.ui.theme.GoldLight
import com.example.ui.theme.GoldPrimary
import com.example.ui.theme.RoyalBlueBorder
import com.example.ui.theme.RoyalBlueCard
import com.example.ui.theme.RoyalBlueDeep
import com.example.ui.theme.RoyalBlueElevated
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MaqraahApp(
    viewModel: MaqraahViewModel
) {
    val language by viewModel.currentLanguage.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val currentScreen by viewModel.currentScreen.collectAsState()
    val toastMessage by viewModel.toastMessage.collectAsState()

    var showLanguageMenu by remember { mutableStateOf(false) }
    var showRoleMenu by remember { mutableStateOf(false) }

    // Auto-dismiss toast
    LaunchedEffect(toastMessage) {
        if (toastMessage != null) {
            delay(3000)
            viewModel.clearToast()
        }
    }

    // Dynamic RTL/LTR Layout Direction
    CompositionLocalProvider(LocalLayoutDirection provides language.layoutDirection) {
        AlJazariTheme(
            darkTheme = true,
            activeFontFamily = language.fontFamily
        ) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .statusBarsPadding()
                    .navigationBarsPadding(),
                topBar = {
                    if (currentScreen != Screen.CHAT_DETAIL && currentScreen != Screen.LIVE_ROOM_DETAIL) {
                        TopAppBar(
                            title = {
                                Column {
                                    Text(
                                        text = Translations.get("app_title", language),
                                        fontSize = 15.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = GoldPrimary
                                    )
                                    Text(
                                        text = Translations.get("app_subtitle", language),
                                        fontSize = 10.sp,
                                        color = GoldLight.copy(alpha = 0.8f)
                                    )
                                }
                            },
                            actions = {
                                // Role switcher pill
                                Box {
                                    Surface(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(12.dp))
                                            .border(1.dp, GoldPrimary.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
                                            .clickable { showRoleMenu = true }
                                            .testTag("role_switcher_pill"),
                                        color = RoyalBlueElevated
                                    ) {
                                        Row(
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Icon(
                                                imageVector = Icons.Default.SwapHoriz,
                                                contentDescription = "Switch Role",
                                                tint = GoldPrimary,
                                                modifier = Modifier.size(14.dp)
                                            )
                                            Text(
                                                text = when (currentUser.role) {
                                                    UserRole.STUDENT -> "طالب"
                                                    UserRole.TEACHER -> "شيخ"
                                                    UserRole.SUPERVISOR -> "مشرف"
                                                    UserRole.ADMIN -> "إدارة"
                                                },
                                                fontSize = 10.sp,
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }

                                    DropdownMenu(
                                        expanded = showRoleMenu,
                                        onDismissRequest = { showRoleMenu = false },
                                        modifier = Modifier.background(RoyalBlueCard)
                                    ) {
                                        UserRole.entries.forEach { role ->
                                            DropdownMenuItem(
                                                text = {
                                                    Text(
                                                        text = when (role) {
                                                            UserRole.STUDENT -> Translations.get("role_student", language)
                                                            UserRole.TEACHER -> Translations.get("role_teacher", language)
                                                            UserRole.SUPERVISOR -> Translations.get("role_supervisor", language)
                                                            UserRole.ADMIN -> Translations.get("role_admin", language)
                                                        },
                                                        fontSize = 12.sp,
                                                        color = if (currentUser.role == role) GoldPrimary else Color.White,
                                                        fontWeight = if (currentUser.role == role) FontWeight.Bold else FontWeight.Normal
                                                    )
                                                },
                                                onClick = {
                                                    viewModel.switchRole(role)
                                                    showRoleMenu = false
                                                }
                                            )
                                        }
                                    }
                                }

                                Spacer(modifier = Modifier.width(6.dp))

                                // Language selector
                                Box {
                                    IconButton(
                                        onClick = { showLanguageMenu = true },
                                        modifier = Modifier.testTag("language_selector_button")
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Language,
                                            contentDescription = "Language",
                                            tint = GoldPrimary
                                        )
                                    }

                                    DropdownMenu(
                                        expanded = showLanguageMenu,
                                        onDismissRequest = { showLanguageMenu = false },
                                        modifier = Modifier.background(RoyalBlueCard)
                                    ) {
                                        AppLanguage.entries.forEach { lang ->
                                            DropdownMenuItem(
                                                text = {
                                                    Row(
                                                        verticalAlignment = Alignment.CenterVertically,
                                                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                                                    ) {
                                                        if (language == lang) {
                                                            Icon(Icons.Default.Check, contentDescription = null, tint = GoldPrimary, modifier = Modifier.size(16.dp))
                                                        }
                                                        Text(
                                                            text = lang.nativeName,
                                                            color = if (language == lang) GoldPrimary else Color.White,
                                                            fontWeight = if (language == lang) FontWeight.Bold else FontWeight.Normal,
                                                            fontSize = 13.sp
                                                        )
                                                    }
                                                },
                                                onClick = {
                                                    viewModel.switchLanguage(lang)
                                                    showLanguageMenu = false
                                                }
                                            )
                                        }
                                    }
                                }
                            },
                            colors = TopAppBarDefaults.topAppBarColors(
                                containerColor = RoyalBlueDeep
                            )
                        )
                    }
                },
                bottomBar = {
                    if (currentScreen != Screen.CHAT_DETAIL && currentScreen != Screen.LIVE_ROOM_DETAIL) {
                        NavigationBar(
                            containerColor = RoyalBlueCard,
                            tonalElevation = 8.dp
                        ) {
                            NavigationBarItem(
                                selected = currentScreen == Screen.CHATS,
                                onClick = { viewModel.navigateTo(Screen.CHATS) },
                                icon = {
                                    Icon(Icons.Default.ChatBubble, contentDescription = "Chats")
                                },
                                label = {
                                    Text(
                                        Translations.get("nav_chats", language),
                                        fontSize = 10.sp,
                                        fontWeight = if (currentScreen == Screen.CHATS) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = RoyalBlueDeep,
                                    selectedTextColor = GoldPrimary,
                                    indicatorColor = GoldPrimary,
                                    unselectedIconColor = GoldLight.copy(alpha = 0.6f),
                                    unselectedTextColor = GoldLight.copy(alpha = 0.6f)
                                ),
                                modifier = Modifier.testTag("nav_chats_tab")
                            )

                            NavigationBarItem(
                                selected = currentScreen == Screen.LIVE_ROOMS,
                                onClick = { viewModel.navigateTo(Screen.LIVE_ROOMS) },
                                icon = {
                                    Icon(Icons.Default.VolumeUp, contentDescription = "Live Rooms")
                                },
                                label = {
                                    Text(
                                        Translations.get("nav_live", language),
                                        fontSize = 10.sp,
                                        fontWeight = if (currentScreen == Screen.LIVE_ROOMS) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = RoyalBlueDeep,
                                    selectedTextColor = GoldPrimary,
                                    indicatorColor = GoldPrimary,
                                    unselectedIconColor = GoldLight.copy(alpha = 0.6f),
                                    unselectedTextColor = GoldLight.copy(alpha = 0.6f)
                                ),
                                modifier = Modifier.testTag("nav_live_tab")
                            )

                            NavigationBarItem(
                                selected = currentScreen == Screen.WALLET,
                                onClick = { viewModel.navigateTo(Screen.WALLET) },
                                icon = {
                                    Icon(Icons.Default.AccountBalanceWallet, contentDescription = "Wallet")
                                },
                                label = {
                                    Text(
                                        Translations.get("nav_wallet", language),
                                        fontSize = 10.sp,
                                        fontWeight = if (currentScreen == Screen.WALLET) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = RoyalBlueDeep,
                                    selectedTextColor = GoldPrimary,
                                    indicatorColor = GoldPrimary,
                                    unselectedIconColor = GoldLight.copy(alpha = 0.6f),
                                    unselectedTextColor = GoldLight.copy(alpha = 0.6f)
                                ),
                                modifier = Modifier.testTag("nav_wallet_tab")
                            )

                            if (currentUser.role == UserRole.TEACHER) {
                                NavigationBarItem(
                                    selected = currentScreen == Screen.TEACHER_PORTAL,
                                    onClick = { viewModel.navigateTo(Screen.TEACHER_PORTAL) },
                                    icon = {
                                        Icon(Icons.Default.School, contentDescription = "Teacher")
                                    },
                                    label = {
                                        Text(
                                            Translations.get("nav_teacher", language),
                                            fontSize = 10.sp,
                                            fontWeight = if (currentScreen == Screen.TEACHER_PORTAL) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = RoyalBlueDeep,
                                        selectedTextColor = GoldPrimary,
                                        indicatorColor = GoldPrimary,
                                        unselectedIconColor = GoldLight.copy(alpha = 0.6f),
                                        unselectedTextColor = GoldLight.copy(alpha = 0.6f)
                                    ),
                                    modifier = Modifier.testTag("nav_teacher_tab")
                                )
                            }

                            if (currentUser.role == UserRole.ADMIN || currentUser.role == UserRole.SUPERVISOR) {
                                NavigationBarItem(
                                    selected = currentScreen == Screen.ADMIN_PANEL,
                                    onClick = { viewModel.navigateTo(Screen.ADMIN_PANEL) },
                                    icon = {
                                        Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin")
                                    },
                                    label = {
                                        Text(
                                            Translations.get("nav_admin", language),
                                            fontSize = 10.sp,
                                            fontWeight = if (currentScreen == Screen.ADMIN_PANEL) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedIconColor = RoyalBlueDeep,
                                        selectedTextColor = GoldPrimary,
                                        indicatorColor = GoldPrimary,
                                        unselectedIconColor = GoldLight.copy(alpha = 0.6f),
                                        unselectedTextColor = GoldLight.copy(alpha = 0.6f)
                                    ),
                                    modifier = Modifier.testTag("nav_admin_tab")
                                )
                            }
                        }
                    }
                },
                floatingActionButton = {
                    if (currentScreen == Screen.CHATS) {
                        QuickActionsFAB(
                            onJoinLiveRoom = { viewModel.enterLiveRoom("room_hafs") },
                            onBuyPackage = { viewModel.navigateTo(Screen.WALLET) },
                            onVoiceRecitation = { viewModel.openConversation("conv_sheikh_ahmed") }
                        )
                    }
                }
            ) { padding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                ) {
                    when (currentScreen) {
                        Screen.CHATS -> ChatListScreen(viewModel = viewModel)
                        Screen.CHAT_DETAIL -> {
                            BackHandler { viewModel.navigateTo(Screen.CHATS) }
                            ChatDetailScreen(
                                viewModel = viewModel,
                                onBack = { viewModel.navigateTo(Screen.CHATS) }
                            )
                        }
                        Screen.LIVE_ROOMS -> LiveRoomsListScreen(viewModel = viewModel)
                        Screen.LIVE_ROOM_DETAIL -> {
                            BackHandler { viewModel.exitLiveRoom() }
                            LiveRoomDetailScreen(
                                viewModel = viewModel,
                                onBack = { viewModel.exitLiveRoom() }
                            )
                        }
                        Screen.WALLET -> WalletScreen(viewModel = viewModel)
                        Screen.TEACHER_PORTAL -> TeacherScreen(viewModel = viewModel)
                        Screen.ADMIN_PANEL -> AdminScreen(viewModel = viewModel)
                        Screen.SETTINGS -> WalletScreen(viewModel = viewModel)
                    }

                    // Toast message popup
                    toastMessage?.let { msg ->
                        Surface(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .padding(16.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .border(1.dp, GoldPrimary, RoundedCornerShape(12.dp)),
                            color = RoyalBlueElevated,
                            shadowElevation = 8.dp
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(8.dp)
                                        .clip(CircleShape)
                                        .background(EmeraldGreen)
                                )
                                Text(
                                    text = msg,
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
