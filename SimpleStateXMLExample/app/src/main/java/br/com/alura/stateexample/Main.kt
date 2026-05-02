package br.com.alura.stateexample

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Main : AppCompatActivity() {
    private var counter = 0
    private lateinit var counterTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Initialize views
        counterTextView = findViewById(R.id.activity_main_text)
        val incrementButton = findViewById<Button>(R.id.activity_main_button)

        //Restore Counter if there's a saved state
        if (savedInstanceState != null) {
            counter = savedInstanceState.getInt("counter", 0)
        }
        //Set initial counter value
        updateCounter()

        //Increment Counter when button is clicked
        incrementButton.setOnClickListener {
            counter++
            updateCounter()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        //Save current state of the counter
        outState.putInt("counter", counter)
        super.onSaveInstanceState(outState)
    }

    private fun updateCounter(){
        counterTextView.text = "Counter: $counter"
    }



}