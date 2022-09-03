package com.example.whooby

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.speech.tts.TextToSpeech
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whooby.personAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable.builder
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException


class whooby : AppCompatActivity(), TextToSpeech.OnInitListener {


    lateinit var  backgroundSceneView : SceneView
    lateinit var textToSpeech: TextToSpeech
    var i=1
    var pass : Int=0
    private lateinit var  adapter: personAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.whooby)

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        val obj= MainActivity()
        pass=obj.getPass()

        val handler = Handler()
        GlobalScope.launch {

            handler.post {
                val layout = layoutInflater.inflate(R.layout.loading, findViewById(R.id.root))

                val myToast = Toast(applicationContext)

                //myToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
                myToast.view = layout//setting the view of custom toast layout

                myToast.show()

            }

        }

        // getting the recyclerview by its id
        val recyclerview = findViewById<RecyclerView>(R.id.recyclerview)

        recyclerview.bringToFront()

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this)


        val query1 = FirebaseDatabase.getInstance().getReference()

        val options: FirebaseRecyclerOptions<User> = FirebaseRecyclerOptions.Builder<User>()
            .setQuery(query1, User::class.java)
            .build()


        // This will pass the ArrayList to our Adapter
        adapter = personAdapter(options)

        // Setting the Adapter with the recyclerview
        recyclerview.adapter = adapter

//        val installIntent = Intent()
//        installIntent.action = TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA
//        startActivity(installIntent)

        backgroundSceneView = findViewById(R.id.backgroundSceneView)

        loadModels()

    }

    override fun onResume() {
        super.onResume()
        try {
            backgroundSceneView.resume()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        backgroundSceneView.pause()

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

//                        for (i in 1..20) {
//                            msg_queue.add("Item "+i)
//                        }

                        modelNode1.getRenderableInstance().animate(true).start()
                        modelNode2.getRenderableInstance().animate(true).start()

                        val obj = User()
                        val msg_queue=obj.messagePopulate()


                        while(!(msg_queue.isEmpty()))
                        {var text: String = msg_queue.first()
                            textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, null)
                            msg_queue.poll()
                        }

                    }



                } catch (ignore: InterruptedException) {
                } catch (ignore: ExecutionException) {
                }
                null
            }
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.empty,R.anim.zoom_out)
        textToSpeech.speak("", TextToSpeech.QUEUE_FLUSH, null)
    }

    fun process()
    {

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

    override fun onStart() {
        super.onStart()

        adapter.startListening()
    }

    // Function to tell the app to stop getting
    // data from database on stopping of the activity
    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}