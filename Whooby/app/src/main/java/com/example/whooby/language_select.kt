package com.example.whooby

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.properties.Delegates


open class language_select : AppCompatActivity() {

    companion object {
        var lang_code=0
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.language_option)

        }

    open fun getter(): Int {
        //lang_code=1
        if(lang_code==1)
        return 1
        else
            return 2
    }


    fun setLanguage(view: View)
    {

        val img = view as Button
        val tappedImage = img.tag.toString().toInt()
        if(tappedImage==1)
            lang_code=1
        else
            lang_code=2

        if(lang_code==1)
        Toast.makeText(this, "Hindi has been selected", Toast.LENGTH_SHORT).show();
        else
        if(lang_code==2)
            Toast.makeText(this, "English has been selected", Toast.LENGTH_SHORT).show();

    }
    }
