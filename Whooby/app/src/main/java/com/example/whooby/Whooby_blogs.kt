package com.example.whooby

import android.R
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable.builder
import com.squareup.picasso.Picasso
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException


class whooby_blogs  : AppCompatActivity(), TextToSpeech.OnInitListener {

    lateinit var  backgroundSceneView : SceneView
    lateinit var textToSpeech: TextToSpeech
    var pass : Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.blogs)

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )


        val obj= MainActivity()
        pass=obj.getPass()
        backgroundSceneView = findViewById(R.id.backgroundSceneView)

        loadModels()


    }


    fun loadModels() {
        val dragon = builder()
            .setSource(
                this, Uri.parse("models/model.glb")
            )
            .setIsFilamentGltf(true)
            .setAsyncLoadEnabled(true)
            .build()

        val backdrop = builder()
            .setSource(
                this, Uri.parse("models/backdrop2.glb")
            )
            .setIsFilamentGltf(true)
            .setAsyncLoadEnabled(true)
            .build()


        CompletableFuture.allOf(dragon, backdrop)
            .handle<Any?> { ok: Void?, ex: Throwable? ->
                try {
                    val modelNode1 = Node()
                    modelNode1.renderable = dragon.get()
                    modelNode1.localScale = Vector3(0.41f, 0.41f, 0.41f)
                    modelNode1.localRotation = Quaternion.multiply(
                        Quaternion.axisAngle(Vector3(1f, 0f, 0.2f), 30f),
                        Quaternion.axisAngle(Vector3(0f, 1f, 0f), 350f)
                    )
                    modelNode1.localPosition = Vector3(0.035f, -0.44f, -0.85f)

                    backgroundSceneView.scene.addChild(modelNode1)
                    val modelNode2 = Node()
                    modelNode2.renderable = backdrop.get()
                    modelNode2.localScale = Vector3(0.3f, 0.3f, 0.3f)
                    modelNode2.localRotation = Quaternion.multiply(
                        Quaternion.axisAngle(Vector3(1f, 0f, 0f), 45f),
                        Quaternion.axisAngle(Vector3(0f, 1f, 0f), 0f)
                    )
                    modelNode2.localPosition = Vector3(0f, 0f, -1.0f)
                    backgroundSceneView.scene.addChild(modelNode2)


                    val btn=findViewById<Button>(R.id.start)

                    textToSpeech= TextToSpeech(this,this)
                    textToSpeech.setSpeechRate(0.74f)
                    btn.setOnClickListener {
                        textToSpeech.speak("", TextToSpeech.QUEUE_FLUSH, null)




                    }


                    val begin = findViewById<Button>(R.id.start)
                    val animation: android.view.animation.Animation = android.view.animation.AnimationUtils.loadAnimation(this, com.example.whooby.R.anim.bounce);
                    //starts the animation
                    begin.startAnimation(animation)
                    modelNode1.getRenderableInstance().animate(true).start()

                    //inflates the about section in form of a custom toast layout
                    val layout = layoutInflater.inflate(
                        com.example.whooby.R.layout.blog, findViewById(
                            com.example.whooby.R.id.blog_root))

                    val myToast = android.widget.Toast(applicationContext)

                    //myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                    myToast.view = layout//setting the view of custom toast layout
                    val countDownTimer = object : android.os.CountDownTimer(5000, 5000) {
                        override fun onTick(millisUntilFinished: kotlin.Long) {}
                        override fun onFinish() {
                            myToast.cancel()
                        }
                    }
                    myToast.show()
                    countDownTimer.start()




                } catch (ignore: InterruptedException) {
                } catch (ignore: ExecutionException) {
                }
                null
            }

        var url = "https://media.geeksforgeeks.org/wp-content/cdn-uploads/logo-new-2.svg"
        val blogBG = findViewById<ImageView>(R.id.blogBg)
        Picasso.get().load(url).into(blogBG)


    }



    override fun onInit(status: Int) {

        if(status== TextToSpeech.SUCCESS)
        {
            var res :Int=1

            if(pass==1)
                res = textToSpeech.setLanguage(Locale("hin"))
            if(pass==0)
                res = textToSpeech.setLanguage(Locale.US)


            if(res==TextToSpeech.LANG_MISSING_DATA || res==TextToSpeech.LANG_NOT_SUPPORTED)
            {
                Toast.makeText(this,"language not supported",Toast.LENGTH_LONG).show()
            }
        }

    }

}