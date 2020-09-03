package com.starchee.easychat.di.modules

import com.google.firebase.auth.FirebaseAuth
import com.starchee.easychat.services.AuthService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AuthServiceModule {

    @Singleton
    @Provides
    fun providesAuthService(firebaseAuth: FirebaseAuth) : AuthService {
        return AuthService(firebaseAuth = firebaseAuth)
    }
}