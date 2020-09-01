package com.starchee.easychat.repositories

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.starchee.easychat.models.User

class FirebaseRepository (database: FirebaseDatabase) {

    companion object {
        private val TAG = FirebaseRepository::class.simpleName
    }

    private val userRef = database.reference.child("user")

    fun saveUserIfNotExist(user: User){
        val query  = userRef.orderByChild("email").equalTo(user.email)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    userRef.push().setValue(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}