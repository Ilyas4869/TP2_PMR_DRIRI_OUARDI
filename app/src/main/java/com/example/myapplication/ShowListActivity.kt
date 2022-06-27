package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Menu
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.ItemToDoAdapter
import com.example.myapplication.adapter.ListeToDoAdapter
import com.example.myapplication.data.DataProvider
import com.example.myapplication.model.ItemToDo
import com.example.myapplication.model.ListeToDo
import com.example.myapplication.model.ProfilListeToDo
import kotlinx.coroutines.*

class ShowListActivity : GenericActivity() {

    var list: RecyclerView? = null;
    private val dataProvider: DataProvider by lazy { DataProvider(this.application, this) }
    var items: List<ItemToDo>? = null;

    private val showListActivityScope = CoroutineScope(
        SupervisorJob() + Dispatchers.Main
    )

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar);
        toolbar.title = getString(R.string.itemActivity_title)
        setSupportActionBar(toolbar);

       /* val button_ok = findViewById<Button>(R.id.button_item_ok)
        button_ok.setOnClickListener {
            onClickOkButton()
        }*/

        val b = intent.extras
        var listId: Long? = null
        if (b != null) listId = b.getLong(BUNDLE_LISTID_KEY)

        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        var apiHash = sharedPrefs.getString(API_TOKEN_KEY, "")
        loadItems(listId!!, apiHash!!)


    }

    override fun onDestroy() {
        super.onDestroy()
        showListActivityScope.cancel()
    }


    private fun loadItems(listId: Long, token: String) {

        showListActivityScope.launch {
            runCatching {
                getItems(listId, token)
            }.fold(
                onSuccess = { items ->
                    this@ShowListActivity.items = items;
                    list = findViewById<RecyclerView>(R.id.liste_of_itemToDo)
                    list!!.adapter = ItemToDoAdapter(this@ShowListActivity, dataSet = items)
                    list!!.layoutManager = LinearLayoutManager(this@ShowListActivity, RecyclerView.VERTICAL, false)

                },
                onFailure = {
                    // displayErrorScreen
                    Log.e("ShowListActivity", "Fails -> $it")
                }
            )

        }
    }

    private suspend fun getItems(listId: Long, token: String): List<ItemToDo> {
        val items = dataProvider.getItems(listId, token)
        return withContext(Dispatchers.Default) {
            items.map { item ->
                ItemToDo(
                    id = item.id,
                    description = item.description,
                    fait = item.fait,
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


    override fun onResume() {
        super.onResume()
        updateData()

    }

    override fun updateData() {
        super.updateData()

        if(items != null) {
            showListActivityScope.launch {
                runCatching {
                    dataProvider.saveItems(items!!)
                }
            }
        }
    }
}