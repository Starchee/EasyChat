package com.starchee.easychat.presenters

import android.content.Intent
import com.starchee.easychat.R
import com.starchee.easychat.services.GoogleSignInService
import com.starchee.easychat.views.GoogleSignInView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class GoogleSignInPresenter @Inject constructor(
    private val googleSignInService: GoogleSignInService
) : MvpPresenter<GoogleSignInView>() {

    companion object {
        private val TAG = GoogleSignInPresenter::class.simpleName
        private const val RC_SIGN_IN = 1
    }

    fun signIn(){
        viewState.startGoogleSignIn(
            intent = googleSignInService.getGoogleSignInIntent(),
            requestCode = RC_SIGN_IN)
    }

    fun handleAuthResponse(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == RC_SIGN_IN) {
            googleSignInService.firebaseAuthWithGoogle(data)
                .subscribe(
                    {
                        viewState.googleSignInSuccess()
                    },
                    {
                        viewState.showMessage(message = R.string.google_login_failure)
                    })
        }
    }
}

