package com.example.myapplication.data


//import androidx.room.Room

import android.app.Application
import android.content.Context
import android.preference.PreferenceManager
import android.service.autofill.UserData
import androidx.room.Room
import com.example.myapplication.UserDataModule
import com.example.myapplication.data.api.*
import com.example.myapplication.data.db.UserDataDatabase
import com.example.myapplication.model.ItemToDo
import com.example.myapplication.model.ListeToDo
import com.example.myapplication.model.ProfilListeToDo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


class DataProvider(app: Application, val context: Context) {

    private val DEFAULT_API_URL = "http://tomnab.fr/todo-api/"

    val clientBuilder = OkHttpClient.Builder();
    val loggingInterceptor = HttpLoggingInterceptor();
    init {
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(loggingInterceptor);
    }


    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(getApiBaseUrl())
        .client(clientBuilder.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val userService = retrofit.create<UserService>()
    private val listsService = retrofit.create<ListsService>()
    private val itemsService = retrofit.create<ItemsService>()

    var user: ProfilListeToDo? = null
    var userModule: UserDataModule = UserDataModule()

    private val database = Room.databaseBuilder(
        app,
        UserDataDatabase::class.java, "database"
    )
        .fallbackToDestructiveMigration()
        .build()



    private val listDao = database.listDao()

      suspend fun getLists(hash: String): List<ListeToDo>  {
          return try {
              val lists = listsService.getLists(hash).lists.toList()
              saveLists(lists)
              lists
          } catch (exception: Exception) {
              listDao.getLists()
          }
      }

      suspend fun saveLists(lists: List<ListeToDo>) {
          listDao.saveOrUpdate(lists)
      }

      private fun List<ListResponse>.toList(): List<ListeToDo> {
          return this.map { listResponse ->
              ListeToDo(listResponse.id, listResponse.label)
          }
      }


      private val itemDao = database.itemDao()

      suspend fun getItems(listId: Long, hash: String): List<ItemToDo>  {

          return try {
              val items = itemsService.getItems(listId, hash).items.toItem()
              saveItems(items)
              items
          } catch (exception: Exception) {
              itemDao.getItems()
          }
      }

      suspend fun saveItems(items: List<ItemToDo>) {
          itemDao.saveOrUpdate(items)
      }

      private fun List<ItemResponse>.toItem(): List<ItemToDo> {
          return this.map { itemResponse ->
              ItemToDo(itemResponse.id, itemResponse.label, itemResponse.checked)
          }
      }



    suspend fun getUserHash(user: String, password: String): String = withContext(Dispatchers.IO) {


        val requestBody: RequestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("user", user)
            .addFormDataPart("password", password)
            .build()
        val hash = userService.getUserHash(requestBody).hash
        hash
    }

    fun getApiBaseUrl(): String {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
        val url = sharedPrefs.getString("API_URL", DEFAULT_API_URL)
        return url!!
    }

}