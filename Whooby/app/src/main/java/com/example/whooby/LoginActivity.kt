package com.example.whooby

import android.content.Intent
import android.nfc.cardemulation.CardEmulation
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout

class LoginActivity : AppCompatActivity() {

    var isLogIn : Boolean=false

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val tvSingUp =findViewById<TextView>(R.id.singUp)
        val tvLogIn =findViewById<TextView>(R.id.logIn)
        val llSingUp =findViewById<ConstraintLayout>(R.id.singUpLayout)
        val llLogIN =findViewById<ConstraintLayout>(R.id.logInLayout)
        val btnSingIn =findViewById<Button>(R.id.singIn)
        tvSingUp.setOnClickListener {
            tvSingUp.background = resources.getDrawable(R.drawable.switch_trcks,null)
            tvSingUp.setTextColor(resources.getColor(R.color.textColor,null))
            tvLogIn.background = null
            btnSingIn.text = "Sign Up"
            llSingUp.visibility = View.VISIBLE
            llLogIN.visibility = View.GONE
            tvLogIn.setTextColor(resources.getColor(R.color.pinkColor,null))
            isLogIn=true

//            laySize.layout(38, 30, 38)
        }
        tvLogIn.setOnClickListener {
            tvSingUp.background = null
            tvSingUp.setTextColor(resources.getColor(R.color.pinkColor,null))
            tvLogIn.background = resources.getDrawable(R.drawable.switch_trcks,null)
            llSingUp.visibility = View.GONE
            btnSingIn.text = "Login"
            llLogIN.visibility = View.VISIBLE
            tvLogIn.setTextColor(resources.getColor(R.color.textColor,null))
            isLogIn=true
        }
        btnSingIn.setOnClickListener {
            startActivity(Intent(this,Opening::class.java))
        }
    }

    fun userInfo(){
        val regdNo = findViewById<TextView>(R.id.regdNo).toString()
        val logInPass= findViewById<TextView>(R.id.passwords)
        val name = findViewById<TextView>(R.id.nameP).toString()
        val pass = findViewById<TextView>(R.id.pass).toString()
        val cnfmPass = findViewById<TextView>(R.id.confmPass).toString()
        val forgotPass = findViewById<TextView>(R.id.textView5).toString()
    }

    fun getLogIn(): Boolean{
        return isLogIn
    }
    fun end(){
        finish()
    }
}