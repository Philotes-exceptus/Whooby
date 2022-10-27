package com.example.whooby

import android.R.attr.country
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONObject


//This class is used to fetch blog data from Enigma blogs using Enigma api
class BlogEdu : AppCompatActivity() {

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.blogs)

        val enigmaApi = RetrofitHelper.getInstance().create(EnigmaApi::class.java)

        GlobalScope.launch {
            val result = enigmaApi.getBlog()
            Log.d("sagar: ", result.body().toString())

            //declaring the json string path for individual parameters
            val title = ""

            val timeStamp = ""
            val imageUrl = ""
            val blogText = ""
        }

    }

}
