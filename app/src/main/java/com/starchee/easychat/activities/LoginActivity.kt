package com.starchee.easychat.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.common.SignInButton
import com.starchee.easychat.App
import com.starchee.easychat.R
import com.starchee.easychat.presenters.GoogleSignInPresenter
import com.starchee.easychat.views.GoogleSignInView
import kotlinx.android.synthetic.main.activity_login.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class LoginActivity : MvpAppCompatActivity(), GoogleSignInView {

    @Inject
    @InjectPresenter
    lateinit var googleSignInPresenter: GoogleSignInPresenter

    @ProvidePresenter
    fun provideGoogleLoginPresenter() = googleSignInPresenter

    companion object {
        private const val RC_SIGN_IN = 1
        private val TAG = LoginActivity::class.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_btn_sign_in.setSize(SignInButton.SIZE_WIDE)
        login_btn_sign_in.setOnClickListener {
            googleSignInPresenter.signIn()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleSignInPresenter.handleAuthResponse(
            requestCode = requestCode,
            resultCode = resultCode,
            data = data)
    }

    override fun startGoogleSignIn(intent: Intent, requestCode: Int) {
        startActivityForResult(intent, requestCode)
    }

    override fun googleSignInSuccess() {
        startActivity(Intent(this, ChatActivity::class.java))
    }

    override fun showMessage(message: Int) {
        Toast.makeText(applicationContext, getString(message), Toast.LENGTH_LONG).show()
    }

}