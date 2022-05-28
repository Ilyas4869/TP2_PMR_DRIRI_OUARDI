package com.example.myapplication.adapter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.ChoixListActivity
import com.example.myapplication.R
import com.example.myapplication.ShowListActivity
import com.example.myapplication.model.ListeToDo

class ListeToDoViewHolder(
    val activity: ChoixListActivity,
    listeView: View
) : RecyclerView.ViewHolder(listeView) {

    private val button = listeView.findViewById<Button>(R.id.listeToDoTitre)

    fun bind(liste: ListeToDo) {
        button.text = liste.titreListeToDo

        button.setOnClickListener {
            onListClick(liste)
        }

    }

    fun onListClick(liste: ListeToDo) {
        val intent = Intent(activity, ShowListActivity::class.java)
        val b = Bundle()
        b.putString(activity.BUNDLE_PSEUDO_KEY, activity.user!!.login)
        b.putString(activity.BUNDLE_LISTNAME_KEY, liste.titreListeToDo)
        intent.putExtras(b)
        activity.startActivity(intent)
    }
}