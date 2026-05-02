package br.com.alura.cardapiglide

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainCard : AppCompatActivity() {

    private lateinit var imageView1: ImageView
    private lateinit var textView1: TextView
    private lateinit var imageView2: ImageView
    private lateinit var textView2: TextView
    private lateinit var imageView3: ImageView
    private lateinit var textView3: TextView
    private lateinit var imageView4: ImageView
    private lateinit var textView4: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //define the individual cards to populate
        val cardView1 = findViewById<View>(R.id.activity_card_1)
        val cardView2 = findViewById<View>(R.id.activity_card_2)
        val cardView3 = findViewById<View>(R.id.activity_card_3)
        val cardView4 = findViewById<View>(R.id.activity_card_4)

        //now I access the texts and image views
        imageView1 = cardView1.findViewById(R.id.activity_card_image)
        textView1 = cardView1.findViewById(R.id.activity_card_text)
        imageView2 = cardView2.findViewById(R.id.activity_card_image)
        textView2 = cardView2.findViewById(R.id.activity_card_text)
        imageView3 = cardView3.findViewById(R.id.activity_card_image)
        textView3 = cardView3.findViewById(R.id.activity_card_text)
        imageView4 = cardView4.findViewById(R.id.activity_card_image)
        textView4 = cardView4.findViewById(R.id.activity_card_text)

        //now I define the URL and text
        val imgUrl1 =
            "https://images.pexels.com/photos/1169084/pexels-photo-1169084.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        val imgUrl2 =
            "https://images.pexels.com/photos/1169084/pexels-photo-1169084.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        val imgUrl3 =
            "https://images.pexels.com/photos/1169084/pexels-photo-1169084.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        val imgUrl4 =
            "https://images.pxels.com/photos/1169084/pexels-photo-1169084.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"

        val number1 = 10
        val number2 = 30
        val number3 = 50
        val number4 = 80

        //Deserialization and Loading ImageView
        //Card 1 -> Good URL and no Delay
        Glide.with(this)
            .load(imgUrl1)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(imageView1)

        //Card 2 -> Good URL but delay - sync
        imageView2.setImageResource(R.drawable.loading)
        Handler(Looper.getMainLooper()).postDelayed({
            Glide.with(this)
                .load(imgUrl2)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imageView2)
        }, 5000)


        //Card 3 -> Good URL but delay - async
        imageView3.setImageResource(R.drawable.loading)
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            Glide.with(imageView3.context)
                .load(imgUrl3)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(imageView3)

        }

        //Card 4 -> Bad Url

        Glide.with(this)
            .load(imgUrl4)
            .placeholder(R.drawable.loading)
            .error(R.drawable.error)
            .into(imageView4)

        //Now, I load each number to their Text inside each CardView
        textView1.text = number1.toString()
        textView2.text = number2.toString()
        textView3.text = number3.toString()
        textView4.text = number4.toString()



    }
}