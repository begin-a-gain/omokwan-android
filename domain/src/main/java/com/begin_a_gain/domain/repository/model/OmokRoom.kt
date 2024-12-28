package com.begin_a_gain.domain.repository.model

import com.begin_a_gain.library.core.type.OmokRoomStatus

data class OmokRoom(
    val id: String = "",
    val title: String = "",
    val isLocked: Boolean = true,
    val date: Int = 0,
    val member: Int = 0,
    val status: OmokRoomStatus = OmokRoomStatus.None,
)