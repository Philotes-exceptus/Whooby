package com.example.whooby

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
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