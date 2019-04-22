package com.mobitant.multichatapplication.message

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.mobitant.multichatapplication.R
import com.mobitant.multichatapplication.model.ChatModel
import kotlinx.android.synthetic.main.activity_message.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class MessageActivity : AppCompatActivity() {

    var uid : String? = null
    var chatRoomUid : String? = null
    var destinationUid : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)

        uid = FirebaseAuth.getInstance().currentUser!!.uid // 채팅을 요구하는 아이디
        destinationUid=intent.getStringExtra("destinationUid")


        messageActivity_button.onClick {
            val chatModel = ChatModel()
            chatModel.users
            chatModel.users

            FirebaseDatabase.getInstance().reference.child("chatrooms").push().setValue(chatModel)
        }




    }

    fun checkchatroom
}
