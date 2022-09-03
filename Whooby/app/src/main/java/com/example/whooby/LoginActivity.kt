package com.example.whooby

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

//This class is responsible for user login into the app using firebase authentication
class LoginActivity : AppCompatActivity() {


    private lateinit var mAuth: FirebaseAuth
    lateinit private var userName: TextInputEditText
    lateinit private var userPassword: TextInputEditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getWindow().setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.login_page)

        //mAuth= FirebaseAuth.getInstance()
        mAuth = Firebase.auth
        userName = findViewById(R.id.name)
        userPassword = findViewById(R.id.password)

        val loginButton = findViewById<Button>(R.id.LoginButton)
        loginButton.setOnClickListener(View.OnClickListener { previousUser() })

        val new_signup = findViewById<TextView>(R.id.newUser)
        new_signup.setOnClickListener(View.OnClickListener {
            val signUpIntent = Intent(this, SignUp::class.java)
            startActivity(signUpIntent)
        })


    }

    fun login(view: View) {
        val homeActivityIntent = Intent(this, Home::class.java)
        startActivity(homeActivityIntent)
    }

    //This function is to check if there already exist a user for our app
    private fun previousUser() {
        val name: String
        val password: String
        name = userName.text.toString()
        password = userPassword.text.toString()


        if (TextUtils.isEmpty(name)) {
            Toast.makeText(
                applicationContext,
                "Please enter your email!!",
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

        mAuth.signInWithEmailAndPassword(name, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val user = mAuth.currentUser
                    val homeIntent = Intent(this, Home::class.java)
                    startActivity(homeIntent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Not a registered member",
                        Toast.LENGTH_SHORT
                    ).show()
                    invalid()

                }
            }
    }


    //if the user doesn't exist then the app notifies with an error entry symbol
    fun invalid() {
        //inflates the about section in form of a custom toast layout
        val layout = layoutInflater.inflate(R.layout.wrong, findViewById(R.id.root))

        val wrong = layout.findViewById<LottieAnimationView>(R.id.invalidUser)
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        //starts the animation
        wrong.startAnimation(animation)
        val myToast = Toast(applicationContext)

        myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        myToast.view = layout//setting the view of custom toast layout

        myToast.duration = Toast.LENGTH_SHORT
        myToast.show()
    }

}

