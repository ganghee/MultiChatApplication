package com.mobitant.multichatapplication

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.mobitant.multichatapplication.model.UserModel
import kotlinx.android.synthetic.main.activity_signup.*
import org.jetbrains.anko.sdk27.coroutines.onClick

class SignupActivity : AppCompatActivity() {

    private lateinit var remoteConfig : FirebaseRemoteConfig


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        //FirebaseRemoteConfig는 안드로이드 코드상이 아니라 Firebase의 Remote Config에서
        //key와 value를 설정하여 원격으로 데이터를 지정할 수 있다.
        //key: splash_background value:[색상값] 을 설정하여 툴바와 버튼의 색상을 지정할 수 있도록 하였다.


        remoteConfig = FirebaseRemoteConfig.getInstance()
        val splash_background:String = remoteConfig.getString(getString(R.string.rc_color))
        window.statusBarColor = Color.parseColor(splash_background)
        signupActivity_button_signup.setBackgroundColor(Color.parseColor(splash_background))

        //회원가입 버튼 클릭시 이벤트
        //email, name, password가 null이 아닐 때 FirebaseAuth의 인증을 한다.
        //userModel의 name에 UI로부터 입력받은 데이터 삽입
        //uid는 일종의 주민등록 번호로 FirebaseDatabase에 저장한다.

        signupActivity_button_signup.onClick {
            if(signupActivity_edittext_email.text.toString()=="" ||
                signupActivity_edittext_name.text.toString()==""||
                signupActivity_edittext_password.text.toString()=="")
            { return@onClick }

            FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(
                signupActivity_edittext_email.text.toString(),
                signupActivity_edittext_password.text.toString())
                .addOnCompleteListener(this@SignupActivity){task->
                    val userModel = UserModel()
                    userModel.userName = signupActivity_edittext_name!!.text.toString()

                    val uid = task.result!!.user.uid
                    FirebaseDatabase.getInstance().reference.child("users").child(uid).setValue(userModel)
                }


        }



    }

}
