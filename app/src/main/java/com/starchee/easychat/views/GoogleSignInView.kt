package com.starchee.easychat.views

import android.content.Intent
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface GoogleSignInView: MvpView {
    fun startGoogleSignIn(intent: Intent, requestCode: Int)
    fun googleSignInSuccess()
    fun showMessage(message: Int)

}