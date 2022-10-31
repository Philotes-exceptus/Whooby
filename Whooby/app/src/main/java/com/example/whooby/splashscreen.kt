package com.example.whooby

/*
#########################################################################################################
# This class is responsible to inflate the splashscreen layout that is the home screen of whooby .      #
# This class also has intents which links to the next activity and the next class.                      #
#                                                                                                       #
#########################################################################################################
*/

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity


@Suppress("DEPRICATION")
class splashscreen : AppCompatActivity() {
    @SuppressLint("SourceLockedOrientationActivity", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)


        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val videoview = findViewById<VideoView>(R.id.intro)
        val path = "android.resource://" + packageName + "/" + R.raw.welcome

        videoview.setVideoURI(Uri.parse(path))
        videoview.start()

        Handler().postDelayed({
            Intent(this, LoginActivity::class.java).also {
                startActivity(it)
                finish()
                overridePendingTransition(R.anim.right_left1, R.anim.right_left2)
            }
        }, 4000)


    }
}