package com.begin_a_gain.feature.main.omoklist

import org.joda.time.DateTime

data class OmokListState(
    val currentDate: DateTime = DateTime.now()
)

interface OmokListSideEffect {

}