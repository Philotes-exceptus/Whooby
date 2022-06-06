package com.example.whooby

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.filament.utils.Float4
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable.builder
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException


class whooby : AppCompatActivity() {

     lateinit var transparentSceneView : SceneView

    lateinit var  backgroundSceneView : SceneView


    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.whooby)

         backgroundSceneView = findViewById(R.id.backgroundSceneView);



        loadModels();
    }

    override fun onResume() {
        super.onResume()
        try {
            backgroundSceneView.resume()
            transparentSceneView.resume()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        backgroundSceneView.pause()
        transparentSceneView.pause()
    }


    fun loadModels() {
        val dragon = builder()
            .setSource(
                this, Uri.parse("models/dragon.glb")
            )
            .setIsFilamentGltf(true)
            .setAsyncLoadEnabled(true)
            .build()

        val backdrop = builder()
            .setSource(
                this, Uri.parse("models/backdrop.glb")
            )
            .setIsFilamentGltf(true)
            .setAsyncLoadEnabled(true)
            .build()


        CompletableFuture.allOf(dragon, backdrop)
            .handle<Any?> { ok: Void?, ex: Throwable? ->
                try {
                    val modelNode1 = Node()
                    modelNode1.renderable = dragon.get()
                    modelNode1.localScale = Vector3(0.25f, 0.25f, 0.25f)
                    modelNode1.localRotation = Quaternion.multiply(
                        Quaternion.axisAngle(Vector3(1f, 1f, 0f), 45f),
                        Quaternion.axisAngle(Vector3(0f, 1f, 0f), 75f)
                    )
                    modelNode1.localPosition = Vector3(0f, 0f, -1.0f)

                    backgroundSceneView.scene.addChild(modelNode1)
                    val modelNode2 = Node()
                    modelNode2.renderable = backdrop.get()
                    modelNode2.localScale = Vector3(0.3f, 0.3f, 0.3f)
                    modelNode2.localRotation = Quaternion.multiply(
                        Quaternion.axisAngle(Vector3(1f, 0f, 0f), 45f),
                        Quaternion.axisAngle(Vector3(0f, 1f, 0f), 75f)
                    )
                    modelNode2.localPosition = Vector3(0f, 0f, -1.0f)
                    backgroundSceneView.scene.addChild(modelNode2)

                    modelNode1.renderableInstance?.material?.filamentMaterialInstance?.setBaseColor(
                        Float4(0.1f,0.1f,0.1f,1f)
                    )

                } catch (ignore: InterruptedException) {
                } catch (ignore: ExecutionException) {
                }
                null
            }


    }
}