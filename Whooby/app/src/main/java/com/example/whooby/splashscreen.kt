package com.example.whooby

/*
#########################################################################################################
# This class is responsible to inflate the splashscreen layout that is the home screen of whooby .      #
# This class also has intents which links to the next activity and the next class.                      #
#                                                                                                       #
#########################################################################################################
*/

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity


@Suppress("DEPRICATION")
class splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        Handler().postDelayed({
            val intent = Intent(this, Opening::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}