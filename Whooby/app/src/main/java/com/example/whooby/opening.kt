package com.example.whooby

/*
#########################################################################################################
# This class is responsible to inflate 3 different layouts that is the Whooby feeds , Whooby reads and  #
# developers info. The function  abt_author gives info about the developers. The function mainApp takes #
# user to the Whooby feeds sections where user enters text. The function anchor_Whooby takes user to    #
# section where Whooby reads the messages fed to it.                                                    #
#########################################################################################################
*/

import CustomAdapter
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class Opening : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        val whooby_feed = findViewById<Button>(R.id.whooby_button)
        val author = findViewById<ImageView>(R.id.developer)
        val whooby_reads = findViewById<Button>(R.id.whooby_reads)

        //sets the buttons transparency to invisible
        whooby_feed.setAlpha(0f);
        author.setAlpha(0f)
        whooby_reads.setAlpha(0f)

        //fixes the different whooby button's animation origin always at a constant coordinate
        whooby_feed.setTranslationY(50F)
        author.setTranslationY(50F)
        whooby_reads.setTranslationY(50F)

        //Animate the alpha value to 1f and set duration as 1.5 secs. This is applied to button animation
        whooby_feed.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).setDuration(1100);
        author.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).setDuration(1100);
        whooby_reads.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).setDuration(1100);

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        val whooby_feed = findViewById<Button>(R.id.whooby_button)
        val whooby_reads = findViewById<Button>(R.id.whooby_reads)
        val author = findViewById<ImageView>(R.id.developer)

        whooby_feed.setAlpha(0f);
        whooby_reads.setAlpha(0f);
        author.setAlpha(0f);

        //Animate the alpha value to 1f and set duration as 1.5 secs.
        whooby_feed.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).setDuration(1100);
        whooby_reads.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).setDuration(1100);
        author.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).setDuration(1100);


    }


    fun abt_author(view: View) {

        val developer = findViewById<ImageView>(R.id.developer)
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce);
        //starts the animation
        developer.startAnimation(animation);

        //inflates the about section in form of a custom toast layout
        val layout = layoutInflater.inflate(R.layout.about, findViewById(R.id.sagar))

        val myToast = Toast(applicationContext)

        myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        myToast.view = layout//setting the view of custom toast layout
        val countDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}
            override fun onFinish() {
                myToast.cancel()
            }
        }
        myToast.show()
        countDownTimer.start()

    }


    fun mainApp(view: View) {

        //this function code opens the whooby feed section  where user enters the text
        Toast.makeText(this, "opening Whooby...", Toast.LENGTH_SHORT).show();

        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce);

        val intent3 = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent3)


        if (view.getId() == R.id.whooby_button);
        run {

            val whooby = findViewById<Button>(R.id.whooby_button)
            whooby.startAnimation(animation)
            startActivity(intent3)
            overridePendingTransition(R.anim.zoom_in, R.anim.empty)
//            val handler = Handler()
//            handler.postDelayed(Runnable {
//
//            }, 1800)

        }
    }

    fun anchor_whooby(view: View) {
        //This function inflates the whooby reads activity where the model reads the messages.
        val intent4 = Intent(applicationContext, whooby::class.java)
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce);


        if (view.getId() == R.id.whooby_reads);
        run {
            val whooby = findViewById<Button>(R.id.whooby_reads)
            whooby.startAnimation(animation);
            startActivity(intent4);
            overridePendingTransition(R.anim.zoom_in,R.anim.empty)
        }
    }
}