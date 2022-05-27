package com.example.myapplication.model

class ListeToDo() {

    var titreListeToDo: String = ""
    val itemList: List<ItemToDo> = ArrayList()

    public fun rechercherItem(descriptionItem: String): ItemToDo? {
        for(item in itemList)
        {
            if (item.description == descriptionItem)
                return item;
        }
        return null;
    }

    override fun toString(): String {
        return "ListeToDo: " + titreListeToDo;
    }
}