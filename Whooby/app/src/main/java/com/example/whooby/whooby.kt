package com.example.whooby

import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.SceneView
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import java.util.concurrent.CompletableFuture
import java.util.concurrent.ExecutionException


class whooby : AppCompatActivity() {

    val backgroundSceneView = findViewById<SceneView>(R.id.backgroundSceneView);

    val transparentSceneView = findViewById<SceneView>(R.id.transparentSceneView);
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.whooby)


         transparentSceneView.setTransparent(true);

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

    @RequiresApi(Build.VERSION_CODES.N)
    fun loadModels() {
        val dragon = ModelRenderable
            .builder()
            .setSource(
                this, Uri.parse("sampledata/aj.gltf")
            )
            .setIsFilamentGltf(true)
            .setAsyncLoadEnabled(true)
            .build()
        val backdrop = ModelRenderable
            .builder()
            .setSource(
                this, Uri.parse("sampledata/aj.gltf")
            )
            .setIsFilamentGltf(true)
            .setAsyncLoadEnabled(true)
            .build()
        CompletableFuture.allOf(dragon, backdrop)
            .handle<Any?> { ok: Void?, ex: Throwable? ->
                try {
                    val modelNode1 = Node()
                    modelNode1.renderable = dragon.get()
                    modelNode1.localScale = Vector3(0.3f, 0.3f, 0.3f)
                    modelNode1.localRotation = Quaternion.multiply(
                        Quaternion.axisAngle(Vector3(1f, 0f, 0f), 45f),
                        Quaternion.axisAngle(Vector3(0f, 1f, 0f), 75f)
                    )
                    modelNode1.localPosition = Vector3(0f, 0f, -1.0f)
                    backgroundSceneView.getScene().addChild(modelNode1)
                    val modelNode2 = Node()
                    modelNode2.renderable = backdrop.get()
                    modelNode2.localScale = Vector3(0.3f, 0.3f, 0.3f)
                    modelNode2.localRotation = Quaternion.multiply(
                        Quaternion.axisAngle(Vector3(1f, 0f, 0f), 45f),
                        Quaternion.axisAngle(Vector3(0f, 1f, 0f), 75f)
                    )
                    modelNode2.localPosition = Vector3(0f, 0f, -1.0f)
                    backgroundSceneView.getScene().addChild(modelNode2)
                    val modelNode3 = Node()
                    modelNode3.renderable = dragon.get()
                    modelNode3.localScale = Vector3(0.3f, 0.3f, 0.3f)
                    modelNode3.localRotation = Quaternion.axisAngle(
                        Vector3(
                            0f,
                            1f,
                            0f
                        ), 35f
                    )
                    modelNode3.localPosition = Vector3(0f, 0f, -1.0f)
                    transparentSceneView.getScene().addChild(modelNode3)
                    val modelNode4 = Node()
                    modelNode4.renderable = backdrop.get()
                    modelNode4.localScale = Vector3(0.3f, 0.3f, 0.3f)
                    modelNode4.localRotation = Quaternion.axisAngle(
                        Vector3(
                            0f,
                            1f,
                            0f
                        ), 35f
                    )
                    modelNode4.localPosition = Vector3(0f, 0f, -1.0f)
                    transparentSceneView.getScene().addChild(modelNode4)
                } catch (ignore: InterruptedException) {
                } catch (ignore: ExecutionException) {
                }
                null
            }
    }


}