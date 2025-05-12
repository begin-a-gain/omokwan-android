package com.begin_a_gain.library.design.component.image

import com.begin_a_gain.library.R

enum class OImageRes(val res: Int) {
    ArrowDown(R.drawable.arrow_down),
    ArrowLeft(R.drawable.arrow_left),
    ArrowRight(R.drawable.arrow_right),
    ArrowUp(R.drawable.arrow_up),

    Cancel(R.drawable.cancel),
    Plus(R.drawable.plus),
    Minus(R.drawable.minus),
    PlaceHolder(R.drawable.placeholder20),

    Profile(R.drawable.profile),
    Bell(R.drawable.bell),

    Checked(R.drawable.check),
    Unchecked(R.drawable.check_disable),

    Locked(R.drawable.lock_closed),
    Unlocked(R.drawable.lock_open),

    SpeechBubble(R.drawable.speech_bubble),
    PrimaryOmokMatch(R.drawable.omok_primary),
    GrayOmokMatch(R.drawable.omok_gray),
    OmokMatchGrid(R.drawable.omok_grid),

    Search(R.drawable.search),
    Menu(R.drawable.menu),

    CalendarDone(R.drawable.calendar_done),
    CalendarMyCombo(R.drawable.combo_mine),
    CalendarOthersCombo(R.drawable.combo_other),
    CalendarMyNone(R.drawable.calendar_my_none),
    CalendarOthersNone(R.drawable.calendar_others_none),
    CalendarNew(R.drawable.calendar_new)
}