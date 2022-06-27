package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ItemToDo(
    @PrimaryKey
    var id: Long = -1,
    var description: String = "",
    var fait: Boolean = false) {

    override fun toString(): String {
        return "Item: " + description + (if (fait) "[Fait]" else "[Non fait]");
    }
}