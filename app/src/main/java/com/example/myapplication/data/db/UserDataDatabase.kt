package com.example.myapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.model.ItemToDo
import com.example.myapplication.model.ListeToDo

@Database(entities = [ListeToDo::class, ItemToDo::class],version = 4)
abstract class UserDataDatabase : RoomDatabase(){

    abstract fun listDao() : ListDao
    abstract fun itemDao() : ItemDao
}