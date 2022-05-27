package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.ItemToDoAdapter
import com.example.myapplication.model.ItemToDo

class ShowListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        toolbar.title = getString(R.string.itemActivity_title)
        setSupportActionBar(toolbar);

        val item = findViewById<RecyclerView>(R.id.liste_of_itemToDo)
        item.adapter = ItemToDoAdapter(dataSet = provideDataSet())
        item.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val button_ok = findViewById<Button>(R.id.button_item_ok)
        button_ok.setOnClickListener {
            onClickOkButton()
        }
    }

    private fun provideDataSet(): List<ItemToDo> {
        val result = mutableListOf<ItemToDo>()
        repeat(1_000) { index ->
            val item = ItemToDo("item $index", index % 2 == 1)

            result.add(item)
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