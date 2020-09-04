package com.starchee.easychat.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.squareup.picasso.Picasso
import com.starchee.easychat.R
import kotlinx.android.synthetic.main.activity_private_chat.*

class PrivateChatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_chat)

        Picasso.get().load(intent.getStringExtra("photo")).into(private_chat_civ_photo)
        private_chat_txt_name.text = intent.getStringExtra("name")
    }
}