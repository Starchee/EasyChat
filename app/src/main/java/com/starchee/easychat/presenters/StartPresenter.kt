package com.starchee.easychat.presenters

import com.starchee.easychat.services.AuthService
import com.starchee.easychat.views.StartView
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class StartPresenter @Inject constructor(
    private val authService: AuthService): MvpPresenter<StartView>() {

    override fun attachView(view: StartView?) {
        super.attachView(view)
        if (authService.userAuthenticated()){
            viewState.startChat()
        } else {
            viewState.startLogin()
        }
    }
}