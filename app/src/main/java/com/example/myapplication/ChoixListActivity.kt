package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.example.myapplication.adapter.ListeToDoAdapter
import com.example.myapplication.model.ListeToDo

class ChoixListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choix_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        toolbar.title = getString(R.string.listeActivity_title)
        setSupportActionBar(toolbar);

        val list = findViewById<RecyclerView>(R.id.liste_of_listeToDo)
        list.adapter = ListeToDoAdapter(dataSet = provideDataSet())
        list.layoutManager = LinearLayoutManager(this, VERTICAL, false)

        val button_ok = findViewById<Button>(R.id.button_liste_ok)
        button_ok.setOnClickListener {
          onClickOkButton()
        }
    }

    private fun provideDataSet(): List<ListeToDo> {
        val result = mutableListOf<ListeToDo>()
        repeat(1_000) { index ->
            val liste = ListeToDo()
            liste.titreListeToDo = "liste $index"

            result.add(liste)
        }
        return result
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if(menu == null)
            return false;

        val toolbar = ToolbarMenu(this, menu)
        return true;
    }

    private fun onClickOkButton() {

    }

}