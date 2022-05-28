package com.example.myapplication

import android.content.Context
import android.preference.PreferenceManager
import android.util.ArrayMap
import com.example.myapplication.model.ProfilListeToDo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class UserDataModule {

    val PREFS_DATA_KEY = "DATA_USERS"

    var users: MutableMap<String, ProfilListeToDo>? = null

    fun getUsersData(context: Context) {
        val gson = Gson()
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        val jsonString = sharedPrefs.getString(PREFS_DATA_KEY, null)
        if(jsonString != null) {
            val mapOfUsersObject: Type = object : TypeToken<ArrayMap<String, ProfilListeToDo?>?>() {}.type
            users = gson.fromJson(jsonString, mapOfUsersObject)
        }
        else
            users = ArrayMap<String, ProfilListeToDo>()
    }

    fun saveUsersData(context: Context) {
        val gson = Gson()
        val jsonString = gson.toJson(users)
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);

        val editor = sharedPrefs.edit()
        editor.apply {
            putString(PREFS_DATA_KEY, jsonString)
        }.apply()
    }
}