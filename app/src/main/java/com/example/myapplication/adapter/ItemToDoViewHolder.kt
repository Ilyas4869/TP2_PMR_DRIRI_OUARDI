package com.example.myapplication.adapter

import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.ItemToDo
import com.example.myapplication.model.ListeToDo

class ItemToDoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val checkBox = itemView.findViewById<CheckBox>(R.id.checkBoxItemToDoFait)

    fun bind(item: ItemToDo) {
        checkBox.isChecked = item.fait
        checkBox.text = item.description
    }

}