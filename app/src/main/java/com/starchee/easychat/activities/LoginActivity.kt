package com.starchee.easychat.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.common.SignInButton
import com.starchee.easychat.App
import com.starchee.easychat.R
import com.starchee.easychat.models.User
import com.starchee.easychat.presenters.GoogleLoginPresenter
import com.starchee.easychat.views.LoginView
import kotlinx.android.synthetic.main.activity_login.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class LoginActivity : MvpAppCompatActivity(), LoginView {

    @Inject
    @InjectPresenter
    lateinit var googleLoginPresenter: GoogleLoginPresenter

    @ProvidePresenter
    fun provideGoogleLoginPresenter() = googleLoginPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        googleLoginPresenter.initUser(logout = intent.getBooleanExtra("logout", false))

        login_btn_sign_in.setSize(SignInButton.SIZE_WIDE)
        login_btn_sign_in.setOnClickListener {
            googleLoginPresenter.startAuthProcess()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleLoginPresenter.handleAuthResponse(requestCode = requestCode, resultCode = resultCode, data = data)
    }

    override fun startActivitiesForResult(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode)
    }

    override fun showMessage(message: Int) {
        Toast.makeText(applicationContext, getString(message), Toast.LENGTH_LONG ).show()
    }

    override fun startChatActivity(user: User) {
        val intent = Intent(this, ChatActivity::class.java)
        intent.putExtra("name", user.name)
        intent.putExtra("email", user.email)
        intent.putExtra("photo", user.photo)
        intent.putExtra("phoneNumber", user.phoneNumber)
        startActivity(intent)
    }


}