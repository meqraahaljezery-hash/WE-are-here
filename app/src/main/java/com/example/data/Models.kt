package com.example.data

enum class UserRole {
    STUDENT,
    TEACHER,
    SUPERVISOR,
    ADMIN
}

data class User(
    val id: String,
    val name: String,
    val role: UserRole,
    val email: String,
    val phone: String,
    val minutesBalance: Int,
    val sanadTrack: String,
    val qiraahSpecialty: String,
    val avatarLetter: String,
    val avatarColor: Long
)

data class Story(
    val id: String,
    val authorName: String,
    val authorRole: String,
    val title: String,
    val description: String,
    val timeAgo: String,
    val isViewed: Boolean = false,
    val badge: String
)

enum class ChatType {
    PRIVATE,
    GROUP,
    ANNOUNCEMENT
}

data class Conversation(
    val id: String,
    val title: String,
    val type: ChatType,
    val lastMessage: String,
    val timestamp: String,
    val unreadCount: Int = 0,
    val isPinned: Boolean = false,
    val isLiveRoomActive: Boolean = false,
    val liveRoomId: String? = null,
    val avatarLetter: String,
    val avatarColor: Long,
    val participantsCount: Int = 0,
    val onlineStatus: String = "online"
)

data class TajweedFeedback(
    val ruleName: String,
    val colorHex: Long
)

data class Message(
    val id: String,
    val conversationId: String,
    val senderId: String,
    val senderName: String,
    val senderRole: UserRole,
    val content: String,
    val timestamp: String,
    val isAudioRecitation: Boolean = false,
    val audioDurationSec: Int = 0,
    val audioWaveform: List<Float> = emptyList(),
    val tajweedFeedback: TajweedFeedback? = null,
    val isFromMe: Boolean = false,
    val isRead: Boolean = true
)

data class QueueItem(
    val studentId: String,
    val studentName: String,
    val joinedTimestamp: String,
    val riwayah: String,
    val surahTarget: String,
    val position: Int
)

data class LiveRoom(
    val id: String,
    val titleKey: String,
    val riwayahName: String,
    val sheikhName: String,
    val sheikhRole: String,
    val activeCount: Int,
    val isLive: Boolean = true,
    val isSheikhMuted: Boolean = false,
    val isSheikhVideoOn: Boolean = true,
    val isMushafShared: Boolean = true,
    val activeReciterName: String? = null,
    val currentAyahArabic: String,
    val currentSurahRef: String,
    val waitingQueue: List<QueueItem> = emptyList()
)

data class Package(
    val id: String,
    val nameAr: String,
    val nameEn: String,
    val minutes: Int,
    val price: Double,
    val currency: String,
    val badge: String,
    val isPopular: Boolean = false,
    val descriptionAr: String,
    val descriptionEn: String
)

enum class PaymentStatus {
    PENDING,
    APPROVED,
    REJECTED
}

data class PaymentRequest(
    val id: String,
    val studentId: String,
    val studentName: String,
    val studentPhone: String,
    val packageName: String,
    val minutes: Int,
    val amount: Double,
    val currency: String,
    val transactionId: String,
    val paymentMethod: String,
    val submittedAt: String,
    val status: PaymentStatus = PaymentStatus.PENDING,
    val rejectionReason: String? = null
)

enum class LedgerType {
    PACKAGE_PURCHASE,
    RECITATION_SESSION,
    ADMIN_CREDIT,
    REFUND
}

data class MinuteLedgerEntry(
    val id: String,
    val studentId: String,
    val deltaMinutes: Int,
    val balanceAfter: Int,
    val type: LedgerType,
    val description: String,
    val timestamp: String
)

data class AuditLogEntry(
    val id: String,
    val actorName: String,
    val actorRole: UserRole,
    val action: String,
    val target: String,
    val timestamp: String,
    val details: String
)
