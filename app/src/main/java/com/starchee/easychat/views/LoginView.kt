package com.starchee.easychat.views

import android.content.Intent
import com.starchee.easychat.models.User
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface LoginView: MvpView {
    fun startActivitiesForResult(intent: Intent, requestCode: Int)
    fun showMessage(message: Int)
    fun startChatActivity(user: User)
}