package com.starchee.easychat.services

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth){


    fun userAuthenticated(): Boolean {
        var userAuthenticated = false
        firebaseAuth.currentUser?.let {
            userAuthenticated = true
        }
        return userAuthenticated
    }

    fun singOut(){
        firebaseAuth.signOut()
    }
}