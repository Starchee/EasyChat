package com.starchee.easychat.di.modules

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.starchee.easychat.database.UserFirebaseDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class UserFirebaseDatabaseModule {

    @Singleton
    @Provides
    fun providesUserDatabaseReference() : DatabaseReference{
        return Firebase.database.reference.child("user")
    }

    @Singleton
    @Provides
    fun providesUserFirebaseDatabase(userReference: DatabaseReference) : UserFirebaseDatabase {
        return UserFirebaseDatabase(userReference = userReference)
    }


}