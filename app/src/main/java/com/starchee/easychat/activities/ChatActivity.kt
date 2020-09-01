package com.starchee.easychat.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import com.starchee.easychat.R
import com.starchee.easychat.presenters.ChatPresenter
import com.starchee.easychat.views.ChatView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter

class ChatActivity : MvpAppCompatActivity(), ChatView {

    @InjectPresenter
    lateinit var chatPresenter: ChatPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.login_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.login_menu_logout -> {
                chatPresenter.logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun logout() {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("logout", true)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }
}