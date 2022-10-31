package com.example.whooby

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//This class is used for new user signup
class SignUp : AppCompatActivity() {

    private lateinit var userEmail: TextInputEditText
    private lateinit var userPassword: TextInputEditText
    private lateinit var userRegNo: TextInputEditText
    private val baseUrl="http://10.0.2.2:3000/"
    private lateinit var retrofitInstance : Auth_Interface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.signup_page)

        val retrofit = Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofitInstance = retrofit.create(Auth_Interface::class.java)


        userEmail = findViewById(R.id.signup_email)
        userPassword = findViewById(R.id.signup_password)
        userRegNo = findViewById(R.id.signup_regNo)

        val signUpButton = findViewById<Button>(R.id.SignUpButton)
        signUpButton.setOnClickListener { registerNewUser() }

    }

    private fun registerNewUser() {

        val email: String = userEmail.text.toString()
        val password: String = userPassword.text.toString()
        val reg_No: String = userRegNo.text.toString()

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(
                applicationContext,
                "Please enter your name!!",
                Toast.LENGTH_LONG
            )
                .show()
            return
        }
        if (TextUtils.isEmpty(reg_No)) {
            Toast.makeText(
                applicationContext,
                "Please enter your registration no!!",
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
        val map: HashMap<String, String> = HashMap()
        map["email"] = email
        map["reg_no"] = reg_No
        map["password"] = password

        retrofitInstance.executeSignup(map)?.enqueue(object : Callback<Void?> {
            override fun onResponse(
                call: Call<Void?>,
                response: Response<Void?>
            ) {
                if (response.code() == 200) {
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
                } else if (response.code() == 400) {
                    Toast.makeText(
                        this@SignUp,
                        "Already registered", Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<Void?>, t: Throwable) {
                // Registration failed
                Toast.makeText(
                    applicationContext, "Registration failed!!"
                            + " Please try again later",
                    Toast.LENGTH_LONG
                )
                    .show()
            }
        })

    }


    fun signed() {


        //inflates the about section in form of a custom toast layout
        val layout = layoutInflater.inflate(R.layout.signed, findViewById(R.id.root))

        val thanks = layout.findViewById<LottieAnimationView>(R.id.signUpVerification)
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        //starts the animation
        thanks.startAnimation(animation)
        val myToast = Toast(applicationContext)

        myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        myToast.view = layout//setting the view of custom toast layout

        myToast.duration = Toast.LENGTH_LONG
        myToast.show()


    }

}