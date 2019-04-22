package com.mobitant.multichatapplication

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.android.synthetic.main.activity_login.*
import org.jetbrains.anko.sdk27.coroutines.onClick
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

class LoginActivity : AppCompatActivity() {

    private lateinit var remoteConfig : FirebaseRemoteConfig
    private lateinit var firebaseAuth : FirebaseAuth
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //RemoteConfig의 value를 설정하여 툴바의 색상과 버튼의 색상을 지정할 수 있도록 하였다.
        remoteConfig = FirebaseRemoteConfig.getInstance()
        firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.signOut()

        val splash_background:String = remoteConfig.getString(getString(R.string.rc_color))
        window.statusBarColor = Color.parseColor(splash_background)

        loginActivity_button_login.setBackgroundColor(Color.parseColor(splash_background))
        loginActivity_button_signup.setBackgroundColor(Color.parseColor(splash_background))
        loginActivity_button_login.onClick{ loginEvent() }
        loginActivity_button_signup.onClick { startActivity<SignupActivity>() }

        //인증이 완료된 경우에만 자동적으로 로그인 인터페이스 리스너가 실행된다
        authStateListener = FirebaseAuth.AuthStateListener{firebaseAuth ->
                var user = firebaseAuth.currentUser
                if (user != null){
                    //로그인
                    startActivity<MainActivity>()
                    finish()
                }else{
                    //로그아웃
                }
           }
    }

    //인증이 완료되면 인터페이스 리스너에게 넘기기
    fun loginEvent(){
        firebaseAuth.signInWithEmailAndPassword(loginActivity_edittext_id.text.toString(),loginActivity_edittext_password.text.toString())
            .addOnCompleteListener(this@LoginActivity){task ->
                if(!task.isSuccessful){
                    toast(task.exception!!.message.toString())
                }
            }
    }

    //인증 붙이기
    override fun onStart() {
        super.onStart()
        firebaseAuth.addAuthStateListener(authStateListener)
    }

    //인증 나가기
   override fun onStop() {
        super.onStop()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }
}
