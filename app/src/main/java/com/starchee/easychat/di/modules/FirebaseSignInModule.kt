package com.starchee.easychat.di.modules

import android.app.Application
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.starchee.easychat.R
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class FirebaseSignInModule {

    @Singleton
    @Provides
    fun providesGoogleSignInClient(application: Application): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(application.getString(R.string.FirebaseApiKey))
            .build()

        return GoogleSignIn.getClient(application, gso)
    }

    @Singleton
    @Provides
    fun providesFirebaseAuth() : FirebaseAuth{

        return FirebaseAuth.getInstance()
    }
}