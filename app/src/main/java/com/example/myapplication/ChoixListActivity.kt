package com.example.myapplication

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.myapplication.adapter.ListeToDoAdapter
import com.example.myapplication.data.DataProvider
import com.example.myapplication.model.ListeToDo
import com.example.myapplication.model.ProfilListeToDo
import kotlinx.coroutines.*

class ChoixListActivity : GenericActivity() {

    var list: RecyclerView? = null;
    private val dataProvider: DataProvider by lazy { DataProvider(this.application, this) }
    var lists: List<ListeToDo>? = null;

    private val choixListActivityScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        toolbar.title = getString(R.string.listeActivity_title)
        setSupportActionBar(toolbar);

        /*val button_ok = findViewById<Button>(R.id.button_liste_ok)
        button_ok.setOnClickListener {
          onClickOkButton()
        }

        userModule.getUsersData(this)*/

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        var apiHash = sharedPrefs.getString(API_TOKEN_KEY, "")
        loadLists(apiHash!!)

    }

    override fun onDestroy() {
        super.onDestroy()
        choixListActivityScope.cancel()
    }


    private fun loadLists(token: String) {

        choixListActivityScope.launch {
            runCatching {
                getLists(token)
            }.fold(
                onSuccess = { items ->
                    lists = items;
                    list = findViewById<RecyclerView>(R.id.liste_of_listeToDo)
                    list!!.adapter = ListeToDoAdapter(activity = this@ChoixListActivity, dataSet = items)
                    list!!.layoutManager = LinearLayoutManager(this@ChoixListActivity, VERTICAL, false)

                },
                onFailure = {
                    // displayErrorScreen
                    Log.e("ChoixListActivity", "Fails -> $it")
                }
            )

        }
    }

    private suspend fun getLists(token: String): List<ListeToDo> {
        val lists = dataProvider.getLists(token)
        return withContext(Dispatchers.Default) {
            lists.map { list ->
                ListeToDo(
                    id = list.id,
                    titre = list.titreListeToDo,
                )
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(menu == null)
            return false;

        val toolbar = ToolbarMenu(this, menu)
        return true;
    }

    private fun onClickOkButton() {
        /*val textView = findViewById<TextView>(R.id.editTextListName)
        val titreOfList = textView.text.toString()

        val listToDo = ListeToDo()
        listToDo.titreListeToDo = titreOfList

        user!!.ajouteListe(listToDo)
        //On enregistre les données
        userModule.saveUsersData(this)

        //On met à jour le RecyclerView
        val position = list!!.adapter?.getItemCount()!! - 1;
        list!!.adapter?.notifyItemInserted(position);
        list!!.adapter?.notifyItemRangeChanged(position, position + 1);

        alerter("Liste ajoutée!")
        textView.text = ""*/
    }


    override fun onResume() {
        super.onResume()
        updateData()

    }

    override fun updateData() {
        super.updateData()

        if(lists != null) {
            choixListActivityScope.launch {
                runCatching {
                    dataProvider.saveLists(lists!!)
                }
            }
        }
    }

}