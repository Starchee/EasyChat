package com.starchee.easychat.views

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface StartView : MvpView{
    fun startLogin()
    fun startChat()
}