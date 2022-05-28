package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ChoixListActivity
import com.example.myapplication.model.ListeToDo
import com.example.myapplication.R

class ListeToDoAdapter(private val activity: ChoixListActivity,
                       private val dataSet: List<ListeToDo>
) : RecyclerView.Adapter<ListeToDoViewHolder>() {

    override fun getItemCount(): Int = dataSet.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListeToDoViewHolder {

        val listeView = LayoutInflater.from(parent.context).inflate(R.layout.listetodo_layout, parent, false)

        return ListeToDoViewHolder(activity = activity, listeView = listeView)
    }

    override fun onBindViewHolder(holder: ListeToDoViewHolder, position: Int) {
        holder.bind(liste = dataSet[position])
    }

}