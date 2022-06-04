package com.example.whooby

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

class opening : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        val  whooby=findViewById<Button>(R.id.whooby_button)
        val author=findViewById<ImageView>(R.id.developer)
        val convert=findViewById<ImageView>(R.id.converter)
        whooby.setAlpha(0f);
        author.setAlpha(0f)
        convert.setAlpha(0f);
        whooby.setTranslationY(50F)
        author.setTranslationY(50F)
        convert.setTranslationY(50F)
        
        //Animate the alpha value to 1f and set duration as 1.5 secs.
        whooby.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).setDuration(1100);
        author.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).setDuration(1100);
        convert.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).setDuration(1100);
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_screen)

        val  whooby=findViewById<Button>(R.id.whooby_button)
        val author=findViewById<ImageView>(R.id.developer)
        val convert=findViewById<ImageView>(R.id.converter)
        whooby.setAlpha(0f);
        author.setAlpha(0f);
        convert.setAlpha(0f);
        //Animate the alpha value to 1f and set duration as 1.5 secs.
        whooby.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).setDuration(1100);
        author.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).setDuration(1100);
        convert.animate().alpha(1f).translationYBy(-50F).setStartDelay(150).setDuration(1100);

    }



    fun abt_author(view :View)
    {
        Toast.makeText(this, "opening info...", Toast.LENGTH_SHORT).show();

        val  developer=findViewById<ImageView>(R.id.developer)
        val animation :     Animation = AnimationUtils.loadAnimation(this, R.anim.bounce);

        developer.startAnimation(animation);

        val layout = layoutInflater.inflate(R.layout.about, findViewById(R.id.sagar))

        val myToast = Toast(applicationContext)

        myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
        myToast.view = layout//setting the view of custom toast layout
        val countDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) { }
            override fun onFinish() { myToast.cancel() }
        }
        myToast.show()
        countDownTimer.start()

    }



    fun mainApp(view :View)
    {
        Toast.makeText(this, "opening Whooby...", Toast.LENGTH_SHORT).show();
        val  whooby=findViewById<Button>(R.id.whooby_button)
        val animation :     Animation = AnimationUtils.loadAnimation(this, R.anim.bounce);

        val intent3 = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent3)


        if(view.getId()==R.id.whooby_button);
        run {

            val whooby = findViewById<Button>(R.id.whooby_button)
            whooby.startAnimation(animation);
            val handler = Handler()
            handler.postDelayed(Runnable {
                startActivity(intent3);
            }, 1800)

        }
    }

    fun convert(view: View)
    {

       val intent2 = Intent(applicationContext, language_select::class.java)


        if(view.getId()==R.id.converter);
        startActivity(intent2);

    }

}