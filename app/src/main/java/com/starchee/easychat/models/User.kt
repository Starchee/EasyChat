package com.starchee.easychat.models

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var name: String? = "",
    var email: String = "",
    var photo: String = "",
    var phoneNumber: String? = ""
)