package com.example.myapplication.model

class ProfilListeToDo(var login: String, var mesListeToDo: MutableList<ListeToDo>) {

    constructor() : this("", ArrayList<ListeToDo>())
    constructor(mesListeToDo: MutableList<ListeToDo>) : this("", mesListeToDo)

    public fun ajouteListe(uneListe: ListeToDo) {
        mesListeToDo.add(uneListe)
    }

    override fun toString(): String {
        return "Profil: " + login;
    }
}