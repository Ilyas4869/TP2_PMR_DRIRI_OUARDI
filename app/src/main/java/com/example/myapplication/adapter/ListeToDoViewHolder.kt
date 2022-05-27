package com.example.myapplication.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.ListeToDo

class ListeToDoViewHolder(listeView: View) : RecyclerView.ViewHolder(listeView) {

    private val titre = listeView.findViewById<TextView>(R.id.listeToDoTitre)

    fun bind(liste: ListeToDo) {
        titre.text = liste.titreListeToDo
    }

}