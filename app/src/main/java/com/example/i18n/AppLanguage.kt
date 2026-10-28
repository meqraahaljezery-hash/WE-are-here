package com.example.i18n

import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.LayoutDirection
import com.example.ui.theme.CairoFontFamily
import com.example.ui.theme.InterFontFamily
import com.example.ui.theme.NotoSansEthiopicFontFamily

enum class AppLanguage(
    val code: String,
    val nativeName: String,
    val layoutDirection: LayoutDirection,
    val fontFamily: FontFamily
) {
    ARABIC("ar", "العربية", LayoutDirection.Rtl, CairoFontFamily),
    AMHARIC("am", "አማርኛ", LayoutDirection.Ltr, NotoSansEthiopicFontFamily),
    ENGLISH("en", "English", LayoutDirection.Ltr, InterFontFamily);

    companion object {
        fun fromCode(code: String): AppLanguage {
            return entries.find { it.code == code } ?: ARABIC
        }
    }
}
