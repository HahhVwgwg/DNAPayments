package com.dnapayments.data.repository

import com.dnapayments.data.model.Zhyrau
import com.dnapayments.domain.network.SimpleResult
import com.dnapayments.utils.awaitWithList
import com.dnapayments.utils.simpleError
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ZhyrauRepository {
    private val firebase: FirebaseFirestore = Firebase.firestore
    suspend fun fetchZhyrauList():
            SimpleResult<List<Zhyrau>> {
        println(firebase)
        return try {
            withContext(Dispatchers.IO) {
                val task =
                    firebase.collection(ZHYRAU).get()
                task.awaitWithList(Zhyrau::class.java)
            }
        } catch (e: Throwable) {
            e.printStackTrace()
            e.simpleError()
        }
    }

    companion object {

        private const val ZHYRAU = "ZHYRAU"
    }
}