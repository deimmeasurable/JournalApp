package com.example.myjournalapp.auth.Event

import com.example.myjournalapp.data.model.LogInRequest
import com.example.myjournalapp.data.model.RegistrationRequest

sealed class AuthEvent{
    data class RegistrationEvent(val request: RegistrationRequest): AuthEvent()
    data class LogInEvent(val request: LogInRequest): AuthEvent()
}
