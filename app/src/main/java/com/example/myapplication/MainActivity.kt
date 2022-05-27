package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        toolbar.title = getString(R.string.activityMain_title)
        setSupportActionBar(toolbar);

        val button_ok = findViewById<Button>(R.id.button_pseudo_ok)
        button_ok.setOnClickListener {
            saveData()
            //val intent = Intent(this, ChoixListActivity::class.java)
            val intent = Intent(this, ShowListActivity::class.java)
            startActivity(intent)
        }



        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        val editor = sharedPrefs.edit()
        editor.apply {
            putString("OK", "OKOKOOKOKOKOK")
        }.apply()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(menu == null)
            return false;

        val toolbar = ToolbarMenu(this, menu)
        return true;
    }

    val PREFS_PSEUDO_KEY = "PSEUDO"
    //val PREFS_PSEUDOS_KEY = "PSEUDOS"
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

        alerter("Pseudo enregistré!")
        textView.text = ""
    }

    private fun alerter(s: String) {
        val t = Toast.makeText(this, s, Toast.LENGTH_SHORT)
        t.show()
    }


}