package com.starchee.easychat.di.components

import android.app.Application
import com.starchee.easychat.activities.LoginActivity
import com.starchee.easychat.di.modules.FirebaseSignInModule
import com.starchee.easychat.di.modules.UserRepositoryModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    FirebaseSignInModule::class,
    UserRepositoryModule::class
])
interface AppComponent {

    fun inject(loginActivity: LoginActivity)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun withApplication(application: Application) : Builder
        fun build() : AppComponent
    }
}