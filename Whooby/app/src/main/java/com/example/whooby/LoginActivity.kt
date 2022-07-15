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
import com.google.firebase.database.DatabaseReference
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    var isLogIn : Boolean=false
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDatabaseReference: DatabaseReference
    private lateinit var loginRegd : TextView
    //    val loginEmail = findViewById<TextView>(R.id.email).toString()
    private lateinit var loginEmail : TextView
    private lateinit var logInPass : TextView
    //    val logInPass= findViewById<TextView>(R.id.password).toString()
//    val name = findViewById<TextView>(R.id.nameP).toString()
    private lateinit var name : TextView
    //    val regdNo = findViewById<TextView>(R.id.regdNo).toString()
    private lateinit var regdNo : TextView
    //    val email = findViewById<TextView>(R.id.mail).toString()
    private lateinit var email : TextView
    //    val pass = findViewById<TextView>(R.id.pass).toString()
    private lateinit var pass : TextView
    //    val cnfmPass = findViewById<TextView>(R.id.confmPass).toString()
    private lateinit var cnfmpass : TextView

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
        loginEmail = findViewById<TextView>(R.id.email)
        loginRegd = findViewById<TextView>(R.id.regdNum)
        logInPass = findViewById<TextView>(R.id.password)
        name = findViewById<TextView>(R.id.nameP)
        regdNo = findViewById<TextView>(R.id.regdNo)
        email = findViewById<TextView>(R.id.mail)
        pass = findViewById<TextView>(R.id.pass)
        cnfmpass = findViewById<TextView>(R.id.confmPass)
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

        //Sign In related
        btnSignIn.setOnClickListener {
            startActivity(Intent(this,Opening::class.java))
            val lgregd = loginRegd.text.toString()
            val lgnemail = loginEmail.text.toString()
            val logInPass = email.text.toString()
            login(lgregd, lgnemail, logInPass)
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

            val lgregd = regdNo.text.toString()
            val lgname = name.text.toString()
            val lgemail = email.text.toString()
            val lgpass = pass.text.toString()
            signup(lgregd,lgname,lgemail,lgpass)
            Toast.makeText(this,"Account created", Toast.LENGTH_SHORT).show();

        }
    }

    //   fun userInfo(){
//        val loginRegd = findViewById<TextView>(R.id.regdNum).toString()
//        val loginEmail = findViewById<TextView>(R.id.email).toString()
//        val logInPass= findViewById<TextView>(R.id.password)
//        val name = findViewById<TextView>(R.id.nameP).toString()
//        val regdNo = findViewById<TextView>(R.id.regdNo).toString()
//        val email = findViewById<TextView>(R.id.mail).toString()
//        val pass = findViewById<TextView>(R.id.pass)
//        val cnfmPass = findViewById<TextView>(R.id.confmPass)
    //   }

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


    private fun login(regd: String, email:String,password:String) {


        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                    Toast.makeText(this@LoginActivity, "Welcome", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "User does not exist", Toast.LENGTH_SHORT).show()
                }
            }
    }


    private fun signup(regd: String, name:String, email:String,password:String) {
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserTODatabase(regd,name,email, mAuth.currentUser?.uid!!)
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                    Toast.makeText(this@LoginActivity, "Successfully Signed up. . . ", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@LoginActivity, "Error Signing up. . . ", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun addUserTODatabase(regd: String, name: String, email: String, uid: String){
        mDatabaseReference = FirebaseDatabase.getInstance().getReference()

        mDatabaseReference.child("User").child(uid).setValue(User(regd,name,email,uid))
    }
}