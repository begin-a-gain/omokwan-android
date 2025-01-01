package com.begin_a_gain.library.design.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object AppColors {
    val White = Color(0xFF_FFFFFF)
    val Black = Color(0xFF_000000)

    // Primary
    val Primary = Color(0xFF_FF8D4D)

    // Gray Scale
    val Gray900 = Color(0xFF_212121)
    val Gray800 = Color(0xFF_424242)
    val Gray700 = Color(0xFF_616161)
    val Gray600 = Color(0xFF_757575)
    val Gray500 = Color(0xFF_9E9E9E)
    val Gray400 = Color(0xFF_BDBDBD)
    val Gray300 = Color(0xFF_DEDCD8)
    val Gray200 = Color(0xFF_EDEBE7)
    val Gray100 = Color(0xFF_F5F3F0)
    val Gray50 = Color(0xFF_FCF9F6)

    // Error
    val Red = Color(0xFF_FF4321)

    // Etc.
    val Kakao = Color(0xFF_FEE500)
    val OmokGradientColor = Color(0xFF_ECFF9F)
    val OmokGrayShadow = Color(0xFF_545F71)
}

enum class ColorToken(val lightColor: Color, val darkColor: Color? = null) {
    UI_BG(AppColors.White),
    UI_BG_MODAL(AppColors.Black.copy(alpha = 0.2f)),
    UI_01(AppColors.Gray50),
    UI_02(AppColors.Gray100),
    UI_03(AppColors.Gray200),
    UI_DISABLE_01(AppColors.Gray200),
    UI_DISABLE_02(AppColors.Gray400),
    UI_PRIMARY(AppColors.Primary),
    UI_ALERT(AppColors.Red),

    STROKE_01(AppColors.Gray200),
    STROKE_02(AppColors.Gray300),
    STROKE_03(AppColors.Gray600),
    STROKE_FOCUS(AppColors.Gray900),
    STROKE_DISABLE(AppColors.Gray400),
    STROKE_ON_01(AppColors.White),
    STROKE_ON_DISABLE(AppColors.Gray600),
    STROKE_PRIMARY(AppColors.Primary),
    STROKE_PRIMARY_OP40(AppColors.Primary.copy(alpha = 0.4f)),
    STROKE_ALERT(AppColors.Red),
    
    TEXT_01(AppColors.Gray900),
    TEXT_02(AppColors.Gray600),
    TEXT_DISABLE(AppColors.Gray400),
    TEXT_ON_01(AppColors.White),
    TEXT_ON_DISABLE(AppColors.Gray600),
    TEXT_PRIMARY(AppColors.Primary),
    TEXT_ALERT(AppColors.Red),

    ICON_01(AppColors.Gray900),
    ICON_02(AppColors.Gray600),
    ICON_DISABLE(AppColors.Gray400),
    ICON_ON_01(AppColors.White),
    ICON_ON_DISABLE(AppColors.Gray600),
    ICON_PRIMARY(AppColors.Primary),
    ICON_ALERT(AppColors.Red);

    companion object {
        @Composable
        fun ColorToken.color(): Color {
            return if (isDarkMode()) this.darkColor?: this.lightColor else this.lightColor
        }
    }
}