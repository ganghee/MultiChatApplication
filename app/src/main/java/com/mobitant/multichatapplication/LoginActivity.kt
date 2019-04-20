package com.mobitant.multichatapplication

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var remoteConfig : FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //RemoteConfig의 value를 설정하여 툴바의 색상과 버튼의 색상을 지정할 수 있도록 하였다.
        remoteConfig = FirebaseRemoteConfig.getInstance()
        val splash_background:String = remoteConfig.getString(getString(R.string.rc_color))
        window.statusBarColor = Color.parseColor(splash_background)

        loginActivity_button_login.setBackgroundColor(Color.parseColor(splash_background))
        loginActivity_button_signup.setBackgroundColor(Color.parseColor(splash_background))
        loginActivity_button_signup.onClick { startActivity<SignupActivity>() }


    }
}
