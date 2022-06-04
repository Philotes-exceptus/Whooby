package com.example.whooby

import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener,
    AdapterView.OnItemSelectedListener {

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