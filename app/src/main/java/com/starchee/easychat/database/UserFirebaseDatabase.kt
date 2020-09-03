package com.starchee.easychat.database

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.starchee.easychat.models.User
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class UserFirebaseDatabase @Inject constructor(
    private val userReference: DatabaseReference) {

    companion object {
        private val TAG = UserFirebaseDatabase::class.simpleName
    }

    fun getCurrentUser(firebaseUser: FirebaseUser) : Single<User> {
        val query  = userReference.orderByChild("email").equalTo(firebaseUser.email)

        return Single.create{ emitter ->
            query.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d(TAG, "exist ${snapshot.value.toString()}")
                    val user: User = snapshot.getValue(User::class.java) as User

                    emitter.onSuccess(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    emitter.onError(error.toException())
                }

            })
        }
    }

    fun saveUserIfNotExist(firebaseUser: FirebaseUser) {
        val query  = userReference.orderByChild("email").equalTo(firebaseUser.email)
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (!snapshot.exists()) {
                    val user = parseFirebaseUser(firebaseUser)
                    userReference.push().setValue(user)
                    }
                }

            override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "DatabaseError ", error.toException())
            }
        })
    }

    private fun parseFirebaseUser(user: FirebaseUser): User {
        val name = user.displayName!!
        val email = user.email!!
        val photo = user.photoUrl.toString()
        val phoneNumber = user.phoneNumber
        return User(
            name = name,
            email = email,
            photo = photo,
            phoneNumber = phoneNumber)
    }
}