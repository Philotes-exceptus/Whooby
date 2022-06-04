package com.example.whooby

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.properties.Delegates
import com.example.whooby.language_select

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener{

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
        var tappedImage : Int
        val obj = language_select()
        tappedImage = obj.getter()
        if(tappedImage==1)
            lang_code=1
        else
            lang_code=2

        Log.d("mesage",""+lang_code)

    }



    override fun onInit(status: Int) {
        if(status== TextToSpeech.SUCCESS)
            {
                val res :Int
                Log.d("mesage",""+lang_code)
                if(this.lang_code==1)
                res = textToSpeech.setLanguage(Locale("hin"));
                else
                    res = textToSpeech.setLanguage(Locale.US);

                if(res==TextToSpeech.LANG_MISSING_DATA || res==TextToSpeech.LANG_NOT_SUPPORTED)
                {
                    Toast.makeText(this,"language not supported",Toast.LENGTH_LONG).show()
                }

        }
    }


}