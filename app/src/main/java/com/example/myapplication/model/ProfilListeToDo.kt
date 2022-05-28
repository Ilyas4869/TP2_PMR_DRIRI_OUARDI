package com.example.myapplication.model

class ProfilListeToDo(var login: String, var mesListeToDo: MutableList<ListeToDo>) {

    constructor() : this("", ArrayList<ListeToDo>())
    constructor(mesListeToDo: MutableList<ListeToDo>) : this("", mesListeToDo)

    fun ajouteListe(uneListe: ListeToDo) {
        if(rechercheListe(uneListe.titreListeToDo) != null)
            return;

        mesListeToDo.add(uneListe)
    }

    fun rechercheListe(titre: String): ListeToDo? {
        for(liste in mesListeToDo)
        {
            if (liste.titreListeToDo == titre)
            //Une liste portant le même nom existe déjà
                return liste;
        }
        return null;
    }


    override fun toString(): String {
        return "Profil: " + login;
    }
}