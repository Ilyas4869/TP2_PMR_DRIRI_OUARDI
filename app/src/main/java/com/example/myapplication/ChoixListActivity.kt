package com.example.myapplication

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.myapplication.adapter.ListeToDoAdapter
import com.example.myapplication.model.ListeToDo
import com.example.myapplication.model.ProfilListeToDo

class ChoixListActivity : GenericActivity() {

    var list: RecyclerView? = null;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        toolbar.title = getString(R.string.listeActivity_title)
        setSupportActionBar(toolbar);

        val button_ok = findViewById<Button>(R.id.button_liste_ok)
        button_ok.setOnClickListener {
          onClickOkButton()
        }

        userModule.getUsersData(this)

        val b = intent.extras
        var pseudo: String? = null
        if (b != null) pseudo = b.getString(BUNDLE_PSEUDO_KEY)
        user = userModule.users?.get(pseudo!!.lowercase())

        if(user != null) {
            list = findViewById<RecyclerView>(R.id.liste_of_listeToDo)
            list!!.adapter = ListeToDoAdapter(activity = this, dataSet = provideDataSet(user!!))
            list!!.layoutManager = LinearLayoutManager(this, VERTICAL, false)
        }
    }

    private fun provideDataSet(user: ProfilListeToDo): List<ListeToDo> {
        return user.mesListeToDo;
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(menu == null)
            return false;

        val toolbar = ToolbarMenu(this, menu)
        return true;
    }

    private fun onClickOkButton() {
        val textView = findViewById<TextView>(R.id.editTextListName)
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
        textView.text = ""
    }

}