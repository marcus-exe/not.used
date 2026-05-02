package br.com.alura.cardapi

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.NetworkPolicy
import com.squareup.picasso.Picasso

class MainCard : AppCompatActivity() {

    private lateinit var imageView1: ImageView
    private lateinit var numberTextView1: TextView
    private lateinit var imageView2: ImageView
    private lateinit var numberTextView2: TextView
    private lateinit var imageView3: ImageView
    private lateinit var numberTextView3: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //here I define the 3 individual cardViews
        val cardView1 = findViewById<View>(R.id.card_view_layout_1)
        val cardView2 = findViewById<View>(R.id.card_view_layout_2)
        val cardView3 = findViewById<View>(R.id.card_view_layout_3)

        //now I access each CardView inside the MainActivity
        imageView1 = cardView1.findViewById(R.id.activity_card_view_image)
        numberTextView1 = cardView1.findViewById(R.id.activity_card_view_text)
        imageView2 = cardView2.findViewById(R.id.activity_card_view_image)
        numberTextView2 = cardView2.findViewById(R.id.activity_card_view_text)
        imageView3 = cardView3.findViewById(R.id.activity_card_view_image)
        numberTextView3 = cardView3.findViewById(R.id.activity_card_view_text)

        //here I define the URLs for my Images and Some Texts
        val imgUrl1 =
            "https://images.pexels.com/photos/1169084/pexels-photo-1169084.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        val imgUrl2 =
            "https://images.pexels.com/photos/1169084/pexels-photo-1169084.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        val imgUrl3 =
            "https://images.pxels.com/photos/1169084/pexels-photo-1169084.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        val number1 = 10
        val number2 = 30
        val number3 = 50

        //Here I deserialize the URL and load it into an Image View
        //Card 1 -> Good URL and no Delay
        Picasso.get().load(imgUrl1)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(imageView1, object : Callback {
                override fun onSuccess() {
                    Log.d("MainCard", "Image Loaded Sucessfully")
                }

                override fun onError(e: Exception?) {
                    Log.e("MainCard", "Error loading image: ${e?.message}")
                }
            })

        //Card 2 -> Good URL, but 5s Delay
        imageView2.setImageResource(R.drawable.loading)
        Handler(Looper.getMainLooper()).postDelayed({
            Picasso.get().load(imgUrl2)
                .networkPolicy(NetworkPolicy.NO_CACHE)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imageView2, object : Callback {
                    override fun onSuccess() {
                        Log.d("MainCard", "Image Loaded Sucessfully")
                    }
                    override fun onError(e: Exception?) {
                        Log.e("MainCard", "Error loading image: ${e?.message}")
                    }
                })
        }, 5000)

        //Card 3 -> Bad URL
        Picasso.get().load(imgUrl3)
            .networkPolicy(NetworkPolicy.NO_CACHE)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(imageView3, object : Callback {
                override fun onSuccess() {
                    Log.d("MainCard", "Image Loaded Sucessfully")
                }

                override fun onError(e: Exception?) {
                    Log.e("MainCard", "Error loading image: ${e?.message}")
                }
            })

        //Now, I load each number to their Text inside each CardView
        numberTextView1.text = number1.toString()
        numberTextView2.text = number2.toString()
        numberTextView3.text = number3.toString()


    }
}