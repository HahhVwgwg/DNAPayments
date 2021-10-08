package com.dnapayments.data.repository

import com.dnapayments.api_clients.Commission
import com.dnapayments.api_clients.IMainService
import com.dnapayments.data.model.*
import com.dnapayments.utils.simpleErrorSecond
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val service: IMainService) {
    suspend fun fetchProfile(deviceType: String, versionName: String):
            SimpleResult<ProfileOtp> {
        return try {
            withContext(Dispatchers.IO) {
                val data = service.getProfileAsync(
                    device_type = deviceType, version = versionName
                ).await()
                data.error?.let {
                    SimpleResult.Error(it)
                } ?: SimpleResult.Success(data)
            }
        } catch (e: Exception) {
            e.simpleErrorSecond()
        }
    }

    suspend fun getCommission(amount: String):
            SimpleResult<Commission> {
        return try {
            withContext(Dispatchers.IO) {
                val data = service.getCommissionAsync(
                    hashMapOf("amount" to amount)
                ).await()
                data.error?.let {
                    SimpleResult.Error(it)
                } ?: SimpleResult.Success(data)
            }
        } catch (e: Exception) {
            e.simpleErrorSecond()
        }
    }

    suspend fun withDraw(amount: String, cardId: Int):
            SimpleResult<MessageOtp> {
        return try {
            withContext(Dispatchers.IO) {
                val data = service.withDrawAsync(
                    hashMapOf("amount" to amount, "card_id" to cardId)
                ).await()
                data.error?.let {
                    SimpleResult.Error(it)
                } ?: SimpleResult.Success(data)
            }
        } catch (e: Exception) {
            e.simpleErrorSecond()
        }
    }

    suspend fun getCardList():
            SimpleResult<List<CardOtp>> {
        return try {
            withContext(Dispatchers.IO) {
                val data = service.getCardListAsync().await()
                SimpleResult.Success(data)
            }
        } catch (e: Exception) {
            e.simpleErrorSecond()
        }
    }

    suspend fun fetchHistory():
            SimpleResult<WalletTransactions> {
        return try {
            withContext(Dispatchers.IO) {
                val data = service.getWalletTransactionAsync().await()
                data.error?.let {
                    SimpleResult.Error(it)
                } ?: SimpleResult.Success(data)
            }
        } catch (e: Exception) {
            e.simpleErrorSecond()
        }
    }

    suspend fun getTransactionById(id: Int):
            SimpleResult<HistoryOtp> {
        return try {
            withContext(Dispatchers.IO) {
                val data = service.getHistoryByIdAsync(id).await()
                data.error?.let {
                    SimpleResult.Error(it)
                } ?: SimpleResult.Success(data)
            }
        } catch (e: Exception) {
            e.simpleErrorSecond()
        }
    }

    suspend fun addCard(cardId: String, brand: String, cardName: String):
            SimpleResult<MessageOtp> {
        return try {
            withContext(Dispatchers.IO) {
                val data = service.addCardAsync(
                    hashMapOf("brand" to brand, "card_id" to cardId, "card_name" to cardName)
                ).await()
                data.error?.let {
                    SimpleResult.Error(it)
                } ?: SimpleResult.Success(data)
            }
        } catch (e: Exception) {
            e.simpleErrorSecond()
        }
    }

    suspend fun deleteCard(cardId: Int): SimpleResult<MessageOtp> {
        return try {
            withContext(Dispatchers.IO) {
                val data = service.deleteCardAsync(
                    hashMapOf("id" to cardId)
                ).await()
                data.error?.let {
                    SimpleResult.Error(it)
                } ?: SimpleResult.Success(data)
            }
        } catch (e: Exception) {
            e.simpleErrorSecond()
        }
    }

    suspend fun getNews(): SimpleResult<List<NotificationElement>> {
        return try {
            withContext(Dispatchers.IO) {
                val data = service.getNewsAsync(
                ).await()
                SimpleResult.Success(data)
            }
        } catch (e: Exception) {
            e.simpleErrorSecond()
        }
    }
}
