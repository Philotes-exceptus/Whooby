package com.example.whooby

import android.os.Build
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


//This class is used to fetch blog data from Enigma blogs using Enigma api
class BlogEdu : AppCompatActivity() {

    private lateinit var arrow: ImageButton
    private lateinit var hiddenView: LinearLayout
    private lateinit var cardView: CardView

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setContentView(R.layout.blogs)

        val enigmaApi = RetrofitHelper.getInstance().create(EnigmaApi::class.java)


        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)

        // ArrayList of class ItemsViewModel
        val data = ArrayList<BlogViewModel>()

        GlobalScope.launch {

            val result = enigmaApi.getBlog()
            //Log.d("sagar: ", result.body().toString())
            val ts = result.body()?.blogs
            if (ts != null) {
                for (item in ts)
                {
                    Log.d("dtest",item.title)
                    if(item.title!="")
                    data.add(BlogViewModel(R.drawable.enigma, item.title))
                    //Log.d("test", item.data.blocks[1].data.meta.description)
                }

//                    Log.d("test", result.body()?.blogs.toString())
            }

        }




        Thread.sleep(5000)

        // This will pass the ArrayList to our Adapter
        val adapter = BlogAdapter(data,recyclerview)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter


    }

}
