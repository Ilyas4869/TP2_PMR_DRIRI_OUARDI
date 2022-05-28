package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.ArrayMap
import android.widget.Toast
import com.example.myapplication.model.ProfilListeToDo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

abstract class GenericActivity : AppCompatActivity() {

    val PREFS_PSEUDO_KEY = "PSEUDO"
    //val PREFS_PSEUDOS_KEY = "PSEUDOS"
    val BUNDLE_PSEUDO_KEY = "PSEUDO"
    val BUNDLE_LISTNAME_KEY = "LIST_NAME"

    var user: ProfilListeToDo? = null
    var userModule: UserDataModule = UserDataModule()

    fun alerter(s: String) {
        val t = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        t.show()
    }

}