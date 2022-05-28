package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.ItemToDoAdapter
import com.example.myapplication.adapter.ListeToDoAdapter
import com.example.myapplication.model.ItemToDo
import com.example.myapplication.model.ListeToDo
import com.example.myapplication.model.ProfilListeToDo

class ShowListActivity : GenericActivity() {

    var list: RecyclerView? = null;
    var listToDo: ListeToDo? = null;

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        toolbar.title = getString(R.string.itemActivity_title)
        setSupportActionBar(toolbar);

        val button_ok = findViewById<Button>(R.id.button_item_ok)
        button_ok.setOnClickListener {
            onClickOkButton()
        }

        val b = intent.extras
        var pseudo: String? = null
        var listeTitre: String? = null
        if (b != null) pseudo = b.getString(BUNDLE_PSEUDO_KEY)
        if (b != null) listeTitre = b.getString(BUNDLE_LISTNAME_KEY)

        userModule.getUsersData(this)

        user = userModule.users?.get(pseudo!!.lowercase())
        listToDo = user!!.rechercheListe(listeTitre!!);

        if(user != null) {
            list = findViewById<RecyclerView>(R.id.liste_of_itemToDo)
            list!!.adapter = ItemToDoAdapter(this, dataSet = provideDataSet(listToDo!!))
            list!!.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        }
    }

    private fun provideDataSet(liste: ListeToDo): List<ItemToDo> {
        return liste.itemList;
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(menu == null)
            return false;

        val toolbar = ToolbarMenu(this, menu)
        return true;
    }

    private fun onClickOkButton() {
        val textView = findViewById<TextView>(R.id.editTextItemName)
        val titreOfItem = textView.text.toString()

        val itemToDo = ItemToDo(titreOfItem)

        listToDo!!.itemList.add(itemToDo)
        //On enregistre les données
        userModule.saveUsersData(this)

        //On met à jour le RecyclerView
        val position = list!!.adapter?.getItemCount()!! - 1;
        list!!.adapter?.notifyItemInserted(position);
        list!!.adapter?.notifyItemRangeChanged(position, position + 1);

        alerter("Item ajouté!")
        textView.text = ""
    }

}