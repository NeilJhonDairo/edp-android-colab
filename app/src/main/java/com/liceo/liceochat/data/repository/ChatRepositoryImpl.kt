package com.liceo.liceochat.data.repository

import com.liceo.liceochat.core.AppResult
import com.liceo.liceochat.data.local.MessageDao
import com.liceo.liceochat.data.network.ChatApiService
import com.liceo.liceochat.data.network.dto.NewMessageDto
import com.liceo.liceochat.data.network.dto.toDomain
import com.liceo.liceochat.data.network.dto.toEntity
import com.liceo.liceochat.domain.ChatRepository
import com.liceo.liceochat.domain.Message
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ChatRepositoryImpl(
    private val api: ChatApiService,
    private val dao: MessageDao
) : ChatRepository {

    override suspend fun getMessages(): AppResult<List<Message>> {
        return try {

            val remoteDtos = api.getMessages()
            val entities = remoteDtos.map { it.toEntity() }
            dao.insertMessages(entities)

            val updatedLocal = dao.getAllMessages()
            AppResult.Success(updatedLocal.map { it.toDomain() })
        } catch (e: Exception) {

            val localEntities = dao.getAllMessages()
            if (localEntities.isNotEmpty()) {

                AppResult.Success(localEntities.map { it.toDomain() })
            } else {

                when (e) {
                    is UnknownHostException, is IOException -> AppResult.Failure.NoInternet
                    is SocketTimeoutException -> AppResult.Failure.Timeout
                    else -> AppResult.Failure.Unknown(e.message)
                }
            }
        }
    }

    override suspend fun sendMessage(sender: String, text: String): AppResult<Unit> {
        val dto = NewMessageDto(sender, text, System.currentTimeMillis())

        return try {

            api.sendMessage(dto)


            dao.insertMessages(listOf(dto.toEntity()))
            AppResult.Success(Unit)
        } catch (e: UnknownHostException) {
            AppResult.Failure.NoInternet
        } catch (e: SocketTimeoutException) {
            AppResult.Failure.Timeout
        } catch (e: IOException) {
            AppResult.Failure.NoInternet
        } catch (e: Exception) {
            AppResult.Failure.Unknown(e.message)
        }
    }
}