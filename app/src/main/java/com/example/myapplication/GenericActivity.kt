package com.example.myapplication

import android.content.Context
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.ArrayMap
import android.widget.Button
import android.widget.Toast
import com.example.myapplication.model.ProfilListeToDo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

abstract class GenericActivity : AppCompatActivity() {

    val PREFS_PSEUDO_KEY = "PSEUDO"
    val PREFS_PSEUDOS_KEY = "PSEUDOS"
    val BUNDLE_LISTID_KEY = "LIST_ID"
    val API_TOKEN_KEY = "API_TOKEN_KEY"

    var user: ProfilListeToDo? = null


    fun alerter(s: String) {
        val t = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        t.show()
    }

    open fun updateData() {
    }

    fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }



}