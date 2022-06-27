package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ListeToDo() {
    @PrimaryKey
    var id: Long = -1
    var titreListeToDo: String = ""

    constructor(id: Long, titre: String) : this() {
        this.id = id
        this.titreListeToDo = titre
    }

    override fun toString(): String {
        return "ListeToDo: " + titreListeToDo;
    }
}