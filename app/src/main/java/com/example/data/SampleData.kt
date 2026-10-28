package com.example.data

object SampleData {
    val sampleUsers = listOf(
        User(
            id = "user_student_1",
            name = "عبد الرحمن الإثيوبي",
            role = UserRole.STUDENT,
            email = "abdurrahman@aljazari.org",
            phone = "+251 91 123 4567",
            minutesBalance = 85,
            sanadTrack = "إجازة حفص عن عاصم",
            qiraahSpecialty = "حفص وورش",
            avatarLetter = "ع",
            avatarColor = 0xFFD4AF37
        ),
        User(
            id = "user_teacher_1",
            name = "فضيلة الشيخ د. أحمد الجزري",
            role = UserRole.TEACHER,
            email = "ahmed.jazari@aljazari.org",
            phone = "+966 50 987 6543",
            minutesBalance = 9999,
            sanadTrack = "مسند بالقراءات العشر الكبرى والصغرى",
            qiraahSpecialty = "القراءات العشر ومجلس الإقراء",
            avatarLetter = "أ",
            avatarColor = 0xFF10B981
        ),
        User(
            id = "user_sup_1",
            name = "الشيخ عبد الله المشرف",
            role = UserRole.SUPERVISOR,
            email = "abdullah.sup@aljazari.org",
            phone = "+251 92 345 6789",
            minutesBalance = 9999,
            sanadTrack = "مشرف شؤون الطلاب والحلقات",
            qiraahSpecialty = "التوجيه القرآني والرقابة",
            avatarLetter = "م",
            avatarColor = 0xFF0EA5E9
        ),
        User(
            id = "user_admin_1",
            name = "إدارة مقرأة الجزري المركزية",
            role = UserRole.ADMIN,
            email = "admin@aljazari.org",
            phone = "+966 11 456 7890",
            minutesBalance = 99999,
            sanadTrack = "الإدارة العامة والاعتمادات",
            qiraahSpecialty = "الإشراف الإداري والمالي",
            avatarLetter = "د",
            avatarColor = 0xFFF59E0B
        )
    )

    val sampleStories = listOf(
        Story(
            id = "story_1",
            authorName = "الشيخ د. أحمد الجزري",
            authorRole = "شيخ المقرأة",
            title = "بشرى منح إجازة بالسند",
            description = "مبارك للطالب بلال الإثيوبي إتمام ختمة القرآن الكريم برواية حفص عن عاصم وحصوله على الإجازة بالسند المتصل إلى النبي صلى الله عليه وسلم.",
            timeAgo = "منذ ساعة",
            badge = "إجازة معتمدة"
        ),
        Story(
            id = "story_2",
            authorName = "مقرأة رواية ورش",
            authorRole = "حلقة الأزرق",
            title = "انطلاق مقرأة ورش المباشرة",
            description = "المقرأة المباشرة مفتوحة الآن للطلاب الراغبين في العرض على فضيلة الشيخ إدريس المغربي في سورة النساء.",
            timeAgo = "مباشر الآن",
            badge = "بث مباشر"
        ),
        Story(
            id = "story_3",
            authorName = "مجلس المتون العلمية",
            authorRole = "شرح الجزرية",
            title = "مجلس الليلة في باب مخارج الحروف",
            description = "نلتقي بإذن الله بعد صلاة العشاء لشرح أبيات مخارج الحروف من منظومة المقدمة الجزرية مع التطبيق العملي.",
            timeAgo = "منذ ساعتين",
            badge = "درس علمي"
        ),
        Story(
            id = "story_4",
            authorName = "الإدارة المركزية",
            authorRole = "الشؤون الطلابية",
            title = "خصم باقة الختمة الذهبية",
            description = "تخفيض 25% على باقة 600 دقيقة لطلاب مسار الإجازة بالسند بمناسبة الختمة الشهرية.",
            timeAgo = "منذ 5 ساعات",
            badge = "عرض خاص"
        )
    )

    val sampleConversations = listOf(
        Conversation(
            id = "conv_sheikh_ahmed",
            title = "فضيلة الشيخ د. أحمد الجزري",
            type = ChatType.PRIVATE,
            lastMessage = "أحسنت يا بني، تلاوتك ممتازة مع ضرورة الانتباه لمد الصلة الكبرى في موضع سورة البقرة.",
            timestamp = "10:45 ص",
            unreadCount = 1,
            isPinned = true,
            avatarLetter = "أ",
            avatarColor = 0xFF10B981,
            onlineStatus = "teaching"
        ),
        Conversation(
            id = "conv_group_hafs",
            title = "حلقة الإجازة بالسند العالي (حفص)",
            type = ChatType.GROUP,
            lastMessage = "المقرأة الصوتية قائمة الآن، الرادود الحالي: عمر الفاروق.",
            timestamp = "10:52 ص",
            unreadCount = 3,
            isPinned = true,
            isLiveRoomActive = true,
            liveRoomId = "room_hafs",
            avatarLetter = "ح",
            avatarColor = 0xFFD4AF37,
            participantsCount = 148
        ),
        Conversation(
            id = "conv_group_warsh",
            title = "مقرأة رواية ورش عن نافع (الأزرق)",
            type = ChatType.GROUP,
            lastMessage = "فضيلة الشيخ إدريس يبدأ إقراء الحزب السادس بعد قليل.",
            timestamp = "09:30 ص",
            unreadCount = 0,
            isPinned = false,
            isLiveRoomActive = true,
            liveRoomId = "room_warsh",
            avatarLetter = "و",
            avatarColor = 0xFF0EA5E9,
            participantsCount = 96
        ),
        Conversation(
            id = "conv_announcements",
            title = "إعلانات المقرأة ومواعيد المجالس",
            type = ChatType.ANNOUNCEMENT,
            lastMessage = "جدول اختبارات الإجازة ونهاية الفصل الدراسي للربع الأخير.",
            timestamp = "أمس",
            unreadCount = 0,
            isPinned = false,
            avatarLetter = "م",
            avatarColor = 0xFFF59E0B,
            participantsCount = 1250
        ),
        Conversation(
            id = "conv_supervisor",
            title = "الشيخ عبد الله المشرف التربوي",
            type = ChatType.PRIVATE,
            lastMessage = "تم تدقيق كشف حضورك الأسبوعي وساعود بملاحظات لجنة الإشراف.",
            timestamp = "أمس",
            unreadCount = 0,
            isPinned = false,
            avatarLetter = "ع",
            avatarColor = 0xFF8B5CF6,
            onlineStatus = "online"
        ),
        Conversation(
            id = "conv_group_matn",
            title = "مدارسة منظومة المقدمة الجزرية",
            type = ChatType.GROUP,
            lastMessage = "يقول ابن الجزري: إِذْ وَاجِبٌ عَلَيْهِمُ مُحَتَّمُ * قَبْلَ الشُّرُوعِ أَوَّلاً أَن يَعْلَمُوا...",
            timestamp = "24 أكتوبر",
            unreadCount = 0,
            isPinned = false,
            avatarLetter = "ج",
            avatarColor = 0xFFEC4899,
            participantsCount = 210
        )
    )

    val sampleMessagesSheikhAhmed = listOf(
        Message(
            id = "msg_1",
            conversationId = "conv_sheikh_ahmed",
            senderId = "user_student_1",
            senderName = "عبد الرحمن الإثيوبي",
            senderRole = UserRole.STUDENT,
            content = "السلام عليكم ورحمة الله وبركاته يا فضيلة الشيخ، أرسل لفضيلتكم مقطع تلاوتي للأوجه الأولى من سورة الفاتحة وأول البقرة للعرض والتدقيق.",
            timestamp = "10:15 ص",
            isFromMe = true,
            isRead = true
        ),
        Message(
            id = "msg_2",
            conversationId = "conv_sheikh_ahmed",
            senderId = "user_student_1",
            senderName = "عبد الرحمن الإثيوبي",
            senderRole = UserRole.STUDENT,
            content = "تلاوة صوتية: سورة الفاتحة (برواية حفص عن عاصم)",
            timestamp = "10:16 ص",
            isAudioRecitation = true,
            audioDurationSec = 68,
            audioWaveform = listOf(0.2f, 0.4f, 0.7f, 0.9f, 0.6f, 0.8f, 0.3f, 0.5f, 0.9f, 0.7f, 0.4f, 0.8f, 0.6f, 0.3f, 0.5f, 0.8f),
            isFromMe = true,
            isRead = true
        ),
        Message(
            id = "msg_3",
            conversationId = "conv_sheikh_ahmed",
            senderId = "user_teacher_1",
            senderName = "فضيلة الشيخ د. أحمد الجزري",
            senderRole = UserRole.TEACHER,
            content = "وعليكم السلام ورحمة الله وبركاته يا عبد الرحمن، بارك الله فيك ونفع بك. استمعت لتلاوتك المباركة.",
            timestamp = "10:25 ص",
            isFromMe = false,
            isRead = true
        ),
        Message(
            id = "msg_4",
            conversationId = "conv_sheikh_ahmed",
            senderId = "user_teacher_1",
            senderName = "فضيلة الشيخ د. أحمد الجزري",
            senderRole = UserRole.TEACHER,
            content = "توجيه صوتي من الشيخ: تصويب مخرج الصاد وترقيق الراء في (الرَّحْمَٰنِ)",
            timestamp = "10:28 ص",
            isAudioRecitation = true,
            audioDurationSec = 45,
            audioWaveform = listOf(0.4f, 0.6f, 0.8f, 1.0f, 0.7f, 0.5f, 0.9f, 0.8f, 0.6f, 0.7f, 0.5f, 0.8f, 0.9f, 0.4f, 0.3f, 0.2f),
            tajweedFeedback = TajweedFeedback(
                ruleName = "مد متصل ٤ حركات + ترقيق راء",
                colorHex = 0xFFD4AF37
            ),
            isFromMe = false,
            isRead = true
        ),
        Message(
            id = "msg_5",
            conversationId = "conv_sheikh_ahmed",
            senderId = "user_teacher_1",
            senderName = "فضيلة الشيخ د. أحمد الجزري",
            senderRole = UserRole.TEACHER,
            content = "أحسنت يا بني، تلاوتك ممتازة، وموعدنا القادم في مقرأة الإجازة الصوتية المباشرة لعرض الربع الثاني بالسند.",
            timestamp = "10:45 ص",
            isFromMe = false,
            isRead = true
        )
    )

    val sampleLiveRooms = listOf(
        LiveRoom(
            id = "room_hafs",
            titleKey = "room_hafs",
            riwayahName = "حفص عن عاصم من طريق الشاطبية",
            sheikhName = "فضيلة الشيخ د. أحمد الجزري",
            sheikhRole = "المقرئ المسند بالعشر الكبرى",
            activeCount = 38,
            isLive = true,
            activeReciterName = "عمر الفاروق الإثيوبي",
            currentAyahArabic = "الْحَمْدُ لِلَّهِ الَّذِي أَنزَلَ عَلَىٰ عَبْدِهِ الْكِتَابَ وَلَمْ يَجْعَل لَّهُ عِوَجًا * قَيِّمًا لِّيُنذِرَ بَأْسًا شَدِيدًا مِّن لَّدُنْهُ وَيُبَشِّرَ الْمُؤْمِنِينَ الَّذِينَ يَعْمَلُونَ الصَّالِحَاتِ أَنَّ لَهُمْ أَجْرًا حَسَنًا",
            currentSurahRef = "سورة الكهف - الآيات ١-٢",
            waitingQueue = listOf(
                QueueItem("q_1", "عمر الفاروق", "10:30 ص", "حفص", "سورة الكهف", 1),
                QueueItem("q_2", "عبد الرحمن الإثيوبي", "10:35 ص", "حفص", "سورة مريم", 2),
                QueueItem("q_3", "مصطفى نور الدين", "10:40 ص", "حفص", "سورة طه", 3),
                QueueItem("q_4", "حمزة بلال", "10:45 ص", "حفص", "سورة الأنبياء", 4)
            )
        ),
        LiveRoom(
            id = "room_warsh",
            titleKey = "room_warsh",
            riwayahName = "ورش عن نافع من طريق الأزرق",
            sheikhName = "فضيلة الشيخ إدريس المغربي",
            sheikhRole = "مقرئ القراءات السبع",
            activeCount = 24,
            isLive = true,
            activeReciterName = "زكريا بن عبد الله",
            currentAyahArabic = "يَا أَيُّهَا النَّاسُ اتَّقُوا رَبَّكُمُ الَّذِي خَلَقَكُم مِّن نَّفْسٍ وَاحِدَةٍ وَخَلَقَ مِنْهَا زَوْجَهَا وَبَثَّ مِنْهُمَا رِجَالًا كَثِيرًا وَنِسَاءً",
            currentSurahRef = "سورة النساء - الآية ١",
            waitingQueue = listOf(
                QueueItem("qw_1", "زكريا بن عبد الله", "10:20 ص", "ورش", "سورة النساء", 1),
                QueueItem("qw_2", "ياسين رضوان", "10:38 ص", "ورش", "سورة المائدة", 2)
            )
        ),
        LiveRoom(
            id = "room_qalun",
            titleKey = "room_qalun",
            riwayahName = "قالون عن نافع المدني",
            sheikhName = "فضيلة الشيخ محمد المدني",
            sheikhRole = "مقرئ الحرم النبوي الشريف",
            activeCount = 18,
            isLive = true,
            activeReciterName = "خالد المحمدي",
            currentAyahArabic = "إِنَّ الَّذِينَ كَفَرُوا سَوَاءٌ عَلَيْهِمْ أَأَنذَرْتَهُمْ أَمْ لَمْ تُنذِرْهُمْ لَا يُؤْمِنُونَ",
            currentSurahRef = "سورة البقرة - الآية ٦",
            waitingQueue = listOf(
                QueueItem("qq_1", "خالد المحمدي", "10:15 ص", "قالون", "سورة البقرة", 1)
            )
        ),
        LiveRoom(
            id = "room_matn",
            titleKey = "room_matn",
            riwayahName = "المقدمة الجزرية وتحفة الأطفال",
            sheikhName = "فضيلة الشيخ إبراهيم القارئ",
            sheikhRole = "محقق ومسند في علوم التجويد",
            activeCount = 45,
            isLive = true,
            activeReciterName = "صالح العدني",
            currentAyahArabic = "مَخَارِجُ الْحُرُوفِ سَبْعَةَ عَشَرْ * عَلَى الَّذِي يَخْتَارُهُ مَنِ اخْتَبَرْ * فَأَلِفُ الْجَوْفِ وَأُخْتَاهَا وَهِي * حُرُوفُ مَدٍّ لِلْهَوَاءِ تَنْتَهِي",
            currentSurahRef = "منظومة الجزرية - باب مخارج الحروف",
            waitingQueue = listOf(
                QueueItem("qm_1", "صالح العدني", "10:10 ص", "الجزرية", "باب المخارج", 1),
                QueueItem("qm_2", "أنس الحبشي", "10:25 ص", "الجزرية", "باب الصفات", 2)
            )
        ),
        LiveRoom(
            id = "room_talqin",
            titleKey = "room_talqin",
            riwayahName = "التلقين والتصحيح المباشر",
            sheikhName = "فضيلة الشيخ طارق الأثري",
            sheikhRole = "معلم القراءات والتصحيح الصوتي",
            activeCount = 20,
            isLive = true,
            activeReciterName = "سفيان داود",
            currentAyahArabic = "قُلْ هُوَ اللَّهُ أَحَدٌ * اللَّهُ الصَّمَدُ * لَمْ يَلِدْ وَلَمْ يُولَدْ * وَلَمْ يَكُن لَّهُ كُفُوًا أَحَدٌ",
            currentSurahRef = "سورة الإخلاص",
            waitingQueue = emptyList()
        ),
        LiveRoom(
            id = "room_sanad",
            titleKey = "room_sanad",
            riwayahName = "مجلس الإجازة وعرض الختمات",
            sheikhName = "فضيلة الشيخ أ.د. حسن الدمشقي",
            sheikhRole = "رئيس هيئة الإقراء والإجازات",
            activeCount = 14,
            isLive = true,
            activeReciterName = "سليمان المهدي",
            currentAyahArabic = "وَتَمَّتْ كَلِمَتُ رَبِّكَ صِدْقًا وَعَدْلًا لَّا مُبَدِّلَ لِكَلِمَاتِهِ وَهُوَ السَّمِيعُ الْعَلِيمُ",
            currentSurahRef = "سورة الأنعام - الآية ١١٥",
            waitingQueue = listOf(
                QueueItem("qs_1", "سليمان المهدي", "10:00 ص", "حفص بالسند", "ختمة كاملة", 1)
            )
        )
    )

    val samplePackages = listOf(
        Package(
            id = "pkg_trial",
            nameAr = "باقة التقييم والتجربة",
            nameEn = "Trial & Evaluation Package",
            minutes = 60,
            price = 15.0,
            currency = "USD",
            badge = "للمبتدئين",
            isPopular = false,
            descriptionAr = "٦٠ دقيقة إقراء فردي لتقييم مخارج الحروف وتحديد المستوى ومسار السند المناسب.",
            descriptionEn = "60 minutes 1-on-1 recitation to assess articulation and plan your Ijazah path."
        ),
        Package(
            id = "pkg_itqan",
            nameAr = "باقة الإتقان والتلقين",
            nameEn = "Itqan & Recitation Mastery",
            minutes = 300,
            price = 65.0,
            currency = "USD",
            badge = "الأكثر طلباً",
            isPopular = true,
            descriptionAr = "٣٠٠ دقيقة للعرض المباشر على المشايخ المسندين وتصحيح التلاوة وتطبيق أحكام التجويد الدقيقة.",
            descriptionEn = "300 minutes with certified Sheikhs to perfect recitation and Tajweed application."
        ),
        Package(
            id = "pkg_khatmah",
            nameAr = "باقة الختمة الكاملة",
            nameEn = "Full Khatmah Track",
            minutes = 600,
            price = 120.0,
            currency = "USD",
            badge = "خصم ٢٠٪",
            isPopular = false,
            descriptionAr = "٦٠٠ دقيقة لإتمام عرض أجزاء من الختمة الشريفة ومتابعة دورية مع تقارير التجويد الصوتية.",
            descriptionEn = "600 minutes dedicated to completing Quranic Juz' recitation with periodic progress reports."
        ),
        Package(
            id = "pkg_sanad",
            nameAr = "باقة الإجازة بالسند العالي",
            nameEn = "Ijazah with Connected Sanad",
            minutes = 1200,
            price = 220.0,
            currency = "USD",
            badge = "شهادة بالسند",
            isPopular = false,
            descriptionAr = "١٢٠٠ دقيقة للختمة الكاملة غيباً أو نظراً مع منح السند المتصل لرسول الله صلى الله عليه وسلم.",
            descriptionEn = "1200 minutes for full recitation to qualify for Ijazah with verified Sanad chain."
        )
    )

    val samplePaymentRequests = mutableListOf(
        PaymentRequest(
            id = "pay_req_101",
            studentId = "user_student_1",
            studentName = "عبد الرحمن الإثيوبي",
            studentPhone = "+251 91 123 4567",
            packageName = "باقة الإتقان والتلقين (٣٠٠ دقيقة)",
            minutes = 300,
            amount = 65.0,
            currency = "USD",
            transactionId = "TB98421094382",
            paymentMethod = "Telebirr / CBE Bank",
            submittedAt = "اليوم 10:20 ص",
            status = PaymentStatus.PENDING
        ),
        PaymentRequest(
            id = "pay_req_102",
            studentId = "user_student_2",
            studentName = "عمر الفاروق",
            studentPhone = "+251 92 888 1234",
            packageName = "باقة التقييم والتجربة (٦٠ دقيقة)",
            minutes = 60,
            amount = 15.0,
            currency = "USD",
            transactionId = "TX87341902411",
            paymentMethod = "Al Rajhi Bank",
            submittedAt = "اليوم 09:15 ص",
            status = PaymentStatus.APPROVED
        ),
        PaymentRequest(
            id = "pay_req_103",
            studentId = "user_student_3",
            studentName = "حمزة بن بلال",
            studentPhone = "+251 93 456 7890",
            packageName = "باقة الختمة الكاملة (٦٠٠ دقيقة)",
            minutes = 600,
            amount = 120.0,
            currency = "USD",
            transactionId = "VF55409821334",
            paymentMethod = "Vodafone Cash",
            submittedAt = "أمس 08:40 م",
            status = PaymentStatus.PENDING
        )
    )

    val sampleLedger = mutableListOf(
        MinuteLedgerEntry(
            id = "led_1",
            studentId = "user_student_1",
            deltaMinutes = 60,
            balanceAfter = 100,
            type = LedgerType.PACKAGE_PURCHASE,
            description = "شحن باقة التقييم والتجربة (٦٠ دقيقة) عبر Telebirr",
            timestamp = "20 أكتوبر 2026"
        ),
        MinuteLedgerEntry(
            id = "led_2",
            studentId = "user_student_1",
            deltaMinutes = -15,
            balanceAfter = 85,
            type = LedgerType.RECITATION_SESSION,
            description = "جلسة إقراء سورة الفاتحة وأول البقرة مع الشيخ د. أحمد الجزري",
            timestamp = "24 أكتوبر 2026"
        )
    )

    val sampleAuditLogs = mutableListOf(
        AuditLogEntry(
            id = "aud_1",
            actorName = "إدارة المقرأة",
            actorRole = UserRole.ADMIN,
            action = "اعتماد حوالة بنكية",
            target = "عمر الفاروق (٦٠ دقيقة)",
            timestamp = "اليوم 09:20 ص",
            details = "تم تأكيد المعاملة TX87341902411 وإضافة الرصيد لحساب الطالب فوراً."
        ),
        AuditLogEntry(
            id = "aud_2",
            actorName = "الشيخ د. أحمد الجزري",
            actorRole = UserRole.TEACHER,
            action = "خصم دقائق جلسة إقراء",
            target = "عبد الرحمن الإثيوبي (-١٥ دقيقة)",
            timestamp = "24 أكتوبر 10:45 ص",
            details = "إتمام جلسة عرض سورة الفاتحة وأول البقرة مع منح تقييم ممتاز."
        ),
        AuditLogEntry(
            id = "aud_3",
            actorName = "إدارة المقرأة",
            actorRole = UserRole.ADMIN,
            action = "نشر إشعار عام",
            target = "جميع المجموعات والحلقات",
            timestamp = "أمس 04:00 م",
            details = "إرسال تعميم جدول اختبارات الإجازة ونهاية الفصل لجميع الطلاب."
        )
    )
}
