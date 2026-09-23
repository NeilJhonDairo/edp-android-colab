package com.example.myapplication.data.network.dto

import com.example.myapplication.domain.model.User

fun UserDto.toDomain(): User = User(
    id = id ?: "",
    fullName = fullname?.trim() ?: "(no name)",
    email = email?.trim() ?: "",
    birthdate = birthdate?.trim() ?: "(not set)"
)