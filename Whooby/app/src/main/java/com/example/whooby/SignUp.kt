package com.example.whooby

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//This class is used for new user signup
class SignUp  : AppCompatActivity()  {

    private lateinit var mAuth: FirebaseAuth
    lateinit private var userName : TextInputEditText
    lateinit private var userPassword : TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.signup_page)

        mAuth = Firebase.auth
        userName = findViewById(R.id.signup_name)
        userPassword = findViewById(R.id.signup_password)

        val signUpButton=findViewById<Button>(R.id.SignUpButton)
        signUpButton.setOnClickListener(View.OnClickListener { registerNewUser() })

    }

    private fun registerNewUser() {

        val name: String
        val password: String
        name = userName.text.toString()
        password = userPassword.text.toString()


        if (TextUtils.isEmpty(name)) {
            Toast.makeText(
                applicationContext,
                "Please enter your name!!",
                Toast.LENGTH_LONG
            )
                .show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(
                applicationContext,
                "Please enter password!!",
                Toast.LENGTH_LONG
            )
                .show()
            return
        }

        // create new user or register new user
        mAuth.createUserWithEmailAndPassword(name, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        applicationContext,
                        "Registration successful!",
                        Toast.LENGTH_LONG
                    )
                        .show()

                    signed()


                    // if the user created intent to login activity
                    val intent = Intent(
                        this@SignUp,
                        LoginActivity::class.java
                    )
                    startActivity(intent)
                } else {

                    // Registration failed
                    Toast.makeText(
                        applicationContext, "Registration failed!!"
                                + " Please try again later",
                        Toast.LENGTH_LONG
                    )
                        .show()

                }
            }
    }


    fun signed() {


        //inflates the about section in form of a custom toast layout
        val layout = layoutInflater.inflate(R.layout.signed, findViewById(R.id.root))

        val thanks = layout.findViewById<LottieAnimationView>(R.id.signUpVerification)
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        //starts the animation
        thanks.startAnimation(animation)
        val myToast = Toast(applicationContext)

        myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        myToast.view = layout//setting the view of custom toast layout

        myToast.duration=Toast.LENGTH_LONG
        myToast.show()


    }



}