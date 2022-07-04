package com.example.whooby

import android.content.Intent
import android.content.pm.ActivityInfo
import android.nfc.cardemulation.CardEmulation
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout

class LoginActivity : AppCompatActivity() {

    var isLogIn : Boolean=false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        val tvsignUp =findViewById<TextView>(R.id.signUp)
        val tvLogIn =findViewById<TextView>(R.id.logIn)
        val llsignUp =findViewById<ConstraintLayout>(R.id.signUpLayout)
        val llLogIN =findViewById<ConstraintLayout>(R.id.logInLayout)
        val btnSignIn =findViewById<Button>(R.id.signInBt)
        val switchLay =findViewById<ConstraintLayout>(R.id.constraintLayout)
        val signInTxt =findViewById<TextView>(R.id.txtSignIn)
        val signUpTxt =findViewById<TextView>(R.id.txtSignUp)
        val btnSignUp =findViewById<Button>(R.id.signUpBt)

        tvsignUp.setOnClickListener {
            tvsignUp.background = resources.getDrawable(R.drawable.switch_trcks,null)
            switchLay.visibility = View.GONE
            btnSignIn.visibility = View.GONE
            tvsignUp.setTextColor(resources.getColor(R.color.textColor,null))
            tvLogIn.background = null
            llsignUp.visibility = View.VISIBLE
            llLogIN.visibility = View.GONE
            tvLogIn.setTextColor(resources.getColor(R.color.pinkColor,null))
            isLogIn=true

        }
        signInTxt.setOnClickListener {
            tvsignUp.background = null
            switchLay.visibility = View.VISIBLE
            btnSignIn.visibility = View.VISIBLE
            tvsignUp.setTextColor(resources.getColor(R.color.pinkColor,null))
            tvLogIn.background = resources.getDrawable(R.drawable.switch_trcks,null)
            llsignUp.visibility = View.GONE
            llLogIN.visibility = View.VISIBLE
            tvLogIn.setTextColor(resources.getColor(R.color.textColor,null))
            isLogIn=true
        }
        signUpTxt.setOnClickListener {
            tvsignUp.background = resources.getDrawable(R.drawable.switch_trcks,null)
            switchLay.visibility = View.GONE
            btnSignIn.visibility = View.GONE
            tvsignUp.setTextColor(resources.getColor(R.color.textColor,null))
            tvLogIn.background = null
            llsignUp.visibility = View.VISIBLE
            llLogIN.visibility = View.GONE
            tvLogIn.setTextColor(resources.getColor(R.color.pinkColor,null))
            isLogIn=true
        }
        tvLogIn.setOnClickListener {
            tvsignUp.background = null
            tvsignUp.setTextColor(resources.getColor(R.color.pinkColor,null))
            tvLogIn.background = resources.getDrawable(R.drawable.switch_trcks,null)
            llsignUp.visibility = View.GONE
            llLogIN.visibility = View.VISIBLE
            tvLogIn.setTextColor(resources.getColor(R.color.textColor,null))
            isLogIn=true
        }
        btnSignIn.setOnClickListener {
            startActivity(Intent(this,Opening::class.java))
            Toast.makeText(this,"Signed in", Toast.LENGTH_SHORT).show()
            overridePendingTransition(R.anim.zoom_in,R.anim.empty)
        }
        btnSignUp.setOnClickListener{
            tvsignUp.background = null
            switchLay.visibility = View.VISIBLE
            btnSignIn.visibility = View.VISIBLE
            tvsignUp.setTextColor(resources.getColor(R.color.pinkColor,null))
            tvLogIn.background = resources.getDrawable(R.drawable.switch_trcks,null)
            llsignUp.visibility = View.GONE
            llLogIN.visibility = View.VISIBLE
            tvLogIn.setTextColor(resources.getColor(R.color.textColor,null))
            isLogIn=true
            Toast.makeText(this,"Account created", Toast.LENGTH_SHORT).show();

        }
    }

    fun userInfo(){
        val loginRegd = findViewById<TextView>(R.id.regdNum).toString()
        val loginEmail = findViewById<TextView>(R.id.email).toString()
        val logInPass= findViewById<TextView>(R.id.password)
        val name = findViewById<TextView>(R.id.nameP).toString()
        val regdNo = findViewById<TextView>(R.id.regdNo).toString()
        val email = findViewById<TextView>(R.id.mail).toString()
        val pass = findViewById<TextView>(R.id.pass)
        val cnfmPass = findViewById<TextView>(R.id.confmPass)
    }

    fun getLogIn(): Boolean{
        return isLogIn
    }
    fun end(){
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}