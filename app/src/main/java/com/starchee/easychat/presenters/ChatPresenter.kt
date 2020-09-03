package com.starchee.easychat.presenters

import com.starchee.easychat.services.AuthService
import com.starchee.easychat.views.ChatView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class ChatPresenter @Inject constructor(
    private val authService: AuthService): MvpPresenter<ChatView>(){

    fun logout(){
        authService.singOut()
        viewState.logout()
    }
}