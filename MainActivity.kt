package com.example.memesharingrecreate

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import kotlinx.android.synthetic.main.activity_main.*

var currUrl: String?=null

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        load()


    }

    private fun load(){

        progress.visibility=View.VISIBLE
        //val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"


        val jsonObject = JsonObjectRequest(
            Request.Method.GET, url,null,
            Response.Listener { response ->

                val currUrl= response.getString("url")
                Glide.with(this).load(currUrl).listener(object: RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progress.visibility=View.GONE
                        return false
                    }
                }).into(memeImage)

            },
            Response.ErrorListener {
            })

    // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObject)

    }

    fun share(view: android.view.View) {

        val intent= Intent(Intent.ACTION_SEND)
        intent.type="text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Enjoy the meme: $currUrl")
        val chooser=Intent.createChooser(intent," Send Via :")
        startActivity(chooser)
    }
    fun next(view: android.view.View) {
        load()
    }
}
