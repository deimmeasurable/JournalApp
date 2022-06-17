package com.example.myjournalapp.auth.Event

import com.example.myjournalapp.data.model.RegistrationRequest

sealed class AuthEvent{
    data class RegistrationEvent(val request: RegistrationRequest): AuthEvent()
}
