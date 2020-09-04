package com.starchee.easychat.di.components

import android.app.Application
import com.starchee.easychat.activities.ChatActivity
import com.starchee.easychat.activities.StartActivity
import com.starchee.easychat.activities.LoginActivity
import com.starchee.easychat.activities.UserListActivity
import com.starchee.easychat.di.modules.FirebaseSignInModule
import com.starchee.easychat.di.modules.GoogleSignInServiceModule
import com.starchee.easychat.di.modules.UserFirebaseDatabaseModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    FirebaseSignInModule::class,
    GoogleSignInServiceModule::class,
    UserFirebaseDatabaseModule::class])
interface AppComponent {

    fun inject(startActivity: StartActivity)
    fun inject(loginActivity: LoginActivity)
    fun inject(chatActivity: ChatActivity)
    fun inject(userListActivity: UserListActivity)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun withApplication(application: Application) : Builder
        fun build() : AppComponent
    }
}