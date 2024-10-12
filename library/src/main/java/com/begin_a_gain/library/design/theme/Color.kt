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
}

enum class ColorToken(val lightColor: Color, val darkColor: Color? = null) {
    ui_backround(AppColors.White),
    ui_backround_modal(AppColors.Black.copy(alpha = 0.2f)),
    ui_01(AppColors.Gray50),
    ui_02(AppColors.Gray100),
    ui_03(AppColors.Gray200),
    ui_disable_01(AppColors.Gray200),
    ui_disable_02(AppColors.Gray400),
    ui_primary(AppColors.Primary),
    ui_alert(AppColors.Red),

    stroke_01(AppColors.Gray200),
    stroke_02(AppColors.Gray300),
    stroke_03(AppColors.Gray600),
    stroke_focus(AppColors.Gray900),
    stroke_disable(AppColors.Gray400),
    stroke_on_01(AppColors.White),
    stroke_on_disable(AppColors.Gray600),
    stroke_primary(AppColors.Primary),
    stroke_primary_op40(AppColors.Primary.copy(alpha = 0.4f)),
    stroke_alert(AppColors.Red),

    text_01(AppColors.Gray900),
    text_02(AppColors.Gray600),
    text_disable(AppColors.Gray400),
    text_on_01(AppColors.White),
    text_on_disable(AppColors.Gray600),
    text_primary(AppColors.Primary),
    text_alert(AppColors.Red);

    companion object {
        @Composable
        fun ColorToken.color(): Color {
            return if (isDarkMode()) this.darkColor?: this.lightColor else this.lightColor
        }
    }
}