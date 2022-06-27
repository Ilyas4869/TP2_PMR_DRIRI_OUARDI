package com.example.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.ItemToDo
import com.example.myapplication.model.ListeToDo

@Dao
interface ItemDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(items : List<ItemToDo>)

    @Query("SELECT * FROM ITEMTODO")
    suspend fun getItems() : List<ItemToDo>
}