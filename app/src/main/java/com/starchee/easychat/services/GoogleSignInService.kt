package com.starchee.easychat.services

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.starchee.easychat.database.UserFirebaseDatabase
import com.starchee.easychat.presenters.GoogleSignInPresenter
import io.reactivex.rxjava3.core.Completable

class GoogleSignInService(
    private val userFirebaseDatabase: UserFirebaseDatabase,
    private val firebaseAuth: FirebaseAuth,
    private val googleSignInClient: GoogleSignInClient) {

    companion object {
        private val TAG = GoogleSignInPresenter::class.simpleName
    }

    fun getGoogleSignInIntent() : Intent {
        return googleSignInClient.signInIntent
    }

    fun firebaseAuthWithGoogle(data: Intent?): Completable {
        return Completable.create { emitter ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken!!, null)
                firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener {
                        userFirebaseDatabase.saveUserIfNotExist(it.user!!)
                        emitter.onComplete()
                    }
            } catch (e : ApiException){
                emitter.onError(e)
            }
        }
    }
}