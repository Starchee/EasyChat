package com.starchee.easychat.di.modules

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.starchee.easychat.presenters.GoogleLoginPresenter
import com.starchee.easychat.repositories.UserFirebaseRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserRepositoryModule {

    @Singleton
    @Provides
    fun providesFirebaseDatabase() : FirebaseDatabase{
        return Firebase.database
    }

    @Singleton
    @Provides
    fun providesFirebaseRepository(database: FirebaseDatabase) : UserFirebaseRepository{
        return UserFirebaseRepository(database = database)
    }
}