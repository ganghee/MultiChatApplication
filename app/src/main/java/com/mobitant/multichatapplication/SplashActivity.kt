package com.mobitant.multichatapplication
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.android.synthetic.main.activity_splash.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.startActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var remoteConfig : FirebaseRemoteConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        //상태바를 없애고 화면 전체를 차지하게 설정
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)


        // Remote Config의 설정 값을 이용하여 splash화면에서 대기시간과 splash_message_caps이
        // true인 경우에 '서버 점검 중' 이라는 Dialog가 나온뒤 화면이 꺼진다.
        //
        remoteConfig = FirebaseRemoteConfig.getInstance()
        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setDeveloperModeEnabled(BuildConfig.DEBUG)
            .setMinimumFetchIntervalInSeconds(4200)
            .build()
        remoteConfig.setConfigSettings(configSettings)
        remoteConfig.setDefaults(R.xml.default_config)
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.result
                    Log.d(TAG,"Config params updated: $updated")
                    Toast.makeText(this, "Fetch and activate succeeded",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Fetch failed",
                        Toast.LENGTH_SHORT).show()
                }
                displayWelcomeMessage()
            }
    }
    private fun displayWelcomeMessage() {

        val splash_background = remoteConfig.getString("splash_background")
        val caps = remoteConfig.getBoolean("splash_message_caps")
        val message = remoteConfig.getString("splash_message")

        splashactivity_linearlayout.setBackgroundColor(Color.parseColor(splash_background))

        if(caps){
            val builder = alert(message)
            builder.positiveButton("YES") {
                finish()
            }
            builder.show()
        }else{
            startActivity<LoginActivity>()
        }
    }

    companion object {

        private const val TAG = "SplashActivity"

    }
}
