package com.example.data.db

import android.util.Log
import com.example.domain.interfaces.LandDBRepository
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class LandDBRepositoryImpl(
    private val firebaseDatabase: FirebaseDatabase
) : LandDBRepository {

    override suspend fun likeLand(id: Int) {
        Log.d("Test", "$id in a repo")
        withContext(Dispatchers.Default) {
            getReference().child(id.toString()).setValue(id)
        }
    }

    override suspend fun dislikeLand(id: Int) {
        withContext(Dispatchers.Default) {
            getReference().child(id.toString()).removeValue()
        }
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return getReference().child(id.toString()).get().await().value != null
    }

    fun getReference() =
        firebaseDatabase.reference.child("favLands")

}