package com.example.whooby

import android.app.KeyguardManager
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import android.os.Bundle
import android.os.CancellationSignal
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import java.util.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener,AdapterView.OnItemSelectedListener {
    private var cancellationSignal: CancellationSignal? = null
    private val authenticationCallback: BiometricPrompt.AuthenticationCallback
        get() = @RequiresApi(Build.VERSION_CODES.P)
        object : BiometricPrompt.AuthenticationCallback() {
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                super.onAuthenticationError(errorCode, errString)
                notifyUser("Authentication Error : $errString")
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult?) {
                super.onAuthenticationSucceeded(result)
                notifyUser("Authentication Succeeded")

                // or start a new Activity

            }
        }
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var sendinfo: SendInfo
    lateinit var textToSpeech: TextToSpeech
    var lang_code=1
    private var speed = 1f
    private var pitch = 1f
    var isCalled: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        setLang()
        var btn: ImageView = findViewById(R.id.button)
        var feed: EditText = findViewById(R.id.editText)  // 'feed' store text from textbox
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("InputText")
        //databaseReference.database.setPersistenceEnabled(true)
        sendinfo = SendInfo()

        textToSpeech= TextToSpeech(this,this)
        btn.setOnClickListener {
            var text: String = feed.text.toString()
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null)
            var typedtext = feed.getText().toString()
            addDatatoFirebase(typedtext)
            var speed1=findViewById<Button>(R.id.speedup)
            var speed2 =findViewById<Button>(R.id.slowdown)
            speed1.setOnClickListener{
                speed +=0.25f
                textToSpeech.setSpeechRate(speed)
                Toast.makeText(this,"speed increased",Toast.LENGTH_LONG).show()
            }
            speed2.setOnClickListener{
                speed -=0.25f
                textToSpeech.setSpeechRate(speed)
                Toast.makeText(this,"speed decreased",Toast.LENGTH_LONG).show()
            }

            var pitch1=findViewById<Button>(R.id.pitchi)
            var pitch2=findViewById<Button>(R.id.pitchd)
            pitch1.setOnClickListener{
                pitch +=0.25f
                textToSpeech.setPitch(pitch)
                Toast.makeText(this,"pitch increase",Toast.LENGTH_LONG).show()
            }
            pitch2.setOnClickListener{
                pitch -=0.25f
                textToSpeech.setPitch(pitch)
                Toast.makeText(this,"pitch decrease",Toast.LENGTH_LONG).show()
            }
        }

       // val animationDrawable = findViewById<ConstraintLayout>(R.id.liveWp).background as AnimationDrawable
      //  animationDrawable.setEnterFadeDuration(2000)
      //  animationDrawable.setExitFadeDuration(4000)
      //  animationDrawable.start()

    }
            // it will be called when authentication is cancelled by the user
            private fun getCancellationSignal(): CancellationSignal {
                cancellationSignal = CancellationSignal()
                cancellationSignal?.setOnCancelListener {
                    notifyUser("Authentication was Cancelled by the user")
                }
                return cancellationSignal as CancellationSignal
            }

            // it checks whether the app the app has fingerprint permission
            @RequiresApi(Build.VERSION_CODES.M)
            private fun checkBiometricSupport(): Boolean {
                val keyguardManager = getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
                if (!keyguardManager.isDeviceSecure) {
                    notifyUser("Fingerprint authentication has not been enabled in settings")
                    return false
                }
                if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_BIOMETRIC) != PackageManager.PERMISSION_GRANTED) {
                    notifyUser("Fingerprint Authentication Permission is not enabled")
                    return false
                }
                return if (packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)) {
                    true
                } else true
            }

            // this is a toast method which is responsible for showing toast
            // it takes a string as parameter
            private fun notifyUser(message: String) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }





    private fun addDatatoFirebase(typedtext: String)
    {
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


//    private fun getdata() {
//
//        val postListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val value = dataSnapshot.getValue<String>()
//
//
//            }
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        }
//        databaseReference.addValueEventListener((postListener))
//    }
//


    fun setLang()
    {
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



    override fun onInit(status: Int) {
        if(status== TextToSpeech.SUCCESS)
            {
                var res :Int=1
                Log.d("mesage",""+lang_code)
                if(lang_code==1)
                res = textToSpeech.setLanguage(Locale("hin"))
                    if(lang_code==0)
                    res = textToSpeech.setLanguage(Locale.US)


                if(res==TextToSpeech.LANG_MISSING_DATA || res==TextToSpeech.LANG_NOT_SUPPORTED)
                {
                    Toast.makeText(this,"language not supported",Toast.LENGTH_LONG).show()
                }

        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, view: View?, position: Int, id: Long) {
        lang_code=position
        if(lang_code ==1)
            Toast.makeText(this, "Hindi has been selected", Toast.LENGTH_SHORT).show()
        else
            if(lang_code ==0)
                Toast.makeText(this, "English has been selected", Toast.LENGTH_SHORT).show()



            var res :Int=1
            Log.d("mesage",""+lang_code)
            if(lang_code==1)
                res = textToSpeech.setLanguage(Locale("hin"))
            if(lang_code==0)
                res = textToSpeech.setLanguage(Locale.US)

    }


    var pass: Int=0
    @JvmName("getPass1")
    fun getPass(): Int {
        if(lang_code==1)
            pass=1
        if(lang_code==0)
            pass=0
        return pass
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.empty,R.anim.zoom_out)
    }


}