package com.starchee.easychat.di.modules

import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.starchee.easychat.database.UserFirebaseDatabase
import com.starchee.easychat.services.GoogleSignInService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [
    UserFirebaseDatabaseModule::class,
    FirebaseSignInModule::class])
class GoogleSignInServiceModule {

    @Singleton
    @Provides
    fun providesGoogleSignInService(
        userFirebaseDatabase: UserFirebaseDatabase,
        firebaseAuth: FirebaseAuth,
        googleSignInClient: GoogleSignInClient) : GoogleSignInService{
        return GoogleSignInService(
            userFirebaseDatabase = userFirebaseDatabase,
            firebaseAuth = firebaseAuth,
            googleSignInClient = googleSignInClient)
    }
}