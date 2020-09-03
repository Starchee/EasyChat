package com.starchee.easychat.activities

import android.content.Intent
import android.os.Bundle
import com.starchee.easychat.App
import com.starchee.easychat.R
import com.starchee.easychat.presenters.StartPresenter
import com.starchee.easychat.views.StartView
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class StartActivity : MvpAppCompatActivity(), StartView {

    @Inject
    @InjectPresenter
    lateinit var startPresenter: StartPresenter

    @ProvidePresenter
    fun provideStartPresenter() = startPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

    }

    override fun startLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun startChat() {
        startActivity(Intent(this, ChatActivity::class.java))
    }
}