package br.com.alura.recyclerviewcards.ui.recyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.alura.recyclerviewcards.R
import br.com.alura.recyclerviewcards.models.CardModel

class MyAdapter(private val dataset: List<CardModel>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.activity_item_text)
        val imageView: ImageView = view.findViewById(R.id.activity_item_image)
    }

    override fun onCreateViewHolder( parent: ViewGroup, viewType: Int ): MyAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_card, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        val item = dataset[position]
        holder.imageView.setImageResource(item.imageSrc)
        holder.textView.text = item.name
    }

    override fun getItemCount(): Int = dataset.size

}