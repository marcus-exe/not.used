package br.com.alura.cardapicoil

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load
import coil.request.CachePolicy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

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

        //defining individual cards
        val cardView1 = findViewById<View>(R.id.card_view_layout_1)
        val cardView2 = findViewById<View>(R.id.card_view_layout_2)
        val cardView3 = findViewById<View>(R.id.card_view_layout_3)
        val cardView4 = findViewById<View>(R.id.card_view_layout_4)

        //Here I access each Card inside the main activity
        imageView1 = cardView1.findViewById(R.id.activity_card_view_image)
        textView1 = cardView1.findViewById(R.id.activity_card_view_text)
        imageView2 = cardView2.findViewById(R.id.activity_card_view_image)
        textView2 = cardView2.findViewById(R.id.activity_card_view_text)
        imageView3 = cardView3.findViewById(R.id.activity_card_view_image)
        textView3 = cardView3.findViewById(R.id.activity_card_view_text)
        imageView4 = cardView4.findViewById(R.id.activity_card_view_image)
        textView4 = cardView4.findViewById(R.id.activity_card_view_text)


        //url for images and some texts
        val imgUrl1 =
            "https://images.pexels.com/photos/1462618/pexels-photo-1462618.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        val imgUrl2 =
            "https://images.pexels.com/photos/1462618/pexels-photo-1462618.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        val imgUrl3 =
            "https://images.pexels.com/photos/1462618/pexels-photo-1462618.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        val imgUrl4 =
            "https://images.pxels.com/photos/1462618/pexels-photo-1462618.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1"
        val number1 = 20
        val number2 = 40
        val number3 = 60
        val number4 = 80


        //Here I deserialize the URL and load it into an ImageView


        //Card1 -> Good Url and no delay
        imageView1.load(imgUrl1) {
            crossfade(true)
            diskCachePolicy(CachePolicy.DISABLED)
            placeholder(R.drawable.loading)
            error(R.drawable.error)
        }

        //Card2 -> Good Url but Async Delay
        imageView2.setImageResource(R.drawable.loading)
        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            imageView2.load(imgUrl2) {
                crossfade(true)
                diskCachePolicy(CachePolicy.DISABLED)
                placeholder(R.drawable.loading)
                error(R.drawable.error)
            }
        }

        //Card3 -> Good Url but Sync Delay
        imageView3.setImageResource(R.drawable.loading)
        Handler(Looper.getMainLooper()).postDelayed({
            imageView3.load(imgUrl3) {
                crossfade(true)
                diskCachePolicy(CachePolicy.DISABLED)
                placeholder(R.drawable.loading)
                error(R.drawable.error)
            }
        },5000)

        //Card4 -> Bad URL
        imageView4.load(imgUrl4) {
            crossfade(true)
            diskCachePolicy(CachePolicy.DISABLED)
            placeholder(R.drawable.loading)
            error(R.drawable.error)
        }

        //Now, I load each number to their Text inside each CardView
        textView1.text = number1.toString()
        textView2.text = number2.toString()
        textView3.text = number3.toString()
        textView4.text = number4.toString()


    }

}