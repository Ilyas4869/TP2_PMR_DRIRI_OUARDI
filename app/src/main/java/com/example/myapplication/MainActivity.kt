package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.ArrayMap
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.myapplication.model.ProfilListeToDo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class MainActivity : GenericActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        toolbar.title = getString(R.string.activityMain_title)
        setSupportActionBar(toolbar);

        val button_ok = findViewById<Button>(R.id.button_pseudo_ok)
        button_ok.setOnClickListener {
            saveData()
        }

        userModule.getUsersData(this);

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(menu == null)
            return false;

        val toolbar = ToolbarMenu(this, menu)
        return true;
    }

    private fun saveData() {

        val textView = findViewById<TextView>(R.id.editTextPseudo)
        val pseudo = textView.text.toString()

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

      /*  var pseudos = sharedPrefs.getStringSet(PREFS_PSEUDOS_KEY, HashSet<String>())
        // On créé une copie de l'ancien tableau
        pseudos = HashSet<String>(pseudos)
        pseudos.add(pseudo)*/

        val editor = sharedPrefs.edit()
        editor.apply {
            putString(PREFS_PSEUDO_KEY, pseudo)
           // putStringSet(PREFS_PSEUDOS_KEY, pseudos)
        }.apply()

        //Si l'utilisateur n'existe pas, on ajoute un nouveau
        if (!userModule.users!!.containsKey(pseudo.lowercase())) {
            //Le pseudo est ouveau, on créé un nouvel utilisateur
            user = ProfilListeToDo()
            user!!.login = pseudo
            userModule.users!![pseudo.lowercase()] = user!!
        }
        else {
            //Le pseudo existe déjà, on récupére l'utilisateur
            user = userModule.users!![pseudo.lowercase()]!!
        }

        userModule.saveUsersData(this)

        alerter("Pseudo enregistré!")
        textView.text = ""

        val intent = Intent(this, ChoixListActivity::class.java)
        val b = Bundle()
        b.putString(BUNDLE_PSEUDO_KEY, pseudo)
        intent.putExtras(b)
        startActivity(intent)
    }

}