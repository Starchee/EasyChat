package com.starchee.easychat.activities

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.starchee.easychat.App
import com.starchee.easychat.R
import com.starchee.easychat.adapters.UserAdapter
import com.starchee.easychat.models.User
import com.starchee.easychat.presenters.UserListPresenter
import com.starchee.easychat.views.UserListView
import kotlinx.android.synthetic.main.activity_user_list.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class UserListActivity : MvpAppCompatActivity(), UserListView {

    private lateinit var userAdapter:UserAdapter

    @Inject
    @InjectPresenter
    lateinit var userListPresenter: UserListPresenter

    @ProvidePresenter
    fun provideUserListPresenter() = userListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        userAdapter = UserAdapter(userListPresenter)

        userListPresenter.loadUsers()

        user_list_rv.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
            setHasFixedSize(true)
        }

    }

    override fun setUser(users: List<User>) {
        userAdapter.setupUsers(userList = users)
    }

    override fun startChatWithUser(name: String, photo: String) {
        val intent = Intent(this, PrivateChatActivity::class.java)
        intent.apply {
            putExtra("name", name)
            putExtra("photo", photo)
        }
        startActivity(intent)
    }
}