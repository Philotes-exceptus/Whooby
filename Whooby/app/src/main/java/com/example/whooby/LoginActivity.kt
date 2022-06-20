package com.example.whooby

import android.content.Intent
import android.content.pm.ActivityInfo
import android.nfc.cardemulation.CardEmulation
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    var isLogIn : Boolean=false
    private lateinit var lregdno: com.google.android.material.textfield.TextInputLayout
    private lateinit var sregdno: com.google.android.material.textfield.TextInputLayout
    private lateinit var lpassw: com.google.android.material.textfield.TextInputLayout
    private lateinit var spassw: com.google.android.material.textfield.TextInputLayout
    private lateinit var spasswcon: com.google.android.material.textfield.TextInputLayout
    private lateinit var btnlogin: Button
    private lateinit var mAuth: FirebaseAuth


    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        val tvSingUp =findViewById<TextView>(R.id.singUp)
        val tvLogIn =findViewById<TextView>(R.id.logIn)
        val llSingUp =findViewById<ConstraintLayout>(R.id.singUpLayout)
        val llLogIN =findViewById<ConstraintLayout>(R.id.logInLayout)
        val btnSingIn =findViewById<Button>(R.id.singIn)
        val lregdno = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.passwords)
        val lpassw = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.eMail)
        var sregdno = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.eMails)
        var spassw = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.pass)
        var spasswcon = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.confmPass)
//        userInfo()


        tvSingUp.setOnClickListener {
            tvSingUp.background = resources.getDrawable(R.drawable.switch_trcks,null)
            tvSingUp.setTextColor(resources.getColor(R.color.textColor,null))
            tvLogIn.background = null
            btnSingIn.text = "Sign Up"
            llSingUp.visibility = View.VISIBLE
            llLogIN.visibility = View.GONE
            tvLogIn.setTextColor(resources.getColor(R.color.pinkColor,null))
//            isLogIn=true

            //val regdNo = findViewById<TextView>(R.id.regdNo).toString()
//            val logInPass= findViewById<TextView>(R.id.passwords).toString()





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
//            isLogIn=true

            val lregnot = lregdno.text.toString()
            val lpasst = lpassw.text.toString()

            login(lregnot , lpasst)
        }
        btnSingIn.setOnClickListener {
            signup()

        }
    }

//    fun userInfo(){
//        val regdNo = findViewById<TextView>(R.id.regdNo).toString()
//        val logInPass= findViewById<TextView>(R.id.passwords)
//        val name = findViewById<TextView>(R.id.nameP).toString()
//        val pass = findViewById<TextView>(R.id.pass)
//        val cnfmPass = findViewById<TextView>(R.id.confmPass)
//    }


    private fun login(lregnot: String,lpasswt: String) {
        mAuth.signInWithEmailAndPassword(lregnot,lpasswt)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    isLogIn = true
                } else {
                    Toast.makeText(this@LoginActivity, "EoororrrLoginn", Toast.LENGTH_SHORT).show()
                }
            }
    }




    private  fun signup() {
        var sregdno = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.eMails)
        var spassw = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.pass)
        var spasswcon = findViewById<com.google.android.material.textfield.TextInputEditText>(R.id.confmPass)
        val sregdnot = sregdno.text.toString()
        val spasswt = spassw.text.toString()
        val spasswcont = spasswcon.text.toString()
        if (sregdnot.isBlank() || spasswt.isBlank() || spasswcont.isBlank()) {
            Toast.makeText(this, "Email and Password can't be blank", Toast.LENGTH_SHORT).show()
            isLogIn = false
            return
        }

        if (spasswt != spasswcont) {
            Toast.makeText(this, "Password and Confirm Password do not match", Toast.LENGTH_SHORT)
                .show()
            isLogIn = false
            val intent = Intent(this, LoginActivity::class.java )
            startActivity(intent)
            return
        }

        mAuth.createUserWithEmailAndPassword(sregdnot,spasswt)
            .addOnCompleteListener(this) { task ->
                if(task.isSuccessful) {
                    Toast.makeText(this, "Successfully Singed Up", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this,Opening::class.java))
                    overridePendingTransition(R.anim.zoom_in,R.anim.empty)
                    finish()
                    isLogIn = true
                } else {
                    Toast.makeText(this@LoginActivity, "EoororrrSignupp", Toast.LENGTH_SHORT).show()
                    isLogIn = false
                }
            }
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