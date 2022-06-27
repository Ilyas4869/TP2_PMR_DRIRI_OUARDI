package com.example.myapplication.adapter

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.ShowListActivity
import com.example.myapplication.model.ItemToDo

class ItemToDoViewHolder(
    val activity: ShowListActivity,
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val checkBox = itemView.findViewById<CheckBox>(R.id.checkBoxItemToDoFait)

    fun bind(item: ItemToDo) {
        checkBox.isChecked = item.fait
        checkBox.text = item.description

        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            onCheckedChange(item, isChecked)
        }

    }

    fun onCheckedChange(item: ItemToDo, isChecked: Boolean) {
        item.fait = isChecked
        activity.updateData()
    }

}