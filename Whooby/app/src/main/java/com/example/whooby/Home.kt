package com.example.whooby

/*
#########################################################################################################
# This class is responsible to inflate 3 different layouts that is the Whooby feeds , Whooby reads and  #
# developers info. The function  abt_author gives info about the developers. The function mainApp takes #
# user to the Whooby feeds sections where user enters text. The function anchor_Whooby takes user to    #
# section where Whooby reads the messages fed to it.                                                    #
#########################################################################################################
*/

import android.content.Intent
import android.content.pm.ActivityInfo
import android.media.MediaPlayer.OnPreparedListener
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.airbnb.lottie.LottieAnimationView


class Home : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        val whooby_feed = findViewById<LottieAnimationView>(R.id.whooby_button)
        val author = findViewById<ImageView>(R.id.developer)
        val whooby_reads = findViewById<LottieAnimationView>(R.id.whooby_reads)
        val github = findViewById<LottieAnimationView>(R.id.github)


        //sets the buttons transparency to invisible
        whooby_feed.alpha = 0f
        author.alpha = 0f
        whooby_reads.alpha = 0f
        github.alpha = 0f


        //fixes the different whooby button's animation origin always at a constant coordinate
        whooby_feed.translationY = 50F
        author.translationY = 50F
        whooby_reads.translationY = 50F
        github.translationY = 50F


        //Animate the alpha value to 1f and set duration as 1.5 secs. This is applied to button animation
        whooby_feed.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).duration = 1100
        author.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).duration = 1100
        whooby_reads.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).duration = 1100
        github.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).duration = 1100


        val videoview = findViewById<VideoView>(R.id.video) as VideoView
        val path = "android.resource://" + packageName + "/" + R.raw.abc

        videoview.setVideoURI(Uri.parse(path))
        videoview.start()

        videoview.setOnPreparedListener(OnPreparedListener { mp -> mp.isLooping = true })

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val whooby_feed = findViewById<LottieAnimationView>(R.id.whooby_button)
        val whooby_reads = findViewById<LottieAnimationView>(R.id.whooby_reads)
        val author = findViewById<ImageView>(R.id.developer)

        whooby_feed.alpha = 0f
        whooby_reads.alpha = 0f
        author.alpha = 0f

        //Animate the alpha value to 1f and set duration as 1.5 secs.
        whooby_feed.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).duration = 1100
        whooby_reads.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).duration = 1100
        author.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).duration = 1100


    }


    fun abt_author(view: View) {

        val developer = findViewById<ImageView>(R.id.developer)
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        //starts the animation
        developer.startAnimation(animation)

        //inflates the about section in form of a custom toast layout
        val layout = layoutInflater.inflate(R.layout.about, findViewById(R.id.root))

        val myToast = Toast(applicationContext)

        //myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        myToast.view = layout//setting the view of custom toast layout

        myToast.show()


    }

    fun githubStar(view: View) {

        val github = findViewById<ImageView>(R.id.github)
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce)
        //starts the animation
        github.startAnimation(animation)

        val uri =
            Uri.parse("http://github.com/Philotes-exceptus/Whooby") // missing 'http://' will cause crashed

        val intent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(intent)

    }


    fun mainApp(view: View) {

        //this function code opens the whooby feed section  where user enters the text
        Toast.makeText(this, "opening Whooby...", Toast.LENGTH_SHORT).show()

        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce)

        val intent3 = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent3)


        if (view.id == R.id.whooby_button);
        run {

            val whooby = findViewById<LottieAnimationView>(R.id.whooby_button)
            whooby.startAnimation(animation)
            startActivity(intent3)
            overridePendingTransition(R.anim.zoom_in, R.anim.empty)
        }
    }


    fun anchor_whooby(view: View) {
        //This function inflates the whooby reads activity where the model reads the messages.
        val intent4 = Intent(applicationContext, whooby::class.java)
        val animation: Animation = AnimationUtils.loadAnimation(this, R.anim.bounce)

        if (view.id == R.id.whooby_reads);
        run {
            val whooby = findViewById<LottieAnimationView>(R.id.whooby_reads)
            whooby.startAnimation(animation)
            startActivity(intent4)
            overridePendingTransition(R.anim.zoom_in, R.anim.empty)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}