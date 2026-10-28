package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.MaqraahRepository
import com.example.data.PaymentStatus
import com.example.data.UserRole
import com.example.i18n.AppLanguage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Al-Jazari Maqraah", appName)
    }

    @Test
    fun `repository handles role and language switching`() {
        val repo = MaqraahRepository()
        assertEquals(AppLanguage.ARABIC, repo.currentLanguage.value)

        repo.setLanguage(AppLanguage.AMHARIC)
        assertEquals(AppLanguage.AMHARIC, repo.currentLanguage.value)

        repo.switchRole(UserRole.TEACHER)
        assertEquals(UserRole.TEACHER, repo.currentUser.value.role)
    }

    @Test
    fun `manual payment request and admin approval workflow`() {
        val repo = MaqraahRepository()
        val initialBalance = repo.currentUser.value.minutesBalance

        val submitted = repo.submitManualPayment(
            packageName = "باقة الإتقان والتلقين",
            minutes = 300,
            amount = 65.0,
            senderName = "عبد الرحمن",
            transactionId = "TX99988877",
            phone = "+251911234567",
            method = "Telebirr"
        )
        assertTrue(submitted)

        val pending = repo.paymentRequests.value.first()
        assertEquals("TX99988877", pending.transactionId)
        assertEquals(PaymentStatus.PENDING, pending.status)

        repo.adminApprovePayment(pending.id)
        val approved = repo.paymentRequests.value.first { it.id == pending.id }
        assertEquals(PaymentStatus.APPROVED, approved.status)
        assertEquals(initialBalance + 300, repo.currentUser.value.minutesBalance)
    }
}
