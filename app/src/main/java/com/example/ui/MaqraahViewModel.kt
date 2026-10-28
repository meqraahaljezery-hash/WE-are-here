package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.Conversation
import com.example.data.LiveRoom
import com.example.data.MaqraahRepository
import com.example.data.Message
import com.example.data.Package
import com.example.data.PaymentRequest
import com.example.data.QueueItem
import com.example.data.Story
import com.example.data.User
import com.example.data.UserRole
import com.example.i18n.AppLanguage
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

enum class Screen {
    CHATS,
    CHAT_DETAIL,
    LIVE_ROOMS,
    LIVE_ROOM_DETAIL,
    WALLET,
    TEACHER_PORTAL,
    ADMIN_PANEL,
    SETTINGS
}

class MaqraahViewModel(
    val repository: MaqraahRepository = MaqraahRepository()
) : ViewModel() {

    val currentLanguage: StateFlow<AppLanguage> = repository.currentLanguage
    val currentUser: StateFlow<User> = repository.currentUser
    val liveRooms: StateFlow<List<LiveRoom>> = repository.liveRooms
    val currentLiveRoom: StateFlow<LiveRoom?> = repository.currentLiveRoom
    val packages: StateFlow<List<Package>> = repository.packages
    val paymentRequests: StateFlow<List<PaymentRequest>> = repository.paymentRequests
    val minuteLedger = repository.minuteLedger
    val auditLogs = repository.auditLogs
    val stories: StateFlow<List<Story>> = repository.stories
    val isLiveSessionActive = repository.isLiveSessionActive
    val activeSessionSeconds = repository.activeSessionSeconds
    val activeSessionStudent = repository.activeSessionStudent

    private val _currentScreen = MutableStateFlow(Screen.CHATS)
    val currentScreen: StateFlow<Screen> = _currentScreen.asStateFlow()

    private val _activeConversationId = MutableStateFlow<String?>("conv_sheikh_ahmed")
    val activeConversationId: StateFlow<String?> = _activeConversationId.asStateFlow()

    private val _selectedChatTab = MutableStateFlow("tab_all")
    val selectedChatTab: StateFlow<String> = _selectedChatTab.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _toastMessage = MutableStateFlow<String?>(null)
    val toastMessage: StateFlow<String?> = _toastMessage.asStateFlow()

    private val _isRecordingAudio = MutableStateFlow(false)
    val isRecordingAudio: StateFlow<Boolean> = _isRecordingAudio.asStateFlow()

    private val _selectedStory = MutableStateFlow<Story?>(null)
    val selectedStory: StateFlow<Story?> = _selectedStory.asStateFlow()

    // Filtered conversations
    val filteredConversations: StateFlow<List<Conversation>> = combine(
        repository.conversations,
        _selectedChatTab,
        _searchQuery
    ) { convs, tab, query ->
        var list = when (tab) {
            "tab_private" -> convs.filter { it.type == com.example.data.ChatType.PRIVATE }
            "tab_groups" -> convs.filter { it.type == com.example.data.ChatType.GROUP }
            "tab_announcements" -> convs.filter { it.type == com.example.data.ChatType.ANNOUNCEMENT }
            "tab_unread" -> convs.filter { it.unreadCount > 0 }
            else -> convs
        }
        if (query.isNotBlank()) {
            list = list.filter {
                it.title.contains(query, ignoreCase = true) ||
                it.lastMessage.contains(query, ignoreCase = true)
            }
        }
        list
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val activeMessages: StateFlow<List<Message>> = combine(
        repository.messages,
        _activeConversationId
    ) { msgs, convId ->
        if (convId == null) emptyList() else msgs.filter { it.conversationId == convId }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        // Run background ticker when a session is active
        viewModelScope.launch {
            while (isActive) {
                delay(1000)
                if (repository.isLiveSessionActive.value) {
                    repository.tickSessionSecond()
                }
            }
        }
    }

    fun navigateTo(screen: Screen) {
        _currentScreen.value = screen
    }

    fun openConversation(convId: String) {
        _activeConversationId.value = convId
        _currentScreen.value = Screen.CHAT_DETAIL
    }

    fun setChatTab(tab: String) {
        _selectedChatTab.value = tab
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun switchLanguage(lang: AppLanguage) {
        repository.setLanguage(lang)
        showToast("تم تغيير اللغة بنجاح / Language updated")
    }

    fun switchRole(role: UserRole) {
        repository.switchRole(role)
        showToast("تم التبديل إلى منظور: ${role.name}")
        when (role) {
            UserRole.TEACHER -> _currentScreen.value = Screen.TEACHER_PORTAL
            UserRole.ADMIN -> _currentScreen.value = Screen.ADMIN_PANEL
            else -> _currentScreen.value = Screen.CHATS
        }
    }

    fun sendMessage(text: String, isAudio: Boolean = false, tajweedTag: String? = null) {
        val convId = _activeConversationId.value ?: "conv_sheikh_ahmed"
        repository.sendMessage(convId, text, isAudio, tajweedTag)
    }

    fun toggleAudioRecording() {
        val next = !_isRecordingAudio.value
        _isRecordingAudio.value = next
        if (!next) {
            // Finished recording, send as audio recitation
            sendMessage("🎤 تلاوة مرتلة (عرض على الشيخ)", isAudio = true, tajweedTag = "مد متصل ٤ حركات")
            showToast("تم إرسال التلاوة الصوتية بنجاح")
        }
    }

    fun enterLiveRoom(roomId: String) {
        repository.joinLiveRoom(roomId)
        _currentScreen.value = Screen.LIVE_ROOM_DETAIL
    }

    fun exitLiveRoom() {
        repository.leaveLiveRoom()
        _currentScreen.value = Screen.LIVE_ROOMS
    }

    fun toggleRaiseHand(roomId: String) {
        repository.toggleRaiseHand(roomId)
        showToast("تم تحديث حالة دورك في قائمة الانتظار")
    }

    fun teacherStartSession(studentQueueItem: QueueItem) {
        repository.startRecitationSession(studentQueueItem)
        showToast("بدأت جلسة الطالب: ${studentQueueItem.studentName} وجارٍ احتساب الدقائق")
    }

    fun teacherEndSession(feedback: String, grade: String) {
        repository.endRecitationSession(feedback, grade)
        showToast("تم إنهاء الجلسة وحسم الدقائق وتوثيق التقييم")
    }

    fun submitManualPayment(
        packageName: String,
        minutes: Int,
        amount: Double,
        senderName: String,
        transactionId: String,
        phone: String,
        method: String
    ) {
        repository.submitManualPayment(packageName, minutes, amount, senderName, transactionId, phone, method)
        showToast("تم إرسال طلب تفعيل الباقة بنجاح، جاري مراجعة الإدارة")
    }

    fun adminApprovePayment(requestId: String) {
        repository.adminApprovePayment(requestId)
        showToast("تم اعتماد الحوالة وشحن رصيد الطالب فوراً")
    }

    fun adminRejectPayment(requestId: String, reason: String) {
        repository.adminRejectPayment(requestId, reason)
        showToast("تم رفض الطلب وإشعار الطالب")
    }

    fun broadcastAnnouncement(title: String, text: String) {
        repository.broadcastAnnouncement(title, text)
        showToast("تم نشر الإعلان العام في كافة الحلقات")
    }

    fun selectStory(story: Story?) {
        _selectedStory.value = story
    }

    fun showToast(msg: String) {
        _toastMessage.value = msg
    }

    fun clearToast() {
        _toastMessage.value = null
    }
}
