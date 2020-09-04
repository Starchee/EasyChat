package com.starchee.easychat.views

import com.starchee.easychat.models.User
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(value = AddToEndSingleStrategy::class)
interface UserListView: MvpView {
    fun setUser(users: List<User>)
}