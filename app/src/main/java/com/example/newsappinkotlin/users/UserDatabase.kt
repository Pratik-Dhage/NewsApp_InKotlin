package com.example.newsappinkotlin.users

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [UserTable::class] ,version=1)
abstract class UserDatabase : RoomDatabase() {

    abstract fun getDao() : UserDao
}