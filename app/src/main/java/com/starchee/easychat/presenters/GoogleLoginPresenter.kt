package com.starchee.easychat.presenters

import android.content.Intent
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.starchee.easychat.R
import com.starchee.easychat.repositories.UserFirebaseRepository
import com.starchee.easychat.views.LoginView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class GoogleLoginPresenter @Inject constructor(
    private val googleSignInClient: GoogleSignInClient,
    private val userRepository: UserFirebaseRepository,
    private val auth: FirebaseAuth
) : MvpPresenter<LoginView>() {

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
                userRepository.getCurrentUser(it)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ user ->
                        viewState.startChatActivity(user)
                    } , { t ->
                        Log.w(TAG, "Current user from db",t)
                    })
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
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                viewState.showMessage(message = R.string.google_login_failure)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithCredential:success") // success message
                    val firebaseUser = auth.currentUser!!

                    userRepository.saveUserIfNotExist(firebaseUser = firebaseUser)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({
                            viewState.startChatActivity(it)
                        } , {
                            Log.w(TAG, "Current user from db", it)
                        })
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

}

