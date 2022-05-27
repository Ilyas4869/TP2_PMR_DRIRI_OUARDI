package com.example.myapplication.model

class ItemToDo(var description: String = "", var fait: Boolean = false) {

    override fun toString(): String {
        return "Item: " + description + (if (fait) "[Fait]" else "[Non fait]");
    }
}