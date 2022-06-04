package com.example.whooby

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener,
    AdapterView.OnItemSelectedListener {

    lateinit var textToSpeech: TextToSpeech
    var lang_code=1
    private var speed = 1f
    private var pitch = 1f
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setLang()
        var btn: ImageView = findViewById(R.id.button)
        var feed: EditText = findViewById(R.id.editText)
        textToSpeech= TextToSpeech(this,this)
        btn.setOnClickListener {
            var text: String = feed.text.toString()
            textToSpeech.speak(text,TextToSpeech.QUEUE_FLUSH,null)

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