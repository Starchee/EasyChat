package com.starchee.easychat.di.modules

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.starchee.easychat.repositories.FirebaseRepository
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
    fun providesFirebaseRepository(database: FirebaseDatabase) : FirebaseRepository{
        return FirebaseRepository(database = database)
    }
}