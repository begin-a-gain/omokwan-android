package com.begin_a_gain.library.design.theme

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

data class OTypography(
    val fontWeight: FontWeight,
    val fontSize: TextUnit,
    val lineHeight: TextUnit,
    val multiLineHeight: TextUnit? = null,
    val letteringSpacing: TextUnit = 0.sp,
)

enum class OTextStyle(
    val typography: OTypography,
) {
    Display(OTypography(FontWeight.Bold, 24.sp, 32.sp)),
    Headline(OTypography(FontWeight.Bold, 20.sp, 28.sp)),

    Title2(OTypography(FontWeight.Bold, 16.sp, 20.sp)),
    Title1(OTypography(FontWeight.Bold, 14.sp, 18.sp)),

    Subtitle3(OTypography(FontWeight.Medium, 16.sp, 20.sp)),
    Subtitle2(OTypography(FontWeight.Medium, 14.sp, 18.sp)),
    Subtitle1(OTypography(FontWeight.Medium, 12.sp, 16.sp)),

    Body2(OTypography(FontWeight.Normal, 16.sp, 20.sp, 24.sp)),
    Body1(OTypography(FontWeight.Normal, 14.sp, 18.sp, 22.sp)),

    Caption(OTypography(FontWeight.Normal, 12.sp, 16.sp, 20.sp))
}