package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.ItemToDo
import com.example.myapplication.R

class ItemToDoAdapter(
    private val dataSet: List<ItemToDo>
) : RecyclerView.Adapter<ItemToDoViewHolder>() {

    override fun getItemCount(): Int = dataSet.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemToDoViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.itemtodo_layout, parent, false)

        return ItemToDoViewHolder(itemView = itemView)
    }

    override fun onBindViewHolder(holder: ItemToDoViewHolder, position: Int) {
        holder.bind(item = dataSet[position])
    }

}