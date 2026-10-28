package com.example.data

import com.example.i18n.AppLanguage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MaqraahRepository {
    private val _currentLanguage = MutableStateFlow(AppLanguage.ARABIC)
    val currentLanguage: StateFlow<AppLanguage> = _currentLanguage.asStateFlow()

    private val _currentUser = MutableStateFlow(SampleData.sampleUsers[0])
    val currentUser: StateFlow<User> = _currentUser.asStateFlow()

    private val _conversations = MutableStateFlow(SampleData.sampleConversations)
    val conversations: StateFlow<List<Conversation>> = _conversations.asStateFlow()

    private val _messages = MutableStateFlow(SampleData.sampleMessagesSheikhAhmed)
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _liveRooms = MutableStateFlow(SampleData.sampleLiveRooms)
    val liveRooms: StateFlow<List<LiveRoom>> = _liveRooms.asStateFlow()

    private val _currentLiveRoom = MutableStateFlow<LiveRoom?>(null)
    val currentLiveRoom: StateFlow<LiveRoom?> = _currentLiveRoom.asStateFlow()

    private val _packages = MutableStateFlow(SampleData.samplePackages)
    val packages: StateFlow<List<Package>> = _packages.asStateFlow()

    private val _paymentRequests = MutableStateFlow(SampleData.samplePaymentRequests.toList())
    val paymentRequests: StateFlow<List<PaymentRequest>> = _paymentRequests.asStateFlow()

    private val _minuteLedger = MutableStateFlow(SampleData.sampleLedger.toList())
    val minuteLedger: StateFlow<List<MinuteLedgerEntry>> = _minuteLedger.asStateFlow()

    private val _auditLogs = MutableStateFlow(SampleData.sampleAuditLogs.toList())
    val auditLogs: StateFlow<List<AuditLogEntry>> = _auditLogs.asStateFlow()

    private val _stories = MutableStateFlow(SampleData.sampleStories)
    val stories: StateFlow<List<Story>> = _stories.asStateFlow()

    // Active session tracking for Teacher / Student Live Room
    private val _isLiveSessionActive = MutableStateFlow(false)
    val isLiveSessionActive: StateFlow<Boolean> = _isLiveSessionActive.asStateFlow()

    private val _activeSessionSeconds = MutableStateFlow(0)
    val activeSessionSeconds: StateFlow<Int> = _activeSessionSeconds.asStateFlow()

    private val _activeSessionStudent = MutableStateFlow<QueueItem?>(null)
    val activeSessionStudent: StateFlow<QueueItem?> = _activeSessionStudent.asStateFlow()

    fun setLanguage(lang: AppLanguage) {
        _currentLanguage.value = lang
    }

    fun switchRole(role: UserRole) {
        val targetUser = SampleData.sampleUsers.find { it.role == role }
            ?: SampleData.sampleUsers[0]
        _currentUser.value = targetUser
    }

    fun sendMessage(conversationId: String, content: String, isAudio: Boolean = false, tajweedTag: String? = null) {
        val user = _currentUser.value
        val timeNow = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val newMessage = Message(
            id = "msg_${System.currentTimeMillis()}",
            conversationId = conversationId,
            senderId = user.id,
            senderName = user.name,
            senderRole = user.role,
            content = content,
            timestamp = timeNow,
            isAudioRecitation = isAudio,
            audioDurationSec = if (isAudio) (20..90).random() else 0,
            audioWaveform = if (isAudio) listOf(0.3f, 0.6f, 0.9f, 0.7f, 0.4f, 0.8f, 0.5f, 0.9f, 0.6f, 0.3f, 0.7f) else emptyList(),
            tajweedFeedback = if (!tajweedTag.isNullOrBlank()) TajweedFeedback(tajweedTag, 0xFFD4AF37) else null,
            isFromMe = true,
            isRead = true
        )
        _messages.value = _messages.value + newMessage

        // Update conversation last message
        _conversations.value = _conversations.value.map { conv ->
            if (conv.id == conversationId) {
                conv.copy(
                    lastMessage = if (isAudio) "🎤 تلاوة صوتية" else content,
                    timestamp = timeNow
                )
            } else conv
        }
    }

    fun joinLiveRoom(roomId: String) {
        val room = _liveRooms.value.find { it.id == roomId } ?: _liveRooms.value.first()
        _currentLiveRoom.value = room
    }

    fun leaveLiveRoom() {
        _currentLiveRoom.value = null
        _isLiveSessionActive.value = false
        _activeSessionSeconds.value = 0
        _activeSessionStudent.value = null
    }

    fun toggleRaiseHand(roomId: String) {
        val current = _currentLiveRoom.value ?: return
        val user = _currentUser.value
        val isInQueue = current.waitingQueue.any { it.studentId == user.id }

        val newQueue = if (isInQueue) {
            current.waitingQueue.filter { it.studentId != user.id }
        } else {
            val item = QueueItem(
                studentId = user.id,
                studentName = user.name,
                joinedTimestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date()),
                riwayah = current.riwayahName,
                surahTarget = "عرض الوجه المقرر",
                position = current.waitingQueue.size + 1
            )
            current.waitingQueue + item
        }

        val updatedRoom = current.copy(waitingQueue = newQueue)
        _currentLiveRoom.value = updatedRoom
        _liveRooms.value = _liveRooms.value.map { if (it.id == roomId) updatedRoom else it }
    }

    fun toggleSheikhMic() {
        val room = _currentLiveRoom.value ?: return
        val updated = room.copy(isSheikhMuted = !room.isSheikhMuted)
        _currentLiveRoom.value = updated
        _liveRooms.value = _liveRooms.value.map { if (it.id == room.id) updated else it }
    }

    fun toggleMushafShare() {
        val room = _currentLiveRoom.value ?: return
        val updated = room.copy(isMushafShared = !room.isMushafShared)
        _currentLiveRoom.value = updated
        _liveRooms.value = _liveRooms.value.map { if (it.id == room.id) updated else it }
    }

    fun startRecitationSession(queueItem: QueueItem) {
        val room = _currentLiveRoom.value ?: return
        val updatedQueue = room.waitingQueue.filter { it.studentId != queueItem.studentId }
        val updatedRoom = room.copy(
            activeReciterName = queueItem.studentName,
            waitingQueue = updatedQueue
        )
        _currentLiveRoom.value = updatedRoom
        _liveRooms.value = _liveRooms.value.map { if (it.id == room.id) updatedRoom else it }
        _activeSessionStudent.value = queueItem
        _isLiveSessionActive.value = true
        _activeSessionSeconds.value = 0
    }

    fun tickSessionSecond() {
        if (_isLiveSessionActive.value) {
            _activeSessionSeconds.value += 1
        }
    }

    fun endRecitationSession(feedbackNotes: String, tajweedGrade: String) {
        val room = _currentLiveRoom.value ?: return
        val student = _activeSessionStudent.value
        val elapsedMinutes = maxOf(1, (_activeSessionSeconds.value + 59) / 60)

        // Deduct minutes if it matches current student or simulated student
        val user = _currentUser.value
        val isCurrentStudent = student?.studentId == user.id || user.role == UserRole.STUDENT

        if (isCurrentStudent) {
            val newBalance = maxOf(0, user.minutesBalance - elapsedMinutes)
            _currentUser.value = user.copy(minutesBalance = newBalance)

            // Add ledger entry
            val ledgerEntry = MinuteLedgerEntry(
                id = "led_${System.currentTimeMillis()}",
                studentId = user.id,
                deltaMinutes = -elapsedMinutes,
                balanceAfter = newBalance,
                type = LedgerType.RECITATION_SESSION,
                description = "جلسة إقراء في ${room.riwayahName} - تقييم: $tajweedGrade ($feedbackNotes)",
                timestamp = SimpleDateFormat("dd MMMM yyyy - hh:mm a", Locale.getDefault()).format(Date())
            )
            _minuteLedger.value = listOf(ledgerEntry) + _minuteLedger.value
        }

        // Add audit log
        val auditEntry = AuditLogEntry(
            id = "aud_${System.currentTimeMillis()}",
            actorName = room.sheikhName,
            actorRole = UserRole.TEACHER,
            action = "إنهاء جلسة إقراء وخصم $elapsedMinutes دقيقة",
            target = student?.studentName ?: "طالب مقرأة",
            timestamp = SimpleDateFormat("dd MMMM yyyy - hh:mm a", Locale.getDefault()).format(Date()),
            details = "التقييم: $tajweedGrade. الملاحظة: $feedbackNotes"
        )
        _auditLogs.value = listOf(auditEntry) + _auditLogs.value

        val updatedRoom = room.copy(activeReciterName = null)
        _currentLiveRoom.value = updatedRoom
        _liveRooms.value = _liveRooms.value.map { if (it.id == room.id) updatedRoom else it }
        _isLiveSessionActive.value = false
        _activeSessionSeconds.value = 0
        _activeSessionStudent.value = null
    }

    fun submitManualPayment(
        packageName: String,
        minutes: Int,
        amount: Double,
        senderName: String,
        transactionId: String,
        phone: String,
        method: String
    ): Boolean {
        val user = _currentUser.value
        val newReq = PaymentRequest(
            id = "pay_${System.currentTimeMillis()}",
            studentId = user.id,
            studentName = if (senderName.isNotBlank()) senderName else user.name,
            studentPhone = if (phone.isNotBlank()) phone else user.phone,
            packageName = packageName,
            minutes = minutes,
            amount = amount,
            currency = "USD",
            transactionId = transactionId,
            paymentMethod = method,
            submittedAt = SimpleDateFormat("dd MMM hh:mm a", Locale.getDefault()).format(Date()),
            status = PaymentStatus.PENDING
        )
        _paymentRequests.value = listOf(newReq) + _paymentRequests.value
        return true
    }

    fun adminApprovePayment(requestId: String) {
        val target = _paymentRequests.value.find { it.id == requestId } ?: return
        val updated = _paymentRequests.value.map {
            if (it.id == requestId) it.copy(status = PaymentStatus.APPROVED) else it
        }
        _paymentRequests.value = updated

        // Credit student balance
        val user = _currentUser.value
        if (user.id == target.studentId || user.role == UserRole.STUDENT) {
            val newBal = user.minutesBalance + target.minutes
            _currentUser.value = user.copy(minutesBalance = newBal)

            val ledgerEntry = MinuteLedgerEntry(
                id = "led_${System.currentTimeMillis()}",
                studentId = target.studentId,
                deltaMinutes = target.minutes,
                balanceAfter = newBal,
                type = LedgerType.PACKAGE_PURCHASE,
                description = "شحن ${target.packageName} عبر ${target.paymentMethod} (معاملة: ${target.transactionId})",
                timestamp = SimpleDateFormat("dd MMMM yyyy - hh:mm a", Locale.getDefault()).format(Date())
            )
            _minuteLedger.value = listOf(ledgerEntry) + _minuteLedger.value
        }

        // Add audit log
        val auditEntry = AuditLogEntry(
            id = "aud_${System.currentTimeMillis()}",
            actorName = "إدارة المقرأة المركزية",
            actorRole = UserRole.ADMIN,
            action = "اعتماد حوالة وشحن ${target.minutes} دقيقة",
            target = "${target.studentName} (${target.transactionId})",
            timestamp = SimpleDateFormat("dd MMM yyyy - hh:mm a", Locale.getDefault()).format(Date()),
            details = "تم تأكيد الاستلام على حساب ${target.paymentMethod} بمبلغ ${target.amount} ${target.currency}"
        )
        _auditLogs.value = listOf(auditEntry) + _auditLogs.value
    }

    fun adminRejectPayment(requestId: String, reason: String) {
        val target = _paymentRequests.value.find { it.id == requestId } ?: return
        val updated = _paymentRequests.value.map {
            if (it.id == requestId) it.copy(status = PaymentStatus.REJECTED, rejectionReason = reason) else it
        }
        _paymentRequests.value = updated

        val auditEntry = AuditLogEntry(
            id = "aud_${System.currentTimeMillis()}",
            actorName = "إدارة المقرأة المركزية",
            actorRole = UserRole.ADMIN,
            action = "رفض طلب دفع يدوي",
            target = "${target.studentName} (${target.transactionId})",
            timestamp = SimpleDateFormat("dd MMM yyyy - hh:mm a", Locale.getDefault()).format(Date()),
            details = "السبب: $reason"
        )
        _auditLogs.value = listOf(auditEntry) + _auditLogs.value
    }

    fun broadcastAnnouncement(title: String, text: String) {
        val timeNow = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
        val msg = Message(
            id = "msg_${System.currentTimeMillis()}",
            conversationId = "conv_announcements",
            senderId = "user_admin_1",
            senderName = "إدارة مقرأة الجزري",
            senderRole = UserRole.ADMIN,
            content = "📢 [$title]\n$text",
            timestamp = timeNow,
            isFromMe = false,
            isRead = true
        )
        _messages.value = _messages.value + msg

        _conversations.value = _conversations.value.map {
            if (it.id == "conv_announcements") {
                it.copy(lastMessage = "📢 $title: $text", timestamp = timeNow)
            } else it
        }

        val auditEntry = AuditLogEntry(
            id = "aud_${System.currentTimeMillis()}",
            actorName = "إدارة مقرأة الجزري",
            actorRole = UserRole.ADMIN,
            action = "نشر إعلان عام",
            target = "جميع الحلقات والطلاب",
            timestamp = SimpleDateFormat("dd MMM yyyy - hh:mm a", Locale.getDefault()).format(Date()),
            details = title
        )
        _auditLogs.value = listOf(auditEntry) + _auditLogs.value
    }
}
