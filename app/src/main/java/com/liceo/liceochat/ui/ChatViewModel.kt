package com.liceo.liceochat.ui

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.liceo.liceochat.core.AppResult
import com.liceo.liceochat.data.local.ChatDatabase
import com.liceo.liceochat.data.network.NetworkModule
import com.liceo.liceochat.data.repository.ChatRepositoryImpl
import com.liceo.liceochat.domain.ChatRepository
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository // GIVEN
) : ViewModel() {
    var uiState: ChatUiState by mutableStateOf(ChatUiState.Loading) // GIVEN
        private set
    var myName: String by mutableStateOf("") // GIVEN — the name typed in the app
        private set
    var draft: String by mutableStateOf("") // GIVEN — the message being typed
        private set

    fun onNameChange(value: String) { myName = value } // GIVEN
    fun onDraftChange(value: String) { draft = value } // GIVEN

    init { load() } // GIVEN

    fun load() {
        viewModelScope.launch {
            uiState = ChatUiState.Loading
            // TODO 9: call repository.getMessages() and put the answer in a when.
            uiState = when (val r = repository.getMessages()) {
                is AppResult.Success -> if (r.data.isEmpty()) ChatUiState.Empty else ChatUiState.Ready(r.data)
                is AppResult.Failure.NoInternet -> ChatUiState.Error("No internet connection.")
                is AppResult.Failure.Timeout -> ChatUiState.Error("The server took too long.")
                is AppResult.Failure -> ChatUiState.Error("Something went wrong.")
            }
        }
    }

    fun send() {
        // TODO 10: do nothing if myName or draft is blank. Just return.
        if (myName.isBlank() || draft.isBlank()) return

        viewModelScope.launch {
            // TODO 11: call repository.sendMessage(myName, draft).
            when (repository.sendMessage(myName, draft)) {
                is AppResult.Success -> {
                    draft = ""
                    load()
                }
                is AppResult.Failure -> {
                    uiState = ChatUiState.Error("Could not send. Check your connection.")
                }
            }
        }
    }

    companion object { // GIVEN
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[APPLICATION_KEY] as Application
                val db = ChatDatabase.get(app)

                ChatViewModel(
                    ChatRepositoryImpl(NetworkModule.chatApi, db.messageDao())
                )
            }
        }
    }
}