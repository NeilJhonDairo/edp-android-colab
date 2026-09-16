package com.liceo.liceochat.data.network.dto

import com.liceo.liceochat.data.local.MessageEntity
import com.liceo.liceochat.domain.Message
import java.util.UUID

fun MessageDto.toDomain(): Message =
    Message(
        id = id ?: "",
        sender = sender ?: "Anonymous",
        text = text ?: "",
        createdAt = createdAt ?: System.currentTimeMillis()
    )

fun MessageDto.toEntity(): MessageEntity =
    MessageEntity(
        id = id ?: UUID.randomUUID().toString(),
        sender = sender ?: "Anonymous",
        text = text ?: "",
        createdAt = createdAt ?: System.currentTimeMillis()
    )

fun MessageEntity.toDomain(): Message =
    Message(
        id = id,
        sender = sender,
        text = text,
        createdAt = createdAt
    )

// KINI ANG GI-ADD: Mapper para sa NewMessageDto -> MessageEntity
fun NewMessageDto.toEntity(): MessageEntity =
    MessageEntity(
        id = UUID.randomUUID().toString(),
        sender = sender ?: "Anonymous",
        text = text ?: "",
        createdAt = createdAt ?: System.currentTimeMillis()
    )