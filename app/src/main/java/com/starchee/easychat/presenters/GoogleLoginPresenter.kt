package com.starchee.easychat.presenters

import android.content.Context
import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.starchee.easychat.models.User
import com.starchee.easychat.repositories.FirebaseRepository
import com.starchee.easychat.views.LoginView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class GoogleLoginPresenter @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
    private val repository: FirebaseRepository,
    private val auth: FirebaseAuth
) : MvpPresenter<LoginView>() {

    private lateinit var appUser:User

    companion object {
        private const val RC_SIGN_IN = 1
        private val TAG = GoogleLoginPresenter::class.simpleName
    }

    fun initUser(logout : Boolean) {
        if (logout){
            auth.signOut()
        } else {
            auth.currentUser?.let {
                Log.d(TAG, it.displayName.toString())
                viewState.startChatActivity(parseFirebaseUser(it))
            }
        }
    }

    fun startAuthProcess(){
        viewState.startActivitiesForResult(intent = googleSignInClient.signInIntent, requestCode = RC_SIGN_IN)
    }


    fun handleAuthResponse(requestCode: Int, resultCode: Int, data: Intent?){
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
                // ...
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success")
                    val user = auth.currentUser!! // тут надо распарсить
                    parseFirebaseUser(user = user)
                    repository.saveUserIfNotExist(user = appUser)
                    viewState.startChatActivity(user = appUser)
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun parseFirebaseUser(user: FirebaseUser): User {
        val name = user.displayName!!
        val email = user.email!!
        val photo = user.photoUrl.toString()
        val phoneNumber = user.phoneNumber
        appUser = User(
            name = name,
            email = email,
            photo = photo,
            phoneNumber = phoneNumber)
        return appUser
    }



}

