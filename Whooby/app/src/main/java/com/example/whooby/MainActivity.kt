package com.example.whooby

import CustomAdapter
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener,AdapterView.OnItemSelectedListener {

    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference
    lateinit var sendinfo: SendInfo
    lateinit var textToSpeech: TextToSpeech
    var lang_code=1
    private var speed = 1f
    private var pitch = 1f


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        setLang()
        var btn: ImageView = findViewById(R.id.button)
        var feed: EditText = findViewById(R.id.editText)  // 'feed' store text from textbox
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.getReference("InputText")
        databaseReference.database.setPersistenceEnabled(true)
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
            }
            speed2.setOnClickListener{
                speed -=0.25f
                textToSpeech.setSpeechRate(speed)
            }

            var pitch1=findViewById<Button>(R.id.pitchi)
            var pitch2=findViewById<Button>(R.id.pitchd)
            pitch1.setOnClickListener{
                pitch +=0.25f
                textToSpeech.setPitch(pitch)
            }
            pitch2.setOnClickListener{
                pitch -=0.25f
                textToSpeech.setPitch(pitch)
            }
        }
    }


    private fun addDatatoFirebase(typedtext: String)
    {
        sendinfo.setinputtext((typedtext))
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                databaseReference.push().setValue(sendinfo)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }
        databaseReference.addListenerForSingleValueEvent((postListener))
    }



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

    override fun onNothingSelected(p0: AdapterView<*>?) {
        TODO("Not yet implemented")
    }


}