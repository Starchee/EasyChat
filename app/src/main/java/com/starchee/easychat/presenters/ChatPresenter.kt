package com.starchee.easychat.presenters

import com.starchee.easychat.views.ChatView
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class ChatPresenter: MvpPresenter<ChatView>(){

    fun logout(){
        viewState.logout()
    }
}