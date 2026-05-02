package br.com.alura.recyclerviewexample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val dataset: List<String>) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    //Here I do the binding to the insides of the item
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.activity_item_textView)
    }

    //Here I inflate the activity itself
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter.ViewHolder {
        val view =  LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_item_layout, parent, false)
        return ViewHolder(view)
    }

    //Now bind the textView to the database
    override fun onBindViewHolder(holder: MyAdapter.ViewHolder, position: Int) {
        holder.textView.text = dataset[position]
    }

    override fun getItemCount(): Int = dataset.size


}