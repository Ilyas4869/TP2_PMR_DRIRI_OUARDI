package com.example.myapplication

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.widget.Toolbar
import com.example.myapplication.data.DataProvider
import com.example.myapplication.model.ProfilListeToDo
import kotlinx.coroutines.*


class MainActivity : GenericActivity() {

    private lateinit var loader: ProgressBar

    private val dataProvider: DataProvider by lazy { DataProvider(this.application, this) }

    private val mainActivityScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        toolbar.title = getString(R.string.activityMain_title)
        setSupportActionBar(toolbar);

        updateData()


        val button_ok = findViewById<Button>(R.id.button_pseudo_ok)
        button_ok.setOnClickListener {
            loginAndSaveData()
        }

        updateAutoPseudoComplete()

        loader = findViewById<ProgressBar>(R.id.loader)
        loader.visibility = View.GONE
    }

    fun updateAutoPseudoComplete() {
        //Propositions d'auto complétion
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        val pseudos = sharedPrefs.getStringSet(PREFS_PSEUDOS_KEY, HashSet<String>())
        val textPseudo =findViewById<AutoCompleteTextView>(R.id.editTextPseudo)
        Log.i("DEBUG", pseudos!!.toTypedArray().size.toString())
        val adapterPseudos: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_list_item_1, pseudos!!.toTypedArray())
        textPseudo.setAdapter(adapterPseudos)
        textPseudo.setThreshold(1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(menu == null)
            return false;

        val toolbar = ToolbarMenu(this, menu)
        return true;
    }

    private fun loginAndSaveData() {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        val pseudoTextView = findViewById<AutoCompleteTextView>(R.id.editTextPseudo)
        val passwordTextView = findViewById<EditText>(R.id.editTextPassword)
        val pseudo = pseudoTextView.text.toString()
        val password = passwordTextView.text.toString()

        var pseudos = sharedPrefs.getStringSet(PREFS_PSEUDOS_KEY, HashSet<String>())
        // On créé une copie de l'ancien tableau
        pseudos = HashSet<String>(pseudos!!)
        pseudos.add(pseudo)

        val editor = sharedPrefs.edit()
        editor.apply {
            putString(PREFS_PSEUDO_KEY, pseudo)
            putStringSet(PREFS_PSEUDOS_KEY, pseudos)
        }.apply()

        updateAutoPseudoComplete()

        loadUserHash(pseudo, password)

    }

    private fun gotoNextActivity() {

        val intent = Intent(this, ChoixListActivity::class.java)
        val b = Bundle()
        //b.putString(BUNDLE_PSEUDO_KEY, pseudo)
        intent.putExtras(b)
        startActivity(intent)
    }



    private fun loadUserHash(user: String, password: String) {

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        val editor = sharedPrefs.edit()

        mainActivityScope.launch {
            loader.visibility = View.VISIBLE

            runCatching {
                getUserHash(user, password)
            }.fold(
                onSuccess = { token ->
                    loader.visibility = View.GONE
                    editor.apply {
                        putString(API_TOKEN_KEY, token)
                    }.apply()

                    alerter("Utilisateur connecté!")
                    gotoNextActivity()
                },
                onFailure = {
                    loader.visibility = View.GONE
                    // displayErrorScreen

                    alerter("Erreur de connection, veuillez vérifier vos identifiants!")
                    Log.e("MainActivity", "Fails -> $it")
                }
            )

        }
    }

    private suspend fun getUserHash(user: String, password: String): String {

        val hash = dataProvider.getUserHash(user, password)
        return withContext(Dispatchers.Default) {
            hash
        }
    }

    override fun onResume() {
        super.onResume()
        updateData()
    }

    override fun updateData() {
        super.updateData()
        val button_ok = findViewById<Button>(R.id.button_pseudo_ok)
        button_ok.isEnabled = isNetworkAvailable();
    }

    override fun onDestroy() {
        super.onDestroy()

        mainActivityScope.cancel()
    }
}