package com.starchee.easychat.presenters

import android.util.Log
import com.starchee.easychat.database.UserFirebaseDatabase
import com.starchee.easychat.views.UserListView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.InjectViewState
import moxy.MvpPresenter
import javax.inject.Inject

@InjectViewState
class UserListPresenter @Inject constructor(
    private val userFirebaseDatabase: UserFirebaseDatabase
) : MvpPresenter<UserListView>() {

    companion object {
        private val TAG = UserListPresenter::class.simpleName
    }

    fun loadUsers() {
        userFirebaseDatabase.getAllUsers()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                viewState.setUser(users = it)
                it.forEach{ user->
                    Log.d(TAG, user.toString())
                }
            },
                {
                    Log.d(TAG, "Can`t load users", it)
                })
    }
}