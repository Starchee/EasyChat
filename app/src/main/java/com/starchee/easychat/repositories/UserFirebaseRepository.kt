package com.starchee.easychat.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.starchee.easychat.models.User
import io.reactivex.rxjava3.core.Single

class UserFirebaseRepository (database: FirebaseDatabase) {

    companion object {
        private val TAG = UserFirebaseRepository::class.simpleName
    }

    private val userRef = database.reference.child("user")

    fun getCurrentUser(firebaseUser: FirebaseUser) : Single<User> {
        val query  = userRef.orderByChild("email").equalTo(firebaseUser.email)

        return Single.create{ emitter ->
            query.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    Log.d(TAG, "exist ${snapshot.value.toString()}")
                    val user: User = snapshot.getValue(User::class.java) as User

                    emitter.onSuccess(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "DatabaseError ", error.toException())
                }

            })
        }
    }

    fun saveUserIfNotExist(firebaseUser: FirebaseUser) : Single<User>{
        val query  = userRef.orderByChild("email").equalTo(firebaseUser.email)

        return Single.create{ emitter ->
            query.addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val user: User

                    if (snapshot.exists()) {
                      user = snapshot.getValue(User::class.java) as User
                    } else {
                        user = parseFirebaseUser(firebaseUser)
                        userRef.push().setValue(user)
                    }

                    emitter.onSuccess(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "DatabaseError ", error.toException())
                }

            })
        }
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