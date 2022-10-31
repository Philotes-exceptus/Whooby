package com.example.whooby

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

/*
#########################################################################################################
# This class is responsible to take in text as input and use text to speech to convert it to speech .   #
# This class also sends this to the firebase and retrieves it on the other end                          #
#                                                                                                       #
#########################################################################################################
*/

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener,
    AdapterView.OnItemSelectedListener {

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    private lateinit var mAuth: FirebaseAuth
    lateinit var sendinfo: SendInfo
    lateinit var textToSpeech: TextToSpeech
    var lang_code = 1
    private var speed = 1f
    private var pitch = 1f
    private lateinit var userList: ArrayList<User>
    lateinit var visualiser: LottieAnimationView


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT


        setLang()
        val btn: ImageView = findViewById(R.id.button)
        val feed: EditText = findViewById(R.id.editText)  // 'feed' store text from textbox
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference()
        userList = ArrayList()
        sendinfo = SendInfo()
        visualiser = findViewById<LottieAnimationView>(R.id.visualizer)
        visualiser.visibility = View.INVISIBLE

        textToSpeech = TextToSpeech(this, this)
        btn.setOnClickListener {
            visualiser.visibility = View.VISIBLE
            val text: String = feed.text.toString()
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null)
            val typedtext = feed.getText().toString()
            addDatatoFirebase(typedtext)
            val speed1 = findViewById<Button>(R.id.speedup)
            val speed2 = findViewById<Button>(R.id.slowdown)
            speed1.setOnClickListener {
                speed += 0.25f
                textToSpeech.setSpeechRate(speed)
                Toast.makeText(this, "speed increased", Toast.LENGTH_LONG).show()
            }
            speed2.setOnClickListener {
                speed -= 0.25f
                textToSpeech.setSpeechRate(speed)
                Toast.makeText(this, "speed decreased", Toast.LENGTH_LONG).show()
            }

            val pitch1 = findViewById<Button>(R.id.pitchi)
            val pitch2 = findViewById<Button>(R.id.pitchd)
            pitch1.setOnClickListener {
                pitch += 0.25f
                textToSpeech.setPitch(pitch)
                Toast.makeText(this, "pitch increase", Toast.LENGTH_LONG).show()
            }
            pitch2.setOnClickListener {
                pitch -= 0.25f
                textToSpeech.setPitch(pitch)
                Toast.makeText(this, "pitch decrease", Toast.LENGTH_LONG).show()
            }

        }


    }

    //this function adds data to firebase
    private fun addDatatoFirebase(typedtext: String) {
        sendinfo.setinputtext((typedtext))
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                databaseReference.push().setValue(sendinfo)

            }

            override fun onCancelled(error: DatabaseError) {

            }

        }
        databaseReference.addListenerForSingleValueEvent((postListener))
    }


    fun setLang() {
        val spinner: Spinner = findViewById(R.id.spinner)
        spinner.onItemSelectedListener = this
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.language,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }

    //initiates the TTS engine
    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            var res: Int = 1
            Log.d("mesage", "" + lang_code)
            if (lang_code == 1)
                res = textToSpeech.setLanguage(Locale("hin"))
            if (lang_code == 0)
                res = textToSpeech.setLanguage(Locale.US)


            if (res == TextToSpeech.LANG_MISSING_DATA || res == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "language not supported", Toast.LENGTH_LONG).show()
            }

        }
    }

    //language selector for the tts
    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
        lang_code = position
        if (lang_code == 1)
            Toast.makeText(this, "Hindi has been selected", Toast.LENGTH_SHORT).show()
        else
            if (lang_code == 0)
                Toast.makeText(this, "English has been selected", Toast.LENGTH_SHORT).show()


        var res: Int = 1
        Log.d("mesage", "" + lang_code)
        if (lang_code == 1)
            res = textToSpeech.setLanguage(Locale("hin"))
        if (lang_code == 0)
            res = textToSpeech.setLanguage(Locale.US)

    }


    var pass: Int = 0

    @JvmName("getPass1")
    fun getPass(): Int {
        if (lang_code == 1)
            pass = 1
        if (lang_code == 0)
            pass = 0
        return pass
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.empty, R.anim.zoom_out)
    }


}