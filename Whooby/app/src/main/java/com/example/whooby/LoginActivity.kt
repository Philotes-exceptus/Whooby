package com.example.whooby

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//This class is responsible for user login into the app using firebase authentication
class LoginActivity : AppCompatActivity() {



    private lateinit var userName: TextInputEditText
    private lateinit var userPassword: TextInputEditText
    private lateinit var userRegNo: TextInputEditText
    private val baseUrl="https://whooby-server.onrender.com"
    private lateinit var retrofitInstance : Auth_Interface

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        setContentView(R.layout.login_page)

        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitInstance = retrofit.create(Auth_Interface::class.java)


        //mAuth= FirebaseAuth.getInstance()
        userName = findViewById(R.id.name)
        userPassword = findViewById(R.id.password)
        userRegNo = findViewById(R.id.regNo)

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
        val email: String = userName.text.toString()
        val password: String = userPassword.text.toString()
        val regNo: String = userRegNo.text.toString()


        if (TextUtils.isEmpty(email)) {
            Toast.makeText(
                applicationContext,
                "Please enter your email!!",
                Toast.LENGTH_LONG
            )
                .show()
            return
        }
        if (TextUtils.isEmpty(regNo)) {
            Toast.makeText(
                applicationContext,
                "Please enter Registration no!!",
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

        val map: HashMap<String, String> = HashMap()
        map["reg_no"] = regNo
        map["password"] = password
        map["email"] = email


        retrofitInstance.executeLogin(map)?.enqueue(object : Callback<LoginResult?> {
            override fun onResponse(call: Call<LoginResult?>?, response: Response<LoginResult?>) {
                if (response.code() == 200) {

                    val homeIntent = Intent(this@LoginActivity, Home::class.java)
                    startActivity(homeIntent)

                } else if (response.code() == 404) {

                    invalid()
                }
            }

            override fun onFailure(call: Call<LoginResult?>?, t: Throwable) {

                invalid()

            }
        })

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

