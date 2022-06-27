package com.example.myapplication.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.ListeToDo

@Dao
interface ListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveOrUpdate(lists : List<ListeToDo>)

    @Query("SELECT * FROM LISTETODO")
    suspend fun getLists() : List<ListeToDo>
}