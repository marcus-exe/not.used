package br.com.alura.recyclerviewcards.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.recyclerviewcards.R
import br.com.alura.recyclerviewcards.models.CardModel
import br.com.alura.recyclerviewcards.ui.recyclerView.MyAdapter


class RecyclerView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val dataset = listOf<CardModel>(
            CardModel(R.drawable.user_1, "Carlos"),
            CardModel(R.drawable.user_2, "Eduardo"),
            CardModel(R.drawable.user_3, "Monique"),
            CardModel(R.drawable.user_4, "Luana"),
            CardModel(R.drawable.user_5, "Diego"),
            CardModel(R.drawable.user_6, "Lucas"),
            CardModel(R.drawable.user_7, "Rafael"),
            CardModel(R.drawable.user_8, "Natalia"),
        )

        val recyclerView = findViewById<RecyclerView>(R.id.activity_recycler_view)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        val adapter = MyAdapter(dataset)
        recyclerView.adapter = adapter



    }
}